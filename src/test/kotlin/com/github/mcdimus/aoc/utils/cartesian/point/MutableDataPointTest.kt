package com.github.mcdimus.aoc.utils.cartesian.point

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MutableDataPointTest {

  @Test
  fun `should allow modifying x and y coordinates`() {
    val point = MutableDataPoint.of(3, 4, "data")

    point.x = 10
    point.y = 20
    point.data = "changed"

    assertThat(point.x).isEqualTo(10)
    assertThat(point.y).isEqualTo(20)
    assertThat(point.data).isEqualTo("changed")
  }

  @Test
  fun `should destructure correctly`() {
    val (x, y, data) = MutableDataPoint.of(11, 22, "data")

    assertThat(x).isEqualTo(11)
    assertThat(y).isEqualTo(22)
    assertThat(data).isEqualTo("data")
    assertThat(MutableDataPoint.of(11, 22, "data").component1()).isEqualTo(11)
    assertThat(MutableDataPoint.of(11, 22, "data").component2()).isEqualTo(22)
    assertThat(MutableDataPoint.of(11, 22, "data").component3()).isEqualTo("data")
  }

  @Nested
  inner class DistanceToTests {

    @Test
    fun `should calculate Euclidean distance correctly`() {
      val point1 = MutableDataPoint.of(0, 0, "data")
      val point2 = MutableDataPoint.of(3, 4, "data")

      val distance = point1 distanceTo point2

      assertThat(distance).isEqualTo(5.0) // sqrt(3^2 + 4^2) = 5
    }

    @Test
    fun `should calculate Euclidean distance for negative coordinates`() {
      val point1 = MutableDataPoint.of(-1, -1, "data")
      val point2 = MutableDataPoint.of(-4, -5, "data")

      val distance = point1 distanceTo point2

      assertThat(distance).isEqualTo(5.0) // sqrt((-4 - -1)^2 + (-5 - -1)^2) = 5
    }

    @Test
    fun `should calculate zero distance for the same point`() {
      val point1 = MutableDataPoint.of(5, 5, "data")

      val distance = point1 distanceTo point1

      assertThat(distance).isEqualTo(0.0)
    }
  }

  @Nested
  inner class ManhattanDistanceToTests {

    @Test
    fun `should calculate Manhattan distance correctly`() {
      val point1 = MutableDataPoint.of(0, 0, "data")
      val point2 = MutableDataPoint.of(3, 4, "data")

      val distance = point1 manhanttanDistanceTo point2

      assertThat(distance).isEqualTo(7) // |3 - 0| + |4 - 0| = 7
    }

    @Test
    fun `should calculate Manhattan distance for negative coordinates`() {
      val point1 = MutableDataPoint.of(-1, -1, "data")
      val point2 = MutableDataPoint.of(-4, -5, "data")

      val distance = point1 manhanttanDistanceTo point2

      assertThat(distance).isEqualTo(7) // |-4 - -1| + |-5 - -1| = 7
    }

    @Test
    fun `should calculate zero Manhattan distance for the same point`() {
      val point1 = MutableDataPoint.of(5, 5, "data")

      val distance = point1 manhanttanDistanceTo point1

      assertThat(distance).isEqualTo(0)
    }
  }

  @Nested
  inner class GetAllPointsWithinManhattanDistanceTests {

    @Test
    fun `should return zero points within Manhattan distance 0`() {
      val point = MutableDataPoint.of(0, 0, "data")

      val points = point.getAllPointsWithinManhattanDistance(0)

      assertThat(points).isEmpty()
    }

    @Test
    fun `should return all points within Manhattan distance 1`() {
      val point = MutableDataPoint.of(0, 0, "data")

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
      val point = MutableDataPoint.of(0, 0, "data")

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
      val point = MutableDataPoint.of(2, 3, "data")

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
      val point = MutableDataPoint.of(0, 0, "data")

      val points = point.getAllPointsWithinManhattanDistance(3)

      // Ensure no duplicates
      assertThat(points).doesNotHaveDuplicates()
    }
  }

  @Nested
  inner class MoveTests {

    @Test
    fun `should move the point by deltaX and deltaY`() {
      assertThat(MutableDataPoint.of(1, 1, "data").move(2, 3)).isEqualTo(MutableDataPoint.of(3, 4, "data"))
    }

    @Test
    fun `should move the point in a specific direction`() {
      assertThat(MutableDataPoint.of(1, 1, "data").move(Direction.UP)).isEqualTo(MutableDataPoint.of(1, 0, "data"))
      assertThat(MutableDataPoint.of(1, 1, "data").move(Direction.DOWN)).isEqualTo(MutableDataPoint.of(1, 2, "data"))
      assertThat(MutableDataPoint.of(1, 1, "data").move(Direction.LEFT)).isEqualTo(MutableDataPoint.of(0, 1, "data"))
      assertThat(MutableDataPoint.of(1, 1, "data").move(Direction.RIGHT)).isEqualTo(MutableDataPoint.of(2, 1, "data"))
      assertThat(MutableDataPoint.of(1, 1, "data").move(Direction.UP_LEFT)).isEqualTo(MutableDataPoint.of(0, 0, "data"))
      assertThat(MutableDataPoint.of(1, 1, "data").move(Direction.UP_RIGHT)).isEqualTo(MutableDataPoint.of(2, 0, "data"))
      assertThat(MutableDataPoint.of(1, 1, "data").move(Direction.DOWN_LEFT)).isEqualTo(MutableDataPoint.of(0, 2, "data"))
      assertThat(MutableDataPoint.of(1, 1, "data").move(Direction.DOWN_RIGHT)).isEqualTo(MutableDataPoint.of(2, 2, "data"))
    }

    @Test
    fun `should not modify the original point when moved`() {
      val point = MutableDataPoint.of(3, 4, "data")

      val movedPoint = point.move(2, 3)

      assertThat(point.x).isEqualTo(3)
      assertThat(point.y).isEqualTo(4)
      assertThat(point.data).isEqualTo("data")
      assertThat(movedPoint.x).isEqualTo(5)
      assertThat(movedPoint.y).isEqualTo(7)
      assertThat(movedPoint.data).isEqualTo("data")
    }

  }

  @Nested
  inner class FactoryTests {

    @Test
    fun `should create a point from x and y`() {
      val point = MutableDataPoint.of(3, 4, "data")

      assertThat(point.x).isEqualTo(3)
      assertThat(point.y).isEqualTo(4)
      assertThat(point.data).isEqualTo("data")
    }

    @Test
    fun `should create a point from point`() {
      val point = MutableDataPoint.of(Point.of(3, 4), "data")

      assertThat(point.x).isEqualTo(3)
      assertThat(point.y).isEqualTo(4)
      assertThat(point.data).isEqualTo("data")
    }

  }

}
