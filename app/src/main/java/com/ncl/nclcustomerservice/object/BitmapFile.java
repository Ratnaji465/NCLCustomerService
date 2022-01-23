package com.ncl.nclcustomerservice.object;

import android.graphics.Bitmap;

/**
 * Created by SupraSoft on 6/1/2018.
 */

public class BitmapFile {
    private String bitmapPath;
    private Bitmap bitmap;

    public String getBitmapPath() {
        return bitmapPath;
    }

    public void setBitmapPath(String bitmapPath) {
        this.bitmapPath = bitmapPath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
