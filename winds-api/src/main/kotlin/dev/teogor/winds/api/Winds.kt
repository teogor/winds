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
import org.gradle.api.provider.ListProperty

interface Winds {
  val allSpecs: ListProperty<ArtifactDescriptor>

  val features: Features

  fun features(action: Features.() -> Unit)

  val moduleMetadata: ModuleMetadata

  fun moduleMetadata(action: ModuleMetadata.() -> Unit)

  val publishing: Publishing

  fun publishing(action: Publishing.() -> Unit)

  val docsGenerator: DocsGenerator

  fun docsGenerator(action: DocsGenerator.() -> Unit)

  val documentationBuilder: DocumentationBuilder

  fun documentationBuilder(configure: DocumentationBuilder.() -> Unit)

  val workflowSynthesizer: WorkflowSynthesizer

  fun workflowSynthesizer(action: WorkflowSynthesizer.() -> Unit)

  val codebaseOptions: CodebaseOptions

  fun codebaseOptions(configure: CodebaseOptions.() -> Unit)

  fun configureProjects(
    includeRoot: Boolean = true,
    action: Project.(Winds) -> Unit,
  )

  infix fun isEnabled(feature: WindsFeature): Boolean {
    return when (feature) {
      WindsFeature.API_VALIDATOR -> features.apiValidator.getOrElse(false)
      WindsFeature.DOCS_GENERATOR -> features.docsGenerator.getOrElse(false)
      WindsFeature.DOKKA -> features.dokka.getOrElse(false)
      WindsFeature.MAVEN_PUBLISH -> features.mavenPublishing.getOrElse(false)
      WindsFeature.SPOTLESS -> features.spotless.getOrElse(false)
      WindsFeature.WORKFLOW_SYNTHESIZER -> features.workflowSynthesizer.getOrElse(false)
    }
  }
}
