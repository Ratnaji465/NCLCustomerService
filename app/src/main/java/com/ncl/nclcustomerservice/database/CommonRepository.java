package com.ncl.nclcustomerservice.database;

import android.app.Application;

/**
 * Created by User on 8/29/2018.
 */

public class CommonRepository {
    private CommonDao commonDao;

    CommonRepository(Application application) {
        DatabaseHandler db = DatabaseHandler.getDatabase(application);
        commonDao = db.commonDao();
    }

    public void insertLoginDetails() {

    }
}
