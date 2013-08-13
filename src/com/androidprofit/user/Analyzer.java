package com.androidprofit.user;

import android.content.Context;
import com.androidprofit.app.PackageInfo;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: sujoe
 * Date: 13-8-13
 * Time: 下午9:12
 * To change this template use File | Settings | File Templates.
 */
public class Analyzer {
    public static final int EVENT_TYPE_DOWNLOAD = 0;
    public static final int EVENT_TYPE_INSTALLED = 1;

    private static final String EVENT_DOWNLOAD_APP = "Download_App";

    /**
     * 统计app操作
     * @param ctx 上下文
     * @param pkg  包信息
     * @param event  事件类型 0:download 1:installed
     */
    public static void analysePackage(Context ctx, PackageInfo pkg, int event){
        String type = "";
        switch(event){
            case 0:
                type = "download";
                break;
            case 1:
                type = "installed";
                break;
        }
        HashMap<String,String> map = new HashMap<String,String>();
        map.put(type, pkg.getName());
        MobclickAgent.onEvent(ctx, EVENT_DOWNLOAD_APP, map);
    }
}
