package com.youth.viewutils.textview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.youth.viewutils.utils.CenterDrawableHelper;


public class CenterDrawableTextView extends TextView {

    public CenterDrawableTextView(Context context) {
        super(context);
    }

    public CenterDrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CenterDrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CenterDrawableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        CenterDrawableHelper.preDraw(this, canvas);
        super.onDraw(canvas);
    }
}
