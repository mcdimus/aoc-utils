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

}
