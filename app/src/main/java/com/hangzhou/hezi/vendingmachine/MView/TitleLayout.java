
package com.hangzhou.hezi.vendingmachine.MView;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hangzhou.hezi.vendingmachine.MUtils.DpUtils;
import com.hangzhou.hezi.vendingmachine.R;

/**
 * Created by linyujie on 18/9/18.
 * 顶部标题栏
 */

public class TitleLayout extends RelativeLayout {

    TextView titleLetfText;
    ImageView titleLetfImage;
    TextView titleCenterText;
    TextView titleRightText;
    ImageView titleRightImage;
    private View view;
    private String leftTextStr;
    private String rightTextStr;
    private String centerTextStr;
    private float leftTextSize;
    private float rightTextSize;
    private float centerTextSize;
    private int leftTextColor;
    private int rightTextColor;
    private int centerTextColor;
    private int leftImageResource;
    private int rightImageResource;
    private float leftImageWidthSize;
    private float leftImageHeightSize;
    private float rightImageWidthSize;
    private float rightImageHeightSize;
    private float leftMargin;
    private float rightMargin;

    public TitleLayout(Context context) {
        super(context);
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
        initDate(context);
        addView(view);
    }


    public TitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
        initDate(context);
        addView(view);
    }

    private void initView(Context context, AttributeSet attrs) {
        view = View.inflate(context, R.layout.title_view_layout, null);
        titleLetfText=view.findViewById(R.id.title_letf_text);
        titleLetfImage=view.findViewById(R.id.title_letf_image);
        titleCenterText=view.findViewById(R.id.title_center_text);
        titleRightText=view.findViewById(R.id.title_right_text);
        titleRightImage=view.findViewById(R.id.title_right_image);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleAttr);
        leftTextStr = typedArray.getString(R.styleable.TitleAttr_leftText);
        rightTextStr = typedArray.getString(R.styleable.TitleAttr_rightText);
        centerTextStr = typedArray.getString(R.styleable.TitleAttr_centerText);
        leftTextSize = typedArray.getDimension(R.styleable.TitleAttr_leftTextSize, 0);
        rightTextSize = typedArray.getDimension(R.styleable.TitleAttr_rightTextSize, 0);
        centerTextSize = typedArray.getDimension(R.styleable.TitleAttr_centerTextSize, 0);
        leftMargin = typedArray.getDimension(R.styleable.TitleAttr_leftMargin, 0);
        rightMargin = typedArray.getDimension(R.styleable.TitleAttr_rightMargin, 0);
        leftTextColor = typedArray.getColor(R.styleable.TitleAttr_leftTextColor, Color.BLACK);
        rightTextColor = typedArray.getColor(R.styleable.TitleAttr_rightTextColor, Color.BLACK);
        centerTextColor = typedArray.getColor(R.styleable.TitleAttr_centerTextColor, Color.BLACK);
        leftImageResource = typedArray.getResourceId(R.styleable.TitleAttr_leftImageBg, 0);
        rightImageResource = typedArray.getResourceId(R.styleable.TitleAttr_rightImageBg, 0);
        leftImageWidthSize = typedArray.getDimension(R.styleable.TitleAttr_leftImageWidthSize, 0);
        leftImageHeightSize = typedArray.getDimension(R.styleable.TitleAttr_leftImageHeightSize, 0);
        rightImageWidthSize = typedArray.getDimension(R.styleable.TitleAttr_rightImageWidthSize, 0);
        rightImageHeightSize = typedArray.getDimension(R.styleable.TitleAttr_rightImageHeightSize, 0);
        typedArray.recycle();
    }


    private void initDate(Context context) {
        if (!TextUtils.isEmpty(leftTextStr)) {
            titleLetfText.setVisibility(VISIBLE);
            RelativeLayout.LayoutParams paramsLeftText =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsLeftText.leftMargin = (int) leftMargin;
            paramsLeftText.addRule(CENTER_VERTICAL);
            paramsLeftText.addRule(ALIGN_PARENT_RIGHT);
            titleLetfText.setLayoutParams(paramsLeftText);
            titleLetfText.setText(leftTextStr);
        }
        if (!TextUtils.isEmpty(rightTextStr)) {
            titleRightText.setVisibility(VISIBLE);
            RelativeLayout.LayoutParams paramsRightText =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsRightText.rightMargin = (int) rightMargin;
            paramsRightText.addRule(CENTER_VERTICAL);
            paramsRightText.addRule(ALIGN_PARENT_RIGHT);
            titleRightText.setLayoutParams(paramsRightText);
            titleRightText.setText(rightTextStr);
        }
        if (!TextUtils.isEmpty(centerTextStr)) {
            titleCenterText.setVisibility(VISIBLE);
            RelativeLayout.LayoutParams paramsCenterText =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsCenterText.rightMargin = (int) rightMargin;
            paramsCenterText.addRule(CENTER_IN_PARENT);
            titleCenterText.setLayoutParams(paramsCenterText);
            titleCenterText.setText(centerTextStr);
        }
        if (leftTextSize != 0) {
            titleLetfText.setTextSize(DpUtils.px2dip(context,leftTextSize));
        }
        if (rightTextSize != 0) {
            titleRightText.setTextSize(DpUtils.px2dip(context,rightTextSize));
        }
        if (centerTextSize != 0) {
            titleCenterText.setTextSize(DpUtils.px2dip(context,centerTextSize));
        }
        titleLetfText.setTextColor(leftTextColor);
        titleRightText.setTextColor(rightTextColor);
        titleCenterText.setTextColor(centerTextColor);
        if (leftImageResource != 0) {
            titleLetfImage.setBackgroundResource(leftImageResource);
        }
        if (rightImageResource != 0) {
            titleRightImage.setBackgroundResource(rightImageResource);
        }
        RelativeLayout.LayoutParams paramsLeftImage =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsLeftImage.width = (int) leftImageWidthSize;
        paramsLeftImage.height = (int) leftImageHeightSize;
        paramsLeftImage.leftMargin = (int) leftMargin;
        paramsLeftImage.addRule(CENTER_VERTICAL);
        paramsLeftImage.addRule(ALIGN_PARENT_LEFT);
        titleLetfImage.setLayoutParams(paramsLeftImage);


        RelativeLayout.LayoutParams paramsRightImage =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsRightImage.width = (int) rightImageWidthSize;
        paramsRightImage.height = (int) rightImageHeightSize;
        paramsRightImage.rightMargin = (int) rightMargin;
        paramsRightImage.addRule(ALIGN_PARENT_RIGHT);
        paramsRightImage.addRule(CENTER_VERTICAL);
        titleRightImage.setLayoutParams(paramsRightImage);

        RelativeLayout.LayoutParams viewparams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(viewparams);
    }

    public void setTitle(String title){
        titleCenterText.setText(title);
    }

    //返回左iv
    public ImageView getLeftImage(){
        return titleLetfImage;
    }
    //返回又tv
    public TextView getRightText(){
        return titleRightText;
    }

}
