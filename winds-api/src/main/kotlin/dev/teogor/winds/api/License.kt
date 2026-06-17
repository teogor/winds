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

sealed interface License {
  val name: String
  val url: String
  val comments: String?
  val distribution: Distribution

  data class Apache2(
    override val url: String = "https://www.apache.org/licenses/LICENSE-2.0",
    override var distribution: Distribution = Distribution.Repo,
  ) : License {
    override val name: String = "Apache License 2.0"
    override var comments: String? = null
  }

  data class MIT(
    override val url: String = "https://opensource.org/licenses/MIT",
    override var distribution: Distribution = Distribution.Repo,
  ) : License {
    override val name: String = "MIT License"
    override var comments: String? = null
  }

  data class GPLv3(
    override val url: String = "https://www.gnu.org/licenses/gpl-3.0.en.html",
    override var distribution: Distribution = Distribution.Repo,
  ) : License {
    override val name: String = "GNU General Public License v3.0"
    override var comments: String? = null
  }

  data class Custom(
    override val name: String,
    override val url: String,
    override var distribution: Distribution = Distribution.Repo,
    override var comments: String? = null,
  ) : License

  object None : License {
    override val name: String = ""
    override val url: String = ""
    override val distribution: Distribution = Distribution.Repo
    override var comments: String? = null
  }
}
