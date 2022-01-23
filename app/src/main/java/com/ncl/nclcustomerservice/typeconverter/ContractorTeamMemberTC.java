package com.ncl.nclcustomerservice.typeconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ncl.nclcustomerservice.object.ActionWorkDone;
import com.ncl.nclcustomerservice.object.CustomerContactResponseVo;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ContractorTeamMemberTC {
    @TypeConverter
    public static List<CustomerContactResponseVo.TeamMemberResVo> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<CustomerContactResponseVo.TeamMemberResVo>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<CustomerContactResponseVo.TeamMemberResVo> someObjects) {
        return new Gson().toJson(someObjects);
    }
}
