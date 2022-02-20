package com.ncl.nclcustomerservice.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import butterknife.BindView
import butterknife.ButterKnife
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.*
import com.ncl.nclcustomerservice.activity.NewContactViewActivity
import com.ncl.nclcustomerservice.adapter.NewContactAdapter
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.commonutils.Constants
import com.ncl.nclcustomerservice.database.DatabaseHandler
import com.ncl.nclcustomerservice.network.RetrofitRequestController
import com.ncl.nclcustomerservice.network.RetrofitResponseListener
import java.io.Serializable

class TabbedContractorListFragment : BaseFragment(), RetrofitResponseListener, OnRefreshListener {
    var db: DatabaseHandler? = null
    private var contactAdapter: NewContactAdapter? = null

    @JvmField
    @BindView(R.id.contact_recycler)
    var contact_recycler: RecyclerView? = null
    private fun callService(userId: String) {
        if (Common.haveInternet(activity)) {
            val contactTeam = Team()
            contactTeam.teamId = userId
            RetrofitRequestController(this).sendRequest(
                Constants.RequestNames.CONTACT_LIST,
                contactTeam,
                false
            )
        } else {
            val contact: List<CustomerContactResponseVo.ContactContractorList>? =
                db!!.commonDao().getContractorContactList(100, 0)
            contact?.let { setOnAdapter(contact_recycler, it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            LayoutInflater.from(activity).inflate(R.layout.tabbed_contractor_list, container, false)
        ButterKnife.bind(this, view)
        db = DatabaseHandler.getDatabase(activity)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        contact_recycler!!.layoutManager = linearLayoutManager
        return view
    }

    override fun onResume() {
        super.onResume()
        callService(Common.getTeamUserIdFromSP(activity))
    }

    private fun setOnAdapter(
        contact_recycler: RecyclerView?,
        contactLists: List<CustomerContactResponseVo.ContactContractorList>
    ) {
        contactAdapter = NewContactAdapter(context, contactLists)
        contact_recycler!!.adapter = contactAdapter
        contactAdapter!!.setOnItemClickListener { view, viewItem, position ->
            val intent = Intent(activity, NewContactViewActivity::class.java)
            intent.putExtra("contactContractorList", contactLists[position] as Serializable)
            intent.putExtra("type", "Contractor")
            startActivity(intent)
        }
    }

    override fun onResponseSuccess(
        objectResponse: ApiResponseController,
        objectRequest: ApiRequestController,
        progressDialog: ProgressDialog?
    ) {
        try {
            when (objectRequest.requestname) {
                Constants.RequestNames.CONTACT_LIST -> if (objectResponse.result != null) {
                    val newCustomerResVo: NewCustomerResVo =
                        Common.getSpecificDataObject<NewCustomerResVo>(
                            objectResponse.result,
                            NewCustomerResVo::class.java
                        )
                    if (newCustomerResVo != null && newCustomerResVo.contactList != null) {
                        if (newCustomerResVo.contactList.contactContractorLists != null && newCustomerResVo.contactList.contactContractorLists.size > 0) {
                            val contactContractorLists: List<CustomerContactResponseVo.ContactContractorList> =
                                newCustomerResVo.contactList.contactContractorLists
                            contact_recycler!!.visibility = View.VISIBLE
                            db!!.commonDao().deleteContactContractorList()
                            db!!.commonDao().insertContractorContact(contactContractorLists)
                            setOnAdapter(contact_recycler, contactContractorLists)
                        }
                    }
                } else {
                    contact_recycler!!.visibility = View.GONE
                    Toast.makeText(activity, objectResponse.message, Toast.LENGTH_SHORT).show()
                }
            }
            Common.dismissProgressDialog(progressDialog)
        } catch (e: Exception) {
            Common.disPlayExpection(e, progressDialog)
        }
    }

    override fun onRefresh() {}
}