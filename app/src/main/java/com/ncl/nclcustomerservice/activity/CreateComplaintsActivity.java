package com.ncl.nclcustomerservice.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateComplaintsActivity extends NetworkChangeListenerActivity implements RetrofitResponseListener {

    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.back_button)
    ImageView back;
    @BindView(R.id.parent)
    View parent;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    String form="";
    DatabaseHandler db;
    String profileName;
    @BindView(R.id.by_supervisor_layout)
    LinearLayout by_supervisor_layout;
    @BindView(R.id.complaint_receiver_layout)
    LinearLayout complaint_receiver_layout;
    @BindView(R.id.commercial_department_layout)
    LinearLayout commercial_department_layout;
    @BindView(R.id.final_remarks_layout)
    LinearLayout final_remarks_layout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaints);
        ButterKnife.bind(this);
        Common.setupUI(parent, this);
        form = getIntent().getStringExtra("complaint_form");
        db = DatabaseHandler.getDatabase(this);
        profileName = Common.getProfileName(this);
        title_text.setText("Add Complaints");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addSupervisorRemark(getLayoutInflater().inflate(R.layout.add_lead_note,null));
        addComplaintRemark(getLayoutInflater().inflate(R.layout.add_lead_note,null));
        addCommercialRemark(getLayoutInflater().inflate(R.layout.add_lead_note,null));
        addFinalRemark(getLayoutInflater().inflate(R.layout.add_lead_note,null));
    }

    private void addFinalRemark(View view) {
        final_remarks_layout.addView(view);
        ViewHolderRemarks viewHolder = new ViewHolderRemarks(view);
        viewHolder.etDate.setText(Common.getCurrentDate());
        if (final_remarks_layout.getChildCount() > 1) {
            viewHolder.removeLayout.setVisibility(View.VISIBLE);
        } else {
            viewHolder.removeLayout.setVisibility(View.GONE);
        }
        viewHolder.addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFinalRemark(getLayoutInflater().inflate(R.layout.add_lead_note,null));
            }
        });
        viewHolder.removeLayout.setTag(view);
        viewHolder.removeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final_remarks_layout.removeView((View) view.getTag());

            }
        });
    }

    private void addCommercialRemark(View view) {
        commercial_department_layout.addView(view);
        ViewHolderRemarks viewHolder = new ViewHolderRemarks(view);
        viewHolder.etDate.setText(Common.getCurrentDate());
        if (commercial_department_layout.getChildCount() > 1) {
            viewHolder.removeLayout.setVisibility(View.VISIBLE);
        } else {
            viewHolder.removeLayout.setVisibility(View.GONE);
        }
        viewHolder.addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCommercialRemark(getLayoutInflater().inflate(R.layout.add_lead_note,null));
            }
        });
        viewHolder.removeLayout.setTag(view);
        viewHolder.removeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commercial_department_layout.removeView((View) view.getTag());

            }
        });
    }

    private void addComplaintRemark(View view) {
        complaint_receiver_layout.addView(view);
        ViewHolderRemarks viewHolder = new ViewHolderRemarks(view);
        viewHolder.etDate.setText(Common.getCurrentDate());
        if (complaint_receiver_layout.getChildCount() > 1) {
            viewHolder.removeLayout.setVisibility(View.VISIBLE);
        } else {
            viewHolder.removeLayout.setVisibility(View.GONE);
        }
        viewHolder.addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComplaintRemark(getLayoutInflater().inflate(R.layout.add_lead_note,null));
            }
        });
        viewHolder.removeLayout.setTag(view);
        viewHolder.removeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complaint_receiver_layout.removeView((View) view.getTag());

            }
        });
    }

    private void addSupervisorRemark(View view) {
        by_supervisor_layout.addView(view);
        ViewHolderRemarks viewHolder = new ViewHolderRemarks(view);
        viewHolder.etDate.setText(Common.getCurrentDate());
        if (by_supervisor_layout.getChildCount() > 1) {
            viewHolder.removeLayout.setVisibility(View.VISIBLE);
        } else {
            viewHolder.removeLayout.setVisibility(View.GONE);
        }
        viewHolder.addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSupervisorRemark(getLayoutInflater().inflate(R.layout.add_lead_note,null));
            }
        });
        viewHolder.removeLayout.setTag(view);
        viewHolder.removeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                by_supervisor_layout.removeView((View) view.getTag());

            }
        });
    }

    @Override
    protected void onInternetConnected() {

    }

    @Override
    protected void onInternetDisconnected() {

    }

    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {

    }

    static class ViewHolderRemarks {
        @BindView(R.id.etDate)
        TextView etDate;
        @BindView(R.id.etRemarks)
        EditText etRemarks;
        @BindView(R.id.etWorkDoneId)
        EditText etWorkDoneId;
        @BindView(R.id.addlayout)
        LinearLayout addLayout;
        @BindView(R.id.removelayout)
        LinearLayout removeLayout;
        public ViewHolderRemarks(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
