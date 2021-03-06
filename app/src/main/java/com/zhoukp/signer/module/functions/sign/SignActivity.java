package com.zhoukp.signer.module.functions.sign;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.zhoukp.signer.R;
import com.zhoukp.signer.adapter.MeetingRecyclerViewAdapter;
import com.zhoukp.signer.module.functions.ledgers.scanxls.ProgressDialog;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.LocationUtils;
import com.zhoukp.signer.utils.SchoolYearUtils;
import com.zhoukp.signer.utils.TimeUtils;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.WindowUtils;
import com.zhoukp.signer.viewpager.CommonViewPager;
import com.zhoukp.signer.viewpager.ViewPagerHolder;
import com.zhoukp.signer.viewpager.ViewPagerHolderCreator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * @author zhoukp
 * @time 2018/2/1 21:15
 * @email 275557625@qq.com
 * @function 签到页面
 */

public class SignActivity extends AppCompatActivity implements View.OnClickListener, SignView {

    @Bind(R.id.tvStartSign)
    TextView tvStartSign;
    @Bind(R.id.ivSignRecord)
    ImageView ivSignRecord;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.pagerEvents)
    CommonViewPager<SignEventsBean.DataBean> pagerEvents;
    @Bind(R.id.tvSign)
    TextView tvSign;
    @Bind(R.id.rlMeetingTheme)
    RelativeLayout rlMeetingTheme;
    @Bind(R.id.ivSignSuccess)
    ImageView ivSignSuccess;
    @Bind(R.id.rlSignSuccess)
    RelativeLayout rlSignSuccess;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private ProgressDialog dialog;

    private LoginBean.UserBean user;
    private List<SignEventsBean.DataBean> signEventsList;
    private SignPresenter presenter;

    private LocationUtils locationUtils;
    private KpLocationListener locationListener;
    private int flag;

    private MeetingRecyclerViewAdapter adapter;

    private Handler sponsorSignHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            SignEventsBean.DataBean dataBean;
            switch (msg.what) {
                case 0:
                    //课堂
                    locationUtils.getLocationClient().stop();
                    locationUtils.getLocationClient().unRegisterLocationListener(locationListener);
                    dataBean = signEventsList.get(pagerEvents.getCurrentItem());
                    presenter.sponsorSign(dataBean.getId(), user.getUserId(),
                            locationUtils.getLongitude(),
                            locationUtils.getLatitude(),
                            (int) locationUtils.getRadius());
                    sponsorSignHandler.removeMessages(flag);
                    break;
                case 1:
                case 2:
                case 3:
                    //会议
                    locationUtils.getLocationClient().stop();
                    locationUtils.getLocationClient().unRegisterLocationListener(locationListener);
                    dataBean = signEventsList.get(pagerEvents.getCurrentItem());
                    presenter.sponsorSignActivity(dataBean.getId(), user.getUserId(),
                            locationUtils.getLongitude(),
                            locationUtils.getLatitude(),
                            (int) locationUtils.getRadius());
                    sponsorSignHandler.removeMessages(flag);
                    break;
            }
        }
    };

    private Handler signHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
             SignEventsBean.DataBean dataBean;
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //课堂签到
                    locationUtils.getLocationClient().stop();
                    locationUtils.getLocationClient().unRegisterLocationListener(locationListener);
                    dataBean = signEventsList.get(pagerEvents.getCurrentItem());
                    presenter.sign(dataBean.getId(), user.getUserId(),
                            locationUtils.getLongitude(),
                            locationUtils.getLatitude());
                    break;
                case 1:
                case 2:
                case 3:
                    locationUtils.getLocationClient().stop();
                    locationUtils.getLocationClient().unRegisterLocationListener(locationListener);
                    dataBean = signEventsList.get(pagerEvents.getCurrentItem());
                    presenter.signActivity(dataBean.getId(), user.getUserId(),
                            locationUtils.getLongitude(),
                            locationUtils.getLatitude());
                    signHandler.removeMessages(flag);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);

        initViews();

        initVariates();

        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initViews() {
        //recyclerView
        RecyclerView.LayoutManager manager =
                new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        //设置recyclerView动画为默认动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        rlSignSuccess = findViewById(R.id.rlSignSuccess);
        if (UserUtil.getInstance().getUser().getUserDuty() != null
                && UserUtil.getInstance().getUser().getUserDuty().equals("班长")) {
            tvStartSign.setVisibility(View.VISIBLE);
        } else {
            tvStartSign.setVisibility(View.GONE);
        }
    }

    private void initVariates() {

        user = UserUtil.getInstance().getUser();
        locationUtils = new LocationUtils(this);
        locationListener = new KpLocationListener();

        presenter = new SignPresenter();
        presenter.attachView(this);
        presenter.getSignEvents(user.getUserId(), false);
    }

    private void initEvent() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.tvStartSign, R.id.tvSign})
    public void onViewClicked(View view) {
        SignEventsBean.DataBean dataBean;
        switch (view.getId()) {
            case R.id.tvStartSign:
                //发起签到
                dataBean = signEventsList.get(pagerEvents.getCurrentItem());
                //课堂签到
                locationUtils.getLocationClient().registerLocationListener(locationListener);
                locationUtils.getLocationClient().start();
                flag = dataBean.getType();
                sponsorSignHandler.sendEmptyMessageDelayed(flag, 2000);
                break;
            case R.id.tvSign:
                dataBean = signEventsList.get(pagerEvents.getCurrentItem());
                locationUtils.getLocationClient().registerLocationListener(locationListener);
                locationUtils.getLocationClient().start();
                flag = dataBean.getType();
                signHandler.sendEmptyMessageDelayed(flag, 2000);
                break;
        }
    }

    public class KpLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            //获取纬度信息
            locationUtils.setLatitude(location.getLatitude());
            //获取经度信息
            locationUtils.setLongitude(location.getLongitude());

            //获取定位精度，默认值为0.0f
            locationUtils.setRadius(location.getRadius());
        }
    }

    @Override
    public void hideLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.showMessage("加载中");
        }
        dialog.dismiss();
    }

    @Override
    public void showLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.showMessage("加载中");
        }
        dialog.show();
    }

    @Override
    public void getSignEventsSuccess(SignEventsBean bean) {
        signEventsList = new ArrayList<>();
        for (int i = 0; i < bean.getData().size(); i++) {
            signEventsList.add(bean.getData().get(i));
        }
        pagerEvents.setIndicatorVisible(false);
        pagerEvents.setPages(signEventsList, new ViewPagerHolderCreator<ViewPagerHolder>() {
            @Override
            public ViewPagerHolder createViewHolder() {
                return new SignEventsViewHolder();
            }
        });

        if (signEventsList.size() > 0) {
            SignEventsBean.DataBean dataBean = signEventsList.get(pagerEvents.getCurrentItem());
            presenter.getSignedHeadIcons(1, user.getUserId());
        }
    }

    @Override
    public void getSignEventsError(int status) {
        switch (status) {
            case Constant.FAILED_CODE:
                ToastUtil.showToast(this, "请求签到事项失败");
                break;
            case Constant.PARAMETER_TYPE_ERROR_CODE:
                ToastUtil.showToast(this, "参数类型错误");
                break;
            case Constant.SERVER_ERROR_CODE:
                ToastUtil.showToast(this, "服务器错误");
                break;
            case Constant.UNKONW_ERROR_CODE:
                ToastUtil.showToast(this, "未知错误");
                break;
            case Constant.SERVICE_NOT_EXIST_ERROR_CODE:
                ToastUtil.showToast(this, "服务不存在");
                break;
            case -1:
                ToastUtil.showToast(this, "请求失败");
                break;
        }
    }

    @Override
    public void sponsorSignSuccess() {
        ToastUtil.showToast(this, "发起签到成功");
    }

    @Override
    public void sponsorSignError(int status) {
        switch (status) {
            case Constant.FAILED_CODE:
                ToastUtil.showToast(this, "发起签到失败");
                break;
            case Constant.PARAMETER_TYPE_ERROR_CODE:
                ToastUtil.showToast(this, "参数类型错误");
                break;
            case Constant.SERVER_ERROR_CODE:
                ToastUtil.showToast(this, "服务器错误");
                break;
            case Constant.UNKONW_ERROR_CODE:
                ToastUtil.showToast(this, "未知错误");
                break;
            case Constant.SERVICE_NOT_EXIST_ERROR_CODE:
                ToastUtil.showToast(this, "服务不存在");
                break;
            case -1:
                ToastUtil.showToast(this, "请求失败");
                break;
        }
    }

    @Override
    public void getEventsSponsorSignStatusSuccess(int status) {
        switch (status) {
            case 201:
                //还未发起签到
                locationUtils.getLocationClient().registerLocationListener(locationListener);
                locationUtils.getLocationClient().start();
                flag = 1;
                signHandler.sendEmptyMessageDelayed(flag, 2000);
                break;
            case 202:
                //已经发起签到
                ToastUtil.showToast(this, "已经发起签到了哦~");
                break;
        }
    }

    @Override
    public void getEventsSponsorSignStatusError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(this, "请求发起签到状态失败");
                break;
            case 101:
                ToastUtil.showToast(this, "数据库IO错误");
                break;
        }
    }

    @Override
    public void signSuccess() {
        rlMeetingTheme.setVisibility(View.GONE);
        rlSignSuccess.setVisibility(View.VISIBLE);
        SignEventsBean.DataBean dataBean = signEventsList.get(pagerEvents.getCurrentItem());
        presenter.getSignedHeadIcons(1, user.getUserId());
    }

    @Override
    public void signError(int status) {
        switch (status) {
            case Constant.FAILED_CODE:
                ToastUtil.showToast(this, "签到失败");
                break;
            case Constant.PARAMETER_TYPE_ERROR_CODE:
                ToastUtil.showToast(this, "参数类型错误");
                break;
            case Constant.SERVER_ERROR_CODE:
                ToastUtil.showToast(this, "服务器错误");
                break;
            case Constant.UNKONW_ERROR_CODE:
                ToastUtil.showToast(this, "未知错误");
                break;
            case Constant.SERVICE_NOT_EXIST_ERROR_CODE:
                ToastUtil.showToast(this, "服务不存在");
                break;
            case -1:
                ToastUtil.showToast(this, "请求失败");
                break;
        }
    }

    @Override
    public void getSignedHeadIconsSuccess(SignedHeadIconsBean bean) {
        adapter = new MeetingRecyclerViewAdapter(this, bean);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getSignedHeadIconsError(int status) {
        switch (status) {
            case Constant.FAILED_CODE:
                ToastUtil.showToast(this, "获取已签到头像失败");
                break;
            case Constant.PARAMETER_TYPE_ERROR_CODE:
                ToastUtil.showToast(this, "参数类型错误");
                break;
            case Constant.SERVER_ERROR_CODE:
                ToastUtil.showToast(this, "服务器错误");
                break;
            case Constant.UNKONW_ERROR_CODE:
                ToastUtil.showToast(this, "未知错误");
                break;
            case Constant.SERVICE_NOT_EXIST_ERROR_CODE:
                ToastUtil.showToast(this, "服务不存在");
                break;
            case -1:
                ToastUtil.showToast(this, "请求失败");
                break;
        }
    }

    private class SignEventsViewHolder implements ViewPagerHolder<SignEventsBean.DataBean> {

        private TextView tvWeek, tvDate, tvTime, tvTheme;
        private CountdownView countdownView;

        @Override
        public View createView(Context context) {
            View view = View.inflate(context, R.layout.sign_events_holder, null);
            tvWeek = view.findViewById(R.id.tvWeek);
            tvDate = view.findViewById(R.id.tvDate);
            tvTime = view.findViewById(R.id.tvTime);
            tvTheme = view.findViewById(R.id.tvTheme);
            countdownView = view.findViewById(R.id.countdownView);
            return view;
        }

        @Override
        public void onBind(final Context context, int position, final SignEventsBean.DataBean data) {
            if (data.getWeek() != null) {
                tvWeek.setText(data.getWeek().toString());
            }
            tvTheme.setText(data.getContent());
            tvDate.setText(TimeUtils.getCurrentDate());
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long timeLag = 0;
            try {
                timeLag = sDateFormat.parse(TimeUtils.getCurrentTime()).getTime() -
                        sDateFormat.parse(TimeUtils.getCurrentDate() + " " + data.getTime()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            countdownView.start(timeLag);

            if (data.getType() == 1) {
                //课堂签到
                tvTime.setText(SchoolYearUtils.getClassBeginTime(data.getTime()));

            } else if (data.getType() == 2) {
                //会议签到
                tvTime.setText(data.getTime().substring(11));

            }

            countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                @Override
                public void onEnd(CountdownView countdownView) {
                    String timeEnd = "";
                    if (data.getType() == 1) {
                        timeEnd = "5分钟";
                    } else if (data.getType() == 2) {
                        timeEnd = "10分钟";
                    }
                    ToastUtil.showToast(context, data.getContent() + "签到还有" + timeEnd + "截止,请尽快签到");
                }
            });
        }
    }
}
