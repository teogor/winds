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

package dev.teogor.winds.api.impl

import dev.teogor.winds.api.MavenPublish
import dev.teogor.winds.api.model.BomOptions
import dev.teogor.winds.api.model.Contributor
import dev.teogor.winds.api.model.ContributorImpl
import dev.teogor.winds.api.model.Developer
import dev.teogor.winds.api.model.DeveloperImpl
import dev.teogor.winds.api.model.IssueManagement
import dev.teogor.winds.api.model.LicenseType
import dev.teogor.winds.api.model.Version
import dev.teogor.winds.api.provider.Scm
import java.time.Year

open class MavenPublishImpl : MavenPublish {
  override var displayName: String? = null
    get() = field ?: getter { displayName }

  override var name: String? = null
    get() = field ?: getter { name }

  override var enforceUniqueNames: Boolean = true

  override var description: String? = null
    get() = field ?: getter { description }

  override var groupId: String? = null
    get() = field ?: getter { groupId }

  override var artifactIdElements: Int? = null
    get() = field ?: getter { artifactIdElements }

  override var url: String? = null
    get() = field ?: getter { url }

  override var scmUrl: String? = null
    get() = field ?: getter { scmUrl }

  override var scmConnection: String? = null
    get() = field ?: getter { scmConnection }

  override var scmDeveloperConnection: String? = null
    get() = field ?: getter { scmDeveloperConnection }

  override var version: Version? = null
    get() = field ?: getter { version } ?: Version(0, 0, 0)

  override var canBePublished: Boolean = true

  override var contributors: List<Contributor>? = null
    get() = field ?: getter { contributors }

  override var developers: List<Developer>? = null
    get() = field ?: getter { developers }

  override var licenses: List<LicenseType>? = null
    get() = field ?: getter { licenses }

  override var bomOptions: BomOptions? = null
    get() = field ?: getter { bomOptions }

  override val isBoM: Boolean
    get() = bomOptions != null

  override var inceptionYear: Int? = null
    get() = field ?: getter { inceptionYear } ?: Year.now().value

  val mavenPublishOptions: MutableList<MavenPublish> = mutableListOf()

  override val artifactId: String?
    get() {
      val names = gets { name }.let {
        if (enforceUniqueNames) {
          it.distinct()
        } else {
          it
        }
      }
      return if (get { artifactIdElements } != null) {
        names.takeLast(get { artifactIdElements }!!)
      } else {
        names
      }.joinToString(separator = "-").replace(".", "-")
    }

  override val dependency: String
    get() = "${get { groupId }}:$artifactId:${get { version }}"

  override val completeName: String
    get() {
      val names = gets { displayName }.let {
        if (enforceUniqueNames) {
          it.distinct()
        } else {
          it
        }
      }
      return names.joinToString(separator = " ")
    }

  override val dependencyBoM: String
    get() = "${get { groupId }}:$artifactId"

  var configured: Boolean = false

  private var _scm: Scm? = null
  override val scm: Scm
    get() = _scm ?: error("no SCM provided")

  private var _issueManagement: IssueManagement? = null
  override val issueManagement: IssueManagement?
    get() = _issueManagement

  override fun addContributors(vararg contributors: Contributor) {
    this.contributors = (this.contributors ?: emptyList()) + contributors
  }

  override fun addContributor(init: ContributorImpl.() -> Unit) {
    val contributor = ContributorImpl().apply(init)
    contributors = (contributors ?: emptyList()) + contributor
  }

  override fun addDeveloper(developer: Developer) {
    developers = (developers ?: emptyList()) + developer
  }

  override fun addDevelopers(vararg developers: Developer) {
    this.developers = (this.developers ?: emptyList()) + developers
  }

  override fun addDeveloper(init: DeveloperImpl.() -> Unit) {
    val developer = DeveloperImpl().apply(init)
    developers = (developers ?: emptyList()) + developer
  }

  override fun addLicense(license: LicenseType) {
    licenses = (licenses ?: emptyList()) + license
  }

  override fun addContributor(contributor: Contributor) {
    contributors = (contributors ?: emptyList()) + contributor
  }

  override fun defineBoM(init: BomOptions.() -> Unit) {
    bomOptions = (bomOptions ?: BomOptions()).apply(init)
  }

  override fun sourceControlManagement(scm: Scm) {
    _scm = scm
    scmConnection = scm.connection
    scmDeveloperConnection = scm.developerConnection
    scmUrl = scm.url
  }

  override fun issueManagement(issueManagement: IssueManagement) {
    _issueManagement = issueManagement
  }

  fun <T> getter(selector: MavenPublish.() -> T?): T? {
    return mavenPublishOptions.firstOrNull {
      it.selector() != null
    }?.selector()
  }

  fun <T> get(selector: MavenPublish.() -> T?): T? {
    return this.selector() ?: mavenPublishOptions.firstOrNull {
      it.selector() != null
    }?.selector()
  }

  fun <T> gets(selector: MavenPublish.() -> T?): List<T> {
    val elements = mutableListOf<T>()
    selector()?.let { elements.add(it) }
    mavenPublishOptions.forEach {
      it.selector()?.let { elements.add(it) }
    }
    return elements.reversed()
  }
}
