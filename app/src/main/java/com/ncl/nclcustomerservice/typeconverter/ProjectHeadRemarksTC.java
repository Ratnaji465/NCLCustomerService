package com.ncl.nclcustomerservice.typeconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ncl.nclcustomerservice.object.RemarksListVO;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ProjectHeadRemarksTC {
    @TypeConverter
    public static List<RemarksListVO> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<RemarksListVO>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<RemarksListVO> someObjects) {
        return new Gson().toJson(someObjects);
    }
}
