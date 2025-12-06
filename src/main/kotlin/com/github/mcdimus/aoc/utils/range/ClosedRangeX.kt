@file:Suppress("TooManyFunctions", "ComplexCondition", "TooGenericExceptionThrown")

package com.github.mcdimus.aoc.utils.range

import kotlin.math.max
import kotlin.math.min

/**
 * Converts a string representation of an integer range into an [IntRange].
 *
 * The string must contain two integers separated by a delimiter (default is `'-'`).
 * The two integers must be in ascending order (i.e., `a <= b`).
 *
 * @param delimiter The character used to separate the two integers in the string. Defaults to `'-'`.
 * @return The parsed [IntRange].
 * @throws IllegalArgumentException If the string is not in the expected format, contains non-numeric values,
 * or the integers are not in ascending order.
 */
fun String.toIntRange(delimiter: Char = '-'): IntRange = this.split(delimiter).let {
  runCatching {
    require(it.size == 2) { "expected 2 elements but got ${it.size}: $it" }
    val a = it[0].toInt()
    val b = it[1].toInt()
    require(a <= b) { "expected $a <= $b" }
    a..b
  }.getOrElse { cause -> throw IllegalArgumentException("invalid range format: $this", cause) }
}

/**
 * Converts a string representation of a long range into a [LongRange].
 *
 * The string must contain two long integers separated by a delimiter (default is `'-'`).
 * The two integers must be in ascending order (i.e., `a <= b`).
 *
 * @param delimiter The character used to separate the two integers in the string. Defaults to `'-'`.
 * @return The parsed [LongRange].
 * @throws IllegalArgumentException If the string is not in the expected format, contains non-numeric values,
 * or the integers are not in ascending order.
 */
fun String.toLongRange(delimiter: Char = '-'): LongRange = this.split(delimiter).let {
  runCatching {
    require(it.size == 2) { "expected 2 elements but got ${it.size}: $it" }
    val a = it[0].toLong()
    val b = it[1].toLong()
    require(a <= b) { "expected $a <= $b" }
    a..b
  }.getOrElse { cause -> throw IllegalArgumentException("invalid range format: $this", cause) }
}

/**
 * Checks if this [ClosedRange] overlaps with another [ClosedRange].
 *
 * Two ranges overlap if their values intersect at any point.
 *
 * @param other The [ClosedRange] to check for overlap.
 * @return `true` if the ranges overlap, `false` otherwise.
 */
fun <T : Comparable<T>> ClosedRange<T>.overlaps(other: ClosedRange<T>): Boolean =
  this.start <= other.endInclusive && other.start <= this.endInclusive

/**
 * Checks if this [ClosedRange] **fully** contains another [ClosedRange].
 *
 * A range is considered contained if all of its values are within this range.
 *
 * @param other The [ClosedRange] to check for containment.
 * @return `true` if this range contains the other range, `false` otherwise.
 */
fun <T : Comparable<T>> ClosedRange<T>.contains(other: ClosedRange<T>): Boolean =
  this.contains(other.start) && this.contains(other.endInclusive)

/**
 * Checks if this [IntRange] touches another [ClosedRange].
 *
 * Two ranges touch if they are adjacent but do not overlap.
 *
 * @param other The [ClosedRange] to check for adjacency.
 * @return `true` if the ranges touch, `false` otherwise.
 */
fun <T : ClosedRange<Int>> IntRange.touches(other: T): Boolean =
  this.endInclusive + 1 == other.start || other.endInclusive + 1 == this.start

/**
 * Checks if this [LongRange] touches another [ClosedRange].
 *
 * Two ranges touch if they are adjacent but do not overlap.
 *
 * @param other The [ClosedRange] to check for adjacency.
 * @return `true` if the ranges touch, `false` otherwise.
 */
fun <T : ClosedRange<Long>> LongRange.touches(other: T): Boolean =
  this.endInclusive + 1 == other.start || other.endInclusive + 1 == this.start

/**
 * Merges this [IntRange] with another [ClosedRange].
 *
 * The ranges must either overlap or touch each other. If they do not, an exception is thrown.
 *
 * @param other The [ClosedRange] to merge with this range.
 * @return A new [IntRange] that spans the minimum start to the maximum end of both ranges.
 * @throws IllegalArgumentException If the ranges do not overlap or touch.
 */
fun <T : ClosedRange<Int>> IntRange.merge(other: T): IntRange {
  require(this.overlaps(other) || this.touches(other)) { "cannot merge non-overlapping and non-touching ranges" }
  return min(start, other.start)..max(endInclusive, other.endInclusive)
}

/**
 * Merges this [LongRange] with another [ClosedRange].
 *
 * The ranges must either overlap or touch each other. If they do not, an exception is thrown.
 *
 * @param other The [ClosedRange] to merge with this range.
 * @return A new [LongRange] that spans the minimum start to the maximum end of both ranges.
 * @throws IllegalArgumentException If the ranges do not overlap or touch.
 */
fun <T : ClosedRange<Long>> LongRange.merge(other: T): LongRange {
  require(this.overlaps(other) || this.touches(other)) { "cannot merge non-overlapping and non-touching ranges" }
  return min(start, other.start)..max(endInclusive, other.endInclusive)
}

/**
 * Calculates the size of this [ClosedRange] of integers.
 *
 * The size is calculated as `(endInclusive - start + 1)`. The method ensures there is no
 * integer overflow by using safe arithmetic.
 *
 * @return The size of this range as an [Int].
 * @throws ArithmeticException If an integer overflow occurs during the calculation.
 */
fun ClosedRange<Int>.size(): Int {
  return Math.addExact(Math.subtractExact(this.endInclusive, this.start), 1)
}

/**
 * Calculates the size of this [ClosedRange] of long integers.
 *
 * The size is calculated as `(endInclusive - start + 1)`. The method ensures there is no
 * integer overflow by using safe arithmetic.
 *
 * @return The size of this range as a [Long].
 * @throws ArithmeticException If an overflow occurs during the calculation.
 */
fun ClosedRange<Long>.size(): Long {
  return Math.addExact(Math.subtractExact(this.endInclusive, this.start), 1)
}
