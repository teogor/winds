# Sudoklify

[//]: # (REGION-DEPENDENCIES)

## Getting Started with Sudoklify

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding Sudoklify dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-sudoklify-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-sudoklify-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).<br>
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).<br>
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).<br>

### Adding Sudoklify Dependencies Manually

To use Sudoklify in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorSudoklify = "1.0.0-beta02"
        
        implementation "dev.teogor.sudoklify:sudoklify-common:$teogorSudoklify"
        implementation "dev.teogor.sudoklify:sudoklify-core:$teogorSudoklify"
        implementation "dev.teogor.sudoklify:sudoklify-ktx:$teogorSudoklify"
        implementation "dev.teogor.sudoklify:sudoklify-seeds:$teogorSudoklify"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorSudoklify = "1.0.0-beta02"
        
        implementation("dev.teogor.sudoklify:sudoklify-common:$teogorSudoklify")
        implementation("dev.teogor.sudoklify:sudoklify-core:$teogorSudoklify")
        implementation("dev.teogor.sudoklify:sudoklify-ktx:$teogorSudoklify")
        implementation("dev.teogor.sudoklify:sudoklify-seeds:$teogorSudoklify")
    }
    ```

### Managing Sudoklify Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of Sudoklify dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-sudoklify = "1.0.0-beta02"
    
    [libraries]
    teogor-sudoklify-common = { group = "dev.teogor.sudoklify", name = "sudoklify-common", version.ref = "teogor-sudoklify" }
    teogor-sudoklify-core = { group = "dev.teogor.sudoklify", name = "sudoklify-core", version.ref = "teogor-sudoklify" }
    teogor-sudoklify-ktx = { group = "dev.teogor.sudoklify", name = "sudoklify-ktx", version.ref = "teogor-sudoklify" }
    teogor-sudoklify-seeds = { group = "dev.teogor.sudoklify", name = "sudoklify-seeds", version.ref = "teogor-sudoklify" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-sudoklify = "1.0.0-beta02"
    
    [libraries]
    teogor-sudoklify-common = { module = "dev.teogor.sudoklify:sudoklify-common", version.ref = "teogor-sudoklify" }
    teogor-sudoklify-core = { module = "dev.teogor.sudoklify:sudoklify-core", version.ref = "teogor-sudoklify" }
    teogor-sudoklify-ktx = { module = "dev.teogor.sudoklify:sudoklify-ktx", version.ref = "teogor-sudoklify" }
    teogor-sudoklify-seeds = { module = "dev.teogor.sudoklify:sudoklify-seeds", version.ref = "teogor-sudoklify" }
    ```

Then, add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        implementation libs.teogor.sudoklify.common
        implementation libs.teogor.sudoklify.core
        implementation libs.teogor.sudoklify.ktx
        implementation libs.teogor.sudoklify.seeds
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(libs.teogor.sudoklify.common)
        implementation(libs.teogor.sudoklify.core)
        implementation(libs.teogor.sudoklify.ktx)
        implementation(libs.teogor.sudoklify.seeds)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

