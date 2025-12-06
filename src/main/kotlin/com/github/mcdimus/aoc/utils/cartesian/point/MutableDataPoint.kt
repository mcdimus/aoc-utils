package com.github.mcdimus.aoc.utils.cartesian.point

/**
 * A mutable implementation of the [DataPoint] interface.
 *
 * Represents a 2D point with integer coordinates `(x, y)` and associated data of type [T].
 * The coordinates and the associated data can be updated.
 *
 * @param T The type of the data associated with this point.
 * @property x The x-coordinate of the point. This property is mutable.
 * @property y The y-coordinate of the point. This property is mutable.
 * @property data The data associated with the point. This property is mutable.
 */
@ConsistentCopyVisibility
data class MutableDataPoint<T> private constructor(override var x: Int, override var y: Int, override var data: T) : DataPoint<T> {
  override fun move(deltaX: Int, deltaY: Int) = MutableDataPoint(x + deltaX, y + deltaY, data)

  companion object {
    /**
     * Creates a new [MutableDataPoint] with the specified coordinates and associated data.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param data The data to associate with the point.
     * @return A new instance of [MutableDataPoint] with the specified coordinates and data.
     */
    fun <T> of(x: Int, y: Int, data: T): MutableDataPoint<T> = MutableDataPoint(x, y, data)

    /**
     * Creates a new [MutableDataPoint] from an existing [Point] and associates it with the specified data.
     *
     * @param point The [Point] whose coordinates will be used for the new [MutableDataPoint].
     * @param data The data to associate with the point.
     * @return A new instance of [MutableDataPoint] with the same coordinates as the given [Point] and the specified data.
     */
    fun <T> of(point: Point<*>, data: T): MutableDataPoint<T> = MutableDataPoint(point.x, point.y, data)
  }

}
