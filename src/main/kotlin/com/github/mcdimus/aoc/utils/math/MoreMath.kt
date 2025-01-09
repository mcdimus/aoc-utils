package com.github.mcdimus.aoc.utils.math

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
  fun gcd(longs: LongArray) = gcd.gcd(*longs)

  /**
   * Returns the greatest common divisor (GCD) of the given numbers.
   *
   * @param ints The numbers.
   * @return The greatest common divisor of the given numbers.
   */
  fun gcd(vararg ints: Int) = gcd.gcd(*ints)

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

}
