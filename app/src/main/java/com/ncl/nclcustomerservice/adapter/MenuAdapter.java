package com.ncl.nclcustomerservice.adapter;

import android.app.Activity;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ncl.duo_navigation_drawer.views.DuoOptionView;
import com.ncl.nclcustomerservice.object.LeftNav;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 10/1/2018.
 */

public class MenuAdapter extends BaseAdapter {
  //  private List<String> mOptions = new ArrayList<>();
    private List<LeftNav> leftNavs = new ArrayList<>();
 //   private List<Integer> mIcons = new ArrayList<>();
    private ArrayList<DuoOptionView> mOptionViews = new ArrayList<>();
    private Activity context;


    public MenuAdapter(List<LeftNav> leftNavs, List<String> mTitles, List<Integer> mIcons, Activity activity) {
        this.leftNavs = leftNavs;
       // this.mOptions = mTitles;
        //this.mIcons = mIcons;
        this.context = activity;

    }

    @Override
    public int getCount() {
        return leftNavs.size();
    }

    @Override
    public Object getItem(int position) {
        return leftNavs.get(position).name;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setViewSelected(int position, boolean selected) {

        // Looping through the options in the menu
        // Selecting the chosen option
        for (int i = 0; i < mOptionViews.size(); i++) {
            if (i == position) {
                mOptionViews.get(i).setSelected(true, context);
            } else {
                mOptionViews.get(i).setSelected(false, context);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String option = leftNavs.get(position).name;
        final int icon = leftNavs.get(position).drawable;
        // Using the DuoOptionView to easily recreate the demo
        final DuoOptionView optionView;
        if (convertView == null) {
            optionView = new DuoOptionView(parent.getContext());
        } else {
            optionView = (DuoOptionView) convertView;
        }

        // Using the DuoOptionView's default selectors
        optionView.bind(option, icon, null);

        // Adding the views to an array list to handle view selection
        mOptionViews.add(optionView);

        return optionView;
    }
}
