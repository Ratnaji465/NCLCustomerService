package com.ncl.nclcustomerservice.activity;

import static java.util.concurrent.TimeUnit.DAYS;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity;
import com.ncl.nclcustomerservice.adapter.CustomSpinnerAdapter;
import com.ncl.nclcustomerservice.adapter.FileDetails;
import com.ncl.nclcustomerservice.application.MyApplication;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.customviews.CustomButton;
import com.ncl.nclcustomerservice.customviews.CustomEditText;
import com.ncl.nclcustomerservice.customviews.CustomTextView;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.ComplaintsInsertReqVo;
import com.ncl.nclcustomerservice.object.DivisionMasterList;
import com.ncl.nclcustomerservice.object.FabUnitList;
import com.ncl.nclcustomerservice.object.NatureOfComplaintList;
import com.ncl.nclcustomerservice.object.ProjectTypeList;
import com.ncl.nclcustomerservice.object.SpinnerModel;
import com.squareup.picasso.Picasso;

import org.joda.time.Days;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateComplaintsActivity extends NetworkChangeListenerActivity implements RetrofitResponseListener {

    private static final String TAG = "CreateComplaintsActivity";
    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.back_button)
    ImageView back;
    @BindView(R.id.parent)
    View parent;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    String form = "";
    DatabaseHandler db;
    String profileName;

    @BindView(R.id.tv_complaint_date)
    CustomTextView tv_complaint_date;
    @BindView(R.id.tv_client_code_head)
    CustomTextView tv_client_code_head;
    @BindView(R.id.et_client_code)
    CustomEditText et_client_code;
    @BindView(R.id.tv_oa_no_head)
    CustomTextView tv_oa_no_head;
    @BindView(R.id.et_oa_no)
    CustomEditText et_oa_no;
    @BindView(R.id.tv_area_office)
    CustomTextView tv_area_office;
    @BindView(R.id.tv_client_name_head)
    CustomTextView tv_client_name_head;
    @BindView(R.id.et_client_name)
    CustomEditText et_client_name;
    @BindView(R.id.tv_project_type_head)
    CustomTextView tv_project_type_head;
    @BindView(R.id.spnr_project_type)
    Spinner spnr_project_type;
    @BindView(R.id.tv_product_head)
    CustomTextView tv_product_head;
    @BindView(R.id.spnr_product)
    Spinner spnr_product;
    @BindView(R.id.tv_Marketing_officer_head)
    CustomTextView tv_Marketing_officer_head;
    @BindView(R.id.et_Marketing_officer)
    CustomEditText et_Marketing_officer;
    @BindView(R.id.tv_fab_unit_head)
    CustomTextView tv_fab_unit_head;
    @BindView(R.id.spnr_fab_unit)
    Spinner spnr_fab_unit;
    @BindView(R.id.tv_Nature_of_compaint_head)
    CustomTextView tv_Nature_of_compaint_head;
    @BindView(R.id.spnr_Nature_of_compaint)
    Spinner spnr_Nature_of_compaint;

    @BindView(R.id.other_project_layout)
    LinearLayout other_project_layout;
    @BindView(R.id.tv_other_project_head)
    CustomTextView tv_other_project_head;
    @BindView(R.id.et_other_project)
    CustomEditText et_other_project;

    @BindView(R.id.other_nature_of_compl_layout)
    LinearLayout other_nature_of_compl_layout;
    @BindView(R.id.tv_other_nature_of_compl_head)
    CustomTextView tv_other_nature_of_compl_head;
    @BindView(R.id.et_other_nature_of_compl)
    CustomEditText et_other_nature_of_compl;

    @BindView(R.id.btn_file1)
    Button btn_file1;
    @BindView(R.id.iv_file1_preview)
    ImageView iv_file1_preview;
    @BindView(R.id.btn_file2)
    Button btn_file2;
    @BindView(R.id.iv_file2_preview)
    ImageView iv_file2_preview;
    @BindView(R.id.btn_file3)
    Button btn_file3;
    @BindView(R.id.iv_file3_preview)
    ImageView iv_file3_preview;
    @BindView(R.id.btn_file4)
    Button btn_file4;
    @BindView(R.id.iv_file4_preview)
    ImageView iv_file4_preview;

    @BindView(R.id.by_supervisor_layout)
    LinearLayout by_supervisor_layout;
    @BindView(R.id.complaint_receiver_layout)
    LinearLayout complaint_receiver_layout;
    @BindView(R.id.commercial_department_layout)
    LinearLayout commercial_department_layout;
    @BindView(R.id.final_remarks_layout)
    LinearLayout final_remarks_layout;

    @BindView(R.id.tv_closing_date)
    CustomTextView tv_closing_date;
    @BindView(R.id.et_days_took_resolve)
    CustomEditText et_days_took_resolve;
    @BindView(R.id.spnr_complaint_status)
    Spinner spnr_complaint_status;

    @BindView(R.id.complaints_save)
    CustomButton complaints_save;
    @BindView(R.id.complaints_cancel)
    CustomButton complaints_cancel;

    String selectedProductType, selectedDivisionMaster, selectedFabUnit, selectedNatureOfComplaint,
            selectedComplaintStatus;
    String selectedProductTypeId,selectedDivisionMasterId, selectedFabUnitId, selectedNatureOfComplaintId;

    private final String[] COMPLAINT_STATUS = new String[]{"Select", "Open", "Live", "Closed"};
    int requestFileCode;
    FileDetails fileDetails1 = null, fileDetails2 = null, fileDetails3 = null, fileDetails4 = null;
    ComplaintsInsertReqVo complaintsInsertReqVo=new ComplaintsInsertReqVo();
    private ProgressDialog progressD;

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
        findViewSetHint();
        loadDataIntoFields();
        if (form!=null && form.equalsIgnoreCase("edit")) {
            title_text.setText("Edit Complaints");
            complaintsInsertReqVo = (ComplaintsInsertReqVo) getIntent().getSerializableExtra("complaints");
            if(complaintsInsertReqVo!=null){
                tv_complaint_date.setText(complaintsInsertReqVo.complaintDate);
                et_client_code.setText(complaintsInsertReqVo.clientCode);
                et_oa_no.setText(complaintsInsertReqVo.oaNumber);
                tv_area_office.setText(complaintsInsertReqVo.areaOffice);
                et_client_name.setText(complaintsInsertReqVo.clientName);
                if(complaintsInsertReqVo.otherProjectType!=null && !TextUtils.isEmpty(complaintsInsertReqVo.otherProjectType)){
                    other_project_layout.setVisibility(View.VISIBLE);
                    et_other_project.setText(complaintsInsertReqVo.otherProjectType);
                }
                spnr_project_type.setSelection(getIndexByProjectTypeId(complaintsInsertReqVo.csProjectTypeId));
                spnr_product.setSelection(getIndexByProductId(complaintsInsertReqVo.divisionMasterId));
                spnr_fab_unit.setSelection(getIndexByFabUnitId(complaintsInsertReqVo.fabUnitId));
                spnr_Nature_of_compaint.setSelection(getIndexByNatuerOfComplaintId(complaintsInsertReqVo.natureOfComplaintId));
                spnr_complaint_status.setSelection(getSelectedComplaintStatus(complaintsInsertReqVo.complaintStatus));
                et_Marketing_officer.setText(complaintsInsertReqVo.marketingOfficerName);
                if(complaintsInsertReqVo.otherNatureOfComplaint!=null && !TextUtils.isEmpty(complaintsInsertReqVo.otherNatureOfComplaint)){
                    other_nature_of_compl_layout.setVisibility(View.VISIBLE);
                    et_other_nature_of_compl.setText(complaintsInsertReqVo.otherNatureOfComplaint);
                }
                tv_closing_date.setText(complaintsInsertReqVo.closingDate);
                et_days_took_resolve.setText(complaintsInsertReqVo.noDaysForResolve);

                if (complaintsInsertReqVo.supervisiorRemarks != null) {
                    by_supervisor_layout.removeAllViews();
                    for (int i=0; i<complaintsInsertReqVo.supervisiorRemarks.size();i++){
                        if (Common.getUserType(this).equalsIgnoreCase("Supervisior")) {
                            addSupervisorRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null),complaintsInsertReqVo.supervisiorRemarks.get(i), true);
                        } else {
                            addSupervisorRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null), complaintsInsertReqVo.supervisiorRemarks.get(i),false);
                        }
                    }

                }
                if (complaintsInsertReqVo.commercialDepartmentRemarks != null) {
                    commercial_department_layout.removeAllViews();
                    for (int i=0; i<complaintsInsertReqVo.commercialDepartmentRemarks.size();i++) {
                        if (Common.getUserType(this).equalsIgnoreCase("Commercial")) {
                            addCommercialRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null), complaintsInsertReqVo.commercialDepartmentRemarks.get(i), true);
                        } else {
                            addCommercialRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null), complaintsInsertReqVo.commercialDepartmentRemarks.get(i), false);
                        }
                    }
                }
                if (complaintsInsertReqVo.complaintReceiverRemarks != null) {
                    complaint_receiver_layout.removeAllViews();
                    for (int i=0; i<complaintsInsertReqVo.complaintReceiverRemarks.size();i++) {
                        if (Common.getUserType(this).equalsIgnoreCase("Fabunit")) {
                            addComplaintRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null), complaintsInsertReqVo.complaintReceiverRemarks.get(i), true);
                        } else {
                            addComplaintRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null), complaintsInsertReqVo.complaintReceiverRemarks.get(i), false);
                        }
                    }
                }
                if (complaintsInsertReqVo.finalRemarks != null) {
                    final_remarks_layout.removeAllViews();
                    for (int i=0; i<complaintsInsertReqVo.finalRemarks.size();i++) {
                        if (Common.getUserType(this).equalsIgnoreCase("Final")) {
                            addFinalRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null), complaintsInsertReqVo.finalRemarks.get(i), true);
                        } else {
                            addFinalRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null), complaintsInsertReqVo.finalRemarks.get(i), false);
                        }
                    }
                }
                switch (complaintsInsertReqVo.imagesLists.size()) {
                    case 1:
                        if ((complaintsInsertReqVo.imagesLists.get(0).imagePath != null && !TextUtils.isEmpty(complaintsInsertReqVo.imagesLists.get(0).imagePath))) {
                            Picasso.with(this)
                                    .load(complaintsInsertReqVo.imagesLists.get(0).imagePath)
                                    .resize(100, 100)
                                    .error(R.drawable.ic_baseline_camera_alt_24)
                                    .into(iv_file1_preview);
                        }
                        break;
                    case 2:
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
    }
    private int getSelectedComplaintStatus(String complaintStatus) {
        for (int i = 0; i < COMPLAINT_STATUS.length; i++) {
            if (COMPLAINT_STATUS[i].equalsIgnoreCase(complaintStatus)) {
                selectedComplaintStatus=COMPLAINT_STATUS[i];
                return i;
            }
        }
        return 0;
    }
    private int getIndexByNatuerOfComplaintId(String natureOfComplaintId) {
        List<NatureOfComplaintList> natureOfComplaintLists= db.commonDao().getAllNatureOfComplaintList();
        for (int i = 0; i < natureOfComplaintLists.size(); i++) {
            if (natureOfComplaintLists.get(i).nature_of_complaint_id.equalsIgnoreCase(natureOfComplaintId)) {
                selectedNatureOfComplaint=natureOfComplaintLists.get(i).nature_of_complaint_name;
                selectedNatureOfComplaintId=natureOfComplaintLists.get(i).nature_of_complaint_id;
                return i+1;
            }
        }
        return 0;
    }
    private int getIndexByFabUnitId(String fabUnitId) {
        List<FabUnitList> fabUnitLists= db.commonDao().getAllFabUnitList();
        for (int i = 0; i < fabUnitLists.size(); i++) {
            if (fabUnitLists.get(i).fab_unit_id.equalsIgnoreCase(fabUnitId)) {
                selectedFabUnit=fabUnitLists.get(i).fab_unit_name;
                selectedFabUnitId=fabUnitLists.get(i).fab_unit_id;
                return i+1;
            }
        }
        return 0;
    }
    private int getIndexByProductId(String divisionMasterId) {
        List<DivisionMasterList> divisionMasterLists= db.commonDao().getAllDivisionMasterList();
        for (int i = 0; i < divisionMasterLists.size(); i++) {
            if (divisionMasterLists.get(i).division_master_id.equalsIgnoreCase(divisionMasterId)) {
                selectedDivisionMaster=divisionMasterLists.get(i).division_name;
                selectedDivisionMasterId=divisionMasterLists.get(i).division_master_id;
                return i+1;
            }
        }
        return 0;
    }

    private int getIndexByProjectTypeId(String csProjectTypeId) {
       List<ProjectTypeList> projectTypeLists= db.commonDao().getAllProjectTypeList();
        for (int i = 0; i < projectTypeLists.size(); i++) {
            if (projectTypeLists.get(i).cs_project_type_id.equalsIgnoreCase(csProjectTypeId)) {
                selectedProductType=projectTypeLists.get(i).project_type_name;
                selectedProductTypeId=projectTypeLists.get(i).cs_project_type_id;
                return i+1;
            }
        }
        return 0;
    }
    @OnClick({R.id.complaints_cancel})
    void cancel() {
        finish();
    }

    @OnClick(R.id.complaints_save)
    void saveComplaint(){
        if(!isValidated()){

        }else {
            complaintsInsertReqVo.roleId =String.valueOf(Common.getRoleIdFromSP(this));
            complaintsInsertReqVo.profileId=Common.getProfileId(this);
            complaintsInsertReqVo.complaintDate=tv_complaint_date.getText().toString();
            complaintsInsertReqVo.clientCode=et_client_code.getText().toString();
            complaintsInsertReqVo.oaNumber=et_oa_no.getText().toString();
            complaintsInsertReqVo.areaOffice=tv_area_office.getText().toString();
            complaintsInsertReqVo.clientName=et_client_name.getText().toString();
            complaintsInsertReqVo.csProjectTypeId=selectedProductTypeId;
            complaintsInsertReqVo.otherProjectType=et_other_project.getText().toString();
            complaintsInsertReqVo.divisionMasterId=selectedDivisionMasterId;
            complaintsInsertReqVo.marketingOfficerName=et_Marketing_officer.getText().toString();
            complaintsInsertReqVo.fabUnitId=selectedFabUnitId;
            complaintsInsertReqVo.natureOfComplaintId=selectedNatureOfComplaintId;
            complaintsInsertReqVo.otherNatureOfComplaint=et_other_nature_of_compl.getText().toString();
//            complaintsInsertReqVo.closingDate=tv_closing_date.getText().toString();
//            complaintsInsertReqVo.noDaysForResolve=et_days_took_resolve.getText().toString();
            complaintsInsertReqVo.complaintStatus=selectedComplaintStatus;
            if (Common.getUserType(this).equalsIgnoreCase("Supervisior")) {
                complaintsInsertReqVo.remarks= getRemarks(by_supervisor_layout);
            }
            if(Common.getUserType(this).equalsIgnoreCase("Fabunit")){
                complaintsInsertReqVo.remarks=getRemarks(complaint_receiver_layout);
            }
            if (Common.getUserType(this).equalsIgnoreCase("Commercial")) {
                complaintsInsertReqVo.remarks = getRemarks(commercial_department_layout);
            }
            if (Common.getUserType(this).equalsIgnoreCase("Final")) {
                complaintsInsertReqVo.remarks = getRemarks(final_remarks_layout);
            }

            complaintsInsertReqVo.requesterId=""+Common.getUserIdFromSP(CreateComplaintsActivity.this);
            if(form!=null && form.equalsIgnoreCase("edit")){
                complaintsInsertReqVo.requestName= Constants.RequestNames.COMPLAINT_REGISTER_UPDATE;
            }else {
                complaintsInsertReqVo.requestName= Constants.RequestNames.COMPLAINT_REGISTER_INSERT;
            }
            sendImage(complaintsInsertReqVo);
        }
    }

    private List<ComplaintsInsertReqVo.RemarksList> getRemarks(LinearLayout linearLayout) {
        List<ComplaintsInsertReqVo.RemarksList> remarksLists=new ArrayList<>();
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View childView = linearLayout.getChildAt(i);
            ViewHolderRemarks viewHolder = new ViewHolderRemarks(childView);
            ComplaintsInsertReqVo.RemarksList remarksList=new ComplaintsInsertReqVo.RemarksList();
            remarksList.date=viewHolder.etDate.getText().toString().trim();
            remarksList.remarksVal=viewHolder.etRemarks.getText().toString().trim();
            remarksLists.add(remarksList);
        }
        return remarksLists;
    }

    private void sendImage(ComplaintsInsertReqVo complaintsInsertReqVo) {
        progressD = new ProgressDialog(this);
        progressD.setMessage("Please Wait....");
        progressD.setCancelable(false);
        progressD.show();
        List<MultipartBody.Part> muPartList = new ArrayList<>();
        if (fileDetails1 != null) {
            if (fileDetails1.filePath != null && fileDetails1.filePath.length() > 5) {
                muPartList.add(prepareFilePart("complaint_register_image[]", Uri.fromFile(new File(fileDetails1.filePath)), new File(fileDetails1.filePath)));
            }
        }
        if (fileDetails2 != null) {
            if (fileDetails2.filePath != null && fileDetails2.filePath.length() > 5) {
                muPartList.add(prepareFilePart("complaint_register_image[]", Uri.fromFile(new File(fileDetails2.filePath)), new File(fileDetails2.filePath)));
            }
        }
        if (fileDetails3 != null) {
            if (fileDetails3.filePath != null && fileDetails3.filePath.length() > 5) {
                muPartList.add(prepareFilePart("complaint_register_image[]", Uri.fromFile(new File(fileDetails3.filePath)), new File(fileDetails3.filePath)));
            }
        }
        if (fileDetails4 != null) {
            if (fileDetails4.filePath != null && fileDetails4.filePath.length() > 5) {
                muPartList.add(prepareFilePart("complaint_register_image[]", Uri.fromFile(new File(fileDetails4.filePath)), new File(fileDetails4.filePath)));
            }
        }
        MultipartBody.Part[] fileParts = muPartList.toArray(new MultipartBody.Part[muPartList.size()]);

        Call<ResponseBody> abc = MyApplication.getInstance().getAPIInterface().uploadPaymentCollection(Constants.API, fileParts, complaintsInsertReqVo);
        abc.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    progressD.dismiss();
                    Toast.makeText(CreateComplaintsActivity.this, "Intenal Server Error", Toast.LENGTH_SHORT).show();

                    return;
                }
                ApiResponseController apiResponseController = null;
                try {
                    apiResponseController = new Gson().fromJson(response.body().string(), ApiResponseController.class);
                    if (apiResponseController != null) {
                        ComplaintsInsertReqVo complaintsInsertReqVo1 = Common.getSpecificDataObject(apiResponseController.result, ComplaintsInsertReqVo.class);
                        if (complaintsInsertReqVo1 != null) {
                        Log.d("Response:",new Gson().toJson(complaintsInsertReqVo1).toString());
                            Intent intent = new Intent(CreateComplaintsActivity.this, ComplaintsViewActivity.class);
                            intent.putExtra("complaint_register_id", complaintsInsertReqVo1.csComplaintRegisterId);
                            startActivity(intent);
                            finish();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                progressD.dismiss();
                finish();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private MultipartBody.Part prepareFilePart(String file_i, Uri uri, File file) {
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getMimeType(this, uri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(file_i, file.getName(), requestFile);
    }
    private String getMimeType(CreateComplaintsActivity context, Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
    }
    private boolean isValidated() {
        boolean Validate = true;
        if (et_client_code.getText().toString().trim().length() == 0) {
            et_client_code.setError("Please Add Client Code");
            return false;
        }
        if (et_oa_no.getText().toString().trim().length() == 0) {
            et_oa_no.setError("Please Add OA Number");
            return false;
        }
        if (et_client_name.getText().toString().trim().length() == 0) {
            et_client_name.setError("Please Add Client Name");
            return false;
        }
        if (spnr_project_type.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select Project Type", Toast.LENGTH_LONG).show();
            return false;
        }
        if (spnr_product.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select Product", Toast.LENGTH_LONG).show();
            return false;
        }
        if (et_Marketing_officer.getText().toString().trim().length() == 0) {
            et_Marketing_officer.setError("Please Add Marking office name");
            return false;
        }
        if (spnr_fab_unit.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select Fab Unit/ Departments", Toast.LENGTH_LONG).show();
            return false;
        }
        if (spnr_Nature_of_compaint.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select Nature of complaint", Toast.LENGTH_LONG).show();
            return false;
        }
        return Validate;
    }

    @OnClick(R.id.btn_file1)
    void captureImage() {
        ImagePicker.Companion.with(CreateComplaintsActivity.this)
                .compress(1024)            //Final image size will be less than 1.0 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
        requestFileCode = 1001;
    }
    @OnClick(R.id.btn_file2)
    void captureImage2() {
        ImagePicker.Companion.with(CreateComplaintsActivity.this)
                .compress(1024)            //Final image size will be less than 1.0 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
        requestFileCode = 1002;
    }
    @OnClick(R.id.btn_file3)
    void captureImage3() {
        ImagePicker.Companion.with(CreateComplaintsActivity.this)
                .compress(1024)            //Final image size will be less than 1.0 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
        requestFileCode = 1003;
    }
    @OnClick(R.id.btn_file4)
    void captureImage4() {
        ImagePicker.Companion.with(CreateComplaintsActivity.this)
                .compress(1024)            //Final image size will be less than 1.0 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
        requestFileCode = 1004;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            File file = ImagePicker.Companion.getFile(data);
            if (file != null) {
                if (requestFileCode == 1001) {
                    Picasso.with(this)
                            .load("file:///" + file.getAbsolutePath())
                            .resize(100, 100)
                            .error(R.drawable.ic_upload)
                            .into(iv_file1_preview);
                    String firstlink1 = file.getAbsolutePath().subSequence(0, file.getAbsolutePath().lastIndexOf('/')).toString();
                    System.out.println("## firstlink:" + firstlink1);
                    fileDetails1 = new FileDetails();
                    fileDetails1.filePath = file.getAbsolutePath();
                }else if (requestFileCode == 1002) {
                    Picasso.with(this)
                            .load("file:///" + file.getAbsolutePath())
                            .resize(100, 100)
                            .error(R.drawable.ic_upload)
                            .into(iv_file2_preview);
                    String secondlink1 = file.getAbsolutePath().subSequence(0, file.getAbsolutePath().lastIndexOf('/')).toString();
                    System.out.println("## secondlink:" + secondlink1);
                    fileDetails2 = new FileDetails();
                    fileDetails2.filePath = file.getAbsolutePath();
                }else if (requestFileCode == 1003) {
                    Picasso.with(this)
                            .load("file:///" + file.getAbsolutePath())
                            .resize(100, 100)
                            .error(R.drawable.ic_upload)
                            .into(iv_file3_preview);
                    String secondlink1 = file.getAbsolutePath().subSequence(0, file.getAbsolutePath().lastIndexOf('/')).toString();
                    System.out.println("## thirdlink:" + secondlink1);
                    fileDetails3 = new FileDetails();
                    fileDetails3.filePath = file.getAbsolutePath();
                }else if (requestFileCode == 1004) {
                    Picasso.with(this)
                            .load("file:///" + file.getAbsolutePath())
                            .resize(100, 100)
                            .error(R.drawable.ic_upload)
                            .into(iv_file4_preview);
                    String secondlink1 = file.getAbsolutePath().subSequence(0, file.getAbsolutePath().lastIndexOf('/')).toString();
                    System.out.println("## fouthlink:" + secondlink1);
                    fileDetails4 = new FileDetails();
                    fileDetails4.filePath = file.getAbsolutePath();
                }
            }
        }
    }

    private void loadDataIntoFields() {
        tv_complaint_date.setText(Common.getCurrentDate());
        tv_area_office.setText(Common.getAreaOffice(this));
        tv_closing_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(CreateComplaintsActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, month, day);
                            tv_closing_date.setText(Common.getDatenewFormat(Common.getOnlyDate(newDate.getTime()))[0]);
                            long millionSeconds =   newDate.getTimeInMillis()-Calendar.getInstance().getTimeInMillis();
                            et_days_took_resolve.setText(""+DAYS.convert(millionSeconds, TimeUnit.MILLISECONDS));
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            }

            ;
        });
        if (Common.getUserType(this).equalsIgnoreCase("Supervisior")) {
            addSupervisorRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null),null, true);
        } else {
            addSupervisorRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null), null,false);
        }

        if (Common.getUserType(this).equalsIgnoreCase("Fabunit")) {
            addComplaintRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null),null,true);
        } else {
            addComplaintRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null),null,false);
        }

        if (Common.getUserType(this).equalsIgnoreCase("Commercial")) {
            addCommercialRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null),null,true);
        } else {
            addCommercialRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null),null,false);
        }

        if (Common.getUserType(this).equalsIgnoreCase("Final")) {
            addFinalRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null),null,true);
        } else {
            addFinalRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null),null,false);
        }

        List<ProjectTypeList> projectTypeLists = db.commonDao().getAllProjectTypeList();
        List<DivisionMasterList> divisionMasterLists = db.commonDao().getAllDivisionMasterList();
        List<FabUnitList> fabUnitLists = db.commonDao().getAllFabUnitList();
        List<NatureOfComplaintList> natureOfComplaintLists = db.commonDao().getAllNatureOfComplaintList();
        List<SpinnerModel> projectType = new ArrayList<>();
        List<SpinnerModel> divisionMaster = new ArrayList<>();
        List<SpinnerModel> fabUnit = new ArrayList<>();
        List<SpinnerModel> natureOfComplaint = new ArrayList<>();
        List<SpinnerModel> complaintStatus = new ArrayList<>();
        SpinnerModel sm = new SpinnerModel();
        sm.setId("0");
        sm.setTitle("Select");
        projectType.add(sm);
        divisionMaster.add(sm);
        fabUnit.add(sm);
        natureOfComplaint.add(sm);

        for (int i = 0; i < projectTypeLists.size(); i++) {
            SpinnerModel spinnerModel = new SpinnerModel();
            spinnerModel.setId(projectTypeLists.get(i).cs_project_type_id);
            spinnerModel.setTitle(projectTypeLists.get(i).project_type_name);
            projectType.add(spinnerModel);
        }
        for (int i = 0; i < divisionMasterLists.size(); i++) {
            SpinnerModel spinnerModel = new SpinnerModel();
            spinnerModel.setId(divisionMasterLists.get(i).division_master_id);
            spinnerModel.setTitle(divisionMasterLists.get(i).division_name);
            spinnerModel.setPrice(divisionMasterLists.get(i).division_sap_code);
            divisionMaster.add(spinnerModel);
        }
        for (int i = 0; i < fabUnitLists.size(); i++) {
            SpinnerModel spinnerModel = new SpinnerModel();
            spinnerModel.setId(fabUnitLists.get(i).fab_unit_id);
            spinnerModel.setTitle(fabUnitLists.get(i).fab_unit_name);
            fabUnit.add(spinnerModel);
        }
        for (int i = 0; i < natureOfComplaintLists.size(); i++) {
            SpinnerModel spinnerModel = new SpinnerModel();
            spinnerModel.setId(natureOfComplaintLists.get(i).nature_of_complaint_id);
            spinnerModel.setTitle(natureOfComplaintLists.get(i).nature_of_complaint_name);
            natureOfComplaint.add(spinnerModel);
        }
        for (String complaint_status : COMPLAINT_STATUS) {
            SpinnerModel spinnerModel = new SpinnerModel();
            spinnerModel.setTitle(complaint_status);
            complaintStatus.add(spinnerModel);
        }
        CustomSpinnerAdapter productTypeAdapter = new CustomSpinnerAdapter(this, 0, projectType);
        spnr_project_type.setAdapter(productTypeAdapter);
        spnr_project_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedProductType = projectTypeLists.get(position - 1).project_type_name;
                    selectedProductTypeId= projectTypeLists.get(position - 1).cs_project_type_id;
                    Log.d("selectedProductType :", selectedProductType);
                    if (selectedProductType.equalsIgnoreCase("Others")) {
                        other_project_layout.setVisibility(View.VISIBLE);
                    } else {
                        other_project_layout.setVisibility(View.GONE);
                    }
                } else {
                    selectedProductType = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomSpinnerAdapter divisionMasterAdapter = new CustomSpinnerAdapter(this, 0, divisionMaster);
        spnr_product.setAdapter(divisionMasterAdapter);
        spnr_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedDivisionMaster = divisionMasterLists.get(position - 1).division_name;
                    selectedDivisionMasterId = divisionMasterLists.get(position - 1).division_master_id;
                    Log.d("selectedDivision :", selectedDivisionMaster);
                } else {
                    selectedDivisionMaster = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomSpinnerAdapter fabUnitAdapter = new CustomSpinnerAdapter(this, 0, fabUnit);
        spnr_fab_unit.setAdapter(fabUnitAdapter);
        spnr_fab_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedFabUnit = fabUnitLists.get(position - 1).fab_unit_name;
                    selectedFabUnitId = fabUnitLists.get(position - 1).fab_unit_id;
                    Log.d("selectedFabUnit :", selectedFabUnit);
                } else {
                    selectedFabUnit = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomSpinnerAdapter natureOfComplaintAdapter = new CustomSpinnerAdapter(this, 0, natureOfComplaint);
        spnr_Nature_of_compaint.setAdapter(natureOfComplaintAdapter);
        spnr_Nature_of_compaint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedNatureOfComplaint = natureOfComplaintLists.get(position - 1).nature_of_complaint_name;
                    selectedNatureOfComplaintId = natureOfComplaintLists.get(position - 1).nature_of_complaint_id;
                    Log.d("selectedNatureOf :", selectedNatureOfComplaint);
                    if (selectedNatureOfComplaint.equalsIgnoreCase("Other")) {
                        other_nature_of_compl_layout.setVisibility(View.VISIBLE);
                    } else {
                        other_nature_of_compl_layout.setVisibility(View.GONE);
                    }
                } else {
                    selectedNatureOfComplaint = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomSpinnerAdapter complaintStatusAdapter = new CustomSpinnerAdapter(this, 0, complaintStatus);
        spnr_complaint_status.setAdapter(complaintStatusAdapter);
        spnr_complaint_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedComplaintStatus = COMPLAINT_STATUS[position];
                    Log.d("selectedComplaint :", selectedComplaintStatus);
                } else {
                    selectedComplaintStatus = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void findViewSetHint() {
        tv_client_code_head.setText(Common.setSppanableText("* Client Code"));
        tv_oa_no_head.setText(Common.setSppanableText("* OA NO"));
        tv_client_name_head.setText(Common.setSppanableText("* Client Name"));
        tv_project_type_head.setText(Common.setSppanableText("* Project Type"));
        tv_product_head.setText(Common.setSppanableText("* Product"));
        tv_Marketing_officer_head.setText(Common.setSppanableText("* Marketing officer name"));
        tv_fab_unit_head.setText(Common.setSppanableText("* Fab Unit/ Departments"));
        tv_Nature_of_compaint_head.setText(Common.setSppanableText("* Nature of complaint"));
        tv_other_project_head.setText(Common.setSppanableText("* Other Project type"));
        tv_other_nature_of_compl_head.setText(Common.setSppanableText("* Other Nature of complaint"));
    }

    private void addFinalRemark(View view,ComplaintsInsertReqVo.RemarksList remarksList,boolean isEnabled) {
        final_remarks_layout.addView(view);
        ViewHolderRemarks viewHolder = new ViewHolderRemarks(view);
        viewHolder.etDate.setText(Common.getCurrentDate());
        if(form!=null && form.equalsIgnoreCase("edit")){
            if(remarksList!=null){
                viewHolder.etDate.setText(remarksList.date);
                viewHolder.etRemarks.setText(remarksList.remarksVal);
            }
        }
        if(isEnabled) {
            if (final_remarks_layout.getChildCount() > 1) {
                viewHolder.removeLayout.setVisibility(View.VISIBLE);
            } else {
                viewHolder.removeLayout.setVisibility(View.GONE);
            }
            viewHolder.addLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addFinalRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null),null,true);
                }
            });
            viewHolder.removeLayout.setTag(view);
            viewHolder.removeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final_remarks_layout.removeView((View) view.getTag());

                }
            });
        }else{
            viewHolder.addLayout.setVisibility(View.GONE);
            viewHolder.removeLayout.setVisibility(View.GONE);
            viewHolder.etRemarks.setEnabled(false);
        }
    }

    private void addCommercialRemark(View view,ComplaintsInsertReqVo.RemarksList remarksList,boolean isEnabled) {
        commercial_department_layout.addView(view);
        ViewHolderRemarks viewHolder = new ViewHolderRemarks(view);
        viewHolder.etDate.setText(Common.getCurrentDate());
        if(form!=null && form.equalsIgnoreCase("edit")){
            if(remarksList!=null){
                viewHolder.etDate.setText(remarksList.date);
                viewHolder.etRemarks.setText(remarksList.remarksVal);
            }
        }
        if(isEnabled) {
            if (commercial_department_layout.getChildCount() > 1) {
                viewHolder.removeLayout.setVisibility(View.VISIBLE);
            } else {
                viewHolder.removeLayout.setVisibility(View.GONE);
            }
            viewHolder.addLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addCommercialRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null),null,true);
                }
            });
            viewHolder.removeLayout.setTag(view);
            viewHolder.removeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    commercial_department_layout.removeView((View) view.getTag());

                }
            });
        }else{
            viewHolder.addLayout.setVisibility(View.GONE);
            viewHolder.removeLayout.setVisibility(View.GONE);
            viewHolder.etRemarks.setEnabled(false);
        }
    }

    private void addComplaintRemark(View view,ComplaintsInsertReqVo.RemarksList remarksList,boolean isEnabled) {
        complaint_receiver_layout.addView(view);
        ViewHolderRemarks viewHolder = new ViewHolderRemarks(view);
        viewHolder.etDate.setText(Common.getCurrentDate());
        if(form!=null && form.equalsIgnoreCase("edit")){
            if(remarksList!=null){
                viewHolder.etDate.setText(remarksList.date);
                viewHolder.etRemarks.setText(remarksList.remarksVal);
            }
        }
        if(isEnabled) {
            if (complaint_receiver_layout.getChildCount() > 1) {
                viewHolder.removeLayout.setVisibility(View.VISIBLE);
            } else {
                viewHolder.removeLayout.setVisibility(View.GONE);
            }
            viewHolder.addLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addComplaintRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null),null, true);
                }
            });
            viewHolder.removeLayout.setTag(view);
            viewHolder.removeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    complaint_receiver_layout.removeView((View) view.getTag());

                }
            });
        }else{
            viewHolder.addLayout.setVisibility(View.GONE);
            viewHolder.removeLayout.setVisibility(View.GONE);
            viewHolder.etRemarks.setEnabled(false);
        }
    }

    private void addSupervisorRemark(View view, ComplaintsInsertReqVo.RemarksList remarksList, boolean isEnabled) {
        by_supervisor_layout.addView(view);
        ViewHolderRemarks viewHolder = new ViewHolderRemarks(view);
        viewHolder.etDate.setText(Common.getCurrentDate());
        if(form!=null && form.equalsIgnoreCase("edit")){
            if(remarksList!=null){
                viewHolder.etDate.setText(remarksList.date);
                viewHolder.etRemarks.setText(remarksList.remarksVal);
            }
        }
        if (isEnabled) {
            if (by_supervisor_layout.getChildCount() > 1) {
                viewHolder.removeLayout.setVisibility(View.VISIBLE);
            } else {
                viewHolder.removeLayout.setVisibility(View.GONE);
            }
            viewHolder.addLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addSupervisorRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null),null, true);
                }
            });
            viewHolder.removeLayout.setTag(view);
            viewHolder.removeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    by_supervisor_layout.removeView((View) view.getTag());
                }
            });
        } else {
            viewHolder.addLayout.setVisibility(View.GONE);
            viewHolder.removeLayout.setVisibility(View.GONE);
            viewHolder.etRemarks.setEnabled(false);
        }
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
