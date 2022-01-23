package com.ncl.nclcustomerservice.commonutils;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by User on 2/8/2019.
 */

public class WrapContentGridLayoutManager extends GridLayoutManager {
    public WrapContentGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public WrapContentGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public WrapContentGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            Common.Log.i("onLayoutChildren..");
            super.onLayoutChildren(recycler, state);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        try {
            Common.Log.i("onLayoutCompleted..");
            super.onLayoutCompleted(state);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
