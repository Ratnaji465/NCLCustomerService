package com.ncl.nclcustomerservice.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.checkinout.EmpActivityLogsPojo;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.object.Customer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 12/21/2017.
 */

public class CustomerCheckinAdapter extends RecyclerView.Adapter<CustomerCheckinAdapter.ViewHolder> {


    Context context;
    List<EmpActivityLogsPojo> hourlyData;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }



    public CustomerCheckinAdapter(Context context, List<EmpActivityLogsPojo> hourlyData) {
        this.context = context;
        this.hourlyData = hourlyData;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_checkin_list, parent, false);
        CustomerCheckinAdapter.ViewHolder vh = new CustomerCheckinAdapter.ViewHolder(v);
        v.setTag(viewType);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

      /*  holder.every_hour.setText(hourlyData.get(position).hour+" HOUR");
        String totalPass=hourlyData.get(position).totalPass==null ? "00" : hourlyData.get(position).totalPass;

        holder.data.setText(totalPass);*/
        Customer customerInfo = DatabaseHandler.getDatabase(context).commonDao().getCustomerById(hourlyData.get(position).customerId);
      holder.checkin_time.setText(hourlyData.get(position).checkInTime);
      holder.checkout_time.setText(hourlyData.get(position).checkOutTime);
      holder.customercompanyname.setText(customerInfo.customerName);
//      holder.customer_address.setText(customerInfo.address);

    }

    @Override
    public int getItemCount() {
        return hourlyData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.customercompanyname)
        TextView customercompanyname;
        @BindView(R.id.customer_address)
        TextView customer_address;
        @BindView(R.id.checkin_time)
        TextView checkin_time;
        @BindView(R.id.checkout_time)
        TextView checkout_time;

       /* @BindView(R.id.customer_checklinear)
        LinearLayout customer_checklinear;
        @BindView(R.id.customer_linear)
        LinearLayout customer_linear;*/
        public ViewHolder(final View rowView) {
            super(rowView);
            ButterKnife.bind(this,rowView);

            rowView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }

        }
    }
}
