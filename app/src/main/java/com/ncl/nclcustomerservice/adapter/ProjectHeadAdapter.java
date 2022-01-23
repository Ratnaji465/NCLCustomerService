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
import com.ncl.nclcustomerservice.object.ProjectHeadReqVo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectHeadAdapter extends RecyclerView.Adapter<ProjectHeadAdapter.ViewHolder>{
    Context context;
    OnItemClickListener listener;
    List<ProjectHeadReqVo> projectHeadReqVoList;

    public ProjectHeadAdapter(Context context, List<ProjectHeadReqVo> projectHeadReqVoList) {
        this.context = context;
        this.projectHeadReqVoList = projectHeadReqVoList;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, View viewItem, int position);
    }

    @NonNull
    @Override
    public ProjectHeadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_contact_list,null);
        ProjectHeadAdapter.ViewHolder holder =new ProjectHeadAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectHeadAdapter.ViewHolder holder, int position) {
            if(projectHeadReqVoList!=null){
                holder.itemTeamSize.setText("Company Name");
                holder.name.setText(Common.toCamelCase(projectHeadReqVoList.get(position).projectHeadName));
                holder.category.setText(Common.toCamelCase(projectHeadReqVoList.get(position).category));
                holder.mobile.setText(projectHeadReqVoList.get(position).projectHeadMobile);
                holder.team_size.setText(projectHeadReqVoList.get(position).companyOrClientName);

            }
    }

    @Override
    public int getItemCount() {
        return projectHeadReqVoList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.linear)
        LinearLayout linear;
        @BindView(R.id.cnt_name)
        TextView name;
        @BindView(R.id.cnt_category)
        TextView category;
        @BindView(R.id.cnt_mobile)
        TextView mobile;
        @BindView(R.id.cnt_team_size)
        TextView team_size;
        @BindView(R.id.item_team_size_label)
        TextView itemTeamSize;
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
