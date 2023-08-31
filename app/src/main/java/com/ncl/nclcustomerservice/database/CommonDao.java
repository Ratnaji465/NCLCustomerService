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
import com.ncl.nclcustomerservice.object.ComplaintRegisterMasterVo;
import com.ncl.nclcustomerservice.object.ComplaintsInsertReqVo;
import com.ncl.nclcustomerservice.object.ComplaintsTable;
import com.ncl.nclcustomerservice.object.ContactList;
import com.ncl.nclcustomerservice.object.ContractList;
import com.ncl.nclcustomerservice.object.Customer;
import com.ncl.nclcustomerservice.object.CustomerContactResponseVo;
import com.ncl.nclcustomerservice.object.CustomerList;
import com.ncl.nclcustomerservice.object.CustomerProjectResVO;
import com.ncl.nclcustomerservice.object.DailyReportsAddVO;
import com.ncl.nclcustomerservice.object.DivisionMasterList;
import com.ncl.nclcustomerservice.object.FabUnitList;
import com.ncl.nclcustomerservice.object.Geo_Tracking_POJO;
import com.ncl.nclcustomerservice.object.NatureOfComplaintList;
import com.ncl.nclcustomerservice.object.OpportunitiesList;
import com.ncl.nclcustomerservice.object.OpportunityBrandsLineItem;
import com.ncl.nclcustomerservice.object.PaymentCollectionList;
import com.ncl.nclcustomerservice.object.ProjectHeadReqVo;
import com.ncl.nclcustomerservice.object.ProjectTypeList;
import com.ncl.nclcustomerservice.object.QuotationList;
import com.ncl.nclcustomerservice.object.QuotationProductList;
import com.ncl.nclcustomerservice.object.SalesCallList;
import com.ncl.nclcustomerservice.object.SalesOrderLineItem;
import com.ncl.nclcustomerservice.object.SalesOrderList;
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

    @Update
    void updateCustomer(CustomerList customerList);

    @Update
    void updateContact(ContactList contactList);

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProjectHeadContact(List<ProjectHeadReqVo> projectHeadReqVoList);

    @Query("SELECT * FROM ProjectHeadReqVo where projectHeadName like :queryString ORDER BY contactId DESC LIMIT :limit OFFSET :offset")
    List<ProjectHeadReqVo> getProjectHeadContactList(int limit, int offset, String queryString);

    @Query("SELECT * FROM ProjectHeadReqVo")
    List<ProjectHeadReqVo> getAllProjectHeadContactList();

    @Query("DELETE FROM ProjectHeadReqVo")
    void deleteProjectHeadContactList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContractorContact(List<CustomerContactResponseVo.ContactContractorList> contactContractorList);

    @Query("SELECT * FROM ContactContractorList where contractorName ORDER BY contactId DESC LIMIT :limit OFFSET :offset")
    List<CustomerContactResponseVo.ContactContractorList> getContractorContactList(int limit, int offset);

    @Query("SELECT * FROM ContactContractorList")
    List<CustomerContactResponseVo.ContactContractorList> getAllCustomerContactList();

    @Query("DELETE FROM ContactContractorList")
    void deleteContactContractorList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContact(List<ContactList> contact);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContactInsert(ContactList contactList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCustomerProject(CustomerProjectResVO customerProjectResVO);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCustomerProjectList(List<CustomerProjectResVO> customerProjectResVOList);

    @Query("DELETE FROM CustomerProjectResVO")
    void deleteCustomerProjectList();

    @Query("SELECT * FROM CustomerProjectResVO where projectName like :queryString ORDER BY customerProjectId DESC LIMIT :limit OFFSET :offset")
    List<CustomerProjectResVO> getCustomerProjectList(int limit, int offset, String queryString);

    @Query("SELECT * FROM CustomerProjectResVO")
    List<CustomerProjectResVO> getAllCustomerProjectList();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProjectTypeList(List<ProjectTypeList> projectTypeLists);
    @Query("DELETE FROM ProjectTypeList")
    void deleteProjectTypeList();
    @Query("SELECT * FROM ProjectTypeList")
    List<ProjectTypeList> getAllProjectTypeList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDivisionMasterList(List<DivisionMasterList> divisionMasterLists);
    @Query("DELETE FROM DivisionMasterList")
    void deleteDivisionMasteList();
    @Query("SELECT * FROM DivisionMasterList")
    List<DivisionMasterList> getAllDivisionMasterList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFabUnitList(List<FabUnitList> fabUnitLists);
    @Query("DELETE FROM FabUnitList")
    void deleteFabUnitList();
    @Query("SELECT * FROM FabUnitList")
    List<FabUnitList> getAllFabUnitList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNatureOfComplaint(List<NatureOfComplaintList> natureOfComplaintLists);
    @Query("DELETE FROM NatureOfComplaintList")
    void deleteNatureOfComplaintList();
    @Query("SELECT * FROM NatureOfComplaintList")
    List<NatureOfComplaintList> getAllNatureOfComplaintList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDailyReports(DailyReportsAddVO dailyreportsResVO);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDailyReportsList(List<DailyReportsAddVO> dailyreportsResVOList);

    @Query("DELETE FROM DailyReportsAddVO")
    void deleteDailyReportsList();

    @Query("SELECT * FROM DailyReportsAddVO where contractorName like :queryString ORDER BY csDailyreportId DESC LIMIT :limit OFFSET :offset")
    List<DailyReportsAddVO> getDailyReportsList(int limit, int offset, String queryString);

    @Query("SELECT * FROM DailyReportsAddVO")
    List<DailyReportsAddVO> getAllDailyReportsList();



    @Query("SELECT * FROM ContactList where firstName like :queryString ORDER BY contactId DESC LIMIT :limit OFFSET :offset")
    List<ContactList> getContactList(int limit, int offset, String queryString);

    @Query("DELETE FROM ContactList")
    void deleteContactList();

    @Query("DELETE FROM ContactList where contactId =:contactId")
    void deleteContactFromDb(int contactId);


//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertComplaints(List<ComplaintsTable> complaint);
//
//
//    @Query("SELECT * FROM ComplaintsTable where customerName like :queryString ORDER BY complaintId DESC LIMIT :limit OFFSET :offset")
//    public List<ComplaintsTable> getComplaints(int limit, int offset, String queryString);
//
//    @Query("DELETE FROM ComplaintsTable")
//    void deleteComplaintList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComplaints(List<ComplaintsInsertReqVo> complaint);


    @Query("SELECT * FROM ComplaintsInsertReqVo where clientName like :queryString ORDER BY csComplaintRegisterId DESC LIMIT :limit OFFSET :offset")
    public List<ComplaintsInsertReqVo> getComplaints(int limit, int offset, String queryString);

    @Query("DELETE FROM ComplaintsInsertReqVo")
    void deleteComplaintList();

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
    public List<PaymentCollectionList> getPaymentCollectionList(int limit, int offset, String queryString);

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