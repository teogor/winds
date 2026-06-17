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

package dev.teogor.winds.common.ktx

import dev.teogor.winds.api.ModuleMetadata
import dev.teogor.winds.ktx.filterNonEmptyLicenses
import org.gradle.api.publish.maven.MavenPom

infix fun ModuleMetadata.toLicensesSpec(pom: MavenPom) {
  pom.licenses { mavenPomLicenseSpec ->
    licenses.filterNonEmptyLicenses().forEach { license ->
      mavenPomLicenseSpec.license {
        it.name.set(license.name)
        it.url.set(license.url)
        it.distribution.set(license.distribution.abbreviation)
        license.comments?.let { comments ->
          it.comments.set(comments)
        }
      }
    }
  }
}
