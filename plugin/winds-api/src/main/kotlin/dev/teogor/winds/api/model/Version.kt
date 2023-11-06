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

package dev.teogor.winds.api.model

import kotlinx.serialization.Serializable

/**
 * TODO Create an interface called Version that can be used to represent
 *  different types of versions, such as date versions and number versions
 *  - DateVersion : Version
 *    Example: DateVersion(2023, 11, 02)
 *  - NumericVersion : Version
 *    Example: NumericVersion(10, 3, 4)
 *
 *
 * TODO: Create an interface called Version that can be used to represent
 *  different types of versions, such as date versions and numeric versions.
 *
 * Tasks:
 *  - Create an interface called Version.
 *  - Create a class called DateVersion that implements the Version interface.
 *  - Add the following properties to the DateVersion class:
 *      - year: The year of the version.
 *      - month: The month of the version.
 *      - day: The day of the version.
 *  - Create a class called NumericVersion that implements the Version interface.
 *  - Add the following properties to the NumericVersion class:
 *      - major: The major version number.
 *      - minor: The minor version number.
 *      - patch: The patch version number.
 *  - Update the interface to reference the new DateVersion and NumericVersion classes.
 *
 *  Examples:
 *  // Example of a date version
 *  val dateVersion = DateVersion(2023, 11, 2)
 *  // Example of a number version
 *  val numericVersion = NumericVersion(10, 3, 4)
 */
@Serializable
data class Version(
  val major: Int,
  val minor: Int,
  val patch: Int,
) {
  var flag: LibraryFlag = LibraryFlag.None
    private set

  private var versionQualifier: Int = 0

  private var versionQualifierPadding: Int = 2

  val isDeprecated: Boolean
    get() = flag == LibraryFlag.Deprecated

  val isAlphaRelease: Boolean
    get() = flag == LibraryFlag.Alpha

  val isBetaRelease: Boolean
    get() = flag == LibraryFlag.Beta

  fun markAsDeprecated() = apply {
    flag = LibraryFlag.Deprecated
  }

  fun setAlphaRelease(version: Int) = apply {
    flag = LibraryFlag.Alpha
    versionQualifier = version
  }

  fun setBetaRelease(version: Int) = apply {
    flag = LibraryFlag.Beta
    versionQualifier = version
  }

  fun setVersionQualifierPadding(padding: Int) = apply {
    versionQualifierPadding = padding
  }

  override fun toString(): String {
    return when (flag) {
      LibraryFlag.Alpha, LibraryFlag.Beta -> {
        val versionQualifierString = versionQualifier.toString().padStart(
          versionQualifierPadding,
          '0',
        )
        "$major.$minor.$patch-${flag.name.lowercase()}$versionQualifierString"
      }
      else -> "$major.$minor.$patch"
    }
  }

  companion object {
    fun from(versionString: String): Version? {
      val parts = versionString.split(".")
      if (parts.size != 3) {
        return null
      }

      return try {
        val major = parts[0].toInt()
        val minor = parts[1].toInt()
        val patch = parts[2].toInt()
        Version(major, minor, patch)
      } catch (e: NumberFormatException) {
        null
      }
    }
  }
}
