package com.zjk.despairk.bottleloading.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * com.zjk.despairk.bottleloading.view
 * DespairK
 *
 * @author ZJK
 *         created at 2017/1/9 16:14
 *         功能:
 */
public class NewLinearLayout extends ViewGroup {
    public NewLinearLayout(Context context) {
        this(context, null);
    }

    public NewLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        //记录当前的高度位置
        int curHeight = 0;
        //将子view逐个摆放
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            //摆放子view，参数分别是子view矩形的左，上，右，下
            child.layout(l, curHeight, 1 + width, curHeight + height);
            curHeight += height;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 将所有子view进行测量，这会触发每个子view的onMeasure函数
         * 注意要与measureChild区分，measureChild是对单个view进行测量
         */
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount == 0) {//没有子view就不占用空间
            setMeasuredDimension(0, 0);
        } else {
            //如果宽高都包括内容
            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
                //我们将高度设置为所有子view的高度相加，宽度设为子view中最大宽度
                int height = getTotleHeight();
                int width = getTotleWidth();
                setMeasuredDimension(width, height);
            } else if (heightMode == MeasureSpec.AT_MOST){//如果高度是wrap
                //宽度设置为viewgroup自己测量的宽度，高度设置为所有子view的高度总和
                setMeasuredDimension(widthSize, getTotleHeight());
            } else if (widthMode == MeasureSpec.AT_MOST) {//如果只有宽度是wrap
                //宽度设置为子view中宽度最大值，高度为测量值
                setMeasuredDimension(getTotleWidth(), heightSize);
            }
        }
    }

    private int getTotleWidth() {
        int childCount = getChildCount();
        int childMaxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getMeasuredWidth() > childMaxWidth) {
                childMaxWidth = childView.getMeasuredWidth();
            }
        }
        return childMaxWidth;
    }

    private int getTotleHeight() {
        int childCount = getChildCount();
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            height += childView.getMeasuredHeight();
        }
        return height;
    }
}
