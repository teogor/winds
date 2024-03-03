# Module

[//]: # (REGION-DEPENDENCIES)

## Getting Started with Module

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding Module dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-Module-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-Module-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).

### Adding Module Dependencies Manually

To use Module in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorExampleModule = "1.0.0-alpha01"
        
        implementation "dev.teogor.winds.example:module-library-1:$teogorExampleModule"
        implementation "dev.teogor.winds.example:module-library-2:$teogorExampleModule"
        implementation "dev.teogor.winds.example:module-library-4:$teogorExampleModule"
        implementation "dev.teogor.winds.example:module-library-3:$teogorExampleModule"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorExampleModule = "1.0.0-alpha01"
        
        implementation("dev.teogor.winds.example:module-library-1:$teogorExampleModule")
        implementation("dev.teogor.winds.example:module-library-2:$teogorExampleModule")
        implementation("dev.teogor.winds.example:module-library-4:$teogorExampleModule")
        implementation("dev.teogor.winds.example:module-library-3:$teogorExampleModule")
    }
    ```

### Managing Module Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of Module dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-example-module = "1.0.0-alpha01"
    
    [libraries]
    teogor-example-module-library-1 = { group = "dev.teogor.winds.example", name = "module-library-1", version.ref = "teogor-example-module" }
    teogor-example-module-library-2 = { group = "dev.teogor.winds.example", name = "module-library-2", version.ref = "teogor-example-module" }
    teogor-example-module-library-4 = { group = "dev.teogor.winds.example", name = "module-library-4", version.ref = "teogor-example-module" }
    teogor-example-module-library-3 = { group = "dev.teogor.winds.example", name = "module-library-3", version.ref = "teogor-example-module" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-example-module = "1.0.0-alpha01"
    
    [libraries]
    teogor-example-module-library-1 = { module = "dev.teogor.winds.example:module-library-1", version.ref = "teogor-example-module" }
    teogor-example-module-library-2 = { module = "dev.teogor.winds.example:module-library-2", version.ref = "teogor-example-module" }
    teogor-example-module-library-4 = { module = "dev.teogor.winds.example:module-library-4", version.ref = "teogor-example-module" }
    teogor-example-module-library-3 = { module = "dev.teogor.winds.example:module-library-3", version.ref = "teogor-example-module" }
    ```

Then, add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        implementation libs.teogor.example.module.library.1
        implementation libs.teogor.example.module.library.2
        implementation libs.teogor.example.module.library.4
        implementation libs.teogor.example.module.library.3
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(libs.teogor.example.module.library.1)
        implementation(libs.teogor.example.module.library.2)
        implementation(libs.teogor.example.module.library.4)
        implementation(libs.teogor.example.module.library.3)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

