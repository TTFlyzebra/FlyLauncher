package com.jancar.launcher.launcherview.pageview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

import com.jancar.launcher.bean.CellBean;
import com.jancar.launcher.bean.PageBean;
import com.jancar.launcher.launcherview.cellview.CellViewFactory;
import com.jancar.launcher.launcherview.cellview.ICellView;

import java.util.List;

public class SimplePageView extends FrameLayout implements IPage {
    private PageBean pageBean;
    private int width;
    private int height;

    public SimplePageView(Context context) {
        this(context, null);
    }

    public SimplePageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimplePageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public void setData(PageBean pageBean) {
        this.pageBean = pageBean;
        List<CellBean> appInfoList = pageBean.cells;
        if (appInfoList == null || appInfoList.isEmpty()) return;
        addAllItemView(appInfoList);
    }

    private void addAllItemView(List<CellBean> appInfoList) {
        requestLayout();
        int sx= 0;
        int sy = 0;
        if(width!=0){
            sx = (width - (pageBean.itemWidth+pageBean.itemPadding*2) * pageBean.columns) / 2;
        }
        if(height!=0){
            sy = (height - (pageBean.itemHeight+pageBean.itemPadding*2) * pageBean.rows) / 2;
        }
        for (int i = 0; i < appInfoList.size(); i++) {
            //多出的Cell不进行绘制
            if (i > pageBean.columns * pageBean.rows) break;
            CellBean appInfo = appInfoList.get(i);
            LayoutParams lp = new LayoutParams(pageBean.itemWidth, pageBean.itemHeight);
            lp.leftMargin = sx + pageBean.x + (i % pageBean.columns) * (pageBean.itemWidth + pageBean.itemPadding*2)+pageBean.itemPadding;
            lp.topMargin = sy + pageBean.y + (i / pageBean.columns) * (pageBean.itemHeight + pageBean.itemPadding*2)+pageBean.itemPadding;
            ICellView iCellView = CellViewFactory.createView(getContext(), appInfo);
            addView((View) iCellView, lp);

            //添加镜像
//            LayoutParams lpMirror = new LayoutParams(pageBean.itemWidth, pageBean.itemHeight);
//            lpMirror.leftMargin = lp.leftMargin;
//            lpMirror.topMargin = lp.topMargin+pageBean.itemHeight+12;
//            MirrorView mirrorView = new MirrorView(getContext());
//            mirrorView.setScaleType(ImageView.ScaleType.FIT_XY);
//            iCellView.setMirrorView(mirrorView);
//            addView(mirrorView,lpMirror);

            iCellView.notifyView();
        }
    }

}
