# Module Metadata Configuration

`moduleMetadata` provides essential information about your project for documentation and publication.

## Properties

- `name`: Human-readable name of the project.
- `description`: A detailed description of the project.
- `yearCreated`: The year the project was started.
- `websiteUrl`: URL to the project's website.
- `apiDocsUrl`: URL to the hosted API documentation.

## Sub-configurations

### `artifactDescriptor`
Defines Maven coordinates.
```kotlin
artifactDescriptor {
    group = "dev.teogor.winds"
    name = "winds"
}
```

### `scm`
Source Control Management details.
```kotlin
scm<Scm.GitHub> {
    owner = "teogor"
    repository = "winds"
}
```

### `licensedUnder`
Project licenses.
```kotlin
licensedUnder(License.Apache2())
```
