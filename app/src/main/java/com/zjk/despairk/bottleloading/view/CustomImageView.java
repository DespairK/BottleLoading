package com.zjk.despairk.bottleloading.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.zjk.despairk.bottleloading.R;

/**
 * com.zjk.despairk.bottleloading.view
 * DespairK
 *
 * @author ZJK
 *         created at 2017/1/9 18:11
 *         功能:
 */
public class CustomImageView extends View {

    private Bitmap mImage;
    private int mImageScareType;
    private String mTitle;
    private int mTextColor;
    private int mTextSize;
    private Rect rect;
    private Paint mPaint;
    private Rect mTextBound;
    private int mWidth;
    private int mHeight;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomImageView_image:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomImageView_imageScaleType:
                    mImageScareType = a.getInt(attr, 0);
                    break;
                case R.styleable.CustomImageView_titleText:
                    mTitle = a.getString(attr);
                    break;
                case R.styleable.CustomImageView_titleTextColor:
                    mTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomImageView_titleTextSize:
                    mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
        rect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();
        mPaint.setTextSize(mTextSize);
//        计算了描绘字体需要的范围
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {//宽度是match_paren宽度就是系统测量
            mWidth = widthSize;
        } else {
            //由图片决定宽度
            int imageWidth = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            //有文字决定宽度
            int textWidth = getPaddingStart() + getPaddingEnd() + mTextBound.width();
            if (widthMode == MeasureSpec.AT_MOST) {//wrap_content
                int desire = Math.max(imageWidth, textWidth);
                mWidth = Math.min(desire, widthSize);
            }
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            int desire = getPaddingTop() + getPaddingBottom() + mTextBound.height() + mImage.getHeight();
            if (heightMode == MeasureSpec.AT_MOST) {
                mHeight = Math.min(desire, heightSize);
            }
        }
        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        //边框
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingEnd();
        rect.top = getPaddingTop();
        rect.bottom = mHeight - getPaddingBottom();

        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        //当前设置的宽度小于字体需要的宽度，将字体改为xxx
        if (mTextBound.width() > mWidth) {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitle, paint, (float) (mWidth - getPaddingStart() - getPaddingEnd()), TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingStart(), mHeight - getPaddingBottom(), mPaint);
        } else {
            //正常情况下将字体居中
            canvas.drawText(mTitle, mWidth / 2 - mTextBound.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);
        }
        //取消使用掉的快
        rect.bottom -= mTextBound.height();

        if (mImageScareType == 0) {
            canvas.drawBitmap(mImage, null, rect, mPaint);
        } else {
            //计算居中矩形的范围
            rect.left = mWidth / 2 - mImage.getWidth() / 2;
            rect.right = mWidth / 2 + mImage.getWidth() / 2;
            rect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
            rect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;
            canvas.drawBitmap(mImage, null, rect, mPaint);
        }
    }
}
