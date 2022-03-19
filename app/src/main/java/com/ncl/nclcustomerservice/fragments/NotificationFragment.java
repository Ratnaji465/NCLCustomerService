package com.ncl.nclcustomerservice.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.activity.MainActivity;
import com.ncl.nclcustomerservice.adapter.NotificationAdapter;
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
import com.ncl.nclcustomerservice.object.LeadInsertReqVo;
import com.ncl.nclcustomerservice.object.LeftNav;
import com.ncl.nclcustomerservice.object.NotificationList;
import com.ncl.nclcustomerservice.object.NotificationReqVo;
import com.ncl.nclcustomerservice.object.NotificationResVo;
import com.ncl.nclcustomerservice.object.OpportunitiesList;
import com.ncl.nclcustomerservice.object.QuotationList;
import com.ncl.nclcustomerservice.object.SalesCallList;
import com.ncl.nclcustomerservice.object.SalesOrderList;
import com.ncl.nclcustomerservice.object.Team;
import com.ncl.nclcustomerservice.object.UpdateTableResVo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sowmy on 10/16/2018.
 */

public class NotificationFragment extends Fragment implements RetrofitResponseListener {
    @BindView(R.id.recycler_notification)
    RecyclerView recycler_notification;
    DatabaseHandler db;
    LeftNav tada;
    String notificationId, notificationType,opportunityId;
    private List<CustomerList> customerList;
    private List<LeadInsertReqVo> lead;
    private List<OpportunitiesList> opportunitiesLists;
    private List<QuotationList> quotationLists;
    private List<ContractList> contractLists;
    private List<SalesCallList> salesCallLists;
    private List<SalesOrderList> salesOrderLists;
    private List<ContactList> contactLists;
    private AlertDialog alertDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        db = DatabaseHandler.getDatabase(getActivity());
        ((MainActivity) getActivity()).findViewById(R.id.filter_task).setVisibility(View.GONE);
        ((MainActivity) getActivity()).findViewById(R.id.add_task).setVisibility(View.GONE);
        ((TextView) ((MainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.title_text)).setText("NOTIFICATIONS");
        tada = (LeftNav) getArguments().getSerializable("notification");


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler_notification.setLayoutManager(linearLayoutManager);

        NotificationReqVo notificationReqVo = new NotificationReqVo();
        notificationReqVo.profileId = Common.getProfileId(getActivity());
        new RetrofitRequestController(this).sendRequest(Constants.RequestNames.NOTIFICATION_LIST, notificationReqVo, true);
        return view;
    }

    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {
        try {
            switch (objectResponse.requestname){
                case Constants.RequestNames.NOTIFICATION_LIST:
                NotificationResVo notificationResVo = Common.getSpecificDataObject(objectResponse.result, NotificationResVo.class);
                if (notificationResVo != null) {
                    List<NotificationList> notificationLists = notificationResVo.notificationList;

                    NotificationAdapter notificationAdapter = new NotificationAdapter(getActivity(), notificationLists);
                    recycler_notification.setAdapter(notificationAdapter);
                    notificationAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, View itemView, int position) {
                            notificationId = notificationLists.get(position).notifficationTypeId;
                            notificationType = notificationLists.get(position).notifficationType;
                            opportunityId = String.valueOf(notificationLists.get(position).opportunityId);
                            setData(notificationType,opportunityId);

                        }
                    });

                }
//                    Team team = new Team();
//                    team.teamId = Common.getTeamUserIdFromSP(getActivity());
//                    team.type = "Android";
//                    new RetrofitRequestController(this).sendRequest(Constants.RequestNames.UPDATE_TABLE_LIST, team, true);
                break;

                case Constants.RequestNames.UPDATE_TABLE_LIST:
                    if (objectResponse.result != null) {
                        UpdateTableResVo updateTableResVo = Common.getSpecificDataObject(objectResponse.result, UpdateTableResVo.class);
                        if (updateTableResVo != null) {


                            List<ContactList> contactList = updateTableResVo.contactList;
                            if (contactList!=null)
                            db.commonDao().insertContact(contactList);

                            List<CustomerList> customerList = updateTableResVo.customerList;
                            db.commonDao().insertCustomer(customerList);

                            List<OpportunitiesList> opportunitiesList = updateTableResVo.opportunitiesList;
                            db.commonDao().insertOpportunityList(opportunitiesList);

                            List<ContractList> contractList = updateTableResVo.contractList;
                            db.commonDao().insertContract(contractList);

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
            Common.dismissProgressDialog(progressDialog);
        } catch (Exception e) {
            Common.disPlayExpection(e, progressDialog);
        }
    }

    private void setData(String notificationType, String opportunityId) {
        switch (notificationType) {
            case Constants.Notification.LEAD:

                for (int i = 0; i < lead.size(); i++) {
                    String leads = String.valueOf(lead.get(i).leadsId);
//                    if (leads.equalsIgnoreCase(notificationId)) {
//                        Intent intent = new Intent(getActivity(), InsertLeadViewActivity.class);
//                        intent.putExtra("leadList", (Serializable) lead.get(i));
//                        intent.putExtra("leads", Common.getLeftNav(getActivity(), Constants.MethodNames.LEAD_LIST));
//                        startActivity(intent);
//                    }
                }
                break;

            case Constants.Notification.LEAD_CONVERTED:
                leadconvertPopup();
                break;

            case Constants.Notification.CUSTOMERS:
                customerList = db.commonDao().getCustomerList();

                for (int i = 0; i < customerList.size(); i++) {
                    String customerLists = String.valueOf(customerList.get(i).customerId);
//                    if (customerLists.equalsIgnoreCase(notificationId)) {
//                        Intent intent = new Intent(getActivity(), CustomerViewActivity.class);
//                        intent.putExtra("customer", (Serializable) customerList.get(i));
//                        intent.putExtra("customers", Common.getLeftNav(getActivity(), Constants.MethodNames.CUSTOMER_LIST));
//                        startActivity(intent);
//                    }
                }
                break;



            case Constants.Notification.OPPORTUNITIES:
                opportunitiesLists = db.commonDao().getOpportunitiesList(20,0);

                for (int i = 0; i < opportunitiesLists.size(); i++) {
                    String oppo = String.valueOf(opportunitiesLists.get(i).opportunityId);
//                    if (oppo.equalsIgnoreCase(notificationId)) {
//                        Intent intent = new Intent(getActivity(), OpportunityViewActivity.class);
//                        intent.putExtra("opportunity", (Serializable) opportunitiesLists.get(i));
//                        intent.putExtra("view","notification");
//                        intent.putExtra("leftnav", Common.getLeftNav(getActivity(), Constants.MethodNames.OPPORTUNITY_LIST));
//                        startActivity(intent);
//                    }
                }
                break;

            case Constants.Notification.CONTRACT:
                contractLists = db.commonDao().getContractList(25,0);

                for (int i = 0; i < contractLists.size(); i++) {
                    String contract = String.valueOf(contractLists.get(i).contractId);

//                    if (contract.equalsIgnoreCase(notificationId)) {
////                        List<ContractLineItem> contractLineItems = db.commonDao().getContractLineItems(contract);
//                        Intent intent = new Intent(getActivity(), ContractViewActivity.class);
//                        intent.putExtra("contractList", (Serializable) contractLists.get(i));
////                        intent.putExtra("contractLineItem", (Serializable) contractLineItems);
//                        intent.putExtra("view","contract");
//                        intent.putExtra("leftnav", Common.getLeftNav(getActivity(), Constants.MethodNames.CONTRACT_LIST));
//                        startActivity(intent);
//                    }
                }
                break;


            case Constants.Notification.SALES_ORDER:
                salesOrderLists = db.commonDao().getSalesOrderList(25,0);

                for (int i = 0; i < salesOrderLists.size(); i++) {
                    String salesorder = String.valueOf(salesOrderLists.get(i).salesOrderId);

//                    if (salesorder.equalsIgnoreCase(notificationId)) {
////                        List<SalesOrderLineItem> productOpportunitieLists = db.commonDao().getSalesOrderLineItems(salesorder);
//                        Intent intent = new Intent(getActivity(), SalesOrderViewActivity.class);
//                        intent.putExtra("salesorderlist", (Serializable) salesOrderLists.get(i));
////                        intent.putExtra("salesorderLineItem", (Serializable) productOpportunitieLists);
//                        intent.putExtra("orderview","orderNotify");
//                        intent.putExtra("leftnav", Common.getLeftNav(getActivity(), Constants.MethodNames.SALES_ORDER_LIST));
//                        startActivity(intent);
//                    }
                }
                break;

            case Constants.Notification.QUOTATION:

                opportunitiesLists = db.commonDao().getOpportunitiesList(20,0);

                for (int i =0 ; i<opportunitiesLists.size();i++){
                    String opportunity = String.valueOf(opportunitiesLists.get(i).opportunityId);

                    if (opportunity.equalsIgnoreCase(opportunityId)){
                        quotationLists = db.commonDao().getQutation(25,0);

                        for (int k = 0; k<quotationLists.size();k++){
                            String quotation = String.valueOf(quotationLists.get(k).quotationId);

//                            if (quotation.equalsIgnoreCase(notificationId)){
////                                List<QuotationProductList> quotationProductLists = db.commonDao().getQuotationProductLineItem(quotation);
//                                Intent intent = new Intent(getActivity(), QuotationViewActivity.class);
//                                intent.putExtra("quotationList",quotationLists.get(k));
////                                intent.putExtra("quotationLineItem", (Serializable) quotationProductLists.get(0));
//                                intent.putExtra("view","notifyview");
////                                intent.putExtra("","");
//                                startActivity(intent);
//                            }
                        }
                    }
                }

                break;
        }

    }

    private void leadconvertPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.lead_converted_popup, null);
        dialogView.findViewById(R.id.btn_ok);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
        dialogView.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
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
