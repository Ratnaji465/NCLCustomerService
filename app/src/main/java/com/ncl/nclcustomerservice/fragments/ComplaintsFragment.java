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
import com.ncl.nclcustomerservice.activity.ComplaintsViewActivity;
import com.ncl.nclcustomerservice.activity.CreateComplaintsActivity;
import com.ncl.nclcustomerservice.activity.MainActivity;
import com.ncl.nclcustomerservice.adapter.ComplaintsAdapter;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.network.RetrofitRequestController;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.ComplaintsInsertReqVo;
import com.ncl.nclcustomerservice.object.MastersResVo;
import com.ncl.nclcustomerservice.object.Team;

import java.io.Serializable;
import java.util.List;

public class ComplaintsFragment extends BaseFragment implements RetrofitResponseListener {
    private ImageView filterView,searchIv;
    RecyclerView rvList;
    private ImageView addView;
    private String queryString = "%%";
    DatabaseHandler db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_customer_project, container, false);
        ((TextView) ((MainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.title_text)).setText("COMPLAINT REGISTER");
        filterView = getActivity().findViewById(R.id.filter_task);
        searchIv= getActivity().findViewById(R.id.searchIv);
        searchIv.setVisibility(View.VISIBLE);
        if (Common.getUserTeam(getActivity()).size() > 1)
            filterView.setVisibility(View.VISIBLE);
        else
            filterView.setVisibility(View.GONE);
        filterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.getSelectedUser(ComplaintsFragment.this);
            }
        });
        rvList = view.findViewById(R.id.rvList);
        db = DatabaseHandler.getDatabase(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(linearLayoutManager);
        addView = getActivity().findViewById(R.id.add_task);
        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(getActivity(), CreateComplaintsActivity.class);
                addIntent.putExtra("form_key", "new");
                startActivity(addIntent);
            }
        });
        ((SearchView)(getActivity().findViewById(R.id.searchView))).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Common.Log.i(s);
                queryString='%'+s+'%';
                List<ComplaintsInsertReqVo> complaintsInsertReqVos = db.commonDao().getComplaints(100, 0, queryString);
                if (complaintsInsertReqVos != null)
                    setOnAdapter(rvList, complaintsInsertReqVos);
                return false;
            }
        });
        return view;
    }
    private void setOnAdapter(RecyclerView rvList, List<ComplaintsInsertReqVo>  complaintsInsertReqVos) {
        ComplaintsAdapter complaintsAdapter = new ComplaintsAdapter(getContext(), complaintsInsertReqVos);
        rvList.setAdapter(complaintsAdapter);
        complaintsAdapter.setOnItemClickListener(new ComplaintsAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, View viewItem, int position) {
                Intent intent = new Intent(getActivity(), ComplaintsViewActivity.class);
                intent.putExtra("complaint_register_id", complaintsInsertReqVos.get(position).csComplaintRegisterId);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        callService(""+Common.getUserIdFromSP(getActivity()));
    }
    private void callService(String userId) {
        if (Common.haveInternet(getActivity())) {
            Team contactTeam = new Team();
            contactTeam.teamId = userId;
            contactTeam.roleId =String.valueOf(Common.getRoleIdFromSP(getActivity()));
            contactTeam.profileId=Common.getProfileId(getActivity());
            new RetrofitRequestController(this).sendRequest(Constants.RequestNames.COMPLAINT_REGISTER_LIST, contactTeam, false);
        } else {
            List<ComplaintsInsertReqVo> complaintsInsertReqVos = db.commonDao().getComplaints( 100,0, queryString);
            if (complaintsInsertReqVos != null)
                setOnAdapter(rvList, complaintsInsertReqVos);
        }
    }
    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {
        try {
            switch (objectRequest.requestname) {
                case Constants.RequestNames.COMPLAINT_REGISTER_LIST:
                    if (objectResponse.result != null) {
                        MastersResVo mastersResVo = Common.getSpecificDataObject(objectResponse.result, MastersResVo.class);
                        if(mastersResVo.complaintList!=null && mastersResVo.complaintList.size()>0) {
                                db.commonDao().deleteComplaintList();
                                db.commonDao().insertComplaints(mastersResVo.complaintList);
                                setOnAdapter(rvList, mastersResVo.complaintList);
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
