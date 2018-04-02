package com.zhoukp.signer.module.functions.sign;


import android.util.Log;

import com.zhoukp.signer.utils.BaseApi;
import com.zhoukp.signer.utils.Constant;

/**
 * @author zhoukp
 * @time 2018/3/15 22:33
 * @email 275557625@qq.com
 * @function SignPresenter
 */

public class SignPresenter {
    private SignView signView;

    /**
     * 绑定视图
     *
     * @param signView SignView
     */
    public void attachView(SignView signView) {
        this.signView = signView;
    }

    /**
     * 获取对应用户的所有签到事项
     * 课程(上课前10分钟-上课后5分钟)、会议(开会前15分钟-开会后10分钟)
     *
     * @param userId 用户ID
     */
    public void getSignEvents(int userId, boolean isAll) {
        signView.showLoadingView();

        BaseApi.request(BaseApi.createApi(ISignApi.class).getSignEvents(userId, isAll),
                new BaseApi.IResponseListener<SignEventsBean>() {
                    @Override
                    public void onSuccess(SignEventsBean data) {
                        Log.e("zkp", "getSignEvents==" + data.getStatus());
                        if (data.getStatus() == Constant.SUCCESS_CODE) {
                            signView.getSignEventsSuccess(data);
                        } else {
                            signView.getSignEventsError(data.getStatus());
                        }
                        signView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        signView.getSignEventsError(-1);
                        signView.hideLoadingView();
                    }
                });
    }

    /**
     * 发起签到
     *
     * @param scheduleId scheduleId
     * @param userId     用户ID
     * @param longitude  经度
     * @param latitude   纬度
     */
    public void sponsorSign(int scheduleId, int userId, double longitude, double latitude, int distance) {

        signView.showLoadingView();

        BaseApi.request(BaseApi.createApi(ISignApi.class).sponsorSign(scheduleId, userId, longitude, latitude, distance),
                new BaseApi.IResponseListener<SponsorSignBean>() {
                    @Override
                    public void onSuccess(SponsorSignBean data) {
                        Log.e("zkp", "sponsorSign==" + data.getStatus());
                        if (data.getStatus() == Constant.SUCCESS_CODE) {
                            signView.sponsorSignSuccess();
                        } else {
                            signView.sponsorSignError(data.getStatus());
                        }
                        signView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        signView.sponsorSignError(-1);
                        signView.hideLoadingView();
                    }
                });
    }

    /**
     * 发起签到
     *
     * @param scheduleId scheduleId
     * @param userId     用户ID
     * @param longitude  经度
     * @param latitude   纬度
     */
    public void sponsorSignActivity(int scheduleId, int userId, double longitude, double latitude, int distance) {

        signView.showLoadingView();

        BaseApi.request(BaseApi.createApi(ISignApi.class).sponsorSignActivity(scheduleId, userId, longitude, latitude, distance),
                new BaseApi.IResponseListener<SponsorSignBean>() {
                    @Override
                    public void onSuccess(SponsorSignBean data) {
                        Log.e("zkp", "sponsorSign==" + data.getStatus());
                        if (data.getStatus() == Constant.SUCCESS_CODE) {
                            signView.sponsorSignSuccess();
                        } else {
                            signView.sponsorSignError(data.getStatus());
                        }
                        signView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        signView.sponsorSignError(-1);
                        signView.hideLoadingView();
                    }
                });
    }

    /**
     * 签到
     *
     * @param userId    用户ID
     * @param longitude 经度
     * @param latitude  纬度
     */
    public void sign(int scheduleId, int userId, double longitude, double latitude) {

        signView.showLoadingView();

        BaseApi.request(BaseApi.createApi(ISignApi.class).sign(scheduleId, userId, longitude, latitude),
                new BaseApi.IResponseListener<SponsorSignBean>() {
                    @Override
                    public void onSuccess(SponsorSignBean data) {
                        Log.e("zkp", "sign==" + data.getStatus());
                        if (data.getStatus() == Constant.SUCCESS_CODE) {
                            signView.signSuccess();
                        } else {
                            signView.signError(data.getStatus());
                        }
                        signView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        signView.signError(-1);
                        signView.hideLoadingView();
                    }
                });
    }

    /**
     * 签到
     *
     * @param userId    用户ID
     * @param longitude 经度
     * @param latitude  纬度
     */
    public void signActivity(int activityId, int userId, double longitude, double latitude) {

        signView.showLoadingView();

        BaseApi.request(BaseApi.createApi(ISignApi.class).signActivity(activityId, userId, longitude, latitude),
                new BaseApi.IResponseListener<SponsorSignBean>() {
                    @Override
                    public void onSuccess(SponsorSignBean data) {
                        Log.e("zkp", "sign==" + data.getStatus());
                        if (data.getStatus() == Constant.SUCCESS_CODE) {
                            signView.signSuccess();
                        } else {
                            signView.signError(data.getStatus());
                        }
                        signView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        signView.signError(-1);
                        signView.hideLoadingView();
                    }
                });
    }

    /**
     * 获取某个事项已签到人的头像
     *
     * @param userId 用户ID
     */
    public void getSignedHeadIcons(int scheduleId, int userId) {

        signView.showLoadingView();

        BaseApi.request(BaseApi.createApi(ISignApi.class).getSignedHeadIcons(scheduleId, userId),
                new BaseApi.IResponseListener<SignedHeadIconsBean>() {
                    @Override
                    public void onSuccess(SignedHeadIconsBean data) {
                        Log.e("zkp", "getSignedHeadIcons==" + data.getStatus());
                        if (data.getStatus() == Constant.SUCCESS_CODE) {
                            signView.getSignedHeadIconsSuccess(data);
                        } else {
                            signView.getSignedHeadIconsError(data.getStatus());
                        }
                        signView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        signView.getSignedHeadIconsError(-1);
                        signView.hideLoadingView();
                    }
                });

    }


    /**
     * 解绑视图
     */
    public void detachView() {
        this.signView = null;
    }
}
