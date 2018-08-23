package com.jancar.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.jancar.launcher.bean.PageBean;
import com.jancar.launcher.launcherview.viewpager.LauncherView;
import com.jancar.launcher.launcherview.viewpager.NavForViewPager;
import com.jancar.launcher.utils.GsonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends Activity {
    private LauncherView launcherView;
    private NavForViewPager naviForViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        launcherView = (LauncherView) findViewById(R.id.ac_main_launcherview);
        naviForViewPager = (NavForViewPager) findViewById(R.id.ac_main_navforviewpager);
        String jsonStr = getAssetFileText("data.json",this);
        PageBean pageBean = GsonUtils.json2Object(jsonStr,PageBean.class);
        launcherView.setData(pageBean);
        naviForViewPager.setViewPager(launcherView);
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
