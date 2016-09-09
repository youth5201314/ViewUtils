package com.youth.viewutils.button;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import com.youth.viewutils.utils.CenterDrawableHelper;

public class CenterDrawableButton extends Button {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CenterDrawableButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CenterDrawableButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CenterDrawableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CenterDrawableButton(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        CenterDrawableHelper.preDraw(this, canvas);
        super.onDraw(canvas);
    }
}
