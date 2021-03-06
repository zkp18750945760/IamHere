package com.zhoukp.signer.module.update;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.utils.PermissionUtils;
import com.zhoukp.signer.utils.ScreenUtil;

/**
 * @author zhoukp
 * @time 2018/3/30 13:31
 * @email 275557625@qq.com
 * @function
 */

public class UpdateDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private DownloadManager manager;

    public UpdateDialog(@NonNull Context context) {
        super(context, R.style.UpdateDialog);
        init(context);
    }

    /**
     * 初始化布局
     */
    private void init(Context context) {
        this.context = context;
        manager = DownloadManager.getInstance();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_update, null);
        setContentView(view);
        setWindowSize(context);
        initView(view);
    }

    private void initView(View view) {
        view.findViewById(R.id.ib_close).setOnClickListener(this);
        TextView title = view.findViewById(R.id.tv_title);
        TextView size = view.findViewById(R.id.tv_size);
        TextView description = view.findViewById(R.id.tv_description);
        Button update = view.findViewById(R.id.btn_update);
        update.setOnClickListener(this);
        //设置界面数据
        if (TextUtils.isEmpty(manager.getApkVersionName())) {
            title.setText(String.format("哇，有新版%s可以下载啦！", manager.getApkVersionName()));
        } else {
            title.setText(String.format("哇，有新版v%s可以下载啦！", manager.getApkVersionName()));
        }
        if (!TextUtils.isEmpty(manager.getApkSize())) {
            size.setText(String.format("新版本大小：%s", manager.getApkSize()));
            size.setVisibility(View.VISIBLE);
        }
        description.setText(manager.getApkDescription());
    }

    private void setWindowSize(Context context) {
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ScreenUtil.getWith(context) * 0.7f);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
        Log.e("zkp", "dismiss");
        if (v.getId() == R.id.btn_update) {
            //检查权限
            if (!PermissionUtils.checkStoragePermission(context)) {
                //没有权限,去申请权限
                context.startActivity(new Intent(context, PermissionActivity.class));
                return;
            }
            context.startService(new Intent(context, DownloadService.class));
        }
    }
}
