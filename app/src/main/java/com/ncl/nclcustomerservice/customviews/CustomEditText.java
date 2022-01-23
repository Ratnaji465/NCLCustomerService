package com.ncl.nclcustomerservice.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.ncl.nclcustomerservice.commonutils.Constants;


/**
 * Created by Venkateswarlu SKP on 30-08-2016.
 */
public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText {
    public CustomEditText(Context context) {
        super(context);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }





    private void init()
    {

        Typeface externalFont=Typeface.createFromAsset(getContext().getAssets(), Constants.CUSTOM_FONT_PATH);
        setTypeface(externalFont);

    }


}
