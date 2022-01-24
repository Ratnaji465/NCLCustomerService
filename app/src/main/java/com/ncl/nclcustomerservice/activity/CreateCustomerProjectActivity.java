package com.ncl.nclcustomerservice.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;
import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity;
import com.ncl.nclcustomerservice.adapter.CustomSpinnerAdapter;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.network.RetrofitRequestController;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.CustomerContactResponseVo;
import com.ncl.nclcustomerservice.object.DropDownData;
import com.ncl.nclcustomerservice.object.DropDownDataReqVo;
import com.ncl.nclcustomerservice.object.ProjectHeadReqVo;
import com.ncl.nclcustomerservice.object.SpinnerModel;
import com.ncl.nclcustomerservice.object.StatesList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateCustomerProjectActivity extends NetworkChangeListenerActivity implements RetrofitResponseListener {
    @BindView(R.id.parent)
    View parent;
    String form_type;
    int id;
    DatabaseHandler db;
    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.back_button)
    ImageView back_button;
    @BindView(R.id.tvProjectName)
    TextView tvProjectName;
    @BindView(R.id.etProjectName)
    EditText etProjectName;
    @BindView(R.id.tvProjectAddress)
    TextView tvProjectAddress;
    @BindView(R.id.etProjectAddress)
    EditText etProjectAddress;
    @BindView(R.id.tvState)
    TextView tvState;
    @BindView(R.id.state_spinner)
    SearchableSpinner state_spinner;
    @BindView(R.id.tvCountry)
    TextView tvCountry;
    @BindView(R.id.etCountry)
    EditText etCountry;
    @BindView(R.id.tvPinCode)
    TextView tvPinCode;
    @BindView(R.id.etPincode)
    EditText etPincode;
    @BindView(R.id.tvProjectHeadName)
    TextView tvProjectHeadName;
    @BindView(R.id.ProjectHeadName_spinner)
    Spinner ProjectHeadName_spinner;
    @BindView(R.id.tvPHMobile)
    TextView tvPHMobile;
    @BindView(R.id.etPHMobile)
    EditText etPHMobile;
    @BindView(R.id.tvPHDepartment)
    TextView tvPHDepartment;
    @BindView(R.id.etPHDepartment)
    EditText etPHDepartment;
    @BindView(R.id.tvPHCompanyName)
    TextView tvPHCompanyName;
    @BindView(R.id.etPHCompanyName)
    EditText etPHCompanyName;
    @BindView(R.id.tvTeamSizeNo)
    TextView tvTeamSizeNo;
    @BindView(R.id.etTeamSizeNo)
    EditText etTeamSizeNo;

    @BindView(R.id.ll_associate_contacts)
    LinearLayout ll_associate_contacts;

    @BindView(R.id.ll_contractor_details)
    LinearLayout ll_contractor_details1;

    @BindView(R.id.ll_contractor_tm_details)
    LinearLayout ll_contractor_tm_details;


    List<ProjectHeadReqVo.AssociateContact> associateContactForSpinnerList;

    List<CustomerContactResponseVo.ContactContractorList> contactContractorLists;
    List<ProjectHeadReqVo> projectHeadReqVoList;

    List<CustomerContactResponseVo.TeamMemberResVo> teamMemberResVoList;
    List<CustomerContactResponseVo.TeamMemberResVo> newTeamMemberResVoList = new ArrayList<>();

    public List<StatesList> statesLists;
    String stateId;

    ContractorContactViewHolder contractorContactViewHolder;

    @Override
    protected void onInternetConnected() {
    }

    @Override
    protected void onInternetDisconnected() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customerproject);
        ButterKnife.bind(this);
        Common.setupUI(parent, this);
        form_type = getIntent().getExtras().getString("form_key", "");
        id = getIntent().getExtras().getInt("id", 0);
        db = DatabaseHandler.getDatabase(this);
        title_text.setText("CUSTOMER PROJECT INFORMATION");

// Associate Contact Details
        projectHeadReqVoList = db.commonDao().getAllProjectHeadContactList();
        ProjectHeadReqVo projectHeadReqVo = new ProjectHeadReqVo();
        projectHeadReqVo.contactProjectHeadId = "0";
        projectHeadReqVo.contactId = "0";
        projectHeadReqVo.projectHeadName = "Select";
        projectHeadReqVoList.add(0, projectHeadReqVo);
        View rowView = getLayoutInflater().inflate(R.layout.associate_contacts_row, null);
        rowView.findViewById(R.id.etACName).setVisibility(View.GONE);
        rowView.findViewById(R.id.ph_name_spinner).setVisibility(View.VISIBLE);
        ll_associate_contacts.addView(rowView);
        ProjectContactAdapter projectContactAdapter = new ProjectContactAdapter(getApplication(), 0, projectHeadReqVoList);
        ProjectHeadName_spinner.setAdapter(projectContactAdapter);
        ProjectHeadName_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    etPHMobile.setText("");
                    etPHDepartment.setText("");
                    etPHCompanyName.setText("");
                } else {
                    associateContactForSpinnerList = new ArrayList<>();
                    String phName = projectHeadReqVoList.get(position).projectHeadName;
                    etPHMobile.setText(projectHeadReqVoList.get(position).projectHeadMobile);
                    etPHDepartment.setText(projectHeadReqVoList.get(position).projectHeadDepartment);
                    etPHCompanyName.setText(projectHeadReqVoList.get(position).companyOrClientName);
                    associateContactForSpinnerList.clear();
                    associateContactForSpinnerList.addAll(projectHeadReqVoList.get(position).associateContacts);
                    addProjectContactDetails();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


// Contractor Details
        contactContractorLists = db.commonDao().getAllCustomerContactList();
        CustomerContactResponseVo.ContactContractorList contactContractorList = new CustomerContactResponseVo.ContactContractorList();
        contactContractorList.contactContractorId = "0";
        contactContractorList.contactId = 0;
        contactContractorList.contractorName = "Select";
        contactContractorLists.add(0, contactContractorList);
        View contractorRowView = getLayoutInflater().inflate(R.layout.contractor_details_row, null);
        ll_contractor_details1.addView(contractorRowView);
        addContractorDetails(ll_contractor_details1, contractorRowView);


        DropDownDataReqVo dropDownDataReqVo = new DropDownDataReqVo();
        dropDownDataReqVo.usersList = "users_list";
        dropDownDataReqVo.customerList = "customer_list";
        dropDownDataReqVo.teamId = Common.getTeamUserIdFromSP(this);
        new RetrofitRequestController(this).sendRequest(Constants.RequestNames.DROP_DOWN_LIST, dropDownDataReqVo, true);
    }

    private void addContractorTMDetails() {
        for (int i = 0; i < ll_contractor_tm_details.getChildCount(); i++) {
            View ll_contractor_details_view = ll_contractor_tm_details.getChildAt(i);
            ContractorTMViewHolder contractorTMViewHolder = new ContractorTMViewHolder(ll_contractor_details_view);
            contractorTMViewHolder.tvTeamMemberName.setText(Common.setSppanableText("* Team Member Name"));
            contractorTMViewHolder.tvTeamMemberMobileNo.setText(Common.setSppanableText("* Team Member Mobile"));
            contractorTMViewHolder.tvCoAadharNo.setText(Common.setSppanableText("* Team Member Aadhar Number"));
            contractorTMViewHolder.etTeamMemberName.setText(newTeamMemberResVoList.get(i).teamMemberName);
            contractorTMViewHolder.etTeamMemberMobileNo.setText(newTeamMemberResVoList.get(i).teamMemberMobileNo);
            contractorTMViewHolder.etCoAadharNo.setText(newTeamMemberResVoList.get(i).teammemberAadharNumber);

            int finalI = i;
            contractorTMViewHolder.removelayout_ctm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_contractor_tm_details.removeViewAt(finalI);
                    newTeamMemberResVoList.remove(finalI);
                    addContractorTMDetails();
                }
            });
        }
    }

    private void addContractorDetails(LinearLayout ll_contractor_details, View view) {
//     for(int i=0;i<ll_contractor_details.getChildCount();i++){
//         View ll_contractor_details_view = ll_contractor_details.getChildAt(i);
        if (view != null) {
            ContractorContactViewHolder contractorContactViewHolder = new ContractorContactViewHolder(view);
            contractorContactViewHolder.tvACName.setText(Common.setSppanableText("* Name"));
            contractorContactViewHolder.tvACMobile.setText(Common.setSppanableText("* Mobile"));
            if (ll_contractor_details.getChildCount() > 1) {
                contractorContactViewHolder.removelayout_cc.setVisibility(View.VISIBLE);
            } else {
                contractorContactViewHolder.removelayout_cc.setVisibility(View.GONE);
            }
//         int finalI = i;
            contractorContactViewHolder.removelayout_cc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_contractor_details.removeView(v);
//                 ll_contractor_details.removeViewAt(finalI);
                    addContractorDetails(ll_contractor_details, null);
                }
            });
            contractorContactViewHolder.addlayout_cc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View rowView1 = getLayoutInflater().inflate(R.layout.contractor_details_row, null);
                    ll_contractor_details.addView(rowView1);
                    addContractorDetails(ll_contractor_details, rowView1);
                }
            });

            ContractorDetailsAdapter contractorDetailsAdapter = new ContractorDetailsAdapter(getApplication(), 0, contactContractorLists);
            contractorContactViewHolder.contractor_name_spinner.setAdapter(contractorDetailsAdapter);
            contractorContactViewHolder.contractor_name_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        contractorContactViewHolder.etContractorMobile.setText(contactContractorLists.get(position).contractorMobileNo);
                        if (contactContractorLists.size() > 0) {
                            teamMemberResVoList = contactContractorLists.get(position).teamMembers;
                            newTeamMemberResVoList.addAll(teamMemberResVoList);
                            ll_contractor_tm_details.removeAllViewsInLayout();
                            for (int i = 0; i < newTeamMemberResVoList.size(); i++) {
                                View contractorTMRowView = getLayoutInflater().inflate(R.layout.contractor_team_member_details_row1, null);
                                ll_contractor_tm_details.addView(contractorTMRowView);
                            }
                            addContractorTMDetails();
                        }
                    }
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }

    private void addProjectContactDetails() {

        for (int i = 0; i < ll_associate_contacts.getChildCount(); i++) {
            View ll_associate_contacts_view = ll_associate_contacts.getChildAt(i);
            AssociateContactViewHolder viewHolder = new AssociateContactViewHolder(ll_associate_contacts_view);
            viewHolder.tvACName.setText(Common.setSppanableText("* Name"));
            viewHolder.tvACDesignation.setText(Common.setSppanableText("* Designation"));
            viewHolder.tvACMobile.setText(Common.setSppanableText("* Mobile"));

            if (ll_associate_contacts.getChildCount() > 1) {
                viewHolder.removeLayout_ac.setVisibility(View.VISIBLE);
            } else {
                viewHolder.removeLayout_ac.setVisibility(View.GONE);
            }
            int finalI = i;
            viewHolder.removeLayout_ac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_associate_contacts.removeViewAt(finalI);
                    addProjectContactDetails();
                }
            });
            viewHolder.addLayout_ac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View rowView1 = getLayoutInflater().inflate(R.layout.associate_contacts_row, null);
                    rowView1.findViewById(R.id.etACName).setVisibility(View.GONE);
                    rowView1.findViewById(R.id.ph_name_spinner).setVisibility(View.VISIBLE);
                    ll_associate_contacts.addView(rowView1);
                    addProjectContactDetails();
                }
            });
            if (associateContactForSpinnerList.size() > 0) {
                ProjectHeadReqVo.AssociateContact associateContact = new ProjectHeadReqVo.AssociateContact();
                associateContact.contactProjectHeadAssociateContactName = "Select";
                associateContact.contactProjectheadAssociatecontactId = "0";
                associateContact.contactId = "0";
                associateContactForSpinnerList.add(0, associateContact);
                AssociateContactAdapter associateContactAdapter = new AssociateContactAdapter(getApplication(), 0, associateContactForSpinnerList);
                viewHolder.ph_name_spinner.setAdapter(associateContactAdapter);
                viewHolder.ph_name_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position > 0) {
                            viewHolder.etACMobile.setText(associateContactForSpinnerList.get(position).contactProjectHeadAssociateContactMobile);
                            viewHolder.etACDesignation.setText(associateContactForSpinnerList.get(position).contactProjectHeadAssociateContactDesignation);
                        } else {
                            viewHolder.etACMobile.setText("");
                            viewHolder.etACDesignation.setText("");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        }
    }

    public static class ContractorTMViewHolder {
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

        @BindView(R.id.removelayout_ctm)
        LinearLayout removelayout_ctm;

        public ContractorTMViewHolder(View rowView) {
            ButterKnife.bind(this, rowView);
            etTeamMemberName.setEnabled(false);
            etTeamMemberMobileNo.setEnabled(false);
            etCoAadharNo.setEnabled(false);
        }
    }

    public static class ContractorContactViewHolder {
        @BindView(R.id.tvACName)
        TextView tvACName;
        @BindView(R.id.contractor_name_spinner)
        Spinner contractor_name_spinner;
        @BindView(R.id.tvACMobile)
        TextView tvACMobile;
        @BindView(R.id.etContractorMobile)
        EditText etContractorMobile;
        @BindView(R.id.addlayout_cc)
        LinearLayout addlayout_cc;
        @BindView(R.id.removelayout_cc)
        LinearLayout removelayout_cc;

        public ContractorContactViewHolder(View rowView) {
            ButterKnife.bind(this, rowView);
            etContractorMobile.setEnabled(false);
        }
    }


    public class AssociateContactViewHolder {
        @BindView(R.id.tvACName)
        TextView tvACName;
        @BindView(R.id.ph_name_spinner)
        Spinner ph_name_spinner;
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
            etACDesignation.setEnabled(false);
            etACMobile.setEnabled(false);
        }
    }

    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {
        try {
            switch (objectResponse.requestname) {
                case Constants.RequestNames.DROP_DOWN_LIST:
                    DropDownData dropDownData = Common.getSpecificDataObject(objectResponse.result, DropDownData.class);
                    if (dropDownData != null) {
                        statesLists = dropDownData.statesList;
                        if (statesLists != null) {
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
                            }
                            CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(this, 0, states);
                            state_spinner.setAdapter(customSpinnerAdapter);
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

//                            if (form_type.equalsIgnoreCase("edit")) {
//                                if (statesLists != null) {
//                                    if (contactContractorList != null && contactContractorList.category.equalsIgnoreCase("Contractor")) {
//                                        for (int i = 0; i < statesLists.size(); i++) {
//                                            if (Common.nullChecker(contactContractorList.contractorState).equalsIgnoreCase(statesLists.get(i).stateName)) {
//                                                state_spinner.setSelection(i);
//                                                break;
//                                            }
//                                        }
//                                    } else if (projectHeadReqVo != null && projectHeadReqVo.category.equalsIgnoreCase("Project Head")) {
//                                        for (int i = 0; i < statesLists.size(); i++) {
//                                            if (Common.nullChecker(projectHeadReqVo.projectHeadState).equalsIgnoreCase(statesLists.get(i).stateName)) {
//                                                ph_state_spinner.setSelection(i);
//                                                break;
//                                            }
//                                        }
//                                    }
//
//                                }
//
//                            }
                        }
                    } else {
                        Toast.makeText(this, "Drop Down Data is Empty.", Toast.LENGTH_LONG).show();
                    }
                    break;
//                case Constants.RequestNames.ADD_CONTACT_PROJECT_HEAD:
//                case Constants.RequestNames.EDIT_CONTACT_PROJECT_HEAD:
//                    try {
//                        if (objectResponse.result == null) {
//                            Toast.makeText(CreateNewContactActivity.this, "Intenal Server Error", Toast.LENGTH_SHORT).show();
//                            progressD.dismiss();
//                            return;
//                        }
//                        Toast.makeText(CreateNewContactActivity.this, "New Project Head Contact inserted successfully.", Toast.LENGTH_SHORT).show();
//
//                        ProjectHeadVO projectHeadContactListResVo = Common.getSpecificDataObject(objectResponse.result, ProjectHeadVO.class);
//                        if (projectHeadContactListResVo != null) {
//                            List<ProjectHeadReqVo> projectHeadReqVoList = projectHeadContactListResVo.list;
//                            if (projectHeadReqVoList != null && projectHeadReqVoList.size() > 0) {
//                                db.commonDao().insertProjectHeadContact(projectHeadReqVoList);
//                                Intent intent = new Intent(CreateNewContactActivity.this, NewContactViewActivity.class);
//                                intent.putExtra("contactProjectHeadList", projectHeadReqVoList.get(0));
//                                intent.putExtra("type", "ProjectHead");
//                                startActivity(intent);
//                                finish();
//                            }
//                        }
//                        progressDialog.dismiss();
//                        finish();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    break;
            }
            Common.dismissProgressDialog(progressDialog);
        } catch (Exception e) {
            Common.disPlayExpection(e, progressDialog);
        }
    }

    private class AssociateContactAdapter extends ArrayAdapter<String> {
        private Context context;
        private List<ProjectHeadReqVo.AssociateContact> associateContactList;
        LayoutInflater inflater;
        ProjectHeadReqVo.AssociateContact tempValues = null;

        public AssociateContactAdapter(@NonNull Context context, int textViewResourceId, List associateContactList) {
            super(context, textViewResourceId, associateContactList);
            this.context = context;
            this.associateContactList = associateContactList;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            View row = inflater.inflate(R.layout.spinner_rows, parent, false);
            /***** Get each Model object from Arraylist ********/
            tempValues = (ProjectHeadReqVo.AssociateContact) associateContactList.get(position);

            TextView label = (TextView) row.findViewById(R.id.spinnertitle);

            // Set values for spinner each row
            label.setText(tempValues.contactProjectHeadAssociateContactName);
            label.setTag(tempValues.contactProjectheadAssociatecontactId);


            return row;
        }
    }

    private class ContractorDetailsAdapter extends ArrayAdapter<String> {
        private Context context;
        LayoutInflater inflater;
        List<CustomerContactResponseVo.ContactContractorList> contactContractorLists;
        CustomerContactResponseVo.ContactContractorList tempValues = null;

        public ContractorDetailsAdapter(@NonNull Context context, int textViewResourceId, List contractorDetailsList) {
            super(context, textViewResourceId, contractorDetailsList);
            this.context = context;
            this.contactContractorLists = contractorDetailsList;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            View row = inflater.inflate(R.layout.spinner_rows, parent, false);
            /***** Get each Model object from Arraylist ********/
            tempValues = (CustomerContactResponseVo.ContactContractorList) contactContractorLists.get(position);
            TextView label = (TextView) row.findViewById(R.id.spinnertitle);
            // Set values for spinner each row
            label.setText(tempValues.contractorName);
            label.setTag(tempValues.contactContractorId);
            return row;
        }

    }

    private class ProjectContactAdapter extends ArrayAdapter<String> {
        private Context context;
        private List<ProjectHeadReqVo> projectHeadReqVoList;
        LayoutInflater inflater;
        ProjectHeadReqVo tempValues = null;

        public ProjectContactAdapter(@NonNull Context context, int textViewResourceId, List projectHeadReqVoList) {
            super(context, textViewResourceId, projectHeadReqVoList);
            this.context = context;
            this.projectHeadReqVoList = projectHeadReqVoList;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            View row = inflater.inflate(R.layout.spinner_rows, parent, false);
            /***** Get each Model object from Arraylist ********/
            tempValues = (ProjectHeadReqVo) projectHeadReqVoList.get(position);

            TextView label = (TextView) row.findViewById(R.id.spinnertitle);

            // Set values for spinner each row
            label.setText(tempValues.projectHeadName);
            label.setTag(tempValues.contactProjectHeadId);


            return row;
        }
    }


}
