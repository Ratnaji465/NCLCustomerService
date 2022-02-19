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
import com.ncl.nclcustomerservice.object.CustomerProjectResVO;
import com.ncl.nclcustomerservice.object.DailyReportsAddVO;
import com.ncl.nclcustomerservice.object.DailyReportsResListVO;

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
        View view = LayoutInflater.from(context).inflate(R.layout.new_customer_project_list,null);
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
        holder.et_date.setText(dailyReportsResVOList.get(position).getCallDate());
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
        @BindView(R.id.et_date)
        TextView et_date;


        @BindView(R.id.item_name_label)
        TextView item_name_label;
        @BindView(R.id.item_category_label)
        TextView item_category_label;
//        @BindView(R.id.item_mobile_label)
//        TextView item_mobile_label;
//        @BindView(R.id.item_team_size_label)
//        TextView item_team_size_label;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            item_name_label.setText("Related To");
            item_category_label.setText("Name");
//            item_mobile_label.setText("");
//            item_team_size_label.setText("");
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
