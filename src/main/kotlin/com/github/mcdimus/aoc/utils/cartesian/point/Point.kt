package com.github.mcdimus.aoc.utils.cartesian.point

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Represents a generic 2D point with integer coordinates `(x, y)`.
 *
 * @param SELF The type of the implementing class, used to support fluent APIs.
 */
interface Point<SELF : Point<SELF>> {
  val x: Int
  val y: Int

  /**
   * Computes the Euclidean distance between this point and another point.
   *
   * The Euclidean distance is calculated as:
   * `sqrt((x2 - x1)^2 + (y2 - y1)^2)`
   *
   * @param other The other point to which the distance is calculated.
   * @return The Euclidean distance as a [Double].
   */
  infix fun distanceTo(other: Point<*>) =
    sqrt((other.x - this.x).toDouble().pow(2) + (other.y - this.y).toDouble().pow(2))

  /**
   * Computes the Manhattan distance between this point and another point.
   *
   * The Manhattan distance is calculated as:
   * `|x2 - x1| + |y2 - y1|`
   *
   * @param other The other point to which the distance is calculated.
   * @return The Manhattan distance as an [Int].
   */
  infix fun manhanttanDistanceTo(other: Point<*>) =
    abs(this.x - other.x) + abs(this.y - other.y)

  /**
   * Retrieves all points within a given Manhattan distance from this point.
   *
   * This method generates all possible points that are within the specified Manhattan distance
   * from this point, including this point itself. The result is a list of unique points.
   *
   * @param manhattanDistance The maximum Manhattan distance from this point.
   * @return A list of all unique points within the specified Manhattan distance.
   */
  fun getAllPointsWithinManhattanDistance(manhattanDistance: Int): List<Point<*>> {
    val points = mutableSetOf<Point<*>>()
    var currentDistance = 1

    while (currentDistance <= manhattanDistance) {
      for (x in 0..currentDistance) {
        for (y in 0..currentDistance) {
          points.add(of(x = this.x + x, y = this.y + y))
          points.add(of(x = this.x - x, y = this.y - y))
          points.add(of(x = this.x + x, y = this.y - y))
          points.add(of(x = this.x - x, y = this.y + y))
        }
      }
      currentDistance++
    }
    return points.filter { it manhanttanDistanceTo this <= manhattanDistance }.distinct()
  }

  /**
   * Moves this point by the specified delta values.
   *
   * @param deltaX The change in the x-coordinate.
   * @param deltaY The change in the y-coordinate.
   * @return A new instance of the point with updated coordinates.
   */
  fun move(deltaX: Int, deltaY: Int): SELF

  /**
   * Moves this point in a specific direction.
   *
   * The direction is defined by a [Direction] object, which specifies the changes
   * in the x and y coordinates.
   *
   * @param direction The direction in which to move the point.
   * @return A new instance of the point with updated coordinates.
   */
  fun move(direction: Direction) = move(direction.deltaX, direction.deltaY)

  companion object {
    /**
     * Creates a new [Point] with the specified x and y coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return A new [Point] instance.
     */
    fun of(x: Int, y: Int): Point<*> = ImmutablePoint(x, y)

    /**
     * Creates a new [Point] from a string representation of coordinates.
     *
     * The string must contain two integers separated by a delimiter (default is `","`).
     *
     * @param string The string representation of the point (e.g., `"3,4"`).
     * @param separator The delimiter separating the x and y values. Defaults to `","`.
     * @return A new [Point] instance.
     * @throws NumberFormatException If the string does not contain valid integers.
     */
    fun of(string: String, separator: String = ","): Point<*> =
      string.split(separator).let { of(x = it[0].toInt(), y = it[1].toInt()) }
  }

  /**
   * A private implementation of an immutable [Point].
   *
   * @property x The x-coordinate of the point.
   * @property y The y-coordinate of the point.
   */
  private data class ImmutablePoint(override val x: Int, override val y: Int) : Point<ImmutablePoint> {
    override fun move(deltaX: Int, deltaY: Int) = ImmutablePoint(x + deltaX, y + deltaY)
  }

  /**
   * Destructures the point into its x-coordinate.
   *
   * @return The x-coordinate of this point.
   */
  operator fun component1(): Int

  /**
   * Destructures the point into its y-coordinate.
   *
   * @return The y-coordinate of this point.
   */
  operator fun component2(): Int
}
