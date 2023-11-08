package dev.teogor.winds.gradle.utils

sealed interface ErrorId {
  val errorId: Int

  val errorIdFormatted: String
    get() = "_eW$errorId"

  fun getErrorIdString(): String = "Error ID: $errorIdFormatted"

  object GenericError : ErrorId {
    override val errorId: Int = 1000
  }

  object BomOptionsError : ErrorId {
    override val errorId: Int = 1001
  }

  object BomLibraryError : ErrorId {
    override val errorId: Int = 1002
  }
}
