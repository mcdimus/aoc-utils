package com.github.mcdimus.aoc.utils.math

import kotlin.math.abs

/**
 * The Binary GCD Algorithm (also known as Stein's algorithm).
 *
 * Algorithm is generally considered faster than the Euclidean algorithm, especially for large numbers.
 *
 * @see EuclideanGCD
 */
internal data object BinaryGCD : GCD {

  override fun gcd(a: Long, b: Long): Long {
    var n1 = abs(a)
    var n2 = abs(b)

    if (n1 == 0L) return n2
    if (n2 == 0L) return n1
    if (n1 == 1L || n2 == 1L) return 1L

    // Count trailing zeros
    var shift = 0
    while ((n1 and 1) == 0L && (n2 and 1) == 0L) {
      shift++
      n1 /= 2
      n2 /= 2
    }

    // Ensure n1 is odd
    while ((n1 and 1) == 0L) {
      n1 /= 2
    }

    // From here on, 'n1' is odd
    do {
      // Ensure 'n2' is odd
      while ((n2 and 1) == 0L) {
        n2 /= 2
      }

      // Swap if necessary so 'n1' is less than or equal to 'n2'
      if (n1 > n2) {
        val temp = n2
        n2 = n1
        n1 = temp
      }

      // Subtract 'n1' from 'n2'
      n2 -= n1
    } while (n2 != 0L)

    // Restore common factors of 2
    return n1 shl shift
  }

}
