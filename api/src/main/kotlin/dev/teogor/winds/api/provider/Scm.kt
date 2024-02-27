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

package dev.teogor.winds.api.provider

@Deprecated("Use Scm")
interface Scm {

  /**
   * The connection URL of this SCM.
   */
  val connection: String

  /**
   * The developer connection URL of this SCM.
   */
  val developerConnection: String

  /**
   * The browsable repository URL of this SCM.
   */
  val url: String

  data class Git(
    val repo: String,
    val owner: String,
  ) : Scm {
    override val connection: String = "https://github.com/$owner/$repo.git"
    override val developerConnection: String = "ssh://git@github.com/$owner/$repo.git"
    override val url: String = "https://github.com/$owner/$repo"
  }

  data class Bitbucket(
    val repo: String,
    val owner: String,
  ) : Scm {
    override val connection: String = "https://bitbucket.org/$owner/$repo.git"
    override val developerConnection: String = "ssh://git@bitbucket.org/$owner/$repo.git"
    override val url: String = "https://bitbucket.org/$owner/$repo"
  }

  data class SVN(
    val repo: String,
    val owner: String,
    val domain: String,
  ) : Scm {
    override val connection: String = "https://$domain/$owner/$repo/trunk"
    override val developerConnection: String = "svn+ssh://$domain/$owner/$repo/trunk"
    override val url: String = "https://$domain/$owner/$repo"
  }

  data class Local(
    val path: String,
  ) : Scm {
    override val connection: String = "file://$path"
    override val developerConnection: String = "file://$path"
    override val url: String = "file://$path"
  }

  data class Custom(
    val repo: String,
    val owner: String,
    val domain: String,
  ) : Scm {
    override val connection: String = "https://$domain/$owner/$repo"
    override val developerConnection: String = "ssh://$domain/$owner/$repo"
    override val url: String = "https://$domain/$owner/$repo"
  }

  data class CustomExplicit(
    override val connection: String,
    override val developerConnection: String,
    override val url: String,
  ) : Scm
}
