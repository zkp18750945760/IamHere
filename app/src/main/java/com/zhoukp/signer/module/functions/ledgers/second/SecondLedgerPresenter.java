package com.zhoukp.signer.module.functions.ledgers.second;

import android.util.Log;

import com.zhoukp.signer.utils.BaseApi;
import com.zhoukp.signer.utils.Constant;

/**
 * @author zhoukp
 * @time 2018/3/28 14:27
 * @email 275557625@qq.com
 * @function
 */

public class SecondLedgerPresenter {

    private SecondLedgerView secondLedgerView;

    /**
     * 绑定视图
     *
     * @param secondLedgerView secondLedgerView
     */
    public void attachView(SecondLedgerView secondLedgerView) {
        this.secondLedgerView = secondLedgerView;
    }

    /**
     * 获取对应学生对应年份的第二台账数据
     *
     * @param userId 用户ID
     * @param year   年份
     */
    public void getSecondLedger(Integer userId, int year) {
        secondLedgerView.showLoadingView();

        BaseApi.request(BaseApi.createApi(ISecondLedgerApi.class).getSecondLedger(userId, year),
                new BaseApi.IResponseListener<SecondLedgerBean>() {
                    @Override
                    public void onSuccess(SecondLedgerBean data) {
                        Log.e("zkp", "getSecondLedger==" + data.getStatus());
                        if (data.getStatus() == Constant.SUCCESS_CODE) {
                            secondLedgerView.getLedgerSuccess(data);
                        } else {
                            secondLedgerView.getLedgerError(data.getStatus());
                        }
                        secondLedgerView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        secondLedgerView.getLedgerError(100);
                        secondLedgerView.hideLoadingView();
                    }
                });
    }

    /**
     * 解绑视图
     */
    public void detachView() {
        this.secondLedgerView = null;
    }
}
