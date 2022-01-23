package com.ncl.nclcustomerservice.typeconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ncl.nclcustomerservice.object.CustomerContactResponseVo;
import com.ncl.nclcustomerservice.object.ProjectHeadReqVo;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ProjectHeadAssociateContactTC {
    @TypeConverter
    public static List<ProjectHeadReqVo.AssociateContact> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<ProjectHeadReqVo.AssociateContact>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<ProjectHeadReqVo.AssociateContact> someObjects) {
        return new Gson().toJson(someObjects);
    }
}
