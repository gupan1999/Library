package com.example.version1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/11/6 0006.
 * huangjialin
 * 普通类型的适配器
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private int mLayoutId;
    private List<T> mData;


    public BaseRecyclerAdapter(Context mContext, int mLayoutId, List<T> mData) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        this.mData = mData;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       return BaseViewHolder.getRecyclerHolder(mContext, parent, mLayoutId);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        convert(holder, mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 对外提供的方法
     */
    public abstract void convert(BaseViewHolder holder, T t);

}