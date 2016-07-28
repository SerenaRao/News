package com.neteasenews.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Yuan
 * @time 2016/6/24  18:45
 * @desc ${TODD}
 */
public class RingTextView extends View {

    private static final int STROKE_WIDTH = 5;
    private static final String CONTENT = "跳过";
    public static final int REFRESH_COUNT = 72;
    private float mIncreaseAngel;
    private final int mTextWidth;
    private final int mWidth;
    private final float mTextHeight;
    private float mAngle;
    private int mRefreshTime;
    private TextPaint mTextPaint;
    private Paint mCirclePaint;
    private Paint mRingPaint;
    private RectF mRectF;
    private Handler mHandler = new Handler();


    public RingTextView(Context context) {
        this(context, null);
    }

    public RingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(25);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        mTextHeight = metrics.ascent + metrics.descent;
        mTextWidth = (int) mTextPaint.measureText(CONTENT, 0, 2);
        mWidth = (mTextWidth + STROKE_WIDTH * 6);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.GRAY);

        mRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRingPaint.setColor(Color.RED);
        mRingPaint.setStrokeWidth(STROKE_WIDTH);
        mRingPaint.setStyle(Paint.Style.STROKE);

        mRectF = new RectF(STROKE_WIDTH / 2, STROKE_WIDTH / 2, mWidth - STROKE_WIDTH / 2, mWidth - STROKE_WIDTH / 2);
        mHandler.postDelayed(refresh, mRefreshTime);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆形背景
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2, mCirclePaint);
        //写中间字
        canvas.drawText(CONTENT, mWidth / 2, mWidth / 2 - mTextHeight / 2, mTextPaint);
        //画进度圆环
        canvas.drawArc(mRectF, -90, mAngle, false, mRingPaint);
    }

    public void setShowTime(int time) {
        mRefreshTime = time / REFRESH_COUNT;
        mIncreaseAngel = 360 / REFRESH_COUNT;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        setAlpha((isPressed() || isFocused() || isSelected()) ? 0.5f : 1.0f);
    }

    Runnable refresh = new Runnable() {
        @Override
        public void run() {
            mAngle += mIncreaseAngel;
            postInvalidate();
            mHandler.postDelayed(refresh, mRefreshTime);
        }
    };
}
