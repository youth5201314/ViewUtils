package com.youth.viewutils.button;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import com.youth.viewutils.utils.CenterDrawableHelper;

public class CenterDrawableCompoundButton extends CompoundButton {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CenterDrawableCompoundButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CenterDrawableCompoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CenterDrawableCompoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CenterDrawableCompoundButton(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        CenterDrawableHelper.preDraw(this, canvas);
        super.onDraw(canvas);
    }
}
