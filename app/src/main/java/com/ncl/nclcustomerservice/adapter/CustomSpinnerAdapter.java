package com.ncl.nclcustomerservice.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.object.SpinnerModel;

import java.util.List;

/**
 * Created by sowmy on 10/1/2018.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Context activity;
    private List<SpinnerModel> data;
    public Resources res;
    SpinnerModel tempValues = null;
    LayoutInflater inflater;

    /*************  CustomSpinnerAdapter Constructor *****************/
    public CustomSpinnerAdapter(
            Context activity,
            int textViewResourceId,
            List data
    ) {
        super(activity, textViewResourceId, data);

        /********** Take passed values **********/
        this.activity = activity;
        this.data = data;

        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.spinner_rows, parent, false);

        /***** Get each Model object from Arraylist ********/
        tempValues = null;
        tempValues = (SpinnerModel) data.get(position);

        TextView label = (TextView) row.findViewById(R.id.spinnertitle);

        // Set values for spinner each row
        label.setText(tempValues.getTitle());
        label.setTag(tempValues.getId());


        return row;
    }
}
