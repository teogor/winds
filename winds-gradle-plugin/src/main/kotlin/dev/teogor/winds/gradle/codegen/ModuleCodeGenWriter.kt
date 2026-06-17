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

package dev.teogor.winds.gradle.codegen

import dev.teogor.winds.api.Scm
import dev.teogor.winds.api.model.ModuleDescriptor
import dev.teogor.winds.gradle.ktx.indent
import dev.teogor.winds.gradle.utils.asVersionCatalogValue
import dev.teogor.winds.gradle.utils.escapeAlias
import dev.teogor.winds.gradle.utils.isBomLibrary
import dev.teogor.winds.gradle.utils.isLibrary
import dev.teogor.winds.gradle.utils.retrieveLibraryAlias
import org.gradle.configurationcache.extensions.capitalized

class ModuleCodeGenWriter(
  private val module: ModuleDescriptor,
  private val modules: List<ModuleDescriptor>,
) {

  fun getPluginIds(
    applyPlugins: Boolean = false,
    forKotlin: Boolean = true,
  ): String {
    // Filter plugin modules and create pluginId to artifact pairs
    val pluginIds = modules.filter { it.isPlugin }
      .flatMap {
        it.documentationBuilder.pluginIds.toList().map { pluginId -> pluginId to it.artifact }
      }

    // Generate plugin declarations for Groovy or Kotlin with optional apply
    val pluginDeclarations = pluginIds.map { (pluginId, artifact) ->
      when {
        forKotlin -> "id(\"$pluginId\")" + if (applyPlugins) " version \"${artifact.version}\" apply false" else ""
        else -> "id '$pluginId'" + if (applyPlugins) " version '${artifact.version}' apply false" else ""
      }
    }

    return pluginDeclarations.joinToString(separator = "\n|        ")
  }

  fun getPluginIdsToml(
    applyPlugins: Boolean = false,
    forKotlin: Boolean = true,
    scm: Scm?,
    includeOwner: Boolean,
  ): String {
    // Filter plugin modules and create pluginId to artifact pairs
    val pluginIds = modules.filter { it.isPlugin }
      .flatMap {
        it.documentationBuilder.pluginIds.toList().map { pluginId ->
          val pluginAlias = retrieveLibraryAlias(
            library = it,
            pluginId = pluginId,
            includeOwner = includeOwner,
            owner = scm?.owner,
            separator = ".",
          )
          pluginAlias
        }
      }

    // Generate plugin declarations for Groovy or Kotlin with optional apply
    val pluginDeclarations = pluginIds.map { pluginId ->
      when {
        forKotlin -> "alias(libs.plugins.$pluginId)" + if (applyPlugins) " apply false" else ""
        else -> "alias libs.plugins.$pluginId" + if (applyPlugins) " apply false" else ""
      }
    }

    return pluginDeclarations.joinToString(separator = "\n|        ")
  }

  fun getDependencies(
    forKotlin: Boolean,
    moduleAliasVar: String,
  ): String {
    val dependencies = StringBuilder()

    // Version
    dependencies.append(
      "${if (forKotlin) "val" else "def"} $moduleAliasVar = \"${module.artifact.version}\"\n\n",
    )

    modules.filter { it.isLibrary() }.forEach { depsModule ->
      val artifactName = depsModule.artifact.name.capitalized()
      val moduleName = module.name.capitalized()

      when {
        depsModule.documentationBuilder.isCompiler -> {
          dependencies.append("\n")
          dependencies.append(
            if (forKotlin) {
              """
              |// To use Kotlin annotation processing tool (kapt)
              |kapt("${depsModule.artifact.module}:$$moduleAliasVar")
              |// To use Kotlin Symbol Processing (KSP)
              |ksp("${depsModule.artifact.module}:$$moduleAliasVar")
              """.trimMargin()
            } else {
              """
              |// To use Kotlin annotation processing tool (kapt)
              |kapt "${depsModule.artifact.module}:$$moduleAliasVar"
              |// To use Kotlin Symbol Processing (KSP)
              |ksp "${depsModule.artifact.module}:$$moduleAliasVar"
              """.trimMargin()
            },
          )
          dependencies.append("\n")
        }

        else -> {
          if (depsModule.documentationBuilder.isOptional) {
            dependencies.append("\n// optional - $artifactName support for $moduleName\n")
          }
          if (forKotlin) {
            dependencies.append(
              "implementation(\"${depsModule.artifact.module}:\$$moduleAliasVar\")\n",
            )
          } else {
            dependencies.append(
              "implementation \"${depsModule.artifact.module}:\$$moduleAliasVar\"\n",
            )
          }
        }
      }
    }
    return dependencies.indent("|        ").toString()
  }

  fun getDependenciesToml(
    forKotlin: Boolean,
    scm: Scm?,
    includeOwner: Boolean,
    isBom: Boolean = false,
  ): String {
    val dependencies = StringBuilder()

    if (isBom) {
      modules.filter { it.isBomLibrary() }.forEach { depsModule ->
        val artifactName = depsModule.artifact.name.capitalized()
        val moduleName = module.name.capitalized()
        val libraryAlias = if (includeOwner && scm?.owner != null) {
          "${scm.owner} ${depsModule.completeName}"
        } else {
          depsModule.completeName
        }.escapeAlias(".")
        if (forKotlin) {
          dependencies.append(
            "implementation(platform(libs.$libraryAlias))\n",
          )
        } else {
          dependencies.append(
            "implementation platform(libs.$libraryAlias)\n",
          )
        }
      }
    }

    modules.filter { it.isLibrary() && (!isBom || (!it.isBomLibrary())) }.forEach { depsModule ->
      val artifactName = depsModule.artifact.name.capitalized()
      val moduleName = module.name.capitalized()
      val libraryAlias = if (includeOwner && scm?.owner != null) {
        "${scm.owner} ${depsModule.completeName}"
      } else {
        depsModule.completeName
      }.escapeAlias(".")

      when {
        depsModule.documentationBuilder.isCompiler -> {
          dependencies.append("\n")
          dependencies.append(
            if (forKotlin) {
              """
              |// To use Kotlin annotation processing tool (kapt)
              |kapt(libs.$libraryAlias)
              |// To use Kotlin Symbol Processing (KSP)
              |ksp(libs.$libraryAlias)
              """.trimMargin()
            } else {
              """
              |// To use Kotlin annotation processing tool (kapt)
              |kapt libs.$libraryAlias
              |// To use Kotlin Symbol Processing (KSP)
              |ksp libs.$libraryAlias
              """.trimMargin()
            },
          )
          dependencies.append("\n")
        }

        else -> {
          if (depsModule.documentationBuilder.isOptional) {
            dependencies.append("\n// optional - $artifactName support for $moduleName\n")
          }
          if (forKotlin) {
            dependencies.append(
              "implementation(libs.$libraryAlias)\n",
            )
          } else {
            dependencies.append(
              "implementation libs.$libraryAlias\n",
            )
          }
        }
      }
    }
    return dependencies.indent("|        ").toString()
  }

  fun getGradleLibsVersionsToml(
    moduleAlias: String,
    scm: Scm?,
    includeOwner: Boolean,
    asModule: Boolean,
    hasPlugin: Boolean,
    isBom: Boolean = false,
  ): String {
    val versionCatalog = buildString {
      val versionRef = moduleAlias.escapeAlias("-")
      appendLine("```toml title=\"gradle/libs.versions.toml\"")
      appendLine("[versions]")
      appendLine("$versionRef = \"${module.artifact.version}\"")
      appendLine()
      appendLine("[libraries]")
      if (isBom) {
        modules.filter { it.isBomLibrary() }
          .forEach { library ->
            appendLine(
              asVersionCatalogValue(
                library = library,
                versionRef = true,
                versionAlias = versionRef,
                owner = scm?.owner,
                includeOwner = includeOwner,
                asModule = asModule,
              ),
            )
          }
        modules.filterNot { it.isBomLibrary() }
          .forEach { library ->
            appendLine(
              asVersionCatalogValue(
                library = library,
                versionRef = false,
                versionAlias = versionRef,
                owner = scm?.owner,
                includeOwner = includeOwner,
                asModule = asModule,
              ),
            )
          }
      } else {
        modules.filter { it.isLibrary() }
          .forEach { library ->
            appendLine(
              asVersionCatalogValue(
                library = library,
                versionRef = true,
                versionAlias = versionRef,
                owner = scm?.owner,
                includeOwner = includeOwner,
                asModule = asModule,
              ),
            )
          }
      }
      if (hasPlugin) {
        appendLine()
        appendLine("[plugins]")
        modules.filter { it.isPlugin }
          .forEach { library ->
            library.documentationBuilder.pluginIds.forEach { pluginId ->
              appendLine(
                asVersionCatalogValue(
                  library = library,
                  pluginId = pluginId,
                  versionRef = true,
                  versionAlias = versionRef,
                  owner = scm?.owner,
                  includeOwner = includeOwner,
                  asModule = asModule,
                ),
              )
            }
          }
      }
      appendLine("```")

      indent("|    ").toString()
    }

    return versionCatalog
  }

  fun generateDependencyDocumentation(
    scm: Scm?,
    includeOwner: Boolean,
    asModule: Boolean,
    short: Boolean = false,
    isBom: Boolean = false,
    implementationPath: String? = null,
  ): String {
    val owner = scm?.owner
    val moduleAlias = if (includeOwner && owner != null) {
      "$owner ${module.completeName}"
    } else {
      module.completeName
    }.escapeAlias(".")
    val moduleAliasVar = moduleAlias.split(".")
      .let { parts ->
        val firstPart = parts.first().lowercase()
        val remainingParts = parts.drop(1).joinToString(separator = "") { it.capitalized() }
        "$firstPart$remainingParts"
      }

    val groovyBuildGradleDependencies = getDependencies(
      forKotlin = false,
      moduleAliasVar = moduleAliasVar,
    )
    val kotlinBuildGradleDependencies = getDependencies(
      forKotlin = true,
      moduleAliasVar = moduleAliasVar,
    )
    val hasPlugin = modules.any { it.isPlugin }.let {
      val totalDeclaredPlugins = modules.filter { it.isPlugin }
        .sumOf { it.documentationBuilder.pluginIds.size }

      totalDeclaredPlugins != 0
    }

    val versionCatalog = getGradleLibsVersionsToml(
      moduleAlias = moduleAlias,
      scm = scm,
      includeOwner = includeOwner,
      asModule = false,
      hasPlugin = hasPlugin,
      isBom = isBom,
    )
    val versionCatalogWithModules = getGradleLibsVersionsToml(
      moduleAlias = moduleAlias,
      scm = scm,
      includeOwner = includeOwner,
      asModule = true,
      hasPlugin = hasPlugin,
      isBom = isBom,
    )
    val groovyDependenciesVersionCatalog = getDependenciesToml(
      forKotlin = false,
      scm = scm,
      includeOwner = includeOwner,
      isBom = isBom,
    )
    val kotlinDependenciesVersionCatalog = getDependenciesToml(
      forKotlin = true,
      scm = scm,
      includeOwner = includeOwner,
      isBom = isBom,
    )
    val stringBuilder = StringBuilder()
    if (!short) {
      val newlineSeparator = module.documentationBuilder.markdownNewlineSeparator
      stringBuilder.appendLine(
        """
        |## Getting Started with $libraryName
        |
        |**Adding Dependencies:**
        |
        |* **Manual Setup:**  This section guides you through adding $libraryName dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-$libraryNameLowercase-dependencies-manually))
        |* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-$libraryNameLowercase-versions-with-version-catalog-recommended))
        |
        |**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.
        |
        |For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).$newlineSeparator
        |For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).$newlineSeparator
        |For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).$newlineSeparator
        |
        |### Adding $libraryName Dependencies Manually
        |
        """.trimMargin(),
      )
    }
    if (hasPlugin) {
      val pluginIdsGroovyApply = getPluginIds(
        applyPlugins = true,
        forKotlin = false,
      )
      val pluginIdsKotlinApply = getPluginIds(
        applyPlugins = true,
        forKotlin = true,
      )
      stringBuilder.appendLine(
        """
        |First, add the `${module.name.lowercase()}-gradle-plugin` to your project's root `build.gradle` file:
        |
        |=== "Groovy"
        |
        |    ```groovy title="build.gradle"
        |    plugins {
        |        $pluginIdsGroovyApply
        |    }
        |    ```
        |
        |=== "Kotlin"
        |
        |    ```kotlin title="build.gradle.kts"
        |    plugins {
        |        $pluginIdsKotlinApply
        |    }
        |    ```
        |
        """.trimMargin(),
      )
    }

    if (hasPlugin) {
      stringBuilder.appendLine(
        """
        |Then, apply the Gradle plugin and add these dependencies in your app's `build.gradle` file:
        |
        """.trimMargin(),
      )
    } else {
      stringBuilder.appendLine(
        """
        |To use $libraryName in your app, add the following dependencies to your app's `build.gradle` file:
        |
        """.trimMargin(),
      )
    }

    val pluginIdsGroovy = if (hasPlugin) {
      val ids = getPluginIds(
        forKotlin = false,
      )
      """
      |
      |    plugins {
      |        $ids
      |    }
      |
      """.trimMargin()
    } else {
      ""
    }
    val pluginIdsKotlin = if (hasPlugin) {
      val ids = getPluginIds(
        forKotlin = true,
      )
      """
      |
      |    plugins {
      |        $ids
      |    }
      |
      """.trimMargin()
    } else {
      ""
    }

    stringBuilder.appendLine(
      """
      |=== "Groovy"
      |
      |    ```groovy title="build.gradle"$pluginIdsGroovy
      |    dependencies {
      $groovyBuildGradleDependencies
      |    }
      |    ```
      |
      |=== "Kotlin"
      |
      |    ```kotlin title="build.gradle.kts"$pluginIdsKotlin
      |    dependencies {
      $kotlinBuildGradleDependencies
      |    }
      |    ```
      |
      """.trimMargin(),
    )

    if (short) {
      stringBuilder.appendLine(
        """
        |For comprehensive instructions on adding these dependencies, refer to the [$libraryName documentation]($implementationPath#getting-started-with-$libraryNameLowercase).
        """.trimMargin(),
      )
    } else {
      stringBuilder.appendLine(
        """
        |### Managing $libraryName Versions with Version Catalog (Recommended)
        |
        |This section guides you through utilizing a version catalog for centralized management of $libraryName dependencies in your project. This approach simplifies updates and ensures consistency.
        |
        |First, define the dependencies in the `libs.versions.toml` file:
        |
        |- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
        |- **Module Based:** This approach is used for declaring libraries referenced by their module.
        |
        |=== "Group-Name Based"
        |
        $versionCatalog
        |
        |=== "Module Based"
        |
        $versionCatalogWithModules
        |
        """.trimMargin(),
      )

      if (hasPlugin) {
        val pluginIdsGroovyApplyToml = getPluginIdsToml(
          applyPlugins = true,
          forKotlin = false,
          scm = scm,
          includeOwner = includeOwner,
        )
        val pluginIdsKotlinApplyToml = getPluginIdsToml(
          applyPlugins = true,
          forKotlin = true,
          scm = scm,
          includeOwner = includeOwner,
        )
        stringBuilder.appendLine(
          """
          |Then, add the `${module.name.lowercase()}-gradle-plugin` to your project's root `build.gradle` file:
          |
          |=== "Groovy"
          |
          |    ```groovy title="build.gradle"
          |    plugins {
          |        $pluginIdsGroovyApplyToml
          |    }
          |    ```
          |
          |=== "Kotlin"
          |
          |    ```kotlin title="build.gradle.kts"
          |    plugins {
          |        $pluginIdsKotlinApplyToml
          |    }
          |    ```
          |
          """.trimMargin(),
        )
      }

      if (hasPlugin) {
        stringBuilder.appendLine(
          """
          |Lastly, apply the Gradle plugin and add these dependencies in your app's `build.gradle` file:
          |
          """.trimMargin(),
        )
      } else {
        stringBuilder.appendLine(
          """
          |Then, add these dependencies in your app's `build.gradle` file:
          |
          """.trimMargin(),
        )
      }

      val pluginIdsGroovyToml = if (hasPlugin) {
        val ids = getPluginIdsToml(
          forKotlin = false,
          scm = scm,
          includeOwner = includeOwner,
        )
        """
        |
        |    plugins {
        |        $ids
        |    }
        |
        """.trimMargin()
      } else {
        ""
      }
      val pluginIdsKotlinToml = if (hasPlugin) {
        val ids = getPluginIdsToml(
          forKotlin = true,
          scm = scm,
          includeOwner = includeOwner,
        )
        """
        |
        |    plugins {
        |        $ids
        |    }
        |
        """.trimMargin()
      } else {
        ""
      }
      stringBuilder.appendLine(
        """
        |=== "Groovy"
        |
        |    ```groovy title="build.gradle"$pluginIdsGroovyToml
        |    dependencies {
        $groovyDependenciesVersionCatalog
        |    }
        |    ```
        |
        |=== "Kotlin"
        |
        |    ```kotlin title="build.gradle.kts"$pluginIdsKotlinToml
        |    dependencies {
        $kotlinDependenciesVersionCatalog
        |    }
        |    ```
        |
        """.trimMargin(),
      )
    }

    return stringBuilder.toString()
      // Replacing templates
      .replace(libraryName, module.name.capitalized())
      .replace(libraryNameLowercase, module.name.lowercase().escapeAlias("-"))
      .trimEnd()
  }
}
