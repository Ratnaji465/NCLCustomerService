package com.ncl.nclcustomerservice.activity;

import android.annotation.SuppressLint;
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

import com.ncl.duo_navigation_drawer.views.DuoDrawerLayout;
import com.ncl.duo_navigation_drawer.views.DuoMenuView;
import com.ncl.duo_navigation_drawer.widgets.DuoDrawerToggle;
import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity;
import com.ncl.nclcustomerservice.adapter.MenuAdapter;
import com.ncl.nclcustomerservice.application.BackgroundService;
import com.ncl.nclcustomerservice.checkinout.AlarmReceiver;
import com.ncl.nclcustomerservice.checkinout.LocationUpdatesService;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.fragments.CustomerProjectFragment;
import com.ncl.nclcustomerservice.fragments.DailyReportsFragment;
import com.ncl.nclcustomerservice.fragments.NewContactsFragment;
import com.ncl.nclcustomerservice.object.LeftNav;
import com.squareup.picasso.Picasso;

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
        profileid = Common.getProfileId(this);

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
        contacts.drawable = R.drawable.contacts;
        leftNavs.add(contacts);

        LeftNav customer_project = new LeftNav();
        customer_project.methodName =Constants.New_MethodNames.CUSTOMER_PROJECT;
        customer_project.name = Constants.New_MethodNames.CUSTOMER_PROJECT;
        customer_project.id = "2";
        customer_project.read = "2";
        customer_project.create = "2";
        customer_project.drawable = R.drawable.customerprojects;
        leftNavs.add(customer_project);

        LeftNav daily_report = new LeftNav();
        daily_report.methodName =Constants.New_MethodNames.DAILY_REPORT;
        daily_report.name = Constants.New_MethodNames.DAILY_REPORT;
        daily_report.id = "3";
        daily_report.read = "3";
        daily_report.create = "3";
        daily_report.drawable = R.drawable.dailyreports;
        leftNavs.add(daily_report);

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
        //mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));
        for (int i = 0; i < Common.NAVITEMICONS.length; i++) {
            mIcons.add(Common.NAVITEMICONS[i]);
        }
        mViewHolder = new ViewHolder();
        handleToolbar();
        handleMenu();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            handleDrawer();
        }
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

    private void goToFragment(Fragment fragment, boolean b) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
            case Constants.New_MethodNames.DAILY_REPORT:
                DailyReportsFragment dailyReportsFragment = new DailyReportsFragment();
                goToFragment(dailyReportsFragment, true);
                break;
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
