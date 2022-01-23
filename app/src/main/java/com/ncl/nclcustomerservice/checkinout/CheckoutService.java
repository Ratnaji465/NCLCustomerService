package com.ncl.nclcustomerservice.checkinout;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.Geo_Tracking_POJO;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by suprasoft on 12/10/2018.
 */

public class CheckoutService extends IntentService implements RetrofitResponseListener {

    private MyReceiver myReceiver;
    private static final String CHANNEL_ID = "channel_auto_checkout_01";
    private int MID = 10001;
    private String[] data;
    private SharedPreferences sharedpreferences;
    private DatabaseHandler db;
    private NotificationManager mNotificationManager;
    private Location locationF;
    private String  checkinlatlong;
    ArrayList<File> files = new ArrayList<>();

    public CheckoutService() {
        super("CheckoutService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

//        sharedpreferences = this.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        db = DatabaseHandler.getDatabase(this);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            // Create the channel for the notification
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            mChannel.setDescription("no sound");
            mChannel.setSound(null, null);
            mChannel.enableLights(false);
            mChannel.setLightColor(Color.BLUE);
            mChannel.enableVibration(false);

            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel);
        }
        myReceiver = new MyReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(LocationUpdatesService.ACTION_BROADCAST));

        long when = System.currentTimeMillis();

        Intent notificationIntent = new Intent();
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Service Auto Fired")
                .setContentText("Gps tracking stopped before 11:59 PM").setSound(alarmSound)
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotifyBuilder.setChannelId(CHANNEL_ID); // Channel ID
        }

//        data = isCheckedIn(this);
        if (data != null && data.length > 0) {
            mNotificationManager.notify(MID, mNotifyBuilder.build());
            if (Common.haveInternet(this))
//                checkOutFunction();
            new Async_Checkout(this).execute(data);
            else{
//                ContentValues contentValues = new ContentValues();
//                contentValues.put(SYNC_STATUS, 0);
//                new DatabaseHandler(this).updateTable(TABLE_GEO_TRACKING, contentValues, KEY_TABLE_GEO_TRACKING_FFMID, data[0]);

            }

        }

    }

    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {

    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(LocationUpdatesService.EXTRA_LOCATION);
            Log.d("location F", location.toString());
            if (location != null) {
                locationF = location;
//                if (displayToast)
//                    Toast.makeText(context, Utils.getLocationText(location),
//                            Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void checkOutFunction() {
         String stringRoutePath;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm:ss");
        String strDate = sdftime.format(c.getTime());
        Log.e("Check_out_time", strDate);
        String visit_date = Common.getCurrentDate();
        double latitude = locationF.getLatitude();
        double longitude = locationF.getLongitude();
        int a = Common.haveInternet(this) ? 1 : 0;

        checkinlatlong = String.valueOf(latitude) + "," + String.valueOf(longitude) + "," + locationF.getAccuracy() + "," + a + "," + Common.currentTimeFromMS(System.currentTimeMillis());

//        Toast.makeText(getActivity(), checkinlatlong, Toast.LENGTH_LONG).show();

        Geo_Tracking_POJO geoTrackingData = db.commonDao().getGeoTrackingData(Common.getCurrentDate(), Common.getUserIdFromSP(getApplicationContext()));
        if (geoTrackingData != null) {
            if (geoTrackingData.routePathLatLon != null)
                stringRoutePath = geoTrackingData.routePathLatLon + ":" + checkinlatlong;
            else
                stringRoutePath = checkinlatlong;
//region StringRoutePath
            if (stringRoutePath != null) {
                if (a == 1) {
                    Geo_Tracking_POJO geo_tracking_pojo = new Geo_Tracking_POJO();
                    geo_tracking_pojo.type = "check_out_lat_lon";
                    geo_tracking_pojo.latlon = checkinlatlong;
                    geo_tracking_pojo.checkOutTime = strDate;
                    geo_tracking_pojo.trackingId = geoTrackingData.trackingId;
                    geo_tracking_pojo.userId = String.valueOf(Common.getUserIdFromSP(this));
                    geo_tracking_pojo.routePathLatLon = stringRoutePath;
                    geo_tracking_pojo.requestname = Constants.RequestNames.GEO_CHECK_IN_OUT;
//                    sendImage(files, geo_tracking_pojo);

                } else {
                    Toast.makeText(this, "No Internet Connection ! \n Please Try again", Toast.LENGTH_SHORT).show();
                }
            }
            //endregion
        }
//        String selectQuerys = "SELECT  " + KEY_TABLE_GEO_TRACKING_ID + "," + KEY_TABLE_GEO_TRACKING_ROUTE_PATH_LAT_LONG + "," + KEY_TABLE_GEO_TRACKING_FFMID +","+PAUSE+","+RESUME+ " FROM " + TABLE_GEO_TRACKING + " where " + KEY_TABLE_GEO_TRACKING_USER_ID + " = " + userId + " and  visit_date like '" + datefromcalander + "%'" + " ORDER BY " + KEY_TABLE_GEO_TRACKING_ID + " DESC LIMIT 1 ";
//        sdbw = db.getWritableDatabase();
//
//        Cursor cc = sdbw.rawQuery(selectQuerys, null);
//        System.out.println("cursor count " +  selectQuerys);
//
//        if (cc != null && cc.moveToFirst()) {
//            ffmIDTrackingID = cc.getString(2);
//            tid = String.valueOf(cc.getLong(0)); //The 0 is the column index, we only have 1 column, so the index is 0
//            String pauseP = cc.getString(3); //The 0 is the column index, we only have 1 column, so the index is 0
//            String resumeP = cc.getString(4); //The 0 is the column index, we only have 1 column, so the index is 0
//
//            Log.e("++ checkout lastId ++", ffmIDTrackingID);
//
//            stringRoutePath = cc.getString(1) + ":" + checkinlatlong;
//
//            //  stringRoutePath = "23.2745823,77.3698906,4.551,1,19-27-24:23.2741552,77.3705778,9.102,1,19-27-38:23.2747185,77.3711825,6.068,1,19-27-52:23.2753108,77.3717187,13.653,1,19-28-07:23.2759512,77.3721397,4.551,1,19-28-17:23.2767163,77.3724288,7.585,1,19-28-30:23.2774168,77.37274,7.585,1,19-28-43:23.2779029,77.3733293,10.619,1,19-28-54:23.2784608,77.373993,7.585,1,19-29-08:23.2790608,77.3746576,13.653,1,19-29-21:23.2797895,77.3745348,11.871,1,19-29-45:23.280539,77.3746082,18.204,1,19-30-01:23.2813289,77.3748156,16.687,1,19-30-20:23.2812582,77.3752041,9.212,1,19-37-52";
//            if (stringRoutePath != null) {
//                if (Common.haveInternet(PlanerOneActivity.this)) {
//                    new Async_Checkout().execute("NA", "0",pauseP,resumeP);
//                } else {
//                    Toast.makeText(PlanerOneActivity.this, "No Internet Connection ! \n Please Try again", Toast.LENGTH_SHORT).show();
//                }
//                ArrayList<String> latlng = new ArrayList(Arrays.asList(stringRoutePath.split(":")));
/*
                if (latlng != null && latlng.size() > 0) {

                    if (latlng.size() > 1) {

                        prepairDataForRoadSnap(latlng);
                    } else {
                        listForSnapRoad.add(constructURL(latlng, null));
                        Common.dismissDialog(progressDialogInOut);
                        if (listForSnapRoad.size() > 0)
                            new AsyncRoadSnap().execute(listForSnapRoad.get(0));
                    }

                }*/


//            }

//        }

    }
//    private void sendImage(ArrayList<File> files, Geo_Tracking_POJO obj) {
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
//        final String strDate = simpleDateFormat.format(calendar.getTime());
//
//        List<MultipartBody.Part> muPartList = new ArrayList<>();
//        if (files != null) {
//            for (int i = 0; i < files.size(); i++) {
//                muPartList.add(prepareFilePart("file_i[]", Uri.fromFile(files.get(i)), files.get(i)));
//            }
//
//        }
//        Common.Log.i("Request obj " + new Gson().toJson(obj));
//        MultipartBody.Part[] fileParts = muPartList.toArray(new MultipartBody.Part[muPartList.size()]);
//        Call<ResponseBody> abc = MyApplication.getInstance().getAPIInterface().uploadFiles(Constants.API, fileParts, obj);
//        abc.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//                try {
//                    ApiResponseController apiResponseController = new Gson().fromJson(response.body().string(), ApiResponseController.class);
//                    CheckInOutResVo checkInOutResVo = Common.getSpecificDataObject(apiResponseController.result, CheckInOutResVo.class);
//                    if (checkInOutResVo != null) {
//                        if (Common.getDefaultSP(getApplicationContext()).getString("checkin", "").equalsIgnoreCase("true")) {
//                            SharedPreferences.Editor editor = Common.getDefaultSP(getApplicationContext()).edit();
//                            editor.putString("tracking_id", "");
//                            editor.putString("checkinlatlong", "");
//                            editor.putString("checkin", "false");
//                            editor.putString("check_in_time", "");
//                            editor.commit();
//                            db.commonDao().updateCheckoutData("1", checkinlatlong, strDate, checkInOutResVo.polyline, checkInOutResVo.distance, checkInOutResVo.personalUsesKm, checkInOutResVo.trackingId);
//                            try {
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                    mService.cancelAlarm();
//                                }
//                                mService.removeLocationUpdates();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            PolylineReqVo polylineReqVo = new PolylineReqVo();
//                            polylineReqVo.trackingId = checkInOutResVo.trackingId;
//                            new RetrofitRequestController(CheckoutService.this).sendRequest(Constants.RequestNames.GEO_POLYLINE, polylineReqVo, true);
//                        } else {
//                            SharedPreferences.Editor editor = Common.getDefaultSP(getApplicationContext()).edit();
//                            editor.putString("tracking_id", checkInOutResVo.trackingId);
//                            editor.putString("checkinlatlong", checkinlatlong);
//                            editor.putString("checkin", "true");
//                            editor.putString("check_in_time", visit_date + " " + check_in_time);
//                            editor.commit();
//                            geo_tracking_pojo.trackingId = checkInOutResVo.trackingId;
//                            geo_tracking_pojo.checkInLatLong = checkinlatlong;
//                            geo_tracking_pojo.routePathLatLon = checkinlatlong;
//                            db.commonDao().insertGeoTrackingPojo(geo_tracking_pojo);
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    rfaButtonColor();
//                                    isSignedIn = true;
//                                }
//                            });
//
//
//                        }
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                try {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        mService.cancelAlarm();
//                    }
//                    mService.removeLocationUpdates();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//    private MultipartBody.Part prepareFilePart(String file_i, Uri uri, File file) {
//        okhttp3.RequestBody requestFile =
//                okhttp3.RequestBody.create(
//                        okhttp3.MediaType.parse(getMimeType(getActivity(), uri)),
//                        file
//                );
//
//        // MultipartBody.Part is used to send also the actual file name
//        return MultipartBody.Part.createFormData(file_i, file.getName(), requestFile);
//    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    private class Async_Checkout extends AsyncTask<String[], Void, String> {

        final MediaType MEDIA_TYPE = MediaType.parse("image/jpg");
        Context context;
        private String jsonData;
        private String strDate;
        private DatabaseHandler db;

        private Async_Checkout(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog = new ProgressDialog(PlanerOneActivity.this);
            // progressDialog.setMessage("Submitting Check out");
            // progressDialog.show();
        }

        protected String doInBackground(String[]... params) {


            db = DatabaseHandler.getDatabase(context);

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm:ss");
            strDate = sdftime.format(c.getTime());
            Log.e("Check_out_time1", strDate);


            try {
                String[] path = params[0][1].split(":");
                final String checkoutLatLng = path[path.length - 1];
                OkHttpClient client = new OkHttpClient();
                 /*For passing parameters*/
                MultipartBuilder multipartBuilder = new MultipartBuilder();
                multipartBuilder.type(MultipartBuilder.FORM);


                multipartBuilder.addFormDataPart("type", "check_out_lat_lon");
                multipartBuilder.addFormDataPart("latlon", checkoutLatLng);
                multipartBuilder.addFormDataPart("check_out_time", strDate);
                multipartBuilder.addFormDataPart("tracking_id", params[0][0]);
                multipartBuilder.addFormDataPart("user_id", params[0][4]);
                //  multipartBuilder.addFormDataPart("distance", params[1]);
//                multipartBuilder.addFormDataPart("route_snap", String.valueOf(snapRoadCallsSuccess));
//                multipartBuilder.addFormDataPart("route_snap_all", String.valueOf(snapRoadCallsAll));
//                multipartBuilder.addFormDataPart("route_snap_failure", String.valueOf(snapRoadCallsFailure));
//                multipartBuilder.addFormDataPart("google_direction", String.valueOf(directionServiceCallsSuccess));
//                multipartBuilder.addFormDataPart("google_direction_all", String.valueOf(directionServiceCallsAll));
//                multipartBuilder.addFormDataPart("google_direction_failure", String.valueOf(directionServiceCallsFailure));
                multipartBuilder.addFormDataPart("pause", Common.nullChecker(params[0][2]));
                multipartBuilder.addFormDataPart("resume", Common.nullChecker(params[0][3]));
                multipartBuilder.addFormDataPart("route_path_lat_lon", params[0][1]);
                // multipartBuilder.addFormDataPart("polyline", params[0]);
                multipartBuilder.addFormDataPart("check_out_by", "5");
               /* if (starting_kms_img.getVisibility() == View.VISIBLE) {
                    multipartBuilder.addFormDataPart("meter_reading_checkout_image", outputFileKm.getAbsolutePath(), RequestBody.create(MEDIA_TYPE, outputFileKm));
                    multipartBuilder.addFormDataPart("meter_reading_checkout_text", meter_reading_checkin_text);

                }

                if (checkInComment != null) {
                    if (tv_comment_hint.getText().toString() != null && tv_comment_hint.getText().toString().trim().equalsIgnoreCase("Personal Uses KM")) {
                        multipartBuilder.addFormDataPart("personal_uses_km", checkInComment);
                    } else {

                        multipartBuilder.addFormDataPart("checkin_comment", checkInComment);
                    }

                }*/
                RequestBody formBody = multipartBuilder.build();
                Response responses = null;

                Log.d("body", "body:  " + Common.bodyToString(formBody));


                /*MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType,
                        "type=check_in_lat_lon&visit_type=1&user_id=7&latlon=17.4411%2C78.3911&visit_date=2016-12-05%2C18%3A27%3A30&check_in_time=18%3A27%3A30&tracking_id=0");*/
                /*Request request = new Request.Builder()
                        .url(Constants.URL_NSL_MAIN + Constants.URL_CHECKINOUT)
                        .post(formBody)
                        .addHeader("authorization", "Basic cmVzdDpzZWVkc0BhZG1pbg==")
                        // .addHeader("content-type", "application/x-www-form-urlencoded")
                        .addHeader("cache-control", "no-cache")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    public SQLiteDatabase sdbw;

                    @Override
                    public void onFailure(Request request, IOException e) {
                       *//* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            mService.cancelAlarm();
                        }
                        mService.removeLocationUpdates();
                        Common.dismissProgressDialog(progressDialogInOut);*//*

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        jsonData = response.body().string();
                        System.out.println("!!!!!!!1 checkout" + jsonData);
                        if (jsonData != null) {
                            try {
                                //deleteFile();
                                // {"status":"success","msg":"successfully! done","tracking_id":"11723"}
                                JSONObject jsonObject = new JSONObject(jsonData);
                                if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                                    String polyline = jsonObject.getString("polyline");
                                    String distance = jsonObject.getString("distance");
                                    String meter_reading_checkout_image = jsonObject.getString("meter_reading_checkout_image");
                                    String tracking_id = jsonObject.getString("tracking_id");
                                    String personal_uses_km = jsonObject.getString("personal_uses_km");
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("tracking_id", "");
                                    editor.putString("checkinlatlong", "");
                                    editor.putString("checkin", "false");
                                    editor.commit();

                                    sdbw = db.getWritableDatabase();
                                    String updatequerys = "UPDATE " + TABLE_EMPLOYEE_VISIT_MANAGEMENT + " SET " + KEY_EMP_STATUS + " = '" + "4" + "' WHERE " + KEY_EMP_VISIT_USER_ID + " = " + Common.getUserIdFromSP(CheckoutService.this) + " and " + KEY_EMP_PLAN_DATE_TIME + " like '" + Common.getCurrentDateYYYYMMDD() + "%'";
                                    sdbw.execSQL(updatequerys);

                                    String updatequery1 = "UPDATE " + TABLE_GEO_TRACKING + " SET " + KEY_TABLE_GEO_TRACKING_UPDATED_STATUS + " = 1 ," + KEY_TABLE_GEO_TRACKING_CHECK_OUT_LAT_LONG + " = '" + checkoutLatLng + "'," + KEY_TABLE_GEO_TRACKING_CHECK_OUT_TIME + " = '" + strDate + "'," + KEY_TABLE_GEO_TRACKING_POLYLINE + " = '" + polyline + "'," + KEY_TABLE_GEO_TRACKING_DISTANCE + " = '" + distance + "'," + METER_READING_CHECKOUT_IMAGE + " = '" + meter_reading_checkout_image + "'," + PERSONAL_USES_KM + " = '" + personal_uses_km + "' WHERE " + KEY_TABLE_GEO_TRACKING_MASTER_ID + " = " + tracking_id;

                                    sdbw.execSQL(updatequery1);

                                    new AsyncCheckoutAcknowledge(CheckoutService.this, tracking_id).execute();

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (sdbw.isOpen())
                                    sdbw.close();
                            }
                        }
                    }
                });
*/

            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


           /* Intent intent1 = new Intent(PlanerOneActivity.this, LocationService.class);
            stopService(intent1);
            Log.d("onDestroy", "onDestroy 2.......................");*/


        }
    }

    /*public static String[] isCheckedIn(Context context) {
        String[] id = new String[5];

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat(Constants.DateFormat.COMMON_DATE_FORMAT);
        String datefromcalander = df.format(c.getTime());
//        String selectQueryss = "SELECT  " + KEY_TABLE_GEO_TRACKING_ID + "," + KEY_TABLE_GEO_TRACKING_ROUTE_PATH_LAT_LONG + "," + KEY_TABLE_GEO_TRACKING_FFMID + "," + KEY_TABLE_GEO_TRACKING_CHECK_OUT_TIME + "," + KEY_TABLE_GEO_TRACKING_CREATED_DATETIME + "," + PAUSE + "," + RESUME + "," + KEY_EMP_VISIT_USER_ID + " FROM " + TABLE_GEO_TRACKING + " where " + " visit_date like '" + datefromcalander + "%' and user_id ='" + Common.getUserIdFromSP(context) + "'" + " ORDER BY " + KEY_TABLE_GEO_TRACKING_ID + " DESC LIMIT 1 ";
        SQLiteDatabase sdbw1 = new DatabaseHandler(context).getWritableDatabase();
        try {

            Cursor ccc = sdbw1.rawQuery(selectQueryss, null);
            System.out.println("cursor count " + ccc.getCount() + "\n" + selectQueryss);
            if (ccc != null && ccc.moveToFirst()) {
                if ((ccc.getString(3) == null || ccc.getString(3).equalsIgnoreCase("") || ccc.getString(3).equalsIgnoreCase("null")) && (ccc.getString(4) != null && ccc.getString(4).length() > 5)) {
                    id[0] = ccc.getString(2);
                    id[1] = ccc.getString(1);
                    id[2] = ccc.getString(5);
                    id[3] = ccc.getString(6);
                    id[4] = ccc.getString(7);
                    return id;
                }
            }
        } catch (Exception e) {

        } finally {
           *//* if (sdbw1!=sdbw1sdbw1.isOpen())
                sdbw1.close();*//*
            Common.Log.i("DB Closed: " + "finally called");
        }
        return null;
    }*/

    /*public class AsyncCheckoutAcknowledge extends AsyncTask<Void, Void, String> {

        private final Context context;
        private final String trackingId;
        private String path;
        private String jsonData;

        public AsyncCheckoutAcknowledge(Context context, String id) {
            this.context = context;
            this.trackingId = id;

            // this.path = path;
        }

        protected String doInBackground(Void... params) {

            try {

                OkHttpClient client = new OkHttpClient();
                 *//*For passing parameters*//*
                RequestBody formBody = new FormEncodingBuilder()
                        //.add("latlon", path)
                        .add("tracking_id", trackingId)
                        .build();

                Response responses = null;
                Log.d("AsyncCheckout", "AsyncCheckoutAcknowledge..");
                *//*MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType,
                        "type=check_in_lat_lon&visit_type=1&user_id=7&latlon=17.4411%2C78.3911&visit_date=2016-12-05%2C18%3A27%3A30&check_in_time=18%3A27%3A30&tracking_id=0");*//*
                Request request = new Request.Builder()
                        .url(Constants.URL_NSL_MAIN + Constants.URL_GEO_POLYLINE)
                        .post(formBody)
                        .addHeader("authorization", "Basic cmVzdDpzZWVkc0BhZG1pbg==")
                        .addHeader("content-type", "application/x-www-form-urlencoded")
                        .addHeader("cache-control", "no-cache")
                        .build();


                client.newCall(request).enqueue(new Callback() {
                    public SQLiteDatabase sdbw;


                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        jsonData = response.body().string();
                        System.out.println("!!!!!!!1 URL_GEO_POLYLINE : " + jsonData);

                        if (jsonData != null) {
                            try {
                                //deleteFile();
                                // {"status":"success","msg":"successfully! done","tracking_id":"11723"}
                                JSONObject jsonObject = new JSONObject(jsonData);
                                if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                                    String polyline = jsonObject.getString("polyline");
                                    String distance = jsonObject.getString("distance");
                                    String meter_reading_checkout_image = jsonObject.getString("meter_reading_checkout_image");
                                    String tracking_id = jsonObject.getString("tracking_id");
                                    String personal_uses_km = jsonObject.getString("personal_uses_km");
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("tracking_id", "");
                                    editor.putString("checkinlatlong", "");
                                    editor.putString("checkin", "false");
                                    editor.commit();
                                    sdbw = db.getWritableDatabase();
                                    String updatequerys = "UPDATE " + TABLE_EMPLOYEE_VISIT_MANAGEMENT + " SET " + KEY_EMP_STATUS + " = '" + "4" + "' WHERE " + KEY_EMP_VISIT_USER_ID + " = " + Common.getUserIdFromSP(CheckoutService.this) + " and " + KEY_EMP_PLAN_DATE_TIME + " like '" + Common.getCurrentDateYYYYMMDD() + "%'";
                                    sdbw.execSQL(updatequerys);

                                    String updatequery1 = "UPDATE " + TABLE_GEO_TRACKING + " SET " + KEY_TABLE_GEO_TRACKING_UPDATED_STATUS + " = 1 ," + KEY_TABLE_GEO_TRACKING_POLYLINE + " = '" + polyline + "'," + KEY_TABLE_GEO_TRACKING_DISTANCE + " = '" + distance + "'," + METER_READING_CHECKOUT_IMAGE + " = '" + meter_reading_checkout_image + "'," + PERSONAL_USES_KM + " = '" + personal_uses_km + "'," + SYNC_STATUS + " = 1 WHERE " + KEY_TABLE_GEO_TRACKING_MASTER_ID + " = " + tracking_id;

                                    sdbw.execSQL(updatequery1);


                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (sdbw.isOpen())
                                    sdbw.close();
                            }
                        }

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonData;
        }


    }*/
}
