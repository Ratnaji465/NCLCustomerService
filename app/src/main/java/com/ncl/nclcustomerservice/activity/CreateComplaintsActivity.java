package com.ncl.nclcustomerservice.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity;
import com.ncl.nclcustomerservice.adapter.CustomSpinnerAdapter;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.customviews.CustomButton;
import com.ncl.nclcustomerservice.customviews.CustomEditText;
import com.ncl.nclcustomerservice.customviews.CustomTextView;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.network.RetrofitRequestController;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.ComplaintRegisterMasterVo;
import com.ncl.nclcustomerservice.object.DivisionMasterList;
import com.ncl.nclcustomerservice.object.DropDownData;
import com.ncl.nclcustomerservice.object.FabUnitList;
import com.ncl.nclcustomerservice.object.NatureOfComplaintList;
import com.ncl.nclcustomerservice.object.ProjectTypeList;
import com.ncl.nclcustomerservice.object.SpinnerModel;

import java.util.ArrayList;
import java.util.List;

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
    String form = "";
    DatabaseHandler db;
    String profileName;

    @BindView(R.id.tv_complaint_date)
    CustomTextView tv_complaint_date;
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

    String selectedProductType,selectedDivisionMaster,selectedFabUnit,selectedNatureOfComplaint;

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
        addSupervisorRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null));
        addComplaintRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null));
        addCommercialRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null));
        addFinalRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null));
    }


    private void loadDataIntoFields() {
        tv_complaint_date.setText(Common.getCurrentDate());
        List<ProjectTypeList> projectTypeLists = db.commonDao().getAllProjectTypeList();
        List<DivisionMasterList> divisionMasterLists = db.commonDao().getAllDivisionMasterList();
        List<FabUnitList> fabUnitLists = db.commonDao().getAllFabUnitList();
        List<NatureOfComplaintList> natureOfComplaintLists = db.commonDao().getAllNatureOfComplaintList();
        List<SpinnerModel> projectType = new ArrayList<>();
        List<SpinnerModel> divisionMaster = new ArrayList<>();
        List<SpinnerModel> fabUnit = new ArrayList<>();
        List<SpinnerModel> natureOfComplaint = new ArrayList<>();
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
        CustomSpinnerAdapter productTypeAdapter = new CustomSpinnerAdapter(this, 0, projectType);
        spnr_project_type.setAdapter(productTypeAdapter);
        spnr_project_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedProductType = projectTypeLists.get(position).project_type_name;
                }else {
                    selectedProductType ="";
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
                    selectedDivisionMaster = divisionMasterLists.get(position).division_name;
                }else {
                    selectedDivisionMaster ="";
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
                    selectedFabUnit = fabUnitLists.get(position).fab_unit_name;
                }else {
                    selectedFabUnit ="";
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
                    selectedNatureOfComplaint = natureOfComplaintLists.get(position).nature_of_complaint_name;
                }else {
                    selectedNatureOfComplaint ="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void findViewSetHint() {
        tv_oa_no_head.setText(Common.setSppanableText("* OA NO"));
        tv_client_name_head.setText(Common.setSppanableText("* Client Name"));
        tv_project_type_head.setText(Common.setSppanableText("* Project Type"));
        tv_product_head.setText(Common.setSppanableText("* Product"));
        tv_Marketing_officer_head.setText(Common.setSppanableText("* Marketing officer name"));
        tv_fab_unit_head.setText(Common.setSppanableText("* Fab Unit/ Departments"));
        tv_Nature_of_compaint_head.setText(Common.setSppanableText("* Nature of complaint"));
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
                addFinalRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null));
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
                addCommercialRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null));
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
                addComplaintRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null));
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
                addSupervisorRemark(getLayoutInflater().inflate(R.layout.add_lead_note, null));
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
