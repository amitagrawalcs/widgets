package com.amit.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class VerticalBar extends View {

    private int fillColor;
    private Rect fillRect;
    private Paint fillPaint;
    private float fillPercent = 0f;
    private float calculatedTop = 0f;

    public VerticalBar(Context context,AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.VerticalBar);

        fillColor = array.getColor(R.styleable.VerticalBar_fill_color, Color.parseColor("#8bc34a"));
        array.recycle();

        fillRect = new Rect();
        fillPaint = new Paint();
        fillPaint.setAntiAlias(true);
        fillPaint.setColor(fillColor);
    }

    public void setFillPercent(float fillPercent){
        this.fillPercent = fillPercent;
    }

    public void startAnimation(){
        float maxTop = (int) (getHeight()*(fillPercent /100f));
        Log.w("MaxTop",(int)maxTop+"");
        ValueAnimator animator = ValueAnimator.ofInt(0, (int) maxTop);
        animator.setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                calculatedTop = getHeight()-value;
                invalidate();
            }
        });
        animator.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float height = getHeight();
        float width = getWidth();

        fillRect.left = 0;
        fillRect.right = (int) width;
        fillRect.top = (int) calculatedTop;
        fillRect.bottom = (int) height;

        Log.w("CalculatedTop",calculatedTop+"");
        canvas.drawRect(fillRect,fillPaint);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }
}
