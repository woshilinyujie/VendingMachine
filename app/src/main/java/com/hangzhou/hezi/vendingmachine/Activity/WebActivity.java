package com.hangzhou.hezi.vendingmachine.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hangzhou.hezi.vendingmachine.MUtils.GsonUtils;
import com.hangzhou.hezi.vendingmachine.MUtils.WsManager;
import com.hangzhou.hezi.vendingmachine.MView.LoadingDialog;
import com.hangzhou.hezi.vendingmachine.MView.MlinearLayout;
import com.hangzhou.hezi.vendingmachine.MView.Mtimmer;
import com.hangzhou.hezi.vendingmachine.R;
import com.hangzhou.hezi.vendingmachine.bean.CodeBean;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2019/3/21.
 */


public class WebActivity extends MBaseActivity {
    @BindView(R.id.wv_task)
    WebView wvTask;
    @BindView(R.id.finish)
    TextView finish;
    @BindView(R.id.web_ll)
    MlinearLayout webLl;
    private String url;
    private LoadingDialog dialog;
    private Intent awaitIntent;

    public Mtimmer countDownTimernew = new Mtimmer(100000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if (awaitIntent == null)
                awaitIntent = new Intent(WebActivity.this, AwaitActivity.class);
            Log.e("调用启动：", "-----");
            startActivity(awaitIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        WsManager.getInstance().init(this);
        initscreen();
        EventBus.getDefault().register(this);
        url = getIntent().getStringExtra("url");
        QbSdk.setDownloadWithoutWifi(true);
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
        initDate();
    }

    private void initDate() {
        wvTask.getSettings().setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        wvTask.getSettings().setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        wvTask.getSettings().setDisplayZoomControls(true); //隐藏原生的缩放控件
        wvTask.getSettings().setBlockNetworkImage(false);//解决图片不显示
        wvTask.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        wvTask.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
        WebSettings settings = wvTask.getSettings();
        // 设置与Js交互的权限
        settings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //localStorage  允许存储
        wvTask.getSettings().setDomStorageEnabled(true);
        wvTask.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);//存储的最大容量
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        wvTask.getSettings().setAppCachePath(appCachePath);
        wvTask.getSettings().setAllowFileAccess(true);
        wvTask.getSettings().setAppCacheEnabled(true);


        wvTask.loadUrl(url);

        //该界面打开更多链接
        wvTask.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                try {
                    //开启加载框
                    if (dialog == null) {
                        dialog = new LoadingDialog(WebActivity.this);
                        Window window =  dialog.getWindow();
                        if (dialog != null && window != null) {
                            WindowManager.LayoutParams attr = window.getAttributes();
                            if (attr != null) {
                                attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                attr.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                attr.gravity = Gravity.CENTER;//设置dialog 在布局中的位置
                            }
                        }
                    }

                    dialog.showLoading();
                } catch (Exception e) {

                }
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                try {
                    //关闭对话框
                    if (dialog != null) {
                        if (dialog.isShowing()) {
                            dialog.dismissLoading();
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
        //监听网页的加载进度
        wvTask.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {

            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onResume() {
        super.onResume();
        wvTask.onResume();
        wvTask.getSettings().setJavaScriptEnabled(true);
        countDownTimernew.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        wvTask.onPause();
        wvTask.getSettings().setLightTouchEnabled(false);
        countDownTimernew.cancel();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        WsManager.getInstance().disconnect(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

    @OnClick(R.id.finish)
    public void onViewClicked() {
        finish();
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
        if (s.equals("timerStartWeb")) {
            countDownTimernew.start();
        } else if (s.equals("timerCancelWeb")) {
            countDownTimernew.cancel();
        } else if (s.contains("socket")) {
            String[] split = s.split("-");
            CodeBean codeBean = GsonUtils.GsonToBean(split[1], CodeBean.class);
            if (codeBean.getCode() == 10000 || codeBean.getCode() == 10001) {
                wvTask.loadUrl(codeBean.getData());
            }
        }
    }


    public void destroyWebView() {

        webLl.removeAllViews();

        if (wvTask != null) {
            wvTask.clearHistory();
            wvTask.clearCache(true);
            wvTask.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
            wvTask.freeMemory();
            wvTask.pauseTimers();
            wvTask = null; // Note that mWebView.destroy() and mWebView = null do the exact same thing
        }

    }
}
