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
import kotlin.reflect.KProperty

interface Winds {
  var buildFeatures: BuildFeatures

  fun buildFeatures(action: BuildFeatures.() -> Unit)

  var mavenPublish: MavenPublish

  fun mavenPublish(action: MavenPublish.() -> Unit)

  var docsGenerator: DocsGenerator

  fun docsGenerator(action: DocsGenerator.() -> Unit)

  var workflowSynthesizer: WorkflowSynthesizer

  fun workflowSynthesizer(action: WorkflowSynthesizer.() -> Unit)

  infix fun isEnabled(feature: WindsFeature): Boolean {
    return when (feature) {
      WindsFeature.API_VALIDATOR -> false
      WindsFeature.DOCS_GENERATOR -> buildFeatures.docsGenerator
      WindsFeature.DOKKA -> false
      WindsFeature.MAVEN_PUBLISH -> buildFeatures.mavenPublish
      WindsFeature.SPOTLESS -> false
      WindsFeature.WORKFLOW_SYNTHESIZER -> buildFeatures.workflowSynthesizer
    }
  }
}

inline operator fun <reified T> Winds.getValue(thisRef: Nothing?, property: KProperty<*>): T {
  return when (T::class) {
    BuildFeatures::class -> buildFeatures as T
    MavenPublish::class -> mavenPublish as T
    DocsGenerator::class -> docsGenerator as T
    WorkflowSynthesizer::class -> workflowSynthesizer as T
    else -> throw IllegalArgumentException("Unsupported property type: ${property.returnType}")
  }
}
