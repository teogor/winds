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
import dev.teogor.winds.api.Winds
import dev.teogor.winds.api.getValue
import dev.teogor.winds.api.impl.DocsGeneratorImpl
import dev.teogor.winds.api.impl.MavenPublishImpl
import dev.teogor.winds.api.model.ModuleInfo
import dev.teogor.winds.api.model.Version
import dev.teogor.winds.api.model.WindsFeature
import dev.teogor.winds.gradle.utils.getAllDependencies
import dev.teogor.winds.gradle.utils.isWindsApplied
import dev.teogor.winds.gradle.utils.lazy
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate

fun Project.configureDocsGenerator() {
  lazy {
    val winds: Winds by extensions
    val docsGeneratorFeatureEnabled = winds isEnabled WindsFeature.DOCS_GENERATOR
    val docsGenerator: DocsGenerator by winds
    val docsGeneratorImpl = docsGenerator as DocsGeneratorImpl
    docsGeneratorImpl.task?.isEnabled = docsGeneratorFeatureEnabled
    if (docsGeneratorFeatureEnabled) {
      subprojects {
        if (docsGenerator.excludedModules.contains(this.path)) {
          return@subprojects
        }
        val projectBase = this
        isWindsApplied(lazyStart = true) {
          lazy {
            val windsBase = projectBase.extensions.getByType<Winds>()
            val mavenPublishFeatureEnabled = windsBase isEnabled WindsFeature.MAVEN_PUBLISH
            val mavenPublish = windsBase.mavenPublish
            val moduleInfo = ModuleInfo(
              completeName = mavenPublish.completeName,
              name = mavenPublish.name ?: "",
              displayName = mavenPublish.displayName ?: "",
              description = mavenPublish.description ?: "",
              groupId = mavenPublish.groupId ?: "",
              artifactId = mavenPublish.artifactId ?: "",
              version = mavenPublish.version ?: Version(0, 0, 0),
              path = path,
              dependencies = getAllDependencies(),
              canBePublished = mavenPublish.canBePublished,
              names = (mavenPublish as MavenPublishImpl).gets { displayName },
            )
            (docsGeneratorImpl.task as? DocsGeneratorTask)?.addLibrary(moduleInfo)
          }
        }
      }
    }
  }
}
