package com.github.mcdimus.aoc.utils

import java.io.BufferedReader
import java.io.FileNotFoundException

/**
 * Reads lines from the given input text file.
 * The file should be located in the classpath and match the `$name.txt` name.
 *
 * @param name The name of the file (without the `.txt` extension) to read from the classpath.
 * @return A list of strings, each representing a line from the file.
 * @throws FileNotFoundException If the file is not found in the classpath.
 */
fun readInput(name: String): List<String> = object {}.javaClass.getResourceAsStream("/$name.txt")?.use {
  it.bufferedReader().use(BufferedReader::readLines)
} ?: throw FileNotFoundException("$name.txt")
