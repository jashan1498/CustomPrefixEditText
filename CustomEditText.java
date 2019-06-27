package com.company.jashan.mymoments.Custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.company.jashan.mymoments.R;

public class CustomEditText extends android.support.v7.widget.AppCompatEditText {
    String mPrefix = "hello";
    Rect mPrefixRect = new Rect();
    int preInputType = getInputType();
    onPrefixClickListener clickListener;
    boolean touchPrefix = false;


    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPrefixClickListener(onPrefixClickListener listener) {
        this.clickListener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measurePrefix();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    void measurePrefix() {
        Log.d("jfiwejfewiwf", "initial " + mPrefixRect.right);
        getPaint().getTextBounds(mPrefix, 0, mPrefix.length(), mPrefixRect);
        Log.d("jfiwejfewiwf", "2nd " + mPrefixRect.right);

        mPrefixRect.right += getPaint().measureText(" ");
        Log.d("jfiwejfewiwf", "3rd " + mPrefixRect.right);


    }

    @Override
    public int getCompoundPaddingLeft() {
        return super.getCompoundPaddingLeft() + mPrefixRect.width() + 20;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getPaint().setColor(getResources().getColor(R.color.cursorColor));
        canvas.drawText(mPrefix, super.getCompoundPaddingLeft(), getBaseline(), getPaint());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                touchPrefix = event.getX() < mPrefixRect.right;
                if (touchPrefix) {
                    setInputType(InputType.TYPE_NULL);
                    return true;
                } else {
                    setInputType(preInputType);
                }
            case MotionEvent.ACTION_UP:
                if (touchPrefix && (clickListener != null)) {
                    clickListener.onPrefixTouched();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                touchPrefix = false;
        }
        return super.onTouchEvent(event);
    }

    public void setPrefix(String prefix) {
        mPrefix = prefix;
        measurePrefix();
        invalidate();

    }

    public interface onPrefixClickListener {
        void onPrefixTouched();
    }
}
