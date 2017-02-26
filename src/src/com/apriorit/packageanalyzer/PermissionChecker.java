
package com.apriorit.packageanalyzer;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;

public class PermissionChecker
{
    private List<String> highDangerousPermissions   = null;

    private List<String> middleDangerousPermissions = null;

    public PermissionChecker()
    {
        highDangerousPermissions = new ArrayList<String>( getDefaultHighPermissions() );
        middleDangerousPermissions = new ArrayList<String>( getDefaultMiddlePermissions() );
    }

    public AlertStatus checkPermission( String permission )
    {
        if ( permission == null || permission.equals( "" ) )
            return AlertStatus.LOW;

        if ( highDangerousPermissions.contains( permission ) )
        {
            return AlertStatus.HIGH;
        }
        else if ( middleDangerousPermissions.contains( permission ) )
        {
            return AlertStatus.MIDDLE;
        }

        return AlertStatus.LOW;
    }

    public AlertStatus checkPermissions( List<String> permissionList )
    {
        AlertStatus status = AlertStatus.LOW;

        for( String permission : permissionList )
        {
            if ( checkPermission( permission ) == AlertStatus.HIGH )
            {
                status = AlertStatus.HIGH;
                break;
            }
            else if ( checkPermission( permission ) == AlertStatus.MIDDLE )
            {
                status = AlertStatus.MIDDLE;
            }
        }

        return status;
    }

    public void setDefaultMiddlePermissions( List<String> permissions )
    {
        if ( permissions == null )
        {
            return;
        }

        middleDangerousPermissions.clear();
        middleDangerousPermissions.addAll( permissions );
    }

    public void setDefaultHighPermissions( List<String> permissions )
    {
        if ( permissions == null )
        {
            return;
        }

        highDangerousPermissions.clear();
        highDangerousPermissions.addAll( permissions );
    }

    private List<String> getDefaultHighPermissions()
    {
        List<String> permissions = new ArrayList<String>();

        permissions.add( Manifest.permission.GET_ACCOUNTS );
        permissions.add( Manifest.permission.INSTALL_PACKAGES );
        permissions.add( Manifest.permission.KILL_BACKGROUND_PROCESSES );
        permissions.add( Manifest.permission.CALL_PHONE );
        permissions.add( Manifest.permission.CALL_PRIVILEGED );
        permissions.add( Manifest.permission.SEND_SMS );
        permissions.add( Manifest.permission.READ_SMS );
        permissions.add( Manifest.permission.RECEIVE_MMS );
        permissions.add( Manifest.permission.RECEIVE_SMS );
        permissions.add( Manifest.permission.READ_CONTACTS );
        permissions.add( Manifest.permission.READ_PROFILE );
        permissions.add( Manifest.permission.WRITE_CONTACTS );
        permissions.add( Manifest.permission.WRITE_PROFILE );
        permissions.add( Manifest.permission.PROCESS_OUTGOING_CALLS );
        permissions.add( Manifest.permission.WRITE_SETTINGS );
        permissions.add( Manifest.permission.WRITE_SECURE_SETTINGS );

        return permissions;
    }

    private List<String> getDefaultMiddlePermissions()
    {
        List<String> permissions = new ArrayList<String>();

        permissions.add( Manifest.permission.READ_HISTORY_BOOKMARKS );
        permissions.add( Manifest.permission.RECEIVE_BOOT_COMPLETED );
        permissions.add( Manifest.permission.ACCESS_COARSE_LOCATION );
        permissions.add( Manifest.permission.ACCESS_FINE_LOCATION );
        permissions.add( Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS );

        return permissions;
    }
}
