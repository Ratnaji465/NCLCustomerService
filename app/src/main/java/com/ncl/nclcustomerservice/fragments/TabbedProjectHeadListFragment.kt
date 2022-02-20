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
import com.ncl.nclcustomerservice.adapter.ProjectHeadAdapter
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.commonutils.Constants
import com.ncl.nclcustomerservice.commonutils.hide
import com.ncl.nclcustomerservice.commonutils.onTextChange
import com.ncl.nclcustomerservice.database.DatabaseHandler
import com.ncl.nclcustomerservice.databinding.TabbedProjectheadListBinding
import com.ncl.nclcustomerservice.network.RetrofitRequestController
import com.ncl.nclcustomerservice.network.RetrofitResponseListener
import java.io.Serializable

class TabbedProjectHeadListFragment : BaseFragment(), RetrofitResponseListener, OnRefreshListener {
    private lateinit var loginResponse: LoginResVo
    var projectHeads: List<ProjectHeadReqVo> = listOf()
    private lateinit var binding: TabbedProjectheadListBinding
    val db: DatabaseHandler by lazy {
        DatabaseHandler.getDatabase(activity)
    }

    private lateinit var projectHeadAdapter: ProjectHeadAdapter

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
//                db.commonDao()?.getProjectHeadContactList(100, 0, "")
            db.commonDao()?.allProjectHeadContactList?.let {
                projectHeads = it.toMutableList()
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
            (R.layout.tabbed_projecthead_list), container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etSearch.onTextChange {
            projectHeadAdapter.filter(it?.toString() ?: "")
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
                            setOnAdapter(binding.contactRecycler, projectHeads)
                        }
                        loginResponse.name -> {
                            setOnAdapter(
                                binding.contactRecycler,
                                projectHeads.filter { it.createdBy == obj.userId.toString() })
                        }
                        else -> {
                            setOnAdapter(
                                binding.contactRecycler,
                                projectHeads.filter { it.createdBy == loginResponse.userId.toString() })
                        }
                    }
                },
            ).show()

        }
    }


    override fun onResume() {
        super.onResume()
        callService(Common.getTeamUserIdFromSP(activity))
    }

    private fun setOnAdapter(
        contact_recycler: RecyclerView,
        projectHeadReqVoList: List<ProjectHeadReqVo>
    ) {
        projectHeadAdapter = ProjectHeadAdapter(requireContext(), projectHeadReqVoList) {
            val intent = Intent(activity, NewContactViewActivity::class.java)
            intent.putExtra(
                "contactProjectHeadList",
                it as Serializable
            )
            intent.putExtra("type", "ProjectHead")
            startActivity(intent)
        }
        contact_recycler.adapter = projectHeadAdapter
    }

    override fun onRefresh() {}
    override fun onResponseSuccess(
        objectResponse: ApiResponseController?,
        objectRequest: ApiRequestController,
        progressDialog: ProgressDialog?
    ) {
        try {
            when (objectRequest.requestname) {
                Constants.RequestNames.CONTACT_LIST -> if (objectResponse?.result != null) {
                    val projectHeadContactListResVo: ProjectHeadContactListResVo =
                        Common.getSpecificDataObject<ProjectHeadContactListResVo>(
                            objectResponse.result,
                            ProjectHeadContactListResVo::class.java
                        )
                    if (projectHeadContactListResVo.projectHeadListResVo != null) {
                        if (projectHeadContactListResVo.projectHeadListResVo.projectHeadReqVoList != null && projectHeadContactListResVo.projectHeadListResVo.projectHeadReqVoList.size > 0) db!!.commonDao()
                            .deleteProjectHeadContactList()
                        db.commonDao()
                            .insertProjectHeadContact(projectHeadContactListResVo.projectHeadListResVo.projectHeadReqVoList)
                        projectHeads =
                            projectHeadContactListResVo.projectHeadListResVo.projectHeadReqVoList
                        setOnAdapter(
                            binding.contactRecycler,
                            projectHeads
                        )

                    }
                } else {
                    binding.contactRecycler.hide()
                    Toast.makeText(activity, objectResponse?.message, Toast.LENGTH_SHORT).show()
                }
            }
            Common.dismissProgressDialog(progressDialog)
        } catch (e: Exception) {
            Common.disPlayExpection(e, progressDialog)
        }
    }
}