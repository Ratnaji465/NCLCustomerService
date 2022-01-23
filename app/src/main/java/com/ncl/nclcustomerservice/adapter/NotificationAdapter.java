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
import com.ncl.nclcustomerservice.object.NotificationList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sowmy on 10/16/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context context;
    private OnItemClickListener listener;
    List<NotificationList> notificationLists;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, View itemView, int position);
    }

    public NotificationAdapter(Context context,List<NotificationList> notificationLists) {
        this.context = context;
        this.notificationLists = notificationLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_list,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.notification_type.setText(Common.nullChecker(notificationLists.get(i).notifficationType));
        holder.created_by.setText(Common.nullChecker(notificationLists.get(i).createdBy));
        holder.subject.setText(Common.nullChecker(notificationLists.get(i).subject));
//        holder.notification_linear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(context, MainActivity.class);
//                intent.putExtra("notiffication_type_id", notificationLists.get(i).notifficationTypeId);
//                intent.putExtra("notiffication_type", notificationLists.get(i).notifficationType);
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return notificationLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.subject)
        TextView subject;
        @BindView(R.id.created_by)
        TextView created_by;
        @BindView(R.id.notification_type)
        TextView notification_type;
        @BindView(R.id.notification_linear)
        LinearLayout notification_linear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener!=null){
                listener.onItemClick(view,itemView,getPosition());
            }
        }
    }
}
