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
import dev.teogor.winds.api.model.Developer
import dev.teogor.winds.api.model.DeveloperImpl
import dev.teogor.winds.api.model.LicenseType
import dev.teogor.winds.api.model.Version
import dev.teogor.winds.api.provider.Scm

interface MavenPublish {
  var displayName: String?
  var name: String?
  var enforceUniqueNames: Boolean
  var description: String?
  var groupId: String?
  val artifactId: String?
  val completeName: String
  val dependency: String
  val dependencyBoM: String
  var artifactIdElements: Int?
  var url: String?
  var scmUrl: String?
  var scmConnection: String?
  var scmDeveloperConnection: String?
  var version: Version?
  var canBePublished: Boolean
  var licenses: List<LicenseType>?
  var developers: List<Developer>?
  var bomOptions: BomOptions?
  val isBoM: Boolean
  var inceptionYear: Int?
  val scm: Scm

  fun addLicense(license: LicenseType)
  fun addDeveloper(developer: Developer)
  fun addDevelopers(vararg developers: Developer)
  fun addDeveloper(init: DeveloperImpl.() -> Unit)
  fun defineBoM(init: BomOptions.() -> Unit = {})
  fun sourceControlManagement(scm: Scm)
}
