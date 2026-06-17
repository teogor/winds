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

import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import kotlin.reflect.KClass

interface ModuleMetadata {
  val name: Property<String>
  val description: Property<String>
  val yearCreated: Property<Int>

  // region BoM
  val isBom: Property<Boolean>
  val bomOptions: Property<BomOptions>
  fun bomOptions(block: BomOptions.() -> Unit)
  // endregion

  // region ArtifactDescriptor
  val artifactDescriptor: Property<ArtifactDescriptor>
  fun artifactDescriptor(block: ArtifactDescriptor.() -> Unit)
  // endregion

  // region Scm
  val scm: Property<Scm>
  fun <T : Scm> scm(type: KClass<T>, block: T.() -> Unit)
  // endregion

  // region TicketSystem
  val ticketSystem: Property<TicketSystem>
  fun <T : TicketSystem> ticketSystem(type: KClass<T>, block: T.() -> Unit)
  // endregion

  // region TicketSystem
  val persons: ListProperty<Person>
  fun <T : Person> person(type: KClass<T>, block: T.() -> Unit)
  // endregion

  // region Licenses
  val licenses: ListProperty<License>
  fun licenses(block: MutableList<License>.() -> Unit)
  fun licenses(vararg licenses: License)
  operator fun plusAssign(license: License)
  operator fun List<License>.plusAssign(license: License)
  infix fun List<License>.register(license: License)
  infix fun ModuleMetadata.licensedUnder(license: License)
  // endregion

  // region Websites
  val websiteUrl: Property<String>
  val apiDocsUrl: Property<String>
  // endregion

  fun copy(fromObj: ModuleMetadata): ModuleMetadata
}
