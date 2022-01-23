package com.ncl.nclcustomerservice.typeconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ncl.nclcustomerservice.object.ActionWorkDone;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class LeadActionTOTC {

    @TypeConverter
    public static List<ActionWorkDone> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<ActionWorkDone>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<ActionWorkDone> someObjects) {
        return new Gson().toJson(someObjects);
    }
}
