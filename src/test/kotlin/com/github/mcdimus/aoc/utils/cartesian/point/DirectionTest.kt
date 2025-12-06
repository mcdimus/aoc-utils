package com.github.mcdimus.aoc.utils.cartesian.point

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DirectionTest {

  @Test
  fun `should return all diagonal directions`() {
    val diagonals = Direction.entriesDiagonals

    assertThat(diagonals).containsExactlyInAnyOrder(
      Direction.UP_LEFT,
      Direction.UP_RIGHT,
      Direction.DOWN_LEFT,
      Direction.DOWN_RIGHT
    )
  }

  @Test
  fun `should return all straight directions`() {
    val straights = Direction.entriesStraight

    assertThat(straights).containsExactlyInAnyOrder(
      Direction.UP,
      Direction.LEFT,
      Direction.RIGHT,
      Direction.DOWN
    )
  }

  @Nested
  inner class StraightOfTests {

    @Test
    fun `should return correct straight direction for single character`() {
      assertThat(Direction.straightOf('U')).isEqualTo(Direction.UP)
      assertThat(Direction.straightOf('L')).isEqualTo(Direction.LEFT)
      assertThat(Direction.straightOf('R')).isEqualTo(Direction.RIGHT)
      assertThat(Direction.straightOf('D')).isEqualTo(Direction.DOWN)
    }

    @Test
    fun `should return correct straight direction for full name`() {
      assertThat(Direction.straightOf("UP")).isEqualTo(Direction.UP)
      assertThat(Direction.straightOf("LEFT")).isEqualTo(Direction.LEFT)
      assertThat(Direction.straightOf("RIGHT")).isEqualTo(Direction.RIGHT)
      assertThat(Direction.straightOf("DOWN")).isEqualTo(Direction.DOWN)
    }

    @Test
    fun `should throw exception for invalid single character`() {
      assertThatThrownBy { Direction.straightOf('Z') }
        .isInstanceOf(NoSuchElementException::class.java)
        .hasMessageContaining("Collection contains no element matching the predicate.")
    }

    @Test
    fun `should throw exception for invalid string`() {
      assertThatThrownBy { Direction.straightOf("INVALID") }
        .isInstanceOf(NoSuchElementException::class.java)
        .hasMessageContaining("Collection contains no element matching the predicate.")
    }

  }

}
