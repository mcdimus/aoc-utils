@file:Suppress("TooManyFunctions", "ComplexCondition", "TooGenericExceptionThrown")
package com.github.mcdimus.aoc.utils.range

import kotlin.math.max
import kotlin.math.min

fun String.toIntRange(delimiter: Char = '-'): IntRange = this.split(delimiter).let {
  runCatching {
    require(it.size == 2) { "expected 2 elements but got ${it.size}: $it" }
    val a = it[0].toInt()
    val b = it[1].toInt()
    require(a <= b) { "expected $a <= $b" }
    a..b
  }.getOrElse { cause -> throw IllegalArgumentException("invalid range format: $this", cause) }
}

fun String.toLongRange(delimiter: Char = '-'): LongRange = this.split(delimiter).let {
  runCatching {
    require(it.size == 2) { "expected 2 elements but got ${it.size}: $it" }
    val a = it[0].toLong()
    val b = it[1].toLong()
    require(a <= b) { "expected $a <= $b" }
    a..b
  }.getOrElse { cause -> throw IllegalArgumentException("invalid range format: $this", cause) }
}

fun <T : Comparable<T>> ClosedRange<T>.overlaps(other: ClosedRange<T>): Boolean =
  this.start <= other.endInclusive && other.start <= this.endInclusive

fun <T : Comparable<T>> ClosedRange<T>.contains(other: ClosedRange<T>): Boolean =
  this.contains(other.start) && this.contains(other.endInclusive)

fun <T : ClosedRange<Int>> IntRange.touches(other: T): Boolean =
  this.endInclusive + 1 == other.start || other.endInclusive + 1 == this.start

fun <T : ClosedRange<Long>> LongRange.touches(other: T): Boolean =
  this.endInclusive + 1 == other.start || other.endInclusive + 1 == this.start

fun <T : Comparable<T>> ClosedRange<T>.merge(other: ClosedRange<T>): ClosedRange<T> {
  require(this.overlaps(other)) { "cannot merge non-overlapping ranges" }
  return minOf(start, other.start)..maxOf(endInclusive, other.endInclusive)
}

fun <T : ClosedRange<Int>> IntRange.merge(other: T): IntRange {
  require(this.overlaps(other) || this.touches(other)) { "cannot merge non-overlapping and non-touching ranges" }
  return min(start, other.start)..max(endInclusive, other.endInclusive)
}

fun <T : ClosedRange<Long>> LongRange.merge(other: T): LongRange {
  require(this.overlaps(other) || this.touches(other)) { "cannot merge non-overlapping and non-touching ranges" }
  return min(start, other.start)..max(endInclusive, other.endInclusive)
}

fun ClosedRange<Int>.size(): Int {
  if (this.start == Int.MIN_VALUE && this.endInclusive == Int.MAX_VALUE
    || this.start == Int.MAX_VALUE && this.endInclusive == Int.MIN_VALUE
  ) {
    throw RuntimeException("integer overflow")
  }
  return this.endInclusive - this.start + 1
}

fun ClosedRange<Long>.size(): Long {
  if (this.start == Long.MIN_VALUE && this.endInclusive == Long.MAX_VALUE
    || this.start == Long.MAX_VALUE && this.endInclusive == Long.MIN_VALUE
  ) {
    throw RuntimeException("long overflow")
  }
  return this.endInclusive - this.start + 1
}
