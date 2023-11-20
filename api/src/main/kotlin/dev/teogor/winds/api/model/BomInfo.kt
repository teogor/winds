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
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Serializable
data class BomInfo(
  val version: Version,
  val date: Long,
) {
  val dateFormatted: String
    get() {
      val instant = Instant.ofEpochSecond(date)
      val zoneId = ZoneId.of("UTC")
      val zonedDateTime = instant.atZone(zoneId)
      val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
      return formatter.format(zonedDateTime)
    }
}
