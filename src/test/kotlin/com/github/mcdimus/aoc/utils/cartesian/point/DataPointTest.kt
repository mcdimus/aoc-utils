package com.github.mcdimus.aoc.utils.cartesian.point

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DataPointTest {

  @Test
  fun `should destructure correctly`() {
    val (x, y, data) = DataPoint.of(11, 22, "a")

    assertThat(x).isEqualTo(11)
    assertThat(y).isEqualTo(22)
    assertThat(data).isEqualTo("a")
    assertThat(DataPoint.of(11, 22, "a").component1()).isEqualTo(11)
    assertThat(DataPoint.of(11, 22, "a").component2()).isEqualTo(22)
    assertThat(DataPoint.of(11, 22, "a").component3()).isEqualTo("a")
  }

  @Test
  fun `should return as simple point`() {
    val dataPoint = DataPoint.of(11, 22, "a")

    assertThat(dataPoint.asPoint()).isEqualTo(Point.of(11, 22))
  }

  @Nested
  inner class DistanceToTests {

    @Test
    fun `should calculate Euclidean distance correctly`() {
      val point1 = DataPoint.of(0, 0, "a")
      val point2 = DataPoint.of(3, 4, "a")

      val distance = point1 distanceTo point2

      assertThat(distance).isEqualTo(5.0) // sqrt(3^2 + 4^2) = 5
    }

    @Test
    fun `should calculate Euclidean distance for negative coordinates`() {
      val point1 = DataPoint.of(-1, -1, "a")
      val point2 = DataPoint.of(-4, -5, "a")

      val distance = point1 distanceTo point2

      assertThat(distance).isEqualTo(5.0) // sqrt((-4 - -1)^2 + (-5 - -1)^2) = 5
    }

    @Test
    fun `should calculate zero distance for the same point`() {
      val point1 = DataPoint.of(5, 5, "a")

      val distance = point1 distanceTo point1

      assertThat(distance).isEqualTo(0.0)
    }
  }

  @Nested
  inner class ManhattanDistanceToTests {

    @Test
    fun `should calculate Manhattan distance correctly`() {
      val point1 = DataPoint.of(0, 0, "a")
      val point2 = DataPoint.of(3, 4, "a")

      val distance = point1 manhanttanDistanceTo point2

      assertThat(distance).isEqualTo(7) // |3 - 0| + |4 - 0| = 7
    }

    @Test
    fun `should calculate Manhattan distance for negative coordinates`() {
      val point1 = DataPoint.of(-1, -1, "a")
      val point2 = DataPoint.of(-4, -5, "a")

      val distance = point1 manhanttanDistanceTo point2

      assertThat(distance).isEqualTo(7) // |-4 - -1| + |-5 - -1| = 7
    }

    @Test
    fun `should calculate zero Manhattan distance for the same point`() {
      val point1 = DataPoint.of(5, 5, "a")

      val distance = point1 manhanttanDistanceTo point1

      assertThat(distance).isEqualTo(0)
    }
  }

  @Nested
  inner class GetAllPointsWithinManhattanDistanceTests {

    @Test
    fun `should return zero points within Manhattan distance 0`() {
      val point = DataPoint.of(0, 0, "a")

      val points = point.getAllPointsWithinManhattanDistance(0)

      assertThat(points).isEmpty()
    }

    @Test
    fun `should return all points within Manhattan distance 1`() {
      val point = DataPoint.of(0, 0, "a")

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
      val point = DataPoint.of(0, 0, "a")

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
      val point = DataPoint.of(2, 3, "a")

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
      val point = DataPoint.of(0, 0, "a")

      val points = point.getAllPointsWithinManhattanDistance(3)

      // Ensure no duplicates
      assertThat(points).doesNotHaveDuplicates()
    }
  }

  @Nested
  inner class MoveTests {

    @Test
    fun `should move the point by deltaX and deltaY`() {
      assertThat(DataPoint.of(1, 1, "a").move(2, 3)).isEqualTo(DataPoint.of(3, 4, "a"))
    }

    @Test
    fun `should move the point in a specific direction`() {
      assertThat(DataPoint.of(1, 1, "a").move(Direction.UP)).isEqualTo(DataPoint.of(1, 0, "a"))
      assertThat(DataPoint.of(1, 1, "a").move(Direction.DOWN)).isEqualTo(DataPoint.of(1, 2, "a"))
      assertThat(DataPoint.of(1, 1, "a").move(Direction.LEFT)).isEqualTo(DataPoint.of(0, 1, "a"))
      assertThat(DataPoint.of(1, 1, "a").move(Direction.RIGHT)).isEqualTo(DataPoint.of(2, 1, "a"))
      assertThat(DataPoint.of(1, 1, "a").move(Direction.UP_LEFT)).isEqualTo(DataPoint.of(0, 0, "a"))
      assertThat(DataPoint.of(1, 1, "a").move(Direction.UP_RIGHT)).isEqualTo(DataPoint.of(2, 0, "a"))
      assertThat(DataPoint.of(1, 1, "a").move(Direction.DOWN_LEFT)).isEqualTo(DataPoint.of(0, 2, "a"))
      assertThat(DataPoint.of(1, 1, "a").move(Direction.DOWN_RIGHT)).isEqualTo(DataPoint.of(2, 2, "a"))
    }

  }

  @Nested
  inner class FactoryTests {

    @Test
    fun `should create a point from x and y`() {
      val point = DataPoint.of(3, 4, "a")

      assertThat(point.x).isEqualTo(3)
      assertThat(point.y).isEqualTo(4)
      assertThat(point.data).isEqualTo("a")
    }

    @Test
    fun `should create a point from a string representation`() {
      val point = DataPoint.of(Point.of(3, 4), "a")

      assertThat(point.x).isEqualTo(3)
      assertThat(point.y).isEqualTo(4)
      assertThat(point.data).isEqualTo("a")
    }

  }

}
