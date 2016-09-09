package com.youth.viewutils.textview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.youth.viewutils.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TextView换行对齐控件
 */
public class JustifyTextView extends TextView {
    private static final String TAG = "JustifyTextView";
    private String mText;
    private Paint mPaint;
    private int textWidth;
    private int textHeight;
    private float lineSpacing;
    public int mFontHeight = 0;

    public JustifyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JustifyTextView(Context context) {
        this(context, null);
    }


    public JustifyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.JustifyTextView, defStyle, 0);
        lineSpacing = a.getDimension(R.styleable.JustifyTextView_lineSpacing, 0);
        a.recycle();
        mPaint = this.getPaint();
        /**
         * Gets the text color set to the brush
         */
        mPaint.setColor(this.getCurrentTextColor());
    }

    /**
     * Word unit array, mainly for English
     */
    private String[] words;

    private void arrayTowords() {
        char[] array = mText.toCharArray();
        int j = 0;
        words = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            words[i] = "";
            if (array[i] >= 0 && array[i] < 0x7f) {
                if (String.valueOf(array[i]).equals("\n")) {
                    j++;
                    words[j] = "\n";
                    j++;
                    continue;
                }
                words[j] = words[j] + (array[i] + "").trim();
                if (array.length - 1 > i + 1
                        && (array[i + 1] == ' ' || array[i + 1] == ' ')) {
                    j++;
                }

            } else {
                if (String.valueOf(array[i]).equals("\n")) {
                    j++;
                    words[j] = "\n";
                    j++;
                    continue;
                }
                words[j] = words[j] + (array[i] + "").trim();
                Character.UnicodeBlock ub = Character.UnicodeBlock.of((array[i + 1]));
                if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                        || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                        || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                        || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                        || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS) {
                    continue;
                }
                j++;
            }
        }

    }

    /**
     * @return lines-int Number of rows in the document after re layout
     */
    private int getLines() {
        float linewidth = 0;
        int line = 0;
        float blankwidth = mPaint.measureText(" ");
        for (int i = 0; i < words.length; i++) {
            float measureText = mPaint.measureText(words[i]);

            if (linewidth + measureText >= textWidth) {
                if (words[i].isEmpty() || words[i] == "")
                    break;
                line++;
                linewidth = 0;
                i--;
            } else {
                if (String.valueOf(words[i]).equals("\n")) {
                    linewidth = textWidth;
                }
                if (!isContainChinese(words[i])) {
                    linewidth += (measureText + blankwidth);
                } else {
                    linewidth += measureText;
                }
            }
        }
        return line + 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        float linewidth = 0;
        int point = 0;
        int line = 0;
        float blankwidth = mPaint.measureText(" ");
        for (int i = 0; i < words.length; i++) {
            float measureText = mPaint.measureText(words[i]);

            if (linewidth + measureText >= textWidth) {
                float widthPoint = 0;
                for (int k = point; k < i; k++) {
                    if (!String.valueOf(words[k]).equals("\n")) {
                        //Line by line by drawing the word
                        canvas.drawText(words[k],
                                widthPoint + getPaddingLeft(),
                                (float) (mFontHeight + lineSpacing) * (line + 1),
                                mPaint);
                    }
                    widthPoint = widthPoint + mPaint.measureText(words[k])
                            + ((textWidth - linewidth) / (i - point - 1));
                    // If it is not Chinese, add a space
                    if (!isContainChinese(words[k])) {
                        widthPoint += blankwidth;
                    }
                }
                line++;
                point = i;
                linewidth = 0;
                widthPoint = 0;
                i--;
            } else { // One by one word length line accumulated enough draw a or line
                if (String.valueOf(words[i]).equals("\n")) {
                    linewidth = textWidth;
                }
                // There is a space behind each word in the English language.
                if (!isContainChinese(words[i])) {
                    linewidth += (measureText + blankwidth);
                } else {
                    linewidth += measureText;
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mText = (String) this.getText();
        // The width of TextView is obtained here; the height needs to be re calculated.
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        // The width of the text area minus about text margin
        textWidth = widthSize - getPaddingLeft() - getPaddingRight();
        arrayTowords();

        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        mFontHeight = fontMetricsInt.bottom - fontMetricsInt.top;
        textHeight = (int) (getLines() * (mFontHeight + lineSpacing)) + getPaddingBottom() + getPaddingTop();
        setMeasuredDimension(widthSize, textHeight);
    }

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[u4e00-u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}