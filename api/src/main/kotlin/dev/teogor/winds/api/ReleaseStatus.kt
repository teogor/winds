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

package dev.teogor.winds.api

/**
 * An enum class representing the different release statuses of a library.
 */
enum class ReleaseStatus {

  /**
   * Indicates the library is in an early stage of development and not yet publicly available.
   * Use this status for libraries that are still undergoing significant changes
   * and might not be feature-complete or stable.
   */
  Alpha,

  /**
   * Indicates the library is nearing completion and is undergoing beta testing.
   * Use this status for libraries that are functionally complete but might still
   * contain bugs or have features under development.
   */
  Beta,

  /**
   * Indicates the library is considered stable and suitable for most use cases.
   * Use this status for libraries that are thoroughly tested and meet the expected
   * quality standards for production use. (**Default value**)
   */
  Stable,

  /**
   * Indicates the library is a release candidate, nearing a final release.
   * Use this status for libraries that have gone through extensive testing and
   * are ready for wider release, potentially after addressing any final fixes.
   */
  ReleaseCandidate,

  /**
   * Indicates the library is no longer actively maintained and may have security vulnerabilities
   * or compatibility issues. Use this status for libraries that are still available for use
   * but are not recommended for new projects.
   */
  Deprecated,
}
