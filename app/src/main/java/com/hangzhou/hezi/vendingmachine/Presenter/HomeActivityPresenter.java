package com.hangzhou.hezi.vendingmachine.Presenter;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hangzhou.hezi.vendingmachine.API.ApiService;
import com.hangzhou.hezi.vendingmachine.Activity.AwaitActivity;
import com.hangzhou.hezi.vendingmachine.Activity.HomeActivity;
import com.hangzhou.hezi.vendingmachine.Activity.MBaseActivity;
import com.hangzhou.hezi.vendingmachine.Activity.WebActivity;
import com.hangzhou.hezi.vendingmachine.Fragment.HomeFragment;
import com.hangzhou.hezi.vendingmachine.MUtils.GlideImageLoader;
import com.hangzhou.hezi.vendingmachine.MUtils.GsonUtils;
import com.hangzhou.hezi.vendingmachine.MUtils.PrefUtils;
import com.hangzhou.hezi.vendingmachine.MUtils.WsManager;
import com.hangzhou.hezi.vendingmachine.MView.Mtimmer;
import com.hangzhou.hezi.vendingmachine.R;
import com.hangzhou.hezi.vendingmachine.bean.BannerBean;
import com.hangzhou.hezi.vendingmachine.bean.FoodBean;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/3/17.
 */

public class HomeActivityPresenter{
    private HomeActivity activity;
    public ArrayList<HomeFragment> fragments=new ArrayList<>();
    public ApiService service;
    private HomeFragment homeFragmentOneOne;
    private HomeFragment homeFragmentOneTwo;
    private HomeFragment homeFragmentOneThree;
    private HomeFragmentPagerAdapter homeFragmentPagerAdapter;
    private Intent awaitIntent;
    private int currentPage=0;
    private String url1="https://wx.jiajiatonghuo.cn/store/web/static/web/matchineActivity/index.html#/step/step?macNo=";

        public Mtimmer countDownTimernew =new Mtimmer(120000, 1000) {
            @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if(awaitIntent ==null)
                awaitIntent =new Intent( activity,AwaitActivity.class);
            Log.e("调用启动：","-----");
            activity.startActivity(awaitIntent);


        }
    };
    public ApiService.IResultMsg<BannerBean> iResultMsgBanner;
    public ApiService.IResultMsg<FoodBean> iResultMsg;

    public HomeActivityPresenter(HomeActivity activity) {
        this.activity = activity;
        service = new ApiService();
    }

    public void connectionSocket(){
        WsManager.getInstance().init(activity);
    }

    /**
     * 初始化商品
     */
    public void Foodinit(){
        fragments.clear();
        homeFragmentOneOne = HomeFragment.newInstance(activity);
        homeFragmentOneTwo = HomeFragment.newInstance(activity);
        homeFragmentOneThree = HomeFragment.newInstance(activity);
        homeFragmentPagerAdapter = new HomeFragmentPagerAdapter();
        iResultMsg = new ApiService.IResultMsg<FoodBean>() {
            @Override
            public void Result(FoodBean bean) {
                activity.setHomeSelectTvS1(bean.getData().get(0).getTypeName());
                activity.setHomeSelectTvS2(bean.getData().get(1).getTypeName());
                activity.setHomeSelectTvS3(bean.getData().get(2).getTypeName());
                homeFragmentOneOne.setData(GsonUtils.GsonString(bean.getData().get(0)));
                homeFragmentOneTwo.setData(GsonUtils.GsonString(bean.getData().get(1)));
                homeFragmentOneThree.setData(GsonUtils.GsonString(bean.getData().get(2)));
                activity.getHomeViewPager().setAdapter(homeFragmentPagerAdapter);
                switch (currentPage){
                    case 0:
                        select1();
                        break;
                    case 1:
                        select2();
                        break;
                    case 2:
                        select3();
                        break;
                }
            }

            @Override
            public void Error(String json) {

            }
        };
        fragments.add(homeFragmentOneOne);
        fragments.add(homeFragmentOneTwo);
        fragments.add(homeFragmentOneThree);
        service.getFood(activity, iResultMsg);
    }

    /**
     * 初始化banner和底部数据
     */
    public void BannerInit(){

        //设置banner样式
        activity.getHomeBanner().setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        activity.getHomeBanner().setImageLoader(new GlideImageLoader());
        //设置自动轮播，默认为true
        activity.getHomeBanner().isAutoPlay(true);
        //设置轮播时间
        activity.getHomeBanner().setDelayTime(4000);
        //设置指示器位置（当banner模式中有指示器时）
        activity.getHomeBanner().setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        iResultMsgBanner = new ApiService.IResultMsg<BannerBean>() {
            @Override
            public void Result(BannerBean bean) {
                if (bean.getData().getTop() != null && bean.getData().getTop().size() > 0) {
                    ArrayList<String> list = new ArrayList<>();
                    for (int x = 0; x < bean.getData().getTop().size(); x++) {
                        list.add(bean.getData().getTop().get(x).getImgUrl());
                    }
                    activity.getHomeBanner().setImages(list);
                    //banner设置方法全部调用完毕时最后调用
                    activity.getHomeBanner().start();
                    Glide.with(activity).asBitmap().load(bean.getData().getBottom().get(0).getImgUrl()).into(activity.getHomeBtIv1());
                    Glide.with(activity).asBitmap().load(bean.getData().getBottom().get(1).getImgUrl()).into(activity.getHomeBtIv2());
                    Glide.with(activity).asBitmap().load(bean.getData().getBottom().get(2).getImgUrl()).into(activity.getHomeBtIv3());
                    Glide.with(activity).asBitmap().load(bean.getData().getBottom().get(3).getImgUrl()).into(activity.getHomeBtIv4());
                    bannerListener(bean);
                    setBottomClick(bean);
                }

            }

            @Override
            public void Error(String json) {

            }
        };
        //数据
        service.getBaner(activity, iResultMsgBanner);
    }

    public void bannerListener(final BannerBean bean){
        activity.getHomeBanner().setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent=new Intent(activity, WebActivity.class);
                intent.putExtra("url",bean.getData().getTop().get(position).getLinkUrl());
                activity.startActivity(intent);
            }
        });
    }

    public void setBottomClick(final BannerBean bean){
        if(bean.getData().getBottom()!=null&&bean.getData().getBottom().size()==4) {

            activity.getHomeBtIv1().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(activity,WebActivity.class);
                    intent.putExtra("url",url1+ PrefUtils.getString(activity,"id",null));
                    activity.startActivity(
                            intent
                    );
                }
            });
            activity.getHomeBtIv2().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(activity,WebActivity.class);
                    intent.putExtra("url",bean.getData().getBottom().get(0).getLinkUrl());
                    activity.startActivity(
                            intent
                    );
                }
            });
            activity.getHomeBtIv3().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(activity,WebActivity.class);
                    intent.putExtra("url",bean.getData().getBottom().get(0).getLinkUrl());
                    activity.startActivity(
                            intent
                    );
                }
            });
            activity.getHomeBtIv4().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(activity,WebActivity.class);
                    intent.putExtra("url",bean.getData().getBottom().get(0).getLinkUrl());
                    activity.startActivity(
                            intent
                    );
                }
            });


        }
    }

    public void select1(){
        currentPage=0;
        activity.getHomeViewPager().setCurrentItem(0);
        activity.setHomeSelectTvBg1(R.drawable.shape_radius_8_ffffff);
        activity.setHomeSelectTvBg2(R.drawable.shape_radius_30_stroke_e31500);
        activity.setHomeSelectTvBg3(R.drawable.shape_radius_30_stroke_e31500);
        activity.setHomeSelectTvColor1("#d01603");
        activity.setHomeSelectTvColor2("#ffffff");
        activity.setHomeSelectTvColor3("#ffffff");
    }
    public void select2(){
        currentPage=1;
        activity.getHomeViewPager().setCurrentItem(1);
        activity.setHomeSelectTvBg2(R.drawable.shape_radius_8_ffffff);
        activity.setHomeSelectTvBg1(R.drawable.shape_radius_30_stroke_e31500);
        activity.setHomeSelectTvBg3(R.drawable.shape_radius_30_stroke_e31500);
        activity.setHomeSelectTvColor1("#ffffff");
        activity.setHomeSelectTvColor2("#d01603");
        activity.setHomeSelectTvColor3("#ffffff");
    }
    public void select3(){
        currentPage=2;
        activity.getHomeViewPager().setCurrentItem(2);
        activity.setHomeSelectTvBg3(R.drawable.shape_radius_8_ffffff);
        activity.setHomeSelectTvBg1(R.drawable.shape_radius_30_stroke_e31500);
        activity.setHomeSelectTvBg2(R.drawable.shape_radius_30_stroke_e31500);
        activity.setHomeSelectTvColor1("#ffffff");
        activity.setHomeSelectTvColor2("#ffffff");
        activity.setHomeSelectTvColor3("#d01603");
    }



    class HomeFragmentPagerAdapter extends PagerAdapter {


        public HomeFragmentPagerAdapter() {

        }



        @Override
        public int getCount() {
            return fragments != null ? fragments.size() : 0;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(fragments.get(position).getView());
            return fragments.get(position).getView();

        }
    }
}
