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

public class MDialog extends Dialog {

    private TextView msg1;
    private TextView msg2;
    private TextView sure;

    public MDialog(@NonNull Context context) {
        this(context, R.style.ActionSheetDialogStyle);
    }

    public MDialog(@NonNull Context context, int themeResId) {
        super(context,  R.style.ActionSheetDialogStyle);
        init(context);
    }


    private void init(Context context) {
        View view = View.inflate(context, R.layout.my_dialog, null);
        msg1 = view.findViewById(R.id.dialog_msg1);
        msg2 = view.findViewById(R.id.dialog_msg2);
        sure = view.findViewById(R.id.dialog_sure);
        setCanceledOnTouchOutside(false);
        setContentView(view);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public TextView getMsg1() {
        return msg1;
    }


    public TextView getSure() {
        return sure;
    }

    public void setMsgS1(String s){
        msg1.setText(s);
    }
    public void setMsgS2(String s){
        msg2.setText(s);
    }
    public void setSureS(String s){
        sure.setText(s);
    }
}
