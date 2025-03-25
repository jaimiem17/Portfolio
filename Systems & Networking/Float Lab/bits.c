/* 
 * The Floating Point Lab 
 * CMPS 2300 Fall 2023
 *
 **********
 * STEP 0 * <Please put your name and Tulane email address here>
 *
 * Jaimie Morris
 * jmorris13@tulane.edu
 *
 **********
 *
 * bits.c - Source file with your solutions to the lab.
 *          This is the file you will hand in to gradescope.
 *
 * As with The Data Lab, you must include comments with each
 * solution explaining how your code works.
 */

#if 0
/******************************************************
 * STEP 1: Read the following instructions carefully. *
 ******************************************************/

/**********************************
 *  FLOATING POINT CODING RULES   *
 **********************************/

For this float lab assignment, you will implement some common
single-precision floating-point operations.  In this section, you are
allowed to use standard control structures (conditionals, loops), and
you may use both int and unsigned data types, including
arbitrary unsigned and integer constants.  You may not use any unions,
structs, or arrays.  Most significantly, you may not use any floating
point data types, operations, or constants.  Instead, any
floating-point operand will be passed to the function as having type
unsigned, and any returned floating-point value will be of type
unsigned.  Your code should perform the bit manipulations that
implement the specified floating point operations.

Functions float_neg and float_twice must handle the full
range of possible argument values, including not-a-number (NaN) and
infinity. We will follow a convention that for any function returning
a NaN value, it will return the one with bit representation 0x7FC00000.

The included program fshow helps you understand the structure
of floating point numbers. You can use fshow to see what an
arbitrary pattern represents as a floating-point number:

  unix> ./fshow 2080374784

  Floating point value 2.658455992e+36
  Bit Representation 0x7c000000, sign = 0, exponent = f8, fraction = 000000
  Normalized.  1.0000000000 X 2^(121)

You can also give fshow hexadecimal and floating point values,
and it will decipher their bit structure.


NOTES:
  1. Use the dlc (data lab checker) compiler (described in the handout) to 
     check the legality of your solutions.
  2. Each function has a maximum number of operators (! ~ & ^ | + << >>)
     that you are allowed to use for your implementation of the function. 
     The max operator count is checked by dlc. Note that '=' is not 
     counted; you may use as many of these as you want without penalty.
  3. Use the btest test harness to check your functions for correctness.
  4. The maximum number of operations for each function is given in the
     header comment for each function. If there are any inconsistencies 
     between the maximum ops in the writeup and in this file, consider
     this file the authoritative source.
*/

/**********************************************************************************
 * STEP 2: Modify the following functions according the coding rules given above. *
 *                                                                                *
 *         You MUST explain each function in a comment                            *
 *         if you want to receive credit                                          *
 **********************************************************************************/

#endif
/* 
 * float_abs - Return bit-level equivalent of absolute value of f for
 *   floating point argument f.
 *   Both the argument and result are passed as unsigned int's, but
 *   they are to be interpreted as the bit-level representations of
 *   single-precision floating point values.
 *   When argument is NaN, return argument..
 *   Legal ops: Any integer/unsigned operations incl. ||, &&. also if, while
 *   Max ops: 10
 *   Rating: 2
 */
unsigned float_abs(unsigned uf) {
  unsigned int e = uf >> 23 & 0xff; //shifts to get only the exponent part
  unsigned int s = uf >> 31 & 0x1; //shifts to get only the sign part
  unsigned int f = uf & 0x007fffff; //gets the fraction bits only

  //created to use less operators
  unsigned int flip = uf & 0x7fffffff; // and uf by 011111.... (and so on) to flip the sign yet preserve the rest of the number else return itself


  // if the exponent is all ones then its NaN or infinity so this nested if checks if the fraction is all zeroes so we know if its infinity or NaN
  if(e == 0xff){
    if(f != 0){
      return uf;
    }

    //if the sign is negative then and it by 011111.... (and so on) to flip the sign yet preserve the rest of the number else return itself
    else if(s == 1){
      return flip;
    }
    else{
      return uf;
    }
  }

  // if its positive return itself if negative then return itself
  else if(s == 0){
    return uf; 
  }
  //AGAIN if the sign is negative then and it by 011111111.... (and so on) to flip the sign yet preserve the rest of the number
  else{
   return flip;
}

}
/* 
 * float_neg - Return bit-level equivalent of expression -f for
 *   floating point argument f.
 *   Both the argument and result are passed as unsigned int's, but
 *   they are to be interpreted as the bit-level representations of
 *   single-precision floating point values.
 *   When argument is NaN, return argument.
 *   Legal ops: Any integer/unsigned operations incl. ||, &&. also if, while
 *   Max ops: 10
 *   Rating: 2
 */
unsigned float_neg(unsigned uf) {
  unsigned int e = uf >> 23 & 0xff; //shifts to get only the exponent part
  unsigned int s = uf >> 31; //shifts to get only the sign part
  unsigned int f = uf & 0x007fffff;

  //created to use less operators
  unsigned int flip = uf & 0x7fffffff; // and uf by 011111.... (and so on) to flip the sign yet preserve the rest of the number else return itself
  unsigned int flipNeg = uf ^ 0x80000000;

  // if the exponent is all ones then its NaN or infinity so this nested if checks if the fraction is all zeroes so we know if its infinity or NaN
  if(e == 0xff){
    if(f != 0){ //returns argument if NaN
      return uf;
    }
    else if(s == 1){ //returns the positive value if uf is negative
      return flip;
    }
    else{ //returns negative value if uf is positive
      return flipNeg;
    }
  
  }
  // if its positive return itself if negative then return itself anded by 0x7fffffff to get the positive value or use ^ to get the negative value
  else if(s == 0){
    return flipNeg; 
  }

   return flip;
}


/* 
 * float_half - Return bit-level equivalent of expression 0.5*f for
 *   floating point argument f.
 *   Both the argument and result are passed as unsigned int's, but
 *   they are to be interpreted as the bit-level representation of
 *   single-precision floating point values.
 *   When argument is NaN, return argument
 *   Legal ops: Any integer/unsigned operations incl. ||, &&. also if, while
 *   Max ops: 30
 *   Rating: 4
 */
unsigned float_half(unsigned uf) {
  unsigned int e = uf >> 23 & 0xff; //shifts to get only the exponent part
  unsigned int s = uf >> 31; //shifts to get only the sign part
  unsigned int f = uf & 0x007fffff; ///gets fraction bits
  unsigned int noSign = uf & 0x7fffffff; //same value as uf without the sign bit

  //checks for the case where fraction bits end on _____11 after rounding
  unsigned int checks = f & 0x3;
  unsigned int checks2 = (checks == 0x3); 

  if(e == 0xff){//special case returns argument if NaN
    return uf;
  }

  //SHIFT the fraftion bits right and then add the rounded up bits
  else if((e & 0xff) == 0x00){
    f = f >> 1; //gets the denormalized values like twice but opposite for half
    f += checks2;
  }
  //checking if it is a normalized value but it has 1 in the exponent
  //normalized to---> denormalized case
  else if((e & 0xff) == 0x01){
    e = 0; //all denormalized values have 0s
    noSign >>= 1;
    noSign += checks2;
    f = noSign & 0x7fffff; // gets correct fraction

  }
  else{
    e = e - 1; //gets the normalized value -- subtracts exponent value by 1 to get half the number
  }

  return s << 31 | e << 23 | f; //places bits back into correct spot
}
/* 
 * float_twice - Return bit-level equivalent of expression 2*f for
 *   floating point argument f.
 *   Both the argument and result are passed as unsigned int's, but
 *   they are to be interpreted as the bit-level representation of
 *   single-precision floating point values.
 *   When argument is NaN, return argument
 *   Legal ops: Any integer/unsigned operations incl. ||, &&. also if, while
 *   Max ops: 30
 *   Rating: 4
 */
unsigned float_twice(unsigned uf) {
  unsigned int e = uf >> 23 & 0xff; //shifts to get only the exponent part
  unsigned int s = uf >> 31; //shifts to get only the sign part
  unsigned int f = uf & 0x007fffff;

  if(e == 0xff){//returns argument if NaN ; special case
      return uf;
  }

  if(e == 0){
    f = f << 1; //denormalized values --  shift left only by 1 number
  }
  else{
    e = e + 1; //normalized values -- adding one to the exponent doubles the number
  }

  return s << 31 | e << 23 | f; //returns sign bit, exponent bit, and fraction bit back into its place

}
