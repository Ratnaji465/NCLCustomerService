package com.ncl.nclcustomerservice.typeconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ncl.nclcustomerservice.object.CustomerProjectResVO;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class CusAssociateContactTC {
    @TypeConverter
    public static List<CustomerProjectResVO.AssociateContact> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<CustomerProjectResVO.AssociateContact>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<CustomerProjectResVO.AssociateContact> someObjects) {
        return new Gson().toJson(someObjects);
    }
}
