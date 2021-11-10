/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.samples.litho.kotlin.errors

import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentScope
import com.facebook.litho.KComponent
import com.facebook.litho.Style
import com.facebook.litho.core.margin
import com.facebook.litho.dp
import com.facebook.litho.useErrorBoundary
import com.facebook.litho.useState

class KErrorBoundary(private val childComponent: Component) : KComponent() {

  override fun ComponentScope.render(): Component? {
    val errorState = useState<Exception?> { null }
    useErrorBoundary { exception: Exception -> errorState.update(exception) }

    errorState.value?.let {
      return Column(style = Style.margin(all = 16.dp)) {
        child(
            DebugErrorComponent.create(context)
                .message("KComponent's Error Boundary")
                .throwable(it)
                .build())
      }
    }

    return childComponent
  }
}