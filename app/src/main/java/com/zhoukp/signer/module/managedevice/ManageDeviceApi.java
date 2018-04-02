package com.zhoukp.signer.module.managedevice;

import com.zhoukp.signer.module.login.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/3/18 19:27
 * @email 275557625@qq.com
 * @function
 */

public interface ManageDeviceApi {

    //解绑设备
    @POST("android_action?serviceNo=10")
    Observable<DeviceBean> unBindDevice(
            @Query("userId") Integer userId
    );

    //绑定设备
    @POST("android_action?serviceNo=10")
    Observable<DeviceBean> bindDevice(
            @Query("userId") Integer userId,
            @Query("serialNo") String stuSerialNo
    );

    //修改密码
    @POST("android_action?serviceNo=9")
    Observable<LoginBean> modifyPassword(
            @Query("userId") Integer userId,
            @Query("password") String userPassword,
            @Query("newPassword") String newPassword
    );
}
