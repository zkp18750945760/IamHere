package com.zhoukp.signer.module.me;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.functions.ledgers.scanxls.ProgressDialog;
import com.zhoukp.signer.module.managedevice.ManageDeviceActivity;
import com.zhoukp.signer.module.login.LoginActivity;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.PermissionUtils;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.lrucache.RxImageLoader;
import com.zhoukp.signer.view.RoundRectImageView;


/**
 * @author zhoukp
 * @time 2018/1/30 21:18
 * @email 275557625@qq.com
 * @function 我的页面
 */

public class MePager extends BaseFragment implements View.OnClickListener, MePagerView {

    /**
     * 请求图片成功
     */
    public static final int SUCCESS = 1;
    /**
     * 图片请求失败
     */
    public static final int FAIL = 2;

    private RoundRectImageView ivHead;
    private TextView tvName, tvDuty;
    private RelativeLayout rlCollege, rlID, rlClass;
    private TextView tvID, tvClass, tvCollege;
    private Button btnExit, btnEditData;
    private TextView tvGoLogin;
    private LinearLayout llPersonInfo;

    private ProgressDialog dialog;

    public MePagerPresenter presenter;
    private String path;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_me, null);
        ivHead = view.findViewById(R.id.ivHead);
        tvName = view.findViewById(R.id.tvName);
        tvDuty = view.findViewById(R.id.tvDuty);
        rlCollege = view.findViewById(R.id.rlCollege);
        tvCollege = view.findViewById(R.id.tvCollege);
        rlID = view.findViewById(R.id.rlID);
        tvID = view.findViewById(R.id.tvID);
        rlClass = view.findViewById(R.id.rlClass);
        tvClass = view.findViewById(R.id.tvClass);
        btnExit = view.findViewById(R.id.btnExit);
        btnEditData = view.findViewById(R.id.btnEditData);
        tvGoLogin = view.findViewById(R.id.tvGoLogin);
        llPersonInfo = view.findViewById(R.id.llPersonInfo);

        if (context.getSharedPreferences("Signer", Context.MODE_PRIVATE).getBoolean("login", false)) {
            llPersonInfo.setVisibility(View.VISIBLE);
            tvGoLogin.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        presenter = new MePagerPresenter();
        presenter.attachView(this);

        refreshUI();

        //presenter.getHeadIcon(UserUtil.getInstance().getUser().getUserId());

        initEvent();
    }

    private void initEvent() {
        btnExit.setOnClickListener(this);
        btnEditData.setOnClickListener(this);
        rlCollege.setOnClickListener(this);
        rlID.setOnClickListener(this);
        rlClass.setOnClickListener(this);
        tvGoLogin.setOnClickListener(this);
        ivHead.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                ToastUtil.showToast(context, "退出");
                UserUtil.getInstance().removeUser();
                refreshUI();
                break;
            case R.id.btnEditData:
                context.startActivityForResult(new Intent(context, ManageDeviceActivity.class), Constant.EditData);
                break;
            case R.id.rlCollege:
                ToastUtil.showToast(context, "学院");
                break;
            case R.id.rlID:
                ToastUtil.showToast(context, "学号");
                break;
            case R.id.rlClass:
                ToastUtil.showToast(context, "班级");
                break;
            case R.id.tvGoLogin:
                context.startActivityForResult(new Intent(context, LoginActivity.class), Constant.Login);
                break;
            case R.id.ivHead:
                //修改头像
                presenter.selectPicture(context);
                break;
            default:
                break;
        }
    }

    /**
     * 登陆成功后刷新本页面的用户信息
     */
    public void refreshUI() {
        LoginBean.UserBean userBean = UserUtil.getInstance().getUser();
        if (userBean == null) {
            llPersonInfo.setVisibility(View.GONE);
            tvGoLogin.setVisibility(View.VISIBLE);
        } else {
            llPersonInfo.setVisibility(View.VISIBLE);
            tvGoLogin.setVisibility(View.GONE);
            ivHead.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_head));
            tvName.setText(userBean.getUserName());
            tvDuty.setText(userBean.getUserDuty());
            tvID.setText(userBean.getUserCollege());
            tvID.setText(userBean.getUserNo().toString());
            tvClass.setText(userBean.getUserGrade() + userBean.getUserClass());
            if (userBean.getUserHead() != null) {
                this.path = userBean.getUserHead();
                RxImageLoader.with(context).load(path).into(ivHead);
            }
        }
    }

    /**
     * 刷新头像
     *
     * @param path 裁剪并压缩后的path
     */
    @Override
    public void refreshHeadIcon(String path) {
        Log.e("zkp", path);
        this.path = path;
        if (PermissionUtils.isGrantExternalRW(context, 1)) {
//            GlideImageLoader loader = new GlideImageLoader();
//            loader.displayImage(context, path, ivHead);
            RxImageLoader.with(context).load(path).into(ivHead);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    GlideImageLoader loader = new GlideImageLoader();
//                    loader.displayImage(context, path, ivHead);

                    RxImageLoader.with(context).load(path).into(ivHead);
                } else {
                    ToastUtil.showToast(context, "请开启读取手机内存权限");
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void uploadHeadIconSuccess(UploadHeadIconBean data) {
        ToastUtil.showToast(context, "上传头像成功");
        this.path = Constant.BaseUrl + data.getData();
        LoginBean.UserBean user = UserUtil.getInstance().getUser();
        user.setUserHead(path);
        UserUtil.getInstance().setUser(user);
        RxImageLoader.with(context).load(path).into(ivHead);
    }

    @Override
    public void uploadHeadIconError(int status) {
        switch (status) {
            case Constant.FAILED_CODE:
                ToastUtil.showToast(context, "上传头像失败");
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
    public void getHeadIconSuccess(UploadHeadIconBean data) {
        this.path = Constant.BaseUrl + data.getData();
        RxImageLoader.with(context).load(Constant.BaseUrl + data.getData()).into(ivHead);
    }

    @Override
    public void getHeadIconError(int status) {
        switch (status) {
            case Constant.FAILED_CODE:
                ToastUtil.showToast(context, "获取头像失败");
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
    public void showLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.showMessage("加载中...");
        }
        dialog.show();
    }

    @Override
    public void hideLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.showMessage("加载中...");
        }
        dialog.dismiss();
    }
}
