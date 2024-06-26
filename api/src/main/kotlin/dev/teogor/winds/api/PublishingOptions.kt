/*
 * Copyright 2023 teogor (Teodor Grigor)
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

package dev.teogor.winds.api

import com.vanniktech.maven.publish.SonatypeHost

@Deprecated(
  message = "Use Publishing instead.",
  replaceWith = ReplaceWith(expression = "Publishing", "dev.teogor.winds.api.Publishing"),
)
interface PublishingOptions {
  @Deprecated(
    message = "Use 'enabled' in Publishing instead.",
    replaceWith = ReplaceWith(expression = "enabled"),
  )
  var publish: Boolean

  @Deprecated(
    message = "Use 'cascade' in Publishing instead.",
    replaceWith = ReplaceWith(expression = "cascade"),
  )
  var cascadePublish: Boolean

  var enablePublicationSigning: Boolean
  var optInForVanniktechPlugin: Boolean
  var sonatypeHost: SonatypeHost

  fun copy(from: PublishingOptions): PublishingOptions
}
