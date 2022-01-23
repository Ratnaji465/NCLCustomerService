package com.ncl.nclcustomerservice.commonutils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.Settings;

import androidx.annotation.RequiresPermission;

import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.RequestBody;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.adapter.UserAdapter;
import com.ncl.nclcustomerservice.application.MyApplication;
import com.ncl.nclcustomerservice.checkinout.EmpActivityLogsPojo;
import com.ncl.nclcustomerservice.checkinout.EmpActivityPojo;
import com.ncl.nclcustomerservice.customviews.CustomButton;
import com.ncl.nclcustomerservice.customviews.CustomTextView;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.fragments.BaseFragment;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.CompaintNameKey;
import com.ncl.nclcustomerservice.object.DivisionList;
import com.ncl.nclcustomerservice.object.Geo_Tracking_POJO;
import com.ncl.nclcustomerservice.object.LeftNav;
import com.ncl.nclcustomerservice.object.LoginResVo;
import com.ncl.nclcustomerservice.object.UsersTeam;


import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import okio.Buffer;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;


public class Common {
    static final String LOG = Common.class.getSimpleName();
    public static final String INTERNET_UNABLEABLE = "Not connected to the internet. Please check your connection and try again.";
    public static final int TOAST_TIME = 2000;
    public static final int[] NAVITEMICONS = new int[]{R.drawable.dashboard, R.drawable.leadsicon, R.drawable.contacts_icon, R.drawable.customers, R.drawable.opportunities_icon, R.drawable.contracts, R.drawable.sales_orders_icon, R.drawable.sales_orders_icon, R.drawable.sales_calls, R.drawable.complaints, R.drawable.expenses, R.drawable.payment_icon, R.drawable.notifications_icon, R.drawable.routemap, R.drawable.logout};
    //  public static final int[] NAVITEMICONS = new int[]{R.drawable.dashboard, R.drawable.leadsicon, R.drawable.contacts_icon,R.drawable.logout};
    private static Activity previousActivity;
    private static Date dates = null;

    public static String getPackageName(Context context) {

        final PackageManager pm = context.getPackageManager();
//get a list of installed apps.

        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        StringBuilder commaSepValueBuilder = new StringBuilder();

        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.indexOf("gps") == -1) {
                continue;
            }

            android.util.Log.d("Installed package :", packageInfo.packageName);
            // Log.d( "Source dir : " , packageInfo.sourceDir);
            // Log.d( "Launch Activity :" , String.valueOf(pm.getLaunchIntentForPackage(packageInfo.packageName)));
            if (commaSepValueBuilder.toString().length() > 0) {
                commaSepValueBuilder.append(",");
            }
            commaSepValueBuilder.append(packageInfo.packageName);

        }
        return commaSepValueBuilder.toString();
    }

    public static String getFormatedDateTime(String dateStr, String strReadFormat, String strWriteFormat) {

        String formattedDate = dateStr;

        SimpleDateFormat readFormat_24 = new SimpleDateFormat(strReadFormat);
        SimpleDateFormat writeFormat_12 = new SimpleDateFormat(strWriteFormat);

        Date date = null;

        try {
            date = readFormat_24.parse(dateStr);
        } catch (ParseException e) {
        }

        if (date != null) {
            formattedDate = writeFormat_12.format(date);
        }

        return formattedDate;
    }

    public static boolean isCheckedIn(DatabaseHandler dbHandler) {
        List<EmpActivityPojo> details = dbHandler.commonDao().getCheckinDetails(getCurrentDate());
        if (details == null || details.size() == 0 || Common.isNull(details.get(0).checkOutTime) != null)
            return false;

        return true;
    }

    public static boolean isCheckedout(DatabaseHandler dbHandler) {
        List<EmpActivityPojo> details = dbHandler.commonDao().getCheckinDetails(getCurrentDate());
        if (details != null && details.size() > 0 && Common.isNull(details.get(0).checkOutTime) != null)
            return true;

        return false;
    }

    public static boolean isCustomerCheckedIn(DatabaseHandler dbHandler, String employeeActivityId) {
        List<EmpActivityLogsPojo> details = dbHandler.commonDao().getCustomerCheckinDetails(employeeActivityId);
        if (details != null) {
            for (int i = 0; i < details.size(); i++) {
                if (Common.isNull(details.get(i).checkInTime) != null && Common.isNull(details.get(i).checkOutTime) == null)
                    return true;
            }
        }

        return false;
    }


    public static void customToast(final Context context, final String msg, final int millisec) {
        Activity activity = (Activity) context;

        if (activity == null) {
            return;
        }

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(context, msg, millisec).show();
            }
        });

    }

    public static String getCurrentDateYYYYMMDD() {

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat(Constants.DateFormat.COMMON_DATE_FORMAT);

        return df.format(c.getTime());
    }


    public static void customToast(final Context context, final String msg) {
        Activity activity = (Activity) context;


        if (activity == null) {
            return;
        }

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }


    public static void showConfirmationDialog(Activity activity, String message) {

        final Dialog confirmationDialog = new Dialog(activity);

        confirmationDialog.setContentView(R.layout.confirmation_dialog);

        confirmationDialog.setCancelable(false);

        CustomTextView ctvConfirmationDialogMessage = (CustomTextView) confirmationDialog.findViewById(R.id.title);

        ctvConfirmationDialogMessage.setText(message);

        CustomButton customButton = (CustomButton) confirmationDialog.findViewById(R.id.ok);

        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationDialog.dismiss();
            }
        });

        confirmationDialog.show();
    }


    public static boolean haveInternet(Context ctx) {
        try {
            NetworkInfo info = ((ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE))
                    .getActiveNetworkInfo();

            if (info == null || !info.isConnected()) {
                return false;
            }
        } catch (Exception e) {
            android.util.Log.d("err", e.toString());
        }
        return true;
    }

    public static void getAlertDialog(final Activity context, String title,
                                      String Message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(Message)
                .setCancelable(false)
                .setPositiveButton("Invite",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                context.finish();
                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public static void showAlertDialog(final Activity context, String title,
                                       String Message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(Message)
                .setCancelable(false)
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        intent.putExtra("EXIT", true);
                        if (context.getIntent().getBooleanExtra("EXIT", false)) {
                            context.finish();
                        }
                        android.os.Process.killProcess(android.os.Process.myPid());

                    }
                })
                .setPositiveButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public static void getAlertDialogWhyChooseUs(final Activity context, String title,
                                                 String Message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(Message)
                .setCancelable(false)

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean mobileValidator(String mobile) {
        Pattern pattern;
        Matcher matcher;
        final String MOBILE_PATTERN = "^[0-9]{10}+(\\.)";
        pattern = Pattern.compile(MOBILE_PATTERN);
        matcher = pattern.matcher(mobile);
        return matcher.matches();
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
//            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static boolean checkSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
//            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        }
        return false;
    }


    public static void hideSoftKeyboardFromDialog(Dialog dialog, Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (dialog.getCurrentFocus() != null) {
//            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            inputMethodManager.hideSoftInputFromWindow(dialog.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static String retrieveContactRecord(Context activity, String phoneNo) {
        String contactId;
        String contactName = "";
        try {
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNo));
            String[] projection = new String[]{ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup.PHOTO_URI};
            String selection = null;
            String[] selectionArgs = null;
            String sortOrder = ContactsContract.PhoneLookup.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
            ContentResolver cr = activity.getContentResolver();
            if (cr != null) {
                Cursor resultCur = cr.query(uri, projection, selection, selectionArgs, sortOrder);
                if (resultCur != null) {
                    while (resultCur.moveToNext()) {
                        contactId = resultCur.getString(resultCur.getColumnIndex(ContactsContract.PhoneLookup._ID));
                        contactName = resultCur.getString(resultCur.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME));
                        String photoUri = resultCur.getString(resultCur.getColumnIndexOrThrow(ContactsContract.PhoneLookup.PHOTO_URI));
//                        Log.e("Info","Contact Id : "+contactId);
//                        Log.e("Info","Contact Display Name : "+contactName);
//                        Log.d("SUBBU", photoUri);
                        byte[] data = resultCur.getBlob(resultCur.getColumnIndex(ContactsContract.PhoneLookup.PHOTO_URI));
                        if (data != null) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//                            Log.d("SUBBU", bitmap + "");
                        }
                        return contactName;

                    }
                    //resultCur.close();
                }
            }
        } catch (Exception sfg) {
            // Log.e("Error", "Error in loadContactRecord : "+sfg.toString());
        }
        return contactName;
    }//fn retrieveContactRecord

    public static long getContactIDFromNumber(String contactNumber, Context context) {
        String UriContactNumber = Uri.encode(contactNumber);
        long phoneContactID = new Random().nextInt();
        Cursor contactLookupCursor = context.getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, UriContactNumber),
                new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID}, null, null, null);
        while (contactLookupCursor.moveToNext()) {
            phoneContactID = contactLookupCursor.getLong(contactLookupCursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
        }
        contactLookupCursor.close();

        return phoneContactID;
    }

    /*date format YYYY-MM-DD to dd-mm-yyyy*/
    public static String dateformate(String dt) {
        String d = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = formatter.parse(dt);
            d = formatter1.format(date);


        } catch (Exception e) {
            // TODO: handle exception
        }
        return d;
    }

    /*date format  dd-mm-yyyy to YYYY-MM-DD */
    public static String dateformateDDMMYYYY(String dt) {
        String d = "";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(dt);
            d = formatter1.format(date);


        } catch (Exception e) {
            // TODO: handle exception
        }
        return d;
    }

    /*date format  dd-mm-yyyy to YYYY-MM-DD */
    public static String dateformateByTmeZone(String dt) {
        String d = "";
        TimeZone TZ1 = TimeZone.getDefault();
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss a");
        try {
            Date date = formatter1.parse(dt);
            formatter2.setTimeZone(TimeZone.getTimeZone(TZ1.getDisplayName()));
            d = formatter2.format(date);

//            d=formatter1.format(date);


        } catch (Exception e) {
            // TODO: handle exception
        }
        return d;
    }

    public static void showGPSDisabledAlertToUser(final Activity context, String title) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                context.startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static String toTitleCase(String input) {
        input = input.toLowerCase();
        char c = input.charAt(0);
        String s = new String("" + c);
        String f = s.toUpperCase();
        return f + input.substring(1);
    }

    public static String getAddressString(Context context, double LATITUDE, double LONGITUDE) {

        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);


            if (addresses != null) {
                Address address = addresses.get(0);

                Log.i("address.toString() : " + address.toString());

                StringBuilder sb = new StringBuilder("");
                String Feature = address.getFeatureName();
                String ThroughFrare = address.getSubThoroughfare();
                String Sub_Admin = address.getSubAdminArea();
                String Locality = address.getLocality();
                String Admin = address.getAdminArea();
                String Country = address.getCountryName();

                /*if (Feature != null) {
                    sb.append(Feature);
                }
                if (ThroughFrare != null) {
                    sb.append(","+ThroughFrare);
                }
                if (Sub_Admin != null) {
                    sb.append(","+Sub_Admin);
                }*/
                if (Locality != null) {
                    Log.i("Locality : " + Locality);
                    sb.append(Locality);
                }
                if (Admin != null) {
                    Log.i("Admin : " + Admin);

                    sb.append(", " + Admin);
                }
                if (Country != null) {

                    Log.i("Country : " + Country);

                    sb.append(", " + Country);
                }
                strAdd = sb.toString();


                android.util.Log.w("My Current", "" + sb.toString());
            } else {
                android.util.Log.w("My Current", "No Address returned!");
            }


        } catch (Exception e) {
            e.printStackTrace();
            android.util.Log.w("My Current", "Canont get Address!");
        }
        return strAdd;
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static String timeformatter(String _12hours) {
        String time = "";
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat formatter1 = new SimpleDateFormat("hh:mm aa");
//        String time = null;
        try {
            Date date = formatter.parse(_12hours);
            time = formatter1.format(date);


        } catch (Exception e) {
            // TODO: handle exception
        }
        return time;
    }


    public static Object stringToObject(String str) {
        try {
            return new ObjectInputStream(new Base64InputStream(
                    new ByteArrayInputStream(str.getBytes()), Base64.NO_PADDING
                    | Base64.NO_WRAP)).readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //eg String=17.12354,86.35466;  to double[] a={17.12354,86.35466};

    public static List<Double> stringToDubleArry(String latlngString) {
        List<Double> latlng = new ArrayList<>();

        String[] sourceArray = latlngString.split(",");

        for (int i = 0; i < sourceArray.length; i++) {
            String lat = sourceArray[i];
            latlng.add(Double.parseDouble(lat));
        }

        return latlng;
    }

    public static String[] spliteString(String stringForSplit) {
        String[] array = stringForSplit.split(",");

        return array;
    }

    public static String constructLatLng(Location latlng) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.valueOf(latlng.getLatitude()));
        stringBuilder.append(',');
        stringBuilder.append(String.valueOf(latlng.getLongitude()));
        String latlngString = stringBuilder.toString();

        return latlngString;
    }


    public static boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    public static Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius) {
        Bitmap finalBitmap;
        if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
            finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius,
                    false);
        else
            finalBitmap = bitmap;
        Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),
                finalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, finalBitmap.getWidth(),
                finalBitmap.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);

        paint.setColor(Color.parseColor("#ff6c2c"));
        canvas.drawCircle(finalBitmap.getWidth() / 2 + 0.7f,
                finalBitmap.getHeight() / 2 + 0.7f,
                finalBitmap.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(finalBitmap, rect, rect, paint);

        return output;
    }


    public static JSONObject getDataJsonObject(Object o) {
//        Object data = ((ApiResponseController) o).getData();

        ApiResponseController apiResponseController = getSpecificDataObject(o, ApiResponseController.class);

        /*
        Object data = ((ApiResponseController) o).data;
        String jsonString = new Gson().toJson(data);
        */

        String jsonString = new Gson().toJson(apiResponseController.result);

        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }


    public static String getJsonStringFromHashMap(HashMap<String, String> hashMap) {
        return new Gson().toJson(hashMap);
    }

    public static SharedPreferences getDefaultSP(Context context) {

        return PreferenceManager.getDefaultSharedPreferences(context);

    }

    public static String getTrackingIdFromSP(Context context) {

        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.TRACKING_ID, "");

    }

    public static void setTrackingIdFromSP(Context context, String tacking_id) {

        getDefaultSP(context).edit().putString(Constants.SharedPreferencesKeys.TRACKING_ID, tacking_id).commit();

    }

    public static int getUserIdFromSP(Context context) {

        return getDefaultSP(context).getInt(Constants.SharedPreferencesKeys.USERID, 0);

    }

    public static String getUserNameFromSP(Context context) {

        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.USERNAME, null);

    }

    public static String getEmailFromSP(Context context) {

        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.EMAIL, null);

    }

    public static String getRolenameFromSP(Context context) {

        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.ROLENAME, null);

    }

    public static String getMobileFromSP(Context context) {

        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.MOBILE, null);

    }

    public static String getFirstNameFromSP(Context context) {

        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.FIRSTNAME, null);

    }

    public static String getLastNameFromSP(Context context) {

        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.LASTNAME, null);

    }

    public static String getImageFromSP(Context context) {

        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.IMAGE, null);

    }

    public static String getDivisionIdFromSP(Context context) {

        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.PRICE_LIST_ID, null);

    }

    public static String getDivisionNameFromSP(Context context) {

        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.PRICE_LIST_NAME, null);

    }

    public static String getUserRoleFromSP(Context context) {

        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.USER_ROLE, "");

    }


    public static int getLoginIdFromSP(Context context) {

        return getDefaultSP(context).getInt(Constants.SharedPreferencesKeys.LOGINID, 0);

    }

    public static String getLoginTypeFromSP(Context context) {
        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.LOGINTYPE, null);
    }

    public static String getProfileId(Context context) {
        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.PROFILE_ID, null);
    }

    public static String getProfileName(Context context) {
        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.PROFILE_NAME, null);
    }

    public static int getDepartmentIdIntoSP(Context context) {

        return getDefaultSP(context).getInt(Constants.SharedPreferencesKeys.DEPARTMENTID, 0);

    }

    public static String getDepartmentFromSP(Context context) {

        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.DEPARTMENTNAME, null);

    }

    public static String getCompanyNameFromSP(Context context) {

        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.COMPANYNAME, null);

    }


    public static int getCompanyIdFromSP(Context context) {

        return getDefaultSP(context).getInt(Constants.SharedPreferencesKeys.COMPANYID, 0);

    }


    public static String getUserFactoryIdFromSP(Context context) {

        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.FACTORY_ID, "");

    }

    public static String getDeviceResolution(Context context) {
        int density = context.getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_MEDIUM:
                return "MDPI";
            case DisplayMetrics.DENSITY_HIGH:
                return "HDPI";
            case DisplayMetrics.DENSITY_LOW:
                return "LDPI";
            case DisplayMetrics.DENSITY_XHIGH:
                return "XHDPI";
            case DisplayMetrics.DENSITY_TV:
                return "TV";
            case DisplayMetrics.DENSITY_XXHIGH:
                return "XXHDPI";
            case DisplayMetrics.DENSITY_XXXHIGH:
                return "XXXHDPI";
            default:
                return "Unknown";
        }
    }

    public static void showContentView(Activity activity, boolean showStatus) {

        int visibleStatus = showStatus ? View.VISIBLE : View.GONE;

        activity.findViewById(android.R.id.content).setVisibility(visibleStatus);


    }

    public static void dismissProgressDialog(ProgressDialog progressDialog) {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }

    public static void finishActivity(Activity activityInstance) {


        if (activityInstance != null) {
            activityInstance.finish();
        }

    }

    public static ProgressDialog showProgressDialog(Context context) {

        ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);

        progressDialog.setMessage("Please Wait.....");


        if (!((Activity) context).isFinishing()) {
            try {
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return progressDialog;


    }


    public static void setupUI(View view, Activity context) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (!(v instanceof EditText))
                        Common.hideSoftKeyboard(context);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, context);
            }
        }
    }

    public static void disPlayExpection(Exception e, ProgressDialog progressDialog) {
        try {
            e.printStackTrace();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.cancel();
            }
            Toast.makeText(MyApplication.getInstance(), e.getMessage(), Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void refreshActivity(Activity activity) {

        Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);

    }

    public static Gson getCustomGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    public static <T> T getSpecificDataObject(Object object, Class<T> classOfT) {

        String jsonString = new Gson().toJson(object);

        return new Gson().fromJson(jsonString, classOfT);

    }

    public static void showScreenDensity() {

    }

//    public static String getDeviceId(Context context) {
//        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//    }

    public static String getStringResourceText(int resourceId) {
        return MyApplication.getInstance().getResources().getString(resourceId);
    }

    public static float getDimensionResourceValue(int resourceId) {
        return MyApplication.getInstance().getResources().getDimension(resourceId);
    }

    public static String getOnlyPhoneNumber(String countryCode, String phoneNumberWithCountryCode) {
        return countryCode == null ? phoneNumberWithCountryCode : phoneNumberWithCountryCode.replaceFirst(countryCode.replace("+", ""), "");
    }

    public static void saveUserIdIntoSP(int userid) {

        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putInt(Constants.SharedPreferencesKeys.USERID, userid).commit();

    }


    public static void saveLoginidIntoSP(int loginid) {

        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putInt(Constants.SharedPreferencesKeys.LOGINID, loginid).commit();

    }

    public static void saveFirstNameIntoSP(String firstName) {
        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.FIRSTNAME, firstName).commit();


    }


    public static void saveLastNameIntoSP(String lastName) {
        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.LASTNAME, lastName).commit();

    }

    public static void saveObject(Context context, LoginResVo obj) {
        getDefaultSP(context).edit().putString("leftnav", new Gson().toJson(obj)).commit();
    }

    public static String getObject(Context context) {
        return getDefaultSP(context).getString("leftnav", null);
    }

    public static void saveTeamObject(Context context, LoginResVo obj) {
        getDefaultSP(context).edit().putString("users_team", new Gson().toJson(obj)).commit();
    }

    public static String getTeamObject(Context context) {
        return getDefaultSP(context).getString("users_team", null);
    }

    public static void saveImageIntoSP(String image) {
        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.IMAGE, image).commit();

    }

    public static void savePriceListIdIntoSP(String divisionId) {
        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.PRICE_LIST_ID, divisionId).commit();

    }

    public static void savePriceAreaNameIntoSP(String divisionName) {
        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.PRICE_LIST_NAME, divisionName).commit();

    }

    public static void saveProfileIdIntoSP(String profileId) {
        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.PROFILE_ID, profileId).commit();

    }

    public static void saveProfileNameIntoSP(String profileId) {
        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.PROFILE_NAME, profileId).commit();

    }

    public static void saveUserNameIntoSP(String userName) {

        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.USERNAME, userName).commit();

    }

    public static void saveRoleIdIntoSP(int roleid) {

        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putInt(Constants.SharedPreferencesKeys.ROLE_ID, roleid).commit();

    }

    public static void saveEmailIntoSP(String email) {

        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.EMAIL, email).commit();

    }

    public static void saveRolenameSP(String rolename) {

        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.ROLENAME, rolename).commit();

    }

    public static void saveLoginStatusIntoSP(boolean status) {

        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putBoolean(Constants.SharedPreferencesKeys.LOGIN_STATUS, status).commit();

    }

    public static void saveUserFactoryIdIntoSP(String factotyId) {
        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.FACTORY_ID, factotyId).commit();
    }


    public static void saveUserRoleIntoSP(String userRole) {

        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.USER_ROLE, userRole).commit();

    }

    public static void saveArrayList(String teamuserId) {
//        Gson gson = new Gson();
//        String json = gson.toJson(teamId);
        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.LIST, teamuserId).commit();

    }

    public static String getTeamUserIdFromSP(Context context) {

        return getDefaultSP(context).getString(Constants.SharedPreferencesKeys.LIST, null);

    }

    public static void saveUserTeam(List<UsersTeam> usersTeams) {
        Gson gson = new Gson();
        String json = gson.toJson(usersTeams);
        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.USER, json).commit();

    }

    public static void saveDivisions(List<DivisionList> usersTeams) {
        Gson gson = new Gson();
        String json = gson.toJson(usersTeams);
        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.DIVISIONS_LIST, json).commit();

    }

    public static List<UsersTeam> getUserTeam(Context context) {
        String jsonData = getDefaultSP(context).getString(Constants.SharedPreferencesKeys.USER, null);
        UsersTeam[] usersTeamArray = new Gson().fromJson(jsonData, UsersTeam[].class);
        List<UsersTeam> usersTeams = new ArrayList<>();
        for (int i = 0; i < usersTeamArray.length; i++) {
            usersTeams.add(usersTeamArray[i]);
        }
        return usersTeams;
    }

    public static List<DivisionList> getDivisions(Context context) {
        String jsonData = getDefaultSP(context).getString(Constants.SharedPreferencesKeys.DIVISIONS_LIST, null);
        DivisionList[] usersTeamArray = new Gson().fromJson(jsonData, DivisionList[].class);
        List<DivisionList> usersTeams = new ArrayList<>();
        if (usersTeamArray != null) {
            for (int i = 0; i < usersTeamArray.length; i++) {
                usersTeams.add(usersTeamArray[i]);
            }
        }
        return usersTeams;
    }

    public static void saveMobileIntoSP(String mobno) {

        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.MOBILE, mobno).commit();

    }

    public static void saveCustomerCompanyIdIntoSP(int companyId) {

        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putInt(Constants.SharedPreferencesKeys.COMPANYID, companyId).commit();

    }

    public static void saveLoginTypeIntoSP(String loginType) {
        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.LOGINTYPE, loginType).commit();
    }


    public static void saveDepartmentIdIntoSP(int companyId) {

        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putInt(Constants.SharedPreferencesKeys.DEPARTMENTID, companyId).commit();

    }

    public static void saveDepartmentIdIntoSP(String deptname) {

        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.DEPARTMENTNAME, deptname).commit();

    }

    public static void saveCompanynameIntoSP(String companyname) {

        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.COMPANYNAME, companyname).commit();

    }

    public static void setCircleImageBackgroundFromUrl(Context context, ImageView profileImgView, String profilePicUrl) {

        if (profilePicUrl != null && !TextUtils.isEmpty(profilePicUrl)) {

            Picasso.with(context).load(profilePicUrl).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(profileImgView);

        } else {
            Log.i("Unable to set Bg Image.");
        }


    }

    public static String getLocaleFromSP(Context context) {
//        return Common.getDefaultSP(context).getString(Constants.SharedPreferencesKeys.APP_LOCALE, Language.ENGLISH.getLocaleCode());

        Log.i("? - Locale.getDefault().getLanguage() : " + Locale.getDefault().getLanguage());

        return Common.getDefaultSP(context).getString(Constants.SharedPreferencesKeys.APP_LOCALE, Locale.getDefault().getLanguage());
    }

    public static void setLocaleToSP(Context context, String localeCode) {
        Common.getDefaultSP(context).edit().putString(Constants.SharedPreferencesKeys.APP_LOCALE, localeCode).commit();

    }

    public static void restartApp(Activity activity) {

        Intent launchIntentForPackage = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
        launchIntentForPackage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        launchIntentForPackage.putExtra(Constants.IntentKeys.SHOW_CONTENT_VIEW, false);
        activity.startActivity(launchIntentForPackage);

    }

    public static void sendUnReadNotificationCountBrodCast(Context context, int unReadNotificationsCount) {

        Intent intent = new Intent();

        // intent.setAction(PushNotificationListenerActivity.UN_READ_NOTIFICATION_COUNT_CHANGE);

        intent.putExtra(Constants.KEY_1, unReadNotificationsCount);

        context.sendBroadcast(intent);

    }

    public static Dialog getAppThemeCustomDialog(Activity activity, int contentViewLayoutId, float leftRightMargin) {

        final Dialog dialog = new Dialog(activity);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(contentViewLayoutId);

        DisplayMetrics metrics = new DisplayMetrics();

        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        Log.i("? - Display width in px is " + metrics.widthPixels);

//                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setLayout(metrics.widthPixels - (int) leftRightMargin, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setGravity(Gravity.CENTER);

        return dialog;

    }


    public static String booleanToIntString(boolean b) {

        return String.valueOf(b ? 1 : 0);

    }

    public static void startActivity(Activity applicationContext, Class<?> activityClass) {
        applicationContext.startActivity(new Intent(applicationContext, activityClass));
    }

    public static String ifNullThenNA(String operator) {
        return operator != null ? operator : "N/A";
    }

//    public static void saveUserDetailsIntoSP(CustomerLoginResVo loginResInfo) {
//        Gson gson = new Gson();
//        String json = gson.toJson(loginResInfo);
//        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.USER_DETIALS, json).commit();
//
//    }
//
//    public static void saveEmpUserDetailsIntoSP(LoginResInfo loginResInfo) {
//        Gson gson = new Gson();
//        String json = gson.toJson(loginResInfo);
//        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.USER_DETIALS, json).commit();
//
//    }

//    public static CustomerLoginResVo getUserDetailsFromSP(Context context) {
//        String userDetails = Common.getDefaultSP(context).getString(Constants.SharedPreferencesKeys.USER_DETIALS, "");
//        Gson gson = new Gson();
//        CustomerLoginResVo loginResInfo = gson.fromJson(userDetails, CustomerLoginResVo.class);
//        return loginResInfo;
//    }
//
//    public static LoginResInfo getEmpUserDetailsFromSP(Context context) {
//        String userDetails = Common.getDefaultSP(context).getString(Constants.SharedPreferencesKeys.USER_DETIALS, "");
//        Gson gson = new Gson();
//        LoginResInfo loginResInfo = gson.fromJson(userDetails, LoginResInfo.class);
//        return loginResInfo;
//    }

    public static void clearPreferenceData(Context context) {
        SharedPreferences.Editor editor = getDefaultSP(context).edit();
        editor.remove(Constants.SharedPreferencesKeys.USER_DETIALS);
        editor.remove(Constants.SharedPreferencesKeys.USER_ROLE);
        editor.remove(Constants.SharedPreferencesKeys.USERID);
        editor.commit();
    }

    public static void saveUserPassword(String password) {
        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.PASSWORD, password).commit();
    }


    public static String getUserPassword(Context context) {
        return Common.getDefaultSP(context).getString(Constants.SharedPreferencesKeys.PASSWORD, "");
    }

    public static String getSelectedDate(Calendar calendar) {
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    public static String getSelectedDate(Date time) {
        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }

    public static String getOnlyDate(Date time) {
        return new SimpleDateFormat("dd-MM-yyyy").format(time);
    }

    public static Date convertDate(String dateStr) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int getRoleIdFromSP(Context context) {
        return getDefaultSP(context).getInt(Constants.SharedPreferencesKeys.ROLE_ID, 0);

    }

    public static void saveLocationServiceStatusIntoSP(String status) {

        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.LOCATION_SERVICE_STATUS, status).commit();

    }

    public static boolean validate(Activity context, EditText editText, String msg) {
        final boolean[] isValidated = {true};
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (editText.getText().toString().trim().length() == 0) {
                    editText.requestFocus();
                    editText.setError(msg);
                    isValidated[0] = false;
                }
            }
        });
        return isValidated[0];
    }

    public static void getSelectedUser(BaseFragment fragment) {
        Activity context = fragment.getActivity();
        List<UsersTeam> usersTeams = Common.getUserTeam(context);
        UsersTeam allTeam = new UsersTeam();
        allTeam.name = "ALL";
        allTeam.userId = 0;
        usersTeams.add(0, allTeam);
        for (int i = 0; i < usersTeams.size(); i++) {
            if (usersTeams.get(i).userId == Common.getUserIdFromSP(context)) {
                usersTeams.get(i).name = "My Data";
                break;
            }
        }
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        View dialogView = context.getLayoutInflater().inflate(R.layout.users_list, null);
        alertDialog.setView(dialogView);
        RecyclerView usersRecycler = dialogView.findViewById(R.id.users_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        usersRecycler.setLayoutManager(linearLayoutManager);
        UserAdapter userAdapter = new UserAdapter(context, usersTeams);
        usersRecycler.setAdapter(userAdapter);
        alertDialog.show();
        userAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, View viewItem, int position) {
                String userId = String.valueOf(usersTeams.get(position).userId);
                if (usersTeams.get(position).name.equalsIgnoreCase("ALL"))
                    userId = Common.getTeamUserIdFromSP(context);
                fragment.setUserId(userId);
                alertDialog.dismiss();
            }
        });

    }


    public static void saveLeftNav(List<LeftNav> leftNavs) {
        Gson gson = new Gson();
        String json = gson.toJson(leftNavs);
        getDefaultSP(MyApplication.getInstance().getApplicationContext()).edit().putString(Constants.SharedPreferencesKeys.LEFTNAV, json).commit();

    }

    public static LeftNav getLeftNav(Context context, String methodName) {
        String jsonData = getDefaultSP(context).getString(Constants.SharedPreferencesKeys.LEFTNAV, null);
        LeftNav[] leftNavArray = new Gson().fromJson(jsonData, LeftNav[].class);
        for (int i = 0; i < leftNavArray.length; i++) {
            if (methodName.equalsIgnoreCase(leftNavArray[i].methodName)) {
                return leftNavArray[i];
            }
        }
        return null;
    }

    public static int getNoOfdays(String visitDate) {
        int days = 0;
        Date currentDate = Common.convertDate(Common.getCurrentDate());
        Date maxDate = Common.convertDate(visitDate);
        if (currentDate.after(maxDate)) {
            long differenceMillis = currentDate.getTime() - maxDate.getTime();
            long daysInMillis = 1000 * 60 * 60 * 24;
            long elapsedDays = differenceMillis / daysInMillis;
            days = (int) elapsedDays;

        }
        return days;
    }

    public static double stringToDuble(String s) {
        if (s == null || s.equalsIgnoreCase("") || !isNumeric(s))
            return 0;
        else return Double.parseDouble(s);
    }

    public static void startService(Context context, Class<?> backgroundServiceClass) {
        try {
            context.startService(new Intent(context, backgroundServiceClass));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void stopService(Context context, Class<?> backgroundServiceClass) {
        try {
            context.stopService(new Intent(context, backgroundServiceClass));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String arrayListToString(List<CompaintNameKey> salesAssessment) {
        try {
            return new Gson().toJson(salesAssessment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = null;
        try {
            arr = new Gson().fromJson(s, clazz);
            return Arrays.asList(arr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

    public static List<CompaintNameKey> getAssessmentData(String s) {
        try {

            if (s != null) {
                return stringToArray(s, CompaintNameKey[].class);

            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static String getAssessmentString(List<CompaintNameKey> list) {
        if (list == null || list.size() == 0) {
            return "";

        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) {
                    sb.append("\n");
                }
                sb.append(list.get(i).complement_ass_name);
            }
            return sb.toString();
        }
    }

    public static SpannableString setSppanableText(String company) {
        SpannableString spannableS = new SpannableString(company);
        spannableS.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableS;
    }

    public static String addZero(int dayOfMonth) {
        if (dayOfMonth > 9) {
            return String.valueOf(dayOfMonth);
        }
        return "0" + dayOfMonth;
    }

    public static class Log {
        public static void i(String string) {

            android.util.Log.i("SUPRASOFT", string);


        }
    }

    public static void setClickable(View view) {
        if (view != null) {
            view.setClickable(false);
            if (view instanceof ViewGroup) {
                ViewGroup vg = ((ViewGroup) view);
                for (int i = 0; i < vg.getChildCount(); i++) {
                    setClickable(vg.getChildAt(i));
                }
            }
        }
    }

    public static void setLanguage(Context context, String language) {
        Locale locale1 = new Locale(language);
        Locale.setDefault(locale1);
        Configuration config1 = new Configuration();
        config1.locale = locale1;
        context.getResources().updateConfiguration(config1,
                context.getResources().getDisplayMetrics());

    }

    public static void hideView(Context context, final View view, int id) {
        Animation animation = AnimationUtils.loadAnimation(context, id);
        //use this to make it longer:  animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }
        });

        view.startAnimation(animation);
    }


    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    public static void copy(String string, Context context) {
        if (string.trim().length() > 0) {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboardMgr = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardMgr.setText(string.trim());
            } else {
                // this api requires SDK version 11 and above, so suppress warning for now
                android.content.ClipboardManager clipboardMgr = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied..", string.trim());
                clipboardMgr.setPrimaryClip(clip);
            }
            customToast(context, Common.getStringResourceText(R.string.copied), TOAST_TIME);
        }
    }

    public void paste(TextView txtNotes, Context context) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard.getText() != null) {
                // txtNotes.getText().insert(txtNotes.getSelectionStart(), clipboard.getText());

            }
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            if (item.getText() != null) {
                // txtNotes.getText().insert(txtNotes.getSelectionStart(), item.getText());
            }
        }
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isValidMobileNumber(String phoneNumber) {

        if (phoneNumber.matches("123456789") || phoneNumber.matches("1234567890")
                || phoneNumber.matches("0123456789") || phoneNumber.matches("0000000000")
                || phoneNumber.matches("1111111111") || phoneNumber.matches("2222222222")
                || phoneNumber.matches("3333333333") || phoneNumber.matches("4444444444")
                || phoneNumber.matches("5555555555") || phoneNumber.matches("6666666666")
                || phoneNumber.matches("7777777777") || phoneNumber.matches("8888888888")
                || phoneNumber.matches("9999999999") || phoneNumber.matches("0000000000")) {
            return false;
        }
        return true;
    }

    public static Bitmap getBitmapFromByteArray(byte[] bytes) {

        return bytes == null ? null : BitmapFactory.decodeStream(new ByteArrayInputStream(bytes));
    }

    public static void bitmapToDrawable(Bitmap bitmap, TextView textView) {
        BitmapDrawable d = new BitmapDrawable(bitmap);
        d.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
        textView.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);


    }


    public static Activity getPreviousActivity() {
        return previousActivity;
    }

    public static void setPreviousActivity(Activity activity) {
        previousActivity = activity;
    }


    public static SecretKey generateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new SecretKeySpec(Constants.ENCRYPTION_KEY.getBytes(), "AES");
    }

    public static byte[] encryptMsg(String message, SecretKey secret) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        /* Encrypt the message. */
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));
        return cipherText;
    }

    public static String decryptMsg(byte[] cipherText, SecretKey secret) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {

        /* Decrypt the message, given derived encContentValues and initialization vector. */
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        String decryptString = new String(cipher.doFinal(cipherText), "UTF-8");
        return decryptString;
    }


    public static String getTwoDecimalRoundValueString(double value) {
//        return new DecimalFormat("#.##").format(value);
        return new DecimalFormat("0.00").format(value);
    }

    public static void removeZeroPrefix(final Context context, final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // android.util.Log.d("beforeTextChanged","No need to enter1 "+s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // android.util.Log.d("beforeTextChanged","No need to enter 2 "+s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // android.util.Log.d("beforeTextChanged","No need to enter 3 "+s.toString());
                if (s.toString().startsWith("0")) {
                    //      android.util.Log.d("beforeTextChanged","No need to enter 4 "+s.toString());
                    editText.setText("");
                    Common.customToast(context, "No need to enter 0");
                }
            }
        });

    }

    public static void restrictSpaceInPasswordField(final Context context, final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                android.util.Log.d("beforeTextChanged", "No need to enter1 " + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                android.util.Log.d("beforeTextChanged", "No need to enter 2 " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                android.util.Log.d("beforeTextChanged", "Space not allowed. " + s.toString());
                if (s.toString().contains(" ")) {
                    //      android.util.Log.d("beforeTextChanged","No need to enter 4 "+s.toString());
                    editText.setText(s.toString().replaceAll("\\s", ""));
                    editText.setSelection(s.toString().replaceAll("\\s", "").length());
                    Common.customToast(context, "Space not allowed.");
                }
            }
        });

    }


    public static String ignoreZeroPrefix(final EditText editText) {
        String text = editText.getText().toString().trim();
        if (NumberUtils.isDigits(text)) {
            if (text.startsWith("0")) {
                return text.substring(1);
            } else {
                return text;
            }


        } else {
            return text;
        }

    }


    public static Bitmap getBitmapFromView(View view) {

        Log.i("? - view.getWidth() : " + view.getWidth());
        Log.i("? - view.getHeight() : " + view.getHeight());

        Log.i("? - view.getMeasuredWidth() : " + view.getMeasuredWidth());
        Log.i("? - view.getMeasuredHeight() : " + view.getMeasuredHeight());

        view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.draw(c);

        return b;


    }


    /**
     * Compares two version strings.
     * <p>
     * Use this instead of String.compareTo() for a non-lexicographical
     * comparison that works for version strings. e.g. "1.10".compareTo("1.6").
     *
     * @param str1 a string of ordinal numbers separated by decimal points.
     * @param str2 a string of ordinal numbers separated by decimal points.
     * @return The result is a negative integer if str1 is _numerically_ less than str2.
     * The result is a positive integer if str1 is _numerically_ greater than str2.
     * The result is zero if the strings are _numerically_ equal.
     * @note It does not work if "1.10" is supposed to be equal to "1.10.0".
     */
    public static int versionCompare(String str1, String str2) {
        String[] vals1 = str1.split("\\.");
        String[] vals2 = str2.split("\\.");
        int i = 0;
        // set index to first non-equal ordinal or length of shortest version string
        while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
            i++;
        }
        // compare first non-equal ordinal number
        if (i < vals1.length && i < vals2.length) {
            int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
            return Integer.signum(diff);
        }
        // the strings are equal or one string is a substring of the other
        // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
        return Integer.signum(vals1.length - vals2.length);
    }


    public static void setupUI(final Activity activity, View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(activity, innerView);
            }
        }
    }


    public static File savefile(Activity activity, Uri sourceuri) {
        String sourceFilename = sourceuri.getPath();
        //   String destinationFilename = android.os.ServiceUrlEnvironment.getExternalStorageDirectory().getPath()+File.separatorChar+"abc.mp3";
        File mFileTemp = null;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            //  mFileTemp = new File(ServiceUrlEnvironment.getExternalStoragePublicDirectory(UpdateProfileActivity.IMAGE_DIRECTORY_NAME), InternalStorageContentProvider.getOutputMediaFile(1));
        } else {
            // mFileTemp = new File(activity.getFilesDir(), InternalStorageContentProvider.getOutputMediaFile(1));
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(sourceFilename));
            bos = new BufferedOutputStream(new FileOutputStream(mFileTemp.getPath(), false));
            byte[] buf = new byte[1024];
            bis.read(buf);
            do {
                bos.write(buf);
            } while (bis.read(buf) != -1);
        } catch (IOException e) {

        } finally {
            try {
                if (bis != null) bis.close();
                if (bos != null) bos.close();
            } catch (IOException e) {

            }
        }
        return mFileTemp;
    }


   /* public static TypedFile getTypedFile(File file) {
        Log.i("file : " + file);
        return (file != null) ? new TypedFile("multipart/form-data", file) : null;
    }*/


    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    public static int getColorFromResource(int resourceId) {
        return MyApplication.getInstance().getResources().getColor(resourceId);
    }


    public static void setViewsEnableStatus(boolean status, View... views) {


        Log.i("views.length : " + views.length);

        for (View view : views) {

            Log.i("view : " + view);
            Log.i("view.toString() : " + view.toString());
            view.setFocusableInTouchMode(status);
            view.setFocusable(status);
            view.setEnabled(status);


        }


    }

    public static void setViewsEnableStatuss(boolean status, View... views) {


        Log.i("views.length : " + views.length);

        for (View view : views) {

            Log.i("view : " + view);
            Log.i("view.toString() : " + view.toString());
//            view.setFocusableInTouchMode(status);
//            view.setFocusable(status);
            view.setEnabled(status);


        }


    }


    public static String currentTime() {
        String time = "";
        long timeInMillis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        //  SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh:mm aa");
        time = simpleDateFormat.format(calendar.getTime());

        return time;
    }

    public static String getCurrentDateAndTime() {
        return new SimpleDateFormat("HH:mm aa").format(new Date());
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getTodaysDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }


    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {


        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm, newWidth, newHeight, false);
        return resizedBitmap;
    }

    public static String nullCheker(String string) {
        return string == null || string.equals("") ? "NA" : string;
    }

    public static String isNull(String string) {
        return string == null || string.equals("") ? "0" : string;
    }

    public static String nullChecker(String string) {
        return string == null || string.equals("") ? "" : string;
    }

    public static String nullCheckerStatus(String string) {
        return string == null || string.equals("") || string.contains("Select") ? "" : string;
    }


    public static void quit(Context context) {
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(0);
       /* Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);*/

    }

    public static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    public static String[] getDatenewFormat(String newdate) {
        String[] dateFormats = new String[2];
        DateFormat inputFormat;
        if (newdate == null) {
            return dateFormats;
        }
        String[] dateF = newdate.split("-");
        if (dateF == null || dateF.length == 0) {
            return dateFormats;
        }
        if (dateF[0].length() > 2) {
            inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        }
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormatServer = new SimpleDateFormat("yyyy-MM-dd");
        String inputDateStr1 = newdate;
        String outputDateStr1 = null;
        try {
            Date newdate1 = inputFormat.parse(inputDateStr1);
            outputDateStr1 = outputFormat.format(newdate1);
            String outputDateStr2 = outputFormatServer.format(newdate1);
            dateFormats[0] = outputDateStr1;
            dateFormats[1] = outputDateStr2;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormats;
    }

    public static Date getDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static List<String> getBetweenDates(String dateString1, String dateString2) {
        ArrayList<String> dates = new ArrayList<String>();
        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            dates.add(Common.getOnlyDate(cal1.getTime()));
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static String getDateOnlyAsString(String date) {
        DateFormat inputFormat = new SimpleDateFormat(
                "dd-MM-YYYY", Locale.ENGLISH);
        try {
            dates = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat outputFormat = new SimpleDateFormat("",
                Locale.ENGLISH);
        //   outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        String output = String.valueOf(outputFormat.format(dates).split("-"));
        return output;
    }


    public static String splitDate(String date, int value) {
        String[] items1 = date.split("-");
        String d1 = items1[0];
        String m1 = items1[1];
        String y1 = items1[2];
        int d = Integer.parseInt(d1);
        int m = Integer.parseInt(m1);
        int y = Integer.parseInt(y1);
        if (value == 1)
            return d1;
        else if (value == 2)
            return m1;
        else return y1;
    }

    public static String getMonthShortName(int monthNumber) {
        String monthName = "";

        if (monthNumber >= 0 && monthNumber < 12)
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, monthNumber);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
                simpleDateFormat.setCalendar(calendar);
                monthName = simpleDateFormat.format(calendar.getTime());
            } catch (Exception e) {
                if (e != null)
                    e.printStackTrace();
            }
        return monthName;
    }

    public static List<String> getSundayslist(String d1, String d2) {
        ArrayList<String> sundays = new ArrayList<String>();
        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(d1);
            date2 = df1.parse(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        while (!cal1.after(cal2)) {
//            if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ){
//                saturday++;
//            }
            if (cal1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                sundays.add(Common.getOnlyDate(cal1.getTime()));
            }

            cal1.add(Calendar.DATE, 1);
        }

//        System.out.println("Saturday Count = "+saturday);
//        System.out.println("Sunday Count = "+sundays);
        return sundays;
    }

    public static String time12HoursFormat(String _24HourTime) {

        String input = _24HourTime;
        String newTimeFormat = "";
        //Date/time pattern of input date
        DateFormat df = new SimpleDateFormat(" HH:mm:ss");
        //Date/time pattern of desired output date
        DateFormat outputformat = new SimpleDateFormat(" hh:mm aa");
        Date date = null;
        String output = null;
        try {
            //Conversion of input String to date
            date = df.parse(input);
            //old date format to new date format
            output = outputformat.format(date);
            String[] formated = output.split(" ");
//            newTimeFormat = formated[1] + " " + formated[2];
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return newTimeFormat;
    }


    public static String checkSunday(String d1) {
        String sundays = null;
        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = null;

        try {
            date1 = df1.parse(d1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


//            if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ){
//                saturday++;
//            }
        if (cal1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            sundays = Common.getOnlyDate(cal1.getTime());
        }

        cal1.add(Calendar.DATE, 1);


//        System.out.println("Saturday Count = "+saturday);
//        System.out.println("Sunday Count = "+sundays);
        return sundays;
    }

    public static String addOneDay(String date) {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = null;

        try {
            date1 = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date1);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        String sdf = Common.getOnlyDate(cal.getTime());
        return sdf;
    }

    public static Date returnDate(String onDate) {
        // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        Date date = null;
        try {
            date = format.parse(onDate);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String getAppCurrentversion(Context context) {

        PackageManager pm = context.getPackageManager();

        PackageInfo pInfo = null;

        try {
            pInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;

        } catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return null;
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

   /* public static void startServices(Class<?> serviceClass, Context context) {
        if (LocationService.isMyServiceRunning(serviceClass, context)) {
            context.stopService(new Intent(context, serviceClass));
        }
        context.startService(new Intent(context, serviceClass));
    }*/

    public static float getDistanceByAccuracy(float accuracy) {

        if (accuracy <= 20) {
            return 80;
        } else if (accuracy > 20 && accuracy <= 30) {
            return 200;
        } else if (accuracy > 30 && accuracy <= 60) {
            return 350;
        } else if (accuracy > 60 && accuracy <= 100) {
            return 700;
        } else if (accuracy > 100 && accuracy <= 500) {
            return 1000 * 2;
        } else if (accuracy > 500 && accuracy <= 1000) {
            return 3000 + accuracy;
        } else if (accuracy > 1000) {
            return accuracy * 6;
        }

        return 20000;
    }

    public static String currentTimeFromMS(long milliseconds) {
        // New date object from millis
        Date date = new Date(milliseconds);
// formattter
        SimpleDateFormat formatter = new SimpleDateFormat("HH-mm-ss");
// Pass date object
        return formatter.format(date);
    }

    public static void saveCurrentLocationInSP(Context context, double latitude, double longitude, float location_accuracy, long location_time, float location_max_speed) {
        getDefaultSP(context).edit().putString("latitude", String.valueOf(latitude)).commit();
        getDefaultSP(context).edit().putString("longitude", String.valueOf(longitude)).commit();
        getDefaultSP(context).edit().putString("location_accuracy", String.valueOf(location_accuracy)).commit();
        getDefaultSP(context).edit().putLong("location_time", location_time).commit();
        getDefaultSP(context).edit().putFloat("location_max_speed", location_max_speed).commit();
    }

    public static Location getCurrentLocationFromSP(Context context) {
        Location location = new Location("");
        location.setLatitude(Double.parseDouble(getDefaultSP(context).getString("latitude", "0")));
        location.setLongitude(Double.parseDouble(getDefaultSP(context).getString("longitude", "0")));
        location.setAccuracy(Float.parseFloat(getDefaultSP(context).getString("location_accuracy", "0")));
        location.setTime(getDefaultSP(context).getLong("location_time", 0));
        location.setSpeed(getDefaultSP(context).getFloat("location_max_speed", 0));

        return location;
    }

    public static boolean isSpeedBelow120KPH(float distanceInMetre, long timeInMilliSeconds, float isSpeed) {
        float timeInSeconds = timeInMilliSeconds / 1000;
        float speed = distanceInMetre / timeInSeconds;
        Common.Log.i("Speed: " + speed + "m/Sec" + " previous speed" + isSpeed);
        if (speed > isSpeed + 3) //34 metre
            return false;

        return true;
    }

    public static float calculateMaxSpeed(float distanceInMetre, long timeInMilliSeconds) {
        float timeInSeconds = timeInMilliSeconds / 1000;
        float speed = distanceInMetre / timeInSeconds;
        Common.Log.i("Speed: " + speed + "m/Sec");
        return speed;
    }

    public static String bodyToString(final RequestBody request) {

        try {
//            final RequestBody copy = request.);
            final Buffer buffer = new Buffer();
            request.writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    public static long timeToMillliSecond(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(time);
            return date.getTime();

        } catch (ParseException e) {
            Common.Log.i("Parse Exception " + e.getMessage() + "\n Parse Localized Exception " + e.getLocalizedMessage());
        }
        return 0;
    }

    public static String millliSecondToStringTime(long milli) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date = new Date(milli);
            return dateFormat.format(date);

        } catch (Exception e) {
        }
        return "00:00:00";
    }

    public static void writeFile(String data) {
        //  File externalDirectory = new File(FOLDER_EXTERNAL_DIRECTORY);
        String FOLDER_EXTERNAL_DIRECTORY = "Test";
        final String FILE_NAME = "error";
        File externalDirectory = new File(Environment.getExternalStorageDirectory(), FOLDER_EXTERNAL_DIRECTORY);
        if (!externalDirectory.exists())
            externalDirectory.mkdirs();
        File toFile = new File(externalDirectory, FILE_NAME + " " + System.currentTimeMillis());
        FileWriter writer = null;
        try {
            writer = new FileWriter(toFile);

            writer.append(data);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void autoLaunchApp(Context context) {
        if ("xiaomi".equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
            autoLaunchXiaomi(context);
        } else if ("huawei".equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
            ifHuaweiAlert(context);
        } else if ("vivo".equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
            autoLaunchVivo(context);
        } else if ("oppo".equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
            initOPPO(context);
        }
    }


    private static void initOPPO(Context context) {
        try {

            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setComponent(new ComponentName("com.oppo.safe", "com.oppo.safe.permission.floatwindow.FloatWindowListActivity"));
            context.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            try {

                Intent intent = new Intent("action.coloros.safecenter.FloatWindowListActivity");
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.floatwindow.FloatWindowListActivity"));
                context.startActivity(intent);
            } catch (Exception ee) {

                ee.printStackTrace();
                try {

                    Intent i = new Intent("com.coloros.safecenter");
                    i.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity"));
                    context.startActivity(i);
                } catch (Exception e1) {

                    e1.printStackTrace();
                }
            }

        }
    }

    private static void autoLaunchVivo(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.iqoo.secure",
                    "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"));
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.vivo.permissionmanager",
                        "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
                context.startActivity(intent);
            } catch (Exception ex) {
                try {
                    Intent intent = new Intent();
                    intent.setClassName("com.iqoo.secure",
                            "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager");
                    context.startActivity(intent);
                } catch (Exception exx) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void autoLaunchXiaomi(Context context) {
        try {
            String manufacturer = "xiaomi";
            if (manufacturer.equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
                //this will open auto start screen where user can enable permission for your app
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                context.startActivity(intent);
            }

        } catch (Exception e) {

        }
    }


    private static void ifHuaweiAlert(final Context context) {
        final SharedPreferences settings = context.getSharedPreferences("ProtectedApps", MODE_PRIVATE);
        final String saveIfSkip = "skipProtectedAppsMessage";
        boolean skipMessage = settings.getBoolean(saveIfSkip, false);
        if (!skipMessage) {
            final SharedPreferences.Editor editor = settings.edit();
            Intent intent = new Intent();
            intent.setClassName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity");
            if (isCallable(intent, context)) {
                final AppCompatCheckBox dontShowAgain = new AppCompatCheckBox(context);
                dontShowAgain.setText("Do not show again");
                dontShowAgain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        editor.putBoolean(saveIfSkip, isChecked);
                        editor.apply();
                    }
                });

                new androidx.appcompat.app.AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Huawei Protected Apps")
                        .setMessage(String.format("%s requires to be enabled in 'Protected Apps' to function properly.%n", context.getString(R.string.app_name)))
                        .setView(dontShowAgain)
                        .setPositiveButton("Protected Apps", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                huaweiProtectedApps(context);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
            } else {
                editor.putBoolean(saveIfSkip, true);
                editor.apply();
            }
        }
    }

    private static boolean isCallable(Intent intent, Context context) {
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private static void huaweiProtectedApps(Context context) {
        try {
            String cmd = "am start -n com.huawei.systemmanager/.optimize.process.ProtectActivity";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                cmd += " --user " + getUserSerial(context);
            }
            Runtime.getRuntime().exec(cmd);
        } catch (IOException ignored) {
        }
    }

    private static String getUserSerial(Context context) {
        //noinspection ResourceType
        Object userManager = context.getSystemService(Context.USER_SERVICE);
        if (null == userManager) return "";

        try {
            Method myUserHandleMethod = android.os.Process.class.getMethod("myUserHandle", (Class<?>[]) null);
            Object myUserHandle = myUserHandleMethod.invoke(android.os.Process.class, (Object[]) null);
            Method getSerialNumberForUser = userManager.getClass().getMethod("getSerialNumberForUser", myUserHandle.getClass());
            Long userSerial = (Long) getSerialNumberForUser.invoke(userManager, myUserHandle);
            if (userSerial != null) {
                return String.valueOf(userSerial);
            } else {
                return "";
            }
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException ignored) {
        }
        return "";
    }

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public static boolean isGpsEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;

        }
        return false;
    }

    public static Handler disableClickEvent(final View v, boolean needEnable) {
        Handler handler = null;
        v.setEnabled(false);
        v.setFocusable(false);
        v.setClickable(false);
        if (needEnable) {
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    v.setEnabled(true);
                    v.setFocusable(true);
                    v.setClickable(true);
                }
            }, 5000);
        }

        return handler;
    }


    public static Handler disableClickEvent(final View v) {
        return disableClickEvent(v, false);
    }

    public static void disableClickEvent(final View v, Handler handler) {
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
        else
            disableClickEvent(v);

    }

    public static void dismissAlertDialog(androidx.appcompat.app.AlertDialog alertDialog) {

        if (alertDialog != null) {
            alertDialog.dismiss();
        }

    }

    public static void dismissDialog(Dialog dialog) {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

    }

    public static String encode(final List<LatLng> path) {
        if (path == null || path.size() < 1) {
            return "";
        }
        long lastLat = 0;
        long lastLng = 0;

        final StringBuffer result = new StringBuffer();

        for (final LatLng point : path) {
            long lat = Math.round(point.latitude * 1e5);
            long lng = Math.round(point.longitude * 1e5);

            long dLat = lat - lastLat;
            long dLng = lng - lastLng;

            encode(dLat, result);
            encode(dLng, result);

            lastLat = lat;
            lastLng = lng;
        }
        return result.toString();
    }

    private static void encode(long v, StringBuffer result) {
        v = v < 0 ? ~(v << 1) : v << 1;
        while (v >= 0x20) {
            result.append(Character.toChars((int) ((0x20 | (v & 0x1f)) + 63)));
            v >>= 5;
        }
        result.append(Character.toChars((int) (v + 63)));
    }

    public static List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;
        try {
            while (index < len) {
                int b;
                int shift = 0;
                int result = 0;
                do {
                    b = poly.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                index=0;
                do {
                    b = poly.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                decoded.add(new LatLng(
                        lat / 100000d, lng / 100000d
                ));
            }

            return decoded;
        }catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }

    public static String textInputToString(TextInputLayout textInputLayout) {
        return textInputLayout.getEditText().getText().toString();
    }

    public static Date returnTime(String onDate) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        Date date = null;
        try {
            date = format.parse(onDate);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String sendEmpty(String string) {
        return string == null || string.equals("") ? "" : string;
    }

    public static boolean isUserCheckedIn(Context context) {
        Geo_Tracking_POJO geoTrackingData = DatabaseHandler.getDatabase(context).commonDao().getGeoTrackingData(Common.getCurrentDate(), Common.getUserIdFromSP(context));
        if (geoTrackingData != null && (geoTrackingData.checkOutTime == null || geoTrackingData.checkOutTime.length() < 6)) {
            return true;
        }
        return false;
    }

    public static String toCamelCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char previousChar = inputString.charAt(i - 1);
            if (previousChar == ' ') {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
        }
        return result;
    }

    @RequiresPermission("android.permission.WRITE_SETTINGS")
    public static boolean setSettingsAutomaticDateTimeIfNeeded(Context context) {
        try {
            if (Settings.Global.getInt(context.getContentResolver(), Settings.Global.AUTO_TIME) == 1) {
                // Toast.makeText(context, "Enabled", Toast.LENGTH_LONG).show();
                // Enabled
                return true;
            } else {
              /* android.provider.Settings.Global.putString(
                       context.getContentResolver(),
                       android.provider.Settings.Global.AUTO_TIME, "1");*/
                //  Toast.makeText(context, "Disabled", Toast.LENGTH_LONG).show();
                return false;
                // Disabed
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public static String divisionString(Context context, String divisionIds) {
        StringBuilder sb = new StringBuilder("");
        boolean foundOne = false;
        if (divisionIds != null && divisionIds.length() > 0) {
            String[] ids = divisionIds.split(",");
            if (ids != null && ids.length > 0) {
                for (int i = 0; i < ids.length; i++) {
                    List<DivisionList> list = Common.getDivisions(context);
                    if (list != null && list.size() > 0) {
                        for (int j = 0; j < list.size(); j++) {
                            if (ids[i].equalsIgnoreCase(list.get(j).divisionMasterId)) {
                                if (foundOne) {
                                    sb.append(",");
                                }
                                foundOne = true;
                                sb.append(list.get(j).divisionName);
                            }
                        }
                    }

                }
            }

        }
        return sb.toString();

    }
}

