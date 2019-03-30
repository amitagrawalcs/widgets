package com.amit.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

public class CustomTextView extends AppCompatTextView {

    private Paint textViewPaint=new Paint();
    private Paint borderPaint = new Paint();
    private float cornerRadius;
    private int backgroundColor;
    private float borderWidth;
    private int borderColor;
    private RectF borderRect = new RectF();
    private RectF textViewRect = new RectF();


    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setDrawingCacheEnabled(true);
        setWillNotDraw(false);


        textViewPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);

        String fontPath = attributes.getString(R.styleable.CustomTextView_custom_font);
        cornerRadius = attributes.getDimension(R.styleable.CustomTextView_corner_radius,0f);
        backgroundColor = attributes.getColor(R.styleable.CustomTextView_background_color,Color.TRANSPARENT);
        borderColor = attributes.getColor(R.styleable.CustomTextView_border_color,Color.WHITE);
        borderWidth = attributes.getDimension(R.styleable.CustomTextView_border_width,0f);

        attributes.recycle();

        if (fontPath!=null) setCustomFont(fontPath);
    }


    public void setCornerRadius(float radius){
        this.cornerRadius = toPixel(radius);
        invalidate();
    }

    public void setBorderColor(int color){
        this.borderColor = color;
        invalidate();
    }

    public void setBorderWidth(float borderWidth){
        this.borderWidth = toPixel(borderWidth);
        invalidate();
    }

    public void setCustomFont(String fontPath){
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),fontPath);
        this.setTypeface(typeface);
    }


    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
    }

    @Override
    public void setBackgroundResource(int resId) {
//        backgroundDrawable = getContext().getDrawable(resId);
//        invalidate();
    }

    @Override
    public void setBackground(Drawable background) {
//        backgroundDrawable = background;
//        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        if (backgroundDrawable!=null){
//            backgroundDrawable.setBounds(0,0,getWidth(),getHeight());
//            backgroundDrawable.draw(canvas);
//        }
//


        if (borderWidth>0) drawBorder(canvas);

        drawTextView(canvas);
        super.onDraw(canvas);
    }

    private void drawBorder(Canvas canvas){

        borderRect.set(0,0,getWidth(),getHeight());
        borderRect.inset(borderWidth/2f,borderWidth/2f);

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);

        cornerRadius = Math.min(cornerRadius,Math.min(getWidth()/2.0f,getHeight()/2.0f));

        canvas.drawRoundRect(borderRect,cornerRadius,cornerRadius,borderPaint);
    }

    private void drawTextView(Canvas canvas){
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        textViewPaint.setColor(backgroundColor);

        textViewRect.set(0,0,width,height);
        textViewRect.inset(borderWidth,borderWidth);

        float innerCornerRadius  = (cornerRadius/(Math.min(getHeight(),getWidth())))* (Math.min(textViewRect.height(),textViewRect.width()));
        canvas.drawRoundRect(textViewRect,innerCornerRadius,innerCornerRadius,textViewPaint);
    }

    private int toPixel(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public int getBorderColor() {
        return borderColor;
    }


}
