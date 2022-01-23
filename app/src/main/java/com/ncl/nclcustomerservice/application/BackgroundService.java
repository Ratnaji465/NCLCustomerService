package com.ncl.nclcustomerservice.application;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

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
import com.ncl.nclcustomerservice.object.OpportunitiesList;
import com.ncl.nclcustomerservice.object.QuotationList;
import com.ncl.nclcustomerservice.object.SalesCallList;
import com.ncl.nclcustomerservice.object.SalesOrderList;
import com.ncl.nclcustomerservice.object.Team;
import com.ncl.nclcustomerservice.object.UpdateTableResVo;

import java.util.List;

/**
 * Created by SupraSoft on 9/11/2018.
 */

public class BackgroundService extends IntentService implements RetrofitResponseListener {
    public static final String NETWORK_CHANGE_RECEIVED = MyApplication.getInstance().getPackageName() + ".NETWORK_CHANGED";
    final int GPS_REQUEST_CODE = 1;
    final int WRITE_REQUEST_CODE = 2;
    DatabaseHandler db;

    public BackgroundService() {
        super("BackgroundService");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        updatedRecords();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    private void updatedRecords() {
//        Team team = new Team();
//        team.type = "Android";
//        team.teamId = Common.getTeamUserIdFromSP(this);
//        new RetrofitRequestController(this).sendRequest(Constants.RequestNames.UPDATE_TABLE_LIST, team,false);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = DatabaseHandler.getDatabase(this);
        //  ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        //   registerReceiver(connectivityReceiver, new IntentFilter(NETWORK_CHANGE_RECEIVED));
    }

    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {
        switch (objectRequest.requestname) {
            case Constants.RequestNames.UPDATE_TABLE_LIST:
                UpdateTableResVo updateTableResVo = Common.getSpecificDataObject(objectResponse.result, UpdateTableResVo.class);
                if (updateTableResVo != null) {
                    List<LeadInsertReqVo> lead = updateTableResVo.leadList;
                    if (!db.isOpen()){
                        db=DatabaseHandler.getDatabase(this);
                    }
                    /*List<Lead> upLead = new ArrayList<>();
                    upLead = db.commonDao().getLead();
                    for (int i = 0; i < upLead.size(); i++) {
                        if (lead.get(i).leadsId == upLead.get(i).leadsId) {
//                                db.commonDao().updateLeadList(lead.get(i).firstName, lead.get(i).leadsId,lead.get(i).lastName,lead.get(i).company,lead.get(i).associateContactId,lead.get(i).annualRevenue, lead.get(i).description,lead.get(i).doNotCall,);
                            db.commonDao().updateLead(lead.get(i));
                        } else {*/
                    if (lead!=null && lead.size()>0)
                    db.commonDao().insertLeadList(lead);
                     /*   }
                    }*/

                    List<ContactList> contactList = updateTableResVo.contactList;
//                    List<ContactList> upcontact = db.commonDao().getContactList();
//                    for (int i = 0; i < upcontact.size(); i++) {
//                        if (contactList.get(i).contactId == upcontact.get(i).contactId) {
//                            db.commonDao().updateContact(contactList.get(i));
//                        } else {
                    if (contactList!=null && contactList.size()>0)
                    db.commonDao().insertContact(contactList);
//                        }
//                    }

                    List<CustomerList> customerLists = updateTableResVo.customerList;
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
//                    List<OpportunitiesList> upopportunity = db.commonDao().getOpportunitiesList();
//                    for (int i=0;i<upopportunity.size();i++){
//                        if (opportunitiesList.get(i).opportunityId == upopportunity.get(i).opportunityId){
//                            db.commonDao().updateOpportunity(opportunitiesList.get(i));
//                        }else {
                    if (opportunitiesList!=null && opportunitiesList.size()>0)
                    db.commonDao().insertOpportunityList(opportunitiesList);
//                        }
//                    }

                    List<ContractList> contractList = updateTableResVo.contractList;
//                    List<ContractList> upcontract = db.commonDao().getContractList();
//                    for (int i=0;i<contractList.size();i++){
//                        if (contractList.get(i).contractId == upcontract.get(i).contractId){
//                            db.commonDao().updateContract(contractList.get(i));
//                        }else {
                    if (contractList!=null && contractList.size()>0)
                    db.commonDao().insertContract(contractList);
//                        }
//                    }

                    List<SalesCallList> salesCallList = updateTableResVo.salesCallList;
//                    List<SalesCallList> upsalesaCall = db.commonDao().getSalesCallList();
//                    for (int i=0;i<salesCallList.size();i++){
//                        if (salesCallList.get(i).salesCallId == upsalesaCall.get(i).salesCallId){
//                            db.commonDao().updateSallesCall(salesCallList.get(i));
//                        }else {
                    if (salesCallList!=null && salesCallList.size()>0)
                    db.commonDao().insertSalesCallList(salesCallList);
//                        }
//                    }

                    List<SalesOrderList> salesOrderList = updateTableResVo.salesOrderList;
//                    List<SalesOrderList> upsalesOrder = db.commonDao().getSalesOrderList();
//                    for (int i =0 ;i<salesOrderList.size();i++){
//                        if (salesOrderList.get(i).salesOrderId == upsalesOrder.get(i).salesOrderId){
//                            db.commonDao().updateSalesOrder(salesOrderList.get(i));
//                        }else {
                    if (salesOrderList!=null && salesOrderList.size()>0)
                    db.commonDao().insertSalesOrderList(salesOrderList);
//                        }
//                    }

                    List<QuotationList> quotationList = updateTableResVo.qutationList;
//                    List<QuotationList> upquotation = db.commonDao().getQutation();
//                    for (int i =0 ;i<quotationList.size();i++){
//                        if (quotationList.get(i).quotationId == upquotation.get(i).quotationId){
//                            db.commonDao().updateQuotation(quotationList.get(i));
//                        }else {
                    if (quotationList!=null && quotationList.size()>0)
                    db.commonDao().insertQuotationList(quotationList);
//                        }
//                    }

                }

                Team team = new Team();
                team.type = "Android";
                new RetrofitRequestController(this).sendRequest(Constants.RequestNames.USERS_UPDATE_DATE, team, false);
                break;

        }


    }
}


