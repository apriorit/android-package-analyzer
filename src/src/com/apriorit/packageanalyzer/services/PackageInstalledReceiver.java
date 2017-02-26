
package com.apriorit.packageanalyzer.services;

import java.util.List;

import com.apriorit.packageanalyzer.AlertStatus;
import com.apriorit.packageanalyzer.PackageScanner;
import com.apriorit.packageanalyzer.PermissionChecker;
import com.apriorit.packageanalyzer.R;
import com.apriorit.packageanalyzer.activities.PackageInfoActivity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class PackageInstalledReceiver extends BroadcastReceiver
{
    private NotificationManager notificationManager = null;

    public void onReceive( Context context, Intent intent )
    {
        notificationManager = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );

        String installedPackageName = intent.getDataString();
        if ( installedPackageName.contains( ":" ) )
        {
            installedPackageName = installedPackageName.substring( installedPackageName.indexOf( ":" ) + 1 );
        }

        handleInstalledPackageEvent( context, installedPackageName );
    }

    private void handleInstalledPackageEvent( Context context, String installedPackageName )
    {
        PermissionChecker permissionChecker = new PermissionChecker();
        PackageScanner packageScaner = new PackageScanner( context );
        
        List<String> permissions = packageScaner.getPermissions( installedPackageName );
        
        if ( permissionChecker.checkPermissions( permissions ) == AlertStatus.HIGH )
        {
            // Send notification
            int notifyID = 1;
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder( context )
                    .setSmallIcon( R.drawable.alert_high_red_small )
                    .setContentTitle( packageScaner.getApplicationName( installedPackageName ) 
                            + " " + context.getString( R.string.notification_installed ))
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setAutoCancel( true )
                    .setContentText( context.getString( R.string.notification_text ) );

            Intent resultIntent = new Intent( context, PackageInfoActivity.class );
            resultIntent.putExtra( PackageInfoActivity.PACKAGE_NAME_EXTRAS, installedPackageName );

            TaskStackBuilder stackBuilder = TaskStackBuilder.create( context );

            stackBuilder.addParentStack( PackageInfoActivity.class );

            // Adds the Intent that starts the Activity to the top of the stack
 
            int requestCode = 0;
            stackBuilder.addNextIntent( resultIntent );
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent( requestCode, PendingIntent.FLAG_UPDATE_CURRENT );
            mBuilder.setContentIntent( resultPendingIntent );

            notificationManager.notify( notifyID, mBuilder.build() );
        }
    }
}
