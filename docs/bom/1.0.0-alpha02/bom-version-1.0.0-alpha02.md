# Winds BoM v1.0.0-alpha02 (Bill of Materials)

The Winds BoM (Bill of Materials) enables you to manage all your Winds library versions by specifying only one version — the BoM's version.

When you use the Winds BoM in your app, the BoM automatically pulls in the individual library versions mapped to the BoM's version. All the individual library versions will be compatible. When you update the BoM's version in your app, all the Winds libraries that you use in your app will update to the versions mapped to that BoM version.

To learn which Winds library versions are mapped to a specific BoM version, check out the release notes for that BoM version. If you need to compare the library versions mapped to one BoM version compared to another BoM version, use the comparison widget below.

Learn more about [Gradle's support for BoM platforms](https://docs.gradle.org/4.6-rc-1/userguide/managing_transitive_dependencies.html#sec:bom_import).

Here's how to use the Winds BoM to declare dependencies in your module (app-level) Gradle file (usually app/build.gradle.kts). When using the BoM, you don't specify individual library versions in the dependency lines.

```kt
dependencies {
  // Import the BoM for the Winds platform
  implementation(platform("dev.teogor.winds:bom:1.0.0-alpha02"))

  // Declare the dependencies for the desired Winds products
  // without specifying versions. For example, declare:
  // Winds Module 1 Library 3
  implementation("dev.teogor.winds:module-1-library-3")
  // Winds Demo Tst 2
  implementation("dev.teogor.winds:winds-demo-kotlin-dsl-tst2")
  // Winds Module 1 Library 1
  implementation("dev.teogor.winds:module-1-library-1")
}
```

## Latest SDK versions

| Status | Service or Product | Gradle dependency | Latest version |
| ------ | ------------------ | ----------------- | -------------- |
| 🧪 | [demo-kotlin-dsl-tst2](/demo-2) | dev.teogor.winds:winds-demo-kotlin-dsl-tst2 | 1.0.0-alpha01 |
| 🧪 | [library-1](/module/library-1) | dev.teogor.winds:module-1-library-1 | 1.0.0-alpha01 |
| 🧪 | [library-2](/module/library-2) | dev.teogor.winds:module-1-library-2 | 1.0.0-alpha01 |
| 🚧 | [library-3](/module/library-3) | dev.teogor.winds:module-1-library-3 | 3.8.2 |
|  | [library-4](/module/library-4) | dev.teogor.winds:module-1-library-4 | 6.2.4 |

### Explore Further

For the latest updates, in-depth documentation, and a comprehensive overview of the Winds ecosystem, visit the official [Winds documentation](/docs/). It's your gateway to a wealth of resources and insights that will elevate your Winds development journey.

Stay informed, stay current, and embrace the full potential of Winds.
