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

package dev.teogor.winds.common.ktx

import dev.teogor.winds.api.Person
import dev.teogor.winds.ktx.filterContributors
import dev.teogor.winds.ktx.filterDevelopers
import org.gradle.api.publish.maven.MavenPom

infix fun <T : Person> List<T>.toContributorsSpec(pom: MavenPom) {
  pom.contributors { mavenPomContributorSpec ->
    filterContributors().forEach { contributor ->
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
}

infix fun <T : Person> List<T>.toDevelopersSpec(pom: MavenPom) {
  pom.developers { mavenPomDeveloperSpec ->
    filterDevelopers().forEach { developer ->
      val developerId = when (developer::class) {
        Person.Developer::class -> (developer as Person.Developer).id
        Person.DeveloperContributor::class -> (developer as Person.DeveloperContributor).id
        else -> null
      }
      mavenPomDeveloperSpec.developer {
        if (developerId != null) {
          it.id.set(developerId)
        }
        it.name.set(developer.name)
        it.email.set(developer.email)
        it.url.set(developer.url)
        it.organization.set(developer.organization)
        it.organizationUrl.set(developer.organizationUrl)
        it.roles.set(developer.roles)
        it.timezone.set(developer.timezone)
        it.properties.set(developer.properties)
      }
    }
  }
}
