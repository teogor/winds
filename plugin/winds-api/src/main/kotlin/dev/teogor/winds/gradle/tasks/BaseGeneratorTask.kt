package dev.teogor.winds.gradle.tasks

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

abstract class BaseGeneratorTask(
  description: String,
) : DefaultTask() {

  init {
    group = "dev.teogor"
    this.description = description
  }

  @get:Internal
  protected val root = File("")

  infix fun File.directory(path: String): File {
    return if (this == root) {
      directoryImpl(path)
    } else {
      directoryImpl("$this/$path")
    }
  }

  infix fun File.file(path: String): File {
    return if (this == root) {
      fileImpl(path)
    } else {
      fileImpl("$this/$path")
    }
  }

  infix fun File.write(block: BufferedWriter.() -> Unit) {
    bufferedWriter().use { it.block() }
  }

  infix fun File.read(block: BufferedReader.() -> Unit) {
    bufferedReader().use { it.block() }
  }

  @TaskAction
  abstract fun action()

  private fun directoryImpl(path: String): File {
    val file = File(path)
    file.mkdirs()
    return file
  }

  private fun fileImpl(path: String): File {
    val file = File(path)
    if (!file.exists()) {
      // Create parent directories if they don't exist
      file.parentFile.mkdirs()

      // Create a new file
      file.createNewFile()
    }
    return file
  }
}
