package com.zhoukp.signer.utils;

import android.os.Environment;

/**
 * @author zhoukp
 * @time 2018/3/15 19:28
 * @email 275557625@qq.com
 * @function 常量类
 */

public class Constant {
        public static final String BaseUrl = "http://192.168.191.2:8080/Signer/";
//      public static final String BaseUrl = "http://120.79.157.43/Signer/";
//    public static final String BaseUrl = "http://192.168.191.1:8080";
    public static final String appPath = Environment.getExternalStorageDirectory() + "/我在/img/";
    public static final String appFilePath = Environment.getExternalStorageDirectory() + "/我在/files/";
    public static final String appCrashPath = Environment.getExternalStorageDirectory() + "/我在/crashs/";

    public static final int Login = 101;
    public static final int EditData = 102;

    public static final int HTTP_TIME_OUT = 5000;

    public static final int PERMISSION_REQUEST_CODE = 1997;

    public static final String DEFAULT_CHANNEL_ID = "appUpdate";

    public static final String DEFAULT_CHANNEL_NAME = "版本更新";

    public static final String THREAD_NAME = "app update thread";

    public static final String PROGRESS = "progress";

    public static final String APK_SUFFIX = ".apk";



    /**
     * 请求成功
     */
    public static final int SUCCESS_CODE = 100;

    /**
     * 请求失败
     */
    public static final int FAILED_CODE = 101;

    /**
     * 参数类型错误
     */
    public static final int PARAMETER_TYPE_ERROR_CODE = 102;

    /**
     * 服务器错误
     */
    public static final int SERVER_ERROR_CODE = 103;

    /**
     * 未知错误
     */
    public static final int UNKONW_ERROR_CODE = 104;

    /**
     * 服务不存在
     */
    public static final int SERVICE_NOT_EXIST_ERROR_CODE = 105;
}
