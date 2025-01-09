package com.github.mcdimus.aoc.utils.math

import kotlin.math.abs

/**
 * The Euclidean GCD Algorithm.
 */
internal object EuclideanGCD : GCD {

  override fun gcd(a: Long, b: Long): Long {
    var n1 = abs(a)
    var n2 = abs(b)
    while (n2 != 0L) {
      val temp = n2
      n2 = n1 % n2
      n1 = temp
    }
    return n1
  }

  override fun toString(): String = this.javaClass.simpleName

}
