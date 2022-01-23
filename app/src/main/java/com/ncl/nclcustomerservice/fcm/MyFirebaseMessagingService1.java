/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ncl.nclcustomerservice.fcm;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.ncl.nclcustomerservice.activity.LoginActivity;
import com.ncl.nclcustomerservice.activity.MainActivity;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.database.DatabaseHandler;

import java.util.Map;

public class MyFirebaseMessagingService1 extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final String UNASSIGNED = "unassigned_tickets";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Common.Log.i("Anil K.. " + "onMessageReceived.." + remoteMessage);
        Map<String, String> data = remoteMessage.getData();
        Common.Log.i("Notification  :" + remoteMessage.getData().get("title") + " ," + data.get("body") + " ," + data.get("type"));
       // String type = "Logout";
        String type = data.get("type");
        String id = data.get("id");
        Intent intent = new Intent(this, MainActivity.class);

//        if (data.get("role_id").equalsIgnoreCase(Com) {
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.putExtra("type", type);

            if (type.equalsIgnoreCase("Lead")) {
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type",type);

            } else if (type.equalsIgnoreCase("Lead Converted")) {
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type",type);

            } else if (type.equalsIgnoreCase("Customer")) {
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type",type);

            } else if (type.equalsIgnoreCase("Contact")) {
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type",type);

            } else if (type.equalsIgnoreCase("Opportunitie")) {
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type",type);

            } else if (type.equalsIgnoreCase("Quotation")) {
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type",type);

            } else if (type.equalsIgnoreCase("Contract")) {
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type",type);

            } else if (type.equalsIgnoreCase("SalesOrder")) {
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type",type);

            } else if (type.equalsIgnoreCase("SalesCalls")) {
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type",type);

        }else if (type.equalsIgnoreCase("Logout")) {
                DatabaseHandler db = DatabaseHandler.getDatabase(this);
                intent = new Intent(this, LoginActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type",type);

                db.commonDao().deleteComplaintList();
                db.commonDao().deleteContactList();
                db.commonDao().deleteContactList();
                db.commonDao().deleteCustomerList();
                db.commonDao().deleteEmpActivityPojo();
                db.commonDao().deleteCustomer();
                db.commonDao().deleteLeadList();
                db.commonDao().deleteOpportunities();
                db.commonDao().deleteSalesCallList();
                db.commonDao().deleteSalesOrderList();
                db.commonDao().deleteTadaList();
                db.commonDao().deleteGeoTrackingdata();
                Common.getDefaultSP(this).edit().clear().commit();

            }
//        } else if (data.get("role_id").equalsIgnoreCase(String.valueOf(Constants.Roles.REGIONAL_MANAGER))) {
//            if (type.equalsIgnoreCase("order_indent")) {
//                intent = new Intent(this, MainActivity.class);
//                intent.putExtra("page_no", 5);
//            } else if (type.equalsIgnoreCase("payment_collection")) {
//                intent = new Intent(this, MainActivity.class);
//                intent.putExtra("page_no", 8);
//            } else if (type.equalsIgnoreCase("complaints")) {
//                intent = new Intent(this, MainActivity.class);
//                intent.putExtra("page_no", 6);
//            }
//        }

        showNotificationMessage(this, data.get("title"), data.get("message"), intent);

    }

    private void showNotificationMessage(MyFirebaseMessagingService1 myFirebaseMessagingService1, String title, String message, Intent intent) {

        NotificationUtils notificationUtils = new NotificationUtils(myFirebaseMessagingService1);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent);
    }

//    private void showNotificationMesscage(MyFirebaseMessagingService1 myFirebaseMessagingService1, String title, String message, String page_type, Intent intent) {
//        NotificationUtils notificationUtils = new NotificationUtils(myFirebaseMessagingService1);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        notificationUtils.showNotificationMessage(title, message + "-" + page_type, intent);
//    }
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob(Map map) {
        Bundle bundle = new Bundle();
        bundle.putString("data", new Gson().toJson(map));
       /* bundle.putString("map", new Gosn);
        bundle.putLong("lon", lon);*/
        // [START dispatch_job]
       /* FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setLifetime(Lifetime.FOREVER)
                .setTag("my-job-tag")
                .setExtras(bundle)
                .build();
        dispatcher.schedule(myJob);*/
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow(Map map) {
      /*  Log.d(TAG, "Short lived task is done.");
        Bundle bundle = new Bundle();
        bundle.putString("data",new Gson().toJson(map));
        Intent intent = new Intent(this, IntentServiceForSendMsg.class);
        intent.putExtras(bundle);
        startService(intent);*/
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    /*private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_stat_ic_notification)
                        .setContentTitle("FCM Message")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, ID,,notificationBuilder.build());
    }*/


}
