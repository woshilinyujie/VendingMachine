package com.hangzhou.hezi.vendingmachine.Presenter;


import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hangzhou.hezi.vendingmachine.Activity.WebActivity;
import com.hangzhou.hezi.vendingmachine.Fragment.FoodFragment;
import com.hangzhou.hezi.vendingmachine.MUtils.GsonUtils;
import com.hangzhou.hezi.vendingmachine.R;
import com.hangzhou.hezi.vendingmachine.bean.FoodBean;

/**
 * Created by Administrator on 2019/3/19.
 */

public class FoodFragmentPresenter {
    FoodFragment fragment;
    private FoodBean.DataBean dataBean;
    private MAdapter mAdapter;

    public FoodFragmentPresenter(FoodFragment fragment) {
        this.fragment = fragment;
    }

    public void initData(String json) {
        dataBean = GsonUtils.GsonToBean(json, FoodBean.DataBean.class);
        fragment.getFoodRcView().setLayoutManager(new GridLayoutManager(fragment.getmActivity(), 3));
        mAdapter = new MAdapter();
        fragment.getFoodRcView().setAdapter(mAdapter);
    }

    class MAdapter extends RecyclerView.Adapter {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Mholder holder = new Mholder(LayoutInflater.from(fragment.getmActivity()).inflate(R.layout.item_home_rc, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final FoodBean.DataBean.VendGoodsBean vendGoodsBean = dataBean.getVendGoods().get(position);
            Mholder mholder = (Mholder) holder;
            mholder.describe.setText(vendGoodsBean.getName());
            mholder.money.setText("￥" + vendGoodsBean.getSalePrice());
            Glide.with(fragment.getmActivity()).asBitmap().load(vendGoodsBean.getImgUrl()).into(mholder.food);
            mholder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(fragment.getmActivity(), WebActivity.class);
                    intent.putExtra("url",vendGoodsBean.getLinkUrl());
                    fragment.getmActivity().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataBean != null && dataBean.getVendGoods().size() > 0 ? dataBean.getVendGoods().size() : 0;
        }
    }

    class Mholder extends RecyclerView.ViewHolder {

        public TextView zhekou;
        public TextView money;
        public TextView describe;
        public ImageView food;
        public RelativeLayout item;

        public Mholder(View itemView) {
            super(itemView);
            zhekou = itemView.findViewById(R.id.home_zhekou);
            money = itemView.findViewById(R.id.home_money);
            describe = itemView.findViewById(R.id.home_describe);
            food = itemView.findViewById(R.id.home_food);
            item = itemView.findViewById(R.id.home_rc_item);
        }
    }
}
