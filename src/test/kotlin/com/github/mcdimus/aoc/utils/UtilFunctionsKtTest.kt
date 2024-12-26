package com.github.mcdimus.aoc.utils

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.io.FileNotFoundException

class UtilFunctionsKtTest {

  @Test
  fun `readInput Throws FileNotFoundException If file is not found`() {
    assertThatThrownBy { readInput("does_not_exist") }
      .isInstanceOf(FileNotFoundException::class.java)
      .hasMessage("does_not_exist.txt")
  }

  @Test
  fun `readInput Returns lines from the file located in resources`() {
    val lines = readInput("file")
    assertThat(lines).containsExactly("line 1", "line 2", "line ...", "line X")
  }

  @Test
  fun `readInput Throws IOException If reading from stream fails`() {
    assertThatThrownBy { readInput("does_not_exist") }
      .isInstanceOf(FileNotFoundException::class.java)
      .hasMessage("does_not_exist.txt")
  }

}
