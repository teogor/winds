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

package dev.teogor.winds.api.impl

import dev.teogor.winds.api.Publishing
import dev.teogor.winds.api.SonatypeHost

open class PublishingImpl : Publishing {
  override var enabled: Boolean = true
  override var enablePublicationSigning: Boolean = true
  override var optInForVanniktechPlugin: Boolean = true
  override var cascade: Boolean = true
  override var sonatypeHost: SonatypeHost = SonatypeHost.DEFAULT

  override fun copy(from: Publishing): Publishing {
    enablePublicationSigning = from.enablePublicationSigning
    optInForVanniktechPlugin = from.optInForVanniktechPlugin
    cascade = from.cascade
    sonatypeHost = from.sonatypeHost
    return this
  }
}
