package com.ncl.nclcustomerservice.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;
import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.network.RetrofitRequestController;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.ContactDeleteReqVo;
import com.ncl.nclcustomerservice.object.ContactList;
import com.ncl.nclcustomerservice.object.CustomerContactResponseVo;
import com.ncl.nclcustomerservice.object.LeftNav;
import com.ncl.nclcustomerservice.object.ListReqVo;
import com.ncl.nclcustomerservice.object.ProjectHeadReqVo;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewContactViewActivity extends NetworkChangeListenerActivity implements RetrofitResponseListener {
    //    contractor contact
    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.back_button)
    ImageView back_button;
    @BindView(R.id.coCategory)
    TextView coCategory;
    @BindView(R.id.coNumber)
    TextView coNumber;
    @BindView(R.id.coContractorName)
    TextView coContractorName;
    @BindView(R.id.coMobileNo)
    TextView coMobileNo;
    @BindView(R.id.coFirmName)
    TextView coFirmName;
    @BindView(R.id.coAadharNo)
    TextView coAadharNo;
    @BindView(R.id.coPanNo)
    TextView coPanNo;
    @BindView(R.id.coGSTNo)
    TextView coGSTNo;
    @BindView(R.id.coTeamSize)
    TextView coTeamSize;
    @BindView(R.id.coRemarks)
    TextView coRemarks;
    @BindView(R.id.iv_aadhar)
    ImageView iv_aadhar;
    @BindView(R.id.iv_pan)
    ImageView iv_pan;
    @BindView(R.id.coAddress)
    TextView coAddress;
    @BindView(R.id.coState)
    TextView coState;
    @BindView(R.id.coCity)
    TextView coCity;
    @BindView(R.id.coCountry)
    TextView coCountry;
    @BindView(R.id.coPincode)
    TextView coPincode;
    @BindView(R.id.ll_contractor_team_member_details)
    LinearLayout ll_contractor_team_member_details;
    @BindView(R.id.edit_linear)
    LinearLayout edit_linear;
    @BindView(R.id.pv_aadhar)
    PDFView pv_aadhar;
    @BindView(R.id.pv_pan)
    PDFView pv_pan;

    //    Project Head contact
    @BindView(R.id.phcategory)
    TextView phcategory;
    @BindView(R.id.phNumber)
    TextView phNumber;
    @BindView(R.id.phName)
    TextView phName;
    @BindView(R.id.phMobileNo)
    TextView phMobileNo;
    @BindView(R.id.phCompanyName)
    TextView phCompanyName;
    @BindView(R.id.phDepartment)
    TextView phDepartment;
    @BindView(R.id.phEmail)
    TextView phEmail;
    @BindView(R.id.phAddress)
    TextView phAddress;
    @BindView(R.id.phState)
    TextView phState;
    @BindView(R.id.phCountry)
    TextView phCountry;
    @BindView(R.id.phPincode)
    TextView phPincode;
    @BindView(R.id.phRemarks)
    TextView phRemarks;
    @BindView(R.id.ll_associate_details)
    LinearLayout ll_associate_details;


    @BindView(R.id.ll_contractor_contact)
    View ll_contractor_contact;
    @BindView(R.id.ll_project_head_contact)
    View ll_project_head_contact;

    CustomerContactResponseVo.ContactContractorList contactContractorList;
    ProjectHeadReqVo projectHeadReqVo;
    String checkType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact_details);
        ButterKnife.bind(this);
        checkType = (String) getIntent().getSerializableExtra("type");
        if (checkType.equalsIgnoreCase("Contractor")) {
            title_text.setText("CONTRACTOR CONTACT");
            ll_contractor_contact.setVisibility(View.VISIBLE);
            contactContractorList = (CustomerContactResponseVo.ContactContractorList) getIntent().getSerializableExtra("contactContractorList");
            coCategory.setText(contactContractorList.category);
            coNumber.setText(contactContractorList.contractorMobileNo);
            coContractorName.setText(contactContractorList.contractorName);
            coMobileNo.setText(contactContractorList.contractorMobileNo);
            coFirmName.setText(contactContractorList.contractorFirmName);
            coAadharNo.setText(contactContractorList.contractorAadharNumber);
            coPanNo.setText(contactContractorList.contractorPanNumber);
            coGSTNo.setText(contactContractorList.contractorGstNumber);
            coTeamSize.setText(contactContractorList.contractorTeamSize);
            coRemarks.setText(contactContractorList.contactContractorRemarks);

            try {
                String aadharPath=contactContractorList.contractorAadharImagePath;
                if(aadharPath!=null){
                    String aadharFilename = aadharPath.substring(aadharPath.lastIndexOf("/") + 1);
                    if(!aadharFilename.contains(".pdf")){
                        iv_aadhar.setVisibility(View.VISIBLE);
                        Glide.with(this)
                                .load(aadharPath)
                                .error(R.drawable.ic_profile)
                                .into(iv_aadhar);
//                    Picasso.with(this)
//                            .load(contactContractorList.contractorAadharImagePath)
//                            .error(R.drawable.ic_profile)
//                            .into(iv_aadhar);
                    }else {
                        pv_aadhar.setVisibility(View.VISIBLE);
                        new RetriveAadharPDFfromUrl().execute(aadharPath);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            try {
                String panPath=contactContractorList.contractorPanImagePath;
                if(panPath!=null){
                    String panFilename = panPath.substring(panPath.lastIndexOf("/") + 1);
                    if(!panFilename.contains(".pdf")){
                        iv_pan.setVisibility(View.VISIBLE);
                        Glide.with(this)
                                .load(panPath)
                                .error(R.drawable.ic_profile)
                                .into(iv_pan);
                    }else {
                        pv_pan.setVisibility(View.VISIBLE);
                        new RetrivePanPDFfromUrl().execute(panPath);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }





            coAddress.setText(contactContractorList.contractorAddress);
            coState.setText(contactContractorList.contractorState);
            coCity.setText(contactContractorList.contractorCity);
            coCountry.setText(contactContractorList.contractorCountry);
            coPincode.setText(contactContractorList.contractorPincode);

            if (contactContractorList.teamMembers != null && contactContractorList.teamMembers.size() > 0) {
                for (int i = 0; i < contactContractorList.teamMembers.size(); i++) {
                    View rowView = getLayoutInflater().inflate(R.layout.contractor_team_member_details_row, null);
                    addContractorTeamList(rowView, contactContractorList.teamMembers.get(i));

                }
            }

        } else if (checkType.equalsIgnoreCase("ProjectHead")) {
            title_text.setText("PROJECT HEAD");
            ll_project_head_contact.setVisibility(View.VISIBLE);
            projectHeadReqVo = (ProjectHeadReqVo) getIntent().getSerializableExtra("contactProjectHeadList");
            if (projectHeadReqVo != null) {
                phcategory.setText(projectHeadReqVo.category);
                phCompanyName.setText(projectHeadReqVo.companyOrClientName);
                phName.setText(projectHeadReqVo.projectHeadName);
                phNumber.setText(projectHeadReqVo.contactNumber);
                phMobileNo.setText(projectHeadReqVo.projectHeadMobile);
                phDepartment.setText(projectHeadReqVo.projectHeadDepartment);
                phEmail.setText(projectHeadReqVo.projectHeadEmail);
                phAddress.setText(projectHeadReqVo.projectHeadAddress);
                phState.setText(projectHeadReqVo.projectHeadState);
                phCountry.setText(projectHeadReqVo.projectHeadCountry);
                phPincode.setText(projectHeadReqVo.projectHeadPincode);
                phRemarks.setText(projectHeadReqVo.projectHeadContactRemarks);
                if (projectHeadReqVo.associateContacts != null && projectHeadReqVo.associateContacts.size() > 0) {
                    for (int i = 0; i < projectHeadReqVo.associateContacts.size(); i++) {
                        View rowView = getLayoutInflater().inflate(R.layout.associate_contacts_row, null);
                        addPHAssociateContacts(rowView, projectHeadReqVo.associateContacts.get(i));
                    }
                }
            }
        }


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        edit_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                if (checkType.equalsIgnoreCase("Contractor")) {
                    Intent editintent = new Intent(NewContactViewActivity.this, CreateNewContactActivity.class);
                    editintent.putExtra("form_key", "edit");
                    editintent.putExtra("contactContractorList", contactContractorList);
                    startActivity(editintent);
                }else if (checkType.equalsIgnoreCase("ProjectHead")) {
                    Intent editintent = new Intent(NewContactViewActivity.this, CreateNewContactActivity.class);
                    editintent.putExtra("form_key", "edit");
                    editintent.putExtra("contactProjectHeadList", projectHeadReqVo);
                    startActivity(editintent);
                }

            }
        });

//        delete_linear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deleteCustomer();
//            }
//        });

//        salescall_linear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int id = 0;
//                if (contactList != null)
//                    id = contactList.contactId;
//                Intent intent = new Intent(NewContactViewActivity.this, ViewSalesActivity.class);
//                intent.putExtra("related_to", Constants.RelatedTo.CONTACTS);
//                intent.putExtra("id", id);
//                startActivity(intent);
//            }
//        });
    }
    class RetriveAadharPDFfromUrl extends AsyncTask<String, Void, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }
        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pv_aadhar.fromStream(inputStream).load();
        }
    }
    class RetrivePanPDFfromUrl extends AsyncTask<String, Void, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }
        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pv_pan.fromStream(inputStream).load();
        }
    }
    private void addPHAssociateContacts(View rowView, ProjectHeadReqVo.AssociateContact associateContact) {
        ll_associate_details.addView(rowView);
        CreateNewContactActivity.AssociateContactViewHolder associateContactViewHolder = new CreateNewContactActivity.AssociateContactViewHolder(rowView);
        if (associateContact != null) {
            associateContactViewHolder.etACName.setEnabled(false);
            associateContactViewHolder.etACMobile.setEnabled(false);
            associateContactViewHolder.etACDesignation.setEnabled(false);
            associateContactViewHolder.etACName.setText(associateContact.contactProjectHeadAssociateContactName);
            associateContactViewHolder.etACMobile.setText(associateContact.contactProjectHeadAssociateContactMobile);
            associateContactViewHolder.etACDesignation.setText(associateContact.contactProjectHeadAssociateContactDesignation);
            associateContactViewHolder.addLayout_ac.setVisibility(View.GONE);
            associateContactViewHolder.removeLayout_ac.setVisibility(View.GONE);
        }
    }

    private void addContractorTeamList(View rowView, CustomerContactResponseVo.TeamMemberResVo teamMemberResVo) {
        ll_contractor_team_member_details.addView(rowView);
        ContractorTeamMemViewHolder loopHolder = new ContractorTeamMemViewHolder(rowView);
        if (teamMemberResVo != null) {
            loopHolder.etCoAadharNo.setEnabled(false);
            loopHolder.etTeamMemberMobileNo.setEnabled(false);
            loopHolder.etTeamMemberName.setEnabled(false);
            loopHolder.etTeamMemberName.setText(teamMemberResVo.teamMemberName);
            loopHolder.etTeamMemberMobileNo.setText(teamMemberResVo.teamMemberMobileNo);
            loopHolder.etCoAadharNo.setText(teamMemberResVo.teammemberAadharNumber);
            loopHolder.btn_choose_file.setVisibility(View.GONE);
            loopHolder.tv_file_name.setVisibility(View.GONE);
            loopHolder.addLayout.setVisibility(View.GONE);
            loopHolder.removeLayout.setVisibility(View.GONE);
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
//        try {
//            switch (objectResponse.requestname) {
//                case Constants.RequestNames.CONTACT_DELETE:
//if (contactList.contactId!=0){
//    DatabaseHandler.getDatabase(this).commonDao().deleteContactFromDb(contactList.contactId);
//}
//                    Toast.makeText(this, objectResponse.message, Toast.LENGTH_LONG).show();
//                    alertDialog.dismiss();
//                    break;
//
//                case Constants.RequestNames.LIST_VIEW:
//                    Toast.makeText(this, objectResponse.message, Toast.LENGTH_SHORT).show();
//                    break;
//            }
//            Common.dismissProgressDialog(progressDialog);
//            finish();
//        }catch (Exception e){
//            Common.disPlayExpection(e,progressDialog);
//        }
    }


//    private void deleteCustomer() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View dialogView = inflater.inflate(R.layout.logout_layout, null);
//        dialogView.findViewById(R.id.btn_yes);
//        dialogView.findViewById(R.id.btn_no);
//        builder.setView(dialogView);
//        alertDialog = builder.create();
//        alertDialog.show();
//        TextView logout_info = dialogView.findViewById(R.id.logout_info);
//
//        logout_info.setText("Do you want to delete ?");
//        dialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void onClick(View v) {
//                ContactDeleteReqVo contactDeleteReqVo = new ContactDeleteReqVo();
//                contactDeleteReqVo.contactId = contactList.contactId;
//                new RetrofitRequestController(NewContactViewActivity.this).sendRequest(Constants.RequestNames.CONTACT_DELETE, contactDeleteReqVo, true);
//            }
//        });
//
//        dialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//        dialogView.findViewById(R.id.logout_close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//    }

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
        @BindView(R.id.addlayout)
        LinearLayout addLayout;
        @BindView(R.id.removelayout)
        LinearLayout removeLayout;


        public ContractorTeamMemViewHolder(View rowView) {
            ButterKnife.bind(this, rowView);
        }
    }
}
