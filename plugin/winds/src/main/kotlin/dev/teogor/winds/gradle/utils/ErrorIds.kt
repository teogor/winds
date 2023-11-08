package dev.teogor.winds.gradle.utils

sealed interface ErrorId {
  val errorId: Int

  val errorIdFormatted: String
    get() = "_eW$errorId"

  fun getErrorIdString(): String = "Error ID: $errorIdFormatted"

  data class GenericError(
    override val errorId: Int = 1000
  ) : ErrorId

  object BomOptionsError : ErrorId {
    override val errorId: Int = 1001
  }
}
