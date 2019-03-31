package com.amit.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class CircularBar extends View {

    private boolean animate;
    private boolean show;
    private int barWidth;
    private int barColor;
    private int barBackgroundColor;
    private int barPercentage;
    private RectF rect = new RectF();
    private Paint barPaint = new Paint();
    private Paint barBackgroundPaint = new Paint();
    private float value = 0;
    private OnAnimationFinished onAnimationFinished;


    public CircularBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircularBar);

        animate = array.getBoolean(R.styleable.CircularBar_animate,true);
        show = array.getBoolean(R.styleable.CircularBar_show,false);
        barWidth = (int)array.getDimension(R.styleable.CircularBar_bar_width,0);
        barColor = array.getColor(R.styleable.CircularBar_bar_color, Color.parseColor("#c32d4e"));
        barBackgroundColor = array.getColor(R.styleable.CircularBar_bar_background_color,getResources().getColor(android.R.color.transparent));
        barPercentage = (int) array.getFloat(R.styleable.CircularBar_bar_percentage,100);

        array.recycle();

        barPaint.setColor(barColor);
        barPaint.setStrokeCap(Paint.Cap.ROUND);
        barPaint.setAntiAlias(true);
        barPaint.setStyle(Paint.Style.STROKE);
        barPaint.setStrokeWidth(barWidth);

        barBackgroundPaint.setColor(barBackgroundColor);
        barBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);
        barBackgroundPaint.setAntiAlias(true);
        barBackgroundPaint.setStyle(Paint.Style.STROKE);
        barBackgroundPaint.setStrokeWidth(barWidth);


    }

    private void startAnimation(){
        invalidate();
        int sweep = (int) ((360f*barPercentage)/100f);
        Log.w("sweep",sweep+"");
        ValueAnimator animator = ValueAnimator.ofInt(0,sweep);
        animator.setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (int)animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (onAnimationFinished!=null) onAnimationFinished.onFinished();
            }
        });
        animator.start();
    }

    public void setPercentage(int percentage){
        this.barPercentage = percentage;
    }

    public void setAnimate(boolean animate){
        this.animate = animate;
    }

    public void show(){
        show = true;
        if (animate) startAnimation();
        else invalidate();
    }

    public void setOnAnimationFinished(OnAnimationFinished onAnimationFinished){
        this.onAnimationFinished = onAnimationFinished;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (show){
            if (animate) {
                canvas.drawArc(rect,270f,360f,false,barBackgroundPaint);
                canvas.drawArc(rect, 270f, value, false, barPaint);
            }else{
                int sweep = (int) ((360f*barPercentage)/100f);
                canvas.drawArc(rect,270f,360f,false,barBackgroundPaint);
                canvas.drawArc(rect, 270f, sweep, false, barPaint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        rect.set(0,0,width,height);
        rect.inset(this.barWidth/2f,this.barWidth/2f);

        if (show) show();

    }

    public static interface OnAnimationFinished{
        public void onFinished();
    }
}
