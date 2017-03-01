package com.example.weile.materialdesignexa.ui.girlphoto;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.weile.materialdesignexa.adapter.GirlPhotoAdapter;
import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.base.BaseFrameFragment;
import com.example.weile.materialdesignexa.bean.GirlData;
import com.example.weile.materialdesignexa.ui.girlphotodetail.GirlphotoDetailActivity;
import com.example.weile.materialdesignexa.widget.ptr_layout.PtrClassicFrameLayout;
import com.example.weile.materialdesignexa.widget.ptr_layout.PtrDefaultHandler2;
import com.example.weile.materialdesignexa.widget.ptr_layout.PtrFrameLayout;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by weile on 2017/2/22.
 */
public class GirlPhotoListFragment extends BaseFrameFragment<GirlPhotoListPresenter,GirlPhotoListModel> implements GirlPhotoListContract.ViewPhoto{
   @Bind(R.id.rv_girlphotolist)
    RecyclerView mRvGirl;
    @Bind(R.id.swiperefreshlayout)
    PtrClassicFrameLayout mSwipe;
    private GirlPhotoAdapter mAdapter;
    private ArrayList<GirlData.Results> mList=new ArrayList<>();
    private int size=30,page=1;
    private boolean isRefresh=false;
    public static GirlPhotoListFragment newInstance(){
        return new GirlPhotoListFragment();
    }
    @Override
    protected boolean isneedani() {
        return true;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_girlphotolist;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        initAdapter();
        getData();
        initListener();
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new GirlPhotoAdapter.RecyclerViewClick() {
            @Override
            public void onItemClick(View view, int posi) {
                Intent intent=new Intent(mContext, GirlphotoDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("picurl",mList.get(posi).url);
                intent.putExtras(bundle);
                ImageView imageView= (ImageView) view.findViewById(R.id.iv_pic);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(getActivity(), imageView, "transition_animation_girlphotos");
                    startActivity(intent, options.toBundle());
                } else {
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                    ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                }
            }
        });
        mSwipe.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                isRefresh=true;
                page++;
                mPresenter.getGirlData(size,page,1);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isRefresh=true;
                size=30;
                page=1;
                mPresenter.getGirlData(size,page,0);
            }
        });
    }

    private void initAdapter() {
//        mRvGirl.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRvGirl.setLayoutManager(new GridLayoutManager(mContext,2));
        mAdapter=new GirlPhotoAdapter(mList,mContext);
        mRvGirl.setAdapter(mAdapter);
    }

    private void getData() {
        mPresenter.getGirlData(size,page,0);
    }

    @Override
    public void refreshData(GirlData data) {
        mList.clear();
        updateDate(data);
    }

    private void updateDate(GirlData data) {
        mList.addAll(data.results);
        mAdapter.notifyDataSetChanged();
        if(isRefresh){
            mSwipe.refreshComplete();
        }
    }

    @Override
    public void loadData(GirlData data) {
        updateDate(data);
    }
}
