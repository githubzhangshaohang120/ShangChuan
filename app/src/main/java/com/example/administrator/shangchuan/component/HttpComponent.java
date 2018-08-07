package com.example.administrator.shangchuan.component;



import com.example.administrator.shangchuan.MainActivity;
import com.example.administrator.shangchuan.module.HttpModule;
import com.example.administrator.shangchuan.paizhao.Main2Activity;

import dagger.Component;

/**
 * Created by Administrator on 2018/5/8 0008.
 */
@Component(modules = HttpModule.class)
public interface HttpComponent {
    void inject(MainActivity mainActivity);
    void inject(Main2Activity main2Activity);

}
