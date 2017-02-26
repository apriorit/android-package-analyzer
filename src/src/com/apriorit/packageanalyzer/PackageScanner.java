
package com.apriorit.packageanalyzer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;

public class PackageScanner
{
    private PackageManager    packageManager    = null;

    private PermissionChecker permissionChecker = null;

    private Context           context           = null;

    public PackageScanner( Context context )
    {
        packageManager = context.getPackageManager();

        permissionChecker = new PermissionChecker();

        this.context = context;
    }

    public List<String> getInstalledPackagesList()
    {
        List<String> list = new ArrayList<String>();

        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages( PackageManager.GET_ACTIVITIES );

        for( PackageInfo packageInfo : packageInfoList )
        {
            list.add( packageInfo.packageName );
        }

        return list;
    }

    public List<String> getActivities( String packageName )
    {
        List<String> list = new ArrayList<String>();
        try
        {
            PackageInfo info = packageManager.getPackageInfo( packageName, PackageManager.GET_ACTIVITIES );

            for( ActivityInfo activityInfo : info.activities )
            {
                list.add( activityInfo.name );
            }
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
        }

        return list;
    }

    public List<String> getServices( String packageName )
    {
        List<String> list = new ArrayList<String>();
        try
        {
            PackageInfo info = packageManager.getPackageInfo( packageName, PackageManager.GET_SERVICES );

            for( ServiceInfo serviceInfo : info.services )
            {
                list.add( serviceInfo.name );
            }
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
        }

        return list;
    }

    public List<String> getReceivers( String packageName )
    {
        List<String> list = new ArrayList<String>();
        try
        {
            PackageInfo info = packageManager.getPackageInfo( packageName, PackageManager.GET_RECEIVERS );

            for( ActivityInfo receiverInfo : info.receivers )
            {
                list.add( receiverInfo.name );
            }
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
        }

        return list;
    }

    public List<String> getPermissions( String packageName )
    {
        List<String> list = new ArrayList<String>();
        try
        {
            PackageInfo info = packageManager.getPackageInfo( packageName, PackageManager.GET_PERMISSIONS );
            for( String permission : info.requestedPermissions )
            {
                list.add( permission );
            }
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
        }

        return list;
    }

    public Drawable getAppIcon( String packageName )
    {
        try
        {
            return packageManager.getApplicationIcon( packageName );
        }
        catch( NameNotFoundException e )
        {
            e.printStackTrace();
        }

        return null;
    }

    public int getStatusIcon( String permissionName )
    {
        switch ( permissionChecker.checkPermission( permissionName ) )
        {
            case HIGH:
                return R.drawable.alert_high_red;
            case MIDDLE:
                return R.drawable.alert_middle_yellow;
            case LOW:
                return R.drawable.alert_low_green;
        }

        return R.drawable.alert_low_green;
    }

    public int getStatusIcon( List<String> permissionList )
    {
        switch ( permissionChecker.checkPermissions( permissionList ) )
        {
            case HIGH:
                return R.drawable.alert_high_red;
            case MIDDLE:
                return R.drawable.alert_middle_yellow;
            case LOW:
                return R.drawable.alert_low_green;
        }

        return R.drawable.alert_low_green;
    }

    public String getApplicationName( String packageName )
    {
        try
        {
            int flags = 0; /*no flags*/
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo( packageName, flags );

            return (String) (applicationInfo != null ? packageManager.getApplicationLabel( applicationInfo ) 
                    : context.getString( R.string.unknown_app_name ));
        }
        catch( NameNotFoundException e )
        {
            e.printStackTrace();
        }

        return null;
    }
}
