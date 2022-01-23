package com.ncl.nclcustomerservice.activity;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.ncl.duo_navigation_drawer.views.DuoDrawerLayout;
import com.ncl.duo_navigation_drawer.views.DuoMenuView;
import com.ncl.duo_navigation_drawer.widgets.DuoDrawerToggle;
import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity;
import com.ncl.nclcustomerservice.adapter.MenuAdapter;
import com.ncl.nclcustomerservice.application.BackgroundService;
import com.ncl.nclcustomerservice.checkinout.AlarmReceiver;
import com.ncl.nclcustomerservice.checkinout.JobScheduleService;
import com.ncl.nclcustomerservice.checkinout.LocationUpdatesService;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.fragments.CustomerProjectFragment;
import com.ncl.nclcustomerservice.fragments.NewContactsFragment;
import com.ncl.nclcustomerservice.fragments.NotificationFragment;
import com.ncl.nclcustomerservice.object.ContactList;
import com.ncl.nclcustomerservice.object.ContractList;
import com.ncl.nclcustomerservice.object.CustomerList;
import com.ncl.nclcustomerservice.object.LeadInsertReqVo;
import com.ncl.nclcustomerservice.object.LeftNav;
import com.ncl.nclcustomerservice.object.LoginResVo;
import com.ncl.nclcustomerservice.object.OpportunitiesList;
import com.ncl.nclcustomerservice.object.SalesCallList;
import com.ncl.nclcustomerservice.object.SalesOrderList;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends NetworkChangeListenerActivity implements DuoMenuView.OnMenuClickListener {


    MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;
    //private List<String> mTitles = new ArrayList<>();
    private List<Integer> mIcons = new ArrayList<>();
    private TextView title;
    ImageView profileImage;
    TextView profileName;
    int TAG = 0;
    private AlertDialog alertDialog;
    String ticket_master_id;
    boolean doubleBackToExitPressedOnce = false;
    TextView designationName;
    private static final String DATABASE_NAME = "sensational_database";
    int leftPanelPostion = -1;
    String pageNo,typeNotification;
    List<LeftNav> leftNavs = new ArrayList<>();
    String profileid;
    String leftnav;
    private Bundle bundle;
    //  ListReqVo listReqVo;
    int id = 0;
    private int type;
    DatabaseHandler db;
    private LinearLayout mainLinearLayout;
    private SearchView searchView;
    private ImageView searchIv;
    private List<LeftNav> leftNavsAll=new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        db = DatabaseHandler.getDatabase(this);

        if (Common.haveInternet(this)){
            Common.startService(getApplicationContext(), BackgroundService.class);
        }

        NewContactsFragment contactsFragment = new NewContactsFragment();
//        bundle.putSerializable("contacts", "");
        goToFragment(contactsFragment, true);
//        listReqVo = (ListReqVo) getIntent().getSerializableExtra("list");
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent intent = new Intent();
                String packageName = getPackageName();
                PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
                if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                }
            }

            if (Common.isUserCheckedIn(this) && !LocationUpdatesService.serviceIsRunningInForeground(this)) {
                AlarmReceiver.setAlarm(false, this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    ComponentName componentName = new ComponentName(this, JobScheduleService.class);
//                    final JobInfo jobInfo = new JobInfo.Builder(mJobId, componentName)
//                            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//                            .build();
//
//                    JobScheduler jobScheduler = (JobScheduler) getSystemService(
//                            Context.JOB_SCHEDULER_SERVICE);
//                    jobScheduler.schedule(jobInfo);
                }
                // LocationProviderChanged.saveGpsStatus(getActivity(), "");
            }

        } catch (Exception e) {

        }


     /*if (listReqVo != null) {
            if (listReqVo.value.equalsIgnoreCase("sales_call")) {
                goToMethod(new SalesFragment(), true);
            } else if (listReqVo.value.equalsIgnoreCase("opportunity_list")) {
                goToMethod(new OpportunitiesFragment(), true);
            } else if (listReqVo.value.equalsIgnoreCase("contact_list")) {
                goToMethod(new ContactsFragment(), true);
            }
        }*/

        profileid = Common.getProfileId(this);
        LoginResVo loginResVo = new Gson().fromJson(Common.getObject(this), LoginResVo.class);
//        leftNavs = loginResVo.leftNav;
       /* if (leftNavsAll.size()>4){
            leftNavs.add(leftNavsAll.get(0));
            leftNavs.add(leftNavsAll.get(1));
            leftNavs.add(leftNavsAll.get(3));
        }*/

        LeftNav routeMap = new LeftNav();
        routeMap.methodName = Constants.MethodNames.ROUTE_MAP;
        routeMap.name = "route map";
        routeMap.id = "0";
        routeMap.read = "0";
        routeMap.create = "0";
        routeMap.drawable = R.drawable.routemap;
//        leftNavs.add(routeMap);



        LeftNav contacts = new LeftNav();
        contacts.methodName =Constants.MethodNames.CONTACT_LIST;
        contacts.name = Constants.New_MethodNames.CONTACTS;
        contacts.id = "1";
        contacts.read = "1";
        contacts.create = "1";
        contacts.drawable = R.drawable.contracts;
        leftNavs.add(contacts);

        LeftNav customer_project = new LeftNav();
        customer_project.methodName =Constants.New_MethodNames.CUSTOMER_PROJECT;
        customer_project.name = Constants.New_MethodNames.CUSTOMER_PROJECT;
        customer_project.id = "2";
        customer_project.read = "2";
        customer_project.create = "2";
        customer_project.drawable = R.drawable.contracts;
        leftNavs.add(customer_project);

        LeftNav daily_report = new LeftNav();
        daily_report.methodName =Constants.New_MethodNames.DAILY_REPORT;
        daily_report.name = Constants.New_MethodNames.DAILY_REPORT;
        daily_report.id = "0";
        daily_report.read = "0";
        daily_report.create = "0";
        daily_report.drawable = R.drawable.contracts;
//        leftNavs.add(daily_report);

        LeftNav final_report = new LeftNav();
        final_report.methodName =Constants.New_MethodNames.FINAL_REPORT;
        final_report.name = Constants.New_MethodNames.FINAL_REPORT;
        final_report.id = "0";
        final_report.read = "0";
        final_report.create = "0";
        final_report.drawable = R.drawable.contracts;
//        leftNavs.add(final_report);

        LeftNav object = new LeftNav();
        object.name = "logout";
        object.methodName = "logout";
        object.id = "0";
        object.read = "0";
        object.create = "0";
        object.drawable = R.drawable.logout;
        leftNavs.add(object);

//        for (int i = 0; i < leftNavs.size(); i++) {
//           if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.SALES_ORDER_LIST)) {
//               LeftNav thirdPartyOrders = new LeftNav();
//               thirdPartyOrders.methodName = Constants.MethodNames.SALES_ODERS_THIRDPARTY;
//               thirdPartyOrders.name ="Third Party Orders";
//               thirdPartyOrders.id = "0";
//               thirdPartyOrders.read = "1";
//               thirdPartyOrders.create = "1";
//               thirdPartyOrders.delete = "0";
//               thirdPartyOrders.update = leftNavs.get(i).update;
//               leftNavs.add(i+1,thirdPartyOrders);
//           }
//            if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.CUSTOMER_LIST)) {
//                LeftNav thirdPartyOrders = new LeftNav();
//                thirdPartyOrders.methodName = Constants.MethodNames.CUSTOMER_LIST_THIRDPARTY;
//                thirdPartyOrders.name ="Third Party Customers";
//                thirdPartyOrders.id = "0";
//                thirdPartyOrders.read = "1";
//                thirdPartyOrders.create = "1";
//                thirdPartyOrders.delete = "0";
//                thirdPartyOrders.update = leftNavs.get(i).update;
//                leftNavs.add(i+1,thirdPartyOrders);
//            }
//
////           if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.DASHBOARD)){
////               leftNavs.get(i).drawable=R.drawable.dashboard;
////           }
//           if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.LEAD_LIST)){
//               leftNavs.get(i).drawable=R.drawable.leadsicon;
//           }if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.CUSTOMER_LIST)){
//               leftNavs.get(i).drawable=R.drawable.customers;
//           }if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.CUSTOMER_LIST_THIRDPARTY)){
//               leftNavs.get(i).drawable=R.drawable.customers;
//           }if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.CONTACT_LIST)){
//               leftNavs.get(i).drawable=R.drawable.contacts_icon;
//           }if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.OPPORTUNITY_LIST)){
//               leftNavs.get(i).drawable=R.drawable.opportunities_icon;
//           }if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.CONTRACT_LIST)){
//               leftNavs.get(i).drawable=R.drawable.contracts;
//           }if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.SALES_ORDER_LIST)){
//               leftNavs.get(i).drawable=R.drawable.sales_orders_icon;
//           }if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.SALES_ODERS_THIRDPARTY)){
//               leftNavs.get(i).drawable=R.drawable.sales_orders_icon;
//           }if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.SALES_CALLS_LIST)){
//               leftNavs.get(i).drawable=R.drawable.sales_calls;
//           }if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.COMPLAINT_LIST)){
//               leftNavs.get(i).drawable=R.drawable.complaints;
//           }if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.EXPENSES_LIST)){
//               leftNavs.get(i).drawable=R.drawable.expenses;
//           }if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.PAYMENT_COLLECTIONS)){
//               leftNavs.get(i).drawable=R.drawable.payment_icon;
//           }if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.QUOTATION_LIST)){
//               leftNavs.get(i).drawable=R.drawable.quotation_icon;
//           }if (leftNavs.get(i).methodName.equalsIgnoreCase(Constants.MethodNames.NOTIFICATION)){
//               leftNavs.get(i).drawable=R.drawable.notifications_icon;
//           }
//        }


        //mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));
        for (int i = 0; i < Common.NAVITEMICONS.length; i++) {
            mIcons.add(Common.NAVITEMICONS[i]);
        }


        if (getIntent()!= null && getIntent().hasExtra("id")) {
            pageNo = getIntent().getStringExtra("id");
            typeNotification = getIntent().getStringExtra("type");

           if (typeNotification.equalsIgnoreCase("Lead")){
               List<LeadInsertReqVo>  lead = db.commonDao().getLead();
               for (int i = 0; i < lead.size(); i++) {
                   String leads = String.valueOf(lead.get(i).leadsId);
//                   if (leads.equalsIgnoreCase(pageNo)) {
//                       Intent intent = new Intent(this, InsertLeadViewActivity.class);
//                       intent.putExtra("leadList", (Serializable) lead.get(i));
//                       intent.putExtra("leads", Common.getLeftNav(this, Constants.MethodNames.LEAD_LIST));
//                       startActivity(intent);
//                   }
               }
           }else if (typeNotification.equalsIgnoreCase("Lead Converted")){
               bundle = new Bundle();
               NotificationFragment notificationFragment = new NotificationFragment();
               bundle.putSerializable("notification", Common.getLeftNav(this,Constants.MethodNames.NOTIFICATION));
               goToFragment(notificationFragment, true);

           }else if (typeNotification.equalsIgnoreCase("Customer")){
              List<CustomerList> customerList = db.commonDao().getCustomerList();

               for (int i = 0; i < customerList.size(); i++) {
                   String customerLists = String.valueOf(customerList.get(i).customerId);
//                   if (customerLists.equalsIgnoreCase(pageNo)) {
//                       Intent intent = new Intent(this, CustomerViewActivity.class);
//                       intent.putExtra("customer", (Serializable) customerList.get(i));
//                       intent.putExtra("customers", Common.getLeftNav(this, Constants.MethodNames.CUSTOMER_LIST));
//                       startActivity(intent);
//                   }
               }

           }else if (typeNotification.equalsIgnoreCase("Contact")){
              List<ContactList> contactLists = db.commonDao().getContactList(100,0, "%%");

               for (int i = 0; i < contactLists.size(); i++) {
                   String contact = String.valueOf(contactLists.get(i).contactId);
                   if (contact.equalsIgnoreCase(pageNo)) {
                       Intent intent = new Intent(this, ContactViewActivity.class);
                       intent.putExtra("contactlist", (Serializable) contactLists.get(i));
                       intent.putExtra("leftnav", Common.getLeftNav(this, Constants.MethodNames.CONTACT_LIST));
                       startActivity(intent);
                   }
               }
           }else if (typeNotification.equalsIgnoreCase("Opportunitie")){
              List<OpportunitiesList> opportunitiesLists = db.commonDao().getOpportunitiesList(20,0);

               for (int i = 0; i < opportunitiesLists.size(); i++) {
                   String oppo = String.valueOf(opportunitiesLists.get(i).opportunityId);
//                   if (oppo.equalsIgnoreCase(pageNo)) {
//                       Intent intent = new Intent(this, OpportunityViewActivity.class);
//                       intent.putExtra("opportunity", (Serializable) opportunitiesLists.get(i));
//                       intent.putExtra("view","notification");
//                       intent.putExtra("leftnav", Common.getLeftNav(this, Constants.MethodNames.OPPORTUNITY_LIST));
//                       startActivity(intent);
//                   }
               }

           }else if (typeNotification.equalsIgnoreCase("Quotation")){
               bundle = new Bundle();
               NotificationFragment notificationFragment = new NotificationFragment();
               bundle.putSerializable("notification", Common.getLeftNav(this,Constants.MethodNames.NOTIFICATION));
               goToFragment(notificationFragment, true);
//              List<OpportunitiesList> opportunitiesLists = db.commonDao().getOpportunitiesList();
//
//               for (int i =0 ; i<opportunitiesLists.size();i++){
//                   String opportunity = String.valueOf(opportunitiesLists.get(i).opportunityId);
//
//                   if (opportunity.equalsIgnoreCase(opportunityId)){
//                      List<QuotationList> quotationLists = db.commonDao().getQutation();
//
//                       for (int k = 0; k<quotationLists.size();k++){
//                           String quotation = String.valueOf(quotationLists.get(k).quotationId);
//
//                           if (quotation.equalsIgnoreCase(pageNo)){
////                                List<QuotationProductList> quotationProductLists = db.commonDao().getQuotationProductLineItem(quotation);
//                               Intent intent = new Intent(this, QuotationViewActivity.class);
//                               intent.putExtra("quotationList",quotationLists.get(k));
////                                intent.putExtra("quotationLineItem", (Serializable) quotationProductLists.get(0));
//                               intent.putExtra("view","notifyview");
////                                intent.putExtra("","");
//                               startActivity(intent);
//                           }
//                       }
//                   }
//               }
           }else if (typeNotification.equalsIgnoreCase("Contract")){
               List<ContractList> contractLists = db.commonDao().getContractList(25,0);

               for (int i = 0; i < contractLists.size(); i++) {
                   String contract = String.valueOf(contractLists.get(i).contractId);

//                   if (contract.equalsIgnoreCase(pageNo)) {
////                        List<ContractLineItem> contractLineItems = db.commonDao().getContractLineItems(contract);
//                       Intent intent = new Intent(this, ContractViewActivity.class);
//                       intent.putExtra("contractList", (Serializable) contractLists.get(i));
////                        intent.putExtra("contractLineItem", (Serializable) contractLineItems);
//                       intent.putExtra("view","contract");
//                       intent.putExtra("leftnav", Common.getLeftNav(this, Constants.MethodNames.CONTRACT_LIST));
//                       startActivity(intent);
//                   }
               }

           }else if (typeNotification.equalsIgnoreCase("SalesOrder")){
               List<SalesOrderList> salesOrderLists = db.commonDao().getSalesOrderList(25,0);

               for (int i = 0; i < salesOrderLists.size(); i++) {
                   String salesorder = String.valueOf(salesOrderLists.get(i).salesOrderId);


               }

           }else if (typeNotification.equalsIgnoreCase("SalesCalls")){
              List<SalesCallList> salesCallLists = db.commonDao().getSalesCallList(25,0);

               for (int i = 0; i < salesCallLists.size(); i++) {
                   String salescall = String.valueOf(salesCallLists.get(i).salesCallId);

               }

           }


//            bundle = new Bundle();
//            NotificationFragment notificationFragment = new NotificationFragment();
//            bundle.putSerializable("notification", Common.getLeftNav(this,Constants.MethodNames.NOTIFICATION));
//            goToFragment(notificationFragment, true);

//            goToFragment(new NotificationFragment(), false);
//            Intent intent = new Intent(MainActivity.this, QuotationListActivity.class);
//            startActivity(intent);
        }


        mViewHolder = new ViewHolder();
        handleToolbar();
        handleMenu();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            handleDrawer();
        }


//        if (pageNo == 2) {
//            goToFragment(new NotificationFragment(), true);
//        }
//        if (pageNo == 5) {
//            goToFragment(new OrderIndentFragment(), true);
//        } else if (pageNo == 6) {
//            goToFragment(new ComplaintsFragment(), true);
//        } else if (pageNo == 8) {
//            leftPanelPostion = 7;
//            if (isRM())
//                goToFragment(new DistributorsFragment(), true);
//        }
//        Intent serviceIntent = new Intent(MainActivity.this, MyIntentService.class);
//        serviceIntent.putExtra("sensational", "get_masters_db");
//        startService(serviceIntent);

//        goToFragment(new ProductCatalogueFragment(), true);
    }

    private void setNotificationData(String pageNo) {

    }

    @Override
    protected void onInternetConnected() {

    }

    @Override
    protected void onInternetDisconnected() {

    }

    private void logoutpopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.logout_layout, null);
        dialogView.findViewById(R.id.btn_yes);
        dialogView.findViewById(R.id.btn_no);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
        dialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                db.commonDao().deleteComplaintList();
                db.commonDao().deleteContactList();
                db.commonDao().deleteContactList();
                db.commonDao().deleteCustomerList();
                db.commonDao().deleteEmpActivityPojo();
                db.commonDao().deleteCustomer();
                db.commonDao().deleteLeadList();
                db.commonDao().deleteOpportunities();
                db.commonDao().deleteSalesCallList();
                db.commonDao().deleteSalesOrderList();
                db.commonDao().deleteTadaList();
                db.commonDao().deleteGeoTrackingdata();
                Common.getDefaultSP(MainActivity.this).edit().clear().commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
//                loginDb = databaseHandler.loginDao().getAll();
//                async = new deleteAllWordsAsyncTask(loginDb);
//                async.execute((Void) null);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                alertDialog.dismiss();
                finishAffinity();

            }
        });

        dialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.logout_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }


    @Override
    public void onFooterClicked() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onHeaderClicked() {
//        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//        startActivity(intent);
//        settexts();
//        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }

    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
        }
    }

    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        View view = getSupportActionBar().getCustomView();
        title = view.findViewById(R.id.title_text);
        mainLinearLayout = view.findViewById(R.id.main_layout1);
        searchView = view.findViewById(R.id.searchView);
        searchIv=view.findViewById(R.id.searchIv);

//        title.setText("Dashboard");



//        filter = view.findViewById(R.id.dashboard_filter);
//       refresh = view.findViewById(R.id.tasks_refresh);
        view.findViewById(R.id.menu_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewHolder.mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
                mViewHolder.mDuoDrawerLayout.openDrawer();
                //settexts();
            }
        });
        searchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainLinearLayout.setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);
                searchView.requestFocus();
                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });


    }

    @SuppressLint("RestrictedApi")
    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(leftNavs, null, mIcons, MainActivity.this);
        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
        View view = mViewHolder.mDuoMenuView.getHeaderView();
        profileImage = view.findViewById(R.id.profile_image);
        profileName = view.findViewById(R.id.profile_name);
        designationName = view.findViewById(R.id.designation_name);
        profileImage.setImageResource(R.drawable.profile);
        //designationName.setText(Common.getLoginTypeFromSP(this));
        settexts();
        mMenuAdapter.notifyDataSetChanged();

    }

    public void settexts() {
        profileName.setText(Common.getUserNameFromSP(this));
        designationName.setText(Common.getRolenameFromSP(this));
//        if (Common.getUserRoleFromSP(this) == Constants.Roles.CUSTOMER_ROLE)
//            profileName.setText(Common.getFirstNameFromSP(this));
//        else profileName.setText(Common.getUserNameFromSP(this));

        if (Common.getImageFromSP(this) != null /*&& Common.getImageFromSP(this).length() > 5*/) {
            Picasso.with(this).load(Common.getImageFromSP(this)).into(profileImage);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        mViewHolder.mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
//        duoDrawerToggle.setDrawerIndicatorEnabled(true);
        duoDrawerToggle.syncState();
    }

   /* private void goToMethod(Fragment fragment, boolean b) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
//        bundle.putInt("leftPanelPostion", leftPanelPostion);
//        bundle.putString("list", String.valueOf(listReqVo));
        bundle.putSerializable("list", listReqVo);
//        bundle.putInt("type",type);
        fragment.setArguments(bundle);
        if (b) {
            transaction.replace(R.id.content_frame, fragment);
//            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
        } else {
            transaction.replace(R.id.content_frame, fragment);
//            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        }
    }*/


    private void goToFragment(Fragment fragment, boolean b) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        Bundle bundle = new Bundle();
//        bundle.putString("notiffication_type_id",notiffication_type_id);
//        bundle.putSerializable(AppUtils.DASHBOARD, getIntent().getSerializableExtra(AppUtils.DASHBOARD));
        fragment.setArguments(bundle);
        if (b) {
            transaction.replace(R.id.content_frame, fragment);
//            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
        } else {
            transaction.replace(R.id.content_frame, fragment);
//            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        }
//        if (fragment instanceof DashboardFragment && searchIv!=null){
//            searchIv.setVisibility(View.GONE);
//            searchView.setVisibility(View.GONE);
//            mainLinearLayout.setVisibility(View.VISIBLE);
//        }else if (searchIv!=null){
//            searchIv.setVisibility(View.VISIBLE);
//            searchView.setVisibility(View.GONE);
//            mainLinearLayout.setVisibility(View.VISIBLE);
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        mViewHolder.mToolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        mMenuAdapter.setViewSelected(position, true);
        settexts();
        bundle = new Bundle();
        String methodName = "";

        methodName = leftNavs.get(position).methodName;
        switch (methodName) {

//            case Constants.MethodNames.DASHBOARD:
//                DashboardFragment dashboardFragment = new DashboardFragment();
//                goToFragment(dashboardFragment, false);
//                break;
//            case Constants.MethodNames.LEAD_LIST:
//                LeadsFragment leadsFragment = new LeadsFragment();
//                bundle.putSerializable("leads", leftNavs.get(position));
//                goToFragment(leadsFragment, true);
//                break;
            case Constants.New_MethodNames.LOGOUT:
                logoutpopup();
                break;
            case  Constants.New_MethodNames.CUSTOMER_PROJECT:
                CustomerProjectFragment customerProjectFragment=new CustomerProjectFragment();
                goToFragment(customerProjectFragment, true);
                break;
            case Constants.MethodNames.CONTACT_LIST:
                NewContactsFragment contactsFragment = new NewContactsFragment();
                bundle.putSerializable("contacts", leftNavs.get(position));
                goToFragment(contactsFragment, true);
                break;
//            case Constants.MethodNames.CUSTOMER_LIST:
//                CustomersFragment customersFragment = new CustomersFragment();
//                bundle.putSerializable("customers", leftNavs.get(position));
//                bundle.putString("from", Constants.MethodNames.CUSTOMER_LIST);
//                goToFragment(customersFragment, true);
//                break;
//                case Constants.MethodNames.CUSTOMER_LIST_THIRDPARTY:
//                CustomersFragment customersFragmentThrdP = new CustomersFragment();
//                bundle.putSerializable("customers", leftNavs.get(position));
//                bundle.putString("from", Constants.MethodNames.CUSTOMER_LIST_THIRDPARTY);
//                goToFragment(customersFragmentThrdP, true);
//                break;
//            case Constants.MethodNames.OPPORTUNITY_LIST:
//                OpportunitiesFragment opportunitiesFragment = new OpportunitiesFragment();
//                bundle.putSerializable("opportunities", leftNavs.get(position));
//                goToFragment(opportunitiesFragment, true);
//                break;
//            case Constants.MethodNames.CONTRACT_LIST:
//                ContractsFragment contractsFragment = new ContractsFragment();
//                bundle.putSerializable("contracts", leftNavs.get(position));
//                goToFragment(contractsFragment, true);
//                break;
//            case Constants.MethodNames.SALES_ORDER_LIST:
//                SalesOrderFragment salesOrderFragment = new SalesOrderFragment();
//                bundle.putSerializable("salesorder", leftNavs.get(position));
//                bundle.putString("from", Constants.MethodNames.SALES_ORDER_LIST);
//                goToFragment(salesOrderFragment, true);
//                break;
//                case Constants.MethodNames.SALES_ODERS_THIRDPARTY:
//                SalesOrderFragment salesOrderThirdPartyFragment = new SalesOrderFragment();
//                bundle.putSerializable("salesorder", leftNavs.get(position));
//                bundle.putString("from", Constants.MethodNames.SALES_ODERS_THIRDPARTY);
//                goToFragment(salesOrderThirdPartyFragment, true);
//                break;
//            case Constants.MethodNames.SALES_CALLS_LIST:
//                SalesFragment salesFragment = new SalesFragment();
//                bundle.putSerializable("sales", leftNavs.get(position));
//                goToFragment(salesFragment, true);
//                break;
//            case Constants.MethodNames.COMPLAINT_LIST:
//                ComplaintsFragment complaintsFragment = new ComplaintsFragment();
//                bundle.putSerializable("complaint", leftNavs.get(position));
//                goToFragment(complaintsFragment, true);
//                break;
//            case Constants.MethodNames.EXPENSES_LIST:
//                TAandDAFragment tAandDAFragment = new TAandDAFragment();
//                bundle.putSerializable("tada", leftNavs.get(position));
//                goToFragment(tAandDAFragment, true);
//                break;
//            case Constants.MethodNames.PAYMENT_COLLECTIONS:
//                PaymentFragment paymentFragment = new PaymentFragment();
//                bundle.putSerializable("payment", leftNavs.get(position));
//                goToFragment(paymentFragment, true);
//                break;
//            case Constants.MethodNames.NOTIFICATION:
//                NotificationFragment notificationFragment = new NotificationFragment();
//                bundle.putSerializable("notification", leftNavs.get(position));
//                goToFragment(notificationFragment, true);
//                break;
//                case Constants.MethodNames.QUOTATION_LIST:
              /*  NotificationFragment quotationlistfragment = new QuotationListActivity();
                bundle.putSerializable("notification", leftNavs.get(position));
                goToFragment(quotationlistfragment, true);*/
//                break;
//            case Constants.MethodNames.ROUTE_MAP:
//                Intent map = new Intent(this, PostRouteMapActivity.class);
//                startActivity(map);
////                goToFragment(new RoutMapFragment(),true);
//                break;
//            default:
//                if (Common.getDefaultSP(this).getString("checkin", "").equalsIgnoreCase("true")) {
//                    checkoutPopup();
//                }
//                else {
//                    logoutpopup();
//                }
//                break;
        }
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }

    private void checkoutPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.checkout_popup, null);
        dialogView.findViewById(R.id.btn_yes);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
        dialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.logout_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (searchView!=null && searchView.getVisibility()==View.VISIBLE){
            searchView.setVisibility(View.GONE);
            mainLinearLayout.setVisibility(View.VISIBLE);
            return;
        }

        if (!doubleBackToExitPressedOnce) {
//            goToFragment(new NewContactsFragment(), false);
            Toast.makeText(this, "Click again to exit", Toast.LENGTH_SHORT).show();
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        }
        this.doubleBackToExitPressedOnce = true;
    }


}
