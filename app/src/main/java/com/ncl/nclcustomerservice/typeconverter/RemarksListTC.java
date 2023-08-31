package com.ncl.nclcustomerservice.typeconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ncl.nclcustomerservice.object.ComplaintsInsertReqVo;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class RemarksListTC {
    @TypeConverter
    public static List<ComplaintsInsertReqVo.RemarksList> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<ComplaintsInsertReqVo.RemarksList>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<ComplaintsInsertReqVo.RemarksList> someObjects) {
        return new Gson().toJson(someObjects);
    }
}
