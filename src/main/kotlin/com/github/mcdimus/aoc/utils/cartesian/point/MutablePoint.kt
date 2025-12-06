package com.github.mcdimus.aoc.utils.cartesian.point

@ConsistentCopyVisibility
data class MutablePoint private constructor(override var x: Int, override var y: Int) : Point<MutablePoint> {
  override fun move(deltaX: Int, deltaY: Int) = MutablePoint(x + deltaX, y + deltaY)

  companion object {
    /**
     * Creates a new [MutablePoint] with the specified x and y coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return A new [MutablePoint] instance.
     */
    fun of(x: Int, y: Int): MutablePoint = MutablePoint(x, y)

    /**
     * Creates a new [MutablePoint] from a string representation of coordinates.
     *
     * The string must contain two integers separated by a delimiter (default is `","`).
     *
     * @param string The string representation of the point (e.g., `"3,4"`).
     * @param separator The delimiter separating the x and y values. Defaults to `","`.
     * @return A new [MutablePoint] instance.
     * @throws NumberFormatException If the string does not contain valid integers.
     */
    fun of(string: String, separator: String = ","): MutablePoint =
      string.split(separator).let { of(x = it[0].toInt(), y = it[1].toInt()) }
  }
}
