package com.ncl.nclcustomerservice.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.object.UsersTeam;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SupraSoft on 12/19/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {


    private List<UsersTeam> usersTeams;
    private Activity context;
    OnItemClickListener listener;

    public UserAdapter(Activity context, List<UsersTeam> usersTeams) {
        this.context = context;
        this.usersTeams = usersTeams;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, View viewItem, int position);
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = context.getLayoutInflater().inflate(R.layout.user_row, null);
        UserHolder userHolder = new UserHolder(itemView);
        return userHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder userHolder, int i) {
        userHolder.userName.setText(usersTeams.get(i).name);
    }

    @Override
    public int getItemCount() {
        return usersTeams.size();
    }

    class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.user_name)
        TextView userName;

        public UserHolder(@NonNull View itemView) {
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
