package com.hangzhou.hezi.vendingmachine.API;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.hangzhou.hezi.vendingmachine.bean.AdBean;
import com.hangzhou.hezi.vendingmachine.MUtils.GsonUtils;
import com.hangzhou.hezi.vendingmachine.MView.LoadingDialog;
import com.hangzhou.hezi.vendingmachine.MView.Toast.MToast;
import com.hangzhou.hezi.vendingmachine.bean.BannerBean;
import com.hangzhou.hezi.vendingmachine.bean.CodeBean;
import com.hangzhou.hezi.vendingmachine.bean.FoodBean;
import com.hangzhou.hezi.vendingmachine.bean.LoginBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * Created by Administrator on 2019/3/19.
 */

public class ApiService {
    LoadingDialog dialog;

    /**
     * 激活
     * @param context
     * @param agentPhone
     * @param deviceNo
     * @param resultMsg
     */
    public void Login(final Context context , String deviceNo, String agentPhone, final IResultMsg<LoginBean> resultMsg){
        if (dialog == null)
            dialog = new LoadingDialog(context);
        Window window =  dialog.getWindow();
        if (dialog != null && window != null) {
            WindowManager.LayoutParams attr = window.getAttributes();
            if (attr != null) {
                attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.gravity = Gravity.CENTER;//设置dialog 在布局中的位置
            }
        }
        dialog.showLoading();
        OkGo.<String>post(APIConfig.API+"vend/v1/vendMachine/open").tag(context)
                .params("no",deviceNo)
                .params("agentPhone",agentPhone)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if(dialog!=null&&dialog.isShowing())
                            dialog.dismiss();
                        try {
                            String s=response.body().toString();
                            LoginBean loginBean = GsonUtils.GsonToBean(s, LoginBean.class);
                            resultMsg.Result(loginBean);
                        }catch (Exception e){
                            showToast(context,e.toString());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if(dialog!=null&&dialog.isShowing())
                            dialog.dismiss();
                    }
                });

    }

    /**
     * banner接口
     * @param context
     * @param resultMsg
     */
    public void getBaner(final Context context, final IResultMsg<BannerBean> resultMsg){
        OkGo.<String>get(APIConfig.API+"vend/v1/adEntrances")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String s=response.body().toString();
                            BannerBean bannerBean = GsonUtils.GsonToBean(s, BannerBean.class);
                            if(bannerBean.getCode()==10000){
                                resultMsg.Result(bannerBean);
                            }else{
                                showToast(context,bannerBean.getMessage());
                            }
                        }catch (Exception e){
                            showToast(context,e.toString());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    /**
     * 商品
     * @param context
     * @param iResultMsg
     */
    public void getFood(final Context context, final IResultMsg<FoodBean>  iResultMsg){
        OkGo.<String>get(APIConfig.API+"vend/v1//goods/list")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
//                        try {
                            String s=response.body().toString();
                            FoodBean foodBean = GsonUtils.GsonToBean(s, FoodBean.class);
                            if(foodBean.getCode()==10000){
                                iResultMsg.Result(foodBean);
                            }else{
                                showToast(context,foodBean.getMessage());
                            }
//                        }catch (Exception e){
//                            showToast(context,e.toString());
//                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }


    /**
     * 广告
     * @param context
     * @param iResultMsg
     */
    public void getAd(final Context context, final IResultMsg<AdBean> iResultMsg){
        OkGo.<String>get(APIConfig.API+"vend/v1/standbyAds")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String s=response.body().toString();
                            AdBean foodBean = GsonUtils.GsonToBean(s, AdBean.class);
                            if(foodBean.getCode()==10000){
                                iResultMsg.Result(foodBean);
                            }else{
                                showToast(context,foodBean.getMessage());
                            }
                        }catch (Exception e){
                            showToast(context,e.toString());
                        }
                    }
                });
    }

    /**
     * 二维码
     * @param context
     * @param code
     * @param iResultMsg
     */
    public void getCode(final Context context, String code, final IResultMsg<CodeBean> iResultMsg){
        OkGo.<String>get(APIConfig.API+"/vend/v1/wechat/qrCode?macNo="+code)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String s=response.body().toString();
                            CodeBean foodBean = GsonUtils.GsonToBean(s, CodeBean.class);
                            if(foodBean.getCode()==10000){
                                iResultMsg.Result(foodBean);
                            }else{
                                showToast(context,foodBean.getMessage());
                            }
                        }catch (Exception e){
                            showToast(context,e.toString());
                        }
                    }
                });
    }

    public interface IResultMsg<T> {
        void Result(T bean);

        void Error(String json);
    }

    public void showToast(Context context,String s){
        MToast.makeText(context,s, Toast.LENGTH_SHORT).show();
    }
}
