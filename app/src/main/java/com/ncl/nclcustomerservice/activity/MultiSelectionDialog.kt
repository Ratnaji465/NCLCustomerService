package com.ncl.nclcustomerservice.activity

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.commonutils.onTextChange
import com.ncl.nclcustomerservice.commonutils.showOrHide
import com.ncl.nclcustomerservice.databinding.DialogMultiSelectionContactBinding
import com.ncl.nclcustomerservice.databinding.ItemMultiSelectionContactBinding

class MultiSelectionDialog<T>(
    context: Context,
    var list: List<T>,
    var mapper: (T) -> String,
    var selectedPosition: MutableList<Int>? = null,
    var isSingleSelection: Boolean = false,
    var isSearchable: Boolean = true,
    var receivedData: (List<Int>) -> Unit,
) : Dialog(context) {

    private lateinit var adapter: MultiSelectionAdapter<T>
    private lateinit var binding: DialogMultiSelectionContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_multi_selection_contact, null, false
        )
        setContentView(binding.root)
        adapter = MultiSelectionAdapter(
            originalList = list,
            filteredList = list.mapIndexed { index, obj -> Pair(index, obj) }.toList(),
            mapper = mapper,
            isSingleSelection = isSingleSelection
        ) {
            closeDialog()
        }
        adapter.selectedPosition = HashSet<Int>(selectedPosition ?: mutableListOf()).toMutableSet()
        binding.rv.adapter = adapter
        binding.apply {
            llButtons.showOrHide(!isSingleSelection)
            etSearch.showOrHide(isSearchable)
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnOk.setOnClickListener {
                closeDialog()
            }
            etSearch.onTextChange {
                it?.let { adapter.filter(it.toString()) }
            }
        }
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun closeDialog() {
        dismiss()
        receivedData(adapter.selectedPosition.toMutableList())
    }
}


class MultiSelectionAdapter<T>(
    var originalList: List<T>,
    var filteredList: List<Pair<Int, T>>,
    var mapper: (T) -> String,
    var isSingleSelection: Boolean,
    var closeListener: () -> Unit
) :
    RecyclerView.Adapter<MultiSelectionAdapter<T>.ViewHolder>() {
    var selectedPosition = mutableSetOf<Int>()

    inner class ViewHolder(var binding: ItemMultiSelectionContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            var pos = filteredList[position].first
            binding.tv.text = mapper(originalList[pos])
            binding.ivChecked.visibility =
                if (selectedPosition.contains(pos)) View.VISIBLE else View.GONE
            binding.root.setOnClickListener {
                if (isSingleSelection)
                    selectedPosition.clear()
                if (selectedPosition.contains(pos))
                    selectedPosition.remove(pos)
                else
                    selectedPosition.add(pos)
                if (isSingleSelection) {
                    closeListener()
                } else
                    notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_multi_selection_contact,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount() = filteredList.size
    fun filter(filterText: String) {
        filteredList = originalList.mapIndexed { index, obj -> Pair(index, obj) }
        if (filterText.isNotEmpty())
            filteredList =
                filteredList.filter { mapper(it.second).contains(filterText, ignoreCase = true) }
        notifyDataSetChanged()
    }

}