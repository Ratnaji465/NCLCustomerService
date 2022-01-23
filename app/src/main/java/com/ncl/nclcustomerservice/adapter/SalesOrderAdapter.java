package com.ncl.nclcustomerservice.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.object.SalesOrderList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SupraSoft on 11/2/2018.
 */

public class SalesOrderAdapter extends RecyclerView.Adapter<SalesOrderAdapter.ViewHolder> {
    private final String navigationFrom;
    Context activity;
    List<SalesOrderList> salesOrderLists;
    OnItemClickListener listener;

    public SalesOrderAdapter(Context activity, List<SalesOrderList> salesOrderLists, String navigationFrom) {
        this.activity = activity;
        this.salesOrderLists = salesOrderLists;
        this.navigationFrom = navigationFrom;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, View viewItem, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.sales_order_list, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.salesorder_list_customername.setText(Common.nullChecker(salesOrderLists.get(position).customer));
        holder.salesorder_list_ordernumber.setText(Common.nullChecker(salesOrderLists.get(position).salesOrderNumber));
        holder.salesorder_list_ordertype.setText(Common.nullChecker(salesOrderLists.get(position).orderType));
        holder.salesorder_list_totalamount.setText(Common.nullChecker(salesOrderLists.get(position).total));
        holder.date.setText(Common.nullChecker(salesOrderLists.get(position).created_date_time));
        holder.tv_owner_name.setText(Common.nullChecker(salesOrderLists.get(position).owner_name));
    }

    @Override
    public int getItemCount() {
        return salesOrderLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.salesorder_linear)
        LinearLayout salesorder_linear;
        @BindView(R.id.salesorder_list_customername)
        TextView salesorder_list_customername;
        @BindView(R.id.salesorder_list_ordernumber)
        TextView salesorder_list_ordernumber;
        @BindView(R.id.salesorder_list_ordertype)
        TextView salesorder_list_ordertype;
        @BindView(R.id.salesorder_list_totalamount)
        TextView salesorder_list_totalamount;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.tv_owner_name)
        TextView tv_owner_name;
 @BindView(R.id.item_lead_source_label)
        TextView item_lead_source_label;

        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            if (navigationFrom!=null && navigationFrom.equalsIgnoreCase(Constants.MethodNames.SALES_ODERS_THIRDPARTY)){
                salesorder_list_totalamount.setVisibility(View.GONE);
                item_lead_source_label.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.OnItemClick(v, itemView, getPosition());
            }
        }
    }
}
