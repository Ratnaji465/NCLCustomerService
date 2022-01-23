package com.ncl.nclcustomerservice.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.object.ContractList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 10/8/2018.
 */

public class ContractsAdapter extends RecyclerView.Adapter<ContractsAdapter.MyViewHolder> {
    List<ContractList> contractLists;
    Activity activity;
    private OnItemClickListener listener;

    public ContractsAdapter(Activity activity, List<ContractList> contractLists) {
        this.activity = activity;
        this.contractLists = contractLists;
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
        View itemView = activity.getLayoutInflater().inflate(R.layout.lead_row, null);
        MyViewHolder leadHolder = new MyViewHolder(itemView);
        return leadHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.item_name_label.setText("Customer");
        holder.item_company_label.setText("Acitvated By");
        holder.item_mobile_label.setText("Activated Date");
        holder.item_lead_source_label.setText("Contract Term");
        holder.item_name.setText(Common.nullChecker(contractLists.get(i).customer));
        holder.company.setText(Common.nullChecker(contractLists.get(i).activatedBy));
//        holder.mobile.setText(Common.nullChecker(Common.getDatenewFormat(contractLists.get(i).activatedDate)[0]));
//        holder.leadSource.setText(Common.nullChecker(contractLists.get(i).contractTerm));
    }

    @Override
    public int getItemCount() {
        return contractLists.size();
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
