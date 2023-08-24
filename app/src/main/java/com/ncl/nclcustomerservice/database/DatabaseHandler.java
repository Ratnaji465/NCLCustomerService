package com.ncl.nclcustomerservice.database;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ncl.nclcustomerservice.activity.LoginActivity;
import com.ncl.nclcustomerservice.checkinout.EmpActivityLogsPojo;
import com.ncl.nclcustomerservice.checkinout.EmpActivityPojo;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.object.ComplaintRegisterMasterVo;
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
import com.ncl.nclcustomerservice.object.Lead;
import com.ncl.nclcustomerservice.object.LeadInsertReqVo;
import com.ncl.nclcustomerservice.object.NatureOfComplaintList;
import com.ncl.nclcustomerservice.object.OpportunitiesList;
import com.ncl.nclcustomerservice.object.PaymentCollectionList;
import com.ncl.nclcustomerservice.object.PriceList;
import com.ncl.nclcustomerservice.object.ProjectHeadReqVo;
import com.ncl.nclcustomerservice.object.ProjectTypeList;
import com.ncl.nclcustomerservice.object.QuotationList;
import com.ncl.nclcustomerservice.object.SalesCallList;
import com.ncl.nclcustomerservice.object.SalesOrderList;
import com.ncl.nclcustomerservice.object.TadaList;
import com.ncl.nclcustomerservice.typeconverter.ContractorTeamMemberTC;
import com.ncl.nclcustomerservice.typeconverter.CusAssociateContactTC;
import com.ncl.nclcustomerservice.typeconverter.CusContractorTC;
import com.ncl.nclcustomerservice.typeconverter.CusProjectHeadTC;
import com.ncl.nclcustomerservice.typeconverter.CusTeamMemberTC;
import com.ncl.nclcustomerservice.typeconverter.CustomerprojectClientprojectDetailsTC;
import com.ncl.nclcustomerservice.typeconverter.DescriptionOfWorkTC;
import com.ncl.nclcustomerservice.typeconverter.LeadActionTOTC;
import com.ncl.nclcustomerservice.typeconverter.LeadAssociatedTC;
import com.ncl.nclcustomerservice.typeconverter.ProjectHeadAssociateContactTC;


/**
 * Created by User on 8/23/2018.
 */
@Database(entities = {LoginDb.class, Customer.class, PriceList.class, EmpActivityPojo.class,
        EmpActivityLogsPojo.class, ComplaintsTable.class, ContactList.class,
        Lead.class, CustomerList.class, SalesCallList.class, ContractList.class,
        OpportunitiesList.class, SalesOrderList.class, DailyReportsAddVO.class,
        TadaList.class, Geo_Tracking_POJO.class, QuotationList.class,
        PaymentCollectionList.class, LeadInsertReqVo.class, CustomerContactResponseVo.ContactContractorList.class,
        ProjectHeadReqVo.class, CustomerProjectResVO.class, ProjectTypeList.class,
        NatureOfComplaintList.class, DivisionMasterList.class,
        FabUnitList.class}, version = 2, exportSchema = false)

@TypeConverters({LeadAssociatedTC.class, LeadActionTOTC.class, ContractorTeamMemberTC.class,
        ProjectHeadAssociateContactTC.class, CusAssociateContactTC.class, CusContractorTC.class,
        CusProjectHeadTC.class, CusTeamMemberTC.class, CustomerprojectClientprojectDetailsTC.class,
        DescriptionOfWorkTC.class})

public abstract class DatabaseHandler extends RoomDatabase {
    private static DatabaseHandler INSTANCE = null;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
    private static final String DATABASE_NAME = "ncl-database";
    public Context context;

    public static DatabaseHandler getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseHandler.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DatabaseHandler.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract CommonDao commonDao();

    public static void logout(SupportSQLiteDatabase databaseHandler, Context context) {
        Common.clearPreferenceData(context);
        Intent intent = new Intent(context, LoginActivity.class);
        Common.Log.i("Logged out");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
