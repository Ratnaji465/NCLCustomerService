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
        if(dailyReportsResVOList.get(position).getRelatedTo()!=null){
            holder.et_related_to.setText(Common.toCamelCase(dailyReportsResVOList.get(position).getRelatedTo()));
        }
        if(dailyReportsResVOList.get(position).getRelatedTo().equalsIgnoreCase("New Project")){
            holder.llProjectName.setVisibility(View.VISIBLE);
            holder.tvContactName.setText("Contact Name");
            holder.tvCallType.setText("Call Type");
            if(dailyReportsResVOList.get(position).getNewprojectName()!=null){
                holder.et_project_name.setText(Common.toCamelCase(dailyReportsResVOList.get(position).getNewprojectName()));
            }
            if(dailyReportsResVOList.get(position).getNewprojectContactName()!=null){
                holder.et_contact_name.setText(Common.toCamelCase(dailyReportsResVOList.get(position).getNewprojectContactName()));
            }
            holder.et_call_type.setText(dailyReportsResVOList.get(position).getCallType());
        }else if(dailyReportsResVOList.get(position).getRelatedTo().equalsIgnoreCase("Existing Project")){
            holder.llProjectName.setVisibility(View.INVISIBLE);
            holder.tvContactName.setText("Client Project");
            holder.tvCallType.setText("Project Head Name");
            if(dailyReportsResVOList.get(position).getCsCustomerprojectClientprojectDetailsid()!=null){
                holder.et_contact_name.setText(dailyReportsResVOList.get(position).getCsCustomerprojectClientprojectDetailsid());
            }
            if(dailyReportsResVOList.get(position).getProjectHeadName()!=null){
                holder.et_call_type.setText(dailyReportsResVOList.get(position).getProjectHeadName());
            }
        }

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
        @BindView(R.id.et_related_to)
        TextView et_related_to;
        @BindView(R.id.et_project_name)
        TextView et_project_name;
        @BindView(R.id.llProjectName)
        LinearLayout llProjectName;
        @BindView(R.id.tvContactName)
        TextView tvContactName;
        @BindView(R.id.et_contact_name)
        TextView et_contact_name;
        @BindView(R.id.tvCallType)
        TextView tvCallType;
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
