package com.github.mcdimus.aoc.utils

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class IntRangeXTest {

  @Test
  fun `toIntRange with default delimiter`() {
    val range = "1-10".toIntRange()
    assertThat(range).isEqualTo(1..10)
  }

  @Test
  fun `toIntRange with custom delimiter`() {
    val range = "1:10".toIntRange(':')
    assertEquals(1..10, range)
  }

  @Test
  fun `toIntRange with invalid input`() {
    Assertions.assertThatThrownBy { "1-abc".toIntRange() }
      .isInstanceOf(RangeFormatException::class.java)
      .hasMessage("Invalid range format: 1-abc")
  }

  @Test
  fun `toIntRange with missing delimiter`() {
    Assertions.assertThatThrownBy { "110".toIntRange() }
      .isInstanceOf(RangeFormatException::class.java)
      .hasMessage("Invalid range format: 110")
  }

  @Test
  fun `toIntRange with extra delimiter`() {
    Assertions.assertThatThrownBy { "1-1-0".toIntRange() }
      .isInstanceOf(RangeFormatException::class.java)
      .hasMessage("Invalid range format: 1-1-0")
  }

  @Test
  fun `contains when range contains other range`() {
    val range = 1..10
    val other = 3..7
    assertThat(range.contains(other)).isTrue()
  }

  @Test
  fun `contains when range is equal to other range`() {
    val range = 1..10
    val other = 1..10
    assertThat(range.contains(other)).isTrue()
  }

  @Test
  fun `contains when range does not fully contain other range`() {
    val range = 5..15
    assertThat(range.contains(1..10)).isFalse()
    assertThat(range.contains(10..20)).isFalse()
  }

  @Test
  fun `contains when other range is after`() {
    val range = 1..10
    val other = 11..20
    assertThat(range.contains(other)).isFalse()
  }

  @Test
  fun `contains when other range is before`() {
    val range = 10..20
    val other = 1..9
    assertThat(range.contains(other)).isFalse()
  }

  @Test
  fun `overlaps when ranges overlap`() {
    val range = 5..15
    assertThat(range.overlaps(1..10)).isTrue()
    assertThat(range.overlaps(10..20)).isTrue()
  }

  @Test
  fun `overlaps when ranges do not overlap`() {
    val range = 5..10
    val other1 = 1..4
    val other2 = 11..20
    assertThat(range.overlaps(other1)).isFalse()
    assertThat(range.overlaps(other2)).isFalse()
  }

  @Test
  fun `overlaps when one range is within the other`() {
    val range = 1..10
    val other = 3..7
    assertThat(range.overlaps(other)).isTrue()
  }

  @Test
  fun `overlaps when ranges are equal`() {
    val range = 1..10
    val other = 1..10
    assertThat(range.overlaps(other)).isTrue()
  }

  @Test
  fun `overlaps when ranges touch at the boundary`() {
    val range = 5..10
    val other1 = 10..20
    val other2 = 1..5
    assertThat(range.overlaps(other1)).isTrue()
    assertThat(range.overlaps(other2)).isTrue()
  }

}
