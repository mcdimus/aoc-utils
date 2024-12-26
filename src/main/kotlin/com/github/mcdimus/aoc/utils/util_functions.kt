package com.github.mcdimus.aoc.utils

import java.io.BufferedReader
import java.io.FileNotFoundException

/**
 * Reads lines from the given input txt file.
 * The file should be located in the classpath and match `$name.txt` name.
 */
fun readInput(name: String): List<String> = object {}.javaClass.getResourceAsStream("/$name.txt")?.use {
  it.bufferedReader().use(BufferedReader::readLines)
} ?: throw FileNotFoundException("$name.txt")
