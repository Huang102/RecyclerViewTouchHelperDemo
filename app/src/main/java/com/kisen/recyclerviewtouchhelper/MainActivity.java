package com.kisen.recyclerviewtouchhelper;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.kisen.touchhelper.swipe.OnActivityTouchListener;
import com.kisen.touchhelper.swipe.RecyclerTouchListener.RecyclerTouchListenerHelper;
import com.kisen.touchhelper.swipe.RecyclerTouchListener;
import com.kisen.touchhelper.swipe.SwipeMenu;
import com.kisen.touchhelper.swipe.SwipeMenuCreator;
import com.kisen.touchhelper.swipe.SwipeMenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerTouchListenerHelper {

    private RecyclerView mRecyclerView;
    private SwipeCommonAdapter adapter;
    private OnActivityTouchListener mTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SwipeCommonAdapter(this, R.layout.item_view);
        mRecyclerView.setAdapter(adapter);
        RecyclerTouchListener mTouchListener = new RecyclerTouchListener(this, mRecyclerView);
        mTouchListener
                .setSwipeOptionViews(0)
                .setSwipeable(R.id.container_item, R.id.container_action, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        switch (viewID) {
                            case 0:
                                Toast.makeText(MainActivity.this, "delete the item of " + position, Toast.LENGTH_SHORT).show();
                                adapter.removed(position);
                                break;
                        }
                    }
                });
        mRecyclerView.addOnItemTouchListener(mTouchListener);

        adapter.setMenuCreator(new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem item = new SwipeMenuItem(MainActivity.this);
                item.setBackground(new ColorDrawable(Color.RED));
                item.setTitle("delete");
                item.setWidth(dpToPx(100));
                item.setTitleColor(Color.WHITE);
                item.setTitleSize(20);
                menu.addMenuItem(item);
            }
        });
        adapter.setOnItemClickListener(new SwipeCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "click the item of " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("swipe item " + i);
        }
        adapter.setList(list);
    }

    public int dpToPx(int dp) {
        return (int) (dp * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mTouchHelper != null)
            mTouchHelper.getTouchCoordinates(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setOnActivityTouchListener(OnActivityTouchListener listener) {
        mTouchHelper = listener;
    }
}
