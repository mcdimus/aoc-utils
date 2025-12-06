package com.github.mcdimus.aoc.utils.range

/**
 * A utility class that represents a collection of non-overlapping and merged [LongRange]s.
 *
 * This class allows to manage a set of [LongRange]s, automatically merging overlapping
 * or touching (adjacent) ranges to maintain a normalized state.
 */
class CompositeClosedLongRange {

  /**
   * A mutable list of [LongRange]s that are managed by this class.
   * The list is kept normalized, meaning no two ranges overlap or are adjacent.
   */
  private val ranges: MutableList<LongRange> = arrayListOf()

  /**
   * Computes the total size of all ranges in this composite range.
   *
   * The size of each range is calculated as `(endInclusive - start + 1)`, and the
   * sizes of all ranges are summed together. E.g. size of `20..30` is `10`.
   *
   * @return The total size of all ranges in this composite range.
   */
  fun size() = ranges.sumOf(LongRange::size)

  /**
   * Retrieves the current list of ranges in this composite range.
   *
   * The returned list is normalized, meaning no two ranges overlap or are touching (adjacent).
   *
   * @return A list of all ranges in this composite range.
   */
  fun ranges(): List<LongRange> = ranges

  /**
   * Adds a new [LongRange] to this composite range.
   *
   * If the new range overlaps or is touching (adjacent to) any existing ranges, it will be merged
   * with them to maintain a normalized state. If the range is fully contained within an
   * existing range, it will not be added.
   *
   * @param range The [LongRange] to add.
   * @param index (Optional) The starting index for the merging process. Defaults to `0`.
   * @return The current instance of [CompositeClosedLongRange], allowing for method chaining.
   */
  fun add(range: LongRange, index: Int = 0): CompositeClosedLongRange {
    if (ranges.isEmpty()) {
      ranges.add(range)
      return this
    }

    var incorporated = false
    var i = index
    while (i < ranges.size) {
      val currentRange = ranges[i]

      if (currentRange.contains(range)) {
        // fully enclosed
        incorporated = true
        break
      }
      if (currentRange.overlaps(range) || currentRange.touches(range)) {
        // intersection
        ranges.removeAt(i)
        add(currentRange.merge(range), i)
        incorporated = true
        break
      }
      // no intersection; continue
      i++
    }

    if (!incorporated) {
      ranges.add(range)
    }

    return this
  }

}
