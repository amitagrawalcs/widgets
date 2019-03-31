package com.amit.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;


public class CustomEditText extends AppCompatEditText {

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText);

        String fontPath = attributes.getString(R.styleable.CustomEditText_custom_font);
        boolean numbers = attributes.getBoolean(R.styleable.CustomEditText_numbers,true);
        attributes.recycle();
        if (fontPath!=null){
            this.setTypeface(Typeface.createFromAsset(getContext().getAssets(),fontPath));
        }

        if (!numbers){
            addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String ss = s.toString();
                    if (containsDigit(ss)){
                        String newName = ss.substring(0, s.length() - 1);
                        setText(newName);
                        setSelection(newName.length());
                    }
                }
            });
        }


    }

    private static boolean containsDigit(String s){
        if (s.contains("0")) return true;
        if (s.contains("1")) return true;
        if (s.contains("2")) return true;
        if (s.contains("3")) return true;
        if (s.contains("4")) return true;
        if (s.contains("5")) return true;
        if (s.contains("6")) return true;
        if (s.contains("7")) return true;
        if (s.contains("8")) return true;
        return s.contains("9");
    }

}
