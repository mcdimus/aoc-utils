package com.github.mcdimus.aoc.utils.math

internal interface GCD {

  fun gcd(longs: LongArray): Long {
    var result = longs[0]
    for (i in 1 until longs.size) {
      result = this.gcd(result, longs[i])
    }
    return result
  }

  fun gcd(ints: IntArray): Int {
    var result = ints[0]
    for (i in 1 until ints.size) {
      result = this.gcd(result, ints[i])
    }
    return result
  }

  /**
   * Returns the greatest common divisor (GCD) of the given numbers.
   *
   * @param a The first number.
   * @param b The second number.
   * @return The greatest common divisor of the given numbers.
   */
  fun gcd(a: Int, b: Int): Int {
    return gcd(a.toLong(), b.toLong()).toInt()
  }

  /**
   * Returns the greatest common divisor (GCD) of the given numbers.
   *
   * @param a The first number.
   * @param b The second number.
   * @return The greatest common divisor of the given numbers.
   */
  fun gcd(a: Long, b: Long): Long

}
