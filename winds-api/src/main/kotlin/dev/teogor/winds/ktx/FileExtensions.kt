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

package dev.teogor.winds.ktx

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File

/**
 * Creates a directory at the given path relative to this file or a specified root
 * directory.
 *
 * @receiver The starting file or the root directory (optional).
 *
 * @param path The path of the directory to create.
 *
 * @return The created directory.
 */
infix fun File.directory(path: String) = File("$this/$path").also {
  if (!it.exists()) {
    it.mkdirs()
  }
}

/**
 * Creates a file at the given path relative to this file or a specified root
 * directory.
 *
 * @receiver The starting file or the root directory (optional).
 *
 * @param path The path of the file to create.
 *
 * @return The created file.
 */
infix fun File.file(path: String) = File("$this/$path").also {
  if (!it.exists()) {
    it.parentFile.mkdirs()
    it.createNewFile()
  }
}

/**
 * Writes content to a file using a buffered writer.
 *
 * @receiver The file to write to.
 *
 * @param block A lambda function containing the writing logic.
 */
infix fun File.write(block: BufferedWriter.() -> Unit) {
  bufferedWriter().use { it.block() }
}

/**
 * Reads content from a file using a buffered reader.
 *
 * @receiver The file to read from.
 *
 * @param block A lambda function containing the reading logic.
 */
infix fun File.read(block: BufferedReader.() -> Unit) {
  bufferedReader().use { it.block() }
}
