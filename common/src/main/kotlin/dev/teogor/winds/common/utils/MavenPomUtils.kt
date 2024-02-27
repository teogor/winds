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

package dev.teogor.winds.common.utils

import dev.teogor.winds.api.MavenPublish
import dev.teogor.winds.api.model.Contributor
import dev.teogor.winds.api.model.Developer
import dev.teogor.winds.api.model.LicenseType
import dev.teogor.winds.common.ErrorId
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPomContributorSpec
import org.gradle.api.publish.maven.MavenPomDeveloperSpec
import org.gradle.api.publish.maven.MavenPomLicenseSpec

@Deprecated("")
fun attachMavenData(pom: MavenPom, mavenPublish: MavenPublish) {
  pom.apply {
    name.set(mavenPublish.completeName)
    description.set(mavenPublish.description)
    inceptionYear.set(mavenPublish.inceptionYear.toString())
    url.set(mavenPublish.url)

    contributors {
      mavenPublish.contributors?.toContributorSpec(
        mavenPomContributorSpec = it,
      )
    }

    developers {
      mavenPublish.developers?.toDeveloperSpec(
        mavenPomDeveloperSpec = it,
      ) ?: developerError()
    }

    licenses {
      mavenPublish.licenses?.toLicenseSpec(
        mavenPomLicenseSpec = it,
      ) ?: licenseError()
    }

    scm {
      it.url.set(mavenPublish.scmUrl)
      it.connection.set(mavenPublish.scmConnection)
      it.developerConnection.set(mavenPublish.scmDeveloperConnection)
    }

    issueManagement { mavenPomIssueManagement ->
      mavenPublish.ticketSystem?.system?.let {
        mavenPomIssueManagement.system.set(it)
      }
      mavenPublish.ticketSystem?.url?.let {
        mavenPomIssueManagement.url.set(it)
      }
    }
  }
}

@Deprecated("")
private fun List<Contributor>.toContributorSpec(
  mavenPomContributorSpec: MavenPomContributorSpec,
) {
  forEach { contributor ->
    mavenPomContributorSpec.contributor {
      it.name.set(contributor.name)
      it.email.set(contributor.email)
      it.url.set(contributor.url)
      it.organization.set(contributor.organization)
      it.organizationUrl.set(contributor.organizationUrl)
      it.roles.set(contributor.roles)
      it.timezone.set(contributor.timezone)
      it.properties.set(contributor.properties)
    }
  }
}

@Deprecated("")
private fun List<Developer>.toDeveloperSpec(
  mavenPomDeveloperSpec: MavenPomDeveloperSpec,
) {
  forEach { developer ->
    mavenPomDeveloperSpec.developer {
      it.id.set(developer.id)
      it.name.set(developer.name)
      it.email.set(developer.email)
      it.url.set(developer.url)
      it.roles.set(developer.roles)
      it.timezone.set(developer.timezone)
      it.organization.set(developer.organization)
      it.organizationUrl.set(developer.organizationUrl)
    }
  }
}

@Deprecated("")
private fun List<LicenseType>.toLicenseSpec(
  mavenPomLicenseSpec: MavenPomLicenseSpec,
) {
  forEach { license ->
    mavenPomLicenseSpec.license {
      it.name.set(license.name)
      it.url.set(license.url)
      it.distribution.set(license.distribution)
      // TODO comments.set(license.comments)
    }
  }
}

@Deprecated("")
private fun licenseError(): Nothing = error(
  """
  Uh-oh! A license must be provided for your module. Please specify the license in the `mavenPublish` block within the `winds` extension.
  If you think this is an error, please [create an issue](https://github.com/teogor/winds) to assist in resolving this matter.
  Be sure to include the following error ID in your report to help us identify and address the issue:
  ${ErrorId.PomLicenseError.getErrorIdString()}
  Thank you for your contribution to improving Winds!
  """.trimIndent(),
)

@Deprecated("")
private fun developerError(): Nothing = error(
  """
  Uh-oh! At least a developer must be provided for your module. Please add developer information in the `mavenPublish` block within the `winds` extension.
  If you think this is an error, please [create an issue](https://github.com/teogor/winds) to assist in resolving this matter.
  Be sure to include the following error ID in your report to help us identify and address the issue:
  ${ErrorId.PomDeveloperError.getErrorIdString()}
  Thank you for your contribution to improving Winds!
  """.trimIndent(),
)

@Deprecated("")
private fun contributorError(): Nothing = error(
  """
  Uh-oh! At least a contributor must be provided for your module. Please add contributor information in the `mavenPublish` block within the `winds` extension.
  If you think this is an error, please [create an issue](https://github.com/teogor/winds) to assist in resolving this matter.
  Be sure to include the following error ID in your report to help us identify and address the issue:
  ${ErrorId.PomContributorError.getErrorIdString()}
  Thank you for your contribution to improving Winds!
  """.trimIndent(),
)
