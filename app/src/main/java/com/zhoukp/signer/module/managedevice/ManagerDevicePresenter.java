package com.zhoukp.signer.module.managedevice;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.BaseApi;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.DeviceUuidFactory;

/**
 * @author zhoukp
 * @time 2018/3/18 19:40
 * @email 275557625@qq.com
 * @function ManagerDevicePresenter
 */

public class ManagerDevicePresenter {

    private ManageDeviceView manageDeviceView;

    public void attachView(ManageDeviceView manageDeviceView) {
        this.manageDeviceView = manageDeviceView;
    }

    /**
     * 解绑设备
     *
     * @param userId 用户ID
     */
    public void unBindDevice(Integer userId) {
        manageDeviceView.showLoadingView();
        BaseApi.request(BaseApi.createApi(ManageDeviceApi.class).unBindDevice(userId),
                new BaseApi.IResponseListener<DeviceBean>() {
                    @Override
                    public void onSuccess(DeviceBean data) {
                        Log.e("zkp", data.getStatus() + "");
                        if (data.getStatus() == Constant.SUCCESS_CODE) {
                            manageDeviceView.unBindSuccess();
                        } else if (data.getStatus() == 101) {
                            manageDeviceView.unBindLater();
                        } else {
                            manageDeviceView.unBindError(-1);
                        }
                        manageDeviceView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        manageDeviceView.unBindError(-1);
                        manageDeviceView.hideLoadingView();
                    }
                });
    }

    /**
     * 绑定设备
     *
     * @param userId 用户ID
     * @param UUID   UUID
     */
    public void bindDevice(Integer userId, String UUID) {
        manageDeviceView.showLoadingView();
        BaseApi.request(BaseApi.createApi(ManageDeviceApi.class).bindDevice(userId, UUID),
                new BaseApi.IResponseListener<DeviceBean>() {
                    @Override
                    public void onSuccess(DeviceBean data) {
                        Log.e("zkp", data.getStatus() + "");
                        if (data.getStatus() == Constant.SUCCESS_CODE) {
                            manageDeviceView.bindSuccess();
                        } else if (data.getStatus() == 101) {
                            manageDeviceView.unBindFirst();
                        } else {
                            manageDeviceView.bindError(-1);
                        }
                        manageDeviceView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        manageDeviceView.bindError(-1);
                        manageDeviceView.hideLoadingView();
                    }
                });
    }

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param stuPassword 旧密码
     * @param newPassword 新密码
     */
    public void modifyPassword(Integer userId, String stuPassword, String newPassword) {
        manageDeviceView.showLoadingView();
        if (TextUtils.isEmpty(stuPassword) || TextUtils.isEmpty(newPassword)) {
            //旧密码或者新密码为空
            manageDeviceView.inputPassword();
            manageDeviceView.hideLoadingView();
            return;
        }
        Log.e("zkp", stuPassword);
        Log.e("zkp", newPassword);
        BaseApi.request(BaseApi.createApi(ManageDeviceApi.class).modifyPassword(userId, stuPassword, newPassword),
                new BaseApi.IResponseListener<LoginBean>() {
                    @Override
                    public void onSuccess(LoginBean data) {
                        Log.e("zkp", data.getStatus() + "");
                        if (data.getStatus() == Constant.SUCCESS_CODE) {
                            //修改密码成功
                            manageDeviceView.modifySuccess();
                            //存储新的用户信息
                            UserUtil.getInstance().removeUser();
                            //提示用户重新登录
                            manageDeviceView.reLogin();
                        } else if (data.getStatus() == 101) {
                            //旧密码错误
                            manageDeviceView.passWordError();
                        } else {
                            manageDeviceView.modifyError(-1);
                        }
                        manageDeviceView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        manageDeviceView.modifyError(-1);
                        manageDeviceView.hideLoadingView();
                    }
                });
    }

    /**
     * 获取设备序列号
     *
     * @return 序列号
     */
    public String getSerialNo(Context context) {
        return DeviceUuidFactory.getUDID(context);
    }

    public void detachView() {
        this.manageDeviceView = null;
    }
}
