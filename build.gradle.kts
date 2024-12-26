import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.CoverageUnit
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  kotlin("jvm") version "2.1.0"
  id("org.jetbrains.kotlinx.kover") version "0.9.0"
  id("io.gitlab.arturbosch.detekt") version "1.23.7"
}

group = "com.github.mcdimus.aoc"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

kotlin {
  jvmToolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
    vendor.set(JvmVendorSpec.ADOPTIUM)
  }
  compilerOptions {
    jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
  }
}

dependencies {
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.4")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.4")
  testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.4")
  testImplementation("org.assertj:assertj-core:3.27.0")
  testImplementation("io.mockk:mockk:1.13.14")
}

tasks.test {
  useJUnitPlatform()

  testLogging {
    events = setOf(TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    exceptionFormat = TestExceptionFormat.FULL
  }
}

kover {
  reports {
    total {
      html.onCheck = true
      xml.onCheck = true
      log {
        onCheck = true
        format = "Instruction coverage: <value>%"
        coverageUnits = CoverageUnit.INSTRUCTION
      }
    }
    verify {
      rule("line coverage") {
        bound {
          aggregationForGroup = AggregationType.COVERED_PERCENTAGE
          coverageUnits = CoverageUnit.LINE
          minValue = 100
        }
      }
      rule("instruction coverage") {
        bound {
          aggregationForGroup = AggregationType.COVERED_PERCENTAGE
          coverageUnits = CoverageUnit.INSTRUCTION
          minValue = 90
        }
      }
      rule("branch coverage") {
        bound {
          aggregationForGroup = AggregationType.COVERED_PERCENTAGE
          coverageUnits = CoverageUnit.BRANCH
          minValue = 66
        }
      }
    }
  }
}

detekt {
  config.from("config/detekt/detekt.yml")
  allRules = true
}

tasks.wrapper {
  gradleVersion = "8.12"
  distributionType = Wrapper.DistributionType.ALL
}
