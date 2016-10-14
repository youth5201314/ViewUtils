package com.youth.viewutils.utils;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.TextView;

public final class CenterDrawableHelper {
    static final int DRAWABLE_LEFT = 0;
    static final int DRAWABLE_TOP = 1;
    static final int DRAWABLE_RIGHT = 2;
    static final int DRAWABLE_BOTTOM = 3;
    private static void onCenterDraw(TextView view, Canvas canvas, Drawable drawable, int gravity) {
        int drawablePadding = view.getCompoundDrawablePadding();
        int ratio = 1;
        float total;
        switch (gravity) {
            case Gravity.RIGHT:
                ratio = -1;
            case Gravity.LEFT:
                total = view.getPaint().measureText(view.getText().toString()) + drawable.getIntrinsicWidth() + drawablePadding + view.getPaddingLeft() + view.getPaddingRight();
                canvas.translate(ratio * (view.getWidth() - total) / 2, 0);
                break;
            case Gravity.BOTTOM:
                ratio = -1;
            case Gravity.TOP:
                Paint.FontMetrics fontMetrics0 = view.getPaint().getFontMetrics();
                total = fontMetrics0.descent - fontMetrics0.ascent + drawable.getIntrinsicHeight() + drawablePadding + view.getPaddingTop() + view.getPaddingBottom();
                canvas.translate(0, ratio * (view.getHeight() - total) / 2);
                break;
        }
    }

    public static void preDraw(TextView view, Canvas canvas) {
        Drawable[] drawables = view.getCompoundDrawables();
        if (drawables != null) {
            if (drawables[DRAWABLE_LEFT] != null) {
                view.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                onCenterDraw(view, canvas, drawables[DRAWABLE_LEFT], Gravity.LEFT);
            } else if (drawables[DRAWABLE_TOP] != null) {
                view.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
                onCenterDraw(view, canvas, drawables[DRAWABLE_TOP], Gravity.TOP);
            } else if (drawables[DRAWABLE_RIGHT] != null) {
                view.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                onCenterDraw(view, canvas, drawables[DRAWABLE_RIGHT], Gravity.RIGHT);
            } else if (drawables[DRAWABLE_BOTTOM] != null) {
                view.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                onCenterDraw(view, canvas, drawables[DRAWABLE_BOTTOM], Gravity.BOTTOM);
            }
        }
    }
}