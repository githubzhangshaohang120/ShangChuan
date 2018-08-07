package com.example.administrator.shangchuan.login;


import com.example.administrator.shangchuan.base.BaseContract;
import com.example.administrator.shangchuan.bean.UserBean;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public interface LoginContract {
    interface view extends BaseContract.BaseView{
        void loginSuccess(UserBean userBean);

    }
    interface presenter extends BaseContract.BasePresenter<view> {
        void login(String mobile, String password);

    }
}
