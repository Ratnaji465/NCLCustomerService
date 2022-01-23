package com.ncl.nclcustomerservice.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.network.RetrofitRequestController;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.ContactList;
import com.ncl.nclcustomerservice.object.ContractList;
import com.ncl.nclcustomerservice.object.CustomerList;
import com.ncl.nclcustomerservice.object.ForgotPassowrd;
import com.ncl.nclcustomerservice.object.GeoTrackingListResVo;
import com.ncl.nclcustomerservice.object.Geo_Tracking_POJO;
import com.ncl.nclcustomerservice.object.LeadInsertReqVo;
import com.ncl.nclcustomerservice.object.LeftNav;
import com.ncl.nclcustomerservice.object.LoginReqVo;
import com.ncl.nclcustomerservice.object.LoginResVo;
import com.ncl.nclcustomerservice.object.MapReqVo;
import com.ncl.nclcustomerservice.object.MastersResVo;
import com.ncl.nclcustomerservice.object.OpportunitiesList;
import com.ncl.nclcustomerservice.object.QuotationList;
import com.ncl.nclcustomerservice.object.SalesCallList;
import com.ncl.nclcustomerservice.object.SalesOrderList;
import com.ncl.nclcustomerservice.object.Team;
import com.ncl.nclcustomerservice.object.UpdateTableResVo;
import com.ncl.nclcustomerservice.object.UsersTeam;
import com.ncl.nclcustomerservice.uploadfiles.OffLineDataUploadService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends NetworkChangeListenerActivity implements RetrofitResponseListener {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.forgot_password)
    TextView forgot_password;
    @BindView(R.id.user_img)
    ImageView user_img;
    @BindView(R.id.password_img)
    ImageView password_img;
    //    @BindView(R.id.register_new)
//    TextView Register_new;
    @BindView(R.id.btn_login)
    ImageView btn_login;
    EditText email;
    private Button getPassword;
    private AlertDialog dialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    List<LeftNav> leftNavs = new ArrayList<>();
    private int i = 0;
    private LoginResVo loginResVo;
    List<UsersTeam> usersTeam = new ArrayList<>();
    List<Integer> teamId = new ArrayList<>();
    String teamuserId;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        db = DatabaseHandler.getDatabase(this);

//        loginService = new LoginServiceImpl(LoginActivity.this);
       /* username.setText("a0361");
        password.setText("1234");*/
        username.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (username.getText().toString().matches(emailPattern) && s.length() > 0) {
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

//        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
//        Register_new.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, NewSignUpActivity.class);
//                startActivity(intent);
//            }
//        });


        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                dialog = dialogBuilder.create();
                View layoutView = getLayoutInflater().inflate(R.layout.password_layout, null);
                dialog.setView(layoutView);
                dialog.setCancelable(true);
                email = layoutView.findViewById(R.id.email_forgot);
                email.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable s) {
                        if (email.getText().toString().matches(emailPattern) && s.length() > 0) {
                        }
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }
                });


                getPassword = layoutView.findViewById(R.id.get_password);
                getPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!email.getText().toString().matches(emailPattern)) {
                            email.setError("Please Enter vaild Email address");
                            email.requestFocus();
                        } else {
                            ForgotPassowrd forgotPassowrd = new ForgotPassowrd();
                            forgotPassowrd.emailId = email.getText().toString();
                            new RetrofitRequestController(LoginActivity.this).sendRequest(Constants.RequestNames.FORGOT_PASSWORD, forgotPassowrd, true);
                        }
                    }
                });
                dialog.show();
            }
        });
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint({"Range", "ResourceAsColor"})
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    user_img.setAlpha(255);
                    password_img.setAlpha(100);
                } else {
                    password_img.setAlpha(255);
                    user_img.setAlpha(100);
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint({"Range", "ResourceAsColor"})
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    user_img.setAlpha(100);
                    password_img.setAlpha(255);
                } else {
                    user_img.setAlpha(255);
                    password_img.setAlpha(100);
                }
            }
        });
    }

    @Override
    protected void onInternetConnected() {

    }

    @Override
    protected void onInternetDisconnected() {

    }

    @OnClick(R.id.btn_login)
    void setBtn_login() {
        if (username.getText().toString().trim().length() == 0) {
            username.setError("Please Enter Email/User Name");
            username.requestFocus();
        } else if (password.getText().toString().trim().length() == 0) {
            password.setError("Please Enter Password");
            password.requestFocus();
        } else {
            LoginReqVo loginReqVo = new LoginReqVo();
            loginReqVo.users = username.getText().toString().trim();
            loginReqVo.password = password.getText().toString().trim();
            loginReqVo.fcmId = FirebaseInstanceId.getInstance().getToken();
            Common.Log.i("fcm id " + loginReqVo.fcmId);
            loginReqVo.deviceid = Common.getDeviceId(this);
            loginReqVo.deviceType = "ANDROID";
            new RetrofitRequestController(this).sendRequest(Constants.RequestNames.LOGIN_DETAILS, loginReqVo, true);
        }
    }

    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {
        try {
            switch (objectRequest.requestname) {
                case Constants.RequestNames.LOGIN_DETAILS:
                    if (objectResponse.result != null) {
                        loginResVo = Common.getSpecificDataObject(objectResponse.result, LoginResVo.class);
                        if (loginResVo != null) {
                            leftNavs = loginResVo.leftNav;
                            usersTeam = loginResVo.usersTeam;


                            if (loginResVo.profileId.equalsIgnoreCase("1")|| loginResVo.profileName==null || loginResVo.profileName.contains("Comercial")|| loginResVo.profileName.contains("Commercial")) {
                                Toast.makeText(this, "Your not Autherized for login", Toast.LENGTH_SHORT).show();
                                Common.dismissProgressDialog(progressDialog);
                                return;
                            }

                            Common.saveLeftNav(leftNavs);
                            Common.saveRoleIdIntoSP(loginResVo.roleId);
                            Common.saveUserRoleIntoSP(loginResVo.roleName);
                            Common.saveUserNameIntoSP(loginResVo.name);
                            Common.saveEmailIntoSP(loginResVo.email);
                            Common.saveUserIdIntoSP(loginResVo.userId);
                            Common.saveProfileNameIntoSP(loginResVo.profileName);
                            Common.saveProfileIdIntoSP(loginResVo.profileId);
                            Common.saveDepartmentIdIntoSP(loginResVo.departmentId);
                            Common.saveDepartmentIdIntoSP(loginResVo.departmentName);
                            Common.saveMobileIntoSP(loginResVo.phone);
                           // Common.saveUserPassword(password.getText().toString().trim());
                            Common.saveObject(this, loginResVo);
                            Common.saveUserTeam(loginResVo.usersTeam);
                            Common.saveDivisions(loginResVo.divisions);
                            Common.saveImageIntoSP(loginResVo.profileImg);
                            Common.savePriceListIdIntoSP(loginResVo.price_list_id);
                            Common.savePriceAreaNameIntoSP(loginResVo.price_list_area);

                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < usersTeam.size(); i++) {
                                if (i > 0)
                                    sb.append("," /*+ usersTeam.get(i).userId*/);

                                sb.append(usersTeam.get(i).userId);
                                teamuserId = sb.toString();
                                // teamId.add(usersTeam.get(i).userId);
                                Common.Log.i("size :" + teamuserId.toString());
                            }
                            Common.saveArrayList(teamuserId);


//                                Team team = new Team();
//                                team.teamId = Common.getTeamUserIdFromSP(this);
//                                team.roleId = String.valueOf(Common.getRoleIdFromSP(this));
                              //  new RetrofitRequestController(this).sendRequest(Constants.RequestNames.MASTERS_LIST, team, true);

                                if (Common.haveInternet(this)) {
                                    Intent mIntent = new Intent(this, OffLineDataUploadService.class);
                                    OffLineDataUploadService.enqueueWork(this, mIntent);
                                }

                                Common.dismissProgressDialog(progressDialog);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("profileid", loginResVo.profileId);
                                intent.putExtra("leftnav", (Serializable) leftNavs);
                                startActivity(intent);
                                finish();


                        }
                    }
                    break;
                case Constants.RequestNames.MASTERS_LIST:
                    if (objectResponse.result != null) {
                        MastersResVo mastersResVo = Common.getSpecificDataObject(objectResponse.result, MastersResVo.class);
                        if (mastersResVo != null) {
                            if (mastersResVo.leadList != null)
                                db.commonDao().insertLeadList(mastersResVo.leadList);
//                            if (mastersResVo.complaintList != null)
//                                db.commonDao().insertComplaints(convertComplaintInsertToTable(mastersResVo.complaintList));
                            if (mastersResVo.contactList != null)
                                db.commonDao().insertContact(mastersResVo.contactList);

                            if (mastersResVo.qutationLists != null) {
                                List<QuotationList> qutationList = mastersResVo.qutationLists;
                                for (int i = 0; i < qutationList.size(); i++) {
                                    db.commonDao().insertQuotation(qutationList.get(i));
                                    if (qutationList.get(i).qutationProductList != null) {
                                        for (int j = 0; j < qutationList.get(i).qutationProductList.size(); j++) {
                                            qutationList.get(i).qutationProductList.get(j).quotationLineId = qutationList.get(i).quotationId;
                                        }
                                        db.commonDao().insertQuotationLineItem(qutationList.get(i).qutationProductList);
                                    }
                                }
                            }

                            if (mastersResVo.contractList != null) {
                                List<ContractList> contractLists = mastersResVo.contractList;
                                for (int i = 0; i < contractLists.size(); i++) {
                                    db.commonDao().insertContractList(contractLists.get(i));
                                    if (contractLists.get(i).contractProduct != null) {
                                        for (int j = 0; j < contractLists.get(i).contractProduct.size(); j++) {
                                            contractLists.get(i).contractProduct.get(j).lineItemId = contractLists.get(i).contractId;
                                        }
                                        db.commonDao().insertContractLineItems(contractLists.get(i).contractProduct);
                                    }
                                }
                            }

                            if (mastersResVo.salesOrderList != null) {
                                List<SalesOrderList> salesOrderLists = mastersResVo.salesOrderList;
                                for (int i = 0; i < salesOrderLists.size(); i++) {
                                    db.commonDao().insertSalesOrder(salesOrderLists.get(i));
                                    if (salesOrderLists.get(i).salesOrderProductList != null) {
                                        for (int j = 0; j < salesOrderLists.get(i).salesOrderProductList.size(); j++) {
                                            salesOrderLists.get(i).salesOrderProductList.get(j).saleslineItemId = salesOrderLists.get(i).salesOrderId;
                                        }
                                        db.commonDao().insertSalesOrderLineItem(salesOrderLists.get(i).salesOrderProductList);
                                    }
                                }
                            }
                            if (mastersResVo.customerList != null) {
                                List<CustomerList> customerLists = mastersResVo.customerList;
                               /* for (int i = 0; i < customerLists.size(); i++) {
                                    db.commonDao().insertCustomerList(customerLists.get(i));
                                    if (customerLists.get(i).customerUserList != null) {
                                        for (int j = 0; j < customerLists.get(i).customerUserList.size(); j++) {
                                            customerLists.get(i).customerUserList.get(j).lineitemid = customerLists.get(i).customerId;
                                        }
                                        db.commonDao().insertCustomerLineItems(customerLists.get(i).customerUserList);
                                    }
                                }*/
                                for (int i = 0; i < customerLists.size(); i++) {
                                    long key = db.commonDao().insertCustomerList(customerLists.get(i));
                                    if (customerLists.get(i).customerUserList!=null) {
                                        for (int j = 0; j < customerLists.get(i).customerUserList.size(); j++) {
                                            customerLists.get(i).customerUserList.get(j).lineitemid = (int) key;
                                        }
                                        db.commonDao().insertCustomerLineItems(customerLists.get(i).customerUserList);
                                    }
                                }

                            }
                            if (mastersResVo.opportunitiesList != null) {
                                List<OpportunitiesList> opportunitiesLists = mastersResVo.opportunitiesList;
                                for (int i = 0; i < opportunitiesLists.size(); i++) {
                                    db.commonDao().insertOpportunities(opportunitiesLists.get(i));
                                    if (opportunitiesLists.get(i).finalProduct != null) {
                                        for (int k = 0; k < opportunitiesLists.get(i).finalProduct.size(); k++) {
//                                            opportunitiesLists.get(i).productOpportunitieList.get(k).oppProduct = (int) primaryKey;
                                            opportunitiesLists.get(i).finalProduct.get(k).oppProduct = opportunitiesLists.get(i).opportunityId;
                                        }
                                        db.commonDao().insertOpportunitiesProducts(opportunitiesLists.get(i).finalProduct);
                                    }
                                    if (opportunitiesLists.get(i).competitionProduct != null) {
                                        for (int k = 0; k < opportunitiesLists.get(i).competitionProduct.size(); k++) {
                                            opportunitiesLists.get(i).competitionProduct.get(k).opportunityCompetion = opportunitiesLists.get(i).opportunityId;
                                        }
                                        db.commonDao().insertOpportunityCompetition(opportunitiesLists.get(i).competitionProduct);
                                    }

                                    if (opportunitiesLists.get(i).brandsProduct != null) {
                                        for (int k = 0; k < opportunitiesLists.get(i).brandsProduct.size(); k++) {
                                            opportunitiesLists.get(i).brandsProduct.get(k).oppoBrand = opportunitiesLists.get(i).opportunityId;
                                        }
                                        db.commonDao().insertOpportunityBrandLineItem(opportunitiesLists.get(i).brandsProduct);
                                    }

                                    if (opportunitiesLists.get(i).associateContact != null){
                                        for (int k = 0;k<opportunitiesLists.get(i).associateContact.size();k++){
                                            opportunitiesLists.get(i).associateContact.get(k).contactId = String.valueOf(opportunitiesLists.get(i).opportunityId);
                                        }
                                       // db.commonDao().insertAssociateContacts(opportunitiesLists.get(i).associateContact);
                                    }
                                }
                            }
                            if (mastersResVo.salesCallList != null)
                                db.commonDao().insertSalesCallList(mastersResVo.salesCallList);


                            if (mastersResVo.tadaList != null)
                                db.commonDao().insertTadaList(mastersResVo.tadaList);

                            if (mastersResVo.paymentCollectionList != null)
                                db.commonDao().insertPaymentCollection(mastersResVo.paymentCollectionList);

                        }
                    }
                    MapReqVo mapReqVo = new MapReqVo();
                    mapReqVo.teamId = Common.getTeamUserIdFromSP(this);
                    mapReqVo.days = "7";
                    new RetrofitRequestController(this).sendRequest(Constants.RequestNames.GEO_TRACKING_LIST, mapReqVo, true);

                    break;
                case Constants.RequestNames.GEO_TRACKING_LIST:
                    if (objectResponse.result != null) {
                        GeoTrackingListResVo geoTrackingListResVo = Common.getSpecificDataObject(objectResponse.result, GeoTrackingListResVo.class);
                        List<Geo_Tracking_POJO> geoTrackingList = geoTrackingListResVo.geoTrackingList;
                        if (geoTrackingList != null && geoTrackingList.size() > 0) {
                            for (int i = 0; i < geoTrackingList.size(); i++) {
                                geoTrackingList.get(i).visitDate = geoTrackingList.get(i).visitDate.split(" ")[0];
                                db.commonDao().insertGeoTrackingPojo(geoTrackingList.get(i));
                            }
                        }
                    }

                    if (loginResVo.profileId.equalsIgnoreCase("1")) {

                        Toast.makeText(this,"Your Not Autherized for Admin",Toast.LENGTH_SHORT).show();
                        Common.dismissProgressDialog(progressDialog);
                    }else {
                        Common.dismissProgressDialog(progressDialog);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("profileid", loginResVo.profileId);
                        intent.putExtra("leftnav", (Serializable) leftNavs);
                        startActivity(intent);
                        finish();

//                        Team team = new Team();
//                        team.teamId = Common.getTeamUserIdFromSP(this);
//                        team.type = "Android";
//                        new RetrofitRequestController(this).sendRequest(Constants.RequestNames.UPDATE_TABLE_LIST, team, true);
                    }


                    break;

                case Constants.RequestNames.UPDATE_TABLE_LIST:
                    if (objectResponse.result != null) {
                        UpdateTableResVo updateTableResVo = Common.getSpecificDataObject(objectResponse.result, UpdateTableResVo.class);
                        if (updateTableResVo != null) {

                            List<LeadInsertReqVo> lead = updateTableResVo.leadList;
                            db.commonDao().insertLeadList(lead);

                            List<ContactList> contactList = updateTableResVo.contactList;
                            db.commonDao().insertContact(contactList);

                            List<CustomerList> customerLists = updateTableResVo.customerList;
                           // db.commonDao().insertCustomer(customerList);

                            if (customerLists!=null) {
                                for (int i = 0; i < customerLists.size(); i++) {
                                    long key = db.commonDao().insertCustomerList(customerLists.get(i));
                                    if (customerLists.get(i).customerUserList != null) {
                                        for (int j = 0; j < customerLists.get(i).customerUserList.size(); j++) {
                                            customerLists.get(i).customerUserList.get(j).lineitemid = (int) key;
                                        }
                                        db.commonDao().insertCustomerLineItems(customerLists.get(i).customerUserList);
                                    }
                                }
                            }

                            List<OpportunitiesList> opportunitiesList = updateTableResVo.opportunitiesList;
                            db.commonDao().insertOpportunityList(opportunitiesList);

                            List<ContractList> contractList = updateTableResVo.contractList;
                            db.commonDao().insertContract(contractList);

                            List<SalesCallList> salesCallList = updateTableResVo.salesCallList;
                            db.commonDao().insertSalesCallList(salesCallList);

                            List<SalesOrderList> salesOrderList = updateTableResVo.salesOrderList;
                            db.commonDao().insertSalesOrderList(salesOrderList);

                            List<QuotationList> quotationList = updateTableResVo.qutationList;
                            db.commonDao().insertQuotationList(quotationList);
                        }
                    }
                    Team teamtype = new Team();
                    teamtype.type = "Android";
                    new RetrofitRequestController(this).sendRequest(Constants.RequestNames.USERS_UPDATE_DATE, teamtype, true);
                    break;
            }


        } catch (Exception e) {
            Common.disPlayExpection(e, progressDialog);
        }

    }

    private void setUpdate() {


    }
}
