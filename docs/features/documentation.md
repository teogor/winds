# Documentation Generation

Winds includes a built-in documentation generator (`windsMd`) and integrates with Dokka for API documentation.

## Built-in Markdown Generator

The `windsMd` task generates release notes and dependency documentation in Markdown format.

### Enable the feature:
```kotlin
winds {
    features {
        docsGenerator = true
    }
}
```

### Running the task:
```bash
./gradlew :windsMd
```

Generated files are typically located in the `docs/` directory of your project.

## Dokka Integration

To use Dokka for API documentation, enable it in the features block:

```kotlin
winds {
    features {
        dokka = true
    }
}
```

This will automatically apply the Dokka plugin to your project.
