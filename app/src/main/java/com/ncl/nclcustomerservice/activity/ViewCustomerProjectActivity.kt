package com.ncl.nclcustomerservice.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.ClientProject
import com.ncl.nclcustomerservice.`object`.CustomerProjectResVO
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.commonutils.getArguments
import com.ncl.nclcustomerservice.databinding.ActivityViewCustomerprojectBinding
import com.ncl.nclcustomerservice.databinding.AssociateContactsRowBindingBinding
import com.ncl.nclcustomerservice.databinding.ContractorDetailsRowBinding
import com.ncl.nclcustomerservice.databinding.ContractorTeamMemberDetailsRow1Binding
import java.io.Serializable


class ViewCustomerProjectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewCustomerprojectBinding
    private lateinit var contactContractorList: CustomerProjectResVO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_customerproject)
        binding.toolbar.titleText.setText("View Customer Project")
        binding.toolbar.backButton.setOnClickListener {
            finish()
        }
        getArguments<Args>()?.customerProjectResVO?.let {
            contactContractorList = it
        }
        if (contactContractorList != null) {
            setContactUI()
            if (contactContractorList.projectHead != null && contactContractorList.projectHead.size > 0) {
                setProjectUI(contactContractorList.projectHead.get(0))
                if (contactContractorList.projectHead.get(0).associateContacts != null && contactContractorList.projectHead.get(
                        0
                    ).associateContacts.size > 0
                ) {
                    setAssociateContactUI(contactContractorList.projectHead.get(0).associateContacts)
                }
            }
            if (contactContractorList.contractors != null && contactContractorList.contractors.size > 0) {
                setContractorUI(contactContractorList.contractors)
            }
            setClientProjectUI()
        }
        binding.btnEdit.setOnClickListener {
            CreateClientProjectActivity.launch(
                startForResult,
                this,
                CreateClientProjectActivity.Args(
                    null,
                    customerProjectId = contactContractorList.customerProjectId
                )
            )
        }
        binding.tvAddClientProject.setOnClickListener {
            CreateClientProjectActivity.open(this)
        }
    }

//    private fun getClientProject(): CreateClientProjectActivity.Args {
//        return CreateClientProjectActivity.Args(
//            ClientProject().apply {
//                products = listOf<ProductAndSubseries>(
//                    ProductAndSubseries(
////                        customerProjectClientProjectProductsId = "114",
////                        divisionMasterId = "3",
////                        divisionName = "Blocks",
////                        divisionSapCode = "30",
////                        productId = "6812",
////                        productName = "santej",
////                        productCode = "P90"
//                    )
//                )
//            }, contactContractorList.customerProjectId
//        )
//    }

    private fun setContactUI() {
        binding.apply {
            etProjectName.setText(contactContractorList.projectName)
            etProjectAddress.setText(contactContractorList.projectAddress)
            etState.setText(contactContractorList.state)
            etPincode.setText(contactContractorList.pincode)
            etTeamSizeNo.setText(contactContractorList.contractorTeamSize)
        }
    }

    private fun setProjectUI(projectHead: CustomerProjectResVO.ProjectHead) {
        binding.apply {
            etPHMobile.setText(projectHead.projectHeadMobile)
            etPHDepartment.setText(projectHead.projectHeadDepartment)
            etHeadName.setText(projectHead.projectHeadName)
            etPHCompanyName.setText(projectHead.projectHeadEmail)
        }
    }

    private fun setAssociateContactUI(associateContact: List<CustomerProjectResVO.AssociateContact>) {
        binding.apply {
            llAssociateContacts.removeAllViews()
            (associateContact.indices).forEach {
                var obj = associateContact[it]
                val binding: AssociateContactsRowBindingBinding = DataBindingUtil.inflate(
                    layoutInflater,
                    R.layout.associate_contacts_row_binding,
                    null,
                    false
                )
                binding.apply {
                    tvACName.text = Common.setSppanableText("* Name")
                    tvACDesignation.text = Common.setSppanableText("* Designation")
                    tvACMobile.text = Common.setSppanableText("* Mobile")
                    removelayoutAc.visibility = View.GONE
                    addlayoutAc.visibility = View.GONE
                    etACName.setText(obj.contactProjectHeadAssociateContactName)
                    etACDesignation.setText(obj.contactProjectHeadAssociateContactDesignation)
                    etACMobile.setText(obj.contactProjectHeadAssociateContactMobile)

                }
                llAssociateContacts.addView(binding.root)
            }
        }

    }

    private fun setContractorUI(contractors: List<CustomerProjectResVO.Contractor>) {
        binding.llContractorDetails.removeAllViews()
        (contractors.indices).forEach {
            var obj = contractors[it]
            val bindingRow: ContractorDetailsRowBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.contractor_details_row,
                null,
                false
            )
            bindingRow.apply {
                tvACName.text = Common.setSppanableText("* Name")
                tvACMobile.text = Common.setSppanableText("* Mobile")
                removelayoutCc.visibility = View.GONE
                addlayoutCc.visibility = View.GONE
                etACName.setText(obj.contractorName)
                etContractorMobile.setText(obj.contractorMobileNo)
            }
            setTeamMemberUI(obj.teamMembers)
            binding.llContractorDetails.addView(bindingRow.root)

        }
    }

    private fun setTeamMemberUI(teamMembers: List<CustomerProjectResVO.TeamMember>) {
        binding.apply {
            llContractorTmDetails.removeAllViews()
            (teamMembers.indices).forEach {
                var obj = teamMembers[it]
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
    }

    private fun setClientProjectUI() {
        binding.apply {
            contactContractorList.clientProjects
            llClientProject.removeAllViews()
            (contactContractorList.clientProjects).forEach { clientProject ->
                var binding: ContractorTeamMemberDetailsRow1Binding = DataBindingUtil.inflate(
                    layoutInflater,
                    R.layout.contractor_team_member_details_row1,
                    null,
                    false
                )
                binding.apply {
                    llTeamMember.visibility = View.GONE
                    tvTeamMemberMobileNo.text =
                        Common.setSppanableText("* OA Number")
                    tvCoAadharNo.text =
                        Common.setSppanableText("* Date")
                    etTeamMemberName.setText("clientProject.oaNumber")
                    etTeamMemberName.isEnabled = false
                    etTeamMemberMobileNo.setText(clientProject.oaNumber)
                    etTeamMemberMobileNo.isEnabled = false
                    etCoAadharNo.setText(clientProject.createdDatetime)
                    etCoAadharNo.isEnabled = false
                    removelayoutCtm.visibility = View.VISIBLE
                }
                binding.removelayoutCtm.setOnClickListener {
                    CreateClientProjectActivity.launch(
                        launcher = startForResult,
                        this@ViewCustomerProjectActivity,
                        CreateClientProjectActivity.Args(
                            clientProject,
                            clientProject.customerProjectId
                        )
                    )
                }
                llClientProject.addView(binding.root)
            }
        }
    }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                intent?.extras?.get("args")?.let {
                    contactContractorList.clientProjects =
                        contactContractorList.clientProjects ?: listOf()
                    var x = it as ClientProject
                    var index =
                        contactContractorList.clientProjects.indexOfFirst { it.csCustomerprojectClientProjectDetailsId == x.csCustomerprojectClientProjectDetailsId }
                    if (index == -1) {
                        contactContractorList.clientProjects.add(x)
                    } else {
                        contactContractorList.clientProjects[index] = x
                        setClientProjectUI()
                    }
                }
                // Handle the Intent
            }
        }

    data class Args(var customerProjectResVO: CustomerProjectResVO) : Serializable

    companion object {
        @JvmStatic
        fun open(context: Context, customerProjectResVO: CustomerProjectResVO) {
            context.startActivity(Intent(context, ViewCustomerProjectActivity::class.java).apply {
                putExtra("args", Args(customerProjectResVO))
            })
        }

    }
}