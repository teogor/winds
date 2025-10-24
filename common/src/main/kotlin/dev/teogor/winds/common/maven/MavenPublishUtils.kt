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

package dev.teogor.winds.common.maven

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import dev.teogor.winds.api.Winds
import dev.teogor.winds.common.ktx.hasVanniktechMavenPlugin
import dev.teogor.winds.common.ktx.toPom
import dev.teogor.winds.ktx.hasPublishGradlePlugin
import org.gradle.api.Project
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension

fun Project.configureMavenPublishing(
  winds: Winds,
  configureAction: MavenPublishBaseExtension.() -> Unit = {},
) {
  val metadata = winds.moduleMetadata
  val publishing = winds.publishing
  if (publishing.enabled && hasVanniktechMavenPlugin()) {
    val mavenPublishing = extensions.getByType(MavenPublishBaseExtension::class.java)
    mavenPublishing.apply {
      publishToMavenCentral()
      if (publishing.enablePublicationSigning) {
        signAllPublications()
      }

      @Suppress("UnstableApiUsage")
      pom { mavenPom ->
        if (hasPublishGradlePlugin()) {
          extensions.findByType(GradlePluginDevelopmentExtension::class.java)?.let {
            with(it) {
              website.set(metadata.websiteUrl)
              vcsUrl.set(metadata.scm?.repositoryUrl)
            }
          }
        } else {
          metadata.artifactDescriptor?.let { dependencySpec ->
            try {
              coordinates(
                groupId = dependencySpec.group,
                artifactId = dependencySpec.artifactId,
                version = dependencySpec.version.toString(),
              )
            } catch (_: Exception) {
              // TODO better handling
            }
          }
        }

        metadata toPom mavenPom
      }

      // Calling at the end to let the user override values
      configureAction()
    }
  }
}
