package com.hangzhou.hezi.vendingmachine.MView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2019/3/24.
 */

public class MlinearLayout extends LinearLayout {
    public MlinearLayout(Context context) {
        super(context);
    }

    public MlinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MlinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                EventBus.getDefault().post("timerCancelWeb");
                break;
            case MotionEvent.ACTION_UP:
                EventBus.getDefault().post("timerStartWeb");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
