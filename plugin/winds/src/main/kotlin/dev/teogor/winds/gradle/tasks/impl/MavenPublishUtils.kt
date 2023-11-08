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

import dev.teogor.winds.api.MavenPublish
import dev.teogor.winds.api.Winds
import dev.teogor.winds.api.getValue
import dev.teogor.winds.api.impl.MavenPublishImpl
import dev.teogor.winds.api.model.WindsFeature
import dev.teogor.winds.gradle.utils.configureBomModule
import dev.teogor.winds.gradle.utils.hasKotlinDslPlugin
import dev.teogor.winds.gradle.utils.hasPublishPlugin
import dev.teogor.winds.gradle.utils.isWindsApplied
import dev.teogor.winds.gradle.utils.lazy
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate

fun Project.configureMavenPublish() {
  val base = this
  lazy {
    val winds: Winds by extensions
    val mavenPublishFeatureEnabled = winds isEnabled WindsFeature.MAVEN_PUBLISH
    if (mavenPublishFeatureEnabled) {
      val maven: MavenPublish by winds
      if (maven.isBoM) {
        if (!hasPublishPlugin()) {
          pluginManager.apply("java-platform")
        }
        pluginManager.apply("com.vanniktech.maven.publish")
        configureBomModule(maven)
      } else if (maven.canBePublished) {
        if (hasKotlinDslPlugin()) {
          pluginManager.apply("java-library")
          pluginManager.apply("com.vanniktech.maven.publish")
        } else if (hasPublishPlugin()) {
          pluginManager.apply("com.vanniktech.maven.publish")
        } else {
          maven.canBePublished = false
        }
      }

      subprojects {
        if (parent == base) {
          isWindsApplied {
            buildFeatures.mavenPublish = winds.buildFeatures.mavenPublish
            (mavenPublish as MavenPublishImpl).let { mavenPublishImpl ->
              mavenPublishImpl.mavenPublishOptions.apply {
                add(winds.mavenPublish)
                addAll((winds.mavenPublish as MavenPublishImpl).mavenPublishOptions)
              }
            }
          }
        }
      }
    }
  }
}
