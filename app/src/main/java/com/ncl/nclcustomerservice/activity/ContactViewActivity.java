package com.ncl.nclcustomerservice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.ncl.nclcustomerservice.object.LeftNav;
import com.ncl.nclcustomerservice.object.ListReqVo;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ContactViewActivity extends NetworkChangeListenerActivity implements RetrofitResponseListener {
    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.back_button)
    ImageView back_button;
    @BindView(R.id.cdetail_saluvation)
    TextView cdetail_saluvation;
    @BindView(R.id.cdetail_name)
    TextView cdetail_name;
    @BindView(R.id.cdetatail_lastname)
    TextView cdetail_lastname;
    @BindView(R.id.cdetail_email)
    TextView cdetail_email;
    @BindView(R.id.cdetatail_mobile)
    TextView cdetatail_mobile;
    @BindView(R.id.cdetail_fax)
    TextView cdetail_fax;
    @BindView(R.id.cdetail_phone)
    TextView cdetail_phone;
    @BindView(R.id.cdetail_category)
    TextView cdetail_category;
    @BindView(R.id.cdetail_company)
    TextView cdetail_company;
    @BindView(R.id.cdetail_title)
    TextView cdetail_title;
    @BindView(R.id.cdetail_mailingaddress)
    TextView cdetail_mailingaddress;
    @BindView(R.id.cdetail_otheraddress)
    TextView cdetail_otheraddress;
    @BindView(R.id.cdetail_otherphone)
    TextView cdetail_otherphone;
    @BindView(R.id.cdetail_homephone)
    TextView cdetail_homephone;
    @BindView(R.id.cdetail_dob)
    TextView cdetail_dob;
    @BindView(R.id.cdetail_decription)
    TextView cdetail_decription;
    //    @BindView(R.id.cdetail_leadsource)
//    TextView cdetail_leadsource;
    @BindView(R.id.cdetail_owner)
    TextView cdetail_owner;
    @BindView(R.id.cdetail_reportsto)
    TextView cdetail_reportsto;
    @BindView(R.id.edit_linear)
    LinearLayout edit_linear;
    @BindView(R.id.delete_linear)
    LinearLayout delete_linear;
    @BindView(R.id.salescall_linear)
    LinearLayout salescall_linear;
    @BindView(R.id.ll_other_category)
    LinearLayout ll_other_category;
    @BindView(R.id.cdetail_other_category)
    TextView cdetail_other_category;
    ContactList contactList;
    private String contactId;
    private AlertDialog alertDialog;
    private Bundle bundle;
    Bundle databundle = new Bundle();
    LeftNav leftNav;
    ListReqVo listReqVo;
//    ListContactListCustomer listContactListCustomers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        ButterKnife.bind(this);
        leftNav = (LeftNav) getIntent().getSerializableExtra("leftnav");

        title_text.setText("CONTACTS");
        if (leftNav != null) {
            if (leftNav.update.equals("1"))
                edit_linear.setVisibility(View.VISIBLE);
            else
                edit_linear.setVisibility(View.GONE);
            if (leftNav.delete.equals("1"))
                delete_linear.setVisibility(View.VISIBLE);
            else
                delete_linear.setVisibility(View.GONE);
        }
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listReqVo = (ListReqVo) getIntent().getSerializableExtra("listreqvo");
        contactList = (ContactList) getIntent().getSerializableExtra("contactlist");
        cdetail_saluvation.setText(Common.nullChecker(contactList.salutation));
        cdetail_name.setText(Common.nullChecker(contactList.firstName));
        cdetail_lastname.setText(Common.nullChecker(contactList.lastName));
        cdetail_email.setText(Common.nullChecker(contactList.email));
        cdetatail_mobile.setText(Common.nullChecker(contactList.mobile));
        cdetail_fax.setText(Common.nullChecker(contactList.fax));
        cdetail_phone.setText(Common.nullChecker(contactList.phone));
        cdetail_category.setText(Common.nullChecker(contactList.category));
        if (contactList.category.equalsIgnoreCase("Other")) {
            ll_other_category.setVisibility(View.VISIBLE);
            cdetail_other_category.setText(Common.nullChecker(contactList.otherCategory));
        }
        cdetail_company.setText(Common.nullChecker(contactList.companyText));
        cdetail_title.setText(Common.nullChecker(contactList.titleDesignation));
        cdetail_mailingaddress.setText(Common.nullChecker(contactList.mallingStreet1 + "," + contactList.mallingstreet2 + "," + contactList.mallingCountry + "," + contactList.mallingStateProvince + "," + contactList.mallingCity + "," + contactList.mallingZipPostal));
        cdetail_otheraddress.setText(Common.nullChecker(contactList.otherStreet1 + "," + contactList.otherstreet2 + "," + contactList.mallingCountry + "," + contactList.mallingStateProvince + "," + contactList.otherCity + "," + contactList.otherZipPostal));
        cdetail_otherphone.setText(Common.nullChecker(contactList.otherPhone));
        cdetail_homephone.setText(Common.nullChecker(contactList.homePhone));
        cdetail_dob.setText(Common.nullChecker(contactList.birthdate));
        cdetail_decription.setText(Common.nullChecker(contactList.description));
        cdetail_owner.setText(Common.nullChecker(contactList.contactOwnerName));
        cdetail_reportsto.setText(Common.nullChecker(contactList.reportsToName));


        edit_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Intent editintent = new Intent(ContactViewActivity.this, CreateContactActivity.class);
//                editintent.putExtra("form_key", "edit");
//                editintent.putExtra("contactlist", contactList);
//                editintent.putExtra("id", getIntent().getExtras().getInt("id", 0));
//                startActivity(editintent);
            }
        });

        delete_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCustomer();
            }
        });

        salescall_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int id = 0;
//                if (contactList != null)
//                    id = contactList.contactId;
//                Intent intent = new Intent(ContactViewActivity.this, ViewSalesActivity.class);
//                intent.putExtra("related_to", Constants.RelatedTo.CONTACTS);
//                intent.putExtra("id", id);
//                startActivity(intent);
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
        try {
            switch (objectResponse.requestname) {
                case Constants.RequestNames.CONTACT_DELETE:
                    if (contactList.contactId != 0) {
                        DatabaseHandler.getDatabase(this).commonDao().deleteContactFromDb(contactList.contactId);
                    }
                    Toast.makeText(this, objectResponse.message, Toast.LENGTH_LONG).show();
                    alertDialog.dismiss();
                    break;

                case Constants.RequestNames.LIST_VIEW:
                    Toast.makeText(this, objectResponse.message, Toast.LENGTH_SHORT).show();
                    break;
            }
            Common.dismissProgressDialog(progressDialog);
            finish();
        } catch (Exception e) {
            Common.disPlayExpection(e, progressDialog);
        }
    }


    private void deleteCustomer() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.logout_layout, null);
        dialogView.findViewById(R.id.btn_yes);
        dialogView.findViewById(R.id.btn_no);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
        TextView logout_info = dialogView.findViewById(R.id.logout_info);

        logout_info.setText("Do you want to delete ?");
        dialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                ContactDeleteReqVo contactDeleteReqVo = new ContactDeleteReqVo();
                contactDeleteReqVo.contactId = contactList.contactId;
                new RetrofitRequestController(ContactViewActivity.this).sendRequest(Constants.RequestNames.CONTACT_DELETE, contactDeleteReqVo, true);
            }
        });

        dialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.logout_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}
