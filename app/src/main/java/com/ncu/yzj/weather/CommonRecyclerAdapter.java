package com.ncu.yzj.weather;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by 叶长建
 * on 2020/3/15 15:59
 */
public class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<BindingViewHolder> {
    private int mLayoutId;
    private int mVariableId;
    private List<T> mList;

    public List<T> getList() {
        return mList;
    }
    public void setList(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void setLayoutId(@LayoutRes int layoutId) {
        this.mLayoutId = layoutId;
    }

    public void setVariableId( int variableId) {
        this.mVariableId = variableId;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, mLayoutId, parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        if (mVariableId == 0 ){
            return;
        }
        if (mList == null){
            return;
        }
        holder.getBinding().setVariable(mVariableId,mList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mList == null){
            return 0;
        }
        return mList.size();
    }
}
