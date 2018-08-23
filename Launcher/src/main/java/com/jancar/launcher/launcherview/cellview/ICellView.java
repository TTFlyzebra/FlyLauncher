package com.jancar.launcher.launcherview.cellview;

import com.jancar.launcher.bean.CellBean;

public interface ICellView {

    void setData(CellBean appInfo);

    void notifyView();

    void runAction();
}
