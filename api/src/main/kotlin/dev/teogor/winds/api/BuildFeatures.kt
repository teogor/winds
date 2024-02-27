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

package dev.teogor.winds.api

// TODO spotless & api-validator & dokka
/**
 * A list of build features that can be disabled or enabled
 * in a project.
 *
 * This list applies to all plugin types.
 */
@Deprecated("Use WindsFeatures")
interface BuildFeatures {

  /**
   * Flag to enable Maven publishing feature.
   *
   * Setting the value to `true` enables Maven publishing.
   *
   * Default value is `false`.
   */
  var mavenPublish: Boolean

  /**
   * Flag to enable documentation generator feature.
   *
   * Setting the value to `true` enables the documentation generator.
   *
   * Default value is `false`.
   */
  var docsGenerator: Boolean

  /**
   * Flag to enable workflow synthesizer feature.
   *
   * Setting the value to `true` enables the workflow synthesizer.
   *
   * Default value is `false`.
   */
  var workflowSynthesizer: Boolean
}
