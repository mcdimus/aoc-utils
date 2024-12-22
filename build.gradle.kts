plugins {
  kotlin("jvm") version "2.0.21"
}

group = "com.github.mcdimus.aoc"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

tasks.wrapper {
  gradleVersion = "8.12"
  distributionType = Wrapper.DistributionType.ALL
}
