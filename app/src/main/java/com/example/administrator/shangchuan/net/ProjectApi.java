package com.example.administrator.shangchuan.net;



import com.example.administrator.shangchuan.bean.BaseBean;
import com.example.administrator.shangchuan.bean.UserBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/5/17 0017.
 */

public class ProjectApi {
    private static ProjectApi projectApi;
    private ProjectApiService projectApiService;
    private ProjectApi(ProjectApiService projectApiService) {
        this.projectApiService=projectApiService;
    }

    public static ProjectApi getProjectApi(ProjectApiService projectApiService){
        if (projectApi==null){
            projectApi=new ProjectApi(projectApiService);
        }
        return projectApi;
    }

    public Observable<UserBean> login(String mobile, String password){
        return projectApiService.login(mobile,password);
    }


    public Observable<BaseBean> updateHeader(RequestBody uid, MultipartBody.Part file){
        return projectApiService.updateHeader(uid,file);
    }


}
