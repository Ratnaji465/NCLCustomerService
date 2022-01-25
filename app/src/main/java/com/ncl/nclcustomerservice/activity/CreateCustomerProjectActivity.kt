package com.ncl.nclcustomerservice.activity

import android.app.ProgressDialog
import android.content.Context
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
        id =  intent.extras!!.getInt("id", 0)
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
            getRequest()
        }
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
                etContractorMobile.setText(obj.contractorName)
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
        var header = projectHeadReqVoList[selectedHeadPosition]
        var ac =
            selectedContractors.map { header.associateContacts[it].contactProjectheadAssociatecontactId }
        var contractors = selectedContractors.map { contactContractorLists[it].contactContractorId }
        var teamMembersId = selectTeamMembers.map { arrTeamMembers[it].contactContractorTeamId }
        println(" header : " + header.contactProjectHeadId)
        println(" assocaiteContacts : " + ac)
        println(" contractors : " + contractors)
        println(" teamMembersId : " + teamMembersId)
    }

}