package com.github.mcdimus.aoc.utils.range

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CompositeClosedLongRangeTest {

  @Test
  fun `should add a single range to an empty composite range`() {
    val compositeRange = CompositeClosedLongRange()
    assertThat(compositeRange.size()).isEqualTo(0)

    compositeRange.add(1L..10L)

    assertThat(compositeRange.ranges()).containsExactly(1L..10L)
    assertThat(compositeRange.size()).isEqualTo(10)
  }

  @Test
  fun `should add multiple non-overlapping ranges`() {
    val compositeRange = CompositeClosedLongRange()
    compositeRange.add(1L..10L)
    compositeRange.add(20L..30L)

    assertThat(compositeRange.ranges()).containsExactly(1L..10L, 20L..30L)
    assertThat(compositeRange.size()).isEqualTo(21)
  }

  @Test
  fun `should merge overlapping ranges into a single range`() {
    val compositeRange = CompositeClosedLongRange()
    compositeRange.add(1L..10L)
    compositeRange.add(5L..15L)

    assertThat(compositeRange.ranges()).containsExactly(1L..15L)
    assertThat(compositeRange.size()).isEqualTo(15)
  }

  @Test
  fun `should merge touching ranges into a single range`() {
    val compositeRange = CompositeClosedLongRange()
    compositeRange.add(1L..10L)
    compositeRange.add(11L..20L)

    assertThat(compositeRange.ranges()).containsExactly(1L..20L)
    assertThat(compositeRange.size()).isEqualTo(20)
  }

  @Test
  fun `should handle fully contained ranges without adding duplicates`() {
    val compositeRange = CompositeClosedLongRange()
    compositeRange.add(1L..20L)
    compositeRange.add(5L..15L)

    assertThat(compositeRange.ranges()).containsExactly(1L..20L)
    assertThat(compositeRange.size()).isEqualTo(20)
  }

  @Test
  fun `should merge multiple overlapping ranges into one`() {
    val compositeRange = CompositeClosedLongRange()
    compositeRange.add(1L..10L)
    compositeRange.add(5L..15L)
    compositeRange.add(12L..20L)

    assertThat(compositeRange.ranges()).containsExactly(1L..20L)
    assertThat(compositeRange.size()).isEqualTo(20)
  }

  @Test
  fun `should add non-overlapping ranges at the correct position`() {
    val compositeRange = CompositeClosedLongRange()
    compositeRange.add(20L..30L)
    compositeRange.add(1L..10L)

    assertThat(compositeRange.ranges()).containsExactly(20L..30L, 1L..10L)
    assertThat(compositeRange.size()).isEqualTo(21)
  }

  @Test
  fun `should handle negative ranges correctly`() {
    val compositeRange = CompositeClosedLongRange()
    compositeRange.add(-10L..-1L)
    compositeRange.add(-20L..-15L)

    assertThat(compositeRange.ranges()).containsExactly(-10L..-1L, -20L..-15L)
    assertThat(compositeRange.size()).isEqualTo(16)
  }

  @Test
  fun `should merge overlapping negative ranges`() {
    val compositeRange = CompositeClosedLongRange()
    compositeRange.add(-10L..-1L)
    compositeRange.add(-5L..5L)

    assertThat(compositeRange.ranges()).containsExactly(-10L..5L)
    assertThat(compositeRange.size()).isEqualTo(16)
  }

  @Test
  fun `should handle very large ranges without overflow`() {
    val compositeRange = CompositeClosedLongRange()
    compositeRange.add(Long.MIN_VALUE..Long.MAX_VALUE)

    assertThat(compositeRange.ranges()).containsExactly(Long.MIN_VALUE..Long.MAX_VALUE)
  }

  @Test
  fun `should add a range that does not overlap with a very large range`() {
    val compositeRange = CompositeClosedLongRange()
    compositeRange.add(Long.MIN_VALUE..0L)
    compositeRange.add(1L..Long.MAX_VALUE)

    assertThat(compositeRange.ranges()).containsExactly(Long.MIN_VALUE..Long.MAX_VALUE)
  }

  @Test
  fun `should merge ranges that overlap with a very large range`() {
    val compositeRange = CompositeClosedLongRange()
    compositeRange.add(Long.MIN_VALUE..0L)
    compositeRange.add(-5L..5L)

    assertThat(compositeRange.ranges()).containsExactly(Long.MIN_VALUE..5L)
  }

  @Test
  fun `should merge multiple ranges`() {
    val compositeRange = CompositeClosedLongRange()
    compositeRange.add(3L..5L)
    compositeRange.add(10L..14L)
    compositeRange.add(16L..20L)
    compositeRange.add(12L..18L)
    compositeRange.add(21L..33L)

    assertThat(compositeRange.ranges()).containsExactly(3L..5L, 10L..33L)
    assertThat(compositeRange.size()).isEqualTo(27)
  }

}
