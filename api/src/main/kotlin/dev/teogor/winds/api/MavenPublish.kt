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

import dev.teogor.winds.api.model.BomOptions
import dev.teogor.winds.api.model.Contributor
import dev.teogor.winds.api.model.Developer
import dev.teogor.winds.api.model.LicenseType
import dev.teogor.winds.api.model.Version
import dev.teogor.winds.api.provider.Scm
import dev.teogor.winds.api.util.ContributorImpl
import dev.teogor.winds.api.util.DeveloperImpl

@Deprecated("Use PublishingOptions")
interface MavenPublish {
  @Deprecated("Use PublishingOptions")
  var displayName: String?

  @Deprecated("Use PublishingOptions")
  var name: String?

  @Deprecated("Use PublishingOptions")
  var enforceUniqueNames: Boolean

  @Deprecated("Use PublishingOptions")
  var description: String?

  @Deprecated("Use PublishingOptions")
  var groupId: String?

  @Deprecated("Use PublishingOptions")
  val artifactId: String?

  @Deprecated("Use PublishingOptions")
  val completeName: String

  @Deprecated("Use PublishingOptions")
  val dependency: String

  @Deprecated("Use PublishingOptions")
  val dependencyBoM: String

  @Deprecated("Use PublishingOptions")
  var artifactIdElements: Int?

  @Deprecated("Use PublishingOptions")
  var url: String?

  @Deprecated("Use PublishingOptions")
  var scmUrl: String?

  @Deprecated("Use PublishingOptions")
  var scmConnection: String?

  @Deprecated("Use PublishingOptions")
  var scmDeveloperConnection: String?

  @Deprecated("Use PublishingOptions")
  var version: Version?

  @Deprecated("Use PublishingOptions")
  var canBePublished: Boolean

  @Deprecated("Use PublishingOptions")
  var contributors: List<Contributor>?

  @Deprecated("Use PublishingOptions")
  var developers: List<Developer>?

  @Deprecated("Use PublishingOptions")
  var licenses: List<LicenseType>?

  @Deprecated("Use PublishingOptions")
  var bomOptions: BomOptions?

  @Deprecated("Use PublishingOptions")
  val isBoM: Boolean

  @Deprecated("Use PublishingOptions")
  var inceptionYear: Int?

  @Deprecated("Use PublishingOptions")
  val scm: Scm

  @Deprecated("Use PublishingOptions")
  val ticketSystem: TicketSystem?

  @Deprecated("Use PublishingOptions")
  fun addContributor(contributor: Contributor)

  @Deprecated("Use PublishingOptions")
  fun addContributors(vararg contributors: Contributor)

  @Deprecated("Use PublishingOptions")
  fun addContributor(init: ContributorImpl.() -> Unit)

  @Deprecated("Use PublishingOptions")
  fun addDeveloper(developer: Developer)

  @Deprecated("Use PublishingOptions")
  fun addDevelopers(vararg developers: Developer)

  @Deprecated("Use PublishingOptions")
  fun addDeveloper(init: DeveloperImpl.() -> Unit)

  @Deprecated("Use PublishingOptions")
  fun addLicense(license: LicenseType)

  @Deprecated("Use PublishingOptions")
  fun defineBoM(init: BomOptions.() -> Unit = {})

  @Deprecated("Use PublishingOptions")
  fun sourceControlManagement(scm: Scm)

  @Deprecated("Use PublishingOptions")
  fun issueManagement(ticketSystem: TicketSystem)
}
