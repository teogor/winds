import org.jetbrains.dokka.gradle.DokkaPlugin

buildscript {
  repositories {
    google()
    mavenCentral()
  }
}

// Lists all plugins used throughout the project without applying them.
plugins {
  // Kotlin Plugins
  alias(libs.plugins.jetbrains.kotlin.jvm) apply false
  alias(libs.plugins.jetbrains.kotlin.serialization) apply false

  // Maven Publishing Plugins
  alias(libs.plugins.vanniktech.maven) apply false

  // API Documentation and Validation Plugins
  alias(libs.plugins.jetbrains.dokka) apply true
  alias(libs.plugins.spotless) apply true
  alias(libs.plugins.api.validator) apply true
}

// Explicitly set the group and version for all subprojects
allprojects {
  group = "dev.teogor.winds"
  version = "1.0.0-beta04"
}

val ktlintVersion = "0.50.0"

val excludedProjects = listOf(
  project.name,
)

subprojects {
  if (!excludedProjects.contains(this.name)) {
    apply<com.diffplug.gradle.spotless.SpotlessPlugin>()
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
      kotlin {
        target("**/*.kt")
        targetExclude("**/build/**/*.kt")
        ktlint(ktlintVersion)
          .editorConfigOverride(
            mapOf(
              "ij_kotlin_allow_trailing_comma" to "true",
              // These rules were introduced in ktlint 0.46.0 and should not be
              // enabled without further discussion. They are disabled for now.
              // See: https://github.com/pinterest/ktlint/releases/tag/0.46.0
              "disabled_rules" to
                "filename," +
                "annotation,annotation-spacing," +
                "argument-list-wrapping," +
                "double-colon-spacing," +
                "enum-entry-name-case," +
                "multiline-if-else," +
                "no-empty-first-line-in-method-block," +
                "package-name," +
                "trailing-comma," +
                "spacing-around-angle-brackets," +
                "spacing-between-declarations-with-annotations," +
                "spacing-between-declarations-with-comments," +
                "unary-op-spacing," +
                "no-trailing-spaces," +
                "no-wildcard-imports," +
                "max-line-length",
            ),
          )
        licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        trimTrailingWhitespace()
        endWithNewline()
      }
      format("kts") {
        target("**/*.kts")
        targetExclude("**/build/**/*.kts")
        // Look for the first line that doesn't have a block comment (assumed to be the license)
        licenseHeaderFile(rootProject.file("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
      }
      format("xml") {
        target("**/*.xml")
        targetExclude("**/build/**/*.xml")
        // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
        licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
      }
    }
  }
}

apiValidation {
  /**
   * Subprojects that are excluded from API validation
   */
  ignoredProjects.addAll(excludedProjects)
}

subprojects {
  if (!excludedProjects.contains(project.name)) {
    apply<DokkaPlugin>()
  }
}
