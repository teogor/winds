# UI

[//]: # (REGION-DEPENDENCIES)

## Getting Started with UI

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding UI dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-ui-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-ui-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).  
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).  
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).  

### Adding UI Dependencies Manually

To use UI in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorCeresUi = "1.0.0-alpha02"
        
        implementation "dev.teogor.ceres:ui-design-system:$teogorCeresUi"
        implementation "dev.teogor.ceres:ui:$teogorCeresUi"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorCeresUi = "1.0.0-alpha02"
        
        implementation("dev.teogor.ceres:ui-design-system:$teogorCeresUi")
        implementation("dev.teogor.ceres:ui:$teogorCeresUi")
    }
    ```

### Managing UI Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of UI dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-ui = "1.0.0-alpha02"
    
    [libraries]
    teogor-ceres-ui-design-system = { group = "dev.teogor.ceres", name = "ui-design-system", version.ref = "teogor-ceres-ui" }
    teogor-ceres-ui = { group = "dev.teogor.ceres", name = "ui", version.ref = "teogor-ceres-ui" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-ui = "1.0.0-alpha02"
    
    [libraries]
    teogor-ceres-ui-design-system = { module = "dev.teogor.ceres:ui-design-system", version.ref = "teogor-ceres-ui" }
    teogor-ceres-ui = { module = "dev.teogor.ceres:ui", version.ref = "teogor-ceres-ui" }
    ```

Then, add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        implementation libs.teogor.ceres.ui.design.system
        implementation libs.teogor.ceres.ui
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(libs.teogor.ceres.ui.design.system)
        implementation(libs.teogor.ceres.ui)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

