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

package dev.teogor.winds.ktx

import dev.teogor.winds.api.ModuleMetadata
import dev.teogor.winds.api.TicketSystem

inline fun <reified T : TicketSystem> ModuleMetadata.ticketSystem(
  noinline block: T.() -> Unit,
) {
  ticketSystem(T::class, block)
}

fun TicketSystem.validate(): TicketSystem {
  when (this) {
    is TicketSystem.GitHub -> {
      require(repository.isNotBlank()) { "Repository name cannot be blank" }
      require(owner.isNotBlank()) { "Owner name cannot be blank" }
    }

    is TicketSystem.GitLab -> {
      require(project.isNotBlank()) { "Project name cannot be blank" }
    }

    is TicketSystem.Jira -> {
      require(projectKey.isNotBlank()) { "Project key cannot be blank" }
      // Consider validating serverUrl format if not optional
    }

    is TicketSystem.Bugzilla -> {
      require(product.isNotBlank()) { "Product name cannot be blank" }
      require(serverUrl.isNotBlank()) { "Server URL cannot be blank" }
      // Consider validating URL format
    }

    is TicketSystem.Redmine -> {
      require(projectId.isNotBlank()) { "Project ID cannot be blank" }
      require(baseUrl.isNotBlank()) { "Base URL cannot be blank" }
      // Consider validating URL format
    }

    is TicketSystem.Default -> {
      // Empty fields are allowed for Default
    }
  }
  return this
}
