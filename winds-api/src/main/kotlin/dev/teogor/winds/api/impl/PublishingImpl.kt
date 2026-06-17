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
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

open class PublishingImpl @Inject constructor(
  objects: ObjectFactory,
) : Publishing {
  override val enabled: Property<Boolean> = objects.property(Boolean::class.java).convention(true)
  override val cascade: Property<Boolean> = objects.property(Boolean::class.java).convention(true)
  override val enablePublicationSigning: Property<Boolean> = objects.property(
    Boolean::class.java,
  ).convention(true)
  override val optInForVanniktechPlugin: Property<Boolean> = objects.property(
    Boolean::class.java,
  ).convention(true)
  override val automaticPublishing: Property<Boolean> = objects.property(
    Boolean::class.java,
  ).convention(false)

  override fun copy(from: Publishing): Publishing {
    enabled.set(from.enabled)
    cascade.set(from.cascade)
    enablePublicationSigning.set(from.enablePublicationSigning)
    optInForVanniktechPlugin.set(from.optInForVanniktechPlugin)
    automaticPublishing.set(from.automaticPublishing)
    return this
  }
}
