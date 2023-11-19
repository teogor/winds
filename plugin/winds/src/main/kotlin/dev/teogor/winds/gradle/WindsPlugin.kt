/*
 * Copyright 2023 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.winds.gradle

import dev.teogor.winds.api.impl.WindsOptions
import dev.teogor.winds.gradle.tasks.impl.configureDocsGenerator
import dev.teogor.winds.gradle.tasks.impl.configureMavenPublish
import dev.teogor.winds.gradle.tasks.impl.configureWorkflowSynthesizer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType

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
 * Creates a `WindsOptions` extension for the project and propagates
 * its `buildFeatures` property to child projects. If the parent project
 * has the `dev.teogor.winds` plugin, the `buildFeatures` and `docsGenerator`
 * properties are copied from the parent project's `WindsOptions` extension.
 */
private fun Project.createWindsExtension() {
  // Check if the WindsOptions extension already exists
  extensions.findByType<WindsOptions>() ?: let {
    val rootHas = parent?.plugins?.hasPlugin("dev.teogor.winds") ?: false
    extensions.create<WindsOptions>(name = "winds").also {
      if (rootHas) {
        val rootWindsOptions = parent!!.extensions.getByType<WindsOptions>()
        it.buildFeatures = rootWindsOptions.buildFeatures
        it.docsGenerator = rootWindsOptions.docsGenerator
      }
    }
  }
}
