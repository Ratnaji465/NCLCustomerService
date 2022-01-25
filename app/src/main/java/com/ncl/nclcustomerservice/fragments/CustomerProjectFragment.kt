package com.ncl.nclcustomerservice.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.activity.CreateCustomerProjectActivity
import com.ncl.nclcustomerservice.activity.MainActivity
import com.ncl.nclcustomerservice.activity.ViewCustomerProjectActivity

class CustomerProjectFragment : BaseFragment() {
    private lateinit var filterView: ImageView
    var rvList: RecyclerView? = null
    private lateinit var addView: ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(activity)
            .inflate(R.layout.fragment_customer_project, container, false)
        ((activity as MainActivity?)!!.supportActionBar!!.customView.findViewById<View>(R.id.title_text) as TextView).text =
            "CUSTOMER PROJECT"
        filterView = (activity as MainActivity?)!!.findViewById(R.id.filter_task)
        filterView.setVisibility(View.GONE)
        rvList = view.findViewById(R.id.rvList)
        addView = (activity as MainActivity).findViewById(R.id.add_task)
        addView.setOnClickListener {
            val addIntent = Intent(activity, CreateCustomerProjectActivity::class.java)
            addIntent.putExtra("form_key", "new")
            startActivity(addIntent)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCustomerProjectActivity.open(requireContext())
    }
}