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

package dev.teogor.winds.common

class MarkdownTable(private val columnTitles: List<String>) {
  private fun String.centerPad(targetLength: Int): String {
    val leftPadding = (targetLength - length) / 2
    val rightPadding = targetLength - length - leftPadding
    return StringBuilder().apply {
      append(" ".repeat(leftPadding))
      append(this@centerPad)
      append(" ".repeat(rightPadding))
    }.toString()
  }

  private val maxColumnWidths: List<Int>
    get() = calculateColumnWidths(columnTitles)

  private fun calculateColumnWidths(titles: List<String>): List<Int> {
    return titles.mapIndexed { index, title ->
      val columnValues = rows.map { it.getOrNull(index) ?: "" }
      maxOf(title.length, columnValues.maxOf { it.length }) + 2
    }
  }

  private val rows: MutableList<List<String>> = mutableListOf()

  fun addRows(vararg rows: List<String>) {
    rows.forEach {
      addRow(it)
    }
  }

  fun addRow(row: List<String>) {
    if (row.size != columnTitles.size) {
      throw IllegalArgumentException("Number of values in the row must match the number of columns")
    }
    rows.add(row)
  }

  fun build(): String {
    val formattedTitles = columnTitles.mapIndexed { index, value ->
      value.centerPad(maxColumnWidths[index])
    }

    val separatorLine = columnTitles.mapIndexed { index, value ->
      buildString {
        repeat(maxColumnWidths[index]) {
          append("-")
        }
      }
    }.joinToString(":|:")

    return buildString {
      appendLine("| ${formattedTitles.joinToString(" | ")} |")
      appendLine("|:$separatorLine:|")
      rows.forEach { row ->
        val formattedRow = row.mapIndexed { index, value ->
          value.centerPad(maxColumnWidths[index])
        }
        appendLine("| ${formattedRow.joinToString(" | ")} |")
      }
    }
  }

  companion object {
    fun withTitles(vararg titles: String): MarkdownTable {
      return MarkdownTable(titles.toList())
    }
  }
}
