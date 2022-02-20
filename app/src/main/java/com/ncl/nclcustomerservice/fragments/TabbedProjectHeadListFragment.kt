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
import com.ncl.nclcustomerservice.adapter.ProjectHeadAdapter
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.commonutils.Constants
import com.ncl.nclcustomerservice.database.DatabaseHandler
import com.ncl.nclcustomerservice.network.RetrofitRequestController
import com.ncl.nclcustomerservice.network.RetrofitResponseListener
import java.io.Serializable

class TabbedProjectHeadListFragment : BaseFragment(), RetrofitResponseListener, OnRefreshListener {
    var db: DatabaseHandler? = null
    private val queryString = "%%"

    @JvmField
    @BindView(R.id.contact_recycler)
    var contact_recycler: RecyclerView? = null
    private var projectHeadAdapter: ProjectHeadAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
            val projectHeadReqVoList: List<ProjectHeadReqVo>? =
                db?.commonDao()?.getProjectHeadContactList(100, 0, queryString)
            projectHeadReqVoList?.let { setOnAdapter(contact_recycler, it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = LayoutInflater.from(activity)
            .inflate(R.layout.tabbed_projecthead_list, container, false)
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
        projectHeadReqVoList: List<ProjectHeadReqVo>
    ) {
        projectHeadAdapter = ProjectHeadAdapter(context, projectHeadReqVoList)
        contact_recycler!!.adapter = projectHeadAdapter
        projectHeadAdapter!!.setOnItemClickListener { view, viewItem, position ->
            val intent = Intent(activity, NewContactViewActivity::class.java)
            intent.putExtra(
                "contactProjectHeadList",
                projectHeadReqVoList[position] as Serializable
            )
            intent.putExtra("type", "ProjectHead")
            startActivity(intent)
        }
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
                    if (projectHeadContactListResVo != null && projectHeadContactListResVo.projectHeadListResVo != null) {
                        if (projectHeadContactListResVo.projectHeadListResVo.projectHeadReqVoList != null && projectHeadContactListResVo.projectHeadListResVo.projectHeadReqVoList.size > 0) db!!.commonDao()
                            .deleteProjectHeadContactList()
                        db!!.commonDao()
                            .insertProjectHeadContact(projectHeadContactListResVo.projectHeadListResVo.projectHeadReqVoList)
                        setOnAdapter(
                            contact_recycler,
                            projectHeadContactListResVo.projectHeadListResVo.projectHeadReqVoList
                        )
                    }
                } else {
                    contact_recycler!!.visibility = View.GONE
                    Toast.makeText(activity, objectResponse?.message, Toast.LENGTH_SHORT).show()
                }
            }
            Common.dismissProgressDialog(progressDialog)
        } catch (e: Exception) {
            Common.disPlayExpection(e, progressDialog)
        }
    }
}