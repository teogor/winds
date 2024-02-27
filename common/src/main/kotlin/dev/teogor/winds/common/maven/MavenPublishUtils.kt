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
import org.gradle.api.Project

fun Project.configureMavenPublishing(
  winds: Winds,
  configureAction: MavenPublishBaseExtension.() -> Unit = {},
) {
  val metadata = winds.moduleMetadata
  val publishingOptions = winds.publishingOptions
  if (publishingOptions.publish && hasVanniktechMavenPlugin()) {
    val mavenPublishing = extensions.getByType(MavenPublishBaseExtension::class.java)
    mavenPublishing.apply {
      publishToMavenCentral(publishingOptions.sonatypeHost)
      if (publishingOptions.enablePublicationSigning) {
        signAllPublications()
      }

      @Suppress("UnstableApiUsage")
      pom { mavenPom ->
        metadata.artifactDescriptor?.let { dependencySpec ->
          coordinates(
            groupId = dependencySpec.group,
            artifactId = dependencySpec.artifactId,
            version = dependencySpec.version.toString(),
          )
        }

        metadata toPom mavenPom
      }

      // Calling at the end to let the user override values
      configureAction()
    }
  }
}
