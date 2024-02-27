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

package dev.teogor.winds.api.model

import dev.teogor.winds.api.ArtifactDescriptor
import dev.teogor.winds.api.DocumentationBuilder
import dev.teogor.winds.api.Scm
import dev.teogor.winds.api.TicketSystem
import kotlinx.serialization.Serializable

@Serializable
data class ModuleDescriptor(
  val name: String,
  val path: Path,
  val artifact: ArtifactDescriptor,
  val dependencies: List<ArtifactDescriptor>,
  val publish: Boolean,
  val isBom: Boolean,
  val completeName: String,
  val description: String?,
  val documentationBuilder: DocumentationBuilder,
  val ticketSystem: TicketSystem?,
  val scm: Scm?,
)
