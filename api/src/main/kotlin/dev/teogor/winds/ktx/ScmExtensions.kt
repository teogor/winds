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
import dev.teogor.winds.api.Scm

inline fun <reified T : Scm> ModuleMetadata.scm(
  noinline block: T.() -> Unit,
) {
  scm(T::class, block)
}

fun Scm.validate(): Scm {
  when (this) {
    is Scm.GitHub, is Scm.GitLab, is Scm.Bitbucket -> {
      require(repository.isNotBlank()) { "Repository name cannot be blank" }
      require(owner.isNotBlank()) { "Owner name cannot be blank" }
    }

    is Scm.Local -> {
      require(path.isNotBlank()) { "Local path cannot be blank" }
    }

    is Scm.Custom -> {
      require(repository.isNotBlank()) { "Repository name cannot be blank" }
      require(owner.isNotBlank()) { "Owner name cannot be blank" }
      require(domain.isNotBlank()) { "Domain cannot be blank" }
      // Consider validating URL format for custom connections
    }

    is Scm.CustomExplicit -> {
      require(repositoryUrl.isNotBlank()) { "Repository URL cannot be blank" }
      require(connection.isNotBlank()) { "Connection URL cannot be blank" }
      require(developerConnection.isNotBlank()) { "Developer connection URL cannot be blank" }
      // Consider validating URL formats
    }

    is Scm.Default -> {
      // Empty fields are allowed for Default
    }
  }
  return this
}
