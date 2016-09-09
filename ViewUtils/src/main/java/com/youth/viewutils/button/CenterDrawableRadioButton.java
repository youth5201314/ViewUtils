package com.youth.viewutils.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.youth.viewutils.utils.CenterDrawableHelper;

public class CenterDrawableRadioButton extends RadioButton {

    @SuppressLint("NewApi")
    public CenterDrawableRadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressLint("NewApi")
    public CenterDrawableRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CenterDrawableRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CenterDrawableRadioButton(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        CenterDrawableHelper.preDraw(this, canvas);
        super.onDraw(canvas);
    }
}
