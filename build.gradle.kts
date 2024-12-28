
import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.CoverageUnit
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jreleaser.model.api.common.Apply

plugins {
  kotlin("jvm") version "2.1.0"
  id("org.jetbrains.kotlinx.kover") version "0.9.0"
  id("io.gitlab.arturbosch.detekt") version "1.23.7"
  id("com.github.jmongard.git-semver-plugin") version "0.13.0"
  id("org.jreleaser") version "1.15.0"
  `maven-publish`
}

group = "com.github.mcdimus.aoc"
version = semver.version

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

semver {
  createReleaseCommit = false
}

jreleaser {
  release {
    github {
      enabled = true
      repoOwner = "mcdimus"
      tagName = project.version.get()
      overwrite = true

      // Skips creating a tag.
      // Useful when the tag was created externally.
      // Defaults to `false`.
      skipTag = true

      // Skips creating a release.
      // Useful when release assets will be handled with an uploader.
      // Defaults to `false`.
      skipRelease = false

      // Signs commits with the configured credentials.
      // The Signing section must be configured as well.
      sign = false

      releaseNotes {
        // Generate release notes using GitHub's native support.
        enabled = false
      }
      changelog {
        enabled = true
        preset = "conventional-commits"
      }

      // Update issues upon release.
      // Adds a label and post a comment to every issue found in the changelog.
      issues {
        // Enables this feature.
        // Defaults to `false`.
        //
        enabled = true

        // Comment to post on matching issues.
        comment = "🎉 This issue has been resolved in `{{tagName}}` ([Release Notes]({{releaseNotesUrl}}))"

        // Applies the current milestone to issues
        applyMilestone = Apply.ALWAYS
      }
    }
  }
}

tasks.check {
  dependsOn("printVersion")
  doLast {
    print("Version: ${project.version}")
  }
}

publishing {
  repositories {
    maven {
      name = "GitHubPackages"
      url = uri("https://maven.pkg.github.com/mcdimus/aoc-utils")
      credentials {
        username = System.getenv("GITHUB_ACTOR") ?: "mcdimus"
        password = System.getenv("GITHUB_TOKEN") ?: "N/A"
      }
    }
  }
  publications {
    register<MavenPublication>("gpr") {
      from(components["java"])
    }
  }
}

tasks.wrapper {
  gradleVersion = "8.12"
  distributionType = Wrapper.DistributionType.ALL
}
