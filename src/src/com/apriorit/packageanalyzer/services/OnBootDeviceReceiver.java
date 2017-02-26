
package com.apriorit.packageanalyzer.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnBootDeviceReceiver extends BroadcastReceiver
{
    public void onReceive( Context context, Intent intent )
    {     
        context.startService( new Intent( context, ScannerService.class ) );
    }
}
