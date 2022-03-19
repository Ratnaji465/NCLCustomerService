package com.ncl.nclcustomerservice.typeconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ncl.nclcustomerservice.object.DailyReportsAddVO;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class DescriptionOfWorkTC {
    @TypeConverter
    public static List<DailyReportsAddVO.DescriptionOfWork> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<DailyReportsAddVO.DescriptionOfWork>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<DailyReportsAddVO.DescriptionOfWork> someObjects) {
        return new Gson().toJson(someObjects);
    }
}
