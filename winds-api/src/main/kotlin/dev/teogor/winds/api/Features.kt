/*
 * Copyright 2024 teogor (Teodor Grigor)
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

package dev.teogor.winds.api

/**
 * Represents features available for projects using the `winds` extension.
 *
 * This interface allows enabling or disabling specific features
 * that are relevant for projects leveraging the `winds` functionality.
 */
interface Features {

  /**
   * Enables or disables Maven publishing for the project.
   *
   * Setting this property to `true` enables Maven publishing,
   * allowing others to easily discover and integrate your project.
   *
   * Defaults to `false`.
   */
  var mavenPublishing: Boolean

  /**
   * Enables or disables the documentation generator for the project.
   *
   * Setting this property to `true` enables the generation of project documentation,
   * which can be helpful for understanding your project and its functionality.
   *
   * Defaults to `false`.
   */
  var docsGenerator: Boolean

  /**
   * Enables or disables the workflow synthesizer for the project.
   *
   * Setting this property to `true` enables the creation of workflows,
   * which can automate tasks and processes within your project.
   *
   * Defaults to `false`.
   */
  var workflowSynthesizer: Boolean
}
