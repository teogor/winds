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

package dev.teogor.winds.common.ktx

import dev.teogor.winds.api.ModuleMetadata
import dev.teogor.winds.common.ErrorId
import org.gradle.api.publish.maven.MavenPom

infix fun ModuleMetadata.toPom(pom: MavenPom) {
  attachMavenData(pom, this)
}

fun attachMavenData(pom: MavenPom, metadata: ModuleMetadata) {
  pom.apply {
    name.set(metadata.artifactDescriptor?.completeName ?: metadata.name)
    description.set(metadata.description)
    inceptionYear.set(metadata.yearCreated.toString())
    url.set(metadata.websiteUrl)

    metadata.persons toContributorsSpec pom

    metadata.persons toDevelopersSpec pom

    metadata toLicensesSpec pom

    scm { mavenPomScm ->
      metadata.scm?.let { scm ->
        mavenPomScm.url.set(scm.repositoryUrl)
        mavenPomScm.connection.set(scm.connection)
        mavenPomScm.developerConnection.set(scm.developerConnection)
      }
    }

    issueManagement { mavenPomIssueManagement ->
      metadata.ticketSystem?.let { ticketSystem ->
        ticketSystem.system.takeIf { it.isNotEmpty() }.let {
          mavenPomIssueManagement.system.set(it)
        }
        ticketSystem.url.takeIf { it.isNotEmpty() }.let {
          mavenPomIssueManagement.url.set(it)
        }
      }
    }
  }
}

private fun licenseError(): Nothing = error(
  """
  Uh-oh! A license must be provided for your module. Please specify the license in the `mavenPublish` block within the `winds` extension.
  If you think this is an error, please [create an issue](https://github.com/teogor/winds) to assist in resolving this matter.
  Be sure to include the following error ID in your report to help us identify and address the issue:
  ${ErrorId.PomLicenseError.getErrorIdString()}
  Thank you for your contribution to improving Winds!
  """.trimIndent(),
)

private fun developerError(): Nothing = error(
  """
  Uh-oh! At least a developer must be provided for your module. Please add developer information in the `mavenPublish` block within the `winds` extension.
  If you think this is an error, please [create an issue](https://github.com/teogor/winds) to assist in resolving this matter.
  Be sure to include the following error ID in your report to help us identify and address the issue:
  ${ErrorId.PomDeveloperError.getErrorIdString()}
  Thank you for your contribution to improving Winds!
  """.trimIndent(),
)

private fun contributorError(): Nothing = error(
  """
  Uh-oh! At least a contributor must be provided for your module. Please add contributor information in the `mavenPublish` block within the `winds` extension.
  If you think this is an error, please [create an issue](https://github.com/teogor/winds) to assist in resolving this matter.
  Be sure to include the following error ID in your report to help us identify and address the issue:
  ${ErrorId.PomContributorError.getErrorIdString()}
  Thank you for your contribution to improving Winds!
  """.trimIndent(),
)
