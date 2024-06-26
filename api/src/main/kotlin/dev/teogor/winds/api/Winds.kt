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

import dev.teogor.winds.api.model.WindsFeature
import org.gradle.api.Project

interface Winds {
  val allSpecs: MutableList<ArtifactDescriptor>

  var features: Features

  fun features(action: Features.() -> Unit)

  @Suppress("DEPRECATION")
  @Deprecated(
    message = "Use features instead.",
    replaceWith = ReplaceWith(expression = "features"),
  )
  var windsFeatures: WindsFeatures

  @Suppress("DEPRECATION")
  @Deprecated(
    message = "Use features(action) instead.",
    replaceWith = ReplaceWith(expression = "features(action)"),
  )
  fun windsFeatures(action: WindsFeatures.() -> Unit)

  var moduleMetadata: ModuleMetadata

  fun moduleMetadata(action: ModuleMetadata.() -> Unit)

  var publishing: Publishing

  fun publishing(action: Publishing.() -> Unit)

  @Suppress("DEPRECATION")
  @Deprecated(
    message = "Use publishing instead.",
    replaceWith = ReplaceWith(expression = "publishing"),
  )
  var publishingOptions: PublishingOptions

  @Suppress("DEPRECATION")
  @Deprecated(
    message = "Use publishing(action) instead.",
    replaceWith = ReplaceWith(expression = "publishing(action)"),
  )
  fun publishingOptions(action: PublishingOptions.() -> Unit)

  var docsGenerator: DocsGenerator

  fun docsGenerator(action: DocsGenerator.() -> Unit)

  var documentationBuilder: DocumentationBuilder

  fun documentationBuilder(configure: DocumentationBuilder.() -> Unit)

  var workflowSynthesizer: WorkflowSynthesizer

  fun workflowSynthesizer(action: WorkflowSynthesizer.() -> Unit)

  var codebaseOptions: CodebaseOptions

  fun codebaseOptions(configure: CodebaseOptions.() -> Unit)

  fun configureProjects(
    includeRoot: Boolean = true,
    action: Project.(Winds) -> Unit,
  )

  infix fun isEnabled(feature: WindsFeature): Boolean {
    return when (feature) {
      WindsFeature.API_VALIDATOR -> false
      WindsFeature.DOCS_GENERATOR -> features.docsGenerator
      WindsFeature.DOKKA -> false
      WindsFeature.MAVEN_PUBLISH -> features.mavenPublishing
      WindsFeature.SPOTLESS -> false
      WindsFeature.WORKFLOW_SYNTHESIZER -> features.workflowSynthesizer
    }
  }
}
