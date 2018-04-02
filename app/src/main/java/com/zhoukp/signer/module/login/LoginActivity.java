package com.zhoukp.signer.module.login;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zhoukp.signer.R;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.WindowUtils;
import com.zhoukp.signer.utils.aes.AesUtil;
import com.zhoukp.signer.utils.aes.MD5;
import com.zhoukp.signer.view.RoundRectImageView;
import com.zhoukp.signer.view.ThreePointLoadingView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @Bind(R.id.ivHead)
    RoundRectImageView ivHead;
    @Bind(R.id.etUsername)
    TextInputLayout etUsername;
    @Bind(R.id.etPassword)
    TextInputLayout etPassword;
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.ThreePointLoadingView)
    ThreePointLoadingView threePointLoadingView;

    private EditText username;
    private EditText password;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter = new LoginPresenter();
        presenter.attachView(this);

        username = etUsername.getEditText();
        password = etPassword.getEditText();
    }

    @OnClick(R.id.btnLogin)
    public void onClick() {
        try {
            String key = MD5.encode("hqu");
            String passwordStr = AesUtil.encrypt(password.getText().toString(), key);
            presenter.login(username.getText().toString(), passwordStr, presenter.getSerialNo(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void hideLoadingView() {
        threePointLoadingView.setVisibility(View.GONE);

    }

    @Override
    public void showLoadingView() {
        threePointLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void nullNameOrPsd() {
        ToastUtil.showToast(this, "用户名或密码不能为空");
    }

    @Override
    public void loginSuccess(LoginBean loginBean) {
        ToastUtil.showToast(this, loginBean.getData().getUserName() + "登陆成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void loginError(int status) {
        switch (status) {
            case Constant.FAILED_CODE:
                ToastUtil.showToast(this, "请求登陆失败");
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
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
