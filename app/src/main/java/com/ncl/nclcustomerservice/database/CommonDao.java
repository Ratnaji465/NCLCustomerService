package com.ncl.nclcustomerservice.database;

/**
 * Created by User on 8/23/2018.
 */

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.ncl.nclcustomerservice.checkinout.EmpActivityLogsPojo;
import com.ncl.nclcustomerservice.checkinout.EmpActivityPojo;
import com.ncl.nclcustomerservice.object.AssociateContact;
import com.ncl.nclcustomerservice.object.AssociateContactLead;
import com.ncl.nclcustomerservice.object.ComplaintsTable;
import com.ncl.nclcustomerservice.object.ContactList;
import com.ncl.nclcustomerservice.object.ContractLineItem;
import com.ncl.nclcustomerservice.object.ContractList;
import com.ncl.nclcustomerservice.object.Customer;
import com.ncl.nclcustomerservice.object.CustomerContactResponseVo;
import com.ncl.nclcustomerservice.object.CustomerList;
import com.ncl.nclcustomerservice.object.CustomerUserList;
import com.ncl.nclcustomerservice.object.Geo_Tracking_POJO;
import com.ncl.nclcustomerservice.object.LeadInsertReqVo;
import com.ncl.nclcustomerservice.object.OpportunitiesList;
import com.ncl.nclcustomerservice.object.OpportunityBrandsLineItem;
import com.ncl.nclcustomerservice.object.OpportunityCompetitionLineItem;
import com.ncl.nclcustomerservice.object.OpportunityProductLineItem;
import com.ncl.nclcustomerservice.object.PaymentCollectionList;
import com.ncl.nclcustomerservice.object.ProjectHeadReqVo;
import com.ncl.nclcustomerservice.object.QuotationList;
import com.ncl.nclcustomerservice.object.QuotationProductList;
import com.ncl.nclcustomerservice.object.SalesOrderLineItem;
import com.ncl.nclcustomerservice.object.SalesCallList;
import com.ncl.nclcustomerservice.object.SalesOrderList;
import com.ncl.nclcustomerservice.object.SalesPersonLineItem;
import com.ncl.nclcustomerservice.object.TadaList;

import java.util.List;

@Dao
public interface CommonDao {

    //login details
    @Query("SELECT * from LoginDb")
    LiveData<LoginDb> getLoginDetails();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLoginDetails(LoginDb... loginDb);

    @Delete
    void DeleteLoginDetails(LoginDb loginDb);

    @Query("SELECT * FROM Customer")
    List<Customer> getCustomers();

    @Query("SELECT * FROM Customer WHERE customerId=:custid")
    Customer getCustomerById(int custid);

    @Query("DELETE FROM Customer")
    void deleteCustomer();

    @Query("DELETE FROM LeadInsertReqVo")
    void deleteLeadList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGeoTrackingPojo(Geo_Tracking_POJO geo_tracking_pojo);

    @Query("SELECT * FROM Geo_Tracking_POJO WHERE visitDate LIKE :currentDate AND userId=:uId ORDER BY visitDate DESC LIMIT 1")
    Geo_Tracking_POJO getGeoTrackingData(String currentDate, int uId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertEmpActivities(EmpActivityPojo empActivityPojo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCustomerList(CustomerList customerLists);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCustomer(List<CustomerList> customerLists);

    @Query("SELECT * FROM CustomerList ORDER BY customerId DESC")
    List<CustomerList> getCustomerList();


    @Query("SELECT * FROM CustomerList where customerType is 'Direct Customer' and customerName like :customerName ORDER BY customerId DESC")
    List<CustomerList> searchCustomerList(String customerName);

    @Query("SELECT * FROM CustomerList where customerType is 'Third party Customer' and customerName like :customerName ORDER BY customerId DESC")
    List<CustomerList> searchThirdPartyCustomerList(String customerName);


    @Query("SELECT priceListId FROM CustomerList WHERE customerId=:id")
    String getPriceListid(int id);

    @Query("SELECT customerName FROM CustomerList WHERE customerId=:id")
    String getCustomerName(int id);

    @Query("DELETE FROM CustomerList")
    void deleteCustomerList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCustomerLineItems(List<CustomerUserList> customerUserList);

    @Query("SELECT * FROM CustomerUserList WHERE lineitemid=:customerId")
    List<CustomerUserList> getCustomerLineItems(int customerId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLeadList(List<LeadInsertReqVo> lead);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLead(LeadInsertReqVo lead);

    @Query("SELECT * FROM LeadInsertReqVo ORDER BY  leadsId DESC")
    List<LeadInsertReqVo> getLead();

    @Query("SELECT * FROM LeadInsertReqVo where leadProjectName like :firstName OR company like :firstName ORDER BY  leadsId DESC")
    List<LeadInsertReqVo> getLead(String firstName);

    @Query("SELECT * FROM LeadInsertReqVo where leadsId =:leadId")
    LeadInsertReqVo getLeadInOpportunity(String leadId);

//    @Query("UPDATE Lead set firstName=:firstName,lastName=:lastName,company=:company,associateContactId=:associateContactId,annualRevenue=:annualRevenue,description=:description,doNotCall=:doNotCall,email=:email,fax=:fax,industry=:industry,leadOwner=:leadOwner,leadSource=:leadSource,leadStatus=:leadStatus,mobile=:mobile,noEmployees=:noEmployees,phone=:phone,rating=:rating,title=:title,website=:website,status=:status,projectName=:projectName,projectType=:projectType,sizeClassProject=:sizeClassProject,statusProject=:statusProject,billingStreet1=:billingStreet1,billingstreet2=:billingstreet2,billingCountry=:billingCountry,stateProvince=:stateProvince,billingCity=:billingCity,billingZipPostal=:billingZipPostal,shippingStreet1=:shippingStreet1,shippingstreet2=:shippingstreet2,shippingCountry=:shippingCountry,shippingStateProvince=:shippingStateProvince,shippingCity=:shippingCity,shippingZipPostal=:shippingZipPostal WHERE leadsId=:leadsId")
//    void updateLeadList(String firstName, int leadsId,String lastName, String comapany, String associateContactId, String annualRevenue, String description, String doNotCall, String email,String fax,String industry, String leadOwner, String leadSource, String leadStatus, String mobile, String noEmployees, String phone, String rating, String title,String website,String status, String projectName, String projectType, String sizeClassProject, String statusProject, String billingStreet1,String billingstreet2,String billingCountry, String stateProvince, String billingCity, String billingZipPostal,String shippingStreet1, String shippingstreet2, String shippingCountry, String shippingStateProvince, String shippingZipPostal);

    @Update
    void updateLead(LeadInsertReqVo lead);

    @Update
    void updateCustomer(CustomerList customerList);

    @Update
    void updateContact(ContactList contactList);

    @Update
    void updateOpportunity(OpportunitiesList opportunitiesList);

    @Update
    void updateContract(ContractList contractList);

    @Update
    void updateSalesOrder(SalesOrderList salesOrderList);

    @Update
    void updateSallesCall(SalesCallList salesCallList);

    @Update
    void updateQuotation(QuotationList quotationList);

    @Query("DELETE FROM QuotationList where quotationId =:quotationId")
    void deleteQuotationListFromDb(int quotationId);

    @Query("DELETE FROM LeadInsertReqVo where leadsId =:leadsId")
    void deleteLeadFromDb(int leadsId);

    @Query("DELETE FROM CustomerList where customerId =:customerId")
    void deleteCustomerFromDb(int customerId);

    @Query("DELETE FROM OpportunitiesList where opportunityId =:opportunityId")
    void deleteOpportunitiesFromDb(int opportunityId);

    @Query("DELETE FROM ContractList where contractId =:contractId")
    void deleteContractFromDb(int contractId);

    @Query("DELETE FROM SalesOrderList where salesOrderId =:salesOrderId")
    void deleteSalesOrderFromDb(int salesOrderId);

    @Query("DELETE FROM SalesCallList where salesCallId =:salesCallId")
    void deleteSalesCallFromDb(int salesCallId);

    @Query("DELETE FROM TadaList where taDaId =:taDaId")
    void deleteTadaFromDb(int taDaId);

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    void insertProjectHeadContact(List<ProjectHeadReqVo> projectHeadReqVoList);

    @Query("SELECT * FROM ProjectHeadReqVo where projectHeadName like :queryString ORDER BY contactId DESC LIMIT :limit OFFSET :offset")
    List<ProjectHeadReqVo> getProjectHeadContactList(int limit, int offset, String queryString);

    @Query("SELECT * FROM ProjectHeadReqVo")
    List<ProjectHeadReqVo> getAllProjectHeadContactList();

    @Query("DELETE FROM ProjectHeadReqVo")
    void deleteProjectHeadContactList();

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    void insertContractorContact(List<CustomerContactResponseVo.ContactContractorList> contactContractorList);

    @Query("SELECT * FROM ContactContractorList where contractorName like :queryString ORDER BY contactId DESC LIMIT :limit OFFSET :offset")
    List<CustomerContactResponseVo.ContactContractorList> getContractorContactList(int limit, int offset, String queryString);

    @Query("SELECT * FROM ContactContractorList")
    List<CustomerContactResponseVo.ContactContractorList> getAllCustomerContactList();

    @Query("DELETE FROM ContactContractorList")
    void deleteContactContractorList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContact(List<ContactList> contact);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContactInsert(ContactList contactList);


    @Query("SELECT * FROM ContactList where firstName like :queryString ORDER BY contactId DESC LIMIT :limit OFFSET :offset")
    List<ContactList> getContactList(int limit, int offset, String queryString);

    @Query("DELETE FROM ContactList")
    void deleteContactList();

    @Query("DELETE FROM ContactList where contactId =:contactId")
    void deleteContactFromDb(int contactId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComplaints(List<ComplaintsTable> complaint);


    @Query("SELECT * FROM ComplaintsTable ORDER BY complaintId DESC LIMIT :limit OFFSET :offset")
    public List<ComplaintsTable> getComplaints(int limit, int offset);

    @Query("DELETE FROM ComplaintsTable")
    void deleteComplaintList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSalesCallList(List<SalesCallList> salesCallLists);

    @Query("SELECT * FROM SalesCallList ORDER BY salesCallId DESC LIMIT :limit OFFSET :offset")
    public List<SalesCallList> getSalesCallList(int limit, int offset);

    @Query("SELECT * FROM SalesCallList where company OR new_contact_customer_company_name like :queryString ORDER BY salesCallId DESC LIMIT :limit OFFSET :offset")
    public List<SalesCallList> getSalesCallList(int limit, int offset, String queryString);

    @Query("SELECT * FROM SalesCallList WHERE callDate LIKE :currectDate AND owner =:owner ORDER BY salesCallId DESC ")
    public List<SalesCallList> getSalesCallList(String currectDate, String owner);

    @Query("DELETE FROM SalesCallList")
    void deleteSalesCallList();

    @Query("SELECT * FROM SalesCallList WHERE callDate LIKE :currectDate")
    public List<SalesCallList> getTodaySalesCalls(String currectDate);


    @Query("UPDATE SalesCallList SET lat_lon_val=:lat_lon_val, geo_status=:geoStatus WHERE salesCallId=:appId")
    void updateSalesCallTable(String lat_lon_val, String geoStatus, int appId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertContractList(ContractList contractList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContract(List<ContractList> contractList);

    @Query("SELECT * FROM ContractList ORDER BY contractId DESC LIMIT :limit OFFSET :offset")
    public List<ContractList> getContractList(int limit, int offset);

    @Query("DELETE FROM ContactList")
    void deleteContractList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertQuotation(QuotationList qutationList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuotationList(List<QuotationList> qutationList);

    @Query("SELECT * FROM QuotationList ORDER BY quotationId DESC LIMIT :limit OFFSET :offset")
    public List<QuotationList> getQutation(int limit, int offset);

    @Query("SELECT * FROM QuotationList where opportunity =:opportunity ORDER BY quotationId DESC ")
    public List<QuotationList> getQutationByOppId(int opportunity);

    @Query("DELETE FROM QuotationList")
    void deleteQutationList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOpportunities(OpportunitiesList opportunitiesList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOpportunityList(List<OpportunitiesList> opportunitiesLists);

    @Query("SELECT * FROM OpportunitiesList ORDER BY opportunityId DESC LIMIT :limit OFFSET :offset")
    public List<OpportunitiesList> getOpportunitiesList(int limit, int offset);

    @Query("SELECT * FROM OpportunitiesList where companyText like :queryString ORDER BY opportunityId DESC LIMIT :limit OFFSET :offset")
    List<OpportunitiesList> getOpportunitiesList(int limit, int offset, String queryString);

    @Query("DELETE FROM OpportunitiesList")
    void deleteOpportunities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPaymentCollection(List<PaymentCollectionList> paymentCollectionLists);

    @Query("SELECT * FROM PaymentCollectionList ORDER BY paymentCollectionId DESC LIMIT :limit OFFSET :offset")
    public List<PaymentCollectionList> getPaymentCollectionList(int limit, int offset);

    @Query("SELECT * FROM PaymentCollectionList where customerName like :queryString ORDER BY paymentCollectionId DESC LIMIT :limit OFFSET :offset")
    public List<PaymentCollectionList> getPaymentCollectionList(int limit, int offset,String queryString);

    @Query("DELETE FROM PaymentCollectionList")
    void deletePaymentCollectionList();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSalesOrder(SalesOrderList salesOrderList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSalesOrderList(List<SalesOrderList> salesOrderList);

    @Query("SELECT * FROM SalesOrderList where orderTypeForm IS NULL OR orderTypeForm IS '' ORDER BY salesOrderId DESC LIMIT :limit OFFSET :offset")
    public List<SalesOrderList> getSalesOrderList(int limit, int offset);

    @Query("SELECT * FROM SalesOrderList where orderTypeForm IS 'Third Party' ORDER BY salesOrderId DESC LIMIT :limit OFFSET :offset")
    public List<SalesOrderList> getSalesOrderListThirdParty(int limit, int offset);

    @Query("SELECT * FROM SalesOrderList where customer like :queryString and orderTypeForm IS 'Third Party' ORDER BY salesOrderId DESC LIMIT :limit OFFSET :offset")
    List<SalesOrderList> getSalesOrderListThirdParty(int limit, int offset, String queryString);

    @Query("SELECT * FROM SalesOrderList where customer like :queryString and (orderTypeForm IS NULL OR orderTypeForm IS '') ORDER BY salesOrderId DESC LIMIT :limit OFFSET :offset")
    List<SalesOrderList> getSalesOrderList(int limit, int offset, String queryString);

    @Query("DELETE FROM SalesOrderList")
    void deleteSalesOrderList();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTadaList(List<TadaList> tadaList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTada(TadaList tada);

    @Query("SELECT * FROM TadaList ORDER BY taDaId DESC LIMIT :limit OFFSET :offset")
    public List<TadaList> getTadaList(int limit, int offset);

    @Query("DELETE FROM TadaList")
    void deleteTadaList();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertEmpActivitiesLogs(List<EmpActivityLogsPojo> empActivityLogsPojo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertEmpActivitiesLogs(EmpActivityLogsPojo empActivityLogsPojo);

    @Query("SELECT * FROM EmpActivityPojo WHERE date LIKE :currectDate")
    public List<EmpActivityPojo> getCheckinDetails(String currectDate);

    @Query("SELECT * FROM EmpActivityPojo WHERE date LIKE :currectDate AND employeeId=:empid")
    public List<EmpActivityPojo> getCheckinDetails(String currectDate, int empid);

//    @Query("SELECT * FROM Employee e INNER JOIN EmpActivityPojo emp ON(e.employeeId=emp.employeeId) GROUP BY e.employeeId")
//    List<Employee> getEmployeeActivity();

//    @Query("SELECT * FROM Employee WHERE roleId=:roleId")
//    List<Employee> getEmployeeActivity(int roleId);


    @Query("SELECT * FROM EmpActivityLogsPojo WHERE employeeActivityId =:employeeActivityId ORDER BY sqlPkLog DESC")
    List<EmpActivityLogsPojo> getCustomerCheckinDetails(String employeeActivityId);

    @Query("SELECT * FROM EmpActivityLogsPojo WHERE employeeActivityId =:employeeActivityId and date =:date and checkOutTime is null ORDER BY sqlPkLog DESC")
    public List<EmpActivityLogsPojo> isCustomerCheckedinDeatails(int employeeActivityId, String date);


    @Query("UPDATE GEO_TRACKING_POJO SET routePathLatLon =:routePath,syncStatus =:syncStatus WHERE trackingId =:trackingId")
    public void updateRoutePath(String routePath, int syncStatus, String trackingId);

    @Query("UPDATE EmpActivityLogsPojo SET checkOutTime =:checkOutTime,checkOutLatLong=:checkOutLatLong,remark=:remark, signature =:signature , status =:status WHERE sqlPkLog =:sqlPk")
    public void updateCustomerCheckout(String checkOutTime, String checkOutLatLong, String remark, String signature, int status, String sqlPk);


//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertProducts(List<Product> products);
//
//    @Query("SELECT * FROM Product")
//    List<Product> getProductsList();
//
//    @Query("DELETE FROM Product WHERE mobileSerivceDetailsId=:record")
//    void deleteProductRecord(long record);

    @Query("UPDATE EmpActivityPojo SET polyline =:polyline, checkOutTime=:checkOutTime, checkOutLatLong =:checkOutLatLong,distance =:distance, status=:status,gpsStatus =:gpsStatus  WHERE employeeActivityId =:employeeActivityId")
    public void updateDateCheckOutData(int employeeActivityId, String checkOutTime, String polyline, String checkOutLatLong, String distance, int status, String gpsStatus);

    @Query("DELETE FROM EmpActivityPojo")
    void deleteEmpActivityPojo();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContractLineItems(List<ContractLineItem> contractProduct);

    @Query("SELECT * FROM ContractLineItem WHERE lineItemId=:contractId")
    List<ContractLineItem> getContractLineItems(String contractId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssociateContacts(List<AssociateContact> associateContact);

    @Query("SELECT * FROM AssociateContact WHERE contacts=:opportunityId")
    List<AssociateContactLead> getAssociateContacts(String opportunityId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuotationLineItem(List<QuotationProductList> qutationProductLists);

    @Query("SELECT * FROM QuotationProductList where quotationLineId=:quotationId")
    List<QuotationProductList> getQuotationProductLineItem(String quotationId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOpportunitiesProducts(List<OpportunityProductLineItem> productOpportunitieList);

    @Query("SELECT * FROM OpportunityProductLineItem WHERE oppProduct=:opportunityId")
    List<OpportunityProductLineItem> getOpportunityProducts(String opportunityId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOpportunityCompetition(List<OpportunityCompetitionLineItem> opportunityCompetitionLineItems);

    @Query("SELECT * FROM OpportunityCompetitionLineItem WHERE opportunityCompetion=:opportunityId")
    List<OpportunityCompetitionLineItem> getOpportunityCompetition(String opportunityId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOpportunityBrandLineItem(List<OpportunityBrandsLineItem> opportunityBrandsLineItems);

    @Query("SELECT * FROM OpportunityBrandsLineItem WHERE oppoBrand=:opportunityId")
    List<OpportunityBrandsLineItem> getOpportunityBrand(String opportunityId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSalesOrderLineItem(List<SalesOrderLineItem> salesOrderProductList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSalesPersonOrderLineItem(List<SalesPersonLineItem> salesOrderProductList);

    @Query("SELECT * FROM SalesOrderLineItem WHERE saleslineItemId=:salesOrderId")
    List<SalesOrderLineItem> getSalesOrderLineItems(String salesOrderId);
@Query("SELECT * FROM SalesPersonLineItem WHERE saleslineItemId=:salesOrderId")
    List<SalesPersonLineItem> getSalesPersonOrderLineItems(String salesOrderId);

    @Query("UPDATE GEO_TRACKING_POJO SET status=:status, checkOutLatLong=:checkinlatlong,checkOutTime=:strDate,polyline=:polyline,distance=:distance,personalUsesKm=:personalUsesKm WHERE trackingId=:trackingId")
    void updateCheckoutData(String status, String checkinlatlong, String strDate, String polyline, String distance, String personalUsesKm, String trackingId);

    @Query("DELETE FROM Geo_Tracking_POJO")
    void deleteGeoTrackingdata();

    @Query("SELECT visitDate FROM GEO_TRACKING_POJO ORDER BY visitDate DESC LIMIT 1")
    String getMaxDate();

    @Query("UPDATE GEO_Tracking_POJO SET polyline=:polyline,distance=:distance,routePathLatLon=:routePathLatLon WHERE trackingId=:trackingId")
    void updateGeoTrackingPojo(String polyline, String distance, String routePathLatLon, String trackingId);


    @Query("SELECT * FROM CustomerList WHERE parentAccount=:customerId")
    List<CustomerList> getCustomersByParentAccount(String customerId);

    @Query("SELECT * FROM CustomerList WHERE customerId=:customerId")
    CustomerList getCustomerListById(int customerId);

    @Query("UPDATE CustomerList SET approval_comments=:approval_comments,approve_status=:approve_status WHERE customerId=:customerId")
    void updateApprovalStatus(String approval_comments, String approve_status, int customerId);


//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    public long insertOrderIndents(OrderIndent orderIndents);
//
//    @Query("SELECT * FROM OrderIndent WHERE createdBy =:empId")
//    List<OrderIndent> getOrderIndentList(int empId);
//
//    @Query("SELECT * FROM OrderIndent")
//    List<OrderIndent> getOrderIndentList();
//
//    @Query("DELETE FROM OrderIndent")
//    void deleteorderIndentList();
//
//    @Query("SELECT * FROM OrderIndent WHERE customerId =:customerId")
//    List<OrderIndent> getOrderIndents(String customerId);
//
//    @Query("UPDATE OrderIndent SET orderCode=:orderCode,orderId =:orderId,id=:id,status=:status WHERE id=:id")
//    void updateApproveOrderIndent(int id, String orderCode, int orderId, int status);
//
//
//    @Query("SELECT * FROM OrderIndent WHERE id=:id")
//        //orderId=:orderid AND orderCode=:orderCode
//    OrderIndent fetchOrderIndents(long id);//instant fetch for recently inserted record.
//
//
//    @Query("UPDATE OrderIndent SET orderCode=:orderCode,orderId =:orderId,orderType=:orderType,status=:status WHERE id=:instantVlaue")
//    void updateOrderIndent(int instantVlaue, String orderCode, int orderId, int orderType, int status);
//
//    @Insert
//    public void insertOrderLineItems(List<OrderLineItem> orderLineItem);
//
//    @Query("SELECT * FROM OrderLineItem")
//    List<OrderLineItem> getOrderLineItems();
//
//    @Query("SELECT * FROM OrderLineItem WHERE mobileSerivceDetailsId=:orderId GROUP BY invoiceNumber")
//    List<OrderLineItem> getInvoiceNumber(int orderId);
//
//    @Query("SELECT * FROM OrderLineItem WHERE invoiceNumber =:invoiceNumber")
//    List<OrderLineItem> getInvoicedLineItems(String invoiceNumber);


//    @Query("SELECT * FROM OrderLineItem WHERE mobileSerivceDetailsId=:orderid")
//    List<OrderLineItem> getOrderLineItems(int orderid);
//
//    @Query("SELECT * FROM OrderLineItem WHERE orderId=:orderid AND invoiceNumber=:invnumber")
//    List<OrderLineItem> getLineItems(int orderid, String invnumber);
//
//    @Query("SELECT * FROM DispatchedLineItems WHERE parentreferenceKey=:orderid AND deliveredStatus=:deliveredStatus")
//    List<DispatchedLineItems> getDeliveredList(int orderid, int deliveredStatus);
//
//    @Query("SELECT * FROM MASTERPRODUCT WHERE productid=:customerId")
//    GetProductList getOnlyProducts(int customerId);
//
//    @Query("DELETE FROM OrderLineItem")
//    void deleteOrderProducts();

//    @Query("UPDATE OrderLineItem SET orderLineItemId=:orderLineItemId,orderId=:orderId WHERE mobileSerivceDetailsId=:id")
//    void updateOrderLineItem(int orderLineItemId, int orderId, int id);
//
//    @Query("UPDATE OrderLineItem SET approvedStatus=:status WHERE mobileSerivceDetailsId=:id")
//    void updateOrderLineItemWithStatus(int status, int id);
//
//    @Query("UPDATE OrderLineItem SET quantity =:quantity,ordersPrice=:ordersPrice WHERE myid=:id")
//    void updateOrderLineItem(String quantity, double ordersPrice, int id);

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    long insertComplaints(ComplaintList complaintLists);
//
//    @Query("SELECT * FROM StockDelivery")
//    List<StockDelivery> getStockDelivery();//int stockdeliveryId
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    long insertStockDelivery(StockDelivery stockDelivery);
//
//    @Query("SELECT * FROM StockDelivery WHERE orderId=:orderId")
//    StockDelivery getStockDeilvery(int orderId);
//
//    @Query("SELECT * FROM ComplaintList")
//    List<ComplaintList> getComplaint();
//
//    @Query("DELETE FROM ComplaintList")
//    public void deleteComplaints();
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertImages(List<ImageList> imageLists);
//
//
//    @Query("SELECT * FROM ImageList WHERE referenceKey=:referenceKey")
//    List<ImageList> getImages(int referenceKey);
//
//    @Query("UPDATE ImageList SET localImage=:localImage WHERE imageKey=:imageKey")
//    public void updateImageList(String localImage, int imageKey);
//
//    @Query("DELETE FROM ImageList")
//    void deleteImages();
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertPayment(List<PaymentList> paymentLists);
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    long insertPaymentItem(PaymentList paymentList);
//
//    @Query("SELECT * FROM PaymentList WHERE paymentId =:paymentId")
//    List<PaymentList> fetchPendingPayments(int paymentId);
//
//    @Query("SELECT * FROM PaymentList WHERE paymentPrimaryKey=:paymentPrimaryKey")
//    PaymentList lastInsertedPayment(int paymentPrimaryKey);
//
//    @Query("SELECT * FROM PaymentList WHERE paymentPrimaryKey =:paymentPrimaryKey ORDER BY paymentPrimaryKey ASC")
//    List<PaymentList> getPayementPrimary(String paymentPrimaryKey);
//
//    @Query("SELECT * FROM PaymentList WHERE customerId =:customerId")
//    List<PaymentList> getPaymentList(int customerId);
//
//    @Query("SELECT * FROM PaymentList")
//    List<PaymentList> getMoPaymentList();
//
//    @Query("DELETE FROM PaymentList")
//    void deletePayment();
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    public void insertSchemeProduct(SchemeProduct schemeProduct);
//
//    @Query("SELECT * FROM SchemeProduct")
//    List<SchemeProduct> getSchemeProducts();
//
//    @Query("DELETE FROM SchemeProduct")
//    public void deleteSchemeProduct();
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    public void insertEmployeeCompany(EmployeeCompany employeeCompany);
//
//    @Query("SELECT * FROM EmployeeCompany")
//    List<EmployeeCompany> getEmployeeCompanies();
//
//    @Query("DELETE FROM EmployeeCompany")
//    public void deleteEmployeeCompany();

//    @Query("SELECT * FROM EmployeeCompany ec INNER JOIN Company c ON (ec.companyId=c.companyId) WHERE ec.employeeId=:id  AND ec.status=:status GROUP BY ec.companyId")
//    public List<Company> getCompaniesByEmployee(int id, String status);

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    public void insertTableVersionData(List<UpdateTableVersion> updateTableVersions);
//
//    @Query("SELECT * FROM UPDATETABLEVERSION")
//    public List<UpdateTableVersion> getTableVersionData();
//
//    @Query("UPDATE UpdateTableVersion SET status=:status WHERE id=:id")
//    void updateTableVersionData(String id, String status);
//
//    @Query("UPDATE PaymentList SET paymentId=:paymentId WHERE paymentPrimaryKey=:paymentPrimaryKey")
//    void updatePaymentRecord(int paymentId, int paymentPrimaryKey);
//
//    @Query("SELECT * FROM EmpActivityPojo WHERE employeeId =:emp_id AND date =:date")
//    EmpActivityPojo getpolyLine(String emp_id, String date);
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertPictures(List<Pictures> pictures);
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertDispatchedPictures(List<DispatchedPictures> pictures);
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertDeliveredPictures(List<DeliveredPictures> pictures);
//
//    @Query("SELECT * FROM STOCKDELIVERY WHERE orderId=:orderId AND invoiceNumber=:invoiceNumber")
//    StockDelivery getDispatchDetails(int orderId, String invoiceNumber);
//
//    @Query("SELECT * FROM Pictures WHERE referenceKey=:stockKey")
//    List<Pictures> getPictures(int stockKey);
//
//    @Query("SELECT * FROM DispatchedPictures WHERE referenceKey=:stockKey")
//    List<DispatchedPictures> getDispatchedPictures(int stockKey);
//
//    @Query("SELECT * FROM DeliveredPictures WHERE referenceKey=:stockKey")
//    List<DeliveredPictures> getDeliveredPictures(int stockKey);
}