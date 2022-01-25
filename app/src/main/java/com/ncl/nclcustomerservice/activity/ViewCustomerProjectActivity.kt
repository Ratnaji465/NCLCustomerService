package com.ncl.nclcustomerservice.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.databinding.ActivityViewCustomerprojectBinding
import com.ncl.nclcustomerservice.databinding.ContractorDetailsRowBinding
import com.ncl.nclcustomerservice.databinding.ContractorTeamMemberDetailsRow1Binding

class ViewCustomerProjectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewCustomerprojectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_customerproject)
        setProjectUI()
        setContactUI()
        setAssociateContactUI()
        setContractorUI()
        setTeamMemberUI()
        setClientProjectUI()
    }


    private fun setContactUI() {

    }

    private fun setProjectUI() {
    }

    private fun setAssociateContactUI() {
        binding.apply {
            llAssociateContacts.removeAllViews()
            (0..2).forEach {
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
                    etACName.setText("obj.contractorName")
                    etACName.isEnabled = false
                    etContractorMobile.setText("obj.contractorName")
                    etContractorMobile.isEnabled = false
                }
                llAssociateContacts.addView(binding.root)
            }
        }

    }

    private fun setContractorUI() {
        binding.llContractorDetails.removeAllViews()
        (0..2).forEach {
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
                etACName.setText("obj.contractorName")
                etACName.isEnabled = false
                etContractorMobile.setText("obj.contractorName")
                etContractorMobile.isEnabled = false
            }
            binding.llContractorDetails.addView(bindingRow.root)
        }
    }

    private fun setTeamMemberUI() {
        binding.apply {
            llContractorTmDetails.removeAllViews()
            (0..2).forEach {
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
                    etTeamMemberName.setText("obj.teamMemberName")
                    etTeamMemberName.isEnabled = false
                    etTeamMemberMobileNo.setText("obj.teamMemberMobileNo")
                    etTeamMemberMobileNo.isEnabled = false
                    etCoAadharNo.setText("obj.teammemberAadharNumber")
                    etCoAadharNo.isEnabled = false
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