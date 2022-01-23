package com.ncl.nclcustomerservice.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutMapFragment extends Fragment {


    private ImageView addView;
    @BindView(R.id.message)
    TextView message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard, null);
        ButterKnife.bind(this, rootView);
        message.setText("Route Map coming soon...");
        ((TextView) ((MainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.title_text)).setText("Route Map");
        addView = ((MainActivity) getActivity()).findViewById(R.id.add_task);
        ((MainActivity) getActivity()).findViewById(R.id.filter_task).setVisibility(View.GONE);
        addView.setVisibility(View.GONE);


        return rootView;

    }


}
