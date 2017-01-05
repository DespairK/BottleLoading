package com.zjk.despairk.bottleloading.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.zjk.despairk.bottleloading.R;

/**
 * com.zjk.despairk.bottleloading.view
 * DespairK
 *
 * @author ZJK
 *         created at 2017/1/5 12:14
 *         功能:
 */
public class CustomTitleView extends View {

    //文本
    private String mTitleText;

    //颜色
    private int mTitleTextColor;

    //大小
    private int mTitleTextSize;

    //范围
    private Rect mBund;
    private Paint mPaint;

    public CustomTitleView(Context context) {
        this(context, null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //获得自定义样式
    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获得自定义的样式
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomTitleView_titleText:
                    mTitleText = a.getString(attr);
                    break;
                case R.styleable.CustomTitleView_titleTextColor:
                    mTitleTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();

//        获得绘制的宽高
        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mPaint.setColor(mTitleTextColor);
        mBund = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBund);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText, getWidth() / 2 - mBund.width() / 2, getHeight() / 2 + mBund.height() / 2, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBund);
            float textWidth = mBund.width();
            int desirced = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desirced;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else
        {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBund);
            float textHeight = mBund.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }

        setMeasuredDimension(width, height);

    }
}
