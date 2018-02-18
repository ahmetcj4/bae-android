package com.kurye.kurye.common.databinding;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.kurye.kurye.BR;


public class ViewHolder<T> extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;

    public ViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(T vm) {
        binding.setVariable(BR.vm, vm);
        binding.executePendingBindings();
    }
}