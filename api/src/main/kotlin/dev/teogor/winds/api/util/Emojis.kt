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

package dev.teogor.winds.api.util

/**
 * Typealias for representing text emojis.
 */
typealias Emoji = String

/**
 * Object containing predefined emoji constants for common release stages.
 */
object Emojis {

  /**
   * Emoji representing an alpha release (first test).
   */
  const val ALPHA: Emoji = "\uD83E\uDDEA"

  /**
   * Emoji representing a beta release (second test).
   */
  const val BETA: Emoji = "\uD83D\uDEE0\uFE0F"

  /**
   * Emoji representing a deprecated feature or release.
   */
  const val DEPRECATED: Emoji = "\uD83D\uDEA7"
}
