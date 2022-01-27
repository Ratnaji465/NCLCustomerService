package com.ncl.nclcustomerservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.object.CustomerContactResponseVo;
import com.ncl.nclcustomerservice.object.CustomerProjectResVO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerProjectAdapter extends RecyclerView.Adapter<CustomerProjectAdapter.ViewHolder>{
    Context context;
    OnItemClickListener listener;
    List<CustomerProjectResVO> customerProjectResVOS;
    public CustomerProjectAdapter(Context context, List<CustomerProjectResVO> customerProjectResVOS) {
        this.context = context;
        this.customerProjectResVOS = customerProjectResVOS;

    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, View viewItem, int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_customer_project_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.project_name.setText(Common.toCamelCase(customerProjectResVOS.get(position).projectName));
        holder.et_address.setText(Common.toCamelCase(customerProjectResVOS.get(position).projectAddress));
        holder.et_project_owner.setText("Venkatesh Kulkarni");
        holder.et_date.setText(customerProjectResVOS.get(position).createdDatetime);
    }

    @Override
    public int getItemCount() {
        return customerProjectResVOS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.et_project_name)
        TextView project_name;
        @BindView(R.id.et_address)
        TextView et_address;
        @BindView(R.id.et_project_owner)
        TextView et_project_owner;
        @BindView(R.id.et_date)
        TextView et_date;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.OnItemClick(view, itemView, getPosition());
            }
        }
    }
}
