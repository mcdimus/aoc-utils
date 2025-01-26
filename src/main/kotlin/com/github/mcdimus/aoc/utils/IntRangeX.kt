package com.github.mcdimus.aoc.utils

/**
 * Converts this string to an IntRange. The string must be in the format "start-end". The delimiter can be customized.
 */
fun String.toIntRange(delimiter: Char = '-'): IntRange = this.split(delimiter).let {
  runCatching {
    require(it.size == 2)
    it[0].toInt()..it[1].toInt()
  }.getOrElse { throw RangeFormatException("Invalid range format: $this", it) }
}

class RangeFormatException(message: String, cause: Throwable) : Exception(message, cause)

/**
 * Checks if this range contains the other range.
 */
infix fun IntRange.contains(other: IntRange): Boolean {
  return this.contains(other.first) && this.contains(other.last)
}

/**
 * Checks if this range overlaps with the other range.
 */
infix fun IntRange.overlaps(other: IntRange): Boolean {
  return this.contains(other.first) || this.contains(other.last)
      || other.contains(this.first) || other.contains(this.last)
}
