package com.ncu.yzj.weather;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

/**
 * Created by 叶长建
 * on 2020/3/15 15:54
 */
public class BindingViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding mBinding;
    public BindingViewHolder(@NonNull ViewDataBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }
    public ViewDataBinding getBinding(){
        return mBinding;
    }
}
