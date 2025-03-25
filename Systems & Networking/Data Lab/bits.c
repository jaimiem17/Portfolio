/* 
 * The Data Lab, Tulane CMPS 2300, Fall 2023
 * 
 **********
 * STEP 0 * YOU MUST READ the rest of this comment and then put your name
 *        * in the place intended below.
 **********
 *
 * By putting your name below you agree that you understand the rules below:
 *
 * You must comment your code! Your solutions will receive no credit if they
 * are not accompanied (in this file) by adequate comments which explain
 * WHY your solution works.
 * 
 * Please note the emphasis above on the word why. Comments which merely
 * restate what the code does (“shift left by 31 bits”, “add one”) are not 
 * sufficient; these are obvious to your instructors who can also read C. 
 * Your comment must explain why you decided to perform that operation.
 *
 * Now please put your name and Tulane email address here, replacing
 * the example below:
 *
 *  Jaimie Morris <jmorris13@tulane.edu>
 */

/*
 * bits.c - Source file with your solutions to the Lab.
 *          This is the file you will edit and hand in.
 *
 * NOTE: Do not include the <stdio.h> header; it confuses the dlc
 * compiler. You can still use printf for debugging without including
 * <stdio.h>, although you might get a compiler warning. In general,
 * it's not good practice to ignore compiler warnings, but in this
 * case it's OK.  
 */

#if 0 /* Do not edit anything until you reach step 2 */
/******************************************************
 * STEP 1: Read the following instructions carefully. *
 ******************************************************/

You will provide your solution to the Data Lab by
editing the collection of functions in this source file.

You will ONLY turn in this file (bits.c), everything else
is provided to help you check your work.

INTEGER CODING RULES:
 
  Replace the "return" statement in each function with one
  or more lines of C code that implements the function. Your code 
  must conform to the following style:
 
  int Funct(arg1, arg2, ...) {
      /* brief description of how your implementation works */
      int var1 = Expr1;
      ...
      int varM = ExprM;

      varJ = ExprJ;
      ...
      varN = ExprN;
      return ExprR;
  }

  Each "Expr" is an expression using ONLY the following:
  1. Integer constants 0 through 255 (0xFF), inclusive. You are
      not allowed to use big constants such as 0xffffffff.
  2. Function arguments and local variables (no global variables).
  3. Unary integer operations ! ~
  4. Binary integer operations & ^ | + << >>
    
  Some of the problems restrict the set of allowed operators even further.
  Each "Expr" may consist of multiple operators. You are not restricted to
  one operator per line.

  You are expressly forbidden to:
  1. Use any control constructs such as if, do, while, for, switch, etc.
  2. Define or use any macros.
  3. Define any additional functions in this file.
  4. Call any functions.
  5. Use any other operations, such as &&, ||, -, or ?:
  6. Use any form of casting.
  7. Use any data type other than int.  This implies that you
     cannot use arrays, structs, or unions.
 
  You may assume that your machine:
  1. Uses 2s complement, 32-bit representations of integers.
  2. Performs right shifts arithmetically.
  3. Has unpredictable behavior when shifting an integer by more
     than the word size.

EXAMPLES OF ACCEPTABLE CODING STYLE:
  /*
   * pow2plus1 - returns 2^x + 1, where 0 <= x <= 31
   */
  int pow2plus1(int x) {
     /* exploit ability of shifts to compute powers of 2 */
     return (1 << x) + 1;
  }

  /*
   * pow2plus4 - returns 2^x + 4, where 0 <= x <= 31
   */
  int pow2plus4(int x) {
     /* exploit ability of shifts to compute powers of 2 */
     int result = (1 << x);
     result += 4;
     return result;
  }

REMINDER: Use the ./btest program to check your functions
          for correctness after making any changes. The
          program ./driver.pl will check for illegal
          operators and give you your final score.
#endif

/******************************************************************************
 * STEP 2: Modify the following functions according to the coding rules given *
 *                                                                            *
 *         You MUST explain each function in a comment                        *
 *         if you want to receive credit                                      *
 *****************************************************************************/
/***************************************************
 * BOOLEAN operations (7 puzzles, 13 points total) *
 ***************************************************/
/*
 * bitOr - x|y using only ~ and &
 *   Example: bitOr(6, 5) = 7
 *   Legal ops: ~ &
 *   Max ops: 8
 *   Rating: 1
 *  
 *   This solution returns the complement of the complement of x AND the complement of y
 */
int bitOr(int x, int y) {
  return ~(~x & ~y);
}
/* 
 * bitAnd - x&y using only ~ and | 
 *   Example: bitAnd(6, 5) = 4
 *   Legal ops: ~ |
 *   Max ops: 8
 *   Rating: 1
 *  
 *   This solution returns the complement of the complement of x OR the complement of y
 */
int bitAnd(int x, int y) {
  return ~(~x | ~y);
}
/* 
 * bitXor - x^y using only ~ and & 
 *   Example: bitXor(4, 5) = 1
 *   Legal ops: ~ &
 *   Max ops: 14
 *   Rating: 2
 *  
 *   This solution uses complement (~) to solve Xor. It takes the complement of the complement of x AND the complement of y AND the complement of x & y
 */
int bitXor(int x, int y) {
  return ~(~x & ~y) & ~ (x & y);
}
/* 
 * specialBits - return bit pattern 0xffca3fff
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 6
 *   Rating: 2
 *  
 *   This solution shifts by divisibles of 8 to shift
 */
int specialBits(void) {
    return (0xff << 24 |0xca << 16 |0x3f << 8 |0xff << 0 );
}
/* 
 * copyLSB - set all bits of result to least significant bit of x
 *   Example: copyLSB(5) = 0xFFFFFFFF, copyLSB(6) = 0x00000000
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 5
 *   Rating: 2
 *   
 *   Shift all bit 31 to the left anf then to the right. This returns the least significant bit by getting the last digit after shifting
 */
int copyLSB(int x) {
  return (x << 31) >> 31;
}
/* 
 * isNotEqual - return 0 is f x == y, and 1 otherwise 
 *   Examples: isNotEqual(5,5) = 0, isNotEqual(4,5) = 1
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 6
 *   Rating: 2
 *  
 *   x Xor y but banged twice! This returns the correct integer because withoit the bangs it may return a negative number instead
 */
int isNotEqual(int x, int y) {
  return !!(x ^ y);
}
/* 
 * conditional - same as x ? y : z (ternary operator)
 * this means: if x is true (nonzero), then y, else z
 *   Examples: conditional(2,4,5) = 4
 *             conditional(0,4,5) = 5
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 16
 *   Rating: 3
 *  
 *   help
 *
 */
int conditional(int x, int y, int z) {
  return (x & y) & (z & y);
}
/*******************************************
 * INTEGERS (8 puzzles, 17 points total)   *
 *******************************************/
/* 
 * minusOne - return a value of -1 
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 2
 *   Rating: 1
 *  
 *   The binary code for -1 is 1111 so the complement of 0000 returns -1
 */
int minusOne(void) {
  return ~0;
}
/* 
 * TMax - return maximum two's complement integer 
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 4
 *   Rating: 1
 *  
 *   this returns the max two's complement by shifting to the left turning 011111111 into 10000000 to get 127
 *
 */
int tmax(void) {
  return ~(1 << 31);
}
/* 
 * negate - return -x 
 *   Example: negate(1) = -1.
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 5
 *   Rating: 2
 *  
 *   this solution takes the complement of x and adds 1 to follow the two's complement rules. if you use ~ without adding 1 you will not 
 *   reach the same number.
 */
int negate(int x) {
  return ~x+1;
}
/* 
 * isNegative - return 1 if x < 0, return 0 otherwise 
 *   Example: isNegative(-1) = 1.
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 6
 *   Rating: 2
 *  
 *   this solution shifts everything to the left and complements and adds 1. the goal of this is to retrieve the first number which is either
 *   0 or 1, 1 meaning negative already.
 */
int isNegative(int x) {
  return ~(x >> 31) + 1;
}
/* 
 * isPositive - return 1 if x > 0, return 0 otherwise 
 *   Example: isPositive(-1) = 0.
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 8
 *   Rating: 3
 *  
 *   The idea of this was to do something similar to above but the opposite. The & has an extra case for if 0. This returns 0 for 0.
 *
 */
int isPositive(int x) {
  return !(~(x >> 31) + 1) & (!(!x));
}
/* 
 * sign - return 1 if positive, 0 if zero, and -1 if negative
 *  Examples: sign(130) = 1
 *            sign(-23) = -1
 *  Legal ops: ! ~ & ^ | + << >>
 *  Max ops: 10
 *  Rating: 2
 *  
 *   The solution for this is also similar to the two above. It just needs to return -1 for negative numbers instead. This was simpler because
 *   by shifting it would either have 0 or 1 at the front for -. the extra condition is for case 0.
 */
int sign(int x) {
    return (x >> 31) | !(!x);
}
/*
 * isTmin - returns 1 if x is the minimum, two's complement number,
 *     and 0 otherwise 
 *   Legal ops: ! ~ & ^ | +
 *   Max ops: 10
 *   Rating: 2
 *  
 *   This starts by comparing/Xor x and its complementary number. Then adding either 0 or 1 and banging that to recieve 0 or 1 and not
 *   the minimum number itself.
 */
int isTmin(int x) {
  return !(x ^ (~x+1) + !x);
}
/* 
 * bang - Compute !x without using !
 *   Examples: bang(3) = 0, bang(0) = 1
 *   Legal ops: ~ & ^ | + << >>
 *   Max ops: 12
 *   Rating: 4 
 *  
 *   This starts by comparing/Xor x and its complementary number then  shifting it right to get a series of 1's or 0's and then adding 1. This makes sense in my head I dont know how to explain
 *
 */
int bang(int x) {
  return ((x | (~x+1)) >> 31) + 1;
}
/*************************************************************
 * BONUS puzzles BELOW! be advised, some are quite difficult *
 ************************************************************/
/***************************************************
 INTEGERS and BOOLEAN (4 puzzles, up to 14 points) *
 ***************************************************/
/* 
 * byteSwap - swaps the nth byte and the mth byte
 *  Examples: byteSwap(0x12345678, 1, 3) = 0x56341278
 *            byteSwap(0xDEADBEEF, 0, 2) = 0xDEEFBEAD
 *  You may assume that 0 <= n <= 3, 0 <= m <= 3
 *  Legal ops: ! ~ & ^ | + << >>
 *  Max ops: 25
 *  Rating: 3
 */
int byteSwap(int x, int n, int m) {
    return 2;
}
/* 
 * logicalShift - shift x to the right by n, using a logical shift
 *   Can assume that 0 <= n <= 31
 *   Examples: logicalShift(0x87654321,4) = 0x08765432
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 20
 *   Rating: 3 
 */
int logicalShift(int x, int n) {
  return 2;
}
/*
 * bitParity - returns 1 if x contains an odd number of 0's
 *   Examples: bitParity(5) = 0, bitParity(7) = 1
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 20
 *   Rating: 4
 */
int bitParity(int x) {
  return 2;
}
/* 
 * addOK - Determine if we can compute x+y without overflow
 *   Example: addOK(0x80000000,0x80000000) = 0,
 *            addOK(0x80000000,0x70000000) = 1, 
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 20
 *   Rating: 4
 */
int addOK(int x, int y) {
  return 2;
}
