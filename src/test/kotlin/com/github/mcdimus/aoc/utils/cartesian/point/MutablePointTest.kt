package com.github.mcdimus.aoc.utils.cartesian.point

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MutablePointTest {

  @Test
  fun `should allow modifying x and y coordinates`() {
    val point = MutablePoint.of(3, 4)

    point.x = 10
    point.y = 20

    assertThat(point.x).isEqualTo(10)
    assertThat(point.y).isEqualTo(20)
  }

  @Test
  fun `should destructure correctly`() {
    val (x, y) = MutablePoint.of(11, 22)

    assertThat(x).isEqualTo(11)
    assertThat(y).isEqualTo(22)
    assertThat(MutablePoint.of(11, 22).component1()).isEqualTo(11)
    assertThat(MutablePoint.of(11, 22).component2()).isEqualTo(22)
  }

  @Nested
  inner class DistanceToTests {

    @Test
    fun `should calculate Euclidean distance correctly`() {
      val point1 = MutablePoint.of(0, 0)
      val point2 = MutablePoint.of(3, 4)

      val distance = point1 distanceTo point2

      assertThat(distance).isEqualTo(5.0) // sqrt(3^2 + 4^2) = 5
    }

    @Test
    fun `should calculate Euclidean distance for negative coordinates`() {
      val point1 = MutablePoint.of(-1, -1)
      val point2 = MutablePoint.of(-4, -5)

      val distance = point1 distanceTo point2

      assertThat(distance).isEqualTo(5.0) // sqrt((-4 - -1)^2 + (-5 - -1)^2) = 5
    }

    @Test
    fun `should calculate zero distance for the same point`() {
      val point1 = MutablePoint.of(5, 5)

      val distance = point1 distanceTo point1

      assertThat(distance).isEqualTo(0.0)
    }
  }

  @Nested
  inner class ManhattanDistanceToTests {

    @Test
    fun `should calculate Manhattan distance correctly`() {
      val point1 = MutablePoint.of(0, 0)
      val point2 = MutablePoint.of(3, 4)

      val distance = point1 manhanttanDistanceTo point2

      assertThat(distance).isEqualTo(7) // |3 - 0| + |4 - 0| = 7
    }

    @Test
    fun `should calculate Manhattan distance for negative coordinates`() {
      val point1 = MutablePoint.of(-1, -1)
      val point2 = MutablePoint.of(-4, -5)

      val distance = point1 manhanttanDistanceTo point2

      assertThat(distance).isEqualTo(7) // |-4 - -1| + |-5 - -1| = 7
    }

    @Test
    fun `should calculate zero Manhattan distance for the same point`() {
      val point1 = MutablePoint.of(5, 5)

      val distance = point1 manhanttanDistanceTo point1

      assertThat(distance).isEqualTo(0)
    }
  }

  @Nested
  inner class GetAllPointsWithinManhattanDistanceTests {

    @Test
    fun `should return zero points within Manhattan distance 0`() {
      val point = MutablePoint.of(0, 0)

      val points = point.getAllPointsWithinManhattanDistance(0)

      assertThat(points).isEmpty()
    }

    @Test
    fun `should return all points within Manhattan distance 1`() {
      val point = MutablePoint.of(0, 0)

      val points = point.getAllPointsWithinManhattanDistance(1)

      assertThat(points).containsExactlyInAnyOrder(
        Point.of(0, 0),
        Point.of(1, 0),
        Point.of(0, 1),
        Point.of(-1, 0),
        Point.of(0, -1)
      )
    }

    @Test
    fun `should return all points within Manhattan distance 2`() {
      val point = MutablePoint.of(0, 0)

      val points = point.getAllPointsWithinManhattanDistance(2)

      assertThat(points).containsExactlyInAnyOrder(
        Point.of(0, 0),
        Point.of(1, 0), Point.of(0, 1), Point.of(-1, 0), Point.of(0, -1),
        Point.of(2, 0), Point.of(0, 2), Point.of(-2, 0), Point.of(0, -2),
        Point.of(1, 1), Point.of(1, -1), Point.of(-1, 1), Point.of(-1, -1)
      )
    }

    @Test
    fun `should return all points within Manhattan distance for a non-origin point`() {
      val point = MutablePoint.of(2, 3)

      val points = point.getAllPointsWithinManhattanDistance(1)

      assertThat(points).containsExactlyInAnyOrder(
        Point.of(2, 3),
        Point.of(3, 3),
        Point.of(2, 4),
        Point.of(1, 3),
        Point.of(2, 2)
      )
    }

    @Test
    fun `should return only unique points within Manhattan distance`() {
      val point = MutablePoint.of(0, 0)

      val points = point.getAllPointsWithinManhattanDistance(3)

      // Ensure no duplicates
      assertThat(points).doesNotHaveDuplicates()
    }
  }

  @Nested
  inner class MoveTests {

    @Test
    fun `should move the point by deltaX and deltaY`() {
      assertThat(MutablePoint.of(1, 1).move(2, 3)).isEqualTo(MutablePoint.of(3, 4))
    }

    @Test
    fun `should move the point in a specific direction`() {
      assertThat(MutablePoint.of(1, 1).move(Direction.UP)).isEqualTo(MutablePoint.of(1, 0))
      assertThat(MutablePoint.of(1, 1).move(Direction.DOWN)).isEqualTo(MutablePoint.of(1, 2))
      assertThat(MutablePoint.of(1, 1).move(Direction.LEFT)).isEqualTo(MutablePoint.of(0, 1))
      assertThat(MutablePoint.of(1, 1).move(Direction.RIGHT)).isEqualTo(MutablePoint.of(2, 1))
      assertThat(MutablePoint.of(1, 1).move(Direction.UP_LEFT)).isEqualTo(MutablePoint.of(0, 0))
      assertThat(MutablePoint.of(1, 1).move(Direction.UP_RIGHT)).isEqualTo(MutablePoint.of(2, 0))
      assertThat(MutablePoint.of(1, 1).move(Direction.DOWN_LEFT)).isEqualTo(MutablePoint.of(0, 2))
      assertThat(MutablePoint.of(1, 1).move(Direction.DOWN_RIGHT)).isEqualTo(MutablePoint.of(2, 2))
    }

    @Test
    fun `should not modify the original point when moved`() {
      val point = MutablePoint.of(3, 4)

      val movedPoint = point.move(2, 3)

      assertThat(point.x).isEqualTo(3)
      assertThat(point.y).isEqualTo(4)
      assertThat(movedPoint.x).isEqualTo(5)
      assertThat(movedPoint.y).isEqualTo(7)
    }

  }

  @Nested
  inner class FactoryTests {

    @Test
    fun `should create a point from x and y`() {
      val point = MutablePoint.of(3, 4)

      assertThat(point.x).isEqualTo(3)
      assertThat(point.y).isEqualTo(4)
    }

    @Test
    fun `should create a point from a string representation`() {
      val point = MutablePoint.of("3,4")

      assertThat(point.x).isEqualTo(3)
      assertThat(point.y).isEqualTo(4)
    }

    @Test
    fun `should throw exception for invalid string format`() {
      val exception = org.junit.jupiter.api.assertThrows<NumberFormatException> {
        MutablePoint.of("invalid,4")
      }

      assertThat(exception).hasMessageContaining("For input string: \"invalid\"")
    }
  }

}
