package com.ncl.nclcustomerservice.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.activity.NewContactViewActivity;
import com.ncl.nclcustomerservice.adapter.NewContactAdapter;
import com.ncl.nclcustomerservice.adapter.ProjectHeadAdapter;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.network.RetrofitRequestController;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.CustomerContactResponseVo;
import com.ncl.nclcustomerservice.object.NewCustomerResVo;
import com.ncl.nclcustomerservice.object.ProjectHeadContactListResVo;
import com.ncl.nclcustomerservice.object.ProjectHeadReqVo;
import com.ncl.nclcustomerservice.object.Team;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabbedProjectHeadListFragment extends BaseFragment implements RetrofitResponseListener, SwipeRefreshLayout.OnRefreshListener {
    DatabaseHandler db;
    private String queryString = "%%";
    @BindView(R.id.contact_recycler)
    RecyclerView contact_recycler;
    private ProjectHeadAdapter projectHeadAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void callService(String userId) {
        if (Common.haveInternet(getActivity())) {
            Team contactTeam = new Team();
            contactTeam.teamId = userId;
            new RetrofitRequestController(this).sendRequest(Constants.RequestNames.CONTACT_LIST, contactTeam, false);
        } else {
            List<ProjectHeadReqVo> projectHeadReqVoList = db.commonDao().getProjectHeadContactList(100, 0, queryString);
            if (projectHeadReqVoList != null)
                setOnAdapter(contact_recycler, projectHeadReqVoList);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tabbed_projecthead_list, container, false);
        ButterKnife.bind(this, view);
        db = DatabaseHandler.getDatabase(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        contact_recycler.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        callService(Common.getTeamUserIdFromSP(getActivity()));
    }

    private void setOnAdapter(RecyclerView contact_recycler, List<ProjectHeadReqVo> projectHeadReqVoList) {
        projectHeadAdapter = new ProjectHeadAdapter(getContext(), projectHeadReqVoList);
        contact_recycler.setAdapter(projectHeadAdapter);
        projectHeadAdapter.setOnItemClickListener(new ProjectHeadAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, View viewItem, int position) {
                Intent intent = new Intent(getActivity(), NewContactViewActivity.class);
                intent.putExtra("contactProjectHeadList", (Serializable) projectHeadReqVoList.get(position));
                intent.putExtra("type", "ProjectHead");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {
        try {
            switch (objectRequest.requestname) {
                case Constants.RequestNames.CONTACT_LIST:
                    if (objectResponse.result != null) {
                        ProjectHeadContactListResVo projectHeadContactListResVo = Common.getSpecificDataObject(objectResponse.result, ProjectHeadContactListResVo.class);
                        if (projectHeadContactListResVo != null && projectHeadContactListResVo.projectHeadListResVo != null) {
                            if(projectHeadContactListResVo.projectHeadListResVo.projectHeadReqVoList!=null && projectHeadContactListResVo.projectHeadListResVo.projectHeadReqVoList.size() > 0)
                            db.commonDao().deleteProjectHeadContactList();
                            db.commonDao().insertProjectHeadContact(projectHeadContactListResVo.projectHeadListResVo.projectHeadReqVoList);
                            setOnAdapter(contact_recycler, projectHeadContactListResVo.projectHeadListResVo.projectHeadReqVoList);
                        }
                    } else {
                        contact_recycler.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), objectResponse.message, Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            Common.Log.i("result-->" + objectResponse.result);
            Common.dismissProgressDialog(progressDialog);

        } catch (Exception e) {
            Common.disPlayExpection(e, progressDialog);
        }
    }
}
