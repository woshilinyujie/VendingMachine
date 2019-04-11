package com.hangzhou.hezi.vendingmachine.MView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2019/3/24.
 */

public class MRelativeLayout extends RelativeLayout {
    public MRelativeLayout(Context context) {
        super(context);
    }

    public MRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                EventBus.getDefault().post("timerCancel");
                break;
            case MotionEvent.ACTION_UP:
                EventBus.getDefault().post("timerStart");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
