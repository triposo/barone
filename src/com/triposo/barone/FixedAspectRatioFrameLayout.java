package com.triposo.barone;

// Based on http://stackoverflow.com/a/13846628/804479

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * A frame container which maintains a fixed aspect ratio.
 *
 * Usage examples for 4:3 aspect ratio landscape:
 *
 *
 * Add a new dimension value:
 * <resources>
 *   <item name="top_image_aspect_ratio" format="float" type="dimen">1.3333</item>
 *
 *
 * Add a new xmlns to the root element of the layout:
 * <Layout
 *     xmlns:android="http://schemas.android.com/apk/res/android"
 *     xmlns:YOUR_APP="http://schemas.android.com/apk/res/YOUR_PACKAGE"
 *
 *
 * <LinearLayout
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     android:orientation="horizontal"
 *     android:weightSum="3">
 *   <!-- The HEIGHT will be dynamic,
 *        because the width will have a value greater than 0,
 *        because of the layout_weight. -->
 *   <com.triposo.barone.FixedAspectRatioFrameLayout
 *       android:layout_height="0px"
 *       android:layout_width="0px"
 *       android:layout_weight="2"
 *       YOUR_APP:aspectRatio="@dimen/top_image_aspect_ratio">
 * </LinearLayout>
 *
 *
 * <RelativeLayout
 *     android:layout_width="match_parent"
 *     android:layout_height="match_parent">
 *   <!-- The HEIGHT will be dynamic, because layout_height is 0px. -->
 *   <com.triposo.barone.FixedAspectRatioFrameLayout
 *       android:layout_width="match_parent"
 *       android:layout_height="0px"
 *       YOUR_APP:aspectRatio="@dimen/top_image_aspect_ratio">
 * </RelativeLayout>
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
    int receivedWidth = MeasureSpec.getSize(widthMeasureSpec);
    int receivedHeight = MeasureSpec.getSize(heightMeasureSpec);

    int measuredWidth;
    int measuredHeight;
    boolean widthDynamic;
    if (heightMode == MeasureSpec.EXACTLY) {
      if (widthMode == MeasureSpec.EXACTLY) {
        widthDynamic = receivedWidth == 0;
      } else {
        widthDynamic = true;
      }
    } else if (widthMode == MeasureSpec.EXACTLY) {
      widthDynamic = false;
    } else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      return;
    }
    if (widthDynamic) {
      // Width is dynamic.
      int w = (int) (receivedHeight * aspectRatio);
      measuredWidth = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
      measuredHeight = heightMeasureSpec;
    } else {
      // Height is dynamic.
      measuredWidth = widthMeasureSpec;
      int h = (int) (receivedWidth / aspectRatio);
      measuredHeight = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
    }
    super.onMeasure(measuredWidth, measuredHeight);
  }
}
