package com.triposo.barone;

// Based on http://stackoverflow.com/a/13846628/804479

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * A frame container which maintains a fixed aspect ratio.
 *
 * Usage example for 4:3 aspect ratio landscape:
 *
 * <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
 *     xmlns:YOUR_APP="http://schemas.android.com/apk/res/YOUR_PACKAGE">
 *   <com.triposo.barone.FixedAspectRatioFrameLayout
 *       android:layout_height="100dp"
 *       android:layout_width="wrap_content"
 *       YOUR_APP:aspectRatio="1.3333">
 *
 */
public class FixedAspectRatioFrameLayout extends FrameLayout {
  /**
   * (width / height)
   */
  private float aspectRatio;

  public FixedAspectRatioFrameLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FixedAspectRatioFrameLayout);
    aspectRatio = a.getFloat(R.styleable.FixedAspectRatioFrameLayout_aspectRatio, 1.3333f);
    a.recycle();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);

    int measuredWidth;
    int measuredHeight;
    if (heightMode == MeasureSpec.EXACTLY &&
        widthMode == MeasureSpec.EXACTLY) {
      throw new IllegalStateException();
    }
    if (heightMode == MeasureSpec.EXACTLY) {
      // Width is dynamic.
      int w = (int) (MeasureSpec.getSize(heightMeasureSpec) * aspectRatio);
      measuredWidth = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
      measuredHeight = heightMeasureSpec;
    } else if (widthMode == MeasureSpec.EXACTLY) {
      // Height is dynamic.
      measuredWidth = widthMeasureSpec;
      int h = (int) (MeasureSpec.getSize(widthMeasureSpec) / aspectRatio);
      measuredHeight = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
    } else {
      throw new IllegalStateException("One of the width and height specs must be MeasureSpec.EXACTLY");
    }
    super.onMeasure(measuredWidth, measuredHeight);
  }
}
