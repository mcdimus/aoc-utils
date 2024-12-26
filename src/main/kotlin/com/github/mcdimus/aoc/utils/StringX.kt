package com.github.mcdimus.aoc.utils

@OptIn(ExperimentalStdlibApi::class)
fun String.md5(): String {
  val md = java.security.MessageDigest.getInstance("MD5")
  return md.digest(this.toByteArray()).toHexString()
}
