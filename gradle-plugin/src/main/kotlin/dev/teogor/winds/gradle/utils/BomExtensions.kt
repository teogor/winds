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

/**
 * Configures a Bill of Materials (BOM) module using the provided Maven publish options.
 *
 * This function gathers BOM constraints based on the provided `publishOptions`,
 * effectively establishing the dependencies required by the BOM.
 *
 * @param publishOptions The Maven publish options containing information for BOM configuration.
 *
 * @see bomOptionsError
 * @see collectBomConstraints
 */
fun Project.configureBomModule(
  publishOptions: MavenPublish,
) {
  collectBomConstraints(publishOptions)
}

/**
 * Handles internal errors triggered during BOM options processing.
 *
 * This private function throws a custom `Nothing` exception, providing helpful instructions
 * for the user to report the issue and offer additional context for debugging.
 *
 * @throws Nothing : Halts program execution and guides the user to report the issue.
 */
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
 * Collects and configures dependency constraints for the current project's Bill of Materials (BOM).
 *
 * @param publishOptions The Maven publishing configuration options for the project.
 *
 * @throws bomOptionsError If the BOM options are not configured correctly.
 *
 * @see bomOptionsError
 * @see MavenPublish
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

/**
 * Adds an "api" dependency constraint to the current handler.
 *
 * @param constraintNotation The notation or reference specifying the dependency constraint.
 *                           It can be a string in Gradle notation, a project reference,
 *                           or a dependency declaration object.
 *
 * @return This handler instance for chaining calls.
 *
 * @see DependencyConstraintHandler
 * @see DependencyConstraintHandler.add
 */
private fun DependencyConstraintHandler.api(
  constraintNotation: Any,
) = add("api", constraintNotation)
