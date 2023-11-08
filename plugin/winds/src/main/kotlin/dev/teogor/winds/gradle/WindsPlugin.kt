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

import dev.teogor.winds.api.Winds
import dev.teogor.winds.api.impl.WindsOptions
import dev.teogor.winds.gradle.tasks.impl.configureDocsGenerator
import dev.teogor.winds.gradle.tasks.impl.configureMavenPublish
import dev.teogor.winds.gradle.tasks.impl.configureWorkflowSynthesizer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate

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
 * Creates a Winds extension for the project and propagates the `buildFeatures`
 * property to child projects.
 */
private fun Project.createWindsExtension() {
  // Check if the WindsOptions extension already exists
  extensions.findByType<WindsOptions>() ?: extensions.create<WindsOptions>(
    name = "winds",
  )

  // Get the Winds extension
  val winds: Winds by extensions

  // Get the current project
  val project = this

  // After the project has been evaluated, iterate over the child projects
  afterEvaluate {
    subprojects {
      afterEvaluate {
        // Get the local project
        val localProject = this

        // Check if the local project is a child of the current project
        if (parent == project) {
          // Get the local Winds extension
          val localWinds = localProject.extensions.findByType<Winds>()

          // If the local Winds extension exists, set its `buildFeatures` property to the `buildFeatures` property of the current project's Winds extension
          if (localWinds != null) {
            localWinds.buildFeatures = winds.buildFeatures
          }
        }
      }
    }
  }
}
