package com.ncl.nclcustomerservice.commonutils;


public class Constants {

//    public static final String API= "Api_1";
    public static final String API= "RestApi";
    public static final String PDUS = "pdus";
    public static final String ANDROID = "Android";
    public static final int SUCCESS = 200;
    public static final String KEY_1 = "key1";
    public static final long CONNECTION_TIME_OUT = 60;
    public static final long READ_TIME_OUT = 60;
    public static final long WRITE_TIME_OUT = 60;
    public static final int ZOOM_LEVEL = 17;
    public static final String MESSAGE = "message";
    public static final String CODE = "code";
    public static final String DESCRIPTION = "description";


    public static final String INTERNET_UNABLEABLE = "Not connected to the internet. Please check your connection and try again.";
    public static final String CUSTOMER = "customer";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";
    public static final String ADD = "ADD";
    public static final String NO_INTERNET_CONNECTION_MESSAGE = "Please Connect to Internet.\nAnd Try Again.";
    public static final String UNABLE_TO_CONNECT_OUR_SERVER = "Unable to Connect Our Server.\nPlease Try Again Later.";
    public static final String ENCRYPTION_KEY = "TATX.KSA";
    public static final String MOBILE = "mobile";
    public static final String CUSTOM_FONT_PATH = "fonts/Montserrat-Regular.ttf";
    public static final String CUSTOM_FONT_PATH_BOLD = "fonts/Montserrat-SemiBold.ttf";
    public static final String CUSTOM_FONT_BOLD = "fonts/montserratbold.ttf";

    public static final int _201 = 201;
    public static final int _202 = 202;


    public interface TableNames {
        String UPDATE_TABLE_VERSION = "update_table_version";
        String EMPLOYEE = "employee";
        String CUSTOMERS = "customers";
        String REGION = "region";
        String PRICE_LIST = "price_list";
        String PRODUCTS = "products";
        String SCHEMES = "schemes";
        String API_KEY = "api_key";
    }


    public interface SharedPreferencesKeys {
        String FIRSTNAME = "firstname";
        String LASTNAME = "lastname";
        String USERNAME = "username";
        String USERID = "userid";
        String LOGIN_STATUS = "loginStatus";
        String USER_DETIALS = "userDetails";
        String APP_LOCALE = "appLocale";
        String USER_ROLE = "role";
        String FACTORY_ID = "factory_id";
        String PASSWORD = "password";
        String EMAIL = "email";
        String MOBILE = "mobile";
        String ROLENAME = "rolename";
        String COMPANYID = "company_id";
        String ROLE_ID = "role_id";
        String DEPARTMENTID = "dept_id";
        String DEPARTMENTNAME = "dept_name";
        String COMPANYNAME = "COMPANYNAME";
        String LOGINTYPE = "login_type";
        String PROFILE_ID = "profile_id";
        String PROFILE_NAME = "profile_name";
        String LOCATION_SERVICE_STATUS = "location_service_status";
        String IMAGE = "image";
        String PRICE_LIST_ID = "price_list_id";
        String PRICE_LIST_NAME = "price_list_name";
        String LOGINID = "loginid";
        String TRACKING_ID = "tracking_id";
        String LIST = "user_id";
        String USER = "users_team";
        String DIVISIONS_LIST = "divisions_list";


        String LEFTNAV = "left_nav";
    }


    public interface IntentKeys {

        String SHOW_CONTENT_VIEW = "showContentView";
    }

    public interface MethodNames {
        String DASHBOARD = "dashboard";
        String LEAD_LIST = "lead_list";
        String CONTACT_LIST = "contact_list";
        String CUSTOMER_LIST = "customer_list";
        String CUSTOMER_LIST_THIRDPARTY = "customer_list_thirdparty";
        String OPPORTUNITY_LIST = "opportunity_list";
        String CONTRACT_LIST = "contract_list";
        String SALES_ORDER_LIST = "sales_oders_list";
        String SALES_ODERS_THIRDPARTY = "sales_oders_thirdparty";
        String SALES_CALLS_LIST = "calls_list";
        String COMPLAINT_LIST = "complaint_list";
        String EXPENSES_LIST = "ta_list";
        String PAYMENT_COLLECTIONS = "payment_collection";
        String NOTIFICATION = "Notification";
        String ROUTE_MAP = "route_map";
        String QUOTATION_LIST = "quotation_list";


    }
    public interface New_MethodNames{
        String CONTACTS = "Contacts";
        String CUSTOMER_PROJECT = "Customer Project";
        String DAILY_REPORT = "Daily Report";
        String FINAL_REPORT = "Final Report";
        String LOGOUT="logout";
    }
    public interface RequestNames {
        String ADD_CONTRACTOR_CONTACT="add_contact_contractor";
        String EDIT_CONTRACTOR_CONTACT="edit_contact_contractor";
        String ADD_CONTACT_PROJECT_HEAD="add_contact_project_head";
        String EDIT_CONTACT_PROJECT_HEAD="edit_contact_project_head";
        String MASTERS_LIST = "masters_list";
        String DASHBOARD = "dashboard";
        String GET_RECORDS_BY_TABLE = "get_records_by_table";
        String CHECKIN_CHECKOUT = "employee_check_in_check_out";
        String CUSTOMER_CHECK_OUT = "customer_check_out";
        String CUSTOMER_CHECK_IN = "customer_check_in";
        String UPDATE_ROUTEPATH = "updateRoutePath";
        String LOGIN_DETAILS = "login_details";
        String CHANGE_PASSWORD = "change_password";
        String PROFILE_UPDATE = "profile_update";
        String FORGOT_PASSWORD = "forgot_password";
        String PROFILE_PICTURE_UPLOAD = "profile_picture_upload";
        String LEAD_LIST = "lead_list";
        String DROP_DOWN_LIST = "dropdownlist";
        String INSERT_LEAD = "lead_insert";
        String CUSTOMER_LIST = "customer_list";
        String CONTACT_LIST = "contact_list";
        String CONTACT_EDIT = "contact_edit";
        String LEADS_CONVERT_CHECK = "leads_convert_check";
        String CONTACT_INSERT = "contact_insert";
        String CUSTOMER_DELETE = "customer_delete";
        String CUSTOMER_INSERT = "customer_insert";
        String CUSTOMER_EDIT = "customer_edit";
        String LEAD_DELETE = "lead_delete";
        String LEAD_EDIT = "lead_edit";
        String SALES_CALL_INSERT = "sales_calls_insert";
        String SALES_CALL_EDIT = "sales_calls_edit";
        String SALES_RELATED_TO = "sales_related_to";
        String CONTACT_DELETE = "contact_delete";
        String SALES_CALL_LIST = "sales_calls_list";
        String SALES_CALL_DELETE = "sales_calls_delete";
        String LEAD_CONVERT = "lead_convert";
        String OPPORTUNITIES_LIST = "opportunities_list";
        String OPPORTUNITIES_EDIT = "opportunities_edit";
        String OPPORTUNITIES_INSERT = "opportunity_insert";
        String OPPORTUNITIES_DELETE = "opportunities_delete";
        String ASSOCIATE_RECORD_DELETE = "delete_record";
        String PRODUCT_PRICE_LIST = "product_price_list";
        String CONTRACT_DROPDOWN = "contract_dropdown";
        String CONTRACTS_LIST = "contract_list";
        String CONTRACT_EDIT = "contract_edit";
        String CONTRACT_INSERT = "contract_insert";
        String CONTRACT_DELETE = "contract_delete";
        String TA_DA_LIST = "tada_list";
        String TA_DA_INSERT = "tada_insert";
        String TA_DA_EDIT = "tada_edit";
        String TA_DA_DELETE = "tada_delete";
        String TA_DA_DROPDOWN = "tada_dropdown";
        String COMPLAINTS_LIST = "complaint_list";
        String COMPLAINTS_INSERT = "complaint_insert";
        String COMPLAINTS_DELETE = "complaint_delete";
        String COMPLAINTS_EDIT = "complaint_edit";
        String QUOTATION_INSERT = "qutation_insert";
        String QUOTATION_EDIT = "qutation_edit";
        String QUOTATION_DELETE = "qutation_delete";
        String CUSTOMER_QUOTATION = "qutation_list_all";
        String QUOTATION_OPPORTUNITY_LIST = "quotation_opportunities_list";
        String QUOTATION_LIST = "qutation_list";
        String QUOTATION_PDF_MAIL = "qutation_pdf_mail";
        String EXPENSES_LIST = "expenses_list";
        String EXPENSES_INSERT = "expenses_insert";
        String EXPENSES_EDIT = "expenses_edit";
        String EXPENSES_DELETE = "expenses_delete";
        String SALES_ORDER_LIST = "salesorder_list";
        String SALES_ORDER_INSERT = "sales_order_insert";
        String SALES_ORDER_BY_THIRD_PARTY = "sales_order_by_third_party";
        String SALES_ORDER_BY_DIRECT_PARTY = "sales_order_by_direct_party";
        String SALES_ORDER_EDIT = "sales_order_edit";
        String LIST_VIEW = "list_view_all";
        String CUSTOMER_USER_LIST = "customer_user_list";
        String CUSTOMER_USER_DELETE = "customer_user_delete";
        String CUSTOMER_USER_INSERT = "customer_user_insert";
        String SALES_ORDER_CONTRACT_LIST = "sales_order_contract_list";
        String CONTRACT_APPROVAL = "contract_approval";
        String SALESORDER_APPROVE = "salesorder_approve";
        String QUOTATION_APPROVAL = "quotation_approval";
        String CUSTOMER_APPROVAL = "customer_approval";
        String SALES_ORDER_LINE_ITEM_DELETE = "sales_order_line_item_delete";
        String SALES_ORDER_DELETE = "sales_order_delete";
        String GEO_CHECK_IN_OUT = "geo_check_inout";
        String GEO_UPDATE_PATH = "geo_updatepath";
        String GEO_POLYLINE="geo_polyline";
        String GEO_TRACKING_LIST="geo_tracking_list";
        String NOTIFICATION_LIST = "notification_list";
        String UPDATE_TABLE_LIST = "update_table_list";
        String USERS_UPDATE_DATE="users_update_date";
        String LEAD_CONVERSION ="lead_conversion";
        String PAYMENT_COLLECTION_LIST = "payment_collection_list";
        String PAYMENT_COLLECTION_INSERT = "payment_collection_insert";
        String PAYMENT_COLLECTION_EDIT = "payment_collection_edit";
        String SALES_CALL_LAT_LON = "sales_call_lat_lon";
        String APP_CURRENT_VERSION="app_current_version";
        String GET_CUSTOMER_INFO = "getCustomerInfo";
        String MAP_CUSTOMER_USER = "mapCustomerUser";
    }

    public interface RelatedTo {
        String LEADS = "Leads";
        String CONTRACTS = "Contracts";
        String OPPORTUNITIES = "Opportunities";
        String CUSTOMERS = "Customers";
        String CONTACTS = "Contacts";
    }

    public interface Notification {
        String LEAD = "Lead";
        String LEAD_CONVERTED = "Lead Converted";
        String CONTACTS = "Contact";
        String CUSTOMERS ="Customer";
        String OPPORTUNITIES ="Opportunitie";
        String CONTRACT = "Contract";
        String SALES_CALLS ="SalesCalls";
        String SALES_ORDER ="SalesOrder";
        String QUOTATION = "Quotation";
    }

    public interface Status {
        String APPROVED = "Accept";
        String REJECTED = "Reject";
    }

    public interface FormTypes {
        int CREATE = 0;
        int UPDATE = 1;
    }

    public interface Roles {
        int ADMIN = 1; // 1 role admin
        int REGIONAL_MANAGER = 2;
        int MARKETING_OFFICER = 3;
    }


    public interface ProfileRoles {
        int SUPERADMIN = 1; // 1 role admin
        int ACCOUNT_MANAGER = 2;
        int SALES_OFFICER = 3;
        int SALES_EXECUTIVE = 4;
        int QUALITY_DEPARTMENT = 5;
        int MANUFACTURING = 6;
        int AREA_MANAGER = 8;
        int REGIONAL_MANAGER = 9;
        int NATIONAL_HEAD = 10;
        int COMERCIAL_DEPARTDEPART = 11;
    }

    public class PanelState {
        public static final String COLLAPSED = "COLLAPSED";
        public static final String EXPANDED = "EXPANDED";
    }

    public interface DateFormat {
        String COMMON_DATE_FORMAT = "yyyy-MM-dd";
        String COMMON_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    }
}


