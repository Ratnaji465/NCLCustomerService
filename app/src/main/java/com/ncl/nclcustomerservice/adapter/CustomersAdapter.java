package com.ncl.nclcustomerservice.adapter;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.object.CustomerList;
import com.ncl.nclcustomerservice.object.CustomerUserList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 10/1/2018.
 */

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.MyViewHolder> {

    private final DatabaseHandler db;
    private final String navigationFrom;
    Activity context;
    private OnItemClickListener listener;
    List<CustomerList> customerLists;

    public CustomersAdapter(Activity activity, List<CustomerList> customerLists, DatabaseHandler db, String navigationFrom) {
        this.context = activity;
        this.customerLists = customerLists;
        this.db = db;
        this.navigationFrom = navigationFrom;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, View viewItem, int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = context.getLayoutInflater().inflate(R.layout.lead_row, null);
        MyViewHolder leadHolder = new MyViewHolder(itemView);
        return leadHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.item_name_label.setText("Customer Name");
        holder.item_company_label.setText("Customer Number");
        holder.item_lead_source_label.setText("Customer Owner");
        holder.lead_owner.setText("SAP Code");
        holder.item_name.setText(Common.nullChecker(customerLists.get(i).customerName));
        holder.company.setText(Common.nullChecker(customerLists.get(i).customer_number));
        holder.mobile.setText(Common.nullChecker(customerLists.get(i).phone));
        holder.tvlead_owner.setText(Common.nullChecker(customerLists.get(i).customerSAPCode));

        holder.tvdate.setText(Common.nullChecker(customerLists.get(i).created_date_time));
        if (db != null) {
            List<CustomerUserList> customerUserLists = db.commonDao().getCustomerLineItems(customerLists.get(i).customerId);
            if (customerUserLists != null && customerUserLists.size() > 0) {
                holder.leadSource.setText(Common.nullChecker(customerUserLists.get(0).userName));
            }
        }
        if (navigationFrom != null && navigationFrom.equalsIgnoreCase(Constants.MethodNames.CUSTOMER_LIST_THIRDPARTY)) {
            holder.lead_owner.setText("Contact Name");
            holder.tvlead_owner.setText(Common.nullChecker(customerLists.get(i).contactName));
        }
    }

    @Override
    public int getItemCount() {
        return customerLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_name_label)
        TextView item_name_label;
        @BindView(R.id.item_company_label)
        TextView item_company_label;
        @BindView(R.id.item_mobile_label)
        TextView item_mobile_label;
        @BindView(R.id.item_lead_source_label)
        TextView item_lead_source_label;
        @BindView(R.id.item_name)
        TextView item_name;
        @BindView(R.id.item_company)
        TextView company;
        @BindView(R.id.item_mobile)
        TextView mobile;
        @BindView(R.id.item_lead_source)
        TextView leadSource;
        @BindView(R.id.tvdate)
        TextView tvdate;
        @BindView(R.id.lead_owner)
        TextView lead_owner;
        @BindView(R.id.tvlead_owner)
        TextView tvlead_owner;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(v, itemView, getPosition());
            }
        }
    }
}
