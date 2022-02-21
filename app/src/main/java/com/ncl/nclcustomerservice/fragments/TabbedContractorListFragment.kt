package com.ncl.nclcustomerservice.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.*
import com.ncl.nclcustomerservice.activity.MultiSelectionDialog
import com.ncl.nclcustomerservice.activity.NewContactViewActivity
import com.ncl.nclcustomerservice.adapter.NewContactAdapter
import com.ncl.nclcustomerservice.commonutils.*
import com.ncl.nclcustomerservice.database.DatabaseHandler
import com.ncl.nclcustomerservice.databinding.TabbedContractorListBinding
import com.ncl.nclcustomerservice.network.RetrofitRequestController
import com.ncl.nclcustomerservice.network.RetrofitResponseListener
import java.io.Serializable

class TabbedContractorListFragment : BaseFragment(), RetrofitResponseListener, OnRefreshListener {
    private var contact: MutableList<CustomerContactResponseVo.ContactContractorList> =
        mutableListOf()
    private lateinit var binding: TabbedContractorListBinding
    private lateinit var loginResponse: LoginResVo
    val db: DatabaseHandler by lazy { DatabaseHandler.getDatabase(activity) }
    private lateinit var contactAdapter: NewContactAdapter

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
            db.commonDao().getContractorContactList(100, 0)?.let {
                contact = it.toMutableList()
                setOnAdapter(binding.contactRecycler, it)
            }
        }
        loginResponse = Common.getLoginResponse(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.tabbed_contractor_list,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etSearch.onTextChange {
            it?.let { filterList(it.toString()) }
        }
        binding.ivFilter.setOnClickListener {
            var list = loginResponse.usersTeam.map { it }.toMutableList()
            list.apply {
                add(0, UsersTeam().apply {
                    userId = -1
                    name = "All"
                })
            }
            MultiSelectionDialog(
                context = requireContext(),
                list = list,
                mapper = { if (it.name == loginResponse.name) "My Data" else it.name },
                selectedPosition = null,
                isSingleSelection = true,
                isSearchable = false,
                receivedData = {
                    binding.etSearch.setText("")
                    var obj = list[it[0]]
                    when (obj.name) {
                        "All" -> {
                            setOnAdapter(binding.contactRecycler, contact)
                        }
                        loginResponse.name -> {
                            setOnAdapter(
                                binding.contactRecycler,
                                contact.filter { it.createdBy == loginResponse.userId.toString() })
                        }
                        else -> {
                            setOnAdapter(
                                binding.contactRecycler,
                                contact.filter { it.createdBy == obj.userId.toString() })
                        }
                    }
                },
            ).show()
        }
    }

    private fun filterList(text: String) {
        contactAdapter.filter(text)
    }

    override fun onResume() {
        super.onResume()
        callService(Common.getTeamUserIdFromSP(activity))
    }

    private fun setOnAdapter(
        contact_recycler: RecyclerView,
        contactLists: List<CustomerContactResponseVo.ContactContractorList>
    ) {
        contactAdapter = NewContactAdapter(requireContext(), contactLists) { obj ->
            val intent = Intent(activity, NewContactViewActivity::class.java)
            intent.putExtra("contactContractorList", obj as Serializable)
            intent.putExtra("type", "Contractor")
            startActivity(intent)
        }
        contact_recycler.adapter = contactAdapter

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
                    if (newCustomerResVo.contactList != null) {
                        if (newCustomerResVo.contactList.contactContractorLists != null && newCustomerResVo.contactList.contactContractorLists.size > 0) {
                            val contactContractorLists: List<CustomerContactResponseVo.ContactContractorList> =
                                newCustomerResVo.contactList.contactContractorLists
                            binding.contactRecycler.show()
                            db.commonDao().deleteContactContractorList()
                            db.commonDao().insertContractorContact(contactContractorLists)
                            contact = contactContractorLists.toMutableList()
                            setOnAdapter(binding.contactRecycler, contactContractorLists)
                        }
                    }
                } else {
                    binding.contactRecycler.hide()
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