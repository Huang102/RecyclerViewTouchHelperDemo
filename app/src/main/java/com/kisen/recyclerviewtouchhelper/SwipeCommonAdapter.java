package com.kisen.recyclerviewtouchhelper;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kisen.touchhelper.swipe.SwipeMenu;
import com.kisen.touchhelper.swipe.SwipeMenuCreator;
import com.kisen.touchhelper.swipe.SwipeViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title : 带侧滑按钮适配器
 * @Description :
 * @Version : v2.1.5
 * Created by huang on 2016/12/5.
 */

public class SwipeCommonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int layoutRes;
    private SwipeMenuCreator creator;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<String> mData;
    private OnItemClickListener listener;

    public SwipeCommonAdapter(Context context, @LayoutRes int layoutRes) {
        this.context = context;
        this.layoutRes = layoutRes;
        mData = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<String> data) {
        if (data == null)
            return;
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void removed(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void setMenuCreator(SwipeMenuCreator creator) {
        this.creator = creator;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SwipeViewHolder(mLayoutInflater.inflate(layoutRes, null, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SwipeViewHolder) {
            final SwipeViewHolder swipeViewHolder = (SwipeViewHolder) holder;
            SwipeMenu menu = new SwipeMenu(context);
            if (creator != null)
                creator.create(menu);
            swipeViewHolder.addMenu(menu);
            swipeViewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(v, swipeViewHolder.getAdapterPosition());
                }
            });

            TextView view = swipeViewHolder.getView(R.id.content);
            view.setText(mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
