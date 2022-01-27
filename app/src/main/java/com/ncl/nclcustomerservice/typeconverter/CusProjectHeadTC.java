package com.ncl.nclcustomerservice.typeconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ncl.nclcustomerservice.object.CustomerProjectResVO;
import com.ncl.nclcustomerservice.object.ProjectHeadReqVo;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class CusProjectHeadTC {
    @TypeConverter
    public static List<CustomerProjectResVO.ProjectHead> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<CustomerProjectResVO.ProjectHead>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<CustomerProjectResVO.ProjectHead> someObjects) {
        return new Gson().toJson(someObjects);
    }
}
