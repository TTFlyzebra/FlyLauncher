package com.jancar.launcher.launcherview.flyview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class FlyTextView extends TextView{
    public FlyTextView(Context context) {
        super(context);
    }

    public FlyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
