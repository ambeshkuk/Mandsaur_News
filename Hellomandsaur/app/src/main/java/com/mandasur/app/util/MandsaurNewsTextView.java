package com.mandasur.app.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ambesh on 11-02-2017.
 */
public class MandsaurNewsTextView extends TextView {
    public MandsaurNewsTextView(Context context) {
        super(context);
    }

    public MandsaurNewsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MandsaurNewsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setTypeface(Typeface tf) {




        Typeface typeface=ActivityUtil.getFontAwesomeTypeFace(getContext());
        super.setTypeface(typeface);
    }
}
