package com.ncl.nclcustomerservice.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;
import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity;
import com.ncl.nclcustomerservice.adapter.CustomSpinnerAdapter;
import com.ncl.nclcustomerservice.application.MyApplication;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.customviews.CustomButton;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.network.RetrofitRequestController;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.CustomerContactResponseVo;
import com.ncl.nclcustomerservice.object.CustomerContractorInsertReqVo;
import com.ncl.nclcustomerservice.object.DepartmentList;
import com.ncl.nclcustomerservice.object.DropDownData;
import com.ncl.nclcustomerservice.object.DropDownDataReqVo;
import com.ncl.nclcustomerservice.object.ProjectHeadReqVo;
import com.ncl.nclcustomerservice.object.ProjectHeadVO;
import com.ncl.nclcustomerservice.object.RemarksListVO;
import com.ncl.nclcustomerservice.object.SpinnerModel;
import com.ncl.nclcustomerservice.object.StatesList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewContactActivity extends NetworkChangeListenerActivity implements RetrofitResponseListener {
    @BindView(R.id.parent)
    View parent;
    String form_type;
    int id;
    DatabaseHandler db;
    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.back_button)
    ImageView back_button;
    @BindView(R.id.radioButton1)
    RadioButton radioButton1;
    @BindView(R.id.radioButton2)
    RadioButton radioButton2;
    @BindView(R.id.radioGroupCustomer)
    RadioGroup radioGroupCustomer;
    @BindView(R.id.layout_contractor)
    View layout_contractor;
    @BindView(R.id.layout_projecthead)
    View layout_projecthead;
    //    contractor
    @BindView(R.id.tvContractorName)
    TextView tvContractorName;
    @BindView(R.id.etContractorName)
    EditText etContractorName;
    @BindView(R.id.tvContractorMobileNo)
    TextView tvContractorMobileNo;
    @BindView(R.id.etContractorMobileNo)
    EditText etContractorMobileNo;
    @BindView(R.id.tvContractorFirmName)
    TextView tvContractorFirmName;
    @BindView(R.id.etContractorFirmName)
    EditText etContractorFirmName;
    @BindView(R.id.tvAadharNo)
    TextView tvAadharNo;
    @BindView(R.id.etAadharNo)
    EditText etAadharNo;
    @BindView(R.id.tvPanNo)
    TextView tvPanNo;
    @BindView(R.id.etPanNo)
    EditText etPanNo;
    @BindView(R.id.tvGSTNo)
    TextView tvGSTNo;
    @BindView(R.id.etGSTNo)
    EditText etGSTNo;
    @BindView(R.id.tvTeamSizeNo)
    TextView tvTeamSizeNo;
    @BindView(R.id.etTeamSizeNo)
    EditText etTeamSizeNo;
    @BindView(R.id.tvNameOfBuilding)
    TextView tvNameOfBuilding;
    @BindView(R.id.etNameOfBuilding)
    EditText etNameOfBuilding;
    @BindView(R.id.tvState)
    TextView tvState;
    @BindView(R.id.state_spinner)
    SearchableSpinner state_spinner;
    @BindView(R.id.tvCity)
    TextView tvCity;
    @BindView(R.id.etCity)
    EditText etCity;
    @BindView(R.id.tvCountry)
    TextView tvCountry;
    @BindView(R.id.etCountry)
    EditText etCountry;
    @BindView(R.id.tvPincode)
    TextView tvPincode;
    @BindView(R.id.etPincode)
    EditText etPincode;
    @BindView(R.id.btn_cc_aadhar)
    Button btn_cc_aadhar;
    @BindView(R.id.adhar_cc_FileName)
    TextView adhar_cc_FileName;
    @BindView(R.id.btn_cc_pan)
    Button btn_cc_pan;
    @BindView(R.id.pan_cc_FileName)
    TextView pan_cc_FileName;
    @BindView(R.id.adhar_cc_editview)
    TextView adhar_cc_editview;
    @BindView(R.id.pan_cc_editview)
    TextView pan_cc_editview;
    @BindView(R.id.tvCCRemarks)
    TextView tvCCRemarks;
    @BindView(R.id.ll_CCRemarks)
    LinearLayout ll_CCRemarks;

    //    project head
    @BindView(R.id.tvCompanyName)
    TextView tvCompanyName;
    @BindView(R.id.etCompanyName)
    EditText etCompanyName;
    @BindView(R.id.tvProjectName)
    TextView tvProjectName;
    @BindView(R.id.etProjectName)
    EditText etProjectName;
    @BindView(R.id.tvClientCode)
    TextView tvClientCode;
    @BindView(R.id.etClientCode)
    EditText etClientCode;
    @BindView(R.id.tvProjectHeadName)
    TextView tvProjectHeadName;
    @BindView(R.id.etProjectHeadName)
    EditText etProjectHeadName;
    @BindView(R.id.tvMobile)
    TextView tvMobile;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.tvDepartment)
    TextView tvDepartment;
    @BindView(R.id.ph_department_spinner)
    SearchableSpinner phDepartmentSpinner;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.tvPHstate)
    TextView tvPHstate;
    @BindView(R.id.ph_state_spinner)
    SearchableSpinner ph_state_spinner;
    @BindView(R.id.tvPHcountry)
    TextView tvPHcountry;
    @BindView(R.id.etPHcountry)
    EditText etPHcountry;
    @BindView(R.id.tvPHpincode)
    TextView tvPHpincode;
    @BindView(R.id.etPHpincode)
    EditText etPHpincode;
    @BindView(R.id.tvPHRemarks)
    TextView tvPHRemarks;
    @BindView(R.id.ll_PHRemarks)
    LinearLayout ll_PHRemarks;

    @BindView(R.id.ll_contractor_team_member_details)
    LinearLayout ll_contractor_team_member_details;

    @BindView(R.id.ll_associate_contacts)
    LinearLayout ll_associate_contacts;

    @BindView(R.id.save)
    CustomButton save_button;
    @BindView(R.id.cancel)
    CustomButton cancel_button;
    boolean click_pan = false;
    boolean click_aadhar = false;
    boolean tm_click_aadhar = false;
    int tm_file_pos = 0;
    CustomerContractorInsertReqVo customerContractorInsertReqVo = new CustomerContractorInsertReqVo();
    List<CustomerContractorInsertReqVo.TeamMember> addTeamMemberList = new ArrayList<>();
    List<ProjectHeadReqVo.AssociateContact> associateContactList = new ArrayList<>();
    private ProgressDialog progressD;
    File con_pan_file, con_aadha_file;
    List<File> tm_aadhar_file_list = new ArrayList<>();
    private List<StatesList> statesLists;
    String stateId, departmentId;
    CustomerContactResponseVo.ContactContractorList contactContractorList;
    ProjectHeadReqVo projectHeadReqVo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_newcontact);
        ButterKnife.bind(this);
        Common.setupUI(parent, this);
        form_type = getIntent().getExtras().getString("form_key", "");
        id = getIntent().getExtras().getInt("id", 0);
        db = DatabaseHandler.getDatabase(this);
        title_text.setText("ADD CONTACT");
//        contractor
        tvContractorName.setText(Common.setSppanableText("* Contractor Name"));
        tvContractorMobileNo.setText(Common.setSppanableText("* Contractor Mobile No"));
//        tvContractorFirmName.setText(Common.setSppanableText("* Contractor Firm Name"));
        tvAadharNo.setText(Common.setSppanableText("* Aadhar Number"));
        tvPanNo.setText(Common.setSppanableText("* PAN Number"));
        tvTeamSizeNo.setText(Common.setSppanableText("* Team Size in nos"));
        tvNameOfBuilding.setText(Common.setSppanableText("* Name of building,Floor,Block no"));
        tvState.setText(Common.setSppanableText("* State"));
        tvCity.setText(Common.setSppanableText("* City"));
        tvCountry.setText(Common.setSppanableText("* Country"));
        tvPincode.setText(Common.setSppanableText("* Pincode"));
//        project head
        tvProjectHeadName.setText(Common.setSppanableText("* Project Head Name"));
        tvCompanyName.setText(Common.setSppanableText("* Company Name(Client name)"));
        tvProjectName.setText(Common.setSppanableText("* Project Name"));
        tvClientCode.setText(Common.setSppanableText("* Client code"));
        tvMobile.setText(Common.setSppanableText("* Mobile"));
        tvDepartment.setText(Common.setSppanableText("* Department"));
//        tvEmail.setText(Common.setSppanableText("* Email"));
        tvAddress.setText(Common.setSppanableText("* Address"));
        tvPHstate.setText(Common.setSppanableText("* State"));
        tvPHcountry.setText(Common.setSppanableText("* Country"));
        tvPHpincode.setText(Common.setSppanableText("* Pincode"));
//        tvPHRemarks.setText(Common.setSppanableText("* Remarks"));
//        tvCCRemarks.setText(Common.setSppanableText("* Remarks"));
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        View rowCCRemarks = getLayoutInflater().inflate(R.layout.remarks_row, null);


        radioGroupCustomer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton1) {
                    layout_projecthead.setVisibility(View.GONE);
                    layout_contractor.setVisibility(View.VISIBLE);
                    ll_PHRemarks.removeAllViews();
                    ll_CCRemarks.addView(rowCCRemarks);
                    addCCRemarks(null);
                } else if (checkedId == R.id.radioButton2) {
                    layout_projecthead.setVisibility(View.VISIBLE);
                    layout_contractor.setVisibility(View.GONE);
                    ll_CCRemarks.removeAllViews();
                    ll_PHRemarks.addView(rowCCRemarks);
                    addPHRemarks(null);
                }

            }
        });


        btn_cc_aadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_aadhar = true;
                click_pan = false;
                tm_click_aadhar = false;
                ImagePicker.Companion.with(CreateNewContactActivity.this)
                        .compress(1024)            //Final image size will be less than 1.0 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        btn_cc_pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_pan = true;
                click_aadhar = false;
                tm_click_aadhar = false;
                ImagePicker.Companion.with(CreateNewContactActivity.this)
                        .compress(1024)            //Final image size will be less than 1.0 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton1.isChecked()) {
                    if (!isContracorFieldsValidated()) {
                        return;
                    }
                    if (areContractorLineItemsValidated()) {
                        customerContractorInsertReqVo.requesterid = "" + Common.getUserIdFromSP(CreateNewContactActivity.this);
                        if (form_type.equalsIgnoreCase("edit")) {
                            if (contactContractorList != null) {
                                customerContractorInsertReqVo.contactContractorId = contactContractorList.contactContractorId;
                                customerContractorInsertReqVo.contactId = "" + contactContractorList.contactId;
                                customerContractorInsertReqVo.requestname = Constants.RequestNames.EDIT_CONTRACTOR_CONTACT;
                            }
                        } else {
                            customerContractorInsertReqVo.requestname = Constants.RequestNames.ADD_CONTRACTOR_CONTACT;
                        }
                        customerContractorInsertReqVo.category = "Contractor";
                        customerContractorInsertReqVo.contractorName = etContractorName.getText().toString().trim();
                        customerContractorInsertReqVo.contractorMobileNo = etContractorMobileNo.getText().toString().trim();
                        customerContractorInsertReqVo.contractorFirmName = etContractorFirmName.getText().toString().trim();
                        customerContractorInsertReqVo.contractorAadharNumber = etAadharNo.getText().toString().trim();
                        customerContractorInsertReqVo.contractorPanNumber = etPanNo.getText().toString().trim();
                        customerContractorInsertReqVo.contractorGstNumber = etGSTNo.getText().toString().trim();
                        customerContractorInsertReqVo.contractorTeamSize = etTeamSizeNo.getText().toString().trim();
                        customerContractorInsertReqVo.contractorAddress = etNameOfBuilding.getText().toString().trim();
                        customerContractorInsertReqVo.contractorCity = etCity.getText().toString().trim();
                        customerContractorInsertReqVo.contractorCountry = etCountry.getText().toString().trim();
                        customerContractorInsertReqVo.contractorPincode = etPincode.getText().toString().trim();
                        customerContractorInsertReqVo.contractorState = stateId;

                        List<CustomerContractorInsertReqVo.TeamMember> teamMemberList = getTeamMemberList();
                        if (teamMemberList == null || teamMemberList.size() == 0) {
                            Toast.makeText(CreateNewContactActivity.this, "Please add Team Member Details", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        customerContractorInsertReqVo.teamMember = teamMemberList;
                        customerContractorInsertReqVo.contactContractorRemarks = getCCRemarks();

                        sendImage(customerContractorInsertReqVo);

                    }

                } else if (radioButton2.isChecked()) {
                    if (!isProjectHeadFieldsValidated()) {
                        return;
                    }
//                    if (arePhAssociateContactsItemsValidated()) {
                    if (Common.haveInternet(getApplication())) {
                        ProjectHeadReqVo projectHeadReq = new ProjectHeadReqVo();
                        projectHeadReq.category = "Project Head";
                        projectHeadReq.projectHeadName = etProjectHeadName.getText().toString().trim();
                        projectHeadReq.projectName = etProjectName.getText().toString().trim();
                        projectHeadReq.clientCode = etClientCode.getText().toString().trim();
                        projectHeadReq.companyOrClientName = etCompanyName.getText().toString().trim();
                        projectHeadReq.projectHeadMobile = etMobile.getText().toString().trim();
                        projectHeadReq.projectHeadDepartment = departmentId;
                        projectHeadReq.projectHeadAddress = etAddress.getText().toString().trim();
                        projectHeadReq.projectHeadState = stateId;
                        projectHeadReq.projectHeadCountry = etPHcountry.getText().toString().trim();
                        projectHeadReq.projectHeadPincode = etPHpincode.getText().toString().trim();
                        projectHeadReq.remarksListVOS = getPHRemarks();
                        projectHeadReq.projectHeadEmail = etEmail.getText().toString().trim();
                        List<ProjectHeadReqVo.AssociateContact> associateContactList = getAssociateContactList();
//                            if (associateContactList == null || associateContactList.size() == 0) {
//                                Toast.makeText(CreateNewContactActivity.this, "Please add Associate Contact Details", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
                        projectHeadReq.associateContacts = associateContactList;
                        if (form_type.equalsIgnoreCase("edit")) {
                            projectHeadReq.contactId = projectHeadReqVo.contactId;
                            projectHeadReq.contactProjectHeadId = projectHeadReqVo.contactProjectHeadId;
                            new RetrofitRequestController(CreateNewContactActivity.this).sendRequest(Constants.RequestNames.EDIT_CONTACT_PROJECT_HEAD, projectHeadReq, true);
                        } else {
                            new RetrofitRequestController(CreateNewContactActivity.this).sendRequest(Constants.RequestNames.ADD_CONTACT_PROJECT_HEAD, projectHeadReq, true);
                        }
                    } else {
                        Toast.makeText(getApplication(), "Please check internet connection", Toast.LENGTH_LONG).show();
                    }
//                    }
                }


            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DropDownDataReqVo dropDownDataReqVo = new DropDownDataReqVo();
        dropDownDataReqVo.usersList = "users_list";
        dropDownDataReqVo.customerList = "customer_list";
        dropDownDataReqVo.teamId = Common.getTeamUserIdFromSP(this);
        new RetrofitRequestController(this).sendRequest(Constants.RequestNames.DROP_DOWN_LIST, dropDownDataReqVo, true);
        if (form_type.equalsIgnoreCase("edit")) {
            title_text.setText("EDIT CONTACT");
            contactContractorList = (CustomerContactResponseVo.ContactContractorList) getIntent().getSerializableExtra("contactContractorList");
            projectHeadReqVo = (ProjectHeadReqVo) getIntent().getSerializableExtra("contactProjectHeadList");
            if (contactContractorList != null && contactContractorList.category.equalsIgnoreCase("Contractor")) {
                radioButton1.setChecked(true);
                layout_projecthead.setVisibility(View.GONE);
                layout_contractor.setVisibility(View.VISIBLE);
                setEditContactorData(contactContractorList);
            } else if (projectHeadReqVo != null && projectHeadReqVo.category.equalsIgnoreCase("Project Head")) {
                radioButton2.setChecked(true);
                layout_projecthead.setVisibility(View.VISIBLE);
                layout_contractor.setVisibility(View.GONE);
                setEditProjectHeadData(projectHeadReqVo);
            }
        } else {
            View rowView = getLayoutInflater().inflate(R.layout.contractor_team_member_details_row, null);
            ll_contractor_team_member_details.addView(rowView);
            addContractorTeamMemberLayout(null);

            View rowAssociateContacts = getLayoutInflater().inflate(R.layout.associate_contacts_row, null);
            ll_associate_contacts.addView(rowAssociateContacts);
            addAssociateContacts(null);


        }
    }

    private void addPHRemarks(List<RemarksListVO> projectHeadRemarks) {
        for (int i = 0; i < ll_PHRemarks.getChildCount(); i++) {
            View ll_PHRemarks_view = ll_PHRemarks.getChildAt(i);
            RemarksViewHolder viewHolder = new RemarksViewHolder(ll_PHRemarks_view);
            if (ll_PHRemarks.getChildCount() > 1) {
                viewHolder.removelayout_remarks.setVisibility(View.VISIBLE);
            } else {
                viewHolder.removelayout_remarks.setVisibility(View.GONE);
            }
            if (projectHeadRemarks != null) {
                viewHolder.etRemarks.setText(projectHeadRemarks.get(i).remark);
            }
            int finalI = i;
            viewHolder.removelayout_remarks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_PHRemarks.removeViewAt(finalI);
                    addPHRemarks(null);
                }
            });
            viewHolder.addlayout_remarks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_PHRemarks.addView(getLayoutInflater().inflate(R.layout.remarks_row, null));
                    addPHRemarks(null);
                }
            });

        }
    }

    private void addCCRemarks(List<RemarksListVO> ccRemarks) {
        for (int i = 0; i < ll_CCRemarks.getChildCount(); i++) {
            View ll_CCRemarks_view = ll_CCRemarks.getChildAt(i);
            RemarksViewHolder viewHolder = new RemarksViewHolder(ll_CCRemarks_view);
            if (ll_CCRemarks.getChildCount() > 1) {
                viewHolder.removelayout_remarks.setVisibility(View.VISIBLE);
            } else {
                viewHolder.removelayout_remarks.setVisibility(View.GONE);
            }
            if (ccRemarks != null) {
                viewHolder.etRemarks.setText(ccRemarks.get(i).remark);
            }
            int finalI = i;
            viewHolder.removelayout_remarks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_CCRemarks.removeViewAt(finalI);
                    addCCRemarks(null);
                }
            });
            viewHolder.addlayout_remarks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_CCRemarks.addView(getLayoutInflater().inflate(R.layout.remarks_row, null));
                    addCCRemarks(null);
                }
            });

        }
    }

    private void setEditProjectHeadData(ProjectHeadReqVo projectHeadReqVo) {
        if (projectHeadReqVo != null) {
            etProjectHeadName.setText(projectHeadReqVo.projectHeadName);
            etProjectName.setText(projectHeadReqVo.projectName);
            etClientCode.setText(projectHeadReqVo.clientCode);
            etCompanyName.setText(projectHeadReqVo.companyOrClientName);
            etMobile.setText(projectHeadReqVo.projectHeadMobile);
            etEmail.setText(projectHeadReqVo.projectHeadEmail);
            etAddress.setText(projectHeadReqVo.projectHeadAddress);
            etPHcountry.setText(projectHeadReqVo.projectHeadCountry);
            etPHpincode.setText(projectHeadReqVo.projectHeadPincode);
            ll_PHRemarks.removeAllViews();
            if (projectHeadReqVo.remarksListVOS != null && projectHeadReqVo.remarksListVOS.size() > 0) {
                for (int i = 0; i < projectHeadReqVo.remarksListVOS.size(); i++) {
                    View rowView = getLayoutInflater().inflate(R.layout.remarks_row, null);
                    ll_PHRemarks.addView(rowView);
                }
                addPHRemarks(projectHeadReqVo.remarksListVOS);
            } else {
                View rowView = getLayoutInflater().inflate(R.layout.remarks_row, null);
                ll_PHRemarks.addView(rowView);
                addPHRemarks(null);
            }

            if (projectHeadReqVo.associateContacts != null && projectHeadReqVo.associateContacts.size() > 0) {
                for (int i = 0; i < projectHeadReqVo.associateContacts.size(); i++) {
                    View rowView = getLayoutInflater().inflate(R.layout.associate_contacts_row, null);
                    ll_associate_contacts.addView(rowView);

                }
                addAssociateContacts(projectHeadReqVo.associateContacts);
            } else {
                View rowAssociateContacts = getLayoutInflater().inflate(R.layout.associate_contacts_row, null);
                ll_associate_contacts.addView(rowAssociateContacts);
                addAssociateContacts(null);
            }
        }
    }

    private void setEditContactorData(CustomerContactResponseVo.ContactContractorList contactContractorList) {
        if (contactContractorList != null) {
            etContractorName.setText(contactContractorList.contractorName);
            etContractorMobileNo.setText(contactContractorList.contractorMobileNo);
            etContractorFirmName.setText(contactContractorList.contractorFirmName);
            etAadharNo.setText(contactContractorList.contractorAadharNumber);
            etPanNo.setText(contactContractorList.contractorPanNumber);
            etGSTNo.setText(contactContractorList.contractorGstNumber);
            etTeamSizeNo.setText(contactContractorList.contractorTeamSize);
            etNameOfBuilding.setText(contactContractorList.contractorAddress);
            etCity.setText(contactContractorList.contractorCity);
            etPincode.setText(contactContractorList.contractorPincode);
            ll_CCRemarks.removeAllViews();
            if (contactContractorList.remarksListVOS != null && contactContractorList.remarksListVOS.size() > 0) {
                for (int i = 0; i < contactContractorList.remarksListVOS.size(); i++) {
                    View rowView = getLayoutInflater().inflate(R.layout.remarks_row, null);
                    ll_CCRemarks.addView(rowView);
                }
                addCCRemarks(contactContractorList.remarksListVOS);
            }

            String aadharPath = contactContractorList.contractorAadharImagePath;
            if (aadharPath != null) {
                String aadharFilename = aadharPath.substring(aadharPath.lastIndexOf("/") + 1);
                adhar_cc_FileName.setText(aadharFilename);
                adhar_cc_editview.setVisibility(View.VISIBLE);
                adhar_cc_editview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CreateNewContactActivity.this, ViewImageActivity.class);
                        intent.putExtra("ImagePath", contactContractorList.contractorAadharImagePath);
                        startActivity(intent);
                    }
                });
            }
            String panPath = contactContractorList.contractorPanImagePath;
            if (panPath != null) {
                String panFilename = panPath.substring(panPath.lastIndexOf("/") + 1);
                pan_cc_FileName.setText(panFilename);
                pan_cc_editview.setVisibility(View.VISIBLE);
                pan_cc_editview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CreateNewContactActivity.this, ViewImageActivity.class);
                        intent.putExtra("ImagePath", contactContractorList.contractorPanImagePath);
                        startActivity(intent);
                    }
                });
            }
            if (contactContractorList.teamMembers != null && contactContractorList.teamMembers.size() > 0) {
                for (int i = 0; i < contactContractorList.teamMembers.size(); i++) {
                    View rowView = getLayoutInflater().inflate(R.layout.contractor_team_member_details_row, null);
                    ll_contractor_team_member_details.addView(rowView);

                }
                addContractorTeamMemberLayout(contactContractorList.teamMembers);
            }
        }
    }

    private void sendImage(CustomerContractorInsertReqVo obj) {
        progressD = new ProgressDialog(this);
        progressD.setMessage("Please Wait....");
        progressD.setCancelable(false);

        List<MultipartBody.Part> muPartList = new ArrayList<>();
        if (con_aadha_file != null && con_aadha_file.length() > 5) {
            muPartList.add(prepareFilePart("contractor_aadhar_image_path[]", Uri.fromFile(con_aadha_file), con_aadha_file));
        }
        if (con_pan_file != null && con_pan_file.length() > 5) {
            muPartList.add(prepareFilePart("contractor_pan_image_path[]", Uri.fromFile(con_pan_file), con_pan_file));
        }
        for (int i = 0; i < tm_aadhar_file_list.size(); i++) {
            if (tm_aadhar_file_list.get(i) != null && tm_aadhar_file_list.get(i).length() > 5) {
                muPartList.add(prepareFilePart("team_member_aadhar_image_path[" + i + "]", Uri.fromFile(tm_aadhar_file_list.get(i)), tm_aadhar_file_list.get(i)));
            }
        }
        Common.Log.i("Request obj " + new Gson().toJson(obj));
       if(!form_type.equalsIgnoreCase("edit")) {
           if (muPartList.size() > 0) {
               progressD.show();
               MultipartBody.Part[] fileParts = muPartList.toArray(new MultipartBody.Part[muPartList.size()]);
               Call<ResponseBody> abc = MyApplication.getInstance().getAPIInterface().uploadPaymentCollection(Constants.API, fileParts, obj);
               abc.enqueue(new Callback<ResponseBody>() {
                   @Override
                   public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                       if (response.body() == null) {
                           Toast.makeText(CreateNewContactActivity.this, "Intenal Server Error", Toast.LENGTH_SHORT).show();
                           progressD.dismiss();
                           return;
                       }
                       Toast.makeText(CreateNewContactActivity.this, "New contact inserted successfully.", Toast.LENGTH_SHORT).show();

                       ApiResponseController apiResponseController = null;
                       try {
                           apiResponseController = new Gson().fromJson(response.body().string(), ApiResponseController.class);
                           CustomerContactResponseVo customerContactResponseVo = Common.getSpecificDataObject(apiResponseController.result, CustomerContactResponseVo.class);
                           if (customerContactResponseVo != null) {
                               List<CustomerContactResponseVo.ContactContractorList> contactContractorList = customerContactResponseVo.contactList;
                               if (contactContractorList != null && contactContractorList.size() > 0) {
                                   db.commonDao().insertContractorContact(contactContractorList);
                                   Intent intent = new Intent(CreateNewContactActivity.this, NewContactViewActivity.class);
                                   intent.putExtra("contactContractorList", contactContractorList.get(0));
                                   intent.putExtra("type", "Contractor");
                                   startActivity(intent);
                                   finish();
                               }
                           }

                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       progressD.dismiss();
                       finish();
                   }

                   @Override
                   public void onFailure(Call<ResponseBody> call, Throwable t) {
                       progressD.dismiss();
                   }
               });
           } else {
               Toast.makeText(CreateNewContactActivity.this, "Please upload required files.", Toast.LENGTH_SHORT).show();
           }
       }else {
           progressD.show();
           MultipartBody.Part[] fileParts = muPartList.toArray(new MultipartBody.Part[muPartList.size()]);
           Call<ResponseBody> abc = MyApplication.getInstance().getAPIInterface().uploadPaymentCollection(Constants.API, fileParts, obj);
           abc.enqueue(new Callback<ResponseBody>() {
               @Override
               public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                   if (response.body() == null) {
                       Toast.makeText(CreateNewContactActivity.this, "Intenal Server Error", Toast.LENGTH_SHORT).show();
                       progressD.dismiss();
                       return;
                   }
                   Toast.makeText(CreateNewContactActivity.this, "New contact inserted successfully.", Toast.LENGTH_SHORT).show();

                   ApiResponseController apiResponseController = null;
                   try {
                       apiResponseController = new Gson().fromJson(response.body().string(), ApiResponseController.class);
                       CustomerContactResponseVo customerContactResponseVo = Common.getSpecificDataObject(apiResponseController.result, CustomerContactResponseVo.class);
                       if (customerContactResponseVo != null) {
                           List<CustomerContactResponseVo.ContactContractorList> contactContractorList = customerContactResponseVo.contactList;
                           if (contactContractorList != null && contactContractorList.size() > 0) {
                               db.commonDao().insertContractorContact(contactContractorList);
                               Intent intent = new Intent(CreateNewContactActivity.this, NewContactViewActivity.class);
                               intent.putExtra("contactContractorList", contactContractorList.get(0));
                               intent.putExtra("type", "Contractor");
                               startActivity(intent);
                               finish();
                           }
                       }

                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   progressD.dismiss();
                   finish();
               }

               @Override
               public void onFailure(Call<ResponseBody> call, Throwable t) {
                   progressD.dismiss();
               }
           });
       }


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

    private String getMimeType(CreateNewContactActivity context, Uri uri) {
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

    private List<ProjectHeadReqVo.AssociateContact> getAssociateContactList() {
        associateContactList.clear();
        for (int i = 0; i < ll_associate_contacts.getChildCount(); i++) {
            View childView = ll_associate_contacts.getChildAt(i);
            AssociateContactViewHolder loopHolder = new AssociateContactViewHolder(childView);
            ProjectHeadReqVo.AssociateContact associateContact = new ProjectHeadReqVo.AssociateContact();
            if (form_type.equalsIgnoreCase("edit")) {
                if (projectHeadReqVo.associateContacts.size() > i) {
                    associateContact.contactProjectheadAssociatecontactId = projectHeadReqVo.associateContacts.get(i).contactProjectheadAssociatecontactId;
                    associateContact.contactId = projectHeadReqVo.associateContacts.get(i).contactId;
                }
            }

            associateContact.contactProjectHeadAssociateContactName = loopHolder.etACName.getText().toString().trim();
            associateContact.contactProjectHeadAssociateContactDesignation = loopHolder.etACDesignation.getText().toString().trim();
            associateContact.contactProjectHeadAssociateContactMobile = loopHolder.etACMobile.getText().toString().trim();
            associateContactList.add(associateContact);
        }
        if (associateContactList.size() == 1) {
            if (associateContactList.get(0).contactProjectHeadAssociateContactName.isEmpty()) {
                return new ArrayList<>();
            }
        }
        return associateContactList;
    }

    private ArrayList<RemarksListVO> getPHRemarks() {
        ArrayList<RemarksListVO> remarkslist = new ArrayList<>();
        for (int i = 0; i < ll_PHRemarks.getChildCount(); i++) {
            View childView = ll_PHRemarks.getChildAt(i);
            RemarksViewHolder loopHolder = new RemarksViewHolder(childView);
            RemarksListVO remarksListVO = new RemarksListVO();
            if (form_type.equalsIgnoreCase("edit")) {
                if (projectHeadReqVo.remarksListVOS.size() > i) {
                    remarksListVO.id = projectHeadReqVo.remarksListVOS.get(i).id;
                }
            }
            if (loopHolder.etRemarks.getText().toString().length() > 0) {
                remarksListVO.remark = loopHolder.etRemarks.getText().toString();
                remarkslist.add(remarksListVO);
            }
        }
        return remarkslist;
    }

    private ArrayList<RemarksListVO> getCCRemarks() {
        ArrayList<RemarksListVO> remarkslist = new ArrayList<>();
        for (int i = 0; i < ll_CCRemarks.getChildCount(); i++) {
            View childView = ll_CCRemarks.getChildAt(i);
            RemarksViewHolder loopHolder = new RemarksViewHolder(childView);
            RemarksListVO remarksListVO = new RemarksListVO();
            if (form_type.equalsIgnoreCase("edit")) {
                if (contactContractorList.remarksListVOS.size() > i) {
                    remarksListVO.id = contactContractorList.remarksListVOS.get(i).id;
                }
            }
            if (loopHolder.etRemarks.getText().toString().length() > 0) {
                remarksListVO.remark = loopHolder.etRemarks.getText().toString();
                remarkslist.add(remarksListVO);
            }
        }
        return remarkslist;
    }

    private List<CustomerContractorInsertReqVo.TeamMember> getTeamMemberList() {
        addTeamMemberList.clear();
        for (int i = 0; i < ll_contractor_team_member_details.getChildCount(); i++) {
            View childView = ll_contractor_team_member_details.getChildAt(i);
            ContractorTeamMemViewHolder loopHolder = new ContractorTeamMemViewHolder(childView);
            CustomerContractorInsertReqVo.TeamMember teamMember = new CustomerContractorInsertReqVo.TeamMember();
            if (contactContractorList != null && contactContractorList.teamMembers != null) {
                if (contactContractorList.teamMembers.get(i).contactContractorTeamId != null) {
                    teamMember.contactContractorTeamId = contactContractorList.teamMembers.get(i).contactContractorTeamId;
                }
            }

            teamMember.teamMemberName = loopHolder.etTeamMemberName.getText().toString().trim();
            teamMember.teamMemberMobileNo = loopHolder.etTeamMemberMobileNo.getText().toString().trim();
            teamMember.teammemberAadharNumber = loopHolder.etCoAadharNo.getText().toString().trim();
            addTeamMemberList.add(teamMember);
        }

        return addTeamMemberList;
    }

    private boolean arePhAssociateContactsItemsValidated() {
        boolean isFilled = true;
        for (int i = 0; i < ll_associate_contacts.getChildCount(); i++) {
            AssociateContactViewHolder associateContactViewHolder = new AssociateContactViewHolder(ll_associate_contacts.getChildAt(i));
            if (associateContactViewHolder.etACName.getText().toString().trim().length() == 0) {
                associateContactViewHolder.etACName.setError("Please enter Name");
                associateContactViewHolder.etACName.requestFocus();
                isFilled = false;
            }
            if (associateContactViewHolder.etACDesignation.getText().toString().trim().length() == 0) {
                associateContactViewHolder.etACDesignation.setError("Please enter Designation");
                associateContactViewHolder.etACDesignation.requestFocus();
                isFilled = false;
            }
            if (associateContactViewHolder.etACMobile.getText().toString().trim().length() == 0) {
                associateContactViewHolder.etACMobile.setError("Please enter Mobile No");
                associateContactViewHolder.etACMobile.requestFocus();
                isFilled = false;
            }
//            return isFilled;
        }
        return isFilled;
    }

    private boolean areContractorLineItemsValidated() {
        boolean isFilled = true;
        for (int i = 0; i < ll_contractor_team_member_details.getChildCount(); i++) {
            ContractorTeamMemViewHolder loopHolder = new ContractorTeamMemViewHolder(ll_contractor_team_member_details.getChildAt(i));

            if (loopHolder.etTeamMemberName.getText().toString().trim().length() == 0) {
                loopHolder.etTeamMemberName.setError("please enter team member name");
                loopHolder.etTeamMemberName.requestFocus();
                isFilled = false;
            }
            if (loopHolder.etTeamMemberMobileNo.getText().toString().trim().length() == 0) {
                loopHolder.etTeamMemberMobileNo.setError("please enter mobile number");
                loopHolder.etTeamMemberMobileNo.requestFocus();
                isFilled = false;
            }
//            if (loopHolder.etCoAadharNo.getText().toString().trim().length() == 0 || loopHolder.etCoAadharNo.getText().toString().trim().length() < 12) {
//                loopHolder.etCoAadharNo.setError("please enter valid Aadhar No.");
//                loopHolder.etCoAadharNo.requestFocus();
//                isFilled = false;
//            }
//            if (loopHolder.tv_file_name.getText().toString().trim().length() == 0) {
//                Toast.makeText(CreateNewContactActivity.this, "Please upload Aadhar card", Toast.LENGTH_SHORT).show();
//                isFilled = false;
//            }
//            return isFilled;
        }
        return isFilled;
    }

    private boolean isProjectHeadFieldsValidated() {
        boolean isFilled = true;
        if (etCompanyName.getText().toString().trim().length() == 0) {
            etCompanyName.requestFocus();
            etCompanyName.setError("Please add Company Name");
            isFilled = false;
        } else if (etProjectName.getText().toString().trim().length() == 0) {
            etProjectName.requestFocus();
            etProjectName.setError("Please add Project Name");
            isFilled = false;
        } else if (etClientCode.getText().toString().trim().length() == 0) {
            etClientCode.requestFocus();
            etClientCode.setError("Please add Client code");
            isFilled = false;
        } else if (etProjectHeadName.getText().toString().trim().length() == 0) {
            etProjectHeadName.requestFocus();
            etProjectHeadName.setError("Please add Project Head Name");
            isFilled = false;
        } else if (etMobile.getText().toString().trim().length() == 0 || etMobile.getText().toString().trim().length() < 10) {
            etMobile.requestFocus();
            etMobile.setError("Please add Mobile No");
            isFilled = false;
        }
//        else if (etEmail.getText().toString().trim().length() == 0) {
//            etEmail.requestFocus();
//            etEmail.setError("Please add Email");
//            isFilled = false;
//        }
        else if (etAddress.getText().toString().trim().length() == 0) {
            etAddress.requestFocus();
            etAddress.setError("Please add Address");
            isFilled = false;
        } else if (etPHcountry.getText().toString().trim().length() == 0) {
            etPHcountry.requestFocus();
            etPHcountry.setError("Please add Country");
            isFilled = false;
        } else if (etPHpincode.getText().toString().trim().length() == 0) {
            etPHpincode.requestFocus();
            etPHpincode.setError("Please add Pincode");
            isFilled = false;
        }
        return isFilled;
    }

    private boolean isContracorFieldsValidated() {
        boolean isFilled = true;
        if (etContractorName.getText().toString().trim().length() == 0) {
            etContractorName.requestFocus();
            etContractorName.setError("Please add Contractor Name");
            isFilled = false;
        } else if (etContractorMobileNo.getText().toString().trim().length() == 0 || etContractorMobileNo.getText().toString().trim().length() < 10) {
            etContractorMobileNo.requestFocus();
            etContractorMobileNo.setError("Please add Contractor Mobile No");
            isFilled = false;
        }
//        else if (etContractorFirmName.getText().toString().trim().length() == 0) {
//            etContractorFirmName.requestFocus();
//            etContractorFirmName.setError("Please add Contractor Firm Name");
//            isFilled = false;
//        }
        else if (etAadharNo.getText().toString().trim().length() == 0 || etAadharNo.getText().toString().trim().length() < 12) {
            etAadharNo.requestFocus();
            etAadharNo.setError("Please enter valid Aadhar number");
            isFilled = false;
        } else if (etPanNo.getText().toString().trim().length() == 0 || etPanNo.getText().toString().trim().length() < 10) {
            etPanNo.requestFocus();
            etPanNo.setError("Please enter valid PAN number");
            isFilled = false;
        }
//        else if (etGSTNo.getText().toString().trim().length() == 0) {
//            etGSTNo.requestFocus();
//            etGSTNo.setError("Please add GST Number");
//            isFilled = false;
//        }
        else if (etTeamSizeNo.getText().toString().trim().length() == 0) {
            etTeamSizeNo.requestFocus();
            etTeamSizeNo.setError("Please add Team Size");
            isFilled = false;
        } else if (etNameOfBuilding.getText().toString().trim().length() == 0) {
            etNameOfBuilding.requestFocus();
            etNameOfBuilding.setError("Please add Name of Building");
            isFilled = false;
        } else if (etCity.getText().toString().trim().length() == 0) {
            etCity.requestFocus();
            etCity.setError("Please add City");
            isFilled = false;
        } else if (etPincode.getText().toString().trim().length() == 0) {
            etPincode.requestFocus();
            etPincode.setError("Please add Pincode");
            isFilled = false;
        } else if (!form_type.equalsIgnoreCase("edit") && (con_aadha_file == null || con_aadha_file.length() < 5)) {
            isFilled = false;
            Toast.makeText(CreateNewContactActivity.this, "Please upload your Aadhar", Toast.LENGTH_SHORT).show();
        } else if (!form_type.equalsIgnoreCase("edit") && (con_pan_file == null || con_pan_file.length() < 5)) {
            isFilled = false;
            Toast.makeText(CreateNewContactActivity.this, "Please upload your PAN", Toast.LENGTH_SHORT).show();
        }
        return isFilled;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK && data != null) {
                if (click_aadhar) {
                    File file = ImagePicker.Companion.getFile(data);
                    String firstlink1 = file.getAbsolutePath().subSequence(0, file.getAbsolutePath().lastIndexOf('/')).toString();
                    con_aadha_file = new File(file.getAbsolutePath()); // Assuming it is in Internal Storage
                    System.out.println("## firstlink:" + firstlink1);
                    adhar_cc_FileName.setText(file.getName());
                }
                if (click_pan) {
                    File file = ImagePicker.Companion.getFile(data);
                    String firstlink1 = file.getAbsolutePath().subSequence(0, file.getAbsolutePath().lastIndexOf('/')).toString();
                    con_pan_file = new File(file.getAbsolutePath()); // Assuming it is in Internal Storage
                    System.out.println("## firstlink:" + firstlink1);
                    pan_cc_FileName.setText(file.getName());
                }
                if (tm_click_aadhar) {
                    File file = ImagePicker.Companion.getFile(data);
                    File tm_aadhar_file = new File(file.getAbsolutePath());
                    setTMAadharFileName(tm_aadhar_file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    private void setTMAadharFileName(File fileName) {
        for (int i = 0; i < ll_contractor_team_member_details.getChildCount(); i++) {
            if (i == tm_file_pos) {
                View childView = ll_contractor_team_member_details.getChildAt(i);
                ContractorTeamMemViewHolder loopHolder = new ContractorTeamMemViewHolder(childView);
                loopHolder.tv_file_name.setText(fileName.getName());
                tm_aadhar_file_list.add(i, fileName);
            }
        }

    }

    private void addAssociateContacts(List<ProjectHeadReqVo.AssociateContact> associateContacts) {
        for (int i = 0; i < ll_associate_contacts.getChildCount(); i++) {
            View ll_associate_contacts_view = ll_associate_contacts.getChildAt(i);
            AssociateContactViewHolder viewHolder = new AssociateContactViewHolder(ll_associate_contacts_view);
//            viewHolder.tvACName.setText(Common.setSppanableText("* Name"));
//            viewHolder.tvACDesignation.setText(Common.setSppanableText("* Designation"));
//            viewHolder.tvACMobile.setText(Common.setSppanableText("* Mobile"));

            if (ll_associate_contacts.getChildCount() > 1) {
                viewHolder.removeLayout_ac.setVisibility(View.VISIBLE);
            } else {
                viewHolder.removeLayout_ac.setVisibility(View.GONE);
            }
            if (associateContacts != null) {
                viewHolder.etACName.setText(associateContacts.get(i).contactProjectHeadAssociateContactName);
                viewHolder.etACDesignation.setText(associateContacts.get(i).contactProjectHeadAssociateContactDesignation);
                viewHolder.etACMobile.setText(associateContacts.get(i).contactProjectHeadAssociateContactMobile);
            }
            int finalI = i;
            viewHolder.removeLayout_ac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_associate_contacts.removeViewAt(finalI);
                    addAssociateContacts(null);
                }
            });
            viewHolder.addLayout_ac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AssociateContactViewHolder associateContactViewHolder = new AssociateContactViewHolder(ll_associate_contacts.getChildAt(ll_associate_contacts.getChildCount() - 1));
                    if (isAssociateLineItemValidated(associateContactViewHolder)) {
                        ll_associate_contacts.addView(getLayoutInflater().inflate(R.layout.associate_contacts_row, null));
                        addAssociateContacts(null);
                    }


                }
            });

        }
    }

    private void addContractorTeamMemberLayout(List<CustomerContactResponseVo.TeamMemberResVo> teamMemberResVo) {
        for (int i = 0; i < ll_contractor_team_member_details.getChildCount(); i++) {
            View contractor_team_member_details_view = ll_contractor_team_member_details.getChildAt(i);
            ContractorTeamMemViewHolder viewHolder = new ContractorTeamMemViewHolder(contractor_team_member_details_view);
            viewHolder.tvTeamMemberName.setText(Common.setSppanableText("* Team Member Name"));
            viewHolder.tvTeamMemberMobileNo.setText(Common.setSppanableText("* Team Member Mobile no"));
            viewHolder.tvCoAadharNo.setText(Common.setSppanableText("* Aadhar Number"));
            if (ll_contractor_team_member_details.getChildCount() > 1) {
                viewHolder.removeLayout.setVisibility(View.VISIBLE);
            } else {
                viewHolder.removeLayout.setVisibility(View.GONE);
            }
            if (teamMemberResVo != null) {
                //This is for edit
                viewHolder.etTeamMemberName.setText(teamMemberResVo.get(i).teamMemberName);
                viewHolder.etTeamMemberMobileNo.setText(teamMemberResVo.get(i).teamMemberMobileNo);
                viewHolder.etCoAadharNo.setText(teamMemberResVo.get(i).teammemberAadharNumber);
                String path = teamMemberResVo.get(i).teamMemberAadharImagePath;
                if (path != null) {
                    String filename = path.substring(path.lastIndexOf("/") + 1);
                    viewHolder.tv_file_name.setText(filename);
                    viewHolder.adhar_tm_editview.setVisibility(View.VISIBLE);
                    viewHolder.adhar_tm_editview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CreateNewContactActivity.this, ViewImageActivity.class);
                            intent.putExtra("ImagePath", path);
                            startActivity(intent);
                        }
                    });
                }
            }
            int finalI = i;
            viewHolder.btn_choose_file.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click_aadhar = false;
                    click_pan = false;
                    tm_click_aadhar = true;
                    tm_file_pos = finalI;
                    try {
                        ImagePicker.Companion.with(CreateNewContactActivity.this)
                                .compress(1024)            //Final image size will be less than 1.0 MB(Optional)
                                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                                .start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            viewHolder.removeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_contractor_team_member_details.removeViewAt(finalI);
                    if (tm_aadhar_file_list.size() >= finalI) {
                        tm_aadhar_file_list.remove(finalI);
                    }
                    addContractorTeamMemberLayout(null);
                }
            });
            viewHolder.addLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContractorTeamMemViewHolder contractorTeamMemViewHolder = new ContractorTeamMemViewHolder(ll_contractor_team_member_details.getChildAt(ll_contractor_team_member_details.getChildCount() - 1));
                    if (isLineItemValidated(contractorTeamMemViewHolder)) {
                        ll_contractor_team_member_details.addView(getLayoutInflater().inflate(R.layout.contractor_team_member_details_row, null));
                        addContractorTeamMemberLayout(null);
                    }
                }
            });
        }
    }

    private boolean isAssociateLineItemValidated(AssociateContactViewHolder viewHolder) {
        if (viewHolder.etACName.getText().toString().trim().length() == 0) {
//            viewHolder.etACName.setError("please enter name");
//            viewHolder.etACName.requestFocus();
            return false;
        }
        if (viewHolder.etACDesignation.getText().toString().trim().length() == 0) {
//            viewHolder.etACDesignation.setError("please enter designation");
//            viewHolder.etACDesignation.requestFocus();
            return false;
        }
        if (viewHolder.etACMobile.getText().toString().trim().length() == 0) {
//            viewHolder.etACMobile.setError("please enter mobile number");
//            viewHolder.etACMobile.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isLineItemValidated(ContractorTeamMemViewHolder viewHolder) {
        if (viewHolder.etTeamMemberName.getText().toString().trim().length() == 0) {
            viewHolder.etTeamMemberName.setError("please enter team member name");
            viewHolder.etTeamMemberName.requestFocus();
            return false;
        }
        if (viewHolder.etTeamMemberMobileNo.getText().toString().trim().length() == 0) {
            viewHolder.etTeamMemberMobileNo.requestFocus();
            return false;
        }
//        if (viewHolder.etCoAadharNo.getText().toString().trim().length() == 0 || viewHolder.etCoAadharNo.getText().toString().trim().length() < 12) {
//            viewHolder.etCoAadharNo.setError("please enter valid Aadhar No.");
//            viewHolder.etCoAadharNo.requestFocus();
//            return false;
//        }
//        if (viewHolder.tv_file_name.getText().toString().trim().length() == 0) {
//            Toast.makeText(CreateNewContactActivity.this, "Please upload Aadhar card", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;
    }

    @Override
    protected void onInternetConnected() {

    }

    @Override
    protected void onInternetDisconnected() {

    }

    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {
        try {
            switch (objectResponse.requestname) {
                case Constants.RequestNames.DROP_DOWN_LIST:
                    DropDownData dropDownData = Common.getSpecificDataObject(objectResponse.result, DropDownData.class);
                    if (dropDownData != null) {
                        loadDepatmentSpinner(dropDownData.departmentLists);
                        statesLists = dropDownData.statesList;
                        if (statesLists != null) {
                            ArrayList<mListString> stringStates = new ArrayList<>();
                            StatesList sl = new StatesList();
                            sl.stateId = "0";
                            sl.stateName = "Select State";
                            statesLists.add(0, sl);
                            List<SpinnerModel> states = new ArrayList<>();
                            for (int i = 0; i < statesLists.size(); i++) {
                                SpinnerModel spinnerModel = new SpinnerModel();
                                spinnerModel.setId(statesLists.get(i).stateId);
                                spinnerModel.setTitle(statesLists.get(i).stateName);
                                states.add(spinnerModel);
                                stringStates.add(new mListString(Integer.parseInt(Common.isNull(statesLists.get(i).stateId)), statesLists.get(i).stateName));
                            }
//                            CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(this, 0, states);
                            state_spinner.setAdapter(stringStates, 1, 1);
                            state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (i > 0)
                                        stateId = statesLists.get(i).stateName;
                                    else stateId = "";

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                            ph_state_spinner.setAdapter(stringStates, 1, 1);
                            ph_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (i > 0)
                                        stateId = statesLists.get(i).stateName;
                                    else stateId = "";

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                            if (form_type.equalsIgnoreCase("edit")) {
                                if (statesLists != null) {
                                    if (contactContractorList != null && contactContractorList.category.equalsIgnoreCase("Contractor")) {
                                        for (int i = 0; i < statesLists.size(); i++) {
                                            if (Common.nullChecker(contactContractorList.contractorState).equalsIgnoreCase(statesLists.get(i).stateName)) {
                                                state_spinner.setSelection(i);
                                                break;
                                            }
                                        }
                                    } else if (projectHeadReqVo != null && projectHeadReqVo.category.equalsIgnoreCase("Project Head")) {
                                        for (int i = 0; i < statesLists.size(); i++) {
                                            if (Common.nullChecker(projectHeadReqVo.projectHeadState).equalsIgnoreCase(statesLists.get(i).stateName)) {
                                                ph_state_spinner.setSelection(i);
                                                break;
                                            }
                                        }
                                    }

                                }

                            }
                        }
                    } else {
                        Toast.makeText(this, "Drop Down Data is Empty.", Toast.LENGTH_LONG).show();
                    }
                    break;
                case Constants.RequestNames.ADD_CONTACT_PROJECT_HEAD:
                case Constants.RequestNames.EDIT_CONTACT_PROJECT_HEAD:
                    try {
                        if (objectResponse.result == null) {
                            Toast.makeText(CreateNewContactActivity.this, "Intenal Server Error", Toast.LENGTH_SHORT).show();
                            progressD.dismiss();
                            return;
                        }
                        Toast.makeText(CreateNewContactActivity.this, "New Project Head Contact inserted successfully.", Toast.LENGTH_SHORT).show();

                        ProjectHeadVO projectHeadContactListResVo = Common.getSpecificDataObject(objectResponse.result, ProjectHeadVO.class);
                        if (projectHeadContactListResVo != null) {
                            List<ProjectHeadReqVo> projectHeadReqVoList = projectHeadContactListResVo.list;
                            if (projectHeadReqVoList != null && projectHeadReqVoList.size() > 0) {
                                db.commonDao().insertProjectHeadContact(projectHeadReqVoList);
                                Intent intent = new Intent(CreateNewContactActivity.this, NewContactViewActivity.class);
                                intent.putExtra("contactProjectHeadList", projectHeadReqVoList.get(0));
                                intent.putExtra("type", "ProjectHead");
                                startActivity(intent);
                                finish();
                            }
                        }
                        progressDialog.dismiss();
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
            }
            Common.dismissProgressDialog(progressDialog);
        } catch (Exception e) {
            Common.disPlayExpection(e, progressDialog);
        }

    }

    private void loadDepatmentSpinner(List<DepartmentList> departmentLists) {
        if (departmentLists != null) {
            ArrayList<mListString> stringDepartments = new ArrayList<>();
            DepartmentList dl = new DepartmentList();
            dl.departmentId = "0";
            dl.departmentName = "Select Department";
            departmentLists.add(0, dl);
            List<SpinnerModel> departSpinner = new ArrayList<>();
            for (int i = 0; i < departmentLists.size(); i++) {
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setId(departmentLists.get(i).departmentId);
                spinnerModel.setTitle(departmentLists.get(i).departmentName);
                departSpinner.add(spinnerModel);
                stringDepartments.add(new mListString(Integer.parseInt(Common.isNull(departmentLists.get(i).departmentId)), departmentLists.get(i).departmentName));
            }
            phDepartmentSpinner.setAdapter(stringDepartments, 1, 1);
            phDepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i > 0)
                        departmentId = departmentLists.get(i).departmentName;
                    else departmentId = "";

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            if (form_type.equalsIgnoreCase("edit")) {
                if (projectHeadReqVo != null && projectHeadReqVo.category.equalsIgnoreCase("Project Head")) {
                    for (int i = 0; i < departmentLists.size(); i++) {
                        if (Common.nullChecker(projectHeadReqVo.projectHeadDepartment).equalsIgnoreCase(departmentLists.get(i).departmentName)) {
                            phDepartmentSpinner.setSelection(i);
                            break;
                        }
                    }
                }
            }
        }
    }

    static class RemarksViewHolder {
        @BindView(R.id.etDate)
        EditText etDate;
        @BindView(R.id.etRemarks)
        EditText etRemarks;
        @BindView(R.id.addlayout_remarks)
        LinearLayout addlayout_remarks;
        @BindView(R.id.removelayout_remarks)
        LinearLayout removelayout_remarks;

        public RemarksViewHolder(View rowView) {
            ButterKnife.bind(this, rowView);
        }
    }

    static class AssociateContactViewHolder {
        @BindView(R.id.tvACName)
        TextView tvACName;
        @BindView(R.id.etACName)
        EditText etACName;
        @BindView(R.id.tvACDesignation)
        TextView tvACDesignation;
        @BindView(R.id.etACDesignation)
        EditText etACDesignation;
        @BindView(R.id.tvACMobile)
        TextView tvACMobile;
        @BindView(R.id.etACMobile)
        EditText etACMobile;
        @BindView(R.id.addlayout_ac)
        LinearLayout addLayout_ac;
        @BindView(R.id.removelayout_ac)
        LinearLayout removeLayout_ac;

        public AssociateContactViewHolder(View rowView) {
            ButterKnife.bind(this, rowView);
        }
    }

    static class ContractorTeamMemViewHolder {
        @BindView(R.id.tvTeamMemberName)
        TextView tvTeamMemberName;
        @BindView(R.id.etTeamMemberName)
        EditText etTeamMemberName;
        @BindView(R.id.tvTeamMemberMobileNo)
        TextView tvTeamMemberMobileNo;
        @BindView(R.id.etTeamMemberMobileNo)
        EditText etTeamMemberMobileNo;
        @BindView(R.id.tvCoAadharNo)
        TextView tvCoAadharNo;
        @BindView(R.id.etCoAadharNo)
        EditText etCoAadharNo;
        @BindView(R.id.btn_choose_file)
        Button btn_choose_file;
        @BindView(R.id.tv_file_name)
        TextView tv_file_name;
        @BindView(R.id.adhar_tm_editview)
        TextView adhar_tm_editview;
        @BindView(R.id.addlayout)
        LinearLayout addLayout;
        @BindView(R.id.removelayout)
        LinearLayout removeLayout;

        public ContractorTeamMemViewHolder(View rowView) {
            ButterKnife.bind(this, rowView);
        }
    }
}
