/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
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

package com.facebook.litho;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import androidx.annotation.Nullable;
import com.facebook.infer.annotation.Nullsafe;
import com.facebook.litho.config.ComponentsConfiguration;

/** Utilities for animations debug. */
@Nullsafe(Nullsafe.Mode.LOCAL)
public class AnimationsDebug {

  public static final boolean ENABLED = ComponentsConfiguration.isEndToEndTestRun;
  static final String TAG = "LithoAnimationDebug";

  static void debugPrintLayoutState(LayoutState layoutState) {
    if (!ENABLED) {
      return;
    }
    Log.d(TAG, layoutState.dumpAsString());
  }

  static boolean areTransitionsEnabled(@Nullable Context context) {
    if (ComponentsConfiguration.isAnimationDisabled) {
      // mostly for unit tests
      return false;
    }

    if (!ComponentsConfiguration.isEndToEndTestRun) {
      return true;
    }

    if (!ComponentsConfiguration.CAN_CHECK_GLOBAL_ANIMATOR_SETTINGS) {
      return false;
    }

    if (context == null) {
      return false;
    }

    float animatorDurationScale =
        Settings.Global.getFloat(
            context.getContentResolver(), Settings.Global.ANIMATOR_DURATION_SCALE, 1f);
    return ComponentsConfiguration.forceEnableTransitionsForInstrumentationTests
        || animatorDurationScale != 0f;
  }
}
