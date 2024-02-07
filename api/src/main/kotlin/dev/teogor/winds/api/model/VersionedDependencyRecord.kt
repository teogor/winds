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

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Base class for records containing version and date information for
 * versioned dependencies.
 *
 * @property version The version of the dependency.
 * @property date The timestamp representing the date associated with
 * the dependency, in epoch seconds.
 * @property dateFormatted A formatted string representation of the date,
 * in the format "dd MMM yyyy".
 */
@Serializable
abstract class VersionedDependencyRecord(
  open val version: Version,
  open val date: Long,
) {
  /**
   * Provides a formatted string representation of the date.
   */
  val dateFormatted: String
    get() {
      val instant = Instant.ofEpochSecond(date)
      val zoneId = ZoneId.of("UTC")
      val zonedDateTime = instant.atZone(zoneId)
      val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
      return formatter.format(zonedDateTime)
    }
}

/**
 * Represents a record for a Bill of Materials (BOM), containing its version
 * and date.
 *
 * @property version The version of the BOM.
 * @property date The timestamp representing the date associated with the BOM.
 *
 * @see VersionedDependencyRecord
 * @see ModuleDependencyRecord
 */
@Serializable
data class BillOfMaterialsRecord(
  @SerialName("bomVersion") override val version: Version,
  @SerialName("bomDate") override val date: Long,
) : VersionedDependencyRecord(version, date)

/**
 * Represents a record for a specific module dependency, including its Gradle
 * dependency string, version, and date.
 *
 * @property module The Gradle dependency string for the module.
 * @property version The version of the module dependency.
 * @property date The timestamp representing the date associated with the
 * module dependency.
 *
 * @see VersionedDependencyRecord
 * @see BillOfMaterialsRecord
 */
@Serializable
data class ModuleDependencyRecord(
  val module: String,
  @SerialName("moduleVersion") override val version: Version,
  @SerialName("moduleDate") override val date: Long,
) : VersionedDependencyRecord(version, date)
