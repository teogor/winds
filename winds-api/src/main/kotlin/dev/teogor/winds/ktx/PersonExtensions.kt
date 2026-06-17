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

import dev.teogor.winds.api.ModuleMetadata
import dev.teogor.winds.api.Person

inline fun <reified T : Person> ModuleMetadata.person(
  noinline block: T.() -> Unit,
) {
  person(T::class, block)
}

fun <T : Person> List<T>.filterContributors(): List<T> {
  return filter { it.isContributor() }
}

fun <T : Person> List<T>.filterDevelopers(): List<T> {
  return filter { it.isDeveloper() }
}
