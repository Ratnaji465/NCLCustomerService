package com.ncl.nclcustomerservice.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import butterknife.ButterKnife
import com.ncl.duo_navigation_drawer.views.DuoDrawerLayout
import com.ncl.duo_navigation_drawer.views.DuoMenuView
import com.ncl.duo_navigation_drawer.views.DuoMenuView.OnMenuClickListener
import com.ncl.duo_navigation_drawer.widgets.DuoDrawerToggle
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.LeftNav
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity
import com.ncl.nclcustomerservice.adapter.MenuAdapter
import com.ncl.nclcustomerservice.application.BackgroundService
import com.ncl.nclcustomerservice.checkinout.AlarmReceiver
import com.ncl.nclcustomerservice.checkinout.LocationUpdatesService
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.commonutils.Constants
import com.ncl.nclcustomerservice.database.DatabaseHandler
import com.ncl.nclcustomerservice.fragments.CustomerProjectFragment
import com.ncl.nclcustomerservice.fragments.DailyReportsFragment
import com.ncl.nclcustomerservice.fragments.NewContactsFragment
import com.squareup.picasso.Picasso

class MainActivity : NetworkChangeListenerActivity(), OnMenuClickListener {
    var mMenuAdapter: MenuAdapter? = null
    private var mViewHolder: ViewHolder? = null

    //private List<String> mTitles = new ArrayList<>();
    private val mIcons: MutableList<Int> = ArrayList()
    private var title: TextView? = null
    var profileImage: ImageView? = null
    var profileName: TextView? = null
    var TAG = 0
    private var alertDialog: AlertDialog? = null
    var ticket_master_id: String? = null
    var doubleBackToExitPressedOnce = false
    var designationName: TextView? = null
    var leftPanelPostion = -1
    var pageNo: String? = null
    var typeNotification: String? = null
    var leftNavs: MutableList<LeftNav> = ArrayList<LeftNav>()
    var profileid: String? = null
    var leftnav: String? = null
    private var bundle: Bundle? = null

    //  ListReqVo listReqVo;
    var id = 0
    private val type = 0
    var db: DatabaseHandler? = null
    private var mainLinearLayout: LinearLayout? = null
    private var searchView: SearchView? = null
    private var searchIv: ImageView? = null
    private val leftNavsAll: List<LeftNav> = ArrayList<LeftNav>()

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        db = DatabaseHandler.getDatabase(this)
        if (Common.haveInternet(this)) {
            Common.startService(applicationContext, BackgroundService::class.java)
        }
        val contactsFragment = NewContactsFragment()
        //        bundle.putSerializable("contacts", "");
        goToFragment(contactsFragment, true)
        //        listReqVo = (ListReqVo) getIntent().getSerializableExtra("list");
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val intent = Intent()
                val packageName = packageName
                val pm: PowerManager = getSystemService(POWER_SERVICE) as PowerManager
                if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                    intent.setData(Uri.parse("package:$packageName"))
                    startActivity(intent)
                }
            }
            if (Common.isUserCheckedIn(this) && !LocationUpdatesService.serviceIsRunningInForeground(
                    this
                )
            ) {
                AlarmReceiver.setAlarm(false, this)
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
        } catch (e: Exception) {
        }
        profileid = Common.getProfileId(this)
        val routeMap = LeftNav()
        routeMap.methodName = Constants.MethodNames.ROUTE_MAP
        routeMap.name = "route map"
        routeMap.id = "0"
        routeMap.read = "0"
        routeMap.create = "0"
        routeMap.drawable = R.drawable.routemap
        //        leftNavs.add(routeMap);
        val contacts = LeftNav()
        contacts.methodName = Constants.MethodNames.CONTACT_LIST
        contacts.name = Constants.New_MethodNames.CONTACTS
        contacts.id = "1"
        contacts.read = "1"
        contacts.create = "1"
        contacts.drawable = R.drawable.contacts
        leftNavs.add(contacts)
        val customer_project = LeftNav()
        customer_project.methodName = Constants.New_MethodNames.CUSTOMER_PROJECT
        customer_project.name = Constants.New_MethodNames.CUSTOMER_PROJECT
        customer_project.id = "2"
        customer_project.read = "2"
        customer_project.create = "2"
        customer_project.drawable = R.drawable.customerprojects
        leftNavs.add(customer_project)
        val daily_report = LeftNav()
        daily_report.methodName = Constants.New_MethodNames.DAILY_REPORT
        daily_report.name = Constants.New_MethodNames.DAILY_REPORT
        daily_report.id = "3"
        daily_report.read = "3"
        daily_report.create = "3"
        daily_report.drawable = R.drawable.dailyreports
        leftNavs.add(daily_report)
        val final_report = LeftNav()
        final_report.methodName = Constants.New_MethodNames.FINAL_REPORT
        final_report.name = Constants.New_MethodNames.FINAL_REPORT
        final_report.id = "0"
        final_report.read = "0"
        final_report.create = "0"
        final_report.drawable = R.drawable.contracts
        //        leftNavs.add(final_report);
        val `object` = LeftNav()
        `object`.name = "logout"
        `object`.methodName = "logout"
        `object`.id = "0"
        `object`.read = "0"
        `object`.create = "0"
        `object`.drawable = R.drawable.logout
        leftNavs.add(`object`)
        //mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));
        for (i in Common.NAVITEMICONS.indices) {
            mIcons.add(Common.NAVITEMICONS[i])
        }
        mViewHolder = ViewHolder()
        handleToolbar()
        handleMenu()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            handleDrawer()
        }
    }

    override fun onInternetConnected() {}
    override fun onInternetDisconnected() {}
    private fun logoutpopup() {
        val builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = LayoutInflater.from(this)
        val dialogView: View = inflater.inflate(R.layout.logout_layout, null)
        dialogView.findViewById<View>(R.id.btn_yes)
        dialogView.findViewById<View>(R.id.btn_no)
        builder.setView(dialogView)
        alertDialog = builder.create()
        alertDialog!!.show()
        dialogView.findViewById<View>(R.id.btn_yes).setOnClickListener {
            db?.commonDao()?.deleteComplaintList()
            db?.commonDao()?.deleteContactList()
            db?.commonDao()?.deleteContactList()
            db?.commonDao()?.deleteCustomerList()
            db?.commonDao()?.deleteEmpActivityPojo()
            db?.commonDao()?.deleteCustomer()
            db?.commonDao()?.deleteLeadList()
            db?.commonDao()?.deleteOpportunities()
            db?.commonDao()?.deleteSalesCallList()
            db?.commonDao()?.deleteSalesOrderList()
            db?.commonDao()?.deleteTadaList()
            db?.commonDao()?.deleteGeoTrackingdata()
            Common.getDefaultSP(this@MainActivity).edit().clear().commit()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            //                loginDb = databaseHandler.loginDao().getAll();
//                async = new deleteAllWordsAsyncTask(loginDb);
//                async.execute((Void) null);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            alertDialog!!.dismiss()
            finishAffinity()
        }
        dialogView.findViewById<View>(R.id.btn_no).setOnClickListener { alertDialog!!.dismiss() }
        dialogView.findViewById<View>(R.id.logout_close)
            .setOnClickListener { alertDialog!!.dismiss() }
    }

    override fun onFooterClicked() {}

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    override fun onHeaderClicked() {
//        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//        startActivity(intent);
//        settexts();
//        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }

    private inner class ViewHolder internal constructor() {
        val mDuoDrawerLayout: DuoDrawerLayout
        val mDuoMenuView: DuoMenuView
        val mToolbar: Toolbar

        init {
            mDuoDrawerLayout = findViewById<View>(R.id.drawer) as DuoDrawerLayout
            mDuoMenuView = mDuoDrawerLayout.getMenuView() as DuoMenuView
            mToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        }
    }

    private fun handleToolbar() {
        setSupportActionBar(mViewHolder!!.mToolbar)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.actionbar)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        val view = supportActionBar!!.customView
        title = view.findViewById(R.id.title_text)
        mainLinearLayout = view.findViewById<LinearLayout>(R.id.main_layout1)
        searchView = view.findViewById(R.id.searchView)
        searchIv = view.findViewById(R.id.searchIv)

//        title.setText("Dashboard");


//        filter = view.findViewById(R.id.dashboard_filter);
//       refresh = view.findViewById(R.id.tasks_refresh);
        view.findViewById<View>(R.id.menu_icon).setOnClickListener {
            mViewHolder!!.mToolbar.setBackgroundColor(resources.getColor(R.color.white))
            mViewHolder!!.mDuoDrawerLayout.openDrawer()
            //settexts();
        }
        searchIv?.setOnClickListener(View.OnClickListener {
            mainLinearLayout?.setVisibility(View.GONE)
            searchView?.setVisibility(View.VISIBLE)
            searchView?.requestFocus()
            (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
                InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        })
    }

    @SuppressLint("RestrictedApi")
    private fun handleMenu() {
        mMenuAdapter = MenuAdapter(leftNavs, null, mIcons, this@MainActivity)
        mViewHolder!!.mDuoMenuView.setOnMenuClickListener(this)
        mViewHolder!!.mDuoMenuView.setAdapter(mMenuAdapter)
        val view: View = mViewHolder!!.mDuoMenuView.getHeaderView()
        profileImage = view.findViewById(R.id.profile_image)
        profileName = view.findViewById(R.id.profile_name)
        designationName = view.findViewById(R.id.designation_name)
        profileImage?.setImageResource(R.drawable.profile)
        //designationName.setText(Common.getLoginTypeFromSP(this));
        settexts()
        mMenuAdapter!!.notifyDataSetChanged()
    }

    fun settexts() {
        profileName!!.text = Common.getUserNameFromSP(this)
        designationName!!.text = Common.getRolenameFromSP(this)
        if (Common.getImageFromSP(this) != null /*&& Common.getImageFromSP(this).length() > 5*/) {
            Picasso.with(this).load(Common.getImageFromSP(this)).into(profileImage)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun handleDrawer() {
        val duoDrawerToggle = DuoDrawerToggle(
            this,
            mViewHolder!!.mDuoDrawerLayout,
            mViewHolder!!.mToolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        mViewHolder!!.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle)
        mViewHolder!!.mToolbar.setBackgroundColor(resources.getColor(R.color.white))
        //        duoDrawerToggle.setDrawerIndicatorEnabled(true);
        duoDrawerToggle.syncState()
    }

    private fun goToFragment(fragment: Fragment, b: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        fragment.arguments = bundle
        if (b) {
            transaction.replace(R.id.content_frame, fragment)
            //            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.commit()
        } else {
            transaction.replace(R.id.content_frame, fragment)
            //            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    override fun onOptionClicked(position: Int, objectClicked: Any) {
        mViewHolder!!.mToolbar.setBackgroundColor(resources.getColor(R.color.toolbar))
        mMenuAdapter!!.setViewSelected(position, true)
        settexts()
        bundle = Bundle()
        var methodName = ""
        methodName = leftNavs[position].methodName
        when (methodName) {
            Constants.New_MethodNames.LOGOUT -> logoutpopup()
            Constants.New_MethodNames.CUSTOMER_PROJECT -> {
                val customerProjectFragment = CustomerProjectFragment()
                goToFragment(customerProjectFragment, true)
            }
            Constants.MethodNames.CONTACT_LIST -> {
                val contactsFragment = NewContactsFragment()
                bundle?.putSerializable("contacts", leftNavs[position])
                goToFragment(contactsFragment, true)
            }
            Constants.New_MethodNames.DAILY_REPORT -> {
                val dailyReportsFragment = DailyReportsFragment()
                goToFragment(dailyReportsFragment, true)
            }
        }
        mViewHolder!!.mDuoDrawerLayout.closeDrawer()
    }

    private fun checkoutPopup() {
        val builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = LayoutInflater.from(this)
        val dialogView: View = inflater.inflate(R.layout.checkout_popup, null)
        dialogView.findViewById<View>(R.id.btn_yes)
        builder.setView(dialogView)
        alertDialog = builder.create()
        alertDialog!!.show()
        dialogView.findViewById<View>(R.id.btn_yes).setOnClickListener { alertDialog!!.dismiss() }
        dialogView.findViewById<View>(R.id.logout_close)
            .setOnClickListener { alertDialog!!.dismiss() }
    }

    override fun onBackPressed() {
        if (searchView != null && searchView!!.visibility == View.VISIBLE) {
            searchView!!.visibility = View.GONE
            mainLinearLayout?.setVisibility(View.VISIBLE)
            return
        }
        if (!doubleBackToExitPressedOnce) {
//            goToFragment(new NewContactsFragment(), false);
            Toast.makeText(this, "Click again to exit", Toast.LENGTH_SHORT).show()
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
        }
        doubleBackToExitPressedOnce = true
    }

    companion object {
        private const val DATABASE_NAME = "sensational_database"
    }
}