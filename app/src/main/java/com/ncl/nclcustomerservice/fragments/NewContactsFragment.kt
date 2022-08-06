package com.ncl.nclcustomerservice.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import butterknife.ButterKnife
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.*
import com.ncl.nclcustomerservice.activity.CreateNewContactActivity
import com.ncl.nclcustomerservice.activity.MainActivity
import com.ncl.nclcustomerservice.application.BackgroundService
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.commonutils.Constants
import com.ncl.nclcustomerservice.commonutils.hide
import com.ncl.nclcustomerservice.database.DatabaseHandler
import com.ncl.nclcustomerservice.network.RetrofitRequestController
import com.ncl.nclcustomerservice.network.RetrofitResponseListener

class NewContactsFragment : BaseFragment(), RetrofitResponseListener, OnRefreshListener {
    var db: DatabaseHandler? = null
    private lateinit var filterView: ImageView
    private lateinit var addView: ImageView
    private lateinit var tabLayout: TabLayout
    override fun onRefresh() {
        callService(Common.getTeamUserIdFromSP(activity))
    }

    private fun callService(userId: String) {
        if (Common.haveInternet(activity)) {
            val contactTeam = Team()
            contactTeam.teamId = userId
            RetrofitRequestController(this).sendRequest(
                Constants.RequestNames.CONTACT_LIST,
                contactTeam,
                true
            )
        }
    }

    override fun setUserId(userId: String) {
        super.setUserId(userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            LayoutInflater.from(activity).inflate(R.layout.fragment_contact, container, false)
        ButterKnife.bind(this, view)
        tabLayout = view.findViewById<View>(R.id.tabs) as TabLayout
        val firstTab = tabLayout.newTab()
        firstTab.text = "Installation Contractor"
        tabLayout.addTab(firstTab)
        val secondTab = tabLayout.newTab()
        secondTab.text = "Project contact details"
        tabLayout.addTab(secondTab)
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, TabbedContractorListFragment())
            .commit()
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, TabbedContractorListFragment())
                        .commit()
                } else if (tab.position == 1) {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, TabbedProjectHeadListFragment())
                        .commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        db = DatabaseHandler.getDatabase(activity)
        if (Common.haveInternet(activity)) {
            Common.startService(activity, BackgroundService::class.java)
        }
        filterView = (activity as MainActivity?)!!.findViewById(R.id.filter_task)
        filterView.hide()
        filterView.setOnClickListener(View.OnClickListener { Common.getSelectedUser(this@NewContactsFragment) })
        addView = (activity as MainActivity?)!!.findViewById(R.id.add_task)
        ((activity as MainActivity?)!!.supportActionBar!!.customView.findViewById<View>(R.id.title_text) as TextView).text =
            "CONTACTS"
        addView.setOnClickListener(View.OnClickListener {
            val addIntent = Intent(activity, CreateNewContactActivity::class.java)
            addIntent.putExtra("form_key", "new")
            startActivity(addIntent)
        })
//        val contact: List<CustomerContactResponseVo.ContactContractorList> =
//            db?.commonDao()?.getContractorContactList(100, 0) as List<CustomerContactResponseVo.ContactContractorList>
        return view
    }

    override fun onResponseSuccess(
        objectResponse: ApiResponseController,
        objectRequest: ApiRequestController,
        progressDialog: ProgressDialog
    ) {
        try {
            when (objectRequest.requestname) {
                Constants.RequestNames.CONTACT_LIST -> if (objectResponse.result != null) {
                    val newCustomerResVo: NewCustomerResVo =
                        Common.getSpecificDataObject<NewCustomerResVo>(
                            objectResponse.result,
                            NewCustomerResVo::class.java
                        )
                    if (newCustomerResVo?.contactList != null) {
                        if (newCustomerResVo.contactList.contactContractorLists != null && newCustomerResVo.contactList.contactContractorLists.size > 0) {
                            val contactContractorLists: List<CustomerContactResponseVo.ContactContractorList> =
                                newCustomerResVo.contactList.contactContractorLists
                            db!!.commonDao().deleteContactContractorList()
                            db!!.commonDao().insertContractorContact(contactContractorLists)
                        }
                    }
                } else {
                    Toast.makeText(activity, objectResponse.message, Toast.LENGTH_SHORT).show()
                }
            }
            Common.dismissProgressDialog(progressDialog)
        } catch (e: Exception) {
            Common.disPlayExpection(e, progressDialog)
        }
    }
}