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
import com.ncl.nclcustomerservice.databinding.DialogMultiSelectionContactBinding
import com.ncl.nclcustomerservice.databinding.ItemMultiSelectionContactBinding

class MultiSelectionDialog<T>(
    context: Context,
    var list: List<T>,
    var mapper: (T) -> String,
    var selectedPosition: MutableList<Int>? = null,
    var receivedData: (List<Int>) -> Unit
) :
    Dialog(context) {

    private lateinit var adapter: MultiSelectionAdapter<T>
    private lateinit var binding: DialogMultiSelectionContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_multi_selection_contact, null, false
        )
        setContentView(binding.root)
        adapter = MultiSelectionAdapter(list, mapper)
        adapter.selectedPosition = HashSet<Int>(selectedPosition ?: mutableListOf()).toMutableSet()
        binding.rv.adapter = adapter
        binding.apply {
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnOk.setOnClickListener {
                dismiss()
                receivedData(adapter.selectedPosition.toMutableList())
            }
        }
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}

class MultiSelectionAdapter<T>(var list: List<T>, var mapper: (T) -> String) :
    RecyclerView.Adapter<MultiSelectionAdapter<T>.ViewHolder>() {
    var selectedPosition = mutableSetOf<Int>()

    inner class ViewHolder(var binding: ItemMultiSelectionContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.tv.text = mapper(list[position])
            binding.ivChecked.visibility =
                if (selectedPosition.contains(position)) View.VISIBLE else View.GONE
            binding.root.setOnClickListener {
                notifyDataSetChanged()
                if (selectedPosition.contains(position))
                    selectedPosition.remove(position)
                else
                    selectedPosition.add(position)
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

    override fun getItemCount() = list.size

}