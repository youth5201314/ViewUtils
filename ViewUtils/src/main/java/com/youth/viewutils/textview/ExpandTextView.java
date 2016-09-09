package com.youth.viewutils.textview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youth.viewutils.R;


/**
 * 按行数进行折叠带过渡动画的TextView
 */
public class ExpandTextView extends LinearLayout implements OnClickListener {
    private final int WHAT = 2;
    private final int WHAT_ANIMATION_END = 3;
    private final int WHAT_EXPAND_ONLY = 4;
    private TextView textView;
    private TextView mTextState;
    private ImageView mImageState;
    private RelativeLayout mRelativeGroup;
    private int drawableShrink=R.mipmap.up_icon;
    private int drawableExpand=R.mipmap.down_icon;
    private int mTextStateColor=0xFF878787;
    private float mTextStateSize=13;
    private String textExpand;
    private String textShrink;
    private boolean isShrink = false;
    private boolean isExpandNeeded = false;
    private boolean isInitTextView = true;
    private int expandLines=2;
    private int textLines;
    private CharSequence textContent;
    private int textContentColor=0xFF3F3F3F;
    private float textContentSize=15;
    private Thread thread;
    private int sleepTime = 22;


    public ExpandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initValue(context, attrs);
        initView(context);
        initClick();
    }

    private void initValue(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ExpandTextView);
        expandLines = ta.getInteger( R.styleable.ExpandTextView_etv_lines, expandLines);
        drawableShrink = ta.getResourceId(R.styleable.ExpandTextView_etv_shrinkDrawable, drawableShrink);
        drawableExpand = ta.getResourceId(R.styleable.ExpandTextView_etv_expandDrawable, drawableExpand);
        mTextStateColor = ta.getColor(R.styleable.ExpandTextView_etv_textStateColor, mTextStateColor);
        mTextStateSize = ta.getDimension(R.styleable.ExpandTextView_etv_textStateSize, mTextStateSize);
        textContentColor = ta.getColor(R.styleable.ExpandTextView_etv_textContentColor, textContentColor);
        textContentSize = ta.getDimension(R.styleable.ExpandTextView_etv_textContentSize, textContentSize);
        textShrink = ta.getString(R.styleable.ExpandTextView_etv_textShrink);
        textExpand = ta.getString(R.styleable.ExpandTextView_etv_textExpand);
        if (TextUtils.isEmpty(textExpand)) textExpand="全部";
        ta.recycle();
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.expand_textview, this);
        mRelativeGroup = (RelativeLayout) findViewById(R.id.rlExpandText);
        mImageState = (ImageView) findViewById(R.id.expandTextImage);
        mTextState = (TextView) findViewById(R.id.expandStateText);
        textView = (TextView) findViewById(R.id.expandText);
        textView.setTextColor(textContentColor);
        textView.setTextSize(textContentSize);
        mTextState.setTextSize(mTextStateSize);
        mTextState.setTextColor(mTextStateColor);

    }

    private void initClick() {
        textView.setOnClickListener(this);
        mRelativeGroup.setOnClickListener(this);
    }

    public void setText(CharSequence charSequence) {
        textContent = charSequence;
        textView.setText(charSequence.toString());
        ViewTreeObserver viewTreeObserver = textView.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!isInitTextView) {
                    return true;
                }
                textLines = textView.getLineCount();
                isExpandNeeded = textLines > expandLines;
                isInitTextView = false;
                if (isExpandNeeded) {
                    isShrink = true;
                    doAnimation(textLines, expandLines, WHAT_ANIMATION_END);
                } else {
                    isShrink = false;
                    doNotExpand();
                }
                return true;
            }
        });

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (WHAT == msg.what) {
                textView.setMaxLines(msg.arg1);
                textView.invalidate();
            } else if (WHAT_ANIMATION_END == msg.what) {
                setExpandState(msg.arg1);
            } else if (WHAT_EXPAND_ONLY == msg.what) {
                changeExpandState(msg.arg1);
            }
            super.handleMessage(msg);
        }

    };

    /**
     * @param startIndex The number of lines to start the animation
     * @param endIndex   End of the animation
     * @param what       Handler signal after the end of the animation
     */
    private void doAnimation(final int startIndex, final int endIndex,final int what) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (startIndex < endIndex) {
                    // If the number of rows is less than the end of the line number, then expand to the end of the line number
                    int count = startIndex;
                    while (count++ < endIndex) {
                        Message msg = handler.obtainMessage(WHAT, count, 0);
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendMessage(msg);
                    }
                } else if (startIndex > endIndex) {
                    // If the number of rows is greater than the end of the line number, then fold up to the end of the line number
                    int count = startIndex;
                    while (count-- > endIndex) {
                        Message msg = handler.obtainMessage(WHAT, count, 0);
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendMessage(msg);
                    }
                }
                // animation end,send signal
                Message msg = handler.obtainMessage(what, endIndex, 0);
                handler.sendMessage(msg);
            }
        });
        thread.start();
    }

    /**
     * Change the state of collapse (only changes the folding and unfolding state, does not hide the folding / unfolding image layout).
     * @param endIndex
     */
    @SuppressWarnings("deprecation")
    private void changeExpandState(int endIndex) {
        mRelativeGroup.setVisibility(View.VISIBLE);
        if (endIndex < textLines) {
            mImageState.setBackgroundResource(drawableExpand);
            mTextState.setText(textExpand);
        } else {
            mImageState.setBackgroundResource(drawableShrink);
            mTextState.setText(textShrink);
        }

    }

    /**
     * Set fold state (if the number of rows is larger than the number of text lines, then the folding / unfolding image layout will be hidden, the text will always be in the unfolded state).
     * @param endIndex
     */
    @SuppressWarnings("deprecation")
    private void setExpandState(int endIndex) {

        if (endIndex < textLines) {
            isShrink = true;
            mRelativeGroup.setVisibility(View.VISIBLE);
            mImageState.setBackgroundResource(drawableExpand);
            textView.setOnClickListener(this);
            mTextState.setText(textExpand);
        } else {
            isShrink = false;
            mRelativeGroup.setVisibility(View.GONE);
            mImageState.setBackgroundResource(drawableShrink);
            textView.setOnClickListener(null);
            mTextState.setText(textShrink);
        }

    }

    /**
     * No need to fold
     */
    private void doNotExpand() {
        textView.setMaxLines(expandLines);
        mRelativeGroup.setVisibility(View.GONE);
        textView.setOnClickListener(null);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rlExpandText ||
                v.getId() == R.id.expandText) {
            clickImageToggle();
        }

    }

    private void clickImageToggle() {
        if (isShrink) {
            // If it is already folded, then the non folding processing
            doAnimation(expandLines, textLines, WHAT_EXPAND_ONLY);
        } else {
            // If it is not folded, then the folding process
            doAnimation(textLines, expandLines, WHAT_EXPAND_ONLY);
        }

        // Switch status
        isShrink = !isShrink;
    }

    public int getDrawableShrink() {
        return drawableShrink;
    }

    public void setDrawableShrink(int drawableShrink) {
        this.drawableShrink = drawableShrink;
    }

    public int getDrawableExpand() {
        return drawableExpand;
    }

    public void setDrawableExpand(int drawableExpand) {
        this.drawableExpand = drawableExpand;
    }

    public int getExpandLines() {
        return expandLines;
    }

    public void setExpandLines(int newExpandLines) {
        int start = isShrink ? this.expandLines : textLines;
        int end = textLines < newExpandLines ? textLines : newExpandLines;
        doAnimation(start, end, WHAT_ANIMATION_END);
        this.expandLines = newExpandLines;
    }

    /**
     * @return content text
     */
    public CharSequence getTextContent() {
        return textContent;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

}
