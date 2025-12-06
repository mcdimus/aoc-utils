package com.github.mcdimus.aoc.utils.range

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ClosedRangeXTest {

  @Nested
  inner class ToIntRangeTests {

    @Test
    fun `should parse valid range string`() {
      val range = "1-10".toIntRange()
      assertThat(range).isEqualTo(1..10)
    }

    @Test
    fun `should handle single-character delimiter`() {
      val range = "5:15".toIntRange(delimiter = ':')
      assertThat(range).isEqualTo(5..15)
    }

    @Test
    fun `should throw exception for invalid range format`() {
      assertThatThrownBy { "5".toIntRange() }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("invalid range format: 5")
        .hasRootCauseMessage("expected 2 elements but got 1: [5]")
    }

    @Test
    fun `should throw exception for non-numeric values`() {
      assertThatThrownBy { "a-b".toIntRange() }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("invalid range format: a-b")
        .hasRootCauseMessage("For input string: \"a\"")
    }

    @Test
    fun `should throw exception for negative values with default delimiter`() {
      assertThatThrownBy { "-5-10".toIntRange() }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("invalid range format: -5-10")
        .hasRootCauseMessage("expected 2 elements but got 3: [, 5, 10]")
    }

    @Test
    fun `should throw exception for invalid order of range elements`() {
      assertThatThrownBy { "10-5".toIntRange() }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("invalid range format: 10-5")
        .hasRootCauseMessage("expected 10 <= 5")
    }

    @Test
    fun `should handle negative numbers`() {
      val range = "-5:-1".toIntRange(delimiter = ':')
      assertThat(range).isEqualTo(-5..-1)
    }

    @Test
    fun `should handle boundary values`() {
      val range = "0-${Int.MAX_VALUE}".toIntRange()
      assertThat(range).isEqualTo(0..Int.MAX_VALUE)
    }

    @Test
    fun `should handle zero length`() {
      val range = "1-1".toIntRange()
      assertThat(range).isEqualTo(1..1)
    }
  }

  @Nested
  inner class ToLongRangeTests {

    @Test
    fun `should parse valid range string`() {
      val range = "1000000000-2000000000".toLongRange()
      assertThat(range).isEqualTo(1_000_000_000L..2_000_000_000L)
    }

    @Test
    fun `should handle single-character delimiter`() {
      val range = "5000000000:15000000000".toLongRange(delimiter = ':')
      assertThat(range).isEqualTo(5_000_000_000L..15_000_000_000L)
    }

    @Test
    fun `should throw exception for invalid range format`() {
      assertThatThrownBy { "1000000000".toLongRange() }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("invalid range format: 1000000000")
        .hasRootCauseMessage("expected 2 elements but got 1: [1000000000]")
    }

    @Test
    fun `should throw exception for non-numeric values`() {
      assertThatThrownBy { "a-b".toLongRange() }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("invalid range format: a-b")
        .hasRootCauseMessage("For input string: \"a\"")
    }

    @Test
    fun `should throw exception for negative values with default delimiter`() {
      assertThatThrownBy { "-1000000000-2000000000".toIntRange() }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("invalid range format: -1000000000-2000000000")
        .hasRootCauseMessage("expected 2 elements but got 3: [, 1000000000, 2000000000]")
    }

    @Test
    fun `should throw exception for invalid order of range elements`() {
      assertThatThrownBy { "2000000000-1000000000".toLongRange() }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("invalid range format: 2000000000-1000000000")
        .hasRootCauseMessage("expected 2000000000 <= 1000000000")
    }

    @Test
    fun `should handle negative numbers`() {
      val range = "-5000000000:-1000000000".toLongRange(delimiter = ':')
      assertThat(range).isEqualTo(-5_000_000_000L..-1_000_000_000L)
    }

    @Test
    fun `should handle boundary values`() {
      val range = "0-${Long.MAX_VALUE}".toLongRange()
      assertThat(range).isEqualTo(0..Long.MAX_VALUE)
    }

    @Test
    fun `should handle zero length`() {
      val range = "5000000000-5000000000".toLongRange()
      assertThat(range).isEqualTo(5000000000L..5000000000L)
    }
  }

  @Nested
  inner class OverlapsTests {

    @Test
    fun `should return true for overlapping ranges`() {
      val range1 = 1..10
      val range2 = 5..15
      assertThat(range1.overlaps(range2)).isTrue
      assertThat(range2.overlaps(range1)).isTrue
    }

    @Test
    fun `should return false for non-overlapping ranges`() {
      val range1 = 1..10
      val range2 = 15..20
      assertThat(range1.overlaps(range2)).isFalse
      assertThat(range2.overlaps(range1)).isFalse
    }

    @Test
    fun `should return false for touching ranges`() {
      val range1 = 1..10
      val range2 = 11..20
      assertThat(range1.overlaps(range2)).isFalse
    }

    @Test
    fun `should handle boundary values`() {
      val range1 = Int.MIN_VALUE..0
      val range2 = 0..Int.MAX_VALUE
      assertThat(range1.overlaps(range2)).isTrue
    }

    @Test
    fun `should handle negative values`() {
      val range1 = -10..-5
      val range2 = -6..-1
      assertThat(range1.overlaps(range2)).isTrue
    }
  }

  @Nested
  inner class ContainsTests {

    @Test
    fun `should return true for fully contained range`() {
      val range1 = 1..10
      val range2 = 3..8
      assertThat(range1.contains(range2)).isTrue
    }

    @Test
    fun `should return false for partially contained range`() {
      val range1 = 1..10
      val range2 = 5..15
      val range3 = 15..115
      assertThat(range1.contains(range2)).isFalse
      assertThat(range2.contains(range3)).isFalse
    }

    @Test
    fun `should return false for non-overlapping range`() {
      val range1 = 1..10
      val range2 = 15..20
      assertThat(range1.contains(range2)).isFalse
    }

    @Test
    fun `should handle boundary values`() {
      val range1 = Int.MIN_VALUE..Int.MAX_VALUE
      val range2 = Int.MIN_VALUE..Int.MAX_VALUE
      assertThat(range1.contains(range2)).isTrue
    }

    @Test
    fun `should handle zero size`() {
      val range1 = 1..1
      val range2 = 1..1
      assertThat(range1.contains(range2)).isTrue
    }
  }

  @Nested
  inner class TouchesTests {

    @Test
    fun `should return true for touching ranges`() {
      assertThat((1..10).touches(11..20)).isTrue
      assertThat((11..20).touches(1..10)).isTrue
      assertThat((1L..10L).touches(11L..20L)).isTrue
      assertThat((11L..20L).touches(1L..10L)).isTrue
    }

    @Test
    fun `should return false for non-touching ranges`() {
      assertThat((1..10).touches(12..20)).isFalse
      assertThat((12..20).touches(1..10)).isFalse
      assertThat((1L..10L).touches(12L..20L)).isFalse
      assertThat((12L..20L).touches(1L..10L)).isFalse
    }

    @Test
    fun `should handle boundary values for IntRange`() {
      val range1 = Int.MIN_VALUE..-1
      val range2 = 0..Int.MAX_VALUE
      assertThat(range1.touches(range2)).isTrue
    }

    @Test
    fun `should handle boundary values for LongRange`() {
      val range1 = Long.MIN_VALUE..-1
      val range2 = 0..Long.MAX_VALUE
      assertThat(range1.touches(range2)).isTrue
    }
  }

  @Nested
  inner class MergeTests {

    @Test
    fun `should combine overlapping ranges`() {
      assertThat((1..10).merge(5..15)).isEqualTo(1..15)
      assertThat((1L..10L).merge(5L..15L)).isEqualTo(1L..15L)
    }

    @Test
    fun `should combine touching ranges`() {
      assertThat((1..10).merge(11..15)).isEqualTo(1..15)
      assertThat((1L..10L).merge(11L..15L)).isEqualTo(1L..15L)
    }

    @Test
    fun `should throw exception for non-overlapping ranges`() {
      assertThatThrownBy { (1..10).merge(15..20) }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("cannot merge non-overlapping and non-touching ranges")
      assertThatThrownBy { (1L..10L).merge(15L..20L) }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("cannot merge non-overlapping and non-touching ranges")
    }

    @Test
    fun `should handle boundary values`() {
      val range1 = Int.MIN_VALUE..0
      val range2 = 0..Int.MAX_VALUE
      assertThat(range1.merge(range2)).isEqualTo(Int.MIN_VALUE..Int.MAX_VALUE)
    }
  }

  @Nested
  inner class SizeTests {

    @Test
    fun `should return correct size`() {
      assertThat((1..10).size()).isEqualTo(10)
      assertThat((1L..10L).size()).isEqualTo(10L)
    }

    @Test
    fun `should return correct size for negative numbers`() {
      assertThat((-10..-1).size()).isEqualTo(10)
      assertThat((-10L..-1L).size()).isEqualTo(10L)
    }

    @Test
    fun `should throw exception for boundary values for IntRange`() {
      assertThatThrownBy { (0..Int.MAX_VALUE).size() }.hasMessage("integer overflow")
      assertThatThrownBy { (Int.MIN_VALUE..0).size() }.hasMessage("integer overflow")
      assertThatThrownBy { (Int.MIN_VALUE..Int.MAX_VALUE).size() }.hasMessage("integer overflow")
      assertThatThrownBy { (Int.MAX_VALUE..Int.MIN_VALUE).size() }.hasMessage("integer overflow")
    }

    @Test
    fun `should throw exception for boundary values for LongRange`() {
      assertThatThrownBy { (0L..Long.MAX_VALUE).size() }.hasMessage("long overflow")
      assertThatThrownBy { (Long.MIN_VALUE..0L).size() }.hasMessage("long overflow")
      assertThatThrownBy { (Long.MIN_VALUE..Long.MAX_VALUE).size() }.hasMessage("long overflow")
      assertThatThrownBy { (Long.MAX_VALUE..Long.MIN_VALUE).size() }.hasMessage("long overflow")
    }
  }

}
