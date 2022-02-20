package com.ncl.nclcustomerservice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.ProjectHeadReqVo
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.databinding.NewContactListBinding

class ProjectHeadAdapter(
    var context: Context,
    var projectHeadReqVoList: List<ProjectHeadReqVo>?,
    var onClick: (ProjectHeadReqVo) -> Unit
) :
    RecyclerView.Adapter<ProjectHeadAdapter.ViewHolder?>() {
    var listener: OnItemClickListener? = null
    var filteredList: List<ProjectHeadReqVo> = projectHeadReqVoList ?: listOf()

    interface OnItemClickListener {
        fun OnItemClick(view: View?, viewItem: View?, position: Int)
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        position: Int
    ): ProjectHeadAdapter.ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                (R.layout.new_contact_list),
                viewGroup,
                false
            )
        )
    }
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ViewHolder {
//        val view: View =
//            LayoutInflater.from(context).inflate(R.layout.new_contact_list, null)
//        return ViewHolder(view)
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)


    override fun getItemCount(): Int {
        return filteredList.size
    }

    inner class ViewHolder(var binding: NewContactListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            if (filteredList != null) {
                binding.itemNameLabel.setText("Company Name")
                binding.cntName.setText(
                    Common.toCamelCase(
                        filteredList[position].projectHeadName
                    )
                )
                binding.cntCategory.setText(
                    Common.toCamelCase(
                        filteredList[position].category
                    )
                )
                binding.cntMobile.setText(filteredList[position].projectHeadMobile)
                binding.cntTeamSize.setText(filteredList[position].companyOrClientName)
                binding.root.setOnClickListener {
                    onClick(filteredList[position])
                }
            }
        }


    }

    fun filter(text: String) {
        filteredList = if (text.isEmpty())
            projectHeadReqVoList ?: listOf()
        else
            projectHeadReqVoList?.filter { it.projectHeadName.contains(text, ignoreCase = true) }
                ?: listOf()
        notifyDataSetChanged()

    }

}