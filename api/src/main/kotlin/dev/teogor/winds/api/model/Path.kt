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

import kotlinx.serialization.Serializable
import org.gradle.api.Project
import java.net.MalformedURLException
import java.net.URL

@Serializable
data class Path(
  val value: String,
) {
  fun isRoot(): Boolean {
    return value == "/"
  }

  fun isUrl(): Boolean {
    try {
      URL(value)
      return true
    } catch (e: MalformedURLException) {
      return false
    }
  }

  fun asPartialLocalPath(): String {
    return value.replace(":", "/")
  }

  val absolutePath: String = "@winds://${value.drop(1)}"

  override fun toString(): String {
    return value
  }

  companion object {
    fun from(project: Project): Path {
      return Path(project.path.replace(":", "/"))
    }

    fun from(root: Path, newPath: String): Path {
      return Path(fixPath("$root/$newPath"))
    }

    fun from(root: String, newPath: String): Path {
      return Path(fixPath("$root/$newPath"))
    }

    fun fixPath(path: String): String {
      /* .trimStart('/') */
      val trimmedPath = path // Remove leading slashes
      val protocolIndex = trimmedPath.indexOf("://")
      // Check if there's a protocol (like C://)
      return if (protocolIndex >= 0) {
        // Allow "//" only at the beginning after the protocol
        trimmedPath.replaceRange(
          protocolIndex + 3,
          trimmedPath.length,
          trimmedPath.substring(protocolIndex + 3).replace(Regex("""/+"""), "/"),
        )
      } else {
        // Replace all other occurrences of "//" with "/"
        trimmedPath.replace(Regex("""/+"""), "/")
      }
    }
  }
}
