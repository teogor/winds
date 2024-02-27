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

import org.gradle.api.Incubating

// TODO values handling
data class BomOptions(
  /**
   * This allows you to define a list of Maven scopes (e.g., compile, test)to exclude from the
   * BOM dependencies.
   */
  @Incubating
  var excludedScopes: List<String> = emptyList(),
  @Incubating
  var acceptedModules: List<String> = emptyList(),
  @Incubating
  var acceptedPaths: List<String> = emptyList(),
)
