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

package dev.teogor.winds.gradle.tasks.impl

import dev.teogor.winds.api.DocsGenerator
import dev.teogor.winds.api.WindsLegacy
import dev.teogor.winds.api.getValue
import dev.teogor.winds.api.impl.DocsGeneratorImpl
import dev.teogor.winds.api.model.WindsFeature
import dev.teogor.winds.gradle.utils.collectModulesInfo
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate

fun Project.configureDocsGenerator() {
  collectModulesInfo {
    val windsLegacy: WindsLegacy by extensions
    val docsGeneratorFeatureEnabled = windsLegacy isEnabled WindsFeature.DOCS_GENERATOR
    val docsGenerator: DocsGenerator by windsLegacy
    val docsGeneratorImpl = docsGenerator as DocsGeneratorImpl
    docsGeneratorImpl.task?.isEnabled = docsGeneratorFeatureEnabled
    if (!docsGenerator.excludedModules.contains(path)) {
      if (docsGeneratorFeatureEnabled) {
        (docsGeneratorImpl.task as? DocsGeneratorTaskLegacy)?.addLibrary(it)
      }
    }
  }
}
