package com.ncl.nclcustomerservice.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.activity.ContactViewActivity;
import com.ncl.nclcustomerservice.activity.CreateNewContactActivity;
import com.ncl.nclcustomerservice.activity.MainActivity;
import com.ncl.nclcustomerservice.activity.NewContactViewActivity;
import com.ncl.nclcustomerservice.adapter.ContactAdapter;
import com.ncl.nclcustomerservice.adapter.NewContactAdapter;
import com.ncl.nclcustomerservice.application.BackgroundService;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.commonutils.EndlessRecyclerOnScrollListener;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.network.RetrofitRequestController;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.ContactList;
import com.ncl.nclcustomerservice.object.CustomerContactResponseVo;
import com.ncl.nclcustomerservice.object.NewCustomerResVo;
import com.ncl.nclcustomerservice.object.Team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
    private ViewPager viewPager;
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
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        db = DatabaseHandler.getDatabase(getActivity());
        if (Common.haveInternet(getActivity())){
            Common.startService(getActivity(), BackgroundService.class);
        }
        filterView = ((MainActivity) getActivity()).findViewById(R.id.filter_task);
//        if (Common.getUserTeam(getActivity()).size() > 1)
//            filterView.setVisibility(View.VISIBLE);
//        else
            filterView.setVisibility(View.GONE);
        filterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.getSelectedUser(NewContactsFragment.this);
            }
        });
        addView = ((MainActivity) getActivity()).findViewById(R.id.add_task);
        ((TextView) ((MainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.title_text)).setText("CONTACTS");
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        contact_recycler.setLayoutManager(linearLayoutManager);
        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(getActivity(), CreateNewContactActivity.class);
                addIntent.putExtra("form_key", "new");
                startActivity(addIntent);
            }
        });
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setRefreshing(false);
        List<CustomerContactResponseVo.ContactContractorList> contact = db.commonDao().getContractorContactList(100,0, queryString);
//
//        if (contact != null)
//            setOnAdapter(contact_recycler, contact);
//
//        mScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int current_page) {
//
//                Log.d("onLoadMore","OnLoadMore.." + current_page);
//
//                int offset = 0;
//                if (contact != null && contact.size() > 0){
//                    offset = (contact.get(contact.size() - 1)).contactId;
//                }
//                List<CustomerContactResponseVo.ContactContractorList> list = db.commonDao().getContractorContactList(100,offset, queryString);
//                if (list != null && list.size() > 0){
//                    contact.addAll(list);
//                    contact_recycler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.d("Pa:", "" + contact.size());
//                            contactAdapter.notifyItemInserted(contact.size() - 1);
//                        }
//                    });
//                }
//                mScrollListener.setLoading(false);
//            }
//        };
//        contact_recycler.addOnScrollListener(mScrollListener);

        ((SearchView)(((MainActivity) getActivity()).findViewById(R.id.searchView))).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Common.Log.i(s);
                queryString = '%' + s + '%';
                List<CustomerContactResponseVo.ContactContractorList> contact = db.commonDao().getContractorContactList(100, 0, queryString);
//                if (contact != null)
//                    setOnAdapter(contact_recycler, contact);
                    return false;
            }
        });

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        if(getActivity()!=null){
            ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
            adapter.addFragment(new TabbedContractorListFragment(), "Contractor");
            adapter.addFragment(new TabbedProjectHeadListFragment(), "Project Head");
            viewPager.setAdapter(adapter);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
//        List<CustomerContactResponseVo.ContactContractorList> contact = db.commonDao().getContractorContactList(100,0, queryString);
//        if (contact != null)
//            setOnAdapter(contact_recycler, contact);
    }

    private void setOnAdapter(RecyclerView contact_recycler, List<CustomerContactResponseVo.ContactContractorList> contactLists) {
//        contactAdapter = new NewContactAdapter(getContext(), contactLists);
//        contact_recycler.setAdapter(contactAdapter);
//        contactAdapter.setOnItemClickListener(new NewContactAdapter.OnItemClickListener() {
//            @Override
//            public void OnItemClick(View view, View viewItem, int position) {
//                Intent intent = new Intent(getActivity(), NewContactViewActivity.class);
//                intent.putExtra("contactContractorList", (Serializable) contactLists.get(position));
//                intent.putExtra("type","Contractor" );
//                startActivity(intent);
//            }
//        });
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

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
