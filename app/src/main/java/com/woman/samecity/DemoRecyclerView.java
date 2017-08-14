package com.woman.samecity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import Recharge.RechargeInterface;
import Recharge.RechargeItem;
import Recharge.RechargePresenter;
import Recharge.SimpleRecyclerAdapter;
import Recharge.SimpleRecyclerHolder;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class DemoRecyclerView extends Activity implements RechargeInterface {

    private RecyclerView recyclerView;
    private RechargePresenter mrechargePresenter;
    private FrameLayout flAlipay,flwechat;
    private TextView tv_recharge_chanel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        recyclerView= (RecyclerView) findViewById(R.id.test_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mrechargePresenter=new RechargePresenter(this,this);
        mrechargePresenter.loadRechargeMap(2,"1110f83c48545a652f666c1bd6b25117");
        flAlipay= (FrameLayout) findViewById(R.id.fl_alipay);
        flwechat= (FrameLayout) findViewById(R.id.fl_wechat);
        tv_recharge_chanel= (TextView) findViewById(R.id.recharge_way);
        RxView.clicks(flAlipay)
                .throttleFirst(50, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        tv_recharge_chanel.setText(getString((R.string.choosed_recharge_way),getString(R.string.recharge_alipay)));
                        flAlipay.setSelected(true);
                        flwechat.setSelected(false);
                    }
                });
        RxView.clicks(flwechat)
                .throttleFirst(50, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        tv_recharge_chanel.setText(getString((R.string.choosed_recharge_way),getString(R.string.recharge_wechat)));
                        flAlipay.setSelected(false);
                        flwechat.setSelected(true);
                    }
                });
    }
    @Override
    public void showRechargeList(List<RechargeItem> list) {
        recyclerView.setAdapter(new RechargeMapAdapter(list));
    }
    @Override
    public void showNetworkException() {
    }
    @Override
    public void showUnknownException() {
    }
    @Override
    public void showDataException(String msg) {
    }
    @Override
    public void showLoadingComplete() {
    }
    @Override
    public Dialog showLoadingDialog() {
        return null;
    }
    @Override
    public void dismissLoadingDialog() {
    }
    private class RechargeMapAdapter extends SimpleRecyclerAdapter<RechargeItem,
            RechargeMapHolder> {
        public RechargeMapAdapter(List<RechargeItem> list) {
            super(list);
        }
        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_recharges;
        }
        @NonNull
        @Override
        protected RechargeMapHolder createHolder(View view) {
            return new RechargeMapHolder(view);
        }
    }
    private class RechargeMapHolder extends SimpleRecyclerHolder<RechargeItem> {
        private TextView tvCurrency, tvRmb, tvTips;
        public RechargeMapHolder(View itemView) {
            super(itemView);
            tvCurrency= (TextView) itemView.findViewById(R.id.item_recharge_tv_coin_amount);
            tvRmb= (TextView) itemView.findViewById(R.id.item_recharge_tv_rmb_amount);
            tvTips= (TextView) itemView.findViewById(R.id.item_recharge_tv_tips);
        }
        @Override
        public void displayData(final RechargeItem data) {
            tvCurrency.setText("钻石:" + data.getCurrencyAmount());
            tvRmb.setText("花费:" + data.getRmbAmount());
            if (data.getPresent().equals("0")) {
                tvTips.setVisibility(View.GONE);
            } else {
                tvTips.setText("赠送:" + data.getPresent());
            }
            RxView.clicks(itemView)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            perfromRecharge(data.getRmbAmount());
                        }
           private void perfromRecharge(String rmbAmount) {
               if (tv_recharge_chanel.getText().equals(getString((R.string.choosed_recharge_way),getString(R.string.recharge_alipay)))){
                   mrechargePresenter.RechargeAlipay(rmbAmount);
                            }
               else if (tv_recharge_chanel.getText().equals(getString((R.string.choosed_recharge_way),getString(R.string.recharge_wechat)))){
                   mrechargePresenter.RechargeWechat("1110f83c48545a652f666c1bd6b25117",rmbAmount);
               }
            }
           });

        }
    }
}
