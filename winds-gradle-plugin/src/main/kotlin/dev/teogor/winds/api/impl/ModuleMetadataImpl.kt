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

import dev.teogor.winds.api.ArtifactDescriptor
import dev.teogor.winds.api.BomOptions
import dev.teogor.winds.api.License
import dev.teogor.winds.api.ModuleMetadata
import dev.teogor.winds.api.Person
import dev.teogor.winds.api.Scm
import dev.teogor.winds.api.TicketSystem
import dev.teogor.winds.api.model.Version
import dev.teogor.winds.api.util.InvalidPersonException
import dev.teogor.winds.api.util.InvalidScmConfigurationException
import dev.teogor.winds.gradle.utils.ifNull
import dev.teogor.winds.ktx.validate
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
open class ModuleMetadataImpl : ModuleMetadata {
  override var name: String? = null
  override var description: String? = null
  override var yearCreated: Int = 0
  override var websiteUrl: String? = null
  override var apiDocsUrl: String? = null

  override var isBom: Boolean = false

  private var _bomOptions: BomOptions? = null
  override val bomOptions: BomOptions?
    get() = _bomOptions

  override fun bomOptions(block: BomOptions.() -> Unit) {
    _bomOptions = _bomOptions.ifNull { BomOptions() }.apply(block)
    isBom = true
  }

  private var _artifactDescriptor: ArtifactDescriptor = ArtifactDescriptor("", "", emptyVersion)
  override val artifactDescriptor: ArtifactDescriptor?
    get() = _artifactDescriptor

  override fun artifactDescriptor(block: ArtifactDescriptor.() -> Unit) {
    _artifactDescriptor = _artifactDescriptor.apply(block)
  }

  private var _scm: Scm = Scm.Default
  override val scm: Scm?
    get() = _scm

  override fun <T : Scm> scm(
    type: KClass<T>,
    block: T.() -> Unit,
  ) {
    val scm = Scm.default(type) as? T

    scm ?: throw InvalidScmConfigurationException("Unsupported Scm type: ${type.simpleName}")

    scm.apply(block)
    scm.validate()

    _scm = scm
  }

  private var _ticketSystem: TicketSystem = TicketSystem.Default
  override val ticketSystem: TicketSystem?
    get() = _ticketSystem

  override fun <T : TicketSystem> ticketSystem(
    type: KClass<T>,
    block: T.() -> Unit,
  ) {
    val ticketSystem = TicketSystem.default(type) as? T

    ticketSystem
      ?: throw InvalidScmConfigurationException("Unsupported TicketSystem type: ${type.simpleName}")

    ticketSystem.apply(block)
    ticketSystem.validate()

    _ticketSystem = ticketSystem
  }

  private var _persons: MutableList<Person> = mutableListOf()
  override val persons: List<Person>
    get() = _persons.toList()

  override fun <T : Person> person(
    type: KClass<T>,
    block: T.() -> Unit,
  ) {
    val person = Person.default(type) as? T

    person ?: throw InvalidPersonException("Unsupported Person type: ${type.simpleName}")

    person.apply(block)
    // person.validate()

    _persons.add(person)
  }

  private var _licenses: MutableList<License> = mutableListOf()
  override val licenses: List<License>
    get() = _licenses.toList()

  override fun licenses(block: MutableList<License>.() -> Unit) {
    block(_licenses)
  }

  override fun licenses(vararg licenses: License) {
    _licenses.addAll(licenses)
  }

  override operator fun plusAssign(license: License) {
    _licenses.add(license)
  }

  override fun List<License>.plusAssign(license: License) {
    _licenses.add(license)
  }

  override fun ModuleMetadata.licensedUnder(license: License) {
    _licenses.add(license)
  }

  override fun List<License>.register(license: License) {
    _licenses.add(license)
  }

  override fun copy(fromObj: ModuleMetadata): ModuleMetadata {
    name = fromObj.name
    description = fromObj.description
    yearCreated = fromObj.yearCreated

    fromObj.artifactDescriptor?.let { _artifactDescriptor = it.copy() }
    fromObj.scm?.let { _scm = it }
    fromObj.ticketSystem?.let { _ticketSystem = it }

    _persons.addAll(fromObj.persons)
    _licenses.addAll(fromObj.licenses)

    websiteUrl = fromObj.websiteUrl
    apiDocsUrl = fromObj.apiDocsUrl
    return this
  }
}

private val emptyVersion = Version(0, 0, 0)
