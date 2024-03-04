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

package dev.teogor.winds.common.utils

import dev.teogor.winds.api.model.Version

data class ChangelogEntry(
  val version: Version,
  val previousVersion: Version?,
  val info: String?,
  val content: String?,
  val sections: List<ChangelogSection>?,
)

data class ChangelogSection(
  val type: String,
  val items: List<ChangelogItem>,
)

data class ChangelogItem(
  val message: String,
  val authors: MutableList<String> = mutableListOf(),
  val elements: MutableList<Int> = mutableListOf(),
) {
  init {
    // Extract authors using a regular expression
    val authorMatches = Regex("@(\\S+)").findAll(message)
    authors.addAll(authorMatches.map { it.groupValues[1] })

    // Extract elements using a regular expression
    val elementMatches = Regex("#(\\d+)").findAll(message)
    elements.addAll(elementMatches.map { it.groupValues[1].toInt() })
  }
}

@Suppress("UNCHECKED_CAST")
fun Map<*, *>.decodeAsChangelogs(): List<ChangelogEntry> = mapNotNull {
  val version = Version.from(it.key as String)
  val value = it.value as LinkedHashMap<*, *>
  val content = value["content"] as? String
  val info = value["info"] as? String
  val sections = (value["sections"] as? ArrayList<*>)?.mapNotNull { section ->
    section as? LinkedHashMap<String, List<String>>
  }?.map { section ->
    section.map { sectionEntry ->
      ChangelogSection(
        type = sectionEntry.key,
        items = sectionEntry.value.map { item ->
          ChangelogItem(item)
        },
      )
    }
  }?.flatten()

  if (version == null) {
    null
  } else {
    ChangelogEntry(
      version = version,
      previousVersion = null,
      sections = sections,
      info = info,
      content = content,
    )
  }
}.let {
  it.mapIndexed { index: Int, changelogEntry: ChangelogEntry ->
    val previousVersion = if (index > 0) {
      it[index - 1].version
    } else {
      null
    }
    changelogEntry.copy(
      previousVersion = previousVersion,
    )
  }
}
