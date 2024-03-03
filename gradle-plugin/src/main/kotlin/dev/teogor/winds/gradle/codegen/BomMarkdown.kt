/*
 * Copyright 2023 teogor (Teodor Grigor)
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

package dev.teogor.winds.gradle.codegen

val implementationBomMarkdownContent = """
[//]: # (This file was automatically generated - do not edit)

# Using the Bill of Materials

The $libraryName Bill of Materials (BOM) lets you manage all of your $libraryName library versions by
specifying only the BOM’s version. The BOM itself has links to the stable versions of the different
$libraryName libraries, in such a way that they work well together. When using the BOM in your app, you
don't need to add any version to the $libraryName library dependencies themselves. When you update the
BOM version, all the libraries that you're using are automatically updated to their new versions.

To find out which $libraryName library versions are mapped to a specific BOM version, check out
the [BOM to library version mapping](bom-mapping.md).

### How do I use a different library version than what's designated in the BOM?

In the `build.gradle` dependencies section, keep the import of the BOM platform. On the library
dependency import, specify the desired version. For example, here's how to declare dependencies if
you want to use an alpha version of Material 3, no matter what version is designated in the BOM:

```groovy
dependencies {
  // Import the $libraryName BOM
  implementation platform('$implementationPlatformBom')

  // Import $importLibraryByVersionName library
  implementation '$importLibraryByVersionCoordinates'

  // Import other $libraryName libraries without version numbers
  // ..
  implementation '$importLibraryCoordinates'
}
```

### Does the BOM automatically add all the $libraryName libraries to my app?

No. To actually add and use $libraryName libraries in your app, you must declare each library as a
separate dependency line in your module (app-level) Gradle file (usually app/build.gradle).

Using the BOM ensures that the versions of any $libraryName libraries in your app are compatible, but the
BOM doesn't actually add those $libraryName libraries to your app.

### Why is the BOM the recommended way to manage $libraryName library versions?

Going forward, $libraryName libraries will be versioned independently, which means version numbers will
start to be incremented at their own pace. The latest stable releases of each library are tested and
guaranteed to work nicely together. However, finding the latest stable versions of each library can
be difficult, and the BOM helps you to automatically use these latest versions.

### Am I forced to use the BOM?

No. You can still choose to add each dependency version manually. However, we recommend using the
BOM as it will make it easier to use all of the latest stable versions at the same time.

### Does the BOM work with version catalogs?

Yes. You can include the BOM itself in the version catalog, and omit the other $libraryName library versions:

```toml
[libraries]
$bomVersionCatalogLibraries
```

Don’t forget to import the BOM in your module’s `build.gradle`:

```groovy
$dependenciesBomVersionCatalog
```

### How do I report an issue or offer feedback on the BOM?

You can file issues on our [issue tracker 🔗]($issueTrackerLink).
""".trimStart()
