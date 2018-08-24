package com.jancar.launcher.launcherview.cellview;

import android.content.Context;

import com.jancar.launcher.bean.CellBean;

public interface ICellView {

    void initView(Context context);

    void setData(CellBean appInfo);

    void notifyView();

    void runAction();
}
