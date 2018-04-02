package com.zhoukp.signer.module.activity.activities;

import android.view.View;
import android.widget.ListView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.activity.ActivityBean;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.view.ThreePointLoadingView;

/**
 * @author zhoukp
 * @time 2018/1/31 13:19
 * @email 275557625@qq.com
 * @function 文体活动页面
 */

public class ActivityFragment extends BaseFragment implements ActivityFragmentView {

    private ListView listView;
    private ThreePointLoadingView threePointLoadingView;

    private ActivityListViewAdapter adapter;
    private ActivityFragmentPresenter presenter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_sports_activity, null);
        listView = view.findViewById(R.id.listView);
        threePointLoadingView = view.findViewById(R.id.threePointLoadingView);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        presenter = new ActivityFragmentPresenter();
        presenter.attachView(this);
        presenter.getActivities(UserUtil.getInstance().getUser().getUserId(), 2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void showLoadingView() {
        threePointLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        threePointLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void getActivitiesSuccess() {
        ToastUtil.showToast(context, "加载数据成功");
    }

    @Override
    public void getData(ActivityBean bean) {
        adapter = new ActivityListViewAdapter(context, bean, presenter);
        listView.setAdapter(adapter);
    }

    @Override
    public void getActivitiesError(int status) {
        switch (status) {
            case Constant.FAILED_CODE:
                ToastUtil.showToast(context, "请求文体活动失败失败");
                break;
            case Constant.PARAMETER_TYPE_ERROR_CODE:
                ToastUtil.showToast(context, "参数类型错误");
                break;
            case Constant.SERVER_ERROR_CODE:
                ToastUtil.showToast(context, "服务器错误");
                break;
            case Constant.UNKONW_ERROR_CODE:
                ToastUtil.showToast(context, "未知错误");
                break;
            case Constant.SERVICE_NOT_EXIST_ERROR_CODE:
                ToastUtil.showToast(context, "服务不存在");
                break;
            case -1:
                ToastUtil.showToast(context, "请求失败");
                break;
        }
    }

    @Override
    public void applyActivitiesSuccess() {
        ToastUtil.showToast(context, "报名成功");
    }

    @Override
    public void applyActivitiesError() {
        ToastUtil.showToast(context, "报名失败");
    }

    @Override
    public void cancelApplySuccess() {
        ToastUtil.showToast(context, "取消报名成功");
    }

    @Override
    public void cancelApplyError() {
        ToastUtil.showToast(context, "取消报名失败");
    }

    @Override
    public void setData(ActivityBean data) {
        adapter.setBean(data);
        adapter.notifyDataSetChanged();
    }
}
