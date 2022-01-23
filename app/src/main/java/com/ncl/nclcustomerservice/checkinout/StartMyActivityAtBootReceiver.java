package com.ncl.nclcustomerservice.checkinout;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by suprasoft on 2/6/2018.
 */

public class StartMyActivityAtBootReceiver extends BroadcastReceiver {

    public static int mJobId=1212;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("StartMyActivityAtBoot", "onReceive......");
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d("StartMyActivityAtBoot", "ACTION_BOOT_COMPLETED......");
           /* Intent intent1=new Intent(context,LocationService.class);
            context.startService(intent1);*/

            ComponentName componentName = new ComponentName(context, JobScheduleService.class);
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(
                    Context.JOB_SCHEDULER_SERVICE);
            final JobInfo jobInfo;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                jobInfo = new JobInfo.Builder(mJobId, componentName)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .build();
                jobScheduler.schedule(jobInfo);
            }


        }
    }
}
