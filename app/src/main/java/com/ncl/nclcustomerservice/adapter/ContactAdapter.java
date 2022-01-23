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
import com.ncl.nclcustomerservice.object.ContactList;
import com.ncl.nclcustomerservice.object.ListContactListCustomer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sowmy on 10/1/2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private int type;
    Context context;
    OnItemClickListener listener;
    List<ContactList> contactLists;
    List<ListContactListCustomer> listContactListCustomers;


    public ContactAdapter(Context context, List<ContactList> contactLists) {
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
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list,null);
        ViewHolder holder =new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {

        if (contactLists != null) {
            holder.name.setText(Common.toCamelCase(contactLists.get(position).firstName));
            holder.email.setText(Common.nullChecker(contactLists.get(position).email));
            holder.mobile.setText(Common.nullChecker(contactLists.get(position).mobile));
            holder.owner.setText(Common.nullChecker(contactLists.get(position).contactOwnerName));
            holder.date.setText(Common.nullChecker(contactLists.get(position).created_date_time));
        }else {

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
        @BindView(R.id.cnt_owner)
        TextView owner;
        @BindView(R.id.cnt_mobile)
        TextView mobile;
        @BindView(R.id.cnt_email)
        TextView email;
        @BindView(R.id.date)
        TextView date;


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
