package dev.teogor.winds.api.model

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlinx.serialization.Serializable

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
