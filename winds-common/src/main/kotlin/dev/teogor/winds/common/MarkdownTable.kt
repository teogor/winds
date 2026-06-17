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

class MarkdownTable(
  private val columnTitles: List<String>,
  private val columnAlignments: List<Align>,
  private val rows: MutableList<List<String>> = mutableListOf(),
) {
  enum class Align {
    Left,
    Center,
    Right,
  }

  private fun String.centerPad(targetLength: Int): String {
    val leftPadding = (targetLength - length) / 2
    val rightPadding = targetLength - length - leftPadding
    return StringBuilder().apply {
      append(" ".repeat(leftPadding))
      append(this@centerPad)
      append(" ".repeat(rightPadding))
    }.toString()
  }

  private fun String.leftPad(targetLength: Int): String {
    val padding = targetLength - length
    return StringBuilder().apply {
      append(this@leftPad)
      append(" ".repeat(padding))
    }.toString()
  }

  private fun String.rightPad(targetLength: Int): String {
    val padding = targetLength - length
    return StringBuilder().apply {
      append(" ".repeat(padding))
      append(this@rightPad)
    }.toString()
  }

  private val maxColumnWidths: List<Int>
    get() = calculateColumnWidths(columnTitles)

  private fun calculateColumnWidths(titles: List<String>): List<Int> {
    if (rows.isEmpty()) {
      // If no rows are available, return default widths based on title lengths
      return titles.map { it.length + 2 }
    }

    return titles.mapIndexed { index, title ->
      val columnValues = rows.map { it.getOrNull(index) ?: "" }
      maxOf(title.length, columnValues.maxOf { it.length }) + 2
    }
  }

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
      when (columnAlignments[index]) {
        Align.Left -> value.leftPad(maxColumnWidths[index])
        Align.Center -> value.centerPad(maxColumnWidths[index])
        Align.Right -> value.rightPad(maxColumnWidths[index])
      }
    }

    val separatorLine = columnTitles.mapIndexed { index, value ->
      val alignment = when (columnAlignments[index]) {
        Align.Left -> ":"
        Align.Center -> ":"
        Align.Right -> ":"
      }
      buildString {
        append(
          when (columnAlignments[index]) {
            Align.Left -> ":"
            Align.Center -> ":"
            Align.Right -> "-"
          },
        )
        repeat(maxColumnWidths[index]) {
          append("-")
        }
        append(
          when (columnAlignments[index]) {
            Align.Left -> "-"
            Align.Center -> ":"
            Align.Right -> ":"
          },
        )
      }
    }.joinToString("|")

    return buildString {
      appendLine("| ${formattedTitles.joinToString(" | ")} |")
      appendLine("|$separatorLine|")
      rows.forEach { row ->
        val formattedRow = row.mapIndexed { index, value ->
          when (columnAlignments[index]) {
            Align.Left -> value.leftPad(maxColumnWidths[index])
            Align.Center -> value.centerPad(maxColumnWidths[index])
            Align.Right -> value.rightPad(maxColumnWidths[index])
          }
        }
        appendLine("| ${formattedRow.joinToString(" | ")} |")
      }
    }
  }

  class Builder {
    private val columnTitles: MutableList<String> = mutableListOf()
    private val columnAlignments: MutableList<Align> = mutableListOf()
    private val rows: MutableList<List<String>> = mutableListOf()

    fun addColumn(title: String, alignment: Align = Align.Center) {
      columnTitles.add(title)
      columnAlignments.add(alignment)
    }

    fun build(): MarkdownTable {
      return MarkdownTable(columnTitles.toList(), columnAlignments.toList(), rows)
    }

    fun row(
      addValuesAt: Boolean = false,
      init: RowBuilder.() -> Unit,
    ) {
      val rowBuilder = RowBuilder(addValuesAt)
      rowBuilder.init()
      rows.add(rowBuilder.row)
    }

    inner class RowBuilder(private val isAddingAt: Boolean) {
      val row: MutableList<String> = if (isAddingAt) {
        MutableList(columnTitles.size) { "" }
      } else {
        mutableListOf()
      }

      fun addValue(value: String) {
        require(!isAddingAt) { "Cannot add single value when adding values at" }
        row.add(value)
      }

      fun addValues(vararg values: String) {
        require(!isAddingAt) { "Cannot add values when adding value at" }
        row.addAll(values.take(columnTitles.size))
      }

      fun addValueAt(index: Int, value: String) {
        require(isAddingAt) { "Cannot add value at when adding values" }
        require(index in 0 until columnTitles.size) { "Index out of bounds" }
        row[index] = value
      }
    }
  }
}

fun markdownTable(init: MarkdownTable.Builder.() -> Unit): MarkdownTable {
  val builder = MarkdownTable.Builder()
  builder.init()
  return builder.build()
}
