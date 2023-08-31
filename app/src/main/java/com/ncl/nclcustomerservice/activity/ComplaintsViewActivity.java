package com.ncl.nclcustomerservice.activity;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.customviews.CustomTextView;
import com.ncl.nclcustomerservice.network.RetrofitRequestController;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.ComplaintsInsertReqVo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComplaintsViewActivity extends AppCompatActivity implements RetrofitResponseListener {
    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.back_button)
    ImageView back;
    @BindView(R.id.tv_complaint_date)
    CustomTextView tv_complaint_date;
    @BindView(R.id.tv_client_code)
    CustomTextView tv_client_code;
    @BindView(R.id.tv_oa_number)
    CustomTextView tv_oa_number;
    @BindView(R.id.tv_area_office)
    CustomTextView tv_area_office;
    @BindView(R.id.tv_client_name)
    CustomTextView tv_client_name;
    @BindView(R.id.tv_project_type)
    CustomTextView tv_project_type;
    @BindView(R.id.tv_product)
    CustomTextView tv_product;
    @BindView(R.id.tv_marketing_officer_name)
    CustomTextView tv_marketing_officer_name;
    @BindView(R.id.tv_fab_unit)
    CustomTextView tv_fab_unit;
    @BindView(R.id.tv_nature_of_complaint)
    CustomTextView tv_nature_of_complaint;
    @BindView(R.id.tv_closing_date)
    CustomTextView tv_closing_date;
    @BindView(R.id.tv_no_of_days_took)
    CustomTextView tv_no_of_days_took;
    @BindView(R.id.tv_complaint_status)
    CustomTextView tv_complaint_status;
    @BindView(R.id.ll_image_preview1)
    LinearLayout ll_image_preview1;
    @BindView(R.id.iv_file1_preview)
    ImageView iv_file1_preview;
    @BindView(R.id.ll_image_preview2)
    LinearLayout ll_image_preview2;
    @BindView(R.id.iv_file2_preview)
    ImageView iv_file2_preview;
    @BindView(R.id.ll_image_preview3)
    LinearLayout ll_image_preview3;
    @BindView(R.id.iv_file3_preview)
    ImageView iv_file3_preview;
    @BindView(R.id.ll_image_preview4)
    LinearLayout ll_image_preview4;
    @BindView(R.id.iv_file4_preview)
    ImageView iv_file4_preview;
    @BindView(R.id.ll_supervisor_remarks)
    LinearLayout ll_supervisor_remarks;
    @BindView(R.id.ll_complaint_receiver_remarks)
    LinearLayout ll_complaint_receiver_remarks;
    @BindView(R.id.ll_commercial_remarks)
    LinearLayout ll_commercial_remarks;
    @BindView(R.id.ll_final_remarks)
    LinearLayout ll_final_remarks;

    @BindView(R.id.edit_linear)
    LinearLayout edit_linear;

    String complaintRegisterId;
    ComplaintsInsertReqVo complaintsInsertReqVo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_view);
        ButterKnife.bind(this);
        title_text.setText("Edit Complaint");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        complaintRegisterId = getIntent().getStringExtra("complaint_register_id");
        loadViewApi();
        edit_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComplaintsViewActivity.this, CreateComplaintsActivity.class);
                intent.putExtra("complaint_form", "edit");
                intent.putExtra("complaints", complaintsInsertReqVo);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadViewApi() {
        ComplaintsInsertReqVo complaintsInsertReqVo2 = new ComplaintsInsertReqVo();
        complaintsInsertReqVo2.csComplaintRegisterId = complaintRegisterId;
        new RetrofitRequestController(ComplaintsViewActivity.this).sendRequest(Constants.RequestNames.COMPLAINT_REGISTER_VIEW, complaintsInsertReqVo2, true);
    }

    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {
        try {
            switch (objectRequest.requestname) {
                case Constants.RequestNames.COMPLAINT_REGISTER_VIEW:
                    if (objectResponse.result != null) {
                        complaintsInsertReqVo = Common.getSpecificDataObject(objectResponse.result, ComplaintsInsertReqVo.class);
                        Log.d("Response:", new Gson().toJson(complaintsInsertReqVo));
                        if (complaintsInsertReqVo != null) {
                            tv_complaint_date.setText(complaintsInsertReqVo.complaintDate);
                            tv_client_code.setText(complaintsInsertReqVo.clientCode);
                            tv_oa_number.setText(complaintsInsertReqVo.oaNumber);
                            tv_area_office.setText(complaintsInsertReqVo.areaOffice);
                            tv_client_name.setText(complaintsInsertReqVo.clientName);
                            tv_project_type.setText(complaintsInsertReqVo.projectTypeName);
                            tv_product.setText(complaintsInsertReqVo.divisionMastername);
                            tv_marketing_officer_name.setText(complaintsInsertReqVo.marketingOfficerName);
                            tv_fab_unit.setText(complaintsInsertReqVo.fabUnitName);
                            tv_nature_of_complaint.setText(complaintsInsertReqVo.natureOfComplaintName);
                            tv_closing_date.setText(complaintsInsertReqVo.closingDate);
                            tv_no_of_days_took.setText(complaintsInsertReqVo.noDaysForResolve);
                            tv_complaint_status.setText(complaintsInsertReqVo.complaintStatus);

                            if (complaintsInsertReqVo.commercialDepartmentRemarks != null) {
                                addRemarksLineItems(ll_commercial_remarks, complaintsInsertReqVo.commercialDepartmentRemarks);
                            }
                            if (complaintsInsertReqVo.supervisiorRemarks != null) {
                                addRemarksLineItems(ll_supervisor_remarks, complaintsInsertReqVo.supervisiorRemarks);
                            }
                            if (complaintsInsertReqVo.complaintReceiverRemarks != null) {
                                addRemarksLineItems(ll_complaint_receiver_remarks, complaintsInsertReqVo.complaintReceiverRemarks);
                            }
                            if (complaintsInsertReqVo.finalRemarks != null) {
                                addRemarksLineItems(ll_final_remarks, complaintsInsertReqVo.finalRemarks);
                            }
                            switch (complaintsInsertReqVo.imagesLists.size()) {
                                case 0:
                                    ll_image_preview1.setVisibility(View.GONE);
                                    ll_image_preview2.setVisibility(View.GONE);
                                    ll_image_preview3.setVisibility(View.GONE);
                                    ll_image_preview4.setVisibility(View.GONE);
                                    break;
                                case 1:
                                    ll_image_preview2.setVisibility(View.GONE);
                                    ll_image_preview3.setVisibility(View.GONE);
                                    ll_image_preview4.setVisibility(View.GONE);
                                    if ((complaintsInsertReqVo.imagesLists.get(0).imagePath != null && !TextUtils.isEmpty(complaintsInsertReqVo.imagesLists.get(0).imagePath))) {
                                        Picasso.with(this)
                                                .load(complaintsInsertReqVo.imagesLists.get(0).imagePath)
                                                .resize(100, 100)
                                                .error(R.drawable.ic_baseline_camera_alt_24)
                                                .into(iv_file1_preview);
                                    }
                                    break;
                                case 2:
                                    ll_image_preview3.setVisibility(View.GONE);
                                    ll_image_preview4.setVisibility(View.GONE);
                                    if ((complaintsInsertReqVo.imagesLists.get(0).imagePath != null && !TextUtils.isEmpty(complaintsInsertReqVo.imagesLists.get(0).imagePath))) {
                                        Picasso.with(this)
                                                .load(complaintsInsertReqVo.imagesLists.get(0).imagePath)
                                                .resize(100, 100)
                                                .error(R.drawable.ic_baseline_camera_alt_24)
                                                .into(iv_file1_preview);
                                    }
                                    if ((complaintsInsertReqVo.imagesLists.get(1).imagePath != null && !TextUtils.isEmpty(complaintsInsertReqVo.imagesLists.get(1).imagePath))) {
                                        Picasso.with(this)
                                                .load(complaintsInsertReqVo.imagesLists.get(1).imagePath)
                                                .resize(100, 100)
                                                .error(R.drawable.ic_baseline_camera_alt_24)
                                                .into(iv_file2_preview);
                                    }
                                    break;
                                case 3:
                                    ll_image_preview4.setVisibility(View.GONE);
                                    if ((complaintsInsertReqVo.imagesLists.get(0).imagePath != null && !TextUtils.isEmpty(complaintsInsertReqVo.imagesLists.get(0).imagePath))) {
                                        Picasso.with(this)
                                                .load(complaintsInsertReqVo.imagesLists.get(0).imagePath)
                                                .resize(100, 100)
                                                .error(R.drawable.ic_baseline_camera_alt_24)
                                                .into(iv_file1_preview);
                                    }
                                    if ((complaintsInsertReqVo.imagesLists.get(1).imagePath != null && !TextUtils.isEmpty(complaintsInsertReqVo.imagesLists.get(1).imagePath))) {
                                        Picasso.with(this)
                                                .load(complaintsInsertReqVo.imagesLists.get(1).imagePath)
                                                .resize(100, 100)
                                                .error(R.drawable.ic_baseline_camera_alt_24)
                                                .into(iv_file2_preview);
                                    }
                                    if ((complaintsInsertReqVo.imagesLists.get(2).imagePath != null && !TextUtils.isEmpty(complaintsInsertReqVo.imagesLists.get(2).imagePath))) {
                                        Picasso.with(this)
                                                .load(complaintsInsertReqVo.imagesLists.get(2).imagePath)
                                                .resize(100, 100)
                                                .error(R.drawable.ic_baseline_camera_alt_24)
                                                .into(iv_file3_preview);
                                    }
                                    break;
                                case 4:
                                    if ((complaintsInsertReqVo.imagesLists.get(0).imagePath != null && !TextUtils.isEmpty(complaintsInsertReqVo.imagesLists.get(0).imagePath))) {
                                        Picasso.with(this)
                                                .load(complaintsInsertReqVo.imagesLists.get(0).imagePath)
                                                .resize(100, 100)
                                                .error(R.drawable.ic_baseline_camera_alt_24)
                                                .into(iv_file1_preview);
                                    }
                                    if ((complaintsInsertReqVo.imagesLists.get(1).imagePath != null && !TextUtils.isEmpty(complaintsInsertReqVo.imagesLists.get(1).imagePath))) {
                                        Picasso.with(this)
                                                .load(complaintsInsertReqVo.imagesLists.get(1).imagePath)
                                                .resize(100, 100)
                                                .error(R.drawable.ic_baseline_camera_alt_24)
                                                .into(iv_file2_preview);
                                    }
                                    if ((complaintsInsertReqVo.imagesLists.get(2).imagePath != null && !TextUtils.isEmpty(complaintsInsertReqVo.imagesLists.get(2).imagePath))) {
                                        Picasso.with(this)
                                                .load(complaintsInsertReqVo.imagesLists.get(2).imagePath)
                                                .resize(100, 100)
                                                .error(R.drawable.ic_baseline_camera_alt_24)
                                                .into(iv_file3_preview);
                                    }
                                    if ((complaintsInsertReqVo.imagesLists.get(3).imagePath != null && !TextUtils.isEmpty(complaintsInsertReqVo.imagesLists.get(3).imagePath))) {
                                        Picasso.with(this)
                                                .load(complaintsInsertReqVo.imagesLists.get(3).imagePath)
                                                .resize(100, 100)
                                                .error(R.drawable.ic_baseline_camera_alt_24)
                                                .into(iv_file4_preview);
                                    }
                                    break;

                            }
                        }
                    }
                    Common.dismissProgressDialog(progressDialog);
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addRemarksLineItems(LinearLayout linearLayout, List<ComplaintsInsertReqVo.RemarksList> remarksLists) {
        for (int i = 0; i < remarksLists.size(); i++) {
            View remarksView = getLayoutInflater().inflate(R.layout.add_lead_note, null);
            linearLayout.addView(remarksView);
            addRemarksView(remarksView, remarksLists.get(i));
        }
    }

    private void addRemarksView(View view, ComplaintsInsertReqVo.RemarksList remarksList) {
        CreateComplaintsActivity.ViewHolderRemarks viewHolder = new CreateComplaintsActivity.ViewHolderRemarks(view);
        viewHolder.etDate.setText(remarksList.date);
        viewHolder.etRemarks.setText(remarksList.remarksVal);
        viewHolder.etRemarks.setEnabled(false);
        viewHolder.addLayout.setVisibility(View.GONE);
        viewHolder.removeLayout.setVisibility(View.GONE);
    }
}
