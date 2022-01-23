package com.ncl.nclcustomerservice.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ncl.nclcustomerservice.commonutils.Constants;


/**
 * Created by User on 12/20/2017.
 */

public class CustomTextViewBold extends TextView {
    public CustomTextViewBold(Context context) {
        super(context);
    }

    public CustomTextViewBold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextViewBold(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomTextViewBold(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        Typeface externalFont=Typeface.createFromAsset(getContext().getAssets(), Constants.CUSTOM_FONT_BOLD);
        
        setTypeface(externalFont);
    }
}
