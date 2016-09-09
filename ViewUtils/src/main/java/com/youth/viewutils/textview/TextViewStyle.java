package com.youth.viewutils.textview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

import com.youth.viewutils.R;

public class TextViewStyle extends TextView {
    private float cornerRadius = 0;
    private RectF labelBounds = new RectF();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//位图抗锯齿的标志

    public TextViewStyle(Context context) {
        super(context);
        init(context, null);
    }

    public TextViewStyle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextViewStyle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextViewStyle);
            cornerRadius=ta.getDimension(R.styleable.TextViewStyle_tvs_backgroundRadius,cornerRadius);
            ColorStateList color = ta.getColorStateList(R.styleable.TextViewStyle_tvs_backgroundColor);
            if (color != null) {
                setLabelColor(color.getDefaultColor());
            }
        }
        // Default padding
        if (getPaddingLeft() == 0
                || getPaddingRight() == 0
                || getPaddingBottom() == 0
                || getPaddingTop() == 0) {
            int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, dm);
            setPadding(padding, padding, padding, padding);
        }
    }

    /**
     * Sets the label color.
     * @param color The color of the label
     */
    public void setLabelColor(int color) {
        paint.setColor(color);
    }

    /**
     * Sets the corner radius on the label. By default the corner radius is 1/20th of the
     * labels width.
     * @param cornerRadius The radius of each corner
     */
    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        labelBounds.left = 0;
        labelBounds.right = getMeasuredWidth();
        labelBounds.top = 0;
        labelBounds.bottom = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (cornerRadius == Integer.MIN_VALUE) {
            drawLabel(canvas, getWidth()/20);
        } else {
            drawLabel(canvas, cornerRadius);
        }
        super.onDraw(canvas);
    }

    private void drawLabel(Canvas canvas, float cornerRadius) {
        canvas.drawRoundRect(labelBounds, cornerRadius, cornerRadius, paint);
    }
}
