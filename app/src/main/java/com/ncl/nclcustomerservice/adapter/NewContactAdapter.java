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
import com.ncl.nclcustomerservice.object.ContactList;
import com.ncl.nclcustomerservice.object.CustomerContactResponseVo;
import com.ncl.nclcustomerservice.object.ListContactListCustomer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sowmy on 10/1/2018.
 */

public class NewContactAdapter extends RecyclerView.Adapter<NewContactAdapter.ViewHolder> {
    Context context;
    OnItemClickListener listener;
    List<CustomerContactResponseVo.ContactContractorList> contactLists;


    public NewContactAdapter(Context context, List<CustomerContactResponseVo.ContactContractorList> contactLists) {
        this.context = context;
        this.contactLists = contactLists;
    }



    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, View viewItem, int position);
    }

    @NonNull
    @Override
    public NewContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_contact_list,null);
        ViewHolder holder =new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewContactAdapter.ViewHolder holder, int position) {

        if (contactLists != null) {
            holder.name.setText(Common.toCamelCase(contactLists.get(position).contractorName));
            holder.category.setText(Common.nullChecker(contactLists.get(position).category));
            holder.mobile.setText(Common.nullChecker(contactLists.get(position).contractorMobileNo));
            holder.team_size.setText(Common.nullChecker(contactLists.get(position).contractorTeamSize));
        }
    }

    @Override
    public int getItemCount() {

        return contactLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
