
package com.apriorit.packageanalyzer;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServiceTools
{
    public static boolean isServiceRunning( Context context, String serviceClassName )
    {
        ActivityManager activityManager = (ActivityManager) context.getSystemService( Context.ACTIVITY_SERVICE );
        List<RunningServiceInfo> services = activityManager.getRunningServices( Integer.MAX_VALUE );

        for( RunningServiceInfo runningServiceInfo : services )
        {
            if ( runningServiceInfo.service.getClassName().equals( serviceClassName ) )
            {
                return true;
            }
        }
        return false;
    }
}
