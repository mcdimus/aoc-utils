package com.github.mcdimus.aoc.utils

/**
 * Computes the MD5 hash of this string.
 *
 * @return The MD5 hash of this string as a hexadecimal string.
 */
@OptIn(ExperimentalStdlibApi::class)
fun String.md5(): String {
  val md = java.security.MessageDigest.getInstance("MD5")
  return md.digest(this.toByteArray()).toHexString()
}
