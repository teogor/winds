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
  fun setBomOptions(init: BomOptions.() -> Unit)
  fun sourceControlManagement(scm: Scm)
}
