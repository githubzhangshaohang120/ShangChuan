package com.example.administrator.shangchuan.module;


import com.example.administrator.shangchuan.net.API;
import com.example.administrator.shangchuan.net.MyInterceptor;
import com.example.administrator.shangchuan.net.ProjectApi;
import com.example.administrator.shangchuan.net.ProjectApiService;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/5/8 0008.
 */
@Module
public class HttpModule {

    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        return new OkHttpClient.Builder()
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(logging);
    }



    @Provides
    ProjectApi provideProjectApi(OkHttpClient.Builder builder){


        builder.addInterceptor(new MyInterceptor());
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        ProjectApiService projectApiService = retrofit.create(ProjectApiService.class);
        return ProjectApi.getProjectApi(projectApiService);


    }
}
