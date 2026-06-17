# BoM

[//]: # (REGION-DEPENDENCIES)

## Getting Started with BoM

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding BoM dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-bom-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-bom-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).  
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).  
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).  

### Adding BoM Dependencies Manually

To use BoM in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorCeresBom = "1.0.0-alpha05"
        
        implementation "dev.teogor.ceres:bom:$teogorCeresBom"
        implementation "dev.teogor.ceres:core-common:$teogorCeresBom"
        implementation "dev.teogor.ceres:core-design-system:$teogorCeresBom"
        implementation "dev.teogor.ceres:core-domain:$teogorCeresBom"
        
        // To use Kotlin annotation processing tool (kapt)
        kapt "dev.teogor.ceres:core-ksp:$teogorCeresBom"
        // To use Kotlin Symbol Processing (KSP)
        ksp "dev.teogor.ceres:core-ksp:$teogorCeresBom"
        implementation "dev.teogor.ceres:core-ktx:$teogorCeresBom"
        implementation "dev.teogor.ceres:core-network:$teogorCeresBom"
        implementation "dev.teogor.ceres:data-database:$teogorCeresBom"
        implementation "dev.teogor.ceres:data-datastore:$teogorCeresBom"
        implementation "dev.teogor.ceres:ui-design-system:$teogorCeresBom"
        implementation "dev.teogor.ceres:ui:$teogorCeresBom"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorCeresBom = "1.0.0-alpha05"
        
        implementation("dev.teogor.ceres:bom:$teogorCeresBom")
        implementation("dev.teogor.ceres:core-common:$teogorCeresBom")
        implementation("dev.teogor.ceres:core-design-system:$teogorCeresBom")
        implementation("dev.teogor.ceres:core-domain:$teogorCeresBom")
        
        // To use Kotlin annotation processing tool (kapt)
        kapt("dev.teogor.ceres:core-ksp:$teogorCeresBom")
        // To use Kotlin Symbol Processing (KSP)
        ksp("dev.teogor.ceres:core-ksp:$teogorCeresBom")
        implementation("dev.teogor.ceres:core-ktx:$teogorCeresBom")
        implementation("dev.teogor.ceres:core-network:$teogorCeresBom")
        implementation("dev.teogor.ceres:data-database:$teogorCeresBom")
        implementation("dev.teogor.ceres:data-datastore:$teogorCeresBom")
        implementation("dev.teogor.ceres:ui-design-system:$teogorCeresBom")
        implementation("dev.teogor.ceres:ui:$teogorCeresBom")
    }
    ```

### Managing BoM Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of BoM dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-bom = "1.0.0-alpha05"
    
    [libraries]
    teogor-ceres-bom = { group = "dev.teogor.ceres", name = "bom", version.ref = "teogor-ceres-bom" }
    teogor-ceres-core-common = { group = "dev.teogor.ceres", name = "core-common" }
    teogor-ceres-core-design-system = { group = "dev.teogor.ceres", name = "core-design-system" }
    teogor-ceres-core-domain = { group = "dev.teogor.ceres", name = "core-domain" }
    teogor-ceres-core-ksp = { group = "dev.teogor.ceres", name = "core-ksp" }
    teogor-ceres-core-ktx = { group = "dev.teogor.ceres", name = "core-ktx" }
    teogor-ceres-core-network = { group = "dev.teogor.ceres", name = "core-network" }
    teogor-ceres-data-database = { group = "dev.teogor.ceres", name = "data-database" }
    teogor-ceres-data-datastore = { group = "dev.teogor.ceres", name = "data-datastore" }
    teogor-ceres-ui-design-system = { group = "dev.teogor.ceres", name = "ui-design-system" }
    teogor-ceres-ui = { group = "dev.teogor.ceres", name = "ui" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-bom = "1.0.0-alpha05"
    
    [libraries]
    teogor-ceres-bom = { module = "dev.teogor.ceres:bom", version.ref = "teogor-ceres-bom" }
    teogor-ceres-core-common = { module = "dev.teogor.ceres:core-common" }
    teogor-ceres-core-design-system = { module = "dev.teogor.ceres:core-design-system" }
    teogor-ceres-core-domain = { module = "dev.teogor.ceres:core-domain" }
    teogor-ceres-core-ksp = { module = "dev.teogor.ceres:core-ksp" }
    teogor-ceres-core-ktx = { module = "dev.teogor.ceres:core-ktx" }
    teogor-ceres-core-network = { module = "dev.teogor.ceres:core-network" }
    teogor-ceres-data-database = { module = "dev.teogor.ceres:data-database" }
    teogor-ceres-data-datastore = { module = "dev.teogor.ceres:data-datastore" }
    teogor-ceres-ui-design-system = { module = "dev.teogor.ceres:ui-design-system" }
    teogor-ceres-ui = { module = "dev.teogor.ceres:ui" }
    ```

Then, add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        implementation platform(libs.teogor.ceres.bom)
        implementation libs.teogor.ceres.core.common
        implementation libs.teogor.ceres.core.design.system
        implementation libs.teogor.ceres.core.domain
        
        // To use Kotlin annotation processing tool (kapt)
        kapt libs.teogor.ceres.core.ksp
        // To use Kotlin Symbol Processing (KSP)
        ksp libs.teogor.ceres.core.ksp
        implementation libs.teogor.ceres.core.ktx
        implementation libs.teogor.ceres.core.network
        implementation libs.teogor.ceres.data.database
        implementation libs.teogor.ceres.data.datastore
        implementation libs.teogor.ceres.ui.design.system
        implementation libs.teogor.ceres.ui
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(platform(libs.teogor.ceres.bom))
        implementation(libs.teogor.ceres.core.common)
        implementation(libs.teogor.ceres.core.design.system)
        implementation(libs.teogor.ceres.core.domain)
        
        // To use Kotlin annotation processing tool (kapt)
        kapt(libs.teogor.ceres.core.ksp)
        // To use Kotlin Symbol Processing (KSP)
        ksp(libs.teogor.ceres.core.ksp)
        implementation(libs.teogor.ceres.core.ktx)
        implementation(libs.teogor.ceres.core.network)
        implementation(libs.teogor.ceres.data.database)
        implementation(libs.teogor.ceres.data.datastore)
        implementation(libs.teogor.ceres.ui.design.system)
        implementation(libs.teogor.ceres.ui)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

