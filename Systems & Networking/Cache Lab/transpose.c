/* CMPS 2300 Cache Lab 
 * Student name: Jaimie Morris
 * Tulane email: jmorris13@tulane.edu
 *
 * transpose.c - Matrix transpose B = A^T
 *
 * A transpose function is evaluated by counting the number of misses
 * on a 1KB direct mapped cache with a block size of 32 bytes.
 *
 */ 
#include <stdio.h>
#include "cachelab.h"

int is_transpose(int M, int N, int A[N][M], int B[M][N]);

/* 
 * transpose_submit - This is the solution transpose function that you
 *     will be graded on for Part B of the assignment. Do not change
 *     the description string "Transpose submission", as the driver
 *     searches for that string to identify the transpose function to
 *     be graded. 
 */


char transpose_submit_desc[] = "Transpose submission";
void transpose_submit(int M, int N, int A[N][M], int B[M][N])
{
    /* NOTE: Programming rules (p.4) specify a 
     * maximum of 12 local (stack) variables */

    // int i, j, tmp;
    int deferred_index, deferred_value;
    int i, j, ii, jj; /*local (stack) variables */
   

    /* To start: below we are showing you a simple 
     * row-wise scan transpose using two for loops.
     *
     * This is a valid transpose, but is far too slow
     * due to high cache misses. 
     *
     * Students: your job is to reduce cache misses by
     *           improving the code below.*/


    if (M == 61 || N == 67){ //if matrix of 61x67
        for (i = 0; i < (N/16)*16; i+=16){ // because the matrix is not a square and neither numbers have easy GCDs the best number to go up until is N/blocking size * blocking size in order to be able make a square so we can iterate without going out of bounds and also go through the diagonal 
            for(j=0; j < M; j+=16){ // doesnt need to be adjusted b/c it will run however many times the first loop runs
                for(ii = i; ii < i+16; ii++){ //ii and jj are inner loops for values to be transposed
                   for(jj = j; jj < j+16; jj++){

                        if(ii != jj) { //if not equal we can transpose normally
                            B[jj][ii] = A[ii][jj];
                            
                        }
                        //if ii and jj are equal we are in the diagonal and values need to be deferred for reassignment
                        //by temporariliy storing these and reassigning them later it avoids redundant rewrites and makes more efficient use of memory and cache
                        //avoids conflict misses!
                        else{ 
                            
                            deferred_index = jj; 
                            deferred_value = A[ii][jj]; 
                        }
                    }
                    
                    //transposing the diagonal
                    if (ii == deferred_index) {
                        B[deferred_index][ii] = deferred_value; 
                    }

                }
            }
        }
    
        //sinces we only went to (N/16)*16 now we have to complete the rest of the matrix
        for(i = (N/16)*16; i < N; i++){
            for(j = 0; j < M; j++){
                B[j][i] = A[i][j];
            }
        }

    }

    else if (N == 64){ //if 64x64 matrix
        
        for (i = 0; i < N; i += 4) { //using a smaller number for blocking (4) was optimal for this case. blocking by small chunks of data keeps data close together in memory making it more likely that the computer will reuse the data making the prosses faster
            for (j = 0; j < N; j += 4) {
                for (ii = i; ii < i + 4; ii++) {
                    for (jj = j; jj < j + 4; jj++) {

                        if (ii != jj) { //if not equal we can transpose normally
                            B[jj][ii] = A[ii][jj];
                        } 
                        else {
                            deferred_value = A[ii][jj]; //saves only value
                        }
                    }

                    if (i == j) { //still a little unsure on how this saved more cache misses but it works more efficiently than ii == jj
                        B[ii][ii] = deferred_value; 
                    }
                }
            }
        }

}


    else{


        for (i = 0; i < N; i+=8){ //blocking by using a smaller number than the original helps avoid capacity misses and also keeps data closer in memory; utilizes spatial locality
            for(j=0; j < M; j+=8){
                for(ii = i; ii < i+8; ii++){
                    for(jj = j; jj < j+8; jj++){

                        if(ii != jj) { //if data is not on the diagonal transpose normally
                            B[jj][ii] = A[ii][jj];
                        }

                        //if ii and jj are equal we are in the diagonal and values need to be deferred for reassignment
                        //by temporariliy storing these and reassigning them later it avoids redundant rewrites and makes more efficient use of memory and cache
                        //avoids conflict misses!
                        else{
                            deferred_index = jj;
                            deferred_value = A[ii][jj];
                        }
                    }

                    //transposing the deferred values
                    if (ii == deferred_index) {
                        B[deferred_index][ii] = deferred_value;
                    }

                }
            }
        }
    }



}
/*
 * registerFunctions - This function registers your transpose
 *     functions with the driver.  At runtime, the driver will
 *     evaluate each of the registered functions and summarize their
 *     performance. This is a handy way to experiment with different
 *     transpose strategies.
 */
void registerFunctions()
{
    /* Required: Register your solution function */
    registerTransFunction(transpose_submit, transpose_submit_desc); 

    /* Optional: you may register any additional transpose functions 
     * below here. This is useful if you'd like to test several 
     * different functions at one time.
     *
     * If you choose to register additional functions for testing,
     * you must provide two things: 
     *    a function name (type void, same 4 arguments as transpose_submit)
     *    a descrption string (character array) */
    //registerTransFunction(transpose_funcion_name, transpose_function_desc); 

}

/* 
 * is_transpose - This helper function checks if B is the transpose of
 *     A. You can check the correctness of your transpose by calling
 *     it before returning from the transpose function.
 */
int is_transpose(int M, int N, int A[N][M], int B[M][N])
{
    int i, j;

    for (i = 0; i < N; i++) {
        for (j = 0; j < M; ++j) {
            if (A[i][j] != B[j][i]) {
                return 0;
            }
        }
    }
    return 1;
}

