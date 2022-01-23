package com.ncl.nclcustomerservice.checkinout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;
import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.application.MyApplication;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.customviews.CircleImageView;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostRouteMapActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, ViewTreeObserver.OnGlobalLayoutListener, RetrofitResponseListener {

    @BindView(R.id.back_button)
    ImageView back_button;
    @BindView(R.id.title_text)
    TextView title_text;
    private static final int REQUEST_CHECK_SETTINGS = 111;
    GoogleMap map;
    DatabaseHandler db;
    ArrayList<MarkerData> markerPoints;
    LatLng point;
    String userId, jsonData, datefromcalander, datefromrecords, str_address, str_name, str_userName;
    SQLiteDatabase sdbw, sdbr;
    SimpleDateFormat sdf;
    SharedPreferences sharedpreferences;
    //Define a request code to send to Google Play services
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    int hours;
    String latitude, longitude, str_distance, timeTaken;
    double duration;
    public static final String mypreference = "mypref";
    private TextView tv_distance, tv_name;
    private EditText et_date;
    private Spinner spin_company;
    //    private ArrayList<SelectedCities> organisations;
    ArrayList<String> adaptercity;
    private String team;
    private int role;
    private String userName;
    private LinearLayout ll_select;
    private Location myCurrentLocation;
    long distanceFinal = 0;
    private List<LatLng> decodedPolyLine;
    private String distanceValueFinal = "0";
//    List<Employee> employeeList = new ArrayList<>();
    String selected_employeeid;
    EmpActivityPojo empActivityPojo = new EmpActivityPojo();
    private List<LatLng> points=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_route_map);
        ButterKnife.bind(this);

        title_text.setText("Route map");
        db = DatabaseHandler.getDatabase(this);
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId = sharedpreferences.getString("userId", "");
        team = sharedpreferences.getString("team", "");
        role = Common.getRoleIdFromSP(this);

        sdf = new SimpleDateFormat(Constants.DateFormat.COMMON_DATE_FORMAT);
        tv_distance = (TextView) findViewById(R.id.tv_distance);
        tv_name = (TextView) findViewById(R.id.tv_name);
        et_date = (EditText) findViewById(R.id.et_date);
        spin_company = (Spinner) findViewById(R.id.spin_user);
        ll_select = (LinearLayout) findViewById(R.id.ll_select);
        et_date.setVisibility(View.VISIBLE);

        if (isMO()) {
            ll_select.setVisibility(View.GONE);
            selected_employeeid = String.valueOf(Common.getUserIdFromSP(this));
        }else {
//            new RetrofitRequestController(this).sendRequest(Constants.RequestNames.EMPLOYEE_ACTIVITY, "", false);
        }


//        new getEmployessByTeam().execute();

        if (datefromcalander == null) {
            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());

            SimpleDateFormat df = new SimpleDateFormat(Constants.DateFormat.COMMON_DATE_FORMAT);
            datefromcalander = df.format(c.getTime());
        }

        // Initializing
        markerPoints = new ArrayList<MarkerData>();


        if (map == null) {
            ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {

                    // check if map is created successfully or not
                    if (map == null) {
                        //Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
                    }
                    map = googleMap;
                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    if (ActivityCompat.checkSelfPermission(PostRouteMapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PostRouteMapActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    map.setMyLocationEnabled(true);
                    map.getUiSettings().setZoomControlsEnabled(true);
                    map.setPadding(0, 30, 30, 100);
                    //googleMap.getUiSettings().setRotateGesturesEnabled(true);
                    //googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                    // googleMap.getUiSettings().setCompassEnabled(true);
                    // map.setMaxZoomPreference(15);
                    map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                        @Override
                        public boolean onMyLocationButtonClick() {
                            if (myCurrentLocation == null) {
                                if (ActivityCompat.checkSelfPermission(PostRouteMapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PostRouteMapActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                    return false;
                                }
                                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, PostRouteMapActivity.this);
                            }

                            displayGoogleLocationSettingPage(PostRouteMapActivity.this, REQUEST_CHECK_SETTINGS);
                            return true;
                        }
                    });
                }
            });

            try {

//                new Async_getalloffline().execute(datefromcalander);
            } catch (Exception e) {
                Log.d("Tag", e.toString());
            }

        }


        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 3000)
                // 10 seconds, in milliseconds
                .setFastestInterval(1 * 2000); // 1 second, in milliseconds
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();
                final int mYear = mcurrentDate.get(Calendar.YEAR);
                final int mMonth = mcurrentDate.get(Calendar.MONTH);
                final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog mDatePicker = new DatePickerDialog(PostRouteMapActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        int sel_month = selectedmonth + 1;
                        String sday = String.valueOf(selectedday);
                        String smonth = null;
                        if (sel_month < 10)
                            smonth = "0" + sel_month;
                        else
                            smonth = String.valueOf(sel_month);

                        if (selectedday < 10)
                            sday = "0" + selectedday;
                        else
                            sday = String.valueOf(selectedday);
                        map.clear();
                        markerPoints.clear();
                        et_date.setText(Common.getDatenewFormat(selectedyear + "-" + smonth + "-" + sday)[0]);
                        et_date.setTag(selectedyear + "-" + smonth + "-" + sday);
                        if (selected_employeeid != null) {
//                            new getPolyline(selected_employeeid, et_date.getTag().toString()).execute();
                        }

                        tv_distance.setVisibility(View.VISIBLE);
                        datefromcalander = et_date.getText().toString();
                        try {


//                            new Async_getalloffline().execute(datefromcalander);
                        } catch (Exception e) {
                            Log.d("Tag", e.toString());
                        }
                    }
                }, mYear, mMonth, mDay);
                //mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        tv_name.setText(str_userName + " > " + datefromcalander);
    }


    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception downloading ", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {
//        db = DatabaseHandler.getDatabase(this);
//        switch (objectRequest.requestname) {
//
//
//            case Constants.RequestNames.EMPLOYEE_ACTIVITY:
//                if (objectResponse.result != null) {
//                    EmpActivityResponseV empActivityPojo = Common.getSpecificDataObject(objectResponse.result, EmpActivityResponseV.class);
//                    if (empActivityPojo != null && empActivityPojo.list != null && empActivityPojo.list.size() > 0)
//                        new EmpActity().execute(empActivityPojo);
//
//                }
//                break;
//            default:
//                break;
//        }
    }

    /*public class EmpActity extends AsyncTask<EmpActivityResponseV, Void, Void> {

        @Override
        protected Void doInBackground(EmpActivityResponseV... voids) {
            for (int i = 0; i < voids[0].list.size(); i++) {
                db = DatabaseHandler.getDatabase(MyApplication.getInstance());
                db.commonDao().insertEmpActivities(voids[0].list.get(i));
                if (voids[0].list.get(i).empActivityLogsPojo != null && voids[0].list.get(i).empActivityLogsPojo.size() > 0) {
                    for (int j = 0; j < voids[0].list.get(i).empActivityLogsPojo.size(); j++) {
                        db.commonDao().insertEmpActivitiesLogs(voids[0].list.get(i).empActivityLogsPojo.get(j));
                    }
                }
            }
            return null;
        }
    }*/


    /*public class getPolyline extends AsyncTask<Void, Void, Void> {
        String employeeid, date;

        public getPolyline(String selected_employeeid, String s) {
            this.employeeid = selected_employeeid;
            this.date = s;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            empActivityPojo = db.commonDao().getpolyLine(employeeid, date);
            return null;
        }

        @Override
        protected void onPostExecute(Void d) {
            super.onPostExecute(d);
            tv_distance.setText(" 0" + " KM");
            if (empActivityPojo != null) {
                if (empActivityPojo.polyline != null && !empActivityPojo.polyline.equalsIgnoreCase("")) {
                    float distanceValue = round(Float.parseFloat(empActivityPojo.distance) / 1000, 2);
                    tv_distance.setText("Distance --> " + distanceValue + " KM");
                    Common.Log.i("poly :" + empActivityPojo.polyline);
                    points = Common.decodePolyLine(empActivityPojo.polyline);
                    map.addPolyline(new PolylineOptions().addAll(points).color(Color.RED).width(5));

                    if (points != null && points.size() > 0) {

                        MarkerOptions options = new MarkerOptions();
                        options.position(points.get(0));
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        map.addMarker(options);

                    }
                    if (points != null && points.size() > 1) {

                        MarkerOptions options = new MarkerOptions();
                        options.position(points.get(points.size() - 1));
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        map.addMarker(options);
                    }

                    List<EmpActivityLogsPojo> customerCheckinDetails = DatabaseHandler.getDatabase(PostRouteMapActivity.this).commonDao().getCustomerCheckinDetails(String.valueOf(empActivityPojo.employeeActivityId));
                    if (customerCheckinDetails != null && customerCheckinDetails.size() > 0) {
                        for (int i = 0; i < customerCheckinDetails.size(); i++) {
                            if (customerCheckinDetails.get(i).checkInLatLong != null) {
                                Customer customers = db.commonDao().getCustomerById(customerCheckinDetails.get(i).customerId);
                                String[] ab = customerCheckinDetails.get(i).checkInLatLong.split(",");
                                MarkerOptions options = new MarkerOptions();
                                options.position(new LatLng(Double.parseDouble(ab[0]), Double.parseDouble(ab[1])));
                                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                options.title(customers.customerName);
                                options.snippet("Time: " + customerCheckinDetails.get(i).checkInTime + " - " + customerCheckinDetails.get(i).checkOutTime);
                                map.addMarker(options).showInfoWindow();
                            }


                        }
                    }
                }else if (empActivityPojo.routePath!=null && !empActivityPojo.routePath.equalsIgnoreCase("")) {
                    ArrayList<String> latlng = new ArrayList(Arrays.asList(empActivityPojo.routePath.split(":")));
                    if (latlng == null)
                        return;

                    for (int i = 0; i < latlng.size(); i++) {
                        ArrayList<String> pointsList = new ArrayList(Arrays.asList(latlng.get(i).split(",")));
                        double lat = Double.parseDouble(pointsList.get(0));
                        double log = Double.parseDouble(pointsList.get(1));
                        points.add(new LatLng(lat, log));

                    }


                    map.addPolyline(new PolylineOptions().addAll(points).color(Color.RED).width(5));

                    if (points != null && points.size() > 0) {

                        MarkerOptions options = new MarkerOptions();
                        options.position(points.get(0));
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        map.addMarker(options);

                    }
                    if (points != null && points.size() > 1) {

                        MarkerOptions options = new MarkerOptions();
                        options.position(points.get(points.size() - 1));
                        options.icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(PostRouteMapActivity.this,0,"ABC")));
                        map.addMarker(options);
                    }

                    List<EmpActivityLogsPojo> customerCheckinDetails = DatabaseHandler.getDatabase(PostRouteMapActivity.this).commonDao().getCustomerCheckinDetails(String.valueOf(empActivityPojo.employeeActivityId));
                    if (customerCheckinDetails != null && customerCheckinDetails.size() > 0) {
                        for (int i = 0; i < customerCheckinDetails.size(); i++) {
                            if (customerCheckinDetails.get(i).checkInLatLong != null) {
                                Customer customers = db.commonDao().getCustomerById(customerCheckinDetails.get(i).customerId);
                                String[] ab = customerCheckinDetails.get(i).checkInLatLong.split(",");
                                MarkerOptions options = new MarkerOptions();
                                options.position(new LatLng(Double.parseDouble(ab[0]), Double.parseDouble(ab[1])));
                                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                options.title(customers.customerName);
                                options.snippet("Time: " + customerCheckinDetails.get(i).checkInTime + " - " + customerCheckinDetails.get(i).checkOutTime);
                                map.addMarker(options).showInfoWindow();
                            }


                        }

                    }
                }else{
                    Toast.makeText(PostRouteMapActivity.this, "Path does not exist, Please select other date", Toast.LENGTH_SHORT).show();
                }
            } else
                Toast.makeText(PostRouteMapActivity.this, "Path does not exist, Please select other date", Toast.LENGTH_SHORT).show();

        }
    }*/

    public float round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    @Override
    public void onLocationChanged(Location location) {
        myCurrentLocation = location;
        //handleNewLocation(location);
        Common.Log.i(String.valueOf(location.getLatitude()));
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        if (location == null) {

        } else {
            handleNewLocation(location);
        }
    }

    private void handleNewLocation(Location location) {
        if (location != null && map != null) {
            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
            LatLng newLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(newLatLng, 12);
            map.animateCamera(yourLocation);
        }
        // Marker marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("You are here!").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        //  Toast.makeText(getActivity(),latitude + longitude,Toast.LENGTH_LONG).show();
        // System.out.println("Location  : " + latitude + "==" + longitude + "==");

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
 /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
        super.onDestroy();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, PostRouteMapActivity.this);

    }

    @Override
    public void onResume() {
        super.onResume();

        mGoogleApiClient.connect();
        if (sharedpreferences.getString("Geo_tracing", "") != null && sharedpreferences.getString("Geo_tracing", "").equalsIgnoreCase("true")) {

        } else {
            try {
                if (Common.haveInternet(this)) {

                    // new Async_getall_geotracking().execute();

                } else {

                }

            } catch (Exception e) {
                Log.d("Tag", e.toString());
            }
        }
    }




    private void animateMarker(GoogleMap googleMap, final Marker marker, final LatLng toPosition,
                               final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = googleMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 5000;
        final Interpolator interpolator = new LinearInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }


    public void displayGoogleLocationSettingPage(final Activity activity, final int requestCode) {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(MyApplication.getInstance().getLocationRequest())
                .setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(MyApplication.getInstance().getGoogleApiClient(), builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                // final LocationSettingsStates s= result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        //  Common.myCurrentLocationCamera(currentLocation, gMap);

                        switch (requestCode) {
                            case REQUEST_CHECK_SETTINGS:

                                if (myCurrentLocation != null) {
                                    handleNewLocation(myCurrentLocation);
                                }
                                break;

                        }

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        //   Toast.makeText(activity, "RESOLUTION_REQUIRED", Toast.LENGTH_SHORT).show();
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(activity, requestCode);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.

                        break;
                }
            }
        });

    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        // final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        Log.d("onActivityResult", "OnresultAxtivity1");
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (myCurrentLocation != null) {
                            handleNewLocation(myCurrentLocation);
                        }
                        Log.d("onActivityResult", "OnresultAxtivity4");
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        // finish();

                        break;

                    default:
                        break;
                }
                break;


        }


    }


    /*public class getEmployessByTeam extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (Constants.Roles.MARKETING_OFFICER != Common.getRoleIdFromSP(PostRouteMapActivity.this)) {
                String[] team = Common.getTeamFromSp(PostRouteMapActivity.this).split(",");
                int[] numbers = new int[team.length];
                for (int i = 0; i < team.length; i++) {
                    numbers[i] = Integer.parseInt(team[i]);
                }
                employeeList = db.commonDao().getEmployeesByTeam(numbers);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (employeeList != null) {
                List<SpinnerModel> cList = new ArrayList<>();
                for (int i = 0; i < employeeList.size(); i++) {
                    SpinnerModel spinnerModel = new SpinnerModel();
                    spinnerModel.setTitle(employeeList.get(i).employeeName);
                    spinnerModel.setId(String.valueOf(employeeList.get(i).employeeId));
                    cList.add(spinnerModel);
                }
                CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(PostRouteMapActivity.this, R.layout.spinner_rows, cList);
                spin_company.setAdapter(customSpinnerAdapter);

                spin_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selected_employeeid = String.valueOf(employeeList.get(position).employeeId);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        }
    }*/

    @OnClick(R.id.back_button)
    void setBack_button() {
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onGlobalLayout() {
        ((MapFragment) getFragmentManager().findFragmentById(
                R.id.map)).getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
        zoomLatLngBounds(map, points);
    }

    public void zoomLatLngBounds(GoogleMap googleMap, List<LatLng> list) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        Log.d("List Size", "" + list.size());
        if (list != null && list.size() > 3) {
            int middlePosition = list.size() / 2;
            builder.include(list.get(0));
            builder.include(list.get(middlePosition));
            builder.include(list.get(list.size() - 1));

        } else if (list != null && list.size() < 3) {
            if (list.size() == 1) {
                builder.include(list.get(0));
            } else if (list.size() == 2) {
                builder.include(list.get(0));
                builder.include(list.get(1));
            }
        }


        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 48));
    }

    public static Bitmap createCustomMarker(Context context, @DrawableRes int resource, String _name) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);

        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);
       // markerImage.setImageResource(resource);
        if (Common.getImageFromSP(context) != null /*&& Common.getImageFromSP(this).length() > 5*/) {
            Picasso.with(context).load(Common.getImageFromSP(context)).into(markerImage);
        }
        TextView txt_name = (TextView)marker.findViewById(R.id.name);
        txt_name.setText(_name);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams((int)(context.getResources().getDimension(R.dimen._46sdp)), ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }

}
