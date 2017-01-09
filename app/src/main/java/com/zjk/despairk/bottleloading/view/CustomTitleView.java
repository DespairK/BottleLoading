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
        /**
         * attrs参数就是在xml文件中<declare-styleable>标签
         * 即属性集合的标签，名字为自定
         */
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

        //将TypedArray对象回收
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
        /***
         * widthMeasureSpec和heightMeasureSpec这两个参数包含宽高和测量模式信息，
         *
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//获取测量模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//获取尺寸
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        /**
         * 获取到的尺寸并不是最终的尺寸大小，而是父view提供的参考大小
         * 测量模式一共有三种参数：
         * UNSPECIFIED：父容器没有对当前view进行任何限制，当前View可以取任意尺寸
         * EXACTLY：当前的尺寸就是当前view应该取得尺寸
         * AT_MOST：当前尺寸是当前view能取得最大尺寸
         *
         * 测量模式和布局文件的宽高之间的关系：
         * match_parent-->EXACTLY.
         * wrap_content-->AT_MOST
         * 固定尺寸-->EXACTLY
         */
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
