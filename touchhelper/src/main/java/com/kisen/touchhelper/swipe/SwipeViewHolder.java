package com.kisen.touchhelper.swipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kisen.touchhelper.R;

/**
 * @Title : 侧滑
 * @Description :
 * @Version :
 * Created by huang on 2016/12/5.
 */

public class SwipeViewHolder extends RecyclerView.ViewHolder {

    protected final SparseArray<View> views;
    private final LinearLayout actionContainer;
    private Context context;
    private final FrameLayout itemContainer;

    public SwipeViewHolder(View view) {
        super(LayoutInflater.from(view.getContext()).inflate(R.layout.item_swipe_view, null, false));
        views = new SparseArray<>();
        context = view.getContext();
        itemContainer = (FrameLayout) itemView.findViewById(R.id.container_item);
        actionContainer = (LinearLayout) itemView.findViewById(R.id.container_action);
        itemContainer.addView(view);
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public void addMenu(SwipeMenu menu) {
        actionContainer.removeAllViews();
        for (int i = 0; i < menu.getMenuItems().size(); i++) {
            SwipeMenuItem menuItem = menu.getMenuItem(i);
            menuItem.setId(i);
            addItem(menuItem, menuItem.getId());
        }
    }

    public View getItemView(){
        return itemContainer;
    }

    private void addItem(SwipeMenuItem item, int id) {
        int width = item.getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width == 0 ? LinearLayout.LayoutParams.WRAP_CONTENT : width,
                LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout parent = new LinearLayout(context);
        parent.setId(id);
        parent.setGravity(Gravity.CENTER);
        parent.setOrientation(LinearLayout.VERTICAL);
        parent.setLayoutParams(params);
        parent.setBackgroundDrawable(item.getBackground());
        actionContainer.addView(parent);

        if (item.getIcon() != null) {
            parent.addView(createIcon(item));
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            parent.addView(createTitle(item));
        }

    }

    private ImageView createIcon(SwipeMenuItem item) {
        ImageView iv = new ImageView(context);
        iv.setImageDrawable(item.getIcon());
        return iv;
    }

    private TextView createTitle(SwipeMenuItem item) {
        TextView tv = new TextView(context);
        tv.setText(item.getTitle());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(item.getTitleSize());
        tv.setTextColor(item.getTitleColor());
        return tv;
    }
}
