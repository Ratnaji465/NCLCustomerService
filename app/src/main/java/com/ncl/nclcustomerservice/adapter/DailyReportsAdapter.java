package com.ncl.nclcustomerservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.object.DailyReportsAddVO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyReportsAdapter extends RecyclerView.Adapter<DailyReportsAdapter.ViewHolder>{
    Context context;
    OnItemClickListener listener;
    List<DailyReportsAddVO> dailyReportsResVOList;
    public DailyReportsAdapter(Context context, List<DailyReportsAddVO> dailyReportsResVOList) {
        this.context = context;
        this.dailyReportsResVOList = dailyReportsResVOList;

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
        View view = LayoutInflater.from(context).inflate(R.layout.daily_reports_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(dailyReportsResVOList.get(position).getRelatedTo()!=null)
        holder.project_name.setText(Common.toCamelCase(dailyReportsResVOList.get(position).getRelatedTo()));
        if(dailyReportsResVOList.get(position).getContractorName()!=null){
            holder.et_address.setText(Common.toCamelCase(dailyReportsResVOList.get(position).getContractorName()));
        }

        if(dailyReportsResVOList.get(position).getUserName()!=null)
        holder.et_project_owner.setText(Common.toCamelCase(dailyReportsResVOList.get(position).getUserName()));
        holder.et_call_type.setText(dailyReportsResVOList.get(position).getCallType());
        if(dailyReportsResVOList.get(position).getCheckInTime()!=null){
            holder.et_checkin.setText(dailyReportsResVOList.get(position).getCheckInTime());
        }else {
            holder.et_checkin.setText("--");
        }
        if(dailyReportsResVOList.get(position).getCheckoutTime()!=null){
            holder.et_checkout.setText(dailyReportsResVOList.get(position).getCheckoutTime());
        }else {
            holder.et_checkout.setText("--");
        }


    }

    @Override
    public int getItemCount() {
        return dailyReportsResVOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.et_project_name)
        TextView project_name;
        @BindView(R.id.et_address)
        TextView et_address;
        @BindView(R.id.et_project_owner)
        TextView et_project_owner;
        @BindView(R.id.et_call_type)
        TextView et_call_type;
        @BindView(R.id.et_checkin)
        TextView et_checkin;
        @BindView(R.id.et_checkout)
        TextView et_checkout;



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
