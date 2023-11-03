package dev.teogor.winds.api.impl

import dev.teogor.winds.api.MavenPublish
import dev.teogor.winds.api.model.BomOptions
import dev.teogor.winds.api.model.Developer
import dev.teogor.winds.api.model.DeveloperImpl
import dev.teogor.winds.api.model.LicenseType
import dev.teogor.winds.api.model.Version
import dev.teogor.winds.api.provider.Scm
import java.time.Year

open class MavenPublishImpl : MavenPublish {
  override var displayName: String? = null
    get() = field ?: getter { displayName }

  override var name: String? = null
    get() = field ?: getter { name }

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

  override var licenses: List<LicenseType>? = null
    get() = field ?: getter { licenses }

  override var developers: List<Developer>? = null
    get() = field ?: getter { developers }

  override var bomOptions: BomOptions? = null
    get() = field ?: getter { bomOptions }

  override val isBoM: Boolean
    get() = bomOptions != null

  override var inceptionYear: Int? = null
    get() = field ?: getter { inceptionYear } ?: Year.now().value

  val mavenPublishOptions: MutableList<MavenPublish> = mutableListOf()

  override val artifactId: String?
    get() {
      val names = gets { name }
      return if (get { artifactIdElements } != null) {
        names.takeLast(get { artifactIdElements }!!)
      } else {
        names
      }.joinToString(separator = "-")
    }

  override val dependency: String
    get() = "${get { groupId }}:$artifactId:${get { version }}"

  override val completeName: String
    get() {
      val names = gets { displayName }
      return names.joinToString(separator = " ")
    }

  override val dependencyBoM: String
    get() = "${get { groupId }}:$artifactId"

  var configured: Boolean = false

  override fun addLicense(license: LicenseType) {
    licenses = (licenses ?: emptyList()) + license
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

  override fun setBomOptions(init: BomOptions.() -> Unit) {
    bomOptions = (bomOptions ?: BomOptions()).apply(init)
  }

  private var _scm: Scm? = null
  override val scm: Scm
    get() = _scm ?: error("no SCM provided")
  override fun sourceControlManagement(scm: Scm) {
    _scm = scm
    scmConnection = scm.connection
    scmDeveloperConnection = scm.developerConnection
    scmUrl = scm.url
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
