package com.ncl.nclcustomerservice.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.object.SalesCallList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 10/3/2018.
 */

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.MyViewHolder> {
    private int from = 0;
    Activity activity;
    private OnItemClickListener listener;
    List<SalesCallList> salesCallLists;


    public SalesAdapter(Activity activity, List<SalesCallList> salesCallLists) {
        this.activity = activity;
        this.salesCallLists = salesCallLists;
    }

    public SalesAdapter(Activity activity, List<SalesCallList> salesCallLists, int from) {
        this.activity = activity;
        this.salesCallLists = salesCallLists;
        this.from = from;
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
        View itemView = activity.getLayoutInflater().inflate(R.layout.salescall_row, null);
        MyViewHolder leadHolder = new MyViewHolder(itemView);
        return leadHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.item_name_label.setText("Status");
        holder.item_company_label.setText("Company");
        holder.item_lead_source_label.setText("Call Type");
        holder.item_mobile_label.setText("Assigned To");
        holder.lead_owner.setText("Owner");


        holder.item_name.setText(Common.nullChecker(salesCallLists.get(i).status));
        if (salesCallLists.get(i).company != null && salesCallLists.get(i).company.length() > 0) {
            holder.company.setText(Common.nullChecker(salesCallLists.get(i).company));
        } else {
            holder.company.setText(Common.nullChecker(salesCallLists.get(i).new_contact_customer_company_name));
        }
        holder.tvcheckin_time.setText(Common.nullChecker(salesCallLists.get(i).checkin_time));
        holder.tvcheckout_time.setText(Common.nullChecker(salesCallLists.get(i).checkout_time));
        holder.mobile.setText(Common.nullChecker(salesCallLists.get(i).assignedTo));
        holder.leadSource.setText(Common.nullChecker(salesCallLists.get(i).callType));
        holder.tvlead_owner.setText(Common.nullChecker(salesCallLists.get(i).ownerName));
        holder.tvdate.setText(Common.nullChecker(salesCallLists.get(i).created_date_time));
        holder.tvStatusValue.setText(Common.nullChecker(salesCallLists.get(i).status));
        holder.tv_priority_value.setText(Common.nullChecker(salesCallLists.get(i).priority));
        if (from != 0) {
            if (salesCallLists.get(i).geo_status != null && salesCallLists.get(i).geo_status.equalsIgnoreCase("1")) {
                holder.staus_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.bg_circle_green));
            } else {
                holder.staus_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.bg_circle_red));
            }
        }

    }

    @Override
    public int getItemCount() {
        return salesCallLists.size();
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
        @BindView(R.id.staus_icon)
        ImageView staus_icon;
        @BindView(R.id.tvdate)
        TextView tvdate;
        @BindView(R.id.tvlead_owner)
        TextView tvlead_owner;
        @BindView(R.id.lead_owner)
        TextView lead_owner;
        @BindView(R.id.tvStatusValue)
        TextView tvStatusValue;
        @BindView(R.id.tv_priority_value)
        TextView tv_priority_value;
        @BindView(R.id.tvcheckin_time)
        TextView tvcheckin_time;
        @BindView(R.id.tvcheckout_time)
        TextView tvcheckout_time;
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
