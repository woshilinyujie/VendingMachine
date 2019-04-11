package com.hangzhou.hezi.vendingmachine.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hangzhou.hezi.vendingmachine.Presenter.FoodFragmentPresenter;
import com.hangzhou.hezi.vendingmachine.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2019/3/21.
 */

@SuppressLint("ValidFragment")
public class FoodFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.food_rc_view)
    RecyclerView foodRcView;
    private View view;
    private String json;
    private FoodFragmentPresenter presenter;
    private Activity activity;

    public static FoodFragment newInstance(Activity activity,String json) {
        FoodFragment fragment = new FoodFragment(activity,json);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ValidFragment")
    public  FoodFragment(Activity activity,String json){
        this.activity=activity;
        view = View.inflate(activity, R.layout.fragment_food_layout, null);
        unbinder = ButterKnife.bind(this, view);
        presenter = new FoodFragmentPresenter(this);
        presenter.initData(json);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    public View getmView(){
        return view;
    }

    public RecyclerView getFoodRcView() {
        return foodRcView;
    }

    public Activity getmActivity(){
        return activity;
    }
}
