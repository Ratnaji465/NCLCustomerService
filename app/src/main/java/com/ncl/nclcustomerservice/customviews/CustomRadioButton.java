package com.ncl.nclcustomerservice.customviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.ncl.nclcustomerservice.commonutils.Constants;


/**
 * Created by User on 8/9/2018.
 */

@SuppressLint("AppCompatCustomView")
public class CustomRadioButton extends RadioButton {
    public CustomRadioButton(Context context) {
        super(context);
        init();
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomRadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        Typeface externalFont = Typeface.createFromAsset(getContext().getAssets(), Constants.CUSTOM_FONT_PATH);
        setTypeface(externalFont);
    }

}

