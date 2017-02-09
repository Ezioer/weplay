package com.example.weile.materialdesignexa.ui.list;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.activity.DoubanMomentDetailActivity;
import com.example.weile.materialdesignexa.base.BaseFrameFragment;
import com.example.weile.materialdesignexa.bean.DoubanMomentListBean;
import com.example.weile.materialdesignexa.util.CommonRecAdapter;
import com.example.weile.materialdesignexa.util.RecycleViewHolder;
import com.example.weile.materialdesignexa.util.Utils;
import com.example.weile.materialdesignexa.widget.ptr_layout.PtrClassicFrameLayout;
import com.example.weile.materialdesignexa.widget.ptr_layout.PtrDefaultHandler2;
import com.example.weile.materialdesignexa.widget.ptr_layout.PtrFrameLayout;
import com.squareup.picasso.Picasso;
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
    private CommonRecAdapter<DoubanMomentListBean.Posts> mAdapter;

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
        mAdapter = new CommonRecAdapter<DoubanMomentListBean.Posts>(mContext, R.layout.item_doubanlist,
                mList) {
            @Override
            public void convert(RecycleViewHolder holder, DoubanMomentListBean.Posts doubanMomentBean, int
                    posi) {
                holder.setText(R.id.tv_title, doubanMomentBean.title);
                holder.setText(R.id.tv_subtitle, doubanMomentBean.abs);
                ImageView imageView = holder.getView(R.id.iv_pic);
                if (doubanMomentBean.thumbs.size()<=0) {
                    return;
                }
                Picasso.with(mContext).load(doubanMomentBean.thumbs.get(0)
                        .small.url).into(imageView);
            }
        };
        mRvDouban.setAdapter(mAdapter);
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new CommonRecAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RecyclerView.ViewHolder holder,
                                    int position) {
                Intent intent=new Intent(mContext, DoubanMomentDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("postid",mList.get(position).id);
                bundle.putString("title",mList.get(position).title);
                bundle.putString("url",mList.get(position).url);
                intent.putExtras(bundle);
                ImageView imageView= (ImageView) view.findViewById(R.id.iv_pic);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(getActivity(), imageView, "transition_animation_news_douban_photos");
                    startActivity(intent, options.toBundle());
                } else {
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                    ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                }
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
                // set the dialog not vibrate when date change, default value is true
                dialog.vibrate(false);

                dialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
    }

    @Override
    public void RequestStart() {

    }

    @Override
    public void Requesterror() {

    }

    @Override
    public void RequestEnd() {

    }

    @Override
    public void NetError() {

    }

}
