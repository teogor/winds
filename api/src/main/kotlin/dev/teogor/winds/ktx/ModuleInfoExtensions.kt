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

import dev.teogor.winds.api.model.ModuleInfo

/**
 * Returns the first module in this list that is a BOM (Bill of Materials) module,
 * or null if no BOM module is found.
 *
 * @receiver The list of modules to search for a BOM module.
 * @return The BOM module, or null if not found.
 */
fun MutableList<ModuleInfo>.bom(): ModuleInfo? = firstOrNull {
  it.isBoM
}

/**
 * Sorts this list of modules in ascending order based on their `path` property.
 *
 * @receiver The list of modules to sort.
 * @return The same list, after sorting by path.
 */
fun MutableList<ModuleInfo>.sortByPath(): MutableList<ModuleInfo> = also {
  sortWith(compareBy(ModuleInfo::path))
}
