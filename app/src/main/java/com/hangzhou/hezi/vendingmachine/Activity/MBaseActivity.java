package com.hangzhou.hezi.vendingmachine.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.hangzhou.hezi.vendingmachine.API.ApiService;
import com.hangzhou.hezi.vendingmachine.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/3/17.
 */

public abstract class MBaseActivity extends AppCompatActivity {
    private Intent intent;
    public abstract void initscreen();


    public MBaseActivity(){
        Log.e("调用够着了","");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
