package com.hangzhou.hezi.vendingmachine.Presenter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.hangzhou.hezi.vendingmachine.API.ApiService;
import com.hangzhou.hezi.vendingmachine.Activity.HomeActivity;
import com.hangzhou.hezi.vendingmachine.Activity.LoginActivity;
import com.hangzhou.hezi.vendingmachine.MUtils.GlideImageLoader;
import com.hangzhou.hezi.vendingmachine.MUtils.PrefUtils;
import com.hangzhou.hezi.vendingmachine.MView.MDialog;
import com.hangzhou.hezi.vendingmachine.MView.Toast.MToast;
import com.hangzhou.hezi.vendingmachine.R;
import com.hangzhou.hezi.vendingmachine.bean.LoginBean;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/3/17.
 */

public class LoginPresenter {
    LoginActivity activity;
    private final ApiService service;
    private MDialog dialog;
    private final boolean isfirst;

    public  LoginPresenter(LoginActivity activity){
        this.activity=activity;
        service = new ApiService();
        isfirst = PrefUtils.getBoolean(activity, "isFirst", true);
        if(isfirst){
            activity.setLoginTextS("第一次开机注册");
        }else{
            activity.setLoginTextS("开机验证");
        }
    }

    public void login(){
        if(TextUtils.isEmpty(activity.getLoginAccount().getText().toString().trim())||activity.getLoginAccount().getText().toString().length()<5){
            MToast.makeText(activity,"请输入5位机器号", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(activity.getLoginPassword().getText().toString().trim())||activity.getLoginPassword().getText().toString().length()<11){
            MToast.makeText(activity,"请输入11位手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        service.Login(activity, activity.getLoginAccount().getText().toString(), activity.getLoginPassword().getText().toString(), new ApiService.IResultMsg<LoginBean>() {


            @Override
            public void Result(LoginBean bean) {
                if(bean.getCode()==10000){
                    PrefUtils.putBoolean(activity,"isFirst",false);
                    PrefUtils.putString(activity,"id",activity.getLoginAccount().getText().toString().trim());
                    Intent intent=new Intent(activity,HomeActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }else{
                    if(dialog==null)
                        dialog = new MDialog(activity);
                    if(isfirst){
                        dialog.setMsgS2("输入的注册信息有误！");
                    }else{
                        dialog.setMsgS2("请输入与该机器匹配的验证信息！");
                    }
                    Window window =  dialog.getWindow();
                    if (dialog != null && window != null) {
                        WindowManager.LayoutParams attr = window.getAttributes();
                        if (attr != null) {
                            attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            attr.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                            attr.gravity = Gravity.CENTER;
                        }
                    }
                    dialog.show();
                }
            }

            @Override
            public void Error(String json) {

            }
        });
    }
}
