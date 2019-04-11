package com.hangzhou.hezi.vendingmachine.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hangzhou.hezi.vendingmachine.MUtils.DataCleanManager;
import com.hangzhou.hezi.vendingmachine.MUtils.GsonUtils;
import com.hangzhou.hezi.vendingmachine.MUtils.WsManager;
import com.hangzhou.hezi.vendingmachine.MView.MRelativeLayout;
import com.hangzhou.hezi.vendingmachine.MView.NoScrollViewPager;
import com.hangzhou.hezi.vendingmachine.Presenter.HomeActivityPresenter;
import com.hangzhou.hezi.vendingmachine.R;
import com.hangzhou.hezi.vendingmachine.bean.CodeBean;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeActivity extends MBaseActivity {

    @BindView(R.id.home_banner)
    Banner homeBanner;
    @BindView(R.id.home_iv)
    ImageView homeIv;
    @BindView(R.id.home_bt_iv1)
    ImageView homeBtIv1;
    @BindView(R.id.home_bt_iv2)
    ImageView homeBtIv2;
    @BindView(R.id.home_bt_iv3)
    ImageView homeBtIv3;
    @BindView(R.id.home_bt_iv4)
    ImageView homeBtIv4;
    @BindView(R.id.home_ll)
    LinearLayout homeLl;
    @BindView(R.id.home_select_tv3)
    TextView homeSelectTv3;
    @BindView(R.id.home_select_tv2)
    TextView homeSelectTv2;
    @BindView(R.id.home_select_tv1)
    TextView homeSelectTv1;
    @BindView(R.id.home_view_pager)
    NoScrollViewPager homeViewPager;
    @BindView(R.id.home_view)
    MRelativeLayout homeView;
    private Unbinder unbinder;
    private HomeActivityPresenter presenter;
    private NetStatusReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initscreen();
        EventBus.getDefault().register(this);
    }

    private void initData() {
        receiver = new NetStatusReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver,intentFilter);
        unbinder = ButterKnife.bind(this);
        presenter = new HomeActivityPresenter(this);
        presenter.BannerInit();
        presenter.Foodinit();
        presenter.connectionSocket();
    }

    public RelativeLayout getHomeView() {
        return homeView;
    }

    public Banner getHomeBanner() {
        return homeBanner;
    }

    public ImageView getHomeIv() {
        return homeIv;
    }

    public ImageView getHomeBtIv1() {
        return homeBtIv1;
    }

    public ImageView getHomeBtIv2() {
        return homeBtIv2;
    }

    public ImageView getHomeBtIv3() {
        return homeBtIv3;
    }

    public ImageView getHomeBtIv4() {
        return homeBtIv4;
    }

    public LinearLayout getHomeLl() {
        return homeLl;
    }

    public TextView getHomeSelectTv3() {
        return homeSelectTv3;
    }

    public TextView getHomeSelectTv2() {
        return homeSelectTv2;
    }

    public TextView getHomeSelectTv1() {
        return homeSelectTv1;
    }

    public NoScrollViewPager getHomeViewPager() {
        return homeViewPager;
    }

    public void setHomeSelectTvS1(String s) {
        homeSelectTv1.setText(s);
    }

    public void setHomeSelectTvS2(String s) {
        homeSelectTv2.setText(s);
    }

    public void setHomeSelectTvS3(String s) {
        homeSelectTv3.setText(s);
    }

    public void setHomeSelectTvColor3(String color) {
        homeSelectTv3.setTextColor(Color.parseColor(color));
    }

    public void setHomeSelectTvColor2(String color) {
        homeSelectTv2.setTextColor(Color.parseColor(color));
    }

    public void setHomeSelectTvColor1(String color) {
        homeSelectTv1.setTextColor(Color.parseColor(color));
    }

    public void setHomeSelectTvBg3(int res) {
        homeSelectTv3.setBackgroundResource(res);
    }

    public void setHomeSelectTvBg2(int res) {
        homeSelectTv2.setBackgroundResource(res);
    }

    public void setHomeSelectTvBg1(int res) {
        homeSelectTv1.setBackgroundResource(res);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        WsManager.getInstance().disconnect(true);
        unregisterReceiver(receiver);
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.countDownTimernew.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.countDownTimernew.cancel();
    }

    @OnClick({R.id.home_select_tv3, R.id.home_select_tv2, R.id.home_select_tv1})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.home_select_tv3:
                presenter.select3();
                break;
            case R.id.home_select_tv2:
                presenter.select2();
                break;
            case R.id.home_select_tv1:
                presenter.select1();
                break;
        }
    }

    @Override
    public void initscreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(String s) {
        if (s.equals("timerStart")) {
            presenter.countDownTimernew.start();
        } else if (s.equals("timerCancel")) {
            presenter.countDownTimernew.cancel();
        }else if(s.contains("socket")){
            String[] split = s.split("-");
            CodeBean codeBean = GsonUtils.GsonToBean(split[1], CodeBean.class);
            if(codeBean.getCode()==20000){
                if(presenter.service!=null&&presenter.iResultMsg!=null&&presenter.iResultMsgBanner!=null){
                    presenter.Foodinit();
                    presenter.service.getBaner(this,presenter.iResultMsgBanner);
                }
            }else if(codeBean.getCode()==20001){
                DataCleanManager.cleanCustomCache(Environment.getDataDirectory().getPath());
            }
        }
    }

    public class NetStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {

                ConnectivityManager connectivityManager
                        = (ConnectivityManager) HomeActivity.this
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if (info != null && info.isAvailable()) {
                    WsManager.getInstance().reconnect();//wify 4g切换重连websocket
                }

            }
        }
    }
}
