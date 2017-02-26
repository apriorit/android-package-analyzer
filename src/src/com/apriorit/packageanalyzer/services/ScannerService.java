
package com.apriorit.packageanalyzer.services;

import com.apriorit.packageanalyzer.R;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.widget.Toast;

public class ScannerService extends Service
{
    public IBinder onBind( Intent intent )
    {
        return null;
    }

    public int onStartCommand( Intent intent, int flags, int startId )
    {
        setComponentEnabled(true, PackageInstalledReceiver.class );
        setComponentEnabled(true, OnBootDeviceReceiver.class );
        
        Toast.makeText( this, getString( R.string.scann_service_started ), Toast.LENGTH_LONG ).show();
        
        return super.onStartCommand( intent, flags, startId );
    }

    public void onDestroy()
    {
        super.onDestroy();

        setComponentEnabled(false, PackageInstalledReceiver.class );
        setComponentEnabled(false, OnBootDeviceReceiver.class );

        Toast.makeText( this,getString( R.string.scann_service_stopped ), Toast.LENGTH_LONG ).show();
    }

    private void setComponentEnabled(boolean enabled, Class<?> clazz)
    {   
        int state = enabled ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED 
                : PackageManager.COMPONENT_ENABLED_STATE_DISABLED; 
        
        getApplicationContext().getPackageManager().setComponentEnabledSetting(
                new ComponentName( getApplicationContext(), clazz ), state, PackageManager.DONT_KILL_APP );
    }
}
