package com.example.weile.materialdesignexa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.bean.GirlData;
import com.example.weile.materialdesignexa.util.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by weile on 2017/2/22.
 */
public class GirlPhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<GirlData.Results> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private RecyclerViewClick mListener;

    public GirlPhotoAdapter(ArrayList mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(mInflater.inflate(R.layout.item_girlphoto, null), mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LinearLayout.LayoutParams laParams = (LinearLayout.LayoutParams) ((viewHolder) holder).mPic
                .getLayoutParams();
        laParams.width = (Utils.getWindowWidth() - Utils.dpToPx(6)) / 2;
        ((viewHolder) holder).mPic.setLayoutParams(laParams);
        Picasso.with(mContext).load(mList.get(position).url).into(((viewHolder) holder).mPic);
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mPic;
        RecyclerViewClick mClickListener;

        public viewHolder(View itemView, RecyclerViewClick mListener) {
            super(itemView);
            mPic = (ImageView) itemView.findViewById(R.id.iv_pic);
            this.mClickListener = mListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (null != mClickListener) {
                mClickListener.onItemClick(view, getLayoutPosition());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnItemClickListener(RecyclerViewClick mListener) {
        this.mListener = mListener;
    }

    public interface RecyclerViewClick {
        void onItemClick(View view, int posi);
    }
}
