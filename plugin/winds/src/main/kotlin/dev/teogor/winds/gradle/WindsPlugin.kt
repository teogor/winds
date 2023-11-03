package dev.teogor.winds.gradle

import dev.teogor.winds.api.impl.WindsOptions
import dev.teogor.winds.gradle.tasks.impl.configureDocsGenerator
import dev.teogor.winds.gradle.tasks.impl.configureMavenPublish
import dev.teogor.winds.gradle.tasks.impl.configureWorkflowSynthesizer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType

/**
 * A Gradle plugin that adds Winds support to projects.
 *
 * This plugin provides the following features:
 *
 * - Configures Maven publishing for the project.
 * - Configures documentation generation for the project.
 */
class WindsPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      // Creates the `Winds` extension object for the project.
      createWindsExtension()

      // Configures Maven publishing for the project.
      configureMavenPublish()

      // Configures documentation generation for the project.
      configureDocsGenerator()

      // Configures workflow synthesis for the project.
      configureWorkflowSynthesizer()
    }
  }
}

/**
 * Creates the `Winds` extension object if it does not
 * already exist.

 * @return The `Winds` extension object.
 */
private fun Project.createWindsExtension() {
  extensions.findByType<WindsOptions>() ?: extensions.create<WindsOptions>(
    name = "winds",
  )
}
