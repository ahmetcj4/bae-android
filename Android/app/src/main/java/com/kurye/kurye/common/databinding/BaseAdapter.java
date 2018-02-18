package com.kurye.kurye.common.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder<T>> {
    List<T> itemList;

    BaseAdapter(List<T> itemList) {
        this.itemList = itemList;
    }

    public ViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new ViewHolder<>(binding);
    }

    void updateData(List<T> items) {
        itemList = items;
        notifyDataSetChanged();
    }

    public void onBindViewHolder(ViewHolder<T> holder, int position) {
        T obj = getObjForPosition(position);
        holder.bind(obj);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    protected abstract T getObjForPosition(int position);

    protected abstract int getLayoutIdForPosition(int position);
}