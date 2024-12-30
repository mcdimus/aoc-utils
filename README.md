# Advent of Code Utilities

Various utilities for Advent of Code, written in Kotlin.

## Overview

This project provides a set of utility functions to assist with solving Advent of Code puzzles. It includes a variety of helper methods and classes that simplify common tasks encountered during the challenges.

## Features

- Utility functions for string manipulation, file handling, and more.
- Helper classes for common Advent of Code patterns.
- Comprehensive unit tests to ensure reliability.

## Installation

The project publishes a Maven artifact to the GitHub Package Registry. To consume this package, add the following configuration to your `build.gradle.kts` file:

```kotlin
repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/mcdimus/aoc-utils")
        credentials {
            username = System.getenv("GITHUB_ACTOR") 
            password = System.getenv("GITHUB_TOKEN") 
        }
    }
}

dependencies {
    implementation("com.github.mcdimus.aoc:aoc-utils:<latest-version>")
}
```
