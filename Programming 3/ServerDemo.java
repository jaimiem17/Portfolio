import java.io.*;
import java.net.*;
import java.util.*;
//This server is supposed to interact with web browsers
public class ServerDemo {

	public ServerDemo(){

		ServerSocket serverSocket=null;

		//create a new server socket like usual
		try{

			serverSocket = new ServerSocket(0);

		}catch(IOException e){
			System.out.println("server not set up properly");
			System.exit(-1);
		}

		try{	
			System.out.println("The address"+InetAddress.getLocalHost().getHostAddress());
			System.out.println("Port: "+ serverSocket.getLocalPort());
		}catch(IOException e){
			System.out.println("Error retrieving host name");
			System.exit(-1);
		}

		while(true){
			Socket clientSock = null;
			try{
				clientSock = serverSocket.accept();
			}catch(IOException e){
				System.out.println("accept issue");
				System.exit(-1);
			}

			Thread clientThread = new Thread(new ClientHandler(clientSock));
			clientThread.start();
		}
	}

	public class ClientHandler implements Runnable{

		private Socket sock;
		private Scanner clientIn;
		private PrintWriter clientOut;

		public ClientHandler(Socket theSock){
			
			sock = theSock;
			try{
				clientIn = new Scanner(theSock.getInputStream());
				clientOut = new PrintWriter(theSock.getOutputStream());
			}catch(IOException e){
				System.out.println("Error retrieving input/output socket streams");
				System.exit(-1);
			}
		}
		
		public void run(){
			
			//reads all lines in from the client browser
			//the clients last line is always empty
			String line;
			do{
			
				line = clientIn.nextLine();
				System.out.println(line);
			}while(line.length() !=0);
						
			clientIn.close();
			clientOut.close();
			try {
				sock.close();
			} catch (IOException e) {
				System.out.println("Error closing socket");
				System.exit(-1);
			}
		}
	}

	public static void main(String[] args){

		new ServerDemo();

	}
}
