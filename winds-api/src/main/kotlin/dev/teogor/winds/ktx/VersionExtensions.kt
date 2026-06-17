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

import dev.teogor.winds.api.ReleaseStatus
import dev.teogor.winds.api.model.Version
import dev.teogor.winds.api.util.Emojis

/**
 * Creates a new `Version` object with the specified major, minor,
 * and patch version numbers, and applies the provided configuration
 * block.
 *
 * @param major The major version number.
 * @param minor The minor version number.
 * @param patch The patch version number.
 * @param block An optional configuration block to apply to the newly
 * created `Version` object. If not provided, the default values will
 * be used.
 *
 * @return A new `Version` object with the specified version numbers
 * and applied configuration.
 */
fun createVersion(
  major: Int,
  minor: Int,
  patch: Int,
  block: Version.VersionBuilder.() -> Unit = {},
): Version {
  return Version.VersionBuilder()
    .apply {
      this.major = major
      this.minor = minor
      this.patch = patch
    }
    .apply(block)
    .build()
}

/**
 * Creates a new `Version` object using a fluent builder pattern.
 *
 * @param block A configuration block to apply to the newly created
 * `Version` object.
 *
 * @return A new `Version` object with the configured properties.
 */
fun versionOf(
  block: Version.VersionBuilder.() -> Unit,
) = Version.VersionBuilder().apply(block).build()

fun Version.getEmoji(): String? {
  if (isDeprecated) {
    return Emojis.DEPRECATED
  }
  return when (flag) {
    ReleaseStatus.Beta -> {
      Emojis.BETA
    }

    ReleaseStatus.Alpha -> {
      Emojis.ALPHA
    }

    else -> null
  }
}

fun Version.getMajorMinorVersion() = "$major.$minor"

fun Version.getMajorMinorPatchVersion() = "$major.$minor.$patch"
