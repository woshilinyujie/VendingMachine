package com.hangzhou.hezi.vendingmachine.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hangzhou.hezi.vendingmachine.Presenter.HomeFragmentPresenter;
import com.hangzhou.hezi.vendingmachine.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2019/3/19.
 */

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.home_fr_view_pager)
    ViewPager homeFrViewPager;
    @BindView(R.id.home_fr_indicator)
    LinearLayout homeFrIndicator;
    private View view;
    private HomeFragmentPresenter presenter;
    String json;
    private  Activity activity;
    public static HomeFragment newInstance(Activity activity) {
        HomeFragment fragment = new HomeFragment(activity);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @SuppressLint("ValidFragment")
    public HomeFragment(Activity activity){
        this.activity=activity;
        view = View.inflate(activity, R.layout.fragment_home_layout, null);
        unbinder = ButterKnife.bind(this, view);
        presenter = new HomeFragmentPresenter(this);
        presenter.viewPagerLinstener();
    }

    public void setData(String json){
        this.json=json;
        presenter.initData(json);
    }
    @Override
    public View getView() {
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public ViewPager getHomeFrViewPager() {
        return homeFrViewPager;
    }

    public LinearLayout getHomeFrIndicator() {
        return homeFrIndicator;
    }

    public Activity getmActivity(){
        return activity;
    }
}
