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

package dev.teogor.winds.gradle.ktx

fun StringBuilder.indent(indent: String): StringBuilder {
  val lines = toString().lines().dropLast(1)
  clear()
  append(lines.joinToString("\n") { indent + it })
  return this
}

fun StringBuilder.replace(
  oldValue: String,
  stringBuilder: StringBuilder.() -> Unit,
): StringBuilder {
  return replace(
    oldValue = oldValue,
    newValue = StringBuilder().apply(stringBuilder).toString(),
  )
}

fun StringBuilder.replace(oldValue: String, newValue: String): StringBuilder {
  var startIndex = 0
  var index = indexOf(oldValue, startIndex)
  while (index != -1) {
    replace(index, index + oldValue.length, newValue)
    startIndex = index + newValue.length
    index = indexOf(oldValue, startIndex)
  }
  return this
}
