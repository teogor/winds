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

import dev.teogor.winds.api.ReleaseStatus
import kotlinx.serialization.Serializable

/**
 * Represents a version number with optional deprecated and release flag
 * information.
 *
 * This data class represents a semantic version number consisting of major,
 * minor, and patch components.
 * It additionally provides properties for indicating whether the version is
 * deprecated and its release flag (alpha or beta).
 *
 * @param major The major version number.
 * @param minor The minor version number.
 * @param patch The patch version number.
 * @param flag The release flag, indicating whether the version is an alpha or beta
 * release (default: None).
 * @param isDeprecated Whether the version is deprecated (default: false).
 * @param versionQualifier The version qualifier for alpha or beta releases (default: 0).
 * @param versionQualifierPadding The padding for the version qualifier (default: 2).
 *
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
  var flag: ReleaseStatus = ReleaseStatus.Stable,
  var isDeprecated: Boolean = false,
  private var versionQualifier: Int = 0,
  private var versionQualifierPadding: Int = 2,
) : Comparable<Version> {

  /**
   * Checks if the version is an alpha release.
   *
   * @return True if the release flag is `LibraryFlag.Alpha`, false otherwise.
   */
  val isAlphaRelease: Boolean
    get() = flag == ReleaseStatus.Alpha

  /**
   * Checks if the version is a beta release.
   *
   * @return True if the release flag is `LibraryFlag.Beta`, false otherwise.
   */
  val isBetaRelease: Boolean
    get() = flag == ReleaseStatus.Beta

  /**
   * Creates a `VersionBuilder` object initialized with the current properties
   * of this version object.
   *
   * @return A `VersionBuilder` object for modifying and building a new `Version`
   * object.
   */
  fun toBuilder(): VersionBuilder {
    return VersionBuilder()
      .apply {
        this.major = this@Version.major
        this.minor = this@Version.minor
        this.patch = this@Version.patch
        this.flag = this@Version.flag
        this.isDeprecated = this@Version.isDeprecated
        this.versionQualifier = this@Version.versionQualifier
        this.versionQualifierPadding = this@Version.versionQualifierPadding
      }
  }

  /**
   * A builder class for creating `Version` objects.
   *
   * This class provides a fluent and expressive way to configure the properties
   * of a `Version` object.
   * You can use method chaining to set the major, minor, and patch version
   * numbers,
   * as well as the release flag, version qualifier, and deprecated flag.
   * Once you have configured the properties you want, you can call the `build()`
   * method to create a new `Version` object.
   *
   * Example usage:
   *
   * ```kotlin
   * val versionBuilder = VersionBuilder()
   *   .major(1)
   *   .minor(2)
   *   .patch(3)
   *   .alphaRelease(4)
   *   .build()
   *
   * println(versionBuilder) // Output: 1.2.3-alpha04
   * ```
   */
  class VersionBuilder {

    /**
     * The major version number.
     */
    var major: Int = 0

    /**
     * The minor version number.
     */
    var minor: Int = 0

    /**
     * The patch version number.
     */
    var patch: Int = 0

    /**
     * The release flag, indicating whether the version is an alpha or beta release.
     */
    internal var flag: ReleaseStatus = ReleaseStatus.Stable

    /**
     * The version qualifier, used for alpha and beta releases.
     */
    internal var versionQualifier: Int = 0

    /**
     * The padding for the version qualifier.
     */
    internal var versionQualifierPadding: Int = 2

    /**
     * Whether the version is deprecated.
     */
    internal var isDeprecated: Boolean = false

    /**
     * Sets the release flag to alpha and the version qualifier to the provided value.
     *
     * @param version The version qualifier for the alpha release.
     * @return The `VersionBuilder` object, allowing method chaining.
     */
    fun alphaRelease(version: Int) = apply {
      this.versionQualifier = version
      this.flag = ReleaseStatus.Alpha
    }

    /**
     * Sets the release flag to beta and the version qualifier to the provided value.
     *
     * @param version The version qualifier for the beta release.
     * @return The `VersionBuilder` object, allowing method chaining.
     */
    fun betaRelease(version: Int) = apply {
      this.versionQualifier = version
      this.flag = ReleaseStatus.Beta
    }

    /**
     * Sets the version qualifier to the provided value.
     *
     * @param versionQualifier The version qualifier.
     * @return The `VersionBuilder` object, allowing method chaining.
     */
    fun versionQualifier(versionQualifier: Int) = apply {
      this.versionQualifier = versionQualifier
    }

    /**
     * Sets the padding for the version qualifier to the provided value.
     *
     * @param versionQualifierPadding The padding for the version qualifier.
     * @return The `VersionBuilder` object, allowing method chaining.
     */
    fun versionQualifierPadding(versionQualifierPadding: Int) = apply {
      this.versionQualifierPadding = versionQualifierPadding
    }

    /**
     * Marks the version as deprecated.
     *
     * @return The `VersionBuilder` object, allowing method chaining.
     */
    fun setIsDeprecated() = apply {
      this.isDeprecated = true
    }

    /**
     * Builds and returns a new `Version` object with the configured properties.
     *
     * @return A new `Version` object.
     */
    fun build() = Version(
      major = major,
      minor = minor,
      patch = patch,
      flag = flag,
      isDeprecated = isDeprecated,
      versionQualifier = versionQualifier,
      versionQualifierPadding = versionQualifierPadding,
    )
  }

  /**
   * Converts the version object to a string representation.
   *
   * The string representation follows the semantic versioning format
   * (major.minor.patch) with an additional qualifier for alpha or beta
   * releases.
   *
   * For example, "1.0.0" for a stable release, "1.0.0-alpha1" for an alpha
   * release, and "1.0.0-beta2" for a beta release.
   *
   * @return A string representation of the version.
   */
  override fun toString(): String {
    return when (flag) {
      ReleaseStatus.Alpha, ReleaseStatus.Beta -> {
        val versionQualifierString = versionQualifier.toString().padStart(
          versionQualifierPadding,
          '0',
        )
        "$major.$minor.$patch-${flag.name.lowercase()}$versionQualifierString"
      }

      else -> "$major.$minor.$patch"
    }
  }

  override operator fun compareTo(other: Version): Int {
    // 1. Compare major versions:
    val majorDiff = major - other.major
    if (majorDiff != 0) return majorDiff

    // 2. If major versions are equal, compare minor versions:
    val minorDiff = minor - other.minor
    if (minorDiff != 0) return minorDiff

    // 3. If major and minor versions are equal, compare patch versions:
    val patchDiff = patch - other.patch
    if (patchDiff != 0) return patchDiff

    // 4. If all components are equal except flag, compare flags:
    val flagDiff = flag.ordinal - other.flag.ordinal
    if (flagDiff != 0) return -flagDiff

    // 5. If all components except flag and qualifier are equal, compare version qualifiers:
    if (flag == other.flag) {
      val qualifierDiff = versionQualifier - other.versionQualifier
      if (qualifierDiff != 0) return qualifierDiff
    }

    // 6. All components are equal, consider equal
    return 0
  }

  companion object {
    /**
     * Parses a version string into a `Version` object.
     *
     * The provided version string should follow the semantic versioning format
     * (major.minor.patch) with an optional qualifier for alpha or beta releases.
     * For example, "1.0.0", "1.0.0-alpha1", or "1.0.0-beta2".
     *
     * @param versionString The version string to parse.
     *
     * @return A `Version` object representing the parsed version, or null if
     * the provided string is not a valid version format.
     */
    fun from(versionString: String): Version? {
      val regex = Regex("""(\d+)\.(\d+)\.(\d+)(?:-([a-zA-Z]+)(\d+)?)?""")
      val match = regex.matchEntire(versionString) ?: return null

      val (major, minor, patch, preReleaseType, preReleaseNumber) = match.destructured

      return try {
        return VersionBuilder().apply {
          this.major = major.toInt()
          this.minor = minor.toInt()
          this.patch = patch.toInt()
          when (preReleaseType.lowercase()) {
            ReleaseStatus.Alpha.name.lowercase() -> alphaRelease(
              version = preReleaseNumber.toInt(),
            )

            ReleaseStatus.Beta.name.lowercase() -> betaRelease(
              version = preReleaseNumber.toInt(),
            )
          }
        }.build()
      } catch (e: NumberFormatException) {
        null
      }
    }
  }
}
