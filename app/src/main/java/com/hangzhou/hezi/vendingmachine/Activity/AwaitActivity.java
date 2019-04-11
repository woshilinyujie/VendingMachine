package com.hangzhou.hezi.vendingmachine.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.hangzhou.hezi.vendingmachine.API.ApiService;
import com.hangzhou.hezi.vendingmachine.MUtils.DpUtils;
import com.hangzhou.hezi.vendingmachine.MUtils.GsonUtils;
import com.hangzhou.hezi.vendingmachine.MUtils.PrefUtils;
import com.hangzhou.hezi.vendingmachine.R;
import com.hangzhou.hezi.vendingmachine.bean.AdBean;
import com.hangzhou.hezi.vendingmachine.bean.CodeBean;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.widget.PLVideoView;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/3/22.
 */

public class AwaitActivity extends AppCompatActivity implements PLOnCompletionListener {
    @BindView(R.id.await_iv)
    RelativeLayout awaitIv;
    @BindView(R.id.await_iv1)
    ImageView awaitIv1;
    @BindView(R.id.video)
    PLVideoView mVideoView;
    @BindView(R.id.await_code)
    ImageView awaitCode;
    private ApiService service;
    private MyHandler handler = new MyHandler(this);
    private int currentImg = 0;
    private int current = 0;
    private AVOptions options;
    private ArrayList<AdBean.DataBean> imgList = new ArrayList();
    private ArrayList<AdBean.DataBean> videoList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initscreen();
        setContentView(R.layout.activity_await);
        ButterKnife.bind(this);
        service = new ApiService();
        initDate();
    }


    private void initDate() {
        service.getCode(this, PrefUtils.getString(this, "id", null), new ApiService.IResultMsg<CodeBean>() {
            @Override
            public void Result(CodeBean bean) {
                Bitmap qr = CodeUtils.createImage(bean.getData(), DpUtils.dip2px(AwaitActivity.this, 116), DpUtils.dip2px(AwaitActivity.this, 116), null);

                awaitCode.setImageBitmap(qr);
            }

            @Override
            public void Error(String json) {

            }
        });

        service.getAd(this, new ApiService.IResultMsg<AdBean>() {
            @Override
            public void Result(AdBean bean) {
                if (bean.getData().size() > 0) {
                    if (bean.getData().get(0).getType() == 1) {
                        //图片
                        mVideoView.setVisibility(View.GONE);
                        imgList.clear();
                        for (int x = 0; x < bean.getData().size(); x++) {
                            if (bean.getData().get(x).getType() == 1)
                                imgList.add(bean.getData().get(x));
                        }
                        Glide.with(AwaitActivity.this).asBitmap().load(imgList.get(0).getIvUrl()).into(awaitIv1);
                        if (bean.getData().size() > 1) {
                            handler.sendEmptyMessageDelayed(0, 5000);
                        }
                    } else {
                        videoList.clear();
                        for (int x = 0; x < bean.getData().size(); x++) {
                            if (bean.getData().get(x).getType() != 1)
                                videoList.add(bean.getData().get(x));
                        }
                        mVideoView.setVisibility(View.VISIBLE);
                        openVideoFromUri(bean.getData().get(0).getIvUrl());

                    }
                }

            }

            @Override
            public void Error(String json) {

            }
        });
    }

    public void initscreen() {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @OnClick({R.id.await_iv, R.id.await_iv1, R.id.video})
    public void onViewClicked(View view) {
        finish();
    }

    private void openVideoFromUri(String url) {
        if (options == null)
            options = new AVOptions();
        options.setString(AVOptions.KEY_CACHE_DIR, getFilesDir() + "/" + url);
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
        mVideoView.setAVOptions(options);
        mVideoView.setVideoPath(url);
        mVideoView.start();
        mVideoView.setOnCompletionListener(this);

    }

    @Override
    public void onCompletion() {
        if (videoList.size() > 1) {
            current++;
            current = current % videoList.size();
            openVideoFromUri(videoList.get(current).getIvUrl());
        } else {
            mVideoView.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }


    class MyHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public MyHandler(Activity activity) {
            mWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = mWeakReference.get();
            if (activity != null) {
                currentImg++;
                currentImg = currentImg % imgList.size();
                Glide.with(activity).asBitmap().load(imgList.get(currentImg).getIvUrl()).into(awaitIv1);
                sendEmptyMessageDelayed(0, 5000);
            }
        }
    }

}
