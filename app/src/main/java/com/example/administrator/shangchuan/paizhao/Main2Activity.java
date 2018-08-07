package com.example.administrator.shangchuan.paizhao;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.shangchuan.R;
import com.example.administrator.shangchuan.SharedPreferencesUtils;
import com.example.administrator.shangchuan.base.BaseActivity;
import com.example.administrator.shangchuan.component.DaggerHttpComponent;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main2Activity extends BaseActivity<UpdatePresenter> implements View.OnClickListener,UpdateHeaderContract.View {
    private Bitmap bitmap;
    private SimpleDraweeView mSdv;
    /**
     * 拍照
     */
    private Button mBtn;
    private String path = Environment.getExternalStorageDirectory()+"/touxiang.png";
    private String pathlu = Environment.getExternalStorageDirectory()+"/touxianglu.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String iconUrl = (String) SharedPreferencesUtils.getParam(this, "iconUrl", "");
        initView();
        mSdv.setImageURI(iconUrl);
    }

    private void initView() {
        mSdv = (SimpleDraweeView) findViewById(R.id.sdv);
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn:
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //在Sdcard 中创建文件 存入图片
                it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                //1.意图   2.requestCode 请求码
                startActivityForResult(it, 1000);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == 1000 && resultCode ==RESULT_OK){
            //调取裁剪功能  om.android.camera.action.CROP 裁剪的Action
            Intent it = new Intent("com.android.camera.action.CROP");
            //得到图片设置类型
            it.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            //是否支持裁剪 设置 true 支持  false 不支持
            it.putExtra("CROP", true);
            //设置比例大小  1:1
            it.putExtra("aspectX", 1);
            it.putExtra("aspectY", 1);
            //输出的大小
            it.putExtra("outputX", 250);
            it.putExtra("outputY", 250);
            //将裁剪好的图片进行返回到Intent中
            it.putExtra("return-data", true);
            startActivityForResult(it, 2000);
        }
        //点击完裁剪的完成以后会执行的方法
        if(requestCode == 2000 && resultCode == RESULT_OK){
            try {

            bitmap = data.getParcelableExtra("data");
            String uid = (String) SharedPreferencesUtils.getParam(this, "uid", "");

            File file = new File(pathlu);

            if (file.exists()){
                file.delete();
            }
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                //把图片以流的方式压缩到文件中
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);

                //上传头像
            mPresenter.updateHeader(uid,pathlu);
            // mIv.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }



    }


    @Override
    public int getContentLayout() {
        return R.layout.activity_main2;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder().build().inject(this);
    }

    @Override
    public void updateSuccess(String code) {
        if ("0".equals(code) && bitmap != null) {
            //toast("上传成功");
            //去设置头像
            mSdv.setImageBitmap(bitmap);


        }
    }
}
