package com.like.demorecycleview;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.List;

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
   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_recycleview);
        recyclerView= (RecyclerView) findViewById(R.id.test_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
       // recyclerView.addItemDecoration(1);
        mrechargePresenter=new RechargePresenter(this);
        mrechargePresenter.loadRechargeMap(2000);
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        recyclerView= (RecyclerView) findViewById(R.id.test_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        // recyclerView.addItemDecoration(1);
        mrechargePresenter=new RechargePresenter(this);
        mrechargePresenter.loadRechargeMap(2,"b2d57ac6b86baa2552a812a3ee68bf46");
    }

    @Override
    public void showRechargeList(List<RechargeItem> list) {
        recyclerView.setAdapter(new RechargeMapAdapter(list));

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
           /* tvCurrency = $(itemView, R.id.item_recharge_tv_coin_amount);
            tvRmb = $(itemView, R.id.item_recharge_tv_rmb_amount);
            tvTips = $(itemView, R.id.item_recharge_tv_tips);*/
           tvCurrency= (TextView) findViewById(R.id.item_recharge_tv_coin_amount);
            tvRmb= (TextView) findViewById(R.id.item_recharge_tv_rmb_amount);
            tvTips= (TextView) findViewById(R.id.item_recharge_tv_tips);
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
//            tvCurrency.setText(data.getCurrencyAmount());
//            tvRmb.setText(getString(R.string.recharge_rmb_amount, data.getRmbAmount()));
//            if (!TextUtils.isEmpty(data.getMsg())) {
//                tvTips.setText(data.getMsg());
//                tvTips.setVisibility(View.VISIBLE);
//            } else {
//                tvTips.setVisibility(View.GONE);
//            }
           /* subscribeClick(itemView, new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    //performRecharge(data.getRmbAmount());
                    //Stub!
//                    toastShort("充值成功");
                  *//*  presenter.performRechargeHuaqiPay(LocalDataManager.getInstance().getLoginInfo().getToken(),
                            LocalDataManager.getInstance().getLoginInfo().getUserId(),
                            data.getRmbAmount(),String.valueOf(mSelectedChannel),data.getCurrencyAmount());*//*
                }
            });*/
            RxView.clicks(itemView)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {

                        }
                    });

        }
    }



}
