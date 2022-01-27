package com.ncl.nclcustomerservice.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.CustomerProjectResVO
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.databinding.ActivityViewCustomerprojectBinding
import com.ncl.nclcustomerservice.databinding.AssociateContactsRowBindingBinding
import com.ncl.nclcustomerservice.databinding.ContractorDetailsRowBinding
import com.ncl.nclcustomerservice.databinding.ContractorTeamMemberDetailsRow1Binding

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
        contactContractorList =
            (intent.getSerializableExtra("CustomerProjectList") as CustomerProjectResVO?)!!
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
    }


    private fun setContactUI() {
        binding.etProjectName.setText(contactContractorList.projectName)
        binding.etProjectAddress.setText(contactContractorList.projectAddress)
        binding.etState.setText(contactContractorList.state)
        binding.etPincode.setText(contactContractorList.pincode)
    }

    private fun setProjectUI(projectHead: CustomerProjectResVO.ProjectHead) {
        binding.etHeadName.setText(projectHead.projectHeadName)
        binding.etPHMobile.setText(projectHead.projectHeadMobile)
        binding.etPHDepartment.setText(projectHead.projectHeadDepartment)
        binding.etPHCompanyName.setText(projectHead.projectHeadEmail)
    }

    private fun setAssociateContactUI(associateContact: List<CustomerProjectResVO.AssociateContact>) {
        binding.apply {
            llAssociateContacts.removeAllViews()
            (0..associateContact.size - 1).forEach {
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
        (0..contractors.size - 1).forEach {
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
            (0..teamMembers.size - 1).forEach {
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
            llClientProject.removeAllViews()
            (0..2).forEach {
                var binding: ContractorTeamMemberDetailsRow1Binding = DataBindingUtil.inflate(
                    layoutInflater,
                    R.layout.contractor_team_member_details_row1,
                    null,
                    false
                )
                binding.apply {
                    tvTeamMemberName.text =
                        Common.setSppanableText("* SNo")
                    tvTeamMemberMobileNo.text =
                        Common.setSppanableText("* QA Number")
                    tvCoAadharNo.text =
                        Common.setSppanableText("* Date")
                    etTeamMemberName.setText("obj.teamMemberName")
                    etTeamMemberName.isEnabled = false
                    etTeamMemberMobileNo.setText("obj.teamMemberMobileNo")
                    etTeamMemberMobileNo.isEnabled = false
                    etCoAadharNo.setText("obj.teammemberAadharNumber")
                    etCoAadharNo.isEnabled = false
                    removelayoutCtm.visibility = View.GONE
                }
                llClientProject.addView(binding.root)
            }
        }

    }


    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, ViewCustomerProjectActivity::class.java))
        }
    }
}