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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.tabs.TabLayout;
import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.activity.CreateNewContactActivity;
import com.ncl.nclcustomerservice.activity.MainActivity;
import com.ncl.nclcustomerservice.application.BackgroundService;
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

import java.util.List;

import butterknife.ButterKnife;

public class NewContactsFragment extends BaseFragment implements RetrofitResponseListener, SwipeRefreshLayout.OnRefreshListener {
    DatabaseHandler db;
    private ImageView filterView;
    private ImageView addView;
//    @BindView(R.id.swipe_layout)
//    SwipeRefreshLayout swipeRefreshLayout;
    private String queryString="%%";
//    private NewContactAdapter contactAdapter;
//    private EndlessRecyclerOnScrollListener mScrollListener = null;
    private boolean isRefreshing;
    private TabLayout tabLayout;
    @Override
    public void onRefresh() {
        callService(Common.getTeamUserIdFromSP(getActivity()));
//        swipeRefreshLayout.setRefreshing(true);
//        isRefreshing = true;
    }

    private void callService(String userId) {
        if (Common.haveInternet(getActivity())) {
            Team contactTeam = new Team();
            contactTeam.teamId = userId;
            new RetrofitRequestController(this).sendRequest(Constants.RequestNames.CONTACT_LIST, contactTeam, true);
        }
//        else {
//            List<CustomerContactResponseVo.ContactContractorList> contact = db.commonDao().getContractorContactList(100,0, queryString);
//            if (contact != null)
//                setOnAdapter(contact_recycler, contact);
//        }
    }

    @Override
    public void setUserId(String userId) {
        super.setUserId(userId);
//        callService(userId);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(this, view);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Contractor");
        tabLayout.addTab(firstTab);
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Project Head");
        tabLayout.addTab(secondTab);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new TabbedContractorListFragment())
                .commit();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView, new TabbedContractorListFragment())
                            .commit();
                }else if(tab.getPosition()==1){
                    getFragmentManager().beginTransaction()
//                            .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left,
//                                    R.anim.slide_in_from_left, R.anim.slide_out_to_right)
                            .replace(R.id.fragmentContainerView, new TabbedProjectHeadListFragment())
                            .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        db = DatabaseHandler.getDatabase(getActivity());
        if (Common.haveInternet(getActivity())){
            Common.startService(getActivity(), BackgroundService.class);
        }
        filterView = ((MainActivity) getActivity()).findViewById(R.id.filter_task);
//
            filterView.setVisibility(View.GONE);
        filterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.getSelectedUser(NewContactsFragment.this);
            }
        });
        addView = ((MainActivity) getActivity()).findViewById(R.id.add_task);
        ((TextView) ((MainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.title_text)).setText("CONTACTS");
        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(getActivity(), CreateNewContactActivity.class);
                addIntent.putExtra("form_key", "new");
                startActivity(addIntent);
            }
        });
        List<CustomerContactResponseVo.ContactContractorList> contact = db.commonDao().getContractorContactList(100,0);

        ((SearchView)(((MainActivity) getActivity()).findViewById(R.id.searchView))).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Common.Log.i(s);
                queryString = '%' + s + '%';
                List<CustomerContactResponseVo.ContactContractorList> contact = db.commonDao().getContractorContactList(100, 0);
//                if (contact != null)
//                    setOnAdapter(contact_recycler, contact);
                    return false;
            }
        });

        return view;
    }


    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {
    try {
        switch (objectRequest.requestname) {
            case Constants.RequestNames.CONTACT_LIST:
                if (objectResponse.result != null) {
//                    swipeRefreshLayout.setRefreshing(false);
                    isRefreshing = false;
                    NewCustomerResVo newCustomerResVo=Common.getSpecificDataObject(objectResponse.result, NewCustomerResVo.class);
                    if(newCustomerResVo!=null && newCustomerResVo.contactList!=null){
                        if(newCustomerResVo.contactList.contactContractorLists!=null && newCustomerResVo.contactList.contactContractorLists.size()>0){
                            List<CustomerContactResponseVo.ContactContractorList> contactContractorLists=newCustomerResVo.contactList.contactContractorLists;
//                            contact_recycler.setVisibility(View.VISIBLE);
                            db.commonDao().deleteContactContractorList();
                            db.commonDao().insertContractorContact(contactContractorLists);
//                            setOnAdapter(contact_recycler, contactContractorLists);
                        }

                    }
                }else {
//                    contact_recycler.setVisibility(View.GONE);
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

}
