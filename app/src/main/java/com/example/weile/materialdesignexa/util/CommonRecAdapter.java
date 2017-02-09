package com.example.weile.materialdesignexa.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by xiaoqing on 2016/9/22.
 */
public abstract class CommonRecAdapter<T> extends RecyclerView.Adapter<RecycleViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected  int mPosi;
    protected OnItemClickListener listener=null;

    public CommonRecAdapter(Context context, int layoutId, List<T> datas)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        RecycleViewHolder viewHolder = RecycleViewHolder.createViewHolder(mContext, parent, mLayoutId);
        setListener(parent,viewHolder,viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position)
    {
        convert(holder, mDatas.get(position),position);
    }

    protected void setListener(final ViewGroup parent, final RecycleViewHolder viewHolder, int viewType){
             viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(listener!=null){
                         int posi=viewHolder.getAdapterPosition();
                         mPosi=posi;
                         listener.onItemClick(parent,v,viewHolder,posi);
                     }
                 }
             });
    }
    public abstract void convert(RecycleViewHolder holder, T t,int posi);

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }
    public interface OnItemClickListener{
        void onItemClick(ViewGroup parent,View view, RecyclerView.ViewHolder holder, int position);
    }
    public void setOnItemClickListener(OnItemClickListener l){
        this.listener=l;
    }
    public int getPosi(){
        return mPosi;
    }
}
