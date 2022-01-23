package com.ncl.nclcustomerservice.activity

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import butterknife.BindView
import butterknife.ButterKnife
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.*
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity
import com.ncl.nclcustomerservice.adapter.CustomSpinnerAdapter
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.commonutils.Constants
import com.ncl.nclcustomerservice.database.DatabaseHandler
import com.ncl.nclcustomerservice.databinding.ActivityCreateCustomerprojectBinding
import com.ncl.nclcustomerservice.databinding.AssociateContactsRowBindingBinding
import com.ncl.nclcustomerservice.network.RetrofitRequestController
import com.ncl.nclcustomerservice.network.RetrofitResponseListener
import java.util.*

class CreateCustomerProjectActivity : NetworkChangeListenerActivity(), RetrofitResponseListener {

    private var selectedHeadPosition = 0
    private var selectedAssociates = mutableListOf<Int>()
    private var selectedContractors = mutableListOf<Int>()
    private lateinit var binding: ActivityCreateCustomerprojectBinding

    lateinit var contactContractorLists: MutableList<CustomerContactResponseVo.ContactContractorList>

    var form_type: String? = null
    var id = 0

    lateinit var db: DatabaseHandler

    @BindView(R.id.ll_contractor_tm_details)
    var ll_contractor_tm_details: LinearLayout? = null
    var projectHeadReqVoList: MutableList<ProjectHeadReqVo> = ArrayList()
    var teamMemberResVoList: MutableList<CustomerContactResponseVo.TeamMemberResVo>? = null
    var newTeamMemberResVoList: MutableList<CustomerContactResponseVo.TeamMemberResVo> =
        ArrayList<CustomerContactResponseVo.TeamMemberResVo>()
    lateinit var statesLists: MutableList<StatesList>
    var stateId: String? = null

    override fun onInternetConnected() {}
    override fun onInternetDisconnected() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_customerproject)
        Common.setupUI(binding.root, this)
        form_type = "new" //intent.extras!!.getString("form_key", "")
        id = 0//intent.extras!!.getInt("id", 0)
        db = DatabaseHandler.getDatabase(this)
        binding.toolbar.titleText.text = "CUSTOMER PROJECT INFORMATION"
// Associate Contact Details
        projectHeadReqVoList = db.commonDao().allProjectHeadContactList
        setEmptyContact(projectHeadReqVoList)
        var bindingAssociate: AssociateContactsRowBindingBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.associate_contacts_row_binding,
                null,
                false
            )
        setProjectHeadSpinner(
            binding.ProjectHeadNameSpinner,
            projectHeadReqVoList,
            bindingAssociate
        )
        contactContractorLists = db.commonDao().allCustomerContactList
        val contactContractorList = CustomerContactResponseVo.ContactContractorList().apply {
            contactContractorId = "0"
            contactId = 0
            contractorName = "Select"
        }
        contactContractorLists.add(0, contactContractorList)
        val contractorRowView = layoutInflater.inflate(R.layout.contractor_details_row, null)
        binding.llContractorDetails.addView(contractorRowView)
        addContractorDetails(binding.llContractorDetails, contractorRowView)
        val dropDownDataReqVo = DropDownDataReqVo().apply {
            usersList = "users_list"
            customerList = "customer_list"
            teamId = Common.getTeamUserIdFromSP(this@CreateCustomerProjectActivity)
        }

        RetrofitRequestController(this).sendRequest(
            Constants.RequestNames.DROP_DOWN_LIST,
            dropDownDataReqVo,
            true
        )
    }

    private fun setEmptyContact(projectHeadReqVoList: MutableList<ProjectHeadReqVo>) {
        val projectHeadReqVo = ProjectHeadReqVo().apply {
            contactProjectHeadId = "0"
            contactId = "0"
            projectHeadName = "Select"
        }
        projectHeadReqVoList.add(0, projectHeadReqVo)
        val rowView = layoutInflater.inflate(R.layout.associate_contacts_row, null).apply {
            findViewById<View>(R.id.etACName).visibility = View.GONE
            findViewById<View>(R.id.ph_name_spinner).visibility =
                View.VISIBLE
        }
        binding.llAssociateContacts.addView(rowView)
    }

    private fun addContractorTMDetails() {
        for (i in 0 until ll_contractor_tm_details!!.childCount) {
            val ll_contractor_details_view = ll_contractor_tm_details!!.getChildAt(i)
            val contractorTMViewHolder = ContractorTMViewHolder(ll_contractor_details_view)
            contractorTMViewHolder.apply {
                tvTeamMemberName!!.text =
                    Common.setSppanableText("* Team Member Name")
                tvTeamMemberMobileNo!!.text =
                    Common.setSppanableText("* Team Member Mobile")
                tvCoAadharNo!!.text =
                    Common.setSppanableText("* Team Member Aadhar Number")
                etTeamMemberName.setText(newTeamMemberResVoList[i].teamMemberName)
                etTeamMemberMobileNo.setText(newTeamMemberResVoList[i].teamMemberMobileNo)
                etCoAadharNo.setText(newTeamMemberResVoList[i].teammemberAadharNumber)
                removelayout_ctm!!.setOnClickListener {
                    ll_contractor_tm_details!!.removeViewAt(i)
                    newTeamMemberResVoList.removeAt(i)
                    addContractorTMDetails()
                }
            }
        }
    }

    private fun addContractorDetails(ll_contractor_details: LinearLayout?, view: View?) {
        if (view != null) {
            val contractorContactViewHolder = ContractorContactViewHolder(view)
            contractorContactViewHolder.tvACName!!.text = Common.setSppanableText("* Name")
            contractorContactViewHolder.tvACMobile!!.text = Common.setSppanableText("* Mobile")
            if (ll_contractor_details!!.childCount > 1) {
                contractorContactViewHolder.removelayout_cc!!.visibility = View.VISIBLE
            } else {
                contractorContactViewHolder.removelayout_cc!!.visibility = View.GONE
            }
            //         int finalI = i;
            contractorContactViewHolder.removelayout_cc!!.setOnClickListener { v ->
                ll_contractor_details.removeView(v)
                //                 ll_contractor_details.removeViewAt(finalI);
                addContractorDetails(ll_contractor_details, null)
            }
            contractorContactViewHolder.addlayout_cc!!.setOnClickListener {
                val rowView1 = layoutInflater.inflate(R.layout.contractor_details_row, null)
                ll_contractor_details.addView(rowView1)
                addContractorDetails(ll_contractor_details, rowView1)
            }
            val contractorDetailsAdapter =
                ContractorDetailsAdapter(application, 0, contactContractorLists)
            contractorContactViewHolder.contractor_name_spinner!!.setAdapter(
                contractorDetailsAdapter
            )
            contractorContactViewHolder.contractor_name_spinner!!.setOnItemSelectedListener(object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        contractorContactViewHolder.etContractorMobile?.setText(
                            contactContractorLists!![position].contractorMobileNo
                        )
                        if (contactContractorLists!!.size > 0) {
                            teamMemberResVoList = contactContractorLists!![position].teamMembers
                            newTeamMemberResVoList.addAll(teamMemberResVoList!!)
                            ll_contractor_tm_details!!.removeAllViewsInLayout()
                            for (i in newTeamMemberResVoList.indices) {
                                val contractorTMRowView = layoutInflater.inflate(
                                    R.layout.contractor_team_member_details_row1,
                                    null
                                )
                                ll_contractor_tm_details!!.addView(contractorTMRowView)
                            }
                            addContractorTMDetails()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })
        }
    }

    private fun setProjectHeadSpinner(
        spinner: Spinner,
        projectHeadReqVoList: List<ProjectHeadReqVo>,
        viewHolder: AssociateContactsRowBindingBinding
    ) {
        val projectContactAdapter = ProjectContactAdapter(application, 0, projectHeadReqVoList)
        spinner.adapter = projectContactAdapter
        spinner.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    selectedHeadPosition = position
                    setProjectHeadUI(projectHeadReqVoList[position])
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }


    private fun setProjectHeadUI(
        projectHead: ProjectHeadReqVo,
    ) {
        binding.etPHMobile.setText(projectHead.projectHeadMobile)
        binding.etPHDepartment.setText(projectHead.projectHeadDepartment)
        binding.etPHCompanyName.setText(projectHead.companyOrClientName)
        binding.ProjectHeadNameSpinner.setSelection(selectedHeadPosition)
        selectedAssociates.clear()
        showAssociatedContactDialog(
            projectHead.associateContacts ?: mutableListOf(),
            selectedAssociates
        )
    }

    private fun showAssociatedContactDialog(
        list: List<ProjectHeadReqVo.AssociateContact>?,
        selectedAssociates: MutableList<Int>,
    ) {
        CustomDialog(
            this,
            list ?: mutableListOf(),
            { obj -> obj.contactProjectHeadAssociateContactName },
            selectedAssociates
        ) {
            this.selectedAssociates = it.toMutableList()
            setAssociateContact(it, list ?: mutableListOf())
        }.show()
    }

    private fun setAssociateContact(
        list: List<Int>,
        associateContacts: List<ProjectHeadReqVo.AssociateContact>
    ) {
        binding.llAssociateContacts.removeAllViews()
        list.forEach {
            var obj = associateContacts[it]
            val viewHolder: AssociateContactsRowBindingBinding =
                DataBindingUtil.inflate(
                    layoutInflater,
                    R.layout.associate_contacts_row_binding,
                    null,
                    false
                )
            viewHolder.tvACName.text = Common.setSppanableText("* Name")
            viewHolder.tvACDesignation.text = Common.setSppanableText("* Designation")
            viewHolder.tvACMobile.text = Common.setSppanableText("* Mobile")
            viewHolder.removelayoutAc.visibility = View.VISIBLE
            viewHolder.etACDesignation.setText(obj.contactProjectHeadAssociateContactDesignation)
            viewHolder.etACMobile.setText(obj.contactProjectHeadAssociateContactMobile)
            viewHolder.phNameSpinner.visibility = View.GONE
            viewHolder.etACName.visibility = View.VISIBLE
            viewHolder.etACName.setText(obj.contactProjectHeadAssociateContactName)
            viewHolder.removelayoutAc.setOnClickListener {
                showAssociatedContactDialog(associateContacts, selectedAssociates)
            }
            viewHolder.addlayoutAc.setOnClickListener {
                showAssociatedContactDialog(associateContacts, selectedAssociates)
            }
            binding.llAssociateContacts.addView(viewHolder.root)

        }
    }


    class ContractorTMViewHolder(rowView: View) {
        @BindView(R.id.tvTeamMemberName)
        lateinit var tvTeamMemberName: TextView

        @BindView(R.id.etTeamMemberName)
        lateinit var etTeamMemberName: EditText

        @BindView(R.id.tvTeamMemberMobileNo)
        lateinit var tvTeamMemberMobileNo: TextView

        @BindView(R.id.etTeamMemberMobileNo)
        lateinit var etTeamMemberMobileNo: EditText

        @BindView(R.id.tvCoAadharNo)
        lateinit var tvCoAadharNo: TextView

        @BindView(R.id.etCoAadharNo)
        lateinit var etCoAadharNo: EditText

        @BindView(R.id.removelayout_ctm)
        lateinit var removelayout_ctm: LinearLayout

        init {
            ButterKnife.bind(this, rowView)
            etTeamMemberName!!.isEnabled = false
            etTeamMemberMobileNo!!.isEnabled = false
            etCoAadharNo!!.isEnabled = false
        }
    }

    class ContractorContactViewHolder(var rowView: View?) {
        //        @BindView(R.id.tvACName)
        var tvACName: TextView? = null

        //        @BindView(R.id.contractor_name_spinner)
        var contractor_name_spinner: Spinner? = null

        //        @BindView(R.id.tvACMobile)
        var tvACMobile: TextView? = null

        //        @BindView(R.id.etContractorMobile)
        var etContractorMobile: EditText? = null

        //        @BindView(R.id.addlayout_cc)
        var addlayout_cc: LinearLayout? = null

        //        @BindView(R.id.removelayout_cc)
        var removelayout_cc: LinearLayout? = null

        init {
//            ButterKnife.bind(this, rowView!!)
            tvACName = rowView?.findViewById(R.id.tvACName)
            tvACMobile = rowView?.findViewById(R.id.tvACMobile)
            etContractorMobile = rowView?.findViewById(R.id.etContractorMobile)
            addlayout_cc = rowView?.findViewById(R.id.addlayout_cc)
            removelayout_cc = rowView?.findViewById(R.id.removelayout_cc)
            contractor_name_spinner = rowView?.findViewById(R.id.contractor_name_spinner)
            etContractorMobile?.isEnabled = false
        }
    }

    inner class AssociateContactViewHolder(var rowView: View) {
        //        @BindView(R.id.tvACName)
        var tvACName: TextView? = null

        //        @BindView(R.id.ph_name_spinner)
        var ph_name_spinner: Spinner? = null

        //        @BindView(R.id.tvACDesignation)
        var tvACDesignation: TextView? = null

        //        @BindView(R.id.etACDesignation)
        var etACDesignation: EditText? = null

        //        @BindView(R.id.etACName)
        var etACName: EditText? = null

        //        @BindView(R.id.tvACMobile)
        var tvACMobile: TextView? = null

        //        @BindView(R.id.etACMobile)
        var etACMobile: EditText? = null

        //        @BindView(R.id.addlayout_ac)
        var addLayout_ac: LinearLayout? = null

        //        @BindView(R.id.removelayout_ac)
        var removeLayout_ac: LinearLayout? = null

        init {
            tvACName = rowView.findViewById<TextView>(R.id.tvACName)
            tvACMobile = rowView.findViewById<TextView>(R.id.tvACMobile)
            tvACDesignation = rowView.findViewById<TextView>(R.id.tvACDesignation)
            etACDesignation = rowView.findViewById<EditText>(R.id.etACDesignation)
            etACName = rowView.findViewById<EditText>(R.id.etACName)
            etACMobile = rowView.findViewById<EditText>(R.id.etACMobile)
            addLayout_ac = rowView.findViewById<LinearLayout>(R.id.addlayout_ac)
            removeLayout_ac = rowView.findViewById<LinearLayout>(R.id.removelayout_ac)
            ph_name_spinner = rowView.findViewById<Spinner>(R.id.ph_name_spinner)
            etACDesignation!!.isEnabled = false
            etACMobile!!.isEnabled = false
        }
    }

    override fun onResponseSuccess(
        objectResponse: ApiResponseController,
        objectRequest: ApiRequestController,
        progressDialog: ProgressDialog
    ) {
        try {
            when (objectResponse.requestname) {
                Constants.RequestNames.DROP_DOWN_LIST -> {
                    val dropDownData: DropDownData = Common.getSpecificDataObject<DropDownData>(
                        objectResponse.result,
                        DropDownData::class.java
                    )
                    if (dropDownData != null) {
                        statesLists = dropDownData.statesList
                        if (statesLists != null) {
                            val sl = StatesList()
                            sl.stateId = "0"
                            sl.stateName = "Select State"
                            statesLists.add(0, sl)
                            val states: MutableList<SpinnerModel> = ArrayList()
                            var i = 0
                            while (i < statesLists!!.size) {
                                val spinnerModel = SpinnerModel()
                                spinnerModel.id = statesLists!![i].stateId
                                spinnerModel.title = statesLists!![i].stateName
                                states.add(spinnerModel)
                                i++
                            }
                            val customSpinnerAdapter = CustomSpinnerAdapter(this, 0, states)
                            binding.stateSpinner.adapter = customSpinnerAdapter
                            binding.stateSpinner.setOnItemSelectedListener(object :
                                AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    adapterView: AdapterView<*>?,
                                    view: View,
                                    i: Int,
                                    l: Long
                                ) {
                                    stateId = if (i > 0) statesLists!![i].stateName else ""
                                }

                                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                            })
                        }
                    } else {
                        Toast.makeText(this, "Drop Down Data is Empty.", Toast.LENGTH_LONG).show()
                    }
                }
            }
            Common.dismissProgressDialog(progressDialog)
        } catch (e: Exception) {
            Common.disPlayExpection(e, progressDialog)
        }
    }

    private inner class AssociateContactAdapter(
        context: Context,
        textViewResourceId: Int,
        associateContactList: List<ProjectHeadReqVo.AssociateContact>
    ) : ArrayAdapter<ProjectHeadReqVo.AssociateContact>(
        context, textViewResourceId, associateContactList
    ) {
        var associateContactList: List<ProjectHeadReqVo.AssociateContact> = associateContactList
        var inflater: LayoutInflater
        var tempValues: ProjectHeadReqVo.AssociateContact? = null
        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            return getCustomView(position, convertView, parent)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return getCustomView(position, convertView, parent)
        }

        fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val row: View = inflater.inflate(R.layout.spinner_rows, parent, false)
            /***** Get each Model object from Arraylist  */
            tempValues = associateContactList[position]
            val label = row.findViewById<View>(R.id.spinnertitle) as TextView

            // Set values for spinner each row
            label.text = tempValues!!.contactProjectHeadAssociateContactName
            label.tag = tempValues!!.contactProjectheadAssociatecontactId
            return row
        }

        init {
            inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }

    class ContractorDetailsAdapter(
        context: Context,
        var textViewResourceId: Int,
        var contractorDetailsList: MutableList<CustomerContactResponseVo.ContactContractorList>
    ) : ArrayAdapter<CustomerContactResponseVo.ContactContractorList>(
        context, textViewResourceId, contractorDetailsList
    ) {
        var inflater: LayoutInflater
        override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
            return getCustomView(position, convertView, parent)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return getCustomView(position, convertView, parent)
        }

        fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val row: View = inflater.inflate(R.layout.spinner_rows, parent, false)

            /***** Get each Model object from Arraylist  */
            var tempValues =
                contractorDetailsList!![position] as CustomerContactResponseVo.ContactContractorList
            val label = row.findViewById<View>(R.id.spinnertitle) as TextView
            // Set values for spinner each row
            label.setText(tempValues.contractorName)
            label.tag = tempValues.contactContractorId
            return row
        }

        init {
            inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }

    private inner class ProjectContactAdapter(
        context: Context,
        textViewResourceId: Int,
        var projectHeadReqVoList: List<ProjectHeadReqVo>
    ) : ArrayAdapter<ProjectHeadReqVo>(
        context, textViewResourceId, projectHeadReqVoList
    ) {
        var inflater: LayoutInflater
        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            return getCustomView(position, convertView, parent)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return getCustomView(position, convertView, parent)
        }

        fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val row: View = inflater.inflate(R.layout.spinner_rows, parent, false)

            var tempValues = projectHeadReqVoList[position]
            val label = row.findViewById<View>(R.id.spinnertitle) as TextView

            // Set values for spinner each row
            label.text = tempValues!!.projectHeadName
            label.tag = tempValues!!.contactProjectHeadId
            return row
        }

        init {
            inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }

}