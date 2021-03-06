package com.flyzebra.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.os.Build;
import android.os.UserHandle;
import android.os.UserManager;

import com.android.launcher3.AppInfo;
import com.android.launcher3.IconCache;
import com.android.launcher3.compat.LauncherActivityInfoCompat;
import com.android.launcher3.compat.LauncherAppsCompat;
import com.android.launcher3.compat.UserHandleCompat;
import com.android.launcher3.compat.UserManagerCompat;
import com.jancar.launcher.data.Const;

import java.util.ArrayList;
import java.util.List;

public class PMUtils {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static List<UserHandle> getUserProfiles(Context context) {
        UserManager mUserManager = (UserManager) context.getSystemService(Context.USER_SERVICE);
        return mUserManager == null ? null : mUserManager.getUserProfiles();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static List<LauncherActivityInfo> getAllLauncgerActivitys(Context context) {
        List<LauncherActivityInfo> retLst = new ArrayList<>();
        LauncherApps launcherApps = (LauncherApps) context.getSystemService(Context.LAUNCHER_APPS_SERVICE);
        if (launcherApps == null) return retLst;
        List<UserHandle> userHandles = getUserProfiles(context);
        if(userHandles==null) return retLst;
        for (UserHandle userHandle : userHandles) {
            List<LauncherActivityInfo> addList = launcherApps.getActivityList(null, userHandle);
            if (addList != null && !addList.isEmpty()) {
                retLst.addAll(addList);
            }
        }
        return retLst;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static List<LauncherActivityInfo> getLauncgerActivitys(String packageName, Context context) {
        List<LauncherActivityInfo> retLst = new ArrayList<>();
        LauncherApps launcherApps = (LauncherApps) context.getSystemService(Context.LAUNCHER_APPS_SERVICE);
        if (launcherApps == null) return retLst;
        List<UserHandle> userHandles = getUserProfiles(context);
        if(userHandles==null) return retLst;
        for (UserHandle userHandle : userHandles) {
            List<LauncherActivityInfo> addList = launcherApps.getActivityList(packageName, userHandle);
            if (addList != null && !addList.isEmpty()) {
                retLst.addAll(addList);
            }
        }
        return retLst;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static List<AppInfo> getAppInfos(String packageName, Context context, IconCache iconCache) {
        List<AppInfo> retList = new ArrayList<>();
        LauncherAppsCompat mLauncherApps = LauncherAppsCompat.getInstance(context);
        if (mLauncherApps == null) return retList;
        UserManagerCompat mUserManager = UserManagerCompat.getInstance(context);
        if(mUserManager==null) return retList;
        List<UserHandleCompat> userHandles = mUserManager.getUserProfiles();
        if(userHandles==null) return retList;
        for (UserHandleCompat userHandle : userHandles) {
            List<LauncherActivityInfoCompat> addList = mLauncherApps.getActivityList(packageName, userHandle);
            if (addList != null) {
                for(LauncherActivityInfoCompat info:addList){
                    /**
                     * 过滤掉包名列表
                     */
                    boolean flag = false;
                    for(String packName:Const.FILTER_PACKNAMES){
                        if(packName.indexOf(info.getComponentName().getPackageName())==0){
                            FlyLog.d("FILTER_PACKNAMES packName=%s,className=%s",
                                    info.getComponentName().getPackageName(),info.getComponentName().getClassName());
                            flag = true;
                            break;
                        }
                    }
                    if(flag) continue;
                    retList.add((new AppInfo(context, info, userHandle, iconCache)));
                }
            }
        }
        return retList;
    }

}
