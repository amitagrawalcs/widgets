package com.amit.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

public class CustomRadioButton extends AppCompatRadioButton {

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomRadioButton);

        String fontPath = attributes.getString(R.styleable.CustomRadioButton_custom_font);
        attributes.recycle();

        if (fontPath!=null){
            this.setTypeface(Typeface.createFromAsset(getContext().getAssets(),fontPath));
        }
    }
}
