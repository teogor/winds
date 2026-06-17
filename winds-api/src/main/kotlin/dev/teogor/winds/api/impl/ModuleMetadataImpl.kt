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
import dev.teogor.winds.api.util.InvalidPersonException
import dev.teogor.winds.api.util.InvalidScmConfigurationException
import dev.teogor.winds.ktx.validate
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import javax.inject.Inject
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
open class ModuleMetadataImpl @Inject constructor(
  private val objects: ObjectFactory,
) : ModuleMetadata {
  override val name: Property<String> = objects.property(String::class.java)
  override val description: Property<String> = objects.property(String::class.java)
  override val yearCreated: Property<Int> = objects.property(Int::class.java).convention(0)
  override val websiteUrl: Property<String> = objects.property(String::class.java)
  override val apiDocsUrl: Property<String> = objects.property(String::class.java)

  override val isBom: Property<Boolean> = objects.property(Boolean::class.java).convention(false)

  override val bomOptions: Property<BomOptions> = objects.property(BomOptions::class.java)
    .convention(objects.newInstance(BomOptionsImpl::class.java))

  override fun bomOptions(block: BomOptions.() -> Unit) {
    bomOptions.get().apply(block)
    isBom.set(true)
  }

  override val artifactDescriptor: Property<ArtifactDescriptor> = objects.property(
    ArtifactDescriptor::class.java,
  )
    .convention(objects.newInstance(ArtifactDescriptorImpl::class.java))

  override fun artifactDescriptor(block: ArtifactDescriptor.() -> Unit) {
    artifactDescriptor.get().apply(block)
  }

  override val scm: Property<Scm> = objects.property(Scm::class.java).convention(Scm.Default)

  override fun <T : Scm> scm(
    type: KClass<T>,
    block: T.() -> Unit,
  ) {
    val scmInstance = Scm.default(type) as? T

    scmInstance ?: throw InvalidScmConfigurationException("Unsupported Scm type: ${type.simpleName}")

    scmInstance.apply(block)
    scmInstance.validate()

    scm.set(scmInstance)
  }

  override val ticketSystem: Property<TicketSystem> = objects.property(
    TicketSystem::class.java,
  ).convention(TicketSystem.Default)

  override fun <T : TicketSystem> ticketSystem(
    type: KClass<T>,
    block: T.() -> Unit,
  ) {
    val ticketSystemInstance = TicketSystem.default(type) as? T

    ticketSystemInstance
      ?: throw InvalidScmConfigurationException("Unsupported TicketSystem type: ${type.simpleName}")

    ticketSystemInstance.apply(block)
    ticketSystemInstance.validate()

    ticketSystem.set(ticketSystemInstance)
  }

  override val persons: ListProperty<Person> = objects.listProperty(
    Person::class.java,
  ).convention(emptyList())

  override fun <T : Person> person(
    type: KClass<T>,
    block: T.() -> Unit,
  ) {
    val person = Person.default(type) as? T

    person ?: throw InvalidPersonException("Unsupported Person type: ${type.simpleName}")

    person.apply(block)
    // person.validate()

    persons.add(person)
  }

  override val licenses: ListProperty<License> = objects.listProperty(
    License::class.java,
  ).convention(emptyList())

  override fun licenses(block: MutableList<License>.() -> Unit) {
    val list = licenses.get().toMutableList()
    block(list)
    licenses.set(list)
  }

  override fun licenses(vararg licenses: License) {
    this.licenses.addAll(*licenses)
  }

  override operator fun plusAssign(license: License) {
    licenses.add(license)
  }

  override fun List<License>.plusAssign(license: License) {
    licenses.add(license)
  }

  override fun ModuleMetadata.licensedUnder(license: License) {
    licenses.add(license)
  }

  override fun List<License>.register(license: License) {
    licenses.add(license)
  }

  override fun copy(fromObj: ModuleMetadata): ModuleMetadata {
    name.set(fromObj.name)
    description.set(fromObj.description)
    yearCreated.set(fromObj.yearCreated)

    artifactDescriptor.set(fromObj.artifactDescriptor)
    scm.set(fromObj.scm)
    ticketSystem.set(fromObj.ticketSystem)

    persons.set(fromObj.persons)
    licenses.set(fromObj.licenses)

    websiteUrl.set(fromObj.websiteUrl)
    apiDocsUrl.set(fromObj.apiDocsUrl)
    return this
  }
}
