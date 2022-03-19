package com.ncl.nclcustomerservice.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.activity.CreateCustomerProjectActivity;
import com.ncl.nclcustomerservice.activity.MainActivity;
import com.ncl.nclcustomerservice.activity.ViewCustomerProjectActivity;
import com.ncl.nclcustomerservice.adapter.CustomerProjectAdapter;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.network.RetrofitRequestController;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.CustomerProjectResListVO;
import com.ncl.nclcustomerservice.object.CustomerProjectResVO;
import com.ncl.nclcustomerservice.object.Team;

import java.util.Collections;
import java.util.List;

public class CustomerProjectFragment extends BaseFragment implements RetrofitResponseListener {
    private ImageView filterView,searchIv;
    RecyclerView rvList;
    private String queryString = "%%";
    private ImageView addView;
    DatabaseHandler db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_customer_project, container, false);
        ((TextView) ((MainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.title_text)).setText("CUSTOMER PROJECT");
        filterView = ((MainActivity) getActivity()).findViewById(R.id.filter_task);
        searchIv=((MainActivity) getActivity()).findViewById(R.id.searchIv);
        searchIv.setVisibility(View.VISIBLE);
        if (Common.getUserTeam(getActivity()).size() > 1)
            filterView.setVisibility(View.VISIBLE);
        else
            filterView.setVisibility(View.GONE);
        filterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.getSelectedUser(CustomerProjectFragment.this);
            }
        });
        rvList = view.findViewById(R.id.rvList);

        db = DatabaseHandler.getDatabase(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(linearLayoutManager);

        addView = ((MainActivity) getActivity()).findViewById(R.id.add_task);
        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(getActivity(), CreateCustomerProjectActivity.class);
                addIntent.putExtra("form_key", "new");
                startActivity(addIntent);
            }
        });
        ((SearchView)(((MainActivity) getActivity()).findViewById(R.id.searchView))).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Common.Log.i(s);
                queryString='%'+s+'%';
                List<CustomerProjectResVO> customerProjectResVOS = db.commonDao().getCustomerProjectList(100, 0,queryString);
                if (customerProjectResVOS != null)
                    setOnAdapter(rvList, customerProjectResVOS);
                return false;
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        callService(Common.getTeamUserIdFromSP(getActivity()));
    }

    private void callService(String userId) {
        if (Common.haveInternet(getActivity())) {
            Team contactTeam = new Team();
            contactTeam.teamId = userId;
            new RetrofitRequestController(this).sendRequest(Constants.RequestNames.CUSTOMER_PROJECT_LIST, contactTeam, false);
        } else {
            List<CustomerProjectResVO> customerProjectResVOS = db.commonDao().getCustomerProjectList(100, 0,null);
            if (customerProjectResVOS != null)
                setOnAdapter(rvList, customerProjectResVOS);
        }
    }

    private void setOnAdapter(RecyclerView rvList, List<CustomerProjectResVO> customerProjectResVOS) {
        CustomerProjectAdapter customerProjectAdapter = new CustomerProjectAdapter(getContext(), customerProjectResVOS);
        rvList.setAdapter(customerProjectAdapter);
        customerProjectAdapter.setOnItemClickListener(new CustomerProjectAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, View viewItem, int position) {
                ViewCustomerProjectActivity.open(
                        requireActivity(),
                        customerProjectResVOS.get(position)
                );
            }
        });
    }

    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {
        try {
            switch (objectRequest.requestname) {
                case Constants.RequestNames.CUSTOMER_PROJECT_LIST:
                    if (objectResponse.result != null) {
                        CustomerProjectResListVO customerProjectResListVO = Common.getSpecificDataObject(objectResponse.result, CustomerProjectResListVO.class);
                        if (customerProjectResListVO != null && customerProjectResListVO.customerProjectResVOList != null) {
                            db.commonDao().deleteCustomerProjectList();
                            db.commonDao().insertCustomerProjectList(customerProjectResListVO.customerProjectResVOList);
                            Collections.reverse(customerProjectResListVO.customerProjectResVOList);
                            setOnAdapter(rvList, customerProjectResListVO.customerProjectResVOList);
                        }
                    } else {
                        Toast.makeText(getActivity(), objectResponse.message, Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            Common.Log.i("result-->" + objectResponse.result);
            Common.dismissProgressDialog(progressDialog);
        } catch (Exception e) {
            e.printStackTrace();
            Common.disPlayExpection(e, progressDialog);
        }
    }
}
