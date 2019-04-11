package com.hangzhou.hezi.vendingmachine.Presenter;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hangzhou.hezi.vendingmachine.Fragment.FoodFragment;
import com.hangzhou.hezi.vendingmachine.Fragment.HomeFragment;
import com.hangzhou.hezi.vendingmachine.MUtils.DpUtils;
import com.hangzhou.hezi.vendingmachine.MUtils.GsonUtils;
import com.hangzhou.hezi.vendingmachine.R;
import com.hangzhou.hezi.vendingmachine.bean.FoodBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/3/19.
 */

public class HomeFragmentPresenter {
    HomeFragment fragment;
    private FoodBean.DataBean dataBean;
    ArrayList<FoodFragment> fragments=new ArrayList<>();
    private ArrayList<View> list;

    public HomeFragmentPresenter(HomeFragment fragment){
        this.fragment=fragment;
    }

    public void initData(String json){
        dataBean = GsonUtils.GsonToBean(json, FoodBean.DataBean.class);
        if(dataBean!=null){
            int page=dataBean.getVendGoods().size()/9+1;
            if(page>1){
                for(int x=0;x<page;x++){
                    if(x==page-1  &&dataBean.getVendGoods().size()>0){
                        fragments.add(FoodFragment.newInstance(fragment.getmActivity(),GsonUtils.GsonString(dataBean)));
                    }else{
                        if(dataBean.getVendGoods().size()>0){
                            ArrayList<FoodBean.DataBean.VendGoodsBean> bean=new ArrayList<FoodBean.DataBean.VendGoodsBean>();
                            for(int z=0;z<9;z++){
                                bean.add(dataBean.getVendGoods().get(0));
                                dataBean.getVendGoods().remove(0);
                            }
                            FoodBean.DataBean dataBean1 = new FoodBean.DataBean();
                            dataBean1.setVendGoods(bean);
                            fragments.add(FoodFragment.newInstance(fragment.getmActivity(),GsonUtils.GsonString(dataBean1)));
                        }
                    }
                }
            }else if(page==1){
                fragments.add(FoodFragment.newInstance(fragment.getmActivity(),GsonUtils.GsonString(dataBean)));
            }
            list = new ArrayList<>();

            for(int x=0;x<fragments.size();x++){
                View view=new View(fragment.getmActivity());
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(DpUtils.dip2px(fragment.getmActivity(),12),DpUtils.dip2px(fragment.getmActivity(),3));
                params.rightMargin=DpUtils.dip2px(fragment.getmActivity(),10);
                params.gravity= Gravity.CENTER;
                view.setLayoutParams(params);
                view.setBackgroundColor(Color.parseColor("#70dfdfdf"));
                fragment.getHomeFrIndicator().addView(view);
                list.add(view);
            }
            if(list.size()>0){
                list.get(0).setBackgroundColor(Color.parseColor("#ffffff"));
            }
            fragment.getHomeFrViewPager().setAdapter(new HomeAdapter());
        }
    }

    public void viewPagerLinstener(){
        fragment.getHomeFrViewPager().setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               for(int x=0;x<list.size();x++){
                   if(x==position){
                       list.get(x).setBackgroundColor(Color.parseColor("#ffffff"));
                   }else{
                       list.get(x).setBackgroundColor(Color.parseColor("#70dfdfdf"));
                   }
               }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class HomeAdapter extends PagerAdapter{

        public HomeAdapter() {
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(fragments.get(position).getmView());
            return fragments.get(position).getmView();

        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
