package com.hangzhou.hezi.vendingmachine.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hangzhou.hezi.vendingmachine.Presenter.LoginPresenter;
import com.hangzhou.hezi.vendingmachine.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/3/18.
 */

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_text)
    TextView loginText;
    @BindView(R.id.login_account)
    EditText loginAccount;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_sure)
    TextView loginSure;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initscreen();
        initData();
    }

    private void initData() {
        presenter = new LoginPresenter(this);
    }

    public void setLoginTextS(String s){
        loginText.setText(s);
    }
    @OnClick(R.id.login_sure)
    public void onViewClicked() {
        presenter.login();
    }

    public TextView getLoginText() {
        return loginText;
    }

    public EditText getLoginAccount() {
        return loginAccount;
    }

    public EditText getLoginPassword() {
        return loginPassword;
    }

    public TextView getLoginSure() {
        return loginSure;
    }

    public void initscreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
