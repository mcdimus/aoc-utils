
import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.CoverageUnit
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jreleaser.model.Active
import org.jreleaser.model.api.common.Apply

plugins {
  alias(libs.plugins.kotlin)
  alias(libs.plugins.kover)
  alias(libs.plugins.detekt)
  alias(libs.plugins.semver)
  alias(libs.plugins.jreleaser)
  alias(libs.plugins.jmh)
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

java {
  withSourcesJar()
}

dependencies {
  testRuntimeOnly(libs.junit.jupiter.engine)
  testImplementation(libs.bundles.junit.jupiter)
  testImplementation(libs.assertj)
  testImplementation(libs.mockk)
}

tasks.test {
  useJUnitPlatform()

  testLogging {
    events = setOf(TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    exceptionFormat = TestExceptionFormat.FULL
  }
}

kover {
  currentProject {
    sources {
      excludedSourceSets.addAll("jmh")
    }
  }
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
  // set Detekt.basePath on each Detekt Gradle task,
  // so that GitHub knows where the repository is to place annotations correctly
  basePath = rootProject.projectDir.absolutePath
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
        formatted = Active.ALWAYS
        preset = "conventional-commits"
        extraProperties = mapOf(
          "categorizeScopes" to true
        )

        contributors {
          enabled = false
        }
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

jmh {
  jmhVersion = libs.versions.jmh.get()
  profilers = listOf("gc")
}

tasks.wrapper {
  gradleVersion = "8.12"
  distributionType = Wrapper.DistributionType.ALL
}
