package com.github.mcdimus.aoc.utils.math

import kotlin.math.sqrt

/**
 * Provides a collection of utility methods for advanced mathematical operations.
 */
object MoreMath {

  /**
   * The greatest common divisor (GCD) algorithm to use.
   */
  private val gcd: GCD = BinaryGCD

  /**
   * Returns the greatest common divisor (GCD) of the given numbers.
   *
   * @param longs The numbers.
   * @return The greatest common divisor of the given numbers.
   */
  fun gcd(longs: LongArray) = gcd.gcd(longs)

  /**
   * Returns the greatest common divisor (GCD) of the given numbers.
   *
   * @param ints The numbers.
   * @return The greatest common divisor of the given numbers.
   */
  fun gcd(ints: IntArray) = gcd.gcd(ints)

  /**
   * Returns the greatest common divisor (GCD) of the given numbers.
   *
   * @param a The first number.
   * @param b The second number.
   * @return The greatest common divisor of the given numbers.
   */
  fun gcd(a: Int, b: Int) = gcd.gcd(a, b)

  /**
   * Returns the greatest common divisor (GCD) of the given numbers.
   *
   * @param a The first number.
   * @param b The second number.
   * @return The greatest common divisor of the given numbers.
   */
  fun gcd(a: Long, b: Long) = gcd.gcd(a, b)

  /**
   * Returns the prime factors of the given number.
   *
   * @param number The number to factorize.
   * @return A prime factors list of the given number.
   * If the number is less than or equal to zero, an empty list is returned.
   */
  fun primeFactorsOf(number: Long): List<Long> {
    if (number <= 0) {
      return emptyList()
    }
    return buildList {
      var n = number
      while (n % 2 == 0L) {
        add(2L)
        n /= 2
      }

      val squareRoot = sqrt(n.toDouble()).toLong()
      for (i in 3..squareRoot step 2) {
        while (n % i == 0L) {
          add(i)
          n /= i
        }
      }
      if (n > 2) {
        add(n)
      }
    }
  }

}
