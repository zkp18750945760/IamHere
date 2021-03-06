package com.zhoukp.signer.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.activity.ActivityPager;
import com.zhoukp.signer.module.functions.ledgers.scanxls.ProgressDialog;
import com.zhoukp.signer.module.functions.ledgers.scanxls.XlsBean;
import com.zhoukp.signer.module.home.HomePager;
import com.zhoukp.signer.module.me.MePager;
import com.zhoukp.signer.module.update.DownloadManager;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.WindowUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * @author zhoukp
 * @time 2018/1/28 18:34
 * @email 275557625@qq.com
 * @function 主页面
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, MainView {

    protected RadioGroup rgTag;
    protected FrameLayout flMainContent;

    private ProgressDialog dialog;
    private MainPresenter presenter;

    /**
     * 选中的位置
     */
    private int position;
    /**
     * 页面的集合
     */
    private ArrayList<BaseFragment> baseFragments;

    /**
     * 是否要退出
     */
    private boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);

        setContentView(R.layout.activity_main);

        initViews();

        initVariates();
    }

    /**
     * 再按一次退出应用
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                ToastUtil.showToast(getApplicationContext(), "再按一次退出应用");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initViews() {
        rgTag = findViewById(R.id.rgTag);
        rgTag.setOnCheckedChangeListener(this);
        flMainContent = findViewById(R.id.flMainContent);
    }

    private void initVariates() {
        baseFragments = new ArrayList<>();
        //首页
        baseFragments.add(new HomePager());
        baseFragments.add(new ActivityPager());
        baseFragments.add(new MePager());

        if (presenter == null) {
            presenter = new MainPresenter();
            presenter.attachView(this);
            presenter.getCrashFile(Constant.appCrashPath, ".log", true);
            presenter.getUpdateInfo();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rbHome:
                position = 0;
                break;
            case R.id.rbActivity:
                position = 1;
                break;
            case R.id.rbMe:
                position = 2;
                break;
            default:
                break;
        }
        setFragment();
    }

    /**
     * 把页面添加到Fragment中
     */
    private void setFragment() {
        //1.得到FragmentManger
        FragmentManager manager = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction ft = manager.beginTransaction();
        //3.替换
        ft.replace(R.id.flMainContent, getFragment());
        //4.提交事务
        ft.commit();
    }

    /**
     * 根据位置得到对应的页面
     *
     * @return ReplaceFragment
     */
    private BaseFragment getFragment() {
        return baseFragments.get(position);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initVariates();
        rgTag.check(R.id.rbHome);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.Login:
                    //登陆回调
                    getSharedPreferences("Signer", MODE_PRIVATE).edit().putBoolean("login", true).apply();
                    ((MePager) baseFragments.get(2)).refreshUI();
                    break;
                case PictureConfig.CHOOSE_REQUEST:
                    //更新头像回调
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (selectList.get(0).isCompressed()) {
                        String compressPath = selectList.get(0).getCompressPath();
                        ((MePager) baseFragments.get(2)).presenter.upLoadHeadIcon(MainActivity.this, compressPath);
                        //rgTag.check(R.id.rbMe);
                    }
                    break;
            }
        }
    }

    @Override
    public void showLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.showMessage("加载中...");
        }
        dialog.show();
    }

    @Override
    public void hideLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.showMessage("加载中...");
        }
        dialog.dismiss();
    }

    @Override
    public void getUpdateInfoSuccess(UpdateBean bean) {
        //获取版本更新信息成功
        //提示用户进行版本升级
        DownloadManager manager = DownloadManager.getInstance(this);
        manager.setApkName(bean.getData().get(0).getAppName())
                .setApkUrl(Constant.BaseUrl + bean.getData().get(0).getDownloadUrl())
                .setDownloadPath(Constant.appFilePath)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setApkVersionCode(bean.getData().get(0).getVersionCode())
                .setApkDescription(bean.getData().get(0).getDescription())
                .setApkVersionName(bean.getData().get(0).getVersionName())
                .setApkSize(presenter.getSize(bean.getData().get(0).getApkSize()))
                //可设置，可不设置
                .setConfiguration(null)
                .download();
    }

    @Override
    public void getUpdateInfoError(int status) {
        switch (status) {
            case Constant.FAILED_CODE:
                ToastUtil.showToast(this, "获取版本更新信息失败");
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
    public void getCrashLogcatSuccess(XlsBean bean) {
        presenter.uploadCrashLogcat(new File(bean.getPath()));
    }

    @Override
    public void getCrashLogcatError() {
        ToastUtil.showToast(this, "还没有错误日志哦");
    }

    @Override
    public void uploadCrashSuccess() {
        ToastUtil.showToast(this, "错误日志上传成功");
    }

    @Override
    public void uploadCrashError(int status) {
        switch (status) {
            case Constant.FAILED_CODE:
                ToastUtil.showToast(this, "上传错误日志失败");
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
}
