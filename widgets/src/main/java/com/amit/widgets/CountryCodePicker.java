package com.amit.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class CountryCodePicker extends com.hbb20.CountryCodePicker {
    public CountryCodePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CountryCodePicker);
        String fontPath = attributes.getString(R.styleable.CountryCodePicker_custom_font);
        attributes.recycle();

        if (fontPath!=null){
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),fontPath);
            setTypeFace(typeface);
        }

    }
}
