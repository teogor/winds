# Core

[//]: # (REGION-DEPENDENCIES)

## Getting Started with Core

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding Core dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-core-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-core-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).  
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).  
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).  

### Adding Core Dependencies Manually

To use Core in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorCeresCore = "1.0.0-beta02"
        
        implementation "dev.teogor.ceres:core-common:$teogorCeresCore"
        implementation "dev.teogor.ceres:core-design-system:$teogorCeresCore"
        implementation "dev.teogor.ceres:core-domain:$teogorCeresCore"
        
        // To use Kotlin annotation processing tool (kapt)
        kapt "dev.teogor.ceres:core-ksp:$teogorCeresCore"
        // To use Kotlin Symbol Processing (KSP)
        ksp "dev.teogor.ceres:core-ksp:$teogorCeresCore"
        implementation "dev.teogor.ceres:core-ktx:$teogorCeresCore"
        implementation "dev.teogor.ceres:core-network:$teogorCeresCore"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorCeresCore = "1.0.0-beta02"
        
        implementation("dev.teogor.ceres:core-common:$teogorCeresCore")
        implementation("dev.teogor.ceres:core-design-system:$teogorCeresCore")
        implementation("dev.teogor.ceres:core-domain:$teogorCeresCore")
        
        // To use Kotlin annotation processing tool (kapt)
        kapt("dev.teogor.ceres:core-ksp:$teogorCeresCore")
        // To use Kotlin Symbol Processing (KSP)
        ksp("dev.teogor.ceres:core-ksp:$teogorCeresCore")
        implementation("dev.teogor.ceres:core-ktx:$teogorCeresCore")
        implementation("dev.teogor.ceres:core-network:$teogorCeresCore")
    }
    ```

### Managing Core Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of Core dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-core = "1.0.0-beta02"
    
    [libraries]
    teogor-ceres-core-common = { group = "dev.teogor.ceres", name = "core-common", version.ref = "teogor-ceres-core" }
    teogor-ceres-core-design-system = { group = "dev.teogor.ceres", name = "core-design-system", version.ref = "teogor-ceres-core" }
    teogor-ceres-core-domain = { group = "dev.teogor.ceres", name = "core-domain", version.ref = "teogor-ceres-core" }
    teogor-ceres-core-ksp = { group = "dev.teogor.ceres", name = "core-ksp", version.ref = "teogor-ceres-core" }
    teogor-ceres-core-ktx = { group = "dev.teogor.ceres", name = "core-ktx", version.ref = "teogor-ceres-core" }
    teogor-ceres-core-network = { group = "dev.teogor.ceres", name = "core-network", version.ref = "teogor-ceres-core" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-core = "1.0.0-beta02"
    
    [libraries]
    teogor-ceres-core-common = { module = "dev.teogor.ceres:core-common", version.ref = "teogor-ceres-core" }
    teogor-ceres-core-design-system = { module = "dev.teogor.ceres:core-design-system", version.ref = "teogor-ceres-core" }
    teogor-ceres-core-domain = { module = "dev.teogor.ceres:core-domain", version.ref = "teogor-ceres-core" }
    teogor-ceres-core-ksp = { module = "dev.teogor.ceres:core-ksp", version.ref = "teogor-ceres-core" }
    teogor-ceres-core-ktx = { module = "dev.teogor.ceres:core-ktx", version.ref = "teogor-ceres-core" }
    teogor-ceres-core-network = { module = "dev.teogor.ceres:core-network", version.ref = "teogor-ceres-core" }
    ```

Then, add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        implementation libs.teogor.ceres.core.common
        implementation libs.teogor.ceres.core.design.system
        implementation libs.teogor.ceres.core.domain
        
        // To use Kotlin annotation processing tool (kapt)
        kapt libs.teogor.ceres.core.ksp
        // To use Kotlin Symbol Processing (KSP)
        ksp libs.teogor.ceres.core.ksp
        implementation libs.teogor.ceres.core.ktx
        implementation libs.teogor.ceres.core.network
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(libs.teogor.ceres.core.common)
        implementation(libs.teogor.ceres.core.design.system)
        implementation(libs.teogor.ceres.core.domain)
        
        // To use Kotlin annotation processing tool (kapt)
        kapt(libs.teogor.ceres.core.ksp)
        // To use Kotlin Symbol Processing (KSP)
        ksp(libs.teogor.ceres.core.ksp)
        implementation(libs.teogor.ceres.core.ktx)
        implementation(libs.teogor.ceres.core.network)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

