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
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.network.RetrofitRequestController;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.CustomerContactResponseVo;
import com.ncl.nclcustomerservice.object.NewCustomerResVo;
import com.ncl.nclcustomerservice.object.Team;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabbedContractorListFragment extends BaseFragment implements RetrofitResponseListener , SwipeRefreshLayout.OnRefreshListener {
    DatabaseHandler db;
    private NewContactAdapter contactAdapter;
    private String queryString = "%%";
    @BindView(R.id.contact_recycler)
    RecyclerView contact_recycler;

    private void callService(String userId) {
        if (Common.haveInternet(getActivity())) {
            Team contactTeam = new Team();
            contactTeam.teamId = userId;
            new RetrofitRequestController(this).sendRequest(Constants.RequestNames.CONTACT_LIST, contactTeam, false);
        } else {
            List<CustomerContactResponseVo.ContactContractorList> contact = db.commonDao().getContractorContactList(100,0, queryString);
            if (contact != null)
                setOnAdapter(contact_recycler, contact);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tabbed_contractor_list, container, false);
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

    private void setOnAdapter(RecyclerView contact_recycler, List<CustomerContactResponseVo.ContactContractorList> contactLists) {
        contactAdapter = new NewContactAdapter(getContext(), contactLists);
        contact_recycler.setAdapter(contactAdapter);
        contactAdapter.setOnItemClickListener(new NewContactAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, View viewItem, int position) {
                Intent intent = new Intent(getActivity(), NewContactViewActivity.class);
                intent.putExtra("contactContractorList", (Serializable) contactLists.get(position));
                intent.putExtra("type","Contractor" );
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {
        try {
            switch (objectRequest.requestname) {
                case Constants.RequestNames.CONTACT_LIST:
                    if (objectResponse.result != null) {
//                    swipeRefreshLayout.setRefreshing(false);
//                        isRefreshing = false;
                        NewCustomerResVo newCustomerResVo=Common.getSpecificDataObject(objectResponse.result, NewCustomerResVo.class);
                        if(newCustomerResVo!=null && newCustomerResVo.contactList!=null){
                            if(newCustomerResVo.contactList.contactContractorLists!=null && newCustomerResVo.contactList.contactContractorLists.size()>0){
                                List<CustomerContactResponseVo.ContactContractorList> contactContractorLists=newCustomerResVo.contactList.contactContractorLists;
                            contact_recycler.setVisibility(View.VISIBLE);
                                db.commonDao().deleteContactContractorList();
                                db.commonDao().insertContractorContact(contactContractorLists);
                            setOnAdapter(contact_recycler, contactContractorLists);
                            }

                        }
                    }else {
                    contact_recycler.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), objectResponse.message, Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            Common.Log.i("result-->" + objectResponse.result);
            Common.dismissProgressDialog(progressDialog);

        }catch (Exception e){
            Common.disPlayExpection(e, progressDialog);
        }
    }

    @Override
    public void onRefresh() {

    }
}
