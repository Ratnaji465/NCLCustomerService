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

public class CustomTextviewSemiBold extends TextView {
    public CustomTextviewSemiBold(Context context) {
        super(context);
    }

    public CustomTextviewSemiBold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextviewSemiBold(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomTextviewSemiBold(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        Typeface externalFont=Typeface.createFromAsset(getContext().getAssets(), Constants.CUSTOM_FONT_PATH_BOLD);

        setTypeface(externalFont);
    }
}
