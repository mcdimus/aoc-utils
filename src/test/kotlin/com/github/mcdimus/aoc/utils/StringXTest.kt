package com.github.mcdimus.aoc.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class StringXTest {

  @ParameterizedTest
  @CsvSource(
    useHeadersInDisplayName = true,
    textBlock = """
      input, result  
      '',d41d8cd98f00b204e9800998ecf8427e
      1,c4ca4238a0b923820dcc509a6f75849b
      hello,5d41402abc4b2a76b9719d911017c592
      world 21 !@#$%^&*()_+,69949f8c409166842c3a425959451655"""
  )
  fun md5(input: String, result: String) {
    assertThat(input.md5()).isEqualTo(result)
  }

}
