package com.jancar.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.jancar.launcher.bean.CellBean;
import com.jancar.launcher.bean.PageBean;
import com.jancar.launcher.launcherview.viewpager.LauncherView;
import com.jancar.launcher.launcherview.viewpager.NavForViewPager;
import com.jancar.launcher.utils.GsonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends Activity {
    private LauncherView launcherView;
    private NavForViewPager naviForViewPager;
//    private ICManager mICManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        mICManager = ICManager.getICManager();
//        mICManager.connect(this, new ICManager.ServiceListener() {
//            @Override
//            public void onStateChange(int i) {
//                ICMessage msg = ICMessage.obtain();
//                msg.setCMD(ICMessage.CMD_REGISTER_APP).setInteger(ICMessage.SourceIndex_Launcher);
//                mICManager.talkWithService(msg);
//            }
//
//            @Override
//            public ICMessage handleMessage(ICMessage icMessage) {
//                return null;
//            }
//        });

        setContentView(R.layout.activity_main);


        launcherView = (LauncherView) findViewById(R.id.ac_main_launcherview);
        naviForViewPager = (NavForViewPager) findViewById(R.id.ac_main_navforviewpager);
        String jsonStr = getAssetFileText("data.json",this);
        PageBean pageBean = GsonUtils.json2Object(jsonStr,PageBean.class);

        if(pageBean!=null&&pageBean.cells!=null) {
            //排序
            Collections.sort(pageBean.cells, new Comparator<CellBean>() {
                @Override
                public int compare(CellBean lhs, CellBean rhs) {
                    return lhs.sort - rhs.sort;
                }
            });
            launcherView.setData(pageBean);
            naviForViewPager.setViewPager(launcherView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        return;
    }
    public static String getAssetFileText(String fileName,Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
