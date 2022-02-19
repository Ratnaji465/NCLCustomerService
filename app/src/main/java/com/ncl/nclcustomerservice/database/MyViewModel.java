package com.ncl.nclcustomerservice.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * Created by User on 8/29/2018.
 */

public class MyViewModel extends AndroidViewModel {
    private CommonRepository commonRepository;

    public MyViewModel(@NonNull Application application) {
        super(application);
        commonRepository = new CommonRepository(application);
    }
}
