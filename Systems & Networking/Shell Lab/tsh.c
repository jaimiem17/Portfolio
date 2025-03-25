/* CMPS2300 Shell Lab
 * tsh - A tiny shell program with job control
 * 
 * Put your name and Tulane email address here
 * Jaimie Morris
 * jmorris13@tulane.edu
 */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <ctype.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <errno.h>
#include <fcntl.h>

/* Misc manifest constants */
#define MAXLINE    1024   /* max line size */
#define MAXARGS     128   /* max args on a command line */
#define MAXJOBS      16   /* max jobs at any point in time */
#define MAXJID    1<<16   /* max job ID */

/* Job states */
#define UNDEF 0 /* undefined */
#define FG 1    /* running in foreground */
#define BG 2    /* running in background */
#define ST 3    /* stopped */

/* 
 * Jobs states: FG (foreground), BG (background), ST (stopped)
 * Job state transitions and enabling actions:
 *     FG -> ST  : ctrl-z
 *     ST -> FG  : fg command
 *     ST -> BG  : bg command
 *     BG -> FG  : fg command
 * At most 1 job can be in the FG state.
 */

/* Global variables */
extern char **environ;      /* defined in libc */
char prompt[] = "tsh> ";    /* command line prompt (DO NOT CHANGE) */
int verbose = 0;            /* if true, print additional output */
int nextjid = 1;            /* next job ID to allocate */
char sbuf[MAXLINE];         /* for composing sprintf messages */

struct job_t {              /* The job struct */
    pid_t pid;              /* job PID */
    int jid;                /* job ID [1, 2, ...] */
    int state;              /* UNDEF, BG, FG, or ST */
    char cmdline[MAXLINE];  /* command line */
};
struct job_t jobs[MAXJOBS]; /* The job list */
/* End global variables */


/* Function prototypes */

/* Here are the functions that you will implement */
void eval(char *cmdline);
int builtin_cmd(char **argv);
void do_bgfg(char **argv);
void do_redirect(char **argv);
void waitfg(pid_t pid);

void sigchld_handler(int sig);
void sigtstp_handler(int sig);
void sigint_handler(int sig);

/* Here are helper routines that we've provided for you */
int parseline(const char *cmdline, char **argv); 
void sigquit_handler(int sig);

void clearjob(struct job_t *job);
void initjobs(struct job_t *jobs);
int maxjid(struct job_t *jobs); 
int addjob(struct job_t *jobs, pid_t pid, int state, char *cmdline);
int deletejob(struct job_t *jobs, pid_t pid); 
pid_t fgpid(struct job_t *jobs);
struct job_t *getjobpid(struct job_t *jobs, pid_t pid);
struct job_t *getjobjid(struct job_t *jobs, int jid); 
int pid2jid(pid_t pid); 
void listjobs(struct job_t *jobs);

void usage(void);
void unix_error(char *msg);
void app_error(char *msg);
typedef void handler_t(int);
handler_t *Signal(int signum, handler_t *handler);

/*
 * main - The shell's main routine 
 */
int main(int argc, char **argv) 
{
    char c;
    char cmdline[MAXLINE];
    int emit_prompt = 1; /* emit prompt (default) */

    /* Redirect stderr to stdout (so that driver will get all output
     * on the pipe connected to stdout) */
    dup2(1, 2);

    /* Parse the command line */
    while ((c = getopt(argc, argv, "hvp")) != EOF) {
        switch (c) {
        case 'h':             /* print help message */
            usage();
	    break;
        case 'v':             /* emit additional diagnostic info */
            verbose = 1;
	    break;
        case 'p':             /* don't print a prompt */
            emit_prompt = 0;  /* handy for automatic testing */
	    break;
	default:
            usage();
	}
    }

    /* Install the signal handlers */

    /* These are the ones you will need to implement */
    Signal(SIGINT,  sigint_handler);   /* ctrl-c */
    Signal(SIGTSTP, sigtstp_handler);  /* ctrl-z */
    Signal(SIGCHLD, sigchld_handler);  /* Terminated or stopped child */

    /* Ignoring these signals simplifies reading from stdin/stdout */
    Signal(SIGTTIN, SIG_IGN);          /* ignore SIGTTIN */
    Signal(SIGTTOU, SIG_IGN);          /* ignore SIGTTOU */


    /* This one provides a clean way to kill the shell */
    Signal(SIGQUIT, sigquit_handler); 

    /* Initialize the job list */
    initjobs(jobs);

    /* Execute the shell's read/eval loop */
    while (1) {

	/* Read command line */
	if (emit_prompt) {
	    printf("%s", prompt);
	    fflush(stdout);
	}
	if ((fgets(cmdline, MAXLINE, stdin) == NULL) && ferror(stdin))
	    app_error("fgets error");
	if (feof(stdin)) { /* End of file (ctrl-d) */
	    fflush(stdout);
	    exit(0);
	}

	/* Evaluate the command line */
	eval(cmdline); //pg 755
	fflush(stdout);
	fflush(stdout);
    } 

    exit(0); /* control never reaches here */
}

/* 
 * eval - Evaluate the command line that the user has just typed in
 * 
 * If the user has requested a built-in command (quit, jobs, bg or fg)
 * then execute it immediately. Otherwise, fork a child process and
 * run the job in the context of the child. If the job is running in
 * the foreground, wait for it to terminate and then return.  Note:
 * each child process must have a unique process group ID so that our
 * background children don't receive SIGINT (SIGTSTP) from the kernel
 * when we type ctrl-c (ctrl-z) at the keyboard.  
*/
void eval(char *cmdline) 
{

    //declare variables
    int bg; //1 if background job | 0 if not
    char *argv [MAXARGS]; //argument list: array of strings
    pid_t pid; //ignore empty lines
    sigset_t mask;

    
    bg = parseline(cmdline, argv); // after this argv will contain parsed cmdline

    //forking and execing a child process
    if(!builtin_cmd(argv)){ 
        
        sigemptyset(&mask); //sets mask to empty set
        sigaddset(&mask, SIGCHLD); //adds sigchld to mask set
        sigprocmask(SIG_BLOCK, &mask, NULL); //must be called BEFORE FORK- changes the set of currently blocked signals

        if((pid = fork()) < 0){ // fork() launches the process
            unix_error("fork error");
        }

        if(pid == 0){// because pid is 0 -> child

            setpgid(0, 0); //sets group id for the processes
            sigprocmask(SIG_UNBLOCK, &mask, NULL); // unblocks for the child so the child can recieve signals

            do_redirect(argv);
            if (execve(argv[0],argv, environ) < 0) { //if this fails we dont want multiple tiny shells to start running
                printf("%s: Command not found\n", argv[0]);
                exit(0); //exit the child process
            }

        }

        // pid is not 0, then it is a parent:        
        // bg = 1; status = BG


        addjob(jobs, pid, bg ? BG : FG, cmdline);// add the job (indicated by pid) // bg ? BG : FG = if bg is equal to 1" else (bg = 0); status = FG
        sigprocmask(SIG_UNBLOCK, &mask, NULL); //unblocks for the parents so the parent can recieve signals


        if(!bg){
            // FOREGROUND
            // save the "interrupt" signals for the jobs
            waitfg(pid);// waits "hangs" the terminal to wait for fg job

        
        }
        else
            // BACKGROUND
            printf("[%d] (%d) %s", pid2jid(pid),(int)pid, cmdline); 
    }

    return;



}

/* 
 * parseline - Parse the command line and build the argv array.
 * 
 * Characters enclosed in single quotes are treated as a single
 * argument.  Return true if the user has requested a BG job, false if
 * the user has requested a FG job.  
 */
int parseline(const char *cmdline, char **argv) 
{
    static char array[MAXLINE]; /* holds local copy of command line */
    char *buf = array;          /* ptr that traverses command line */
    char *delim;                /* points to first space delimiter */
    int argc;                   /* number of args */
    int bg;                     /* background job? */

    strcpy(buf, cmdline);
    buf[strlen(buf)-1] = ' ';  /* replace trailing '\n' with space */
    while (*buf && (*buf == ' ')) /* ignore leading spaces */
	buf++;

    /* Build the argv list */
    argc = 0;
    if (*buf == '\'') {
	buf++;
	delim = strchr(buf, '\'');
    }
    else {
	delim = strchr(buf, ' ');
    }

    while (delim) {
	argv[argc++] = buf;
	*delim = '\0';
	buf = delim + 1;
	while (*buf && (*buf == ' ')) /* ignore spaces */
	       buf++;

	if (*buf == '\'') {
	    buf++;
	    delim = strchr(buf, '\'');
	}
	else {
	    delim = strchr(buf, ' ');
	}
    }
    argv[argc] = NULL;
    
    if (argc == 0)  /* ignore blank line */
	return 1;

    /* should the job run in the background? */
    if ((bg = (*argv[argc-1] == '&')) != 0) {
	argv[--argc] = NULL;
    }
    return bg;
}

/* 
 * builtin_cmd - If the user has typed a built-in command then execute
 *    it immediately.  
 */
int builtin_cmd(char **argv) 
{

 
    if (strcmp(argv[0], "quit") == 0){ //if type quit then exit
        exit(0);
    }  
    if (strcmp(argv[0], "bg") == 0){ //if type bg then run do_bgfg function
        do_bgfg(argv);
        return 1;
    }
    if (strcmp(argv[0], "fg") == 0){ //if type fg then run do_bgfg function
        do_bgfg(argv);
        return 1;
    }
    if (strcmp(argv[0], "jobs") == 0){ //if type jobs then lists jobs added from eval and through jobs func
        listjobs(jobs);
        return 1;
    }      
    return 0;       /* Not a builtin command */ 


}


/* 
 * do_redirect - scans argv for any use of < or > which indicate input or output redirection
 *
 */
void do_redirect(char **argv)
{
    int i;

    for(i=0; argv[i]; i++){
        if (!strcmp(argv[i],"<")) { //if input redirection
        /* add code for input redirection below */
            if(argv[i+1] == NULL){
                printf ("No input file provided\n");
                return;
            } 

            int input_file = open(argv[i+1], O_RDONLY, 0); //open read only file

            //copy stuff from file to the STDIN_NO

            dup2(input_file, STDIN_FILENO); //copies  input_file to STDIN_FILENO, overwriting the previous contents of STDIN_FILENO if any

            close(input_file);




            /* the line below cuts argv short. This
            removes the < and whatever follows from argv */
            argv[i]=NULL; //keep at end of condition | dont store symbol in argument list
        }

        else if (!strcmp(argv[i],">")) { //if output redirections
            /* add code for output redirection here */

            if(argv[i+1] == NULL){
                printf ("No output file provided.\n");
                return;
            } 

            int output_file = open(argv[i+1], O_WRONLY | O_CREAT | O_TRUNC, 0666); //if it doesnt exist already create the file, trunc deletes all and start over

            dup2(output_file, STDOUT_FILENO);//copies  output_file to STDOUT_FILENO, overwriting the previous contents of STDOUT_FILENO if any
            close(output_file);

            //pass argv[i+1]

            /* the line below cuts argv short. This
            removes the > and whatever follows from argv */
            argv[i]=NULL; //keep at end of condition
        }
    }
}

/* 
 * do_bgfg - Execute the builtin bg and fg commands
 */
void do_bgfg(char **argv) //
{
    struct job_t* job;
    pid_t pid;
    int jid;

    if (argv[1] == NULL){ //if not pid or job argument is entered
        printf ("%s command requires PID or %%jobid argument\n", argv[0]);
        return;
    }
    else if(argv[1][0] == '%'){
        jid = atoi(argv[1] + 1); // translate number after %, 1 is just a pointer so +1 it is needed to shift it to point to the number we want
        job = getjobjid(jobs, jid); //get jobs struct, //need jid because we had % earlier and we will be getting pid later

        //check if job is null then print statement 

        if(job == NULL){
            printf("%s: No such job\n", argv[1]);
            return;
        }

        pid = job -> pid; //get pid
    }

    else if(isdigit(argv[1][0])){
        
        pid = atoi(argv[1]); //we already know there is a pid so we store pid automatically
        job = getjobpid(jobs, pid); //gets job id for the process

        if(job == NULL){ //if no job entered
            printf("(%d): No such process\n", pid);
            return;
         
        }
       
    }
    else{
        printf("%s: argument must be a PID or %%jobid\n", argv[0]);  // fg: argument must be a PID or %jobid
        return;
    }


    //if fg typed
    if(!strcmp(argv[0], "bg")){
        printf("[%d] (%d) %s", job -> jid, job -> pid, job -> cmdline); //[1] (1115466) ./myspin 20
        job -> state = BG; //changes state of job to background
        kill(-pid, SIGCONT); //kill sends the signal. A process stops and remains stopped until recieves SIGCONT signal in which it will run again
        
    }

    else if(!strcmp(argv[0], "fg")){
        job -> state = FG; //changes state of job to foreground
        kill(-pid, SIGCONT); //kill sends the signal. A process stops and remains stopped until recieves SIGCONT signal in which it will run again
        waitfg(job -> pid);
    }
    
    return;
}

/* 
 * waitfg - Block until process pid is no longer the foreground process
 */
void waitfg(pid_t pid)
{
    //create a job/initialize /getjobpid(jobs, pid)
    // if you have pid of the process, get the job from the joblist
    struct job_t* job = getjobpid(jobs, pid);

    if(job != NULL){ // while we still have the job
        while(pid==fgpid(jobs)){ // while still a FG -> return pid from the jobs
            sleep(1);
        }
    }
        
    return;
    
}

/*****************
 * Signal handlers
 *****************/

/* 
 * sigchld_handler - The kernel sends a SIGCHLD to the shell whenever
 *     a child job terminates (becomes a zombie), or stops because it
 *     received a SIGSTOP or SIGTSTP signal. The handler reaps all
 *     available zombie children, but doesn't wait for any other
 *     currently running children to terminate.  
 */
void sigchld_handler(int sig) 
{

    // //notify if something happens to child
    pid_t pid;
    int status;
    while ((pid = waitpid(-1, &status, WNOHANG | WUNTRACED)) > 0){ //reap child
        if(WIFSIGNALED(status)){//Returns true if the child process terminated be- cause of a signal that was not caught.
            
            printf("Job [%d] (%d) terminated by signal %d\n", pid2jid(pid), pid,  WTERMSIG(status));
            
            // deletejob
            deletejob(jobs, pid);
        }
        else if(WIFSTOPPED(status)){ //Returns the number of the signal that caused the child process to terminate. This status is only defined if WIFSIGNALED() returned true.
            
            printf("Job [%d] (%d) stopped by signal %d\n", pid2jid(pid), pid, WSTOPSIG(status)); //WIFSTOPPED(status) returns true if the child that caused the return is currently stopped.
            
            // change the state of the job to ST
            getjobpid(jobs, pid) -> state = ST; //changes it to stopped
        }
        else if (WIFEXITED(status)){ //if normal execution
            if(verbose){
                printf("child %d terminated normally with exit status=%d\n", pid, WEXITSTATUS(status)); //WEXITSTATUS(status) returns the exit status of a normally terminated child //only defined if WIFEXITED() returned true
            }
            deletejob(jobs, pid);
        }
        return;
    } 
}

/* 
 * sigint_handler - The kernel sends a SIGINT to the shell whenver the
 *    user types ctrl-c at the keyboard.  Catch it and send it along
 *    to the foreground job.  
 */
void sigint_handler(int sig) 
{

    pid_t pid = fgpid(jobs);
    kill(-pid, sig); //kill sends the signal. Sends sig to all the processes defined by pid (child processes) negative makes it so it goes to child as well.
    return;
}

/*
 * sigtstp_handler - The kernel sends a SIGTSTP to the shell whenever
 *     the user types ctrl-z at the keyboard. Catch it and suspend the
 *     foreground job by sending it a SIGTSTP.  
 */
void sigtstp_handler(int sig) 
{
    pid_t pid = fgpid(jobs); //sets pid to fgpid
    kill(-pid, sig); //kill sends the signal. Sends sig to all the processes defined by pid (child processes) negative makes it so it goes to child as well.
    return;
}

/*********************
 * End signal handlers
 *********************/

/***********************************************
 * Helper routines that manipulate the job list
 **********************************************/

/* clearjob - Clear the entries in a job struct */
void clearjob(struct job_t *job) {
    job->pid = 0;
    job->jid = 0;
    job->state = UNDEF;
    job->cmdline[0] = '\0';
}

/* initjobs - Initialize the job list */
void initjobs(struct job_t *jobs) {
    int i;

    for (i = 0; i < MAXJOBS; i++)
	clearjob(&jobs[i]);
}

/* maxjid - Returns largest allocated job ID */
int maxjid(struct job_t *jobs) 
{
    int i, max=0;

    for (i = 0; i < MAXJOBS; i++)
	if (jobs[i].jid > max)
	    max = jobs[i].jid;
    return max;
}

/* addjob - Add a job to the job list */
int addjob(struct job_t *jobs, pid_t pid, int state, char *cmdline) 
{
    int i;
    
    if (pid < 1)
	return 0;

    for (i = 0; i < MAXJOBS; i++) {
	if (jobs[i].pid == 0) {
	    jobs[i].pid = pid;
	    jobs[i].state = state;
	    jobs[i].jid = nextjid++;
	    if (nextjid > MAXJOBS)
		nextjid = 1;
	    strcpy(jobs[i].cmdline, cmdline);
  	    if(verbose){
	        printf("Added job [%d] %d %s\n", jobs[i].jid, jobs[i].pid, jobs[i].cmdline);
            }
            return 1;
	}
    }
    printf("Tried to create too many jobs\n");
    return 0;
}

/* deletejob - Delete a job whose PID=pid from the job list */
int deletejob(struct job_t *jobs, pid_t pid) 
{
    int i;

    if (pid < 1)
	return 0;

    for (i = 0; i < MAXJOBS; i++) {
	if (jobs[i].pid == pid) {
	    clearjob(&jobs[i]);
	    nextjid = maxjid(jobs)+1;
	    return 1;
	}
    }
    return 0;
}

/* fgpid - Return PID of current foreground job, 0 if no such job */
pid_t fgpid(struct job_t *jobs) {
    int i;

    for (i = 0; i < MAXJOBS; i++)
	if (jobs[i].state == FG)
	    return jobs[i].pid;
    return 0;
}

/* getjobpid  - Find a job (by PID) on the job list */
struct job_t *getjobpid(struct job_t *jobs, pid_t pid) {
    int i;

    if (pid < 1)
	return NULL;
    for (i = 0; i < MAXJOBS; i++)
	if (jobs[i].pid == pid)
	    return &jobs[i];
    return NULL;
}

/* getjobjid  - Find a job (by JID) on the job list */
struct job_t *getjobjid(struct job_t *jobs, int jid) 
{
    int i;

    if (jid < 1)
	return NULL;
    for (i = 0; i < MAXJOBS; i++)
	if (jobs[i].jid == jid)
	    return &jobs[i];
    return NULL;
}

/* pid2jid - Map process ID to job ID */
int pid2jid(pid_t pid) 
{
    int i;

    if (pid < 1)
	return 0;
    for (i = 0; i < MAXJOBS; i++)
	if (jobs[i].pid == pid) {
            return jobs[i].jid;
        }
    return 0;
}

/* listjobs - Print the job list */
void listjobs(struct job_t *jobs) 
{
    int i;
    
    for (i = 0; i < MAXJOBS; i++) {
	if (jobs[i].pid != 0) {
	    printf("[%d] (%d) ", jobs[i].jid, jobs[i].pid);
	    switch (jobs[i].state) {
		case BG: 
		    printf("Running ");
		    break;
		case FG: 
		    printf("Foreground ");
		    break;
		case ST: 
		    printf("Stopped ");
		    break;
	    default:
		    printf("listjobs: Internal error: job[%d].state=%d ", 
			   i, jobs[i].state);
	    }
	    printf("%s", jobs[i].cmdline);
	}
    }
}
/******************************
 * end job list helper routines
 ******************************/


/***********************
 * Other helper routines
 ***********************/

/*
 * usage - print a help message
 */
void usage(void) 
{
    printf("Usage: shell [-hvp]\n");
    printf("   -h   print this message\n");
    printf("   -v   print additional diagnostic information\n");
    printf("   -p   do not emit a command prompt\n");
    exit(1);
}

/*
 * unix_error - unix-style error routine
 */
void unix_error(char *msg)
{
    fprintf(stdout, "%s: %s\n", msg, strerror(errno));
    exit(1);
}

/*
 * app_error - application-style error routine
 */
void app_error(char *msg)
{
    fprintf(stdout, "%s\n", msg);
    exit(1);
}

/*
 * Signal - wrapper for the sigaction function
 */
handler_t *Signal(int signum, handler_t *handler) 
{
    struct sigaction action, old_action;

    action.sa_handler = handler;  
    sigemptyset(&action.sa_mask); /* block sigs of type being handled */
    action.sa_flags = SA_RESTART; /* restart syscalls if possible */

    if (sigaction(signum, &action, &old_action) < 0)
	unix_error("Signal error");
    return (old_action.sa_handler);
}

/*
 * sigquit_handler - The driver program can gracefully terminate the
 *    child shell by sending it a SIGQUIT signal.
 */
void sigquit_handler(int sig) 
{
    printf("Terminating after receipt of SIGQUIT signal\n");
    exit(1);
}



