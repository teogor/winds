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

import java.io.File

enum class RegionTag(val tag: String) {
  ApiReference("API-REFERENCE"),
  BomToLibraryVersionMapping("BOM-TO-LIBRARY-VERSION-MAPPING"),
  BomWithVersionCatalog("BOM-WITH-VERSION-CATALOG"),
  Dependencies("DEPENDENCIES"),
  DifferentLibraryVersionUsage("DIFFERENT-LIBRARY-VERSION-USAGE"),
  Feedback("FEEDBACK"),
  GroupOverview("GROUP-OVERVIEW"),
  GroupVersionOverview("GROUP-VERSION-OVERVIEW"),
  ReleaseTable("RELEASE-TABLE"),
  ReportIssueFeedback("REPORT-ISSUE-FEEDBACK"),
  VersionChangelog("VERSION-CHANGELOG"),
  ;

  override fun toString(): String {
    return "REGION-$tag"
  }

  fun getRegionTag(): String {
    return "[//]: # (REGION-$tag)"
  }
}

fun File.replaceContentsWithinRegion(
  region: RegionTag,
  trimReplacement: Boolean = true,
  additionalReplacements: StringBuilder.() -> Unit = {},
  stringBuilder: StringBuilder.() -> Unit,
) {
  val content = StringBuilder()
  var insideRegion = false

  val preparedReplacement = StringBuilder()
    .apply(stringBuilder)
    .apply(additionalReplacements)
    .let {
      if (trimReplacement) {
        it.trimEnd()
      } else {
        it
      }
    }

  forEachLine { line ->
    if (line.trim() == region.getRegionTag()) {
      insideRegion = !insideRegion
      content.append(line).append("\n")
      if (insideRegion) {
        // Add replacement content here
        content.appendLine()
        content.append(preparedReplacement).append("\n")
        content.appendLine()
      }
    } else if (!insideRegion) {
      // Add the line to the content if outside the region
      content.append(line).append("\n")
    }
  }

  // Write the modified content back to the file
  writeText(content.toString())
}

val bomMdDefault = """
|# Using the Bill of Materials
|
|The BoM Bill of Materials (BOM) lets you manage all of your BoM library versions by
|specifying only the BOM’s version. The BOM itself has links to the stable versions of the different
|BoM libraries, in such a way that they work well together. When using the BOM in your app, you
|don't need to add any version to the BoM library dependencies themselves. When you update the
|BOM version, all the libraries that you're using are automatically updated to their new versions.
|
|To find out which BoM library versions are mapped to a specific BOM version, check out
|the [BOM to library version mapping](bom-mapping.md).
|
|${RegionTag.DifferentLibraryVersionUsage.getRegionTag()}
|
|${RegionTag.DifferentLibraryVersionUsage.getRegionTag()}
|
|### Does the BOM automatically add all the BoM libraries to my app?
|
|No. To actually add and use BoM libraries in your app, you must declare each library as a
|separate dependency line in your module (app-level) Gradle file (usually `app/build.gradle`).
|
|Using the BOM ensures that the versions of any BoM libraries in your app are compatible, but the
|BOM doesn't actually add those BoM libraries to your app.
|
|### Why is the BOM the recommended way to manage BoM library versions?
|
|Going forward, BoM libraries will be versioned independently, which means version numbers will
|start to be incremented at their own pace. The latest stable releases of each library are tested and
|guaranteed to work nicely together. However, finding the latest stable versions of each library can
|be difficult, and the BOM helps you to automatically use these latest versions.
|
|### Am I forced to use the BOM?
|
|No. You can still choose to add each dependency version manually. However, we recommend using the
|BOM as it will make it easier to use all of the latest stable versions at the same time.
|
|${RegionTag.BomWithVersionCatalog.getRegionTag()}
|
|${RegionTag.BomWithVersionCatalog.getRegionTag()}
|
|${RegionTag.ReportIssueFeedback.getRegionTag()}
|
|${RegionTag.ReportIssueFeedback.getRegionTag()}
""".trimMargin()

val bomModuleMd = """
|${RegionTag.ApiReference.getRegionTag()}
|
|${RegionTag.ApiReference.getRegionTag()}
|
|${RegionTag.GroupOverview.getRegionTag()}
|
|${RegionTag.GroupOverview.getRegionTag()}
|
|${RegionTag.GroupVersionOverview.getRegionTag()}
|
|${RegionTag.GroupVersionOverview.getRegionTag()}
|
|${RegionTag.ReportIssueFeedback.getRegionTag()}
|
|${RegionTag.ReportIssueFeedback.getRegionTag()}
|
""".trimMargin()

val indexModuleMarkdownContent = """
# $libraryName

${RegionTag.Dependencies.getRegionTag()}

${RegionTag.Dependencies.getRegionTag()}

""".trimStart()

val bomMappingMdContent = """
# BOM to library version mapping

${RegionTag.BomToLibraryVersionMapping.getRegionTag()}

${RegionTag.BomToLibraryVersionMapping.getRegionTag()}

""".trimStart()

val releaseMarkdownContent = """
${RegionTag.ApiReference.getRegionTag()}

${RegionTag.ApiReference.getRegionTag()}

${RegionTag.ReleaseTable.getRegionTag()}

${RegionTag.ReleaseTable.getRegionTag()}

${RegionTag.Dependencies.getRegionTag()}

${RegionTag.Dependencies.getRegionTag()}

${RegionTag.Feedback.getRegionTag()}

${RegionTag.Feedback.getRegionTag()}

${RegionTag.VersionChangelog.getRegionTag()}

${RegionTag.VersionChangelog.getRegionTag()}

""".trimStart()
