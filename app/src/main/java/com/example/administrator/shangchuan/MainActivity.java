package com.example.administrator.shangchuan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.example.administrator.shangchuan.base.BaseActivity;
import com.example.administrator.shangchuan.bean.UserBean;
import com.example.administrator.shangchuan.component.DaggerHttpComponent;
import com.example.administrator.shangchuan.login.LoginContract;
import com.example.administrator.shangchuan.login.LoginPresenter;
import com.example.administrator.shangchuan.paizhao.Main2Activity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

public class MainActivity extends BaseActivity<LoginPresenter> implements LoginContract.view, View.OnClickListener {


    /**
     * 调取相机
     */
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder().build().inject(this);
    }

    @Override
    public void loginSuccess(UserBean userBean) {
        String icon = userBean.getData().getIcon();
        //mSdv.setImageURI(icon);
        SharedPreferencesUtils.setParam(this, "iconUrl",icon);
        SharedPreferencesUtils.setParam(this, "uid", userBean.getData().getUid() + "");

    }


    private void initView() {

        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
        mPresenter.login("17610531823","222111");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn:
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);

                break;
        }
    }
}
