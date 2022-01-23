package com.ncl.nclcustomerservice.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import android.content.Context;
import android.content.Intent;

import com.ncl.nclcustomerservice.activity.LoginActivity;
import com.ncl.nclcustomerservice.checkinout.EmpActivityLogsPojo;
import com.ncl.nclcustomerservice.checkinout.EmpActivityPojo;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.object.AssociateContact;
import com.ncl.nclcustomerservice.object.ComplaintsTable;
import com.ncl.nclcustomerservice.object.Contact;
import com.ncl.nclcustomerservice.object.ContactList;
import com.ncl.nclcustomerservice.object.ContractLineItem;
import com.ncl.nclcustomerservice.object.ContractList;
import com.ncl.nclcustomerservice.object.Customer;
import com.ncl.nclcustomerservice.object.CustomerContactResponseVo;
import com.ncl.nclcustomerservice.object.CustomerList;
import com.ncl.nclcustomerservice.object.CustomerUserList;
import com.ncl.nclcustomerservice.object.Geo_Tracking_POJO;
import com.ncl.nclcustomerservice.object.Lead;
import com.ncl.nclcustomerservice.object.LeadInsertReqVo;
import com.ncl.nclcustomerservice.object.OpportunitiesList;
import com.ncl.nclcustomerservice.object.OpportunityBrandsLineItem;
import com.ncl.nclcustomerservice.object.OpportunityCompetitionLineItem;
import com.ncl.nclcustomerservice.object.OpportunityProductLineItem;
import com.ncl.nclcustomerservice.object.PaymentCollectionList;
import com.ncl.nclcustomerservice.object.PriceList;
import com.ncl.nclcustomerservice.object.ProjectHeadReqVo;
import com.ncl.nclcustomerservice.object.QuotationList;
import com.ncl.nclcustomerservice.object.QuotationProductList;
import com.ncl.nclcustomerservice.object.SalesOrderLineItem;
import com.ncl.nclcustomerservice.object.SalesCallList;
import com.ncl.nclcustomerservice.object.SalesOrderList;
import com.ncl.nclcustomerservice.object.SalesPersonLineItem;
import com.ncl.nclcustomerservice.object.TadaList;
import com.ncl.nclcustomerservice.typeconverter.ContractorTeamMemberTC;
import com.ncl.nclcustomerservice.typeconverter.LeadActionTOTC;
import com.ncl.nclcustomerservice.typeconverter.LeadAssociatedTC;
import com.ncl.nclcustomerservice.typeconverter.ProjectHeadAssociateContactTC;


/**
 * Created by User on 8/23/2018.
 */
@Database(entities = {LoginDb.class, Customer.class, PriceList.class, EmpActivityPojo.class, EmpActivityLogsPojo.class, ComplaintsTable.class, Contact.class, ContactList.class, Lead.class, CustomerList.class, CustomerUserList.class, SalesCallList.class, ContractList.class, OpportunitiesList.class, SalesOrderList.class, TadaList.class, ContractLineItem.class, Geo_Tracking_POJO.class, OpportunityProductLineItem.class, OpportunityCompetitionLineItem.class, OpportunityBrandsLineItem.class, SalesOrderLineItem.class, SalesPersonLineItem.class, QuotationList.class, QuotationProductList.class, AssociateContact.class, PaymentCollectionList.class, LeadInsertReqVo.class, CustomerContactResponseVo.ContactContractorList.class, ProjectHeadReqVo.class}, version = 18, exportSchema = false)
@TypeConverters({ LeadAssociatedTC.class, LeadActionTOTC.class, ContractorTeamMemberTC.class, ProjectHeadAssociateContactTC.class})
public abstract class DatabaseHandler extends RoomDatabase {
    private static DatabaseHandler INSTANCE = null;
    private static Context context1;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
    private static final String DATABASE_NAME = "ncl-database";
    public Context context;
    public static final String TABLE_GEO_TRACKING = "geo_tracking";
    // table GEO_TRACKING column names
    public static final String KEY_TABLE_GEO_TRACKING_ID = "t_id";
    public static final String KEY_TABLE_GEO_TRACKING_MASTER_ID = "tracking_id";
    public static final String KEY_TABLE_GEO_TRACKING_VISIT_TYPE = "visit_type";
    public static final String KEY_TABLE_GEO_TRACKING_USER_ID = "user_id";
    public static final String KEY_TABLE_GEO_TRACKING_CHECK_IN_LAT_LONG = "check_in_lat_lon";
    public static final String KEY_TABLE_GEO_TRACKING_CHECK_OUT_LAT_LONG = "check_out_lat_lon";
    public static final String KEY_TABLE_GEO_TRACKING_ROUTE_PATH_LAT_LONG = "route_path_lat_lon";
    public static final String KEY_TABLE_GEO_TRACKING_DISTANCE = "distance";
    public static final String KEY_TABLE_GEO_TRACKING_VISIT_DATE = "visit_date";
    public static final String KEY_TABLE_GEO_TRACKING_CHECK_IN_TIME = "check_in_time";
    public static final String KEY_TABLE_GEO_TRACKING_CHECK_OUT_TIME = "check_out_time";
    public static final String KEY_TABLE_GEO_TRACKING_STATUS = "status";
    public static final String KEY_TABLE_GEO_TRACKING_FFMID = "ffmid";
    public static final String KEY_TABLE_GEO_TRACKING_CREATED_DATETIME = "created_datetime";
    public static final String KEY_TABLE_GEO_TRACKING_UPDATED_DATETIME = "updated_datetime";
    public static final String KEY_TABLE_GEO_TRACKING_UPDATED_STATUS = "updated_status"; //1-> Synced, 0-> Need to Sync
    public static final String KEY_TABLE_GEO_TRACKING_POLYLINE = "polyline";
    public static final String KEY_TABLE_GEO_TRACKING_VERSION = "version";

    public static DatabaseHandler getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.inMemoryDatabaseBuilder(context,
                    DatabaseHandler.class)
                    .build();
        }
        return INSTANCE;
    }

    public static DatabaseHandler getDatabase(Context context) {
        context1=context;

        if (INSTANCE == null) {
            synchronized (DatabaseHandler.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DatabaseHandler.class, DATABASE_NAME)
                           // .addMigrations(MIGRATION_1_2)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //    static final Migration MIGRATION_ALL = new Migration(1, 2) {
//        @Override
//        public void migrate(@Nonnull SupportSQLiteDatabase database) {
////            database.execSQL("ALTER TABLE OrderIndent ADD COLUMN keyValue INTEGER NOT NULL DEFAULT 0");
////            database.execSQL("ALTER TABLE OrderIndent ADD COLUMN keyPair INTEGER NOT NULL DEFAULT 0");
////            database.execSQL("ALTER TABLE OrderIndent ADD COLUMN key TEXT");
//            database.execSQL("ALTER TABLE ProductList ADD COLUMN key TEXT");
////            database.execSQL("ALTER TABLE ProductDescription ADD COLUMN tetsValue TEXT");
////            database.execSQL("ALTER TABLE ProductDescription ADD COLUMN tetsValue2 TEXT");
////            database.execSQL("ALTER TABLE ProductDescription ADD COLUMN tetsValue3 TEXT");
//            //  logout(INSTANCE, MyApplication.getInstance());
//        }
//    };
    //
//    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE OrderIndent ADD COLUMN key TEXT");
//        }
//    };
//
    static final Migration MIGRATION_1_2 = new Migration(7, 8) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // database.execSQL("ALTER TABLE ProductList ADD COLUMN key TEXT");
            if (context1!=null)
            logout(database,context1);
        }
    };
//
    static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE ProductDescription ADD COLUMN tetsValue TEXT");
        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE ProductDescription ADD COLUMN tetsValue2 TEXT");
        }
    };
//
//    static final Migration MIGRATION_7_8 = new Migration(7, 8) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE ProductDescription ADD COLUMN tetsValue3 TEXT");
//        }
//    };


    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract CommonDao commonDao();

    public static void logout(SupportSQLiteDatabase databaseHandler, Context context) {
        Common.clearPreferenceData(context);
//        databaseHandler.clearAllTables();
//        context.deleteDatabase(DATABASE_NAME);
//        databaseHandler.destroyInstance();
        Intent intent = new Intent(context, LoginActivity.class);
        Common.Log.i("Logged out");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
