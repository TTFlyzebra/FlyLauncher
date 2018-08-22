package com.jancar.launcher.jancarview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jancar.launcher.R;
import com.jancar.launcher.utils.FlyLog;

public class SimpeCellView extends FrameLayout implements ICellView{
    private AppInfo appInfo;
    private FlyImageView imageView;
    private TextView textView;
    public SimpeCellView(Context context) {
        this(context,null);
    }

    public SimpeCellView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpeCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        FlyLog.d();
        imageView = new FlyImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(imageView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        textView = new FlyTextView(context);
        addView(textView,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        test();
    }

    private void test() {
        setData(new AppInfo());
        notifyView();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                runAction();
            }
        });
    }

    @Override
    public void setData(AppInfo appInfo) {
        FlyLog.d();
        this.appInfo = appInfo;
        textView.setGravity(Gravity.CENTER);
        try {
            textView.setTextColor(Color.parseColor(appInfo.textColor));
        }catch (Exception e){
            textView.setTextColor(0xffffffff);
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,appInfo.textSize);
        LayoutParams params = (LayoutParams) textView.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        params.leftMargin = appInfo.textLeft;
        params.topMargin = appInfo.textTop;
        params.rightMargin = appInfo.textRight;
        params.bottomMargin = appInfo.textBottom;
        textView.setLayoutParams(params);
        requestLayout();
    }

    @Override
    public void notifyView() {
        FlyLog.d();
        imageView.setImageResource(R.drawable.item1);
        textView.setText(appInfo.textTitle);
    }

    @Override
    public void runAction() {
        CommondTool.execStartPackage(getContext(),appInfo.packName,appInfo.className);
    }
}
