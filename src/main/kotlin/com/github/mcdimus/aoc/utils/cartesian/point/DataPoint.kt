package com.github.mcdimus.aoc.utils.cartesian.point

/**
 * Represents a 2D point with additional associated data.
 *
 * A [DataPoint] combines a point in a 2D Cartesian coordinate system `(x, y)` with
 * an additional piece of data of type [T]. It extends the [Point] interface, inheriting
 * basic point-related functionality, and adds support for storing and accessing the associated data.
 *
 * @param T The type of the data associated with this point.
 */
interface DataPoint<T> : Point<DataPoint<T>> {
  val data: T

  companion object {
    /**
     * Creates a new [DataPoint] with the specified coordinates and associated data.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param data The data to associate with the point.
     * @return A new instance of [DataPoint] with the specified coordinates and data.
     */
    fun <T> of(x: Int, y: Int, data: T): DataPoint<T> = ImmutableDataPoint(x, y, data)

    /**
     * Creates a new [DataPoint] from an existing [Point] and associates it with the specified data.
     *
     * @param point The [Point] whose coordinates will be used for the new [DataPoint].
     * @param data The data to associate with the point.
     * @return A new instance of [DataPoint] with the same coordinates as the given [Point] and the specified data.
     */
    fun <T> of(point: Point<*>, data: T): DataPoint<T> = ImmutableDataPoint(point.x, point.y, data)
  }

  /**
   * Converts this [DataPoint] into a basic [Point] without the associated data.
   *
   * @return A [Point] instance with the same `x` and `y` coordinates as this [DataPoint].
   */
  fun asPoint() = Point.of(x, y)

  /**
   * A private implementation of an immutable [DataPoint].
   *
   * @param T The type of the data associated with this point.
   * @property x The x-coordinate of the point.
   * @property y The y-coordinate of the point.
   * @property data The data associated with the point.
   */
  private data class ImmutableDataPoint<T>(override val x: Int, override val y: Int, override val data: T) :
    DataPoint<T> {
    override fun move(deltaX: Int, deltaY: Int) = ImmutableDataPoint(x + deltaX, y + deltaY, data)
  }

  /**
   * Destructures the point into its associated data.
   *
   * This is the third component in the destructuring declaration.
   *
   * @return The data associated with this point.
   */
  operator fun component3(): T
}
