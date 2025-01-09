package com.github.mcdimus.aoc.utils.math

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MoreMathTest {

  @Test
  fun gcd() {
    assertThat(MoreMath.gcd(12, 18)).isEqualTo(6)
    assertThat(MoreMath.gcd(12L, 18L)).isEqualTo(6)
    assertThat(MoreMath.gcd(intArrayOf(12, 18, 60))).isEqualTo(6)
    assertThat(MoreMath.gcd(longArrayOf(12L, 18L, 60L))).isEqualTo(6)
  }

  @Test
  fun primeFactorsOfPrimeNumber() {
    assertThat(MoreMath.primeFactorsOf(13L)).containsExactly(13L)
  }

  @Test
  fun primeFactorsOfCompositeNumber() {
    assertThat(MoreMath.primeFactorsOf(60L)).containsExactly(2L, 2L, 3L, 5L)
    assertThat(MoreMath.primeFactorsOf(54235234562L)).containsExactly(2L, 89L, 587L, 519067L)
  }

  @Test
  fun primeFactorsOfOne() {
    assertThat(MoreMath.primeFactorsOf(1L)).isEmpty()
  }

  @Test
  fun primeFactorsOfZero() {
    assertThat(MoreMath.primeFactorsOf(0L)).isEmpty()
  }

  @Test
  fun primeFactorsOfNegativeNumber() {
    assertThat(MoreMath.primeFactorsOf(-60L)).isEmpty()
  }

}
