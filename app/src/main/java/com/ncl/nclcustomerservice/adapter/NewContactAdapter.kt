package com.ncl.nclcustomerservice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.CustomerContactResponseVo
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.databinding.NewContactListBinding

/**
 * Created by sowmy on 10/1/2018.
 */
class NewContactAdapter(
    var context: Context,
    var contactLists: List<CustomerContactResponseVo.ContactContractorList>?,
    var listener: (CustomerContactResponseVo.ContactContractorList) -> Unit
) :
    RecyclerView.Adapter<NewContactAdapter.ViewHolder?>() {
    var filteredList: List<CustomerContactResponseVo.ContactContractorList> =
        contactLists ?: listOf()

    interface OnItemClickListener {
        fun OnItemClick(view: View?, viewItem: View?, position: Int)
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        position: Int
    ): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                (R.layout.new_contact_list),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)

    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    inner class ViewHolder(var binding: NewContactListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            if (filteredList != null) {
                binding.cntName.setText(
                    Common.toCamelCase(
                        filteredList[position].contractorName
                    )
                )
                binding.cntCategory.setText(
                    Common.nullChecker(
                        filteredList[position].category
                    )
                )
                binding.cntMobile.setText(
                    Common.nullChecker(
                        filteredList[position].contractorMobileNo
                    )
                )
                binding.cntTeamSize.setText(
                    Common.nullChecker(
                        filteredList[position].contractorTeamSize
                    )
                )
                binding.root.setOnClickListener {
                    listener(filteredList[position])
                }
            }

        }
    }

    fun filter(text: String) {
        filteredList = if (text.isEmpty())
            contactLists ?: listOf()
        else
            contactLists?.filter { it.contractorName.contains(text, ignoreCase = true) } ?: listOf()
        notifyDataSetChanged()
    }

}