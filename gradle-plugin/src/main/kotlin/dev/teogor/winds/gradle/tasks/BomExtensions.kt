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

package dev.teogor.winds.gradle.tasks

import dev.teogor.winds.api.BomOptions
import dev.teogor.winds.api.PublishingOptions
import dev.teogor.winds.api.Winds
import dev.teogor.winds.common.ErrorId
import dev.teogor.winds.gradle.WindsPlugin
import dev.teogor.winds.ktx.getValue
import dev.teogor.winds.ktx.processProjectsWithWinds
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyConstraintHandler
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType

fun Project.configureBomModule(
  publishingOptions: PublishingOptions,
  bomOptions: BomOptions?,
) {
  collectBomConstraints(publishingOptions, bomOptions)
}

private fun bomOptionsError(): Nothing = error(
  """
  Uh-oh! An internal error occurred while handling BoM options.
  Please [create an issue](https://github.com/teogor/winds) to assist in resolving this matter.
  Be sure to include the following error ID in your report to help us identify and address the issue:
  ${ErrorId.BomOptionsError.getErrorIdString()}
  Thank you for your contribution to improving Winds!
  """.trimIndent(),
)

private fun Project.collectBomConstraints(
  publishingOptions: PublishingOptions,
  bomOptions: BomOptions?,
) {
  // Collects the BoM constraints for the project.
  val bomConstraints: DependencyConstraintHandler = dependencies.constraints

  // Retrieves the name of the project.
  val bomProject = this

  getter { module ->
    if (module != bomProject) {
      val modulePublishingOptions: PublishingOptions by this
      // Checks if the subproject can be published.
      if (modulePublishingOptions.publish) {
        // Adds the subproject as a dependency (api) of the BoM.
        moduleMetadata.artifactDescriptor?.let { bomConstraints.api(it.coordinates) }
      }
    }
  }
}

private fun Project.getter(
  block: Winds.(Project) -> Unit,
) {
  rootProject.processProjectsWithWinds(true) {
    plugins.withType<WindsPlugin> {
      if (state.executed) {
        val winds: Winds by extensions
        block(winds, this@processProjectsWithWinds)
      } else {
        afterEvaluate {
          val winds: Winds by extensions
          block(winds, this@processProjectsWithWinds)
        }
      }
    }
  }
}

private fun DependencyConstraintHandler.api(
  constraintNotation: Any,
) = add("api", constraintNotation)
