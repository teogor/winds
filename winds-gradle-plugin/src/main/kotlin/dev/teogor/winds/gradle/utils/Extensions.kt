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

package dev.teogor.winds.gradle.utils

import dev.teogor.winds.api.model.ModuleDescriptor

inline fun <T> T?.ifNull(defaultValue: () -> T): T = this ?: defaultValue()

fun String.escapeAlias(separator: String): String {
  val regex = Regex("""\h|-|\.|_|,""")
  val str = this.replace(Regex("\\s+"), " ")
  return regex.replace(str, separator).lowercase()
}

fun retrieveLibraryAlias(
  library: ModuleDescriptor,
  pluginId: String?,
  includeOwner: Boolean,
  owner: String?,
  separator: String = "-",
): String {
  val names: List<String> = library.artifact.artifactNames.distinct()
  val pluginIds: List<String> = pluginId?.split(".") ?: emptyList()
  val commonWord = pluginIds.firstOrNull { it in names }

  val commonIndex = pluginIds.indexOf(commonWord)
  val elementsAfterCommonWord = if (commonIndex != -1 && commonIndex + 1 < pluginIds.size) {
    pluginIds.subList(commonIndex + 1, pluginIds.size).joinToString(" ")
  } else {
    ""
  }

  val droppedNames = if (commonWord != null) {
    names.subList(0, names.indexOf(commonWord) + 1).joinToString(" ")
  } else {
    names.joinToString(" ")
  }

  return buildString {
    if (owner != null && includeOwner) {
      append(owner)
      if (droppedNames.isNotEmpty()) {
        append(" $droppedNames")
      }
      if (elementsAfterCommonWord.isNotEmpty()) {
        append(" $elementsAfterCommonWord")
      }
    } else {
      append(droppedNames)
      if (elementsAfterCommonWord.isNotEmpty()) {
        append(" $elementsAfterCommonWord")
      }
    }
  }.escapeAlias(separator)
}

fun asVersionCatalogValue(
  library: ModuleDescriptor,
  versionRef: Boolean,
  owner: String?,
  pluginId: String? = null,
  includeOwner: Boolean = true,
  asModule: Boolean = false,
  versionAlias: String = if (includeOwner && owner != null) {
    "$owner ${library.completeName}"
  } else {
    library.completeName
  }.escapeAlias("-"),
): String {
  val libraryAlias = retrieveLibraryAlias(
    library = library,
    pluginId = pluginId,
    includeOwner = includeOwner,
    owner = owner,
    separator = "-",
  )
  val components = mutableListOf<String>()
  if (pluginId != null) {
    components.add("id = \"${pluginId}\"")
  } else if (asModule) {
    components.add("module = \"${library.artifact.module}\"")
  } else {
    components.add("group = \"${library.artifact.group}\"")
    components.add("name = \"${library.artifact.artifactId}\"")
  }
  if (versionRef) {
    components.add("version.ref = \"$versionAlias\"")
  }
  return "$libraryAlias = { ${components.joinToString(separator = ", ")} }"
}

fun ModuleDescriptor.isBomLibrary(): Boolean {
  return isBom
}

fun ModuleDescriptor.isLibrary(): Boolean {
  return !isPlugin
}

fun String.appendTo(writer: StringBuilder) {
  writer.appendLine(this)
  writer.appendLine()
}

inline fun StringBuilder.appendContent(crossinline content: StringBuilder.() -> Unit) {
  content()
  appendLine()
}
