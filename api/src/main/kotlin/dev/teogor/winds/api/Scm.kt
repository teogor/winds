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

package dev.teogor.winds.api

import kotlin.reflect.KClass

sealed interface Scm {
  val repository: String
  val owner: String
  val connection: String
  val developerConnection: String
  val repositoryUrl: String

  fun hasVersionControl(): Boolean {
    return this !is Local
  }

  data class GitHub(
    override var repository: String,
    override var owner: String,
  ) : Scm {
    override val repositoryUrl: String
      get() = "https://github.com/$owner/$repository"
    override val connection: String
      get() = "https://github.com/$owner/$repository.git"
    override val developerConnection: String
      get() = "ssh://git@github.com/$owner/$repository.git"
  }

  data class GitLab(
    override var repository: String,
    override var owner: String,
    var groupName: String? = null,
  ) : Scm {
    override val repositoryUrl: String
      get() = "https://gitlab.com/${groupName ?: owner}/$repository"
    override val connection: String
      get() = "https://gitlab.com/${groupName ?: owner}/$repository.git"
    override val developerConnection: String
      get() = "ssh://git@gitlab.com/${groupName ?: owner}/$repository.git"
  }

  data class Bitbucket(
    override var repository: String,
    override var owner: String,
    var workspaceName: String? = null,
  ) : Scm {
    override val repositoryUrl: String
      get() =
        "https://bitbucket.org/${workspaceName ?: owner}/$repository"
    override val connection: String
      get() = "https://bitbucket.org/$owner/$repository.git"
    override val developerConnection: String
      get() = "ssh://git@bitbucket.org/$owner/$repository.git"
  }

  data class Local(
    var path: String,
    override var repository: String,
    override var owner: String,
  ) : Scm {
    override val repositoryUrl: String
      get() = "file://$path"
    override val connection: String
      get() = "file://$path"
    override val developerConnection: String
      get() = "file://$path"
  }

  data class Custom(
    override var repository: String,
    override var owner: String,
    var domain: String,
  ) : Scm {
    override val repositoryUrl: String
      get() = "https://$domain/$owner/$repository"
    override val connection: String
      get() = "https://$domain/$owner/$repository"
    override val developerConnection: String
      get() = "ssh://$domain/$owner/$repository"
  }

  data class CustomExplicit(
    override var connection: String,
    override var developerConnection: String,
    override var repository: String,
    override var owner: String,
    override var repositoryUrl: String,
  ) : Scm

  object Default : Scm {
    override var repository: String = ""
    override var owner: String = ""
    override var connection: String = ""
    override var developerConnection: String = ""
    override var repositoryUrl: String = ""
  }

  companion object {
    inline fun <reified T : Scm> default() = default(T::class)

    fun default(type: KClass<out Scm>): Scm? {
      return when (type) {
        GitHub::class -> GitHub("", "")
        GitLab::class -> GitLab("", "", null)
        Bitbucket::class -> Bitbucket("", "", null)
        Local::class -> Local("", "", "")
        Custom::class -> Custom("", "", "")
        CustomExplicit::class -> CustomExplicit(
          "",
          "",
          "",
          "",
          "",
        )

        Default::class -> Default
        else -> null
      }
    }
  }
}
