package com.example.weile.materialdesignexa.ui.doubanmomentlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.weile.materialdesignexa.adapter.DoubanMomentAdapter;
import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseFrameFragment;
import com.example.weile.materialdesignexa.bean.DoubanMomentListBean;
import com.example.weile.materialdesignexa.ui.doubanmomentdetail.DoubanMomentDetailActivity;
import com.example.weile.materialdesignexa.util.Utils;
import com.example.weile.materialdesignexa.widget.ptr_layout.PtrClassicFrameLayout;
import com.example.weile.materialdesignexa.widget.ptr_layout.PtrDefaultHandler2;
import com.example.weile.materialdesignexa.widget.ptr_layout.PtrFrameLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;

/**
 * Created by weile on 2017/1/11.
 */
public class DouBanMomentFragment extends BaseFrameFragment<DoubanMomentPresenter,
        DoubanMomentModel> implements DoubanMomentListContract.View {
    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    @Bind(R.id.swiperefreshlayout)
    PtrClassicFrameLayout mRefreshLayout;
    @Bind(R.id.fla_button)
    FloatingActionButton mFlaButton;
    @Bind(R.id.rv_doubanlist)
    RecyclerView mRvDouban;
    private boolean isrefresh;
    private int mLoadcount = 0;
    private ArrayList<DoubanMomentListBean.Posts> mList=new ArrayList<>();
    private DoubanMomentAdapter mAdapter;

    public static DouBanMomentFragment newInstance() {
        return new DouBanMomentFragment();
    }

    public void getdata() {
        mPresenter.getDoubanMomentList(Utils.getDayDate(),0);
    }

    @Override
    public void refreshData(DoubanMomentListBean doubanMomentBean) {
        mList.clear();
        updatedata(doubanMomentBean);
    }

    @Override
    public void loadData(DoubanMomentListBean doubanMomentListBean) {
        updatedata(doubanMomentListBean);
    }

    private void updatedata(DoubanMomentListBean doubanMomentBean) {
        mList.addAll(doubanMomentBean.posts);
        mAdapter.notifyDataSetChanged();
        if(isrefresh)
            mRefreshLayout.refreshComplete();
    }

    @Override
    protected boolean isneedani() {
        return true;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_doubanlist;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        initAdapter();
        getdata();
        initListener();
    }

    private void initAdapter() {
        mRvDouban.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter=new DoubanMomentAdapter(mContext,mList);
        mRvDouban.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mRvDouban.setAdapter(mAdapter);
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new DoubanMomentAdapter.onRecyclerViewOnclicklistener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Intent intent=new Intent(mContext, DoubanMomentDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("postid",mList.get(position).id);
                bundle.putString("title",mList.get(position).title);
                bundle.putString("url",mList.get(position).url);
                if(mList.get(position).thumbs.size()!=0){
                    bundle.putString("picurl",mList.get(position).thumbs.get(0).large.url);
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mRefreshLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                isrefresh=true;
                mLoadcount++;
                mPresenter.getDoubanMomentList(Utils.getHisDate(mLoadcount),1);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isrefresh=true;
                mPresenter.getDoubanMomentList(Utils.getDayDate(),0);
            }
        });
        mFlaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                now.set(mYear, mMonth, mDay);
                DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar temp = Calendar.getInstance();
                        temp.clear();
                        temp.set(year, monthOfYear, dayOfMonth);
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        mPresenter.getDoubanMomentList(Utils.getDay(temp.getTimeInMillis()),0);
                    }
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                dialog.setMaxDate(Calendar.getInstance());
                Calendar minDate = Calendar.getInstance();
                minDate.set(2016, 1, 13);
                dialog.setMinDate(minDate);
                dialog.vibrate(false);
                dialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
    }

}
