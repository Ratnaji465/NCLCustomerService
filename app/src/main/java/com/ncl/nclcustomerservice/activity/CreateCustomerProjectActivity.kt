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
import com.ncl.nclcustomerservice.database.DatabaseHandler
import com.ncl.nclcustomerservice.databinding.ActivityCreateCustomerprojectBinding
import com.ncl.nclcustomerservice.databinding.AssociateContactsRowBindingBinding
import com.ncl.nclcustomerservice.databinding.ContractorDetailsRowBinding
import com.ncl.nclcustomerservice.databinding.ContractorTeamMemberDetailsRow1Binding
import com.ncl.nclcustomerservice.network.RetrofitRequestController
import com.ncl.nclcustomerservice.network.RetrofitResponseListener
import java.util.*

class CreateCustomerProjectActivity : NetworkChangeListenerActivity(), RetrofitResponseListener {

    var projectHeadReqVoList: MutableList<ProjectHeadReqVo> = ArrayList()
    private var selectedHeadPosition = 0
    private var selectedAssociates = mutableListOf<Int>()
    lateinit var contactContractorLists: MutableList<CustomerContactResponseVo.ContactContractorList>
    private var selectedContractors = mutableListOf<Int>()
    private var arrTeamMembers: List<CustomerContactResponseVo.TeamMemberResVo> = mutableListOf()
    private var selectTeamMembers = mutableListOf<Int>()
    var hmTeamMembers = mutableMapOf<String, List<CustomerContactResponseVo.TeamMemberResVo>>()
    private var arrAssociate: List<ProjectHeadReqVo.AssociateContact> = mutableListOf()
    var hmAssociate = mutableMapOf<String, List<ProjectHeadReqVo.AssociateContact>>()
    private lateinit var binding: ActivityCreateCustomerprojectBinding


    var form_type: String? = null
    var id = 0

    lateinit var db: DatabaseHandler

    lateinit var statesLists: MutableList<StatesList>
    var stateId: String? = null

    override fun onInternetConnected() {}
    override fun onInternetDisconnected() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_customerproject)
        Common.setupUI(binding.root, this)
        form_type = intent.extras!!.getString("form_key", "")
        id = intent.extras!!.getInt("id", 0)

        db = DatabaseHandler.getDatabase(this)
        binding.toolbar.titleText.text = "CUSTOMER PROJECT INFORMATION"
        binding.tvProjectName.text = Common.setSppanableText("* Project Name")
        binding.tvProjectAddress.text = Common.setSppanableText("* Project Address")
        binding.tvState.text = Common.setSppanableText("* State")
        binding.tvCountry.text = Common.setSppanableText("* Country")
        binding.tvPinCode.text = Common.setSppanableText("* Pincode")
        binding.tvProjectHeadName.text = Common.setSppanableText("* Project Head Name")
        binding.tvPHMobile.text = Common.setSppanableText("* Mobile")
        binding.tvPHDepartment.text = Common.setSppanableText("* Department")
        binding.tvPHCompanyName.text = Common.setSppanableText("* Company/Client Name")
        binding.tvTeamSizeNo.text = Common.setSppanableText("* Team Size")

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
//        arrAssociate =
//                projectHeadReqVoList.flatMap { projectHeadReqVoList[it].contactProjectHeadId }
//        hmTeamMembers = arrTeamMembers.groupBy { it.contactContractorTeamId }.toMutableMap()
        contactContractorLists = db.commonDao().allCustomerContactList
        val dropDownDataReqVo = DropDownDataReqVo().apply {
            usersList = "users_list"
            customerList = "customer_list"
            teamId = Common.getTeamUserIdFromSP(this@CreateCustomerProjectActivity)
        }
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
        RetrofitRequestController(this).sendRequest(
            Constants.RequestNames.DROP_DOWN_LIST,
            dropDownDataReqVo,
            true
        )
        binding.save.setOnClickListener {
//            if(isValidate()){
//                if(binding.stateSpinner.selectedItemPosition==0){
//                    Toast.makeText(this, "Please Select State", Toast.LENGTH_SHORT).show()
//                }else if(binding.ProjectHeadNameSpinner.selectedItemPosition==0){
//                    Toast.makeText(this, "Please Select Project Head Name", Toast.LENGTH_SHORT).show()
//                }else if(selectedContractors.size==0){
//                    Toast.makeText(this, "Please Select Contractor Details", Toast.LENGTH_SHORT).show()
//                }
            getRequest()
//            }
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

    private fun setTeamMembersUI(
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
                Constants.RequestNames.ADD_CUSTOMER_PROJECT -> {
                    val customerProjectResVO: CustomerProjectResVO =
                        Common.getSpecificDataObject<CustomerProjectResVO>(
                            objectResponse.result,
                            CustomerProjectResVO::class.java
                        )
                    if (customerProjectResVO != null) {
                        db.commonDao().insertCustomerProject(customerProjectResVO)
                        val intent = Intent(this@CreateCustomerProjectActivity,
                            NewContactViewActivity::class.java
                        )
                        intent.putExtra("CustomerProjectList", customerProjectResVO)
                        startActivity(intent)
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
        println(" assocaiteContacts : " + ac)
        println(" contractors : " + contractorsLst)
        println(" teamMembersId : " + teamMembersId)
        hmTeamMembers.keys.forEach {
            println(" team member id : $it :  contactsid: ${hmTeamMembers[it]?.get(0)?.contactId}")
        }
        var hmContacts =
            hmTeamMembers.keys.groupBy { hmTeamMembers[it]?.get(0)?.contactId }.toMutableMap()
        var alProjectHead = mutableListOf<CustomerProjectReqVO.ProjectHead>()

        hmAssociate.forEach{
            val projectHeadList = CustomerProjectReqVO.ProjectHead().apply {
                contactProjectHeadId = Integer.parseInt(header.contactProjectHeadId) //contractor ID
                var associateContactsList = mutableListOf<CustomerProjectReqVO.AssociateContact>()
                it.value.map {
                    var associate = CustomerProjectReqVO.AssociateContact()
                    associate.contactProjectheadAssociatecontactId = it.contactProjectheadAssociatecontactId
                    println("teamid :$it") // team Ids
                    associateContactsList.add(associate)
                }.toMutableList()
                associateContacts = associateContactsList
            }
            alProjectHead.add(projectHeadList)
        }


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
            contractors=alContractors
            projectHeads=alProjectHead
        }

        RetrofitRequestController(this).sendRequest(
            Constants.RequestNames.ADD_CUSTOMER_PROJECT,
            customerProjectReqVO,
            true
        )

    }

}