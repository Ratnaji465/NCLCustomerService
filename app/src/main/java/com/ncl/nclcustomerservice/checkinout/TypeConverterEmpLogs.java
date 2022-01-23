package com.ncl.nclcustomerservice.checkinout;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by suprasoft on 9/13/2018.
 */

public class TypeConverterEmpLogs {
    @TypeConverter
    public static List<EmpActivityLogsPojo> stringToMeasurements(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<EmpActivityLogsPojo>>() {}.getType();
        List<EmpActivityLogsPojo> measurements = gson.fromJson(json, type);
        return measurements;
    }

    @TypeConverter
    public static String measurementsToString(List<EmpActivityLogsPojo> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<EmpActivityLogsPojo>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}
