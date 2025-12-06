package com.github.mcdimus.aoc.utils.cartesian.point

enum class Direction(val deltaX: Int, val deltaY: Int, val isDiagonal: Boolean) {
  UP_LEFT(-1, -1, true),
  UP(0, -1, false),
  UP_RIGHT(1, -1, true),
  LEFT(-1, 0, false),
  RIGHT(1, 0, false),
  DOWN_LEFT(-1, 1, true),
  DOWN(0, 1, false),
  DOWN_RIGHT(1, 1, true);

  companion object {
    val entriesDiagonals: List<Direction> = entries.filter { it.isDiagonal }
    val entriesStraight: List<Direction> = entries.filter { !it.isDiagonal }

    fun straightOf(value: Char) = entriesStraight.single { it.name.startsWith(value) }
    fun straightOf(value: String) = entriesStraight.single { it.name.startsWith(value) }
  }

}
