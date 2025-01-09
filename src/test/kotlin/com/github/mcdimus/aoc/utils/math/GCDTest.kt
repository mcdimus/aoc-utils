package com.github.mcdimus.aoc.utils.math

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

internal class GCDTest {

  private class GCDProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
      return Stream.of(
        Arguments.of(EuclideanGCD),
        Arguments.of(BinaryGCD),
      )
    }
  }

  @ParameterizedTest
  @ArgumentsSource(GCDProvider::class)
  fun `gcd of one number`(gcd: GCD) {
    assertThat(gcd.gcd(Int.MIN_VALUE)).isEqualTo(Int.MIN_VALUE)
    assertThat(gcd.gcd(-1)).isEqualTo(-1)
    assertThat(gcd.gcd(0)).isEqualTo(0)
    assertThat(gcd.gcd(12)).isEqualTo(12)
    assertThat(gcd.gcd(12423423)).isEqualTo(12423423)
    assertThat(gcd.gcd(Int.MAX_VALUE)).isEqualTo(Int.MAX_VALUE)

    assertThat(gcd.gcd(Long.MIN_VALUE)).isEqualTo(Long.MIN_VALUE)
    assertThat(gcd.gcd(-1L)).isEqualTo(-1L)
    assertThat(gcd.gcd(0L)).isEqualTo(0L)
    assertThat(gcd.gcd(12L)).isEqualTo(12L)
    assertThat(gcd.gcd(12423423L)).isEqualTo(12423423L)
    assertThat(gcd.gcd(Long.MAX_VALUE)).isEqualTo(Long.MAX_VALUE)
  }

  @ParameterizedTest
  @ArgumentsSource(GCDProvider::class)
  fun `gcd of number and zero`(gcd: GCD) {
    assertThat(gcd.gcd(12, 0)).isEqualTo(12)
    assertThat(gcd.gcd(0, 18)).isEqualTo(18)
    assertThat(gcd.gcd(-12, 0)).isEqualTo(12)
    assertThat(gcd.gcd(0, -18)).isEqualTo(18)
    assertThat(gcd.gcd(0, 0)).isEqualTo(0)

    assertThat(gcd.gcd(12L, 0L)).isEqualTo(12L)
    assertThat(gcd.gcd(0L, 18L)).isEqualTo(18L)
    assertThat(gcd.gcd(-12L, 0L)).isEqualTo(12L)
    assertThat(gcd.gcd(0L, -18L)).isEqualTo(18L)
    assertThat(gcd.gcd(0L, 0L)).isEqualTo(0L)
  }

  @ParameterizedTest
  @ArgumentsSource(GCDProvider::class)
  fun `gcd of two positive numbers`(gcd: GCD) {
    assertThat(gcd.gcd(12, 18)).isEqualTo(6)
    assertThat(gcd.gcd(48, 180)).isEqualTo(12)

    assertThat(gcd.gcd(12L, 18L)).isEqualTo(6L)
    assertThat(gcd.gcd(48L, 180L)).isEqualTo(12L)
  }

  @ParameterizedTest
  @ArgumentsSource(GCDProvider::class)
  fun `gcd of two negative numbers`(gcd: GCD) {
    assertThat(gcd.gcd(-12, -18)).isEqualTo(6)
    assertThat(gcd.gcd(-48, -180)).isEqualTo(12)

    assertThat(gcd.gcd(-12L, -18L)).isEqualTo(6L)
    assertThat(gcd.gcd(-48L, -180L)).isEqualTo(12L)
  }

  @ParameterizedTest
  @ArgumentsSource(GCDProvider::class)
  fun `gcd of three positive numbers`(gcd: GCD) {
    assertThat(gcd.gcd(12, 18, 30)).isEqualTo(6)
    assertThat(gcd.gcd(10, 15, 25)).isEqualTo(5)
    assertThat(gcd.gcd(21, 763, 28)).isEqualTo(7)

    assertThat(gcd.gcd(12L, 18L, 30L)).isEqualTo(6L)
    assertThat(gcd.gcd(10L, 15L, 25L)).isEqualTo(5L)
    assertThat(gcd.gcd(21L, 763L, 28L)).isEqualTo(7L)
  }

  @ParameterizedTest
  @ArgumentsSource(GCDProvider::class)
  fun `gcd of negative and positive numbers`(gcd: GCD) {
    assertThat(gcd.gcd(-12, 18)).isEqualTo(6)
    assertThat(gcd.gcd(12, -18)).isEqualTo(6)

    assertThat(gcd.gcd(-12L, 18L)).isEqualTo(6L)
    assertThat(gcd.gcd(12L, -18L)).isEqualTo(6L)
  }

  @ParameterizedTest
  @ArgumentsSource(GCDProvider::class)
  fun `gcd of large numbers`(gcd: GCD) {
    assertThat(gcd.gcd(1234567890L, 9876543210L)).isEqualTo(90L)
    assertThat(gcd.gcd(2147483640L, 9223372036854775800L)).isEqualTo(120L)
  }

  @ParameterizedTest
  @ArgumentsSource(GCDProvider::class)
  fun `gcd of prime numbers`(gcd: GCD) {
    assertThat(gcd.gcd(12, 1)).isEqualTo(1)
    assertThat(gcd.gcd(1, 18)).isEqualTo(1)
    assertThat(gcd.gcd(7, 11)).isEqualTo(1)
    assertThat(gcd.gcd(13, 17, 19)).isEqualTo(1)
    assertThat(gcd.gcd(13, 2, 10, 12312312)).isEqualTo(1)

    assertThat(gcd.gcd(12L, 1L)).isEqualTo(1L)
    assertThat(gcd.gcd(1L, 18L)).isEqualTo(1L)
    assertThat(gcd.gcd(7L, 11L)).isEqualTo(1L)
    assertThat(gcd.gcd(13L, 17L, 19L)).isEqualTo(1L)
    assertThat(gcd.gcd(13L, 2L, 10L, 12312312L)).isEqualTo(1L)
  }

}
