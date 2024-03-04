[//]: # (This file was automatically generated - do not edit)

# Using the Bill of Materials

The Example Bill of Materials (BOM) lets you manage all of your Example library versions by
specifying only the BOM’s version. The BOM itself has links to the stable versions of the different
Example libraries, in such a way that they work well together. When using the BOM in your app, you
don't need to add any version to the Example library dependencies themselves. When you update the
BOM version, all the libraries that you're using are automatically updated to their new versions.

To find out which Example library versions are mapped to a specific BOM version, check out
the [BOM to library version mapping](bom-mapping.md).

### How do I use a different library version than what's designated in the BOM?

In the `build.gradle` dependencies section, keep the import of the BOM platform. On the library
dependency import, specify the desired version. For example, here's how to declare dependencies if
you want to use an alpha version of Material 3, no matter what version is designated in the BOM:

```groovy
dependencies {
  // Import the Example BOM
  implementation platform('dev.teogor.winds.example:bom:1.0.0-alpha01')

  // Import Network library
  implementation 'dev.teogor.winds.example:example-core-network:1.0.0-beta03'

  // Import other Example libraries without version numbers
  // ..
  implementation 'dev.teogor.winds.example:stitch-common'
}
```

### Does the BOM automatically add all the Example libraries to my app?

No. To actually add and use Example libraries in your app, you must declare each library as a
separate dependency line in your module (app-level) Gradle file (usually app/build.gradle).

Using the BOM ensures that the versions of any Example libraries in your app are compatible, but the
BOM doesn't actually add those Example libraries to your app.

### Why is the BOM the recommended way to manage Example library versions?

Going forward, Example libraries will be versioned independently, which means version numbers will
start to be incremented at their own pace. The latest stable releases of each library are tested and
guaranteed to work nicely together. However, finding the latest stable versions of each library can
be difficult, and the BOM helps you to automatically use these latest versions.

### Am I forced to use the BOM?

No. You can still choose to add each dependency version manually. However, we recommend using the
BOM as it will make it easier to use all of the latest stable versions at the same time.

### Does the BOM work with version catalogs?

Yes. You can include the BOM itself in the version catalog, and omit the other Example library versions:

```toml
[libraries]
teogor-example-bom = { group = "dev.teogor.winds.example", name = "bom", version.ref = "teogor-example-bom" }
teogor-example-stitch-common = { group = "dev.teogor.winds.example", name = "stitch-common" }
```

Don’t forget to import the BOM in your module’s `build.gradle`:

```groovy
dependencies {
    val teogorExampleBom = platform(libs.teogor.example.bom)
    implementation(teogorExampleBom)
    androidTestImplementation(teogorExampleBom)

    // import Example dependencies as usual
}
```

### How do I report an issue or offer feedback on the BOM?

You can file issues on our [issue tracker 🔗](https://github.com/teogor/winds/issues).
