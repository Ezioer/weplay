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
public abstract class CommonDiyRecAdapter<T> extends RecyclerView.Adapter<RecycleViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected  int mPosi;
    protected OnItemClickListener listener=null;
    private static int TYPE_HEADER=111;
    private static int TYPE_NORMAL=112;
    private View mHeaderViewId;


    public CommonDiyRecAdapter(Context context, int layoutId, List<T> datas)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderViewId==null)return TYPE_NORMAL;
        if(position==0)return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        RecycleViewHolder viewHolder=null;
        if(mHeaderViewId!=null&&viewType==TYPE_HEADER){
            viewHolder=RecycleViewHolder.createViewHolder(mContext, mHeaderViewId);
        }else{
            viewHolder = RecycleViewHolder.createViewHolder(mContext, parent, mLayoutId);
        }
        setListener(parent,viewHolder,viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position)
    {
        if(getItemViewType(position)==TYPE_HEADER) return;
        position=mHeaderViewId==null?holder.getLayoutPosition():holder.getLayoutPosition()-1;
        convert(holder, mDatas.get(position),position);
    }

    protected void setListener(final ViewGroup parent, final RecycleViewHolder viewHolder, int viewType){
        if(viewType==TYPE_HEADER)return;
             viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(listener!=null){
                         int posi=viewHolder.getAdapterPosition();
                         mPosi=posi;
                         listener.onItemClick(parent,v,viewHolder,posi-1);
                     }
                 }
             });
    }
    public abstract void convert(RecycleViewHolder holder, T t,int posi);

    @Override
    public int getItemCount()
    {
        return mHeaderViewId==null? mDatas.size():mDatas.size()+1;
    }
    public interface OnItemClickListener{
        void onItemClick(ViewGroup parent, View view, RecyclerView.ViewHolder holder, int position);
    }
    public void setOnItemClickListener(OnItemClickListener l){
        this.listener=l;
    }
    public int getPosi(){
        return mPosi;
    }

    public void addHeadView(View headerid){
        mHeaderViewId=headerid;
        notifyItemInserted(0);
    }
}
