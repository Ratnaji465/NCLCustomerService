package com.ncl.nclcustomerservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.object.ComplaintsInsertReqVo;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.ViewHolder>{
    Context context;
    ComplaintsAdapter.OnItemClickListener listener;
    List<ComplaintsInsertReqVo> complaintsInsertReqVos;
    public ComplaintsAdapter(Context context, List<ComplaintsInsertReqVo> complaintsInsertReqVos) {
        this.context = context;
        this.complaintsInsertReqVos = complaintsInsertReqVos;

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
        View view = LayoutInflater.from(context).inflate(R.layout.complaint_list,null);
        return new ComplaintsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_complaint_date.setText(complaintsInsertReqVos.get(position).complaintDate);
        holder.tv_client_code.setText(complaintsInsertReqVos.get(position).clientCode);
        holder.tv_oa_no.setText(complaintsInsertReqVos.get(position).oaNumber);
        holder.tv_area_office.setText(complaintsInsertReqVos.get(position).areaOffice);
        holder.tv_client_name.setText(complaintsInsertReqVos.get(position).clientName);
        holder.tv_project_type.setText(complaintsInsertReqVos.get(position).projectTypeName);
    }
    @Override
    public int getItemCount() {
        return complaintsInsertReqVos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_complaint_date)
        TextView tv_complaint_date;
        @BindView(R.id.tv_client_code)
        TextView tv_client_code;
        @BindView(R.id.tv_oa_no)
        TextView tv_oa_no;
        @BindView(R.id.tv_area_office)
        TextView tv_area_office;
        @BindView(R.id.tv_client_name)
        TextView tv_client_name;
        @BindView(R.id.tv_project_type)
        TextView tv_project_type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.OnItemClick(v, itemView, getPosition());
            }
        }
    }
}
