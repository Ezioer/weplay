package com.example.weile.materialdesignexa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.bean.DoubanMomentListBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by weile on 2017/2/21.
 */
public class DoubanMomentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<DoubanMomentListBean.Posts> mList;
    private static final int NORMAL=11;
    private static final int NOIMG=12;
    private onRecyclerViewOnclicklistener onListener;
    public DoubanMomentAdapter(Context mContext,List<DoubanMomentListBean.Posts> mList){
        this.mContext=mContext;
        this.mList=mList;
        this.mLayoutInflater=LayoutInflater.from(mContext);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==NORMAL){
            return new normalViewHolder(mLayoutInflater.inflate(R.layout.item_doubanlist,null),onListener);
        }else if(viewType==NOIMG){
            return new noImgViewHolder(mLayoutInflater.inflate(R.layout.item_doubanlistnoimg,null),onListener);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(mList.get(position).thumbs.size()==0){
            return NOIMG;
        }
        return NORMAL;
    }

    public class normalViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
        TextView mTitle,mSubTitle;
        ImageView mImage;
        onRecyclerViewOnclicklistener mListener;

        public normalViewHolder(View itemView,onRecyclerViewOnclicklistener listener) {
            super(itemView);
            mTitle= (TextView) itemView.findViewById(R.id.tv_title);
            mSubTitle= (TextView) itemView.findViewById(R.id.tv_subtitle);
            mImage= (ImageView) itemView.findViewById(R.id.iv_pic);
            this.mListener=listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(null!=mListener){
                mListener.onItemClickListener(view,getLayoutPosition());
            }
        }
    }

    public class noImgViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTitle,mSubTitle;
        onRecyclerViewOnclicklistener mListener;
        public noImgViewHolder(View itemview,onRecyclerViewOnclicklistener listener){
            super(itemview);
            mTitle= (TextView) itemView.findViewById(R.id.tv_title);
            mSubTitle= (TextView) itemView.findViewById(R.id.tv_subtitle);
            this.mListener=listener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if(null!=mListener){
                mListener.onItemClickListener(view,getLayoutPosition());
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof normalViewHolder){
            Picasso.with(mContext).load(mList.get(position).thumbs.get(0)
                    .small.url).into(((normalViewHolder) holder).mImage);
            ((normalViewHolder) holder).mTitle.setText(mList.get(position).title);
            ((normalViewHolder) holder).mSubTitle.setText(mList.get(position).abs);
        }else{
            ((noImgViewHolder) holder).mTitle.setText(mList.get(position).title);
            ((noImgViewHolder) holder).mSubTitle.setText(mList.get(position).abs);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnItemClickListener(onRecyclerViewOnclicklistener listener){
        this.onListener=listener;

    }

    public interface onRecyclerViewOnclicklistener{
        void onItemClickListener(View view, int pos);
    }
}
