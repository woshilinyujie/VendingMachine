package com.hangzhou.hezi.vendingmachine.MView;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.hangzhou.hezi.vendingmachine.R;


/**
 * Created by linyujie on 18/11/12.
 */

public class CancelDialog extends Dialog {

    private TextView msg;
    private TextView cancle;
    private TextView sure;

    public CancelDialog(@NonNull Context context) {
        this(context, R.style.ActionSheetDialogStyle);
    }

    public CancelDialog(@NonNull Context context, int themeResId) {
        super(context,  R.style.ActionSheetDialogStyle);
        init(context);
    }


    private void init(Context context) {
        View view = View.inflate(context, R.layout.cancle_dialog, null);
        msg = view.findViewById(R.id.cancle_dialog_msg);
        cancle = view.findViewById(R.id.cancle_dialog_cancle);
        sure = view.findViewById(R.id.cancle_dialog_sure);
        setCanceledOnTouchOutside(false);
        setContentView(view);
    }

    public TextView getMsg() {
        return msg;
    }

    public TextView getCancle() {
        return cancle;
    }

    public TextView getSure() {
        return sure;
    }

    public void setMsgS(String s){
        msg.setText(s);
    }
    public void setCancleS(String s){
        cancle.setText(s);
    }
    public void setSureS(String s){
        sure.setText(s);
    }
}
