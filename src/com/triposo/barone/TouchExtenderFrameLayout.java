/*
 * Copyright (C) 2012 Triposo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.triposo.barone;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.FrameLayout;

/**
 * A FrameLayout which extends the touch area of its child.
 * Do not add more than one child.
 */
public class TouchExtenderFrameLayout extends FrameLayout {

  public TouchExtenderFrameLayout(Context context) {
    super(context);
  }

  public TouchExtenderFrameLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TouchExtenderFrameLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onLayout(
      boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    final View child = getChildAt(0);
    if (child == null) {
      return;
    }
    Rect childRect = new Rect(0, 0, getWidth(), getHeight());
    setTouchDelegate(new TouchDelegate(childRect, child));
  }
}
