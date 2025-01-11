package com.github.mcdimus.aoc.utils.math

import kotlin.math.abs

/**
 * The Euclidean GCD Algorithm.
 */
internal data object EuclideanGCD : GCD {

  override fun gcd(a: Long, b: Long): Long {
    var n1 = abs(a)
    var n2 = abs(b)

    if (n1 == 0L) return n2
    if (n2 == 0L) return n1
    if (n1 == 1L || n2 == 1L) return 1L

    while (n2 != 0L) {
      val temp = n2
      n2 = n1 % n2
      n1 = temp
    }
    return n1
  }

}
