package com.zhoukp.signer.module.home;

import android.util.Log;

import com.zhoukp.signer.utils.BaseApi;
import com.zhoukp.signer.utils.Constant;

/**
 * @author zhoukp
 * @time 2018/3/22 20:49
 * @email 275557625@qq.com
 * @function HomePagerPresenter
 */

public class HomePagerPresenter {
    private HomePagerView homePagerView;

    /**
     * 绑定视图
     *
     * @param homePagerView homePagerView
     */
    public void attachView(HomePagerView homePagerView) {
        this.homePagerView = homePagerView;
    }


    public void getAllSchedule(int userId, boolean isAll) {
        homePagerView.showLoadingView();

        BaseApi.request(BaseApi.createApi(IHomePagerApi.class).getAllSchedule(userId, isAll),
                new BaseApi.IResponseListener<ScheduleBean>() {
                    @Override
                    public void onSuccess(ScheduleBean data) {
                        Log.e("zkp", "getAllSchedule==" + data.getStatus());
                        if (data.getStatus() == Constant.SUCCESS_CODE) {
                            homePagerView.getScheduleSuccess(data);
                        } else {
                            homePagerView.getScheduleError(data.getStatus());
                        }
                        homePagerView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        homePagerView.getScheduleError(-1);
                        homePagerView.hideLoadingView();
                    }
                });
    }

    public void getBanners() {
        homePagerView.showLoadingView();

        BaseApi.request(BaseApi.createApi(IHomePagerApi.class).getBanners(),
                new BaseApi.IResponseListener<BannerBean>() {
                    @Override
                    public void onSuccess(BannerBean data) {
                        Log.e("zkp", "getBanners==" + data.getStatus());
                        if (data.getStatus() == Constant.SUCCESS_CODE) {
                            homePagerView.getBannersSuccess(data);
                        } else {
                            homePagerView.getBannersError(data.getStatus());
                        }
                        homePagerView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        homePagerView.getBannersError(-1);
                        homePagerView.hideLoadingView();
                    }
                });
    }

    /**
     * 解绑视图
     */
    public void detachView() {
        this.homePagerView = null;
    }
}
