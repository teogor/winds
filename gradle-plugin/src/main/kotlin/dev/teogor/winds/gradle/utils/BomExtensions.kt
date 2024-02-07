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

package dev.teogor.winds.gradle.utils

import dev.teogor.winds.api.MavenPublish
import dev.teogor.winds.common.ErrorId
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyConstraintHandler

fun Project.configureBomModule(
  publishOptions: MavenPublish,
) {
  collectBomConstraints(publishOptions)
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

/**
 * Collects the BoM constraints for the project.
 *
 * @param publishOptions The MavenPublish options for the project.
 */
private fun Project.collectBomConstraints(
  publishOptions: MavenPublish,
) {
  // Collects the BoM constraints for the project.
  val bomConstraints: DependencyConstraintHandler = dependencies.constraints
  val bomOptions = publishOptions.bomOptions ?: bomOptionsError()

  // Retrieves the name of the project.
  val bomName = name

  // Iterates over all the subprojects in the project.
  rootProject.afterWindsPluginConfiguration {
    // Checks if the name of the subproject is not equal to the name of the BoM.
    if (name != bomName) {
      // Checks if the subproject can be published.
      if (it.mavenPublish.canBePublished) {
        // Adds the subproject as a dependency (api) of the BoM.
        bomConstraints.api(this)
      }

      afterWindsPluginConfiguration { winds ->
        // Checks if the subproject can be published.
        if (winds.mavenPublish.canBePublished) {
          // Adds the subproject as a dependency (api) of the BoM.
          bomConstraints.api(winds.mavenPublish.dependency)
        }
      }
    }
  }
}

private fun DependencyConstraintHandler.api(
  constraintNotation: Any,
) = add("api", constraintNotation)
