package com.ncl.nclcustomerservice.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ncl.nclcustomerservice.commonutils.Common;

/**
 * Created by suprasoft on 9/21/2018.
 */

public class BaseFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public boolean isRM(){
        if (Common.getRoleIdFromSP(getContext())==2)
            return true;

        return false;
    }

    public boolean isMO(){
        if (Common.getRoleIdFromSP(getContext())==3)
            return true;

        return false;
    }
    public void setUserId(String userId){
    }
}
