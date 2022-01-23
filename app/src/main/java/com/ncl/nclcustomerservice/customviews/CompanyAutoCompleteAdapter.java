package com.ncl.nclcustomerservice.customviews;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.object.CustomerDropDown;

import java.util.ArrayList;
import java.util.List;

public class CompanyAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private List<CustomerDropDown> CustomersLists;
    Context context;

    private final Object mLock = new Object();
    // private ArrayList<String> fullList;
    private List<CustomerDropDown> mOriginalValues;
    private ArrayFilter mFilter;
    private OnItemSelectClickListener mSelectListener;
    private OnItemDeleteClickListener mDeleteListener;

    TextView itemName, itemDel;

    private LayoutInflater mInflater;


    public interface OnItemSelectClickListener {
        public void onItemSelectClicked(CustomerDropDown item);
    }

    public interface OnItemDeleteClickListener {
        public void onItemDeleteClicked();
    }

    /**
     * Set listener for clicks on the footer item
     */
    public void setOnItemSelectClickListener(OnItemSelectClickListener listener) {
        mSelectListener = listener;
    }

    /*public void setOnItemDeleteClickListener(OnItemDeleteClickListener listener) {
        mDeleteListener = listener;
    }*/

    public CompanyAutoCompleteAdapter(Context context, String[] objects) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // fullList = new ArrayList<String>( Arrays.asList(objects) );
        this.context = context;
    }

    public CompanyAutoCompleteAdapter(Context context, List<CustomerDropDown> CustomersLists) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.CustomersLists = CustomersLists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return CustomersLists.size();
    }

    @Override
    public CustomerDropDown getItem(int position) {
        return CustomersLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_adapter_row, parent, false);

        itemName = (TextView) rowView.findViewById(R.id.itemName);
        itemDel = (TextView) rowView.findViewById(R.id.itemDel);

        itemName.setText(getItem(position).customerName);

        //clear button click listener
        itemDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomersLists.remove(getItem(position));
                notifyDataSetChanged();
                Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
            }
        });
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectListener != null) {
                    mSelectListener.onItemSelectClicked(getItem(position));
                }
            }
        });
        return rowView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    //filter items which does not contain typed text
    private class ArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {

            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = CustomersLists;
                }
            }

            if (prefix == null || prefix.length() == 0) {
                final List<CustomerDropDown> list;
                synchronized (mLock) {
                    list = mOriginalValues;
                }
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                final List<CustomerDropDown> values;

                synchronized (mLock) {
                    values = mOriginalValues;
                }
                results.values = values;
                results.count = values.size();

                final int count = values.size();

                final List<CustomerDropDown> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    String item = values.get(i).customerName;
                    if (item.toLowerCase().contains(prefixString)) {
                        newValues.add(values.get(i));
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            CustomersLists = (List<CustomerDropDown>) results.values;

            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }

        }


    }

}