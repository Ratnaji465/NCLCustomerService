package com.ncl.nclcustomerservice.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.*
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity
import com.ncl.nclcustomerservice.adapter.CustomSpinnerAdapter
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.commonutils.Constants
import com.ncl.nclcustomerservice.commonutils.getArguments
import com.ncl.nclcustomerservice.database.DatabaseHandler
import com.ncl.nclcustomerservice.databinding.ActivityCreateCustomerprojectBinding
import com.ncl.nclcustomerservice.databinding.AssociateContactsRowBindingBinding
import com.ncl.nclcustomerservice.databinding.ContractorDetailsRowBinding
import com.ncl.nclcustomerservice.databinding.ContractorTeamMemberDetailsRow1Binding
import com.ncl.nclcustomerservice.network.RetrofitRequestController
import com.ncl.nclcustomerservice.network.RetrofitResponseListener
import java.io.Serializable

class CreateCustomerProjectActivity : NetworkChangeListenerActivity(), RetrofitResponseListener {

    private lateinit var customerProjectId: String
    var projectHeadReqVoList: MutableList<ProjectHeadReqVo> = ArrayList()
    private var selectedHeadPosition = -1
    private var selectedAssociates = mutableListOf<Int>()
    lateinit var contactContractorLists: MutableList<CustomerContactResponseVo.ContactContractorList>
    private var selectedContractors = mutableListOf<Int>()
    private var arrTeamMembers: List<CustomerContactResponseVo.TeamMemberResVo> = mutableListOf()
    private var selectTeamMembers = mutableListOf<Int>()
    var hmTeamMembers = mutableMapOf<String, List<CustomerContactResponseVo.TeamMemberResVo>>()
    var isEdit = false

    private lateinit var binding: ActivityCreateCustomerprojectBinding

    lateinit var db: DatabaseHandler

    lateinit var statesLists: MutableList<StatesList>
    var stateId: String? = null

    override fun onInternetConnected() {}
    override fun onInternetDisconnected() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_customerproject)
        Common.setupUI(binding.root, this)
        binding.apply {
            toolbar.titleText.text = "CUSTOMER PROJECT INFORMATION"
            toolbar.backButton.setOnClickListener {
                finish()
            }
            tvProjectName.text = Common.setSppanableText("* Project Name")
            tvProjectAddress.text = Common.setSppanableText("* Project Address")
            tvState.text = Common.setSppanableText("* State")
            tvCountry.text = Common.setSppanableText("* Country")
            tvPinCode.text = Common.setSppanableText("* Pincode")
            tvProjectHeadName.text = Common.setSppanableText("* Project Head Name")
            tvPHMobile.text = Common.setSppanableText("* Mobile")
            tvPHDepartment.text = Common.setSppanableText("* Department")
            tvPHCompanyName.text = Common.setSppanableText("* Company/Client Name")
            tvTeamSizeNo.text = Common.setSppanableText("* Team Size")
        }
        db = DatabaseHandler.getDatabase(this)
// Associate Contact Details
        projectHeadReqVoList = db.commonDao().allProjectHeadContactList
//        var bindingAssociate: AssociateContactsRowBindingBinding =
//            DataBindingUtil.inflate(
//                layoutInflater,
//                R.layout.associate_contacts_row_binding,
//                null,
//                false
//            )
        contactContractorLists = db.commonDao().allCustomerContactList
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
        getArguments<Args>()?.let {
            isEdit = true
            val head = it.customerProjectResVO
            customerProjectId = head.customerProjectId
            selectedHeadPosition =
                projectHeadReqVoList.indexOfFirst { it.contactProjectHeadId == head.projectHead[0].contactProjectHeadId }
            selectedAssociates.clear()
            projectHeadReqVoList[selectedHeadPosition].associateContacts.forEachIndexed { index, associateContact ->
                var position =
                    head.projectHead[0].associateContacts.indexOfFirst { it.contactProjectheadAssociatecontactId == associateContact.contactProjectheadAssociatecontactId }
                if (position >= 0) {
                    selectedAssociates.add(index)
                }
            }
            setProjectHeadUI(head.toProjectReq())
            binding.etProjectName.setText(head.projectName)
            binding.etProjectAddress.setText(head.projectAddress)
            binding.etCountry.setText(head.country)
            binding.etPincode.setText(head.pincode)
            setAssociateContact(
                selectedAssociates,
                projectHeadReqVoList[selectedHeadPosition].associateContacts
            )
//            binding.ProjectHeadNameSpinner.setSelection(selectedHeadPosition)
            contactContractorLists.forEachIndexed { index, contactContractorList ->
                var position = head.contractors.indexOfFirst {
                    it.contractorAadharNumber == contactContractorList.contractorAadharNumber
                }
                if (position >= 0) {
                    selectedContractors.add(index)
                }
            }
            setContractorsUI(
                binding.llContractorDetails,
                contactContractorLists,
                selectedContractors
            )
            arrTeamMembers =
                selectedContractors.flatMap { contactContractorLists[it].teamMembers }
            hmTeamMembers = arrTeamMembers.groupBy { it.contactContractorTeamId }.toMutableMap()

            var listTM = head.contractors.flatMap { it.teamMembers }
                .map { it.teammemberAadharNumber }

            arrTeamMembers.forEachIndexed { index, teamMemberResVo ->
                if (listTM.contains(teamMemberResVo.teammemberAadharNumber)) {
                    selectTeamMembers.add(index)
                }
            }
            binding.etTeamSizeNo.setText(head.contractorTeamSize)
            setTeamMembersUI(
                binding.llContractorTmDetails,
                arrTeamMembers,
                selectTeamMembers
            )
        }
        setListeners()
    }

    private fun setListeners() {
        binding.btnContractAdd.setOnClickListener {
            MultiSelectionDialog(
                this,
                contactContractorLists,
                { it.contractorName },
                selectedContractors
            ) {
                selectedContractors = it.toMutableList()
                setContractorsUI(
                    binding.llContractorDetails,
                    contactContractorLists,
                    selectedContractors
                )
                arrTeamMembers =
                    selectedContractors.flatMap { contactContractorLists[it].teamMembers }
                hmTeamMembers = arrTeamMembers.groupBy { it.contactContractorTeamId }.toMutableMap()
                selectTeamMembers.clear()
                (arrTeamMembers.indices).forEach { selectTeamMembers.add(it) }
                setTeamMembersUI(
                    binding.llContractorTmDetails,
                    arrTeamMembers,
                    selectTeamMembers
                )
            }.show()
        }
        binding.btnContractTeamMemberAdd.setOnClickListener {
            showTeamMembers()
        }
        binding.save.setOnClickListener {
            if (isValidate()) {
                if (binding.stateSpinner.selectedItemPosition == 0) {
                    Toast.makeText(this, "Please Select State", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else if (binding.ProjectHeadNameSpinner.selectedItemPosition == 0) {
                    Toast.makeText(this, "Please Select Project Head Name", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                } else if (selectedContractors.size == 0) {
                    Toast.makeText(this, "Please Select Contractor Details", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                getRequest()
            }
        }
        binding.etProjectHeadName.setOnClickListener {
            showProjectHeadList()
        }
        binding.btnAssociateContractAdd.setOnClickListener {
            if (selectedHeadPosition >= 0) {
                showAssociatedContactDialog(
                    projectHeadReqVoList[selectedHeadPosition].associateContacts,
                    selectedAssociates
                )
            } else {
                Toast.makeText(this, "Please select Project Head name", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun isValidate(): Boolean {
        var isFilled = true
        if (binding.etProjectName.text?.length == 0) {
            binding.etProjectName.requestFocus()
            binding.etProjectName.setError("Please add Project Name")
            isFilled = false
        } else if (binding.etProjectAddress.text?.length == 0) {
            binding.etProjectAddress.requestFocus()
            binding.etProjectAddress.setError("Please add Project Address")
            isFilled = false
        } else if (binding.etPincode.text?.length == 0) {
            binding.etPincode.requestFocus()
            binding.etPincode.setError("Please add Project Address")
            isFilled = false
        } else if (binding.etTeamSizeNo.text?.length == 0) {
            binding.etTeamSizeNo.requestFocus()
            binding.etTeamSizeNo.setError("Please add Team Size")
            isFilled = false
        }
        return isFilled
    }

    private fun showTeamMembers() {
        MultiSelectionDialog(
            this,
            arrTeamMembers,
            { it.teamMemberName },
            selectTeamMembers
        ) {
            selectTeamMembers = it.toMutableList()
            setTeamMembersUI(
                binding.llContractorTmDetails,
                arrTeamMembers,
                selectTeamMembers
            );
        }.show()
    }

    fun setTeamMembersUI(
        llContractorTmDetails: LinearLayout,
        teamMembers: List<CustomerContactResponseVo.TeamMemberResVo>,
        selectTeamMembers: MutableList<Int>
    ) {
        llContractorTmDetails.removeAllViews()
        selectTeamMembers.forEach {
            val obj = teamMembers[it]
            var binding: ContractorTeamMemberDetailsRow1Binding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.contractor_team_member_details_row1,
                null,
                false
            )
            binding.apply {
                tvTeamMemberName.text =
                    Common.setSppanableText("* Name")
                tvTeamMemberMobileNo.text =
                    Common.setSppanableText("* Mobile")
                tvCoAadharNo.text =
                    Common.setSppanableText("* Aadhar Number")
                etTeamMemberName.setText(obj.teamMemberName)
                etTeamMemberMobileNo.setText(obj.teamMemberMobileNo)
                etCoAadharNo.setText(obj.teammemberAadharNumber)
                removelayoutCtm.visibility = View.GONE
            }
            llContractorTmDetails.addView(binding.root)
        }
    }


    private fun setContractorsUI(
        viewGroup: ViewGroup,
        arrContractors: MutableList<CustomerContactResponseVo.ContactContractorList>,
        selectedContractors: MutableList<Int>
    ) {
        viewGroup.removeAllViews()
        selectedContractors.forEach {
            var obj = arrContractors[it]
            val binding: ContractorDetailsRowBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.contractor_details_row,
                null,
                false
            )
            binding.apply {
                tvACName.text = Common.setSppanableText("* Name")
                tvACMobile.text = Common.setSppanableText("* Mobile")
                removelayoutCc.visibility = View.GONE
                addlayoutCc.visibility = View.GONE
                etACName.setText(obj.contractorName)
                etContractorMobile.setText(obj.contractorMobileNo)
            }
            viewGroup.addView(binding.root)
        }
    }

    private fun showProjectHeadList() {
        MultiSelectionDialog(
            this,
            projectHeadReqVoList,
            { it.projectHeadName },
            mutableListOf(selectedHeadPosition),
            true
        ) { arrPositions ->
            selectedHeadPosition = arrPositions[0]
            selectedAssociates.clear()
            setProjectHeadUI(projectHeadReqVoList[selectedHeadPosition])
            showAssociatedContactDialog(
                projectHeadReqVoList[selectedHeadPosition].associateContacts ?: mutableListOf(),
                selectedAssociates
            )
        }.show()
    }


    private fun setProjectHeadUI(
        projectHead: ProjectHeadReqVo,
    ) {
        binding.etProjectHeadName.setText(projectHead.projectHeadName)
        binding.etPHMobile.setText(projectHead.projectHeadMobile)
        binding.etPHDepartment.setText(projectHead.projectHeadDepartment)
        binding.etPHCompanyName.setText(projectHead.companyOrClientName)
    }

    private fun showAssociatedContactDialog(
        list: List<ProjectHeadReqVo.AssociateContact>?,
        selectedAssociates: MutableList<Int>,
    ) {
        MultiSelectionDialog(
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
            viewHolder.removelayoutAc.visibility = View.GONE
            viewHolder.addlayoutAc.visibility = View.GONE
            viewHolder.etACDesignation.setText(obj.contactProjectHeadAssociateContactDesignation)
            viewHolder.etACMobile.setText(obj.contactProjectHeadAssociateContactMobile)
            viewHolder.phNameSpinner.visibility = View.GONE
            viewHolder.etACName.visibility = View.GONE
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
                Constants.RequestNames.ADD_CUSTOMER_PROJECT,
                Constants.RequestNames.EDIT_CUSTOMER_PROJECT -> {

                    val customerProjectResVO: CustomerProjectResVO =
                        Common.getSpecificDataObject<CustomerProjectResVO>(
                            objectResponse.result,
                            CustomerProjectResVO::class.java
                        )
                    if (customerProjectResVO != null) {
                        db.commonDao().insertCustomerProject(customerProjectResVO)
                        ViewCustomerProjectActivity.open(
                            this@CreateCustomerProjectActivity,
                            customerProjectResVO
                        )
                        finish()

                    }
                }
            }
            Common.dismissProgressDialog(progressDialog)
        } catch (e: Exception) {
            Common.disPlayExpection(e, progressDialog)
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

    fun getRequest() {
        val header = projectHeadReqVoList[selectedHeadPosition]
        val ac =
            selectedAssociates.map { header.associateContacts[it].contactProjectheadAssociatecontactId }
        val contractorsLst =
            selectedContractors.map { contactContractorLists[it].contactContractorId }
        val teamMembersId = selectTeamMembers.map { arrTeamMembers[it].contactContractorTeamId }
        println(" header : " + header.contactProjectHeadId)
        println(" assocaiteContacts : $ac")
        println(" contractors : $contractorsLst")
        println(" teamMembersId : $teamMembersId")
        hmTeamMembers.keys.forEach {
            println(" team member id : $it :  contactsid: ${hmTeamMembers[it]?.get(0)?.contactId}")
        }
        var hmContacts =
            hmTeamMembers.keys.groupBy { hmTeamMembers[it]?.get(0)?.contactId }.toMutableMap()
        var alProjectHead = mutableListOf<CustomerProjectReqVO.ProjectHead>()
        val projectHeadList = CustomerProjectReqVO.ProjectHead().apply {
            contactProjectHeadId = header.contactProjectHeadId
            associateContacts = ac.map { id ->
                CustomerProjectReqVO.AssociateContact()
                    .apply { contactProjectheadAssociatecontactId = id }
            }
        }
        alProjectHead.add(projectHeadList)


        var alContractors = mutableListOf<CustomerProjectReqVO.Contractor>()
        hmContacts.forEach {
            println("contact: ${it.key}  : team members : ${it.value}")
            val contractorList = CustomerProjectReqVO.Contractor().apply {
                contactContractorId = it.key //contractor ID
                var teams = mutableListOf<CustomerProjectReqVO.Team>()
                it.value.map {
                    var team = CustomerProjectReqVO.Team()
                    team.contactContractorTeamId = it
                    println("teamid :$it") // team Ids
                    teams.add(team)
                }.toMutableList()
                team = teams
            }
            alContractors.add(contractorList)
        }
        println("final : ${alContractors.toString()}")

        val customerProjectReqVO = CustomerProjectReqVO().apply {
            projectName = binding.etProjectName.text.toString()
            projectAddress = binding.etProjectAddress.text.toString()
            state = binding.stateSpinner.selectedItemId.toString()
            country = binding.etCountry.text.toString()
            pincode = binding.etPincode.text.toString()
            contractorTeamSize = binding.etTeamSizeNo.text.toString()
            contractors = alContractors
            projectHeads = alProjectHead
        }
        println(customerProjectReqVO)
        if (isEdit) {
            customerProjectReqVO.customerProjectId = customerProjectId
            RetrofitRequestController(this).sendRequest(
                Constants.RequestNames.EDIT_CUSTOMER_PROJECT,
                customerProjectReqVO,
                true
            )
            //edit
        } else {
            RetrofitRequestController(this).sendRequest(
                Constants.RequestNames.ADD_CUSTOMER_PROJECT,
                customerProjectReqVO,
                true
            )
        }
    }

    companion object {
        fun open(context: Context, args: Args) {
            context.startActivity(
                Intent(context, CreateCustomerProjectActivity::class.java).apply {
                    putExtra("args", args)
                }
            )
        }
    }

    data class Args(var customerProjectResVO: CustomerProjectResVO) : Serializable
}

private fun CustomerProjectResVO.toProjectReq(): ProjectHeadReqVo {
    return ProjectHeadReqVo().apply {
        this.contactProjectHeadId = this@toProjectReq.projectHead[0].contactProjectHeadId
        this.category = ""
        this.contactProjectHeadId = this@toProjectReq.projectHead[0].contactProjectHeadId
        this.projectHeadName = this@toProjectReq.projectHead[0].projectHeadName
        this.projectHeadMobile = this@toProjectReq.projectHead[0].projectHeadMobile
        this.projectHeadDepartment = this@toProjectReq.projectHead[0].projectHeadDepartment
    }
}