package com.jancar.launcher.launcherview.cellview;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jancar.launcher.R;
import com.jancar.launcher.bean.CellBean;
import com.jancar.launcher.utils.CommondUtils;
import com.jancar.launcher.launcherview.flyview.FlyImageView;
import com.jancar.launcher.launcherview.flyview.FlyTextView;
import com.jancar.launcher.utils.FlyLog;

public class SimpeCellView extends FrameLayout implements ICellView, View.OnTouchListener,View.OnClickListener {
    private CellBean appInfo;
    private FlyImageView imageView;
    private TextView textView;
    private Handler mHandler = new Handler();

    public SimpeCellView(Context context) {
        this(context, null);
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
        addView(imageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        textView = new FlyTextView(context);
        addView(textView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setOnClickListener(this);
        setOnTouchListener(this);
    }

    @Override
    public void setData(CellBean appInfo) {
        FlyLog.d();
        this.appInfo = appInfo;
        textView.setGravity(Gravity.CENTER);
        try {
            textView.setTextColor(Color.parseColor(appInfo.textColor));
        } catch (Exception e) {
            textView.setTextColor(0xffffffff);
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, appInfo.textSize);
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
        Glide.with(getContext()).load(appInfo.defaultImageUrl).into(imageView);
        textView.setText(appInfo.textTitle);
    }

    /**
     * 启动优先级，包名+类名>Action>包名
     */
    @Override
    public void runAction() {
        if(CommondUtils.execStartPackage(getContext(),appInfo.packName,appInfo.className)) return;
        if(CommondUtils.execStartActivity(getContext(),appInfo.action)) return;
        if(!CommondUtils.execStartPackage(getContext(),appInfo.packName)){
            Toast.makeText(getContext(),getContext().getResources().getString(R.string.startAppFailed),Toast.LENGTH_SHORT).show();
        }
    }

    private Runnable show = new Runnable() {
        @Override
        public void run() {
            setAlpha(1.0f);
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setAlpha(0.7f);
//            mHandler.removeCallbacks(show);
//            mHandler.postDelayed(show,500);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            setAlpha(1.0f);
        }
        if(event.getAction()==MotionEvent.ACTION_MOVE){
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();
            if (!isTouchPointInView(v, x, y)) {
                setAlpha(1.0f);
            }else{
                mHandler.removeCallbacks(show);
                mHandler.postDelayed(show,300);
                setAlpha(0.7f);
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        runAction();
    }

    @Override
    protected void onDetachedFromWindow() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }

    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        //view.isClickable() &&
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }
}
