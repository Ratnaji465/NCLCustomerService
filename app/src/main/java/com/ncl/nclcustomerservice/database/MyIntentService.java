package com.ncl.nclcustomerservice.database;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


/**
 * Created by User on 8/24/2018.
 */

public class MyIntentService extends IntentService {

    DatabaseHandler db;
    String SENSATIONAL;

    @Override
    public void onCreate() {
        super.onCreate();
        db = DatabaseHandler.getDatabase(this);
    }

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        SENSATIONAL = intent.getStringExtra("sensational");

        if (SENSATIONAL.equalsIgnoreCase("islogin")) {
//            LoginResVo loginResVo = (LoginResVo) intent.getSerializableExtra("loginresponse");
//            saveLoginInDb(loginResVo);
        } /*else if (SENSATIONAL.equalsIgnoreCase("productcatalogue")) {
            List<ProductList> headerList = (List<ProductList>) intent.getSerializableExtra("productcatalogue");
//            saveProductCatalogueInDb(headerList);

        } /*else if (SENSATIONAL.equalsIgnoreCase("complaints")) {
            List<ComplaintList> complaintLists = (List<ComplaintList>) intent.getSerializableExtra("complaints");
            db.commonDao().deleteComplaints();
            for (ComplaintList complaintList : complaintLists) {
                long complaintPrimaryKey = db.commonDao().insertComplaints(complaintList);
                List<ImageList> imageLists = complaintList.imageList;
                if (imageLists != null) {
                    for (int i = 0; i < imageLists.size(); i++) {
                        imageLists.get(i).referenceKey = (int) complaintPrimaryKey;
                    }
                    db.commonDao().insertImages(imageLists);
                }
            }

        }*//*else if (SENSATIONAL.equalsIgnoreCase("paymentlist")){
            List<PaymentList> paymentLists = (List<PaymentList>) intent.getSerializableExtra("paymentlist");
//            savePayments(paymentLists);
//            db.commonDao().deletePayment();
            for (PaymentList paymentList : paymentLists) {

            db.commonDao().insertPayment(paymentList);
            }



    }*/ else if (SENSATIONAL.equalsIgnoreCase("masters")) {
//            MasterData masterData = (MasterData) intent.getSerializableExtra("master_data");
//            ArrayList<Customer> customers = intent.getParcelableArrayListExtra("customers");
//            List<Employee> employees = intent.getParcelableArrayListExtra("employees");
//            List<PriceList> priceList = intent.getParcelableArrayListExtra("price_list");
//            List<MasterProduct> products = intent.getParcelableArrayListExtra("products");
//            List<Region> regions = intent.getParcelableArrayListExtra("regions");
//            List<Scheme> schemes = intent.getParcelableArrayListExtra("schemes");
//            List<State> states = intent.getParcelableArrayListExtra("states");
//            List<Company> companies = intent.getParcelableArrayListExtra("company");
//            List<SchemeProduct> schemeProducts = intent.getParcelableArrayListExtra("scheme_product");
//            List<EmployeeCompany> employeeCompanies = intent.getParcelableArrayListExtra("employee_company");
           // List<EmployeeCustomer> employeeCustomers = intent.getParcelableArrayListExtra("empcustmap");
            //List<MoCustomer> moCustomers = intent.getParcelableArrayListExtra("mocustmap");
//            Common.Log.i("debugged" + employeeCompanies.size());
//            for (int i = 0; i < customers.size(); i++) {
//                db.commonDao().insertCustomer(customers.get(i));
//            }
//            if (employeeCustomers != null)
//                for (int i = 0; i < employeeCustomers.size(); i++) {
//                    db.commonDao().insertEmployeeCustomer(employeeCustomers.get(i));
//                }
//            if (moCustomers != null)
//                for (int i = 0; i < moCustomers.size(); i++) {
//                    db.commonDao().insertMoCustomer(moCustomers.get(i));
//                }

//            if (employees != null)
//                for (int i = 0; i < employees.size(); i++) {
//                    db.commonDao().insertEmployee(employees.get(i));
//                }
//            for (int i = 0; i < priceList.size(); i++) {
//                db.commonDao().insertPriceList(priceList.get(i));
//            }
//            for (int i = 0; i < products.size(); i++) {
//                db.commonDao().insertProduct(products.get(i));
//            }
//            for (int i = 0; i < regions.size(); i++) {
//                db.commonDao().insertRegion(regions.get(i));
//            }
//            for (int i = 0; i < schemes.size(); i++) {
//                db.commonDao().insertScheme(schemes.get(i));
//            }
//            for (int i = 0; i < states.size(); i++) {
//                db.commonDao().insertState(states.get(i));
//            }
//            for (int i = 0; i < companies.size(); i++) {
//                db.commonDao().insertCompany(companies.get(i));
//            }
//            for (int i = 0; i < schemeProducts.size(); i++) {
//                db.commonDao().insertSchemeProduct(schemeProducts.get(i));
//            }
//            if (employeeCompanies != null)
//                for (int i = 0; i < employeeCompanies.size(); i++) {
//                    db.commonDao().insertEmployeeCompany(employeeCompanies.get(i));
//                }
        } else if (SENSATIONAL.equalsIgnoreCase("get_masters_db")) {
            Intent receiveIntent = new Intent("get_master_data");
            // You can also include some extra data.
            Bundle b = new Bundle();
//            b.putParcelableArrayList("company", getCompany());
//            b.putParcelableArrayList("customers", getCustomers());
//            b.putParcelableArrayList("employees", getEmployees());
            receiveIntent.putExtra("masters_db", b);
//            receiveIntent.setAction("get_master_data");
//            sendBroadcast(receiveIntent);
            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(receiveIntent);

        } else if (SENSATIONAL.equalsIgnoreCase("get_products_db")) {
            Intent receiveIntent = new Intent("get_product_data");
            // You can also include some extra data.
//            Bundle b = new Bundle();
//            receiveIntent.putExtra("products", getProducts());
//            receiveIntent.putExtra("products_db", b);
            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(receiveIntent);
        } else if (SENSATIONAL.equalsIgnoreCase("get_orders_db")) {
            Intent receiveIntent = new Intent("get_orders_data");
            // You can also include some extra data.
            String customer_id = intent.getStringExtra("customer_id");
//            receiveIntent.putExtra("order_indent", (Serializable) db.commonDao().getOrderIndentList());
//            receiveIntent.putExtra("order_products", (Serializable) db.commonDao().getOrderLineItems());
            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(receiveIntent);

        } else if (SENSATIONAL.equalsIgnoreCase("get_custmproducts_db")) {
//            Common.Log.i("Customers " + getCustomers().size());
//            Common.Log.i("Employees " + getEmployees().size());
//            Common.Log.i("Products " + getProducts().size());
//            Common.Log.i("PriceLists " + getPriceList().size());
//            Common.Log.i("Regions " + getRegions().size());
//            Common.Log.i("States " + getStates().size());
//            Common.Log.i("Schemes " + getSchemes().size());
            Intent receiveIntent = new Intent("get_custmproduct_data");

            Bundle b = new Bundle();
//            b.putParcelableArrayList("customers", getCustomers());
//            b.putParcelableArrayList("products", getProducts());
            receiveIntent.putExtra("custmproducts_db", b);
            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(receiveIntent);
        } else if (SENSATIONAL.equalsIgnoreCase("get_orderindent_db")) {
            // int user_id = intent.getIntExtra("user_id", -1);
            String customer_id = intent.getStringExtra("customer_id");
            Intent receiveIntent = new Intent("get_orderindent_data");
//            receiveIntent.putExtra("orderIndents_db", (Serializable) getOrderIndents(customer_id));
            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(receiveIntent);


        } else if (SENSATIONAL.equalsIgnoreCase("get_products_from_db")) {
            int customer_id = intent.getIntExtra("customer_id", -1);
            Intent receiveIntent = new Intent("get_products_data");
//            receiveIntent.putExtra("products_db", (Serializable) getProductsList(customer_id));
            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(receiveIntent);

        } else if (SENSATIONAL.equalsIgnoreCase("delete_masters_db")) {
            db.commonDao().deleteCustomer();
//            db.commonDao().deleteEmployee();
//            db.commonDao().deleteMasterProduct();
//            db.commonDao().deletePriceList();
//            db.commonDao().deleteRegion();
//            db.commonDao().deleteScheme();
//            db.commonDao().deleteState();
//            db.commonDao().deleteCompany();
//            db.commonDao().deleteSchemeProduct();
//            db.commonDao().deleteEmployeeCompany();
//            db.commonDao().deleteEmployeeCustomer();
//            db.commonDao().deleteMoCustomer();
//            List<OrderIndent> od = db.commonDao().getOrderIndentList();
//            for (int i = 0; i < od.size(); i++) {
//                db.commonDao().deleteorderIndentList(od.get(i).id);
//                db.commonDao().deleteOrderProducts(od.get(i).id);
//            }

        } else if (SENSATIONAL.equalsIgnoreCase("insert_order")) {

//        if(intent.getStringExtra("insert").equalsIgnoreCase("fetchall")){
//            db.commonDao().deleteorderIndentList();
//        }

//            List<OrderIndent> orderIndents = (List<OrderIndent>) intent.getSerializableExtra("order_indent");

//            for (OrderIndent object : orderIndents) {
//                long orderindentkey = db.commonDao().insertOrderIndents(object);
//                List<OrderLineItem> orderLineItems = object.products;
//                if (orderLineItems != null) {
//                    for (OrderLineItem orderLineItem : orderLineItems) {
//                        orderLineItem.mobileSerivceDetailsId = (int) orderindentkey;
//                        db.commonDao().updateOrderLineItem(orderLineItem.orderLineItemId, orderLineItem.orderLineItemId, orderLineItem.orderId);
//                    }
//                }
//            }
        }
    }

//    private List<OrderIndent> getOrderIndents(String customer_id) {
//        return db.commonDao().getOrderIndents(customer_id);
//    }

//    private List<OrderLineItem> getProductsList(int customer_id) {
//        return db.commonDao().getOrderLineItems(customer_id);
//    }

//    public ArrayList<Customer> getCustomers() {
//        return (ArrayList<Customer>) db.commonDao().getCustomers();
//    }

//    public ArrayList<Company> getCompany() {
//        return (ArrayList<Company>) db.commonDao().getCompany();
//    }

//    public ArrayList<Employee> getEmployees() {
//        return (ArrayList<Employee>) db.commonDao().getEmployees();
//    }

//    public ArrayList<MasterProduct> getProducts() {
//        return (ArrayList<MasterProduct>) db.commonDao().getProducts();
//    }

//    public ArrayList<PriceList> getPriceList() {
//        return (ArrayList<PriceList>) db.commonDao().getPriceList();
//    }

//    public ArrayList<Region> getRegions() {
//        return (ArrayList<Region>) db.commonDao().getRegions();
//    }

//    public ArrayList<State> getStates() {
//        return (ArrayList<State>) db.commonDao().getStates();
//    }

//    public ArrayList<Scheme> getSchemes() {
//        return (ArrayList<Scheme>) db.commonDao().getSchemes();
//    }

//    public ArrayList<ComplaintList> getComplaint() {
//        return (ArrayList<ComplaintList>) db.commonDao().getComplaint();
//    }


//    private void saveLoginInDb(LoginResVo loginResVo) {
//        LoginDb loginDb1 = new LoginDb();
//        loginDb1.setEmailid(loginResVo.emailId);
//        loginDb1.setMobilenumber(loginResVo.mobileNumber);
//        loginDb1.setPassword(loginResVo.password);
//        loginDb1.setProfileimg(loginResVo.profileImg);
//        loginDb1.setUserid(loginResVo.userId);
//        loginDb1.setUsername(loginResVo.userName);
//        loginDb1.setUsertype(loginResVo.userType);
//        db.commonDao().insertLoginDetails(loginDb1);
//        Common.Log.i("pre login --->  " + loginDb1);
//        Common.Log.i("pre login list ---->  " + db.commonDao().getLoginDetails());
//    }

}
