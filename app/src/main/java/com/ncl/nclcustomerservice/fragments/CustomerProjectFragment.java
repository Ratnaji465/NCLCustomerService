package com.ncl.nclcustomerservice.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.activity.CreateCustomerProjectActivity;
import com.ncl.nclcustomerservice.activity.CreateNewContactActivity;
import com.ncl.nclcustomerservice.activity.MainActivity;

public class CustomerProjectFragment extends BaseFragment{
    private ImageView filterView;
    RecyclerView rvList;
    private ImageView addView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_customer_project, container, false);
        ((TextView) ((MainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.title_text)).setText("CUSTOMER PROJECT");
        filterView = ((MainActivity) getActivity()).findViewById(R.id.filter_task);
        filterView.setVisibility(View.GONE);
        rvList=view.findViewById(R.id.rvList);
        addView = ((MainActivity) getActivity()).findViewById(R.id.add_task);
        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(getActivity(), CreateCustomerProjectActivity.class);
                addIntent.putExtra("form_key", "new");
                startActivity(addIntent);
            }
        });
        return view;
    }
}
