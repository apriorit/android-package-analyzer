
package com.apriorit.packageanalyzer.activities;

import java.util.List;

import com.apriorit.packageanalyzer.ComponentListAdapter;
import com.apriorit.packageanalyzer.PackageScanner;
import com.apriorit.packageanalyzer.PermissionListAdapter;
import com.apriorit.packageanalyzer.R;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class PackageInfoActivity extends Activity implements OnClickListener, OnItemClickListener
{
    public static final String PACKAGE_NAME_EXTRAS = "package_name";

    private enum PackageResources
    {
        ACTIVITY, SERVICE, RECEIVER, PERMISSION
    };

    private PackageScanner    scaner              = null;
    private Button           btnActivities       = null;
    private Button           btnServices         = null;
    private Button           btnReceivers        = null;
    private Button           btnPermissions      = null;
    private ListView         packageInfoListView = null;

    private PackageResources type                = PackageResources.ACTIVITY;
    private String           packageName;
    private List<String>     list                = null;

    private Handler          handler             = null;
    private String           title;

    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.package_info );
        
        scaner = new PackageScanner( this );
        
        packageInfoListView = (ListView) findViewById( R.id.listView );
        btnActivities = (Button) findViewById( R.id.btnActivity );          
        btnServices = (Button) findViewById( R.id.btnService );
        btnReceivers = (Button) findViewById( R.id.btnReceiver );
        btnPermissions = (Button) findViewById( R.id.btnPermission );

        btnActivities.setOnClickListener( this );
        btnServices.setOnClickListener( this );
        btnReceivers.setOnClickListener( this );
        btnPermissions.setOnClickListener( this );

        packageInfoListView.setOnItemClickListener( this );

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        packageName = extras.getString( PACKAGE_NAME_EXTRAS );

        setTitle( scaner.getApplicationName( packageName ) + ": " + getString( R.string.button_activities ) );

        handler = new Handler()
        {
            public void handleMessage( Message msg )
            {
                super.handleMessage( msg );

                BaseAdapter adapter = null;

                if ( type == PackageResources.PERMISSION )
                {
                    adapter = new PermissionListAdapter( getApplicationContext(), list );
                }
                else
                {
                    adapter = new ComponentListAdapter( getApplicationContext(), list );
                }
                
                packageInfoListView.setAdapter( adapter );
                setTitle( title );
            }
        };

        onClick( btnActivities );
    }

    public void onClick( View v )
    {
        final Button source = (Button) v;
        if ( list != null )
        {
            list.clear();
        }

        new Thread()
        {
            public void run()
            {
                switch ( source.getId() )
                {
                    case R.id.btnActivity:
                        type = PackageResources.ACTIVITY;
                        list = scaner.getActivities( packageName );
                        title = scaner.getApplicationName( packageName ) 
                                + ": " + getString( R.string.button_activities );
                        break;

                    case R.id.btnService:
                        type = PackageResources.SERVICE;
                        list = scaner.getServices( packageName );
                        title = scaner.getApplicationName( packageName ) 
                                + ": " + getString( R.string.button_services );
                        break;

                    case R.id.btnReceiver:
                        type = PackageResources.RECEIVER;
                        list = scaner.getReceivers( packageName );
                        title = scaner.getApplicationName( packageName ) 
                                + ": " + getString( R.string.button_receivers );
                        break;

                    case R.id.btnPermission:
                        type = PackageResources.PERMISSION;
                        list = scaner.getPermissions( packageName );
                        title = scaner.getApplicationName( packageName ) 
                                + ": " + getString( R.string.button_permission );
                        break;
                }
                handler.sendEmptyMessage( RESULT_OK );
            }
        }.start();
    }

    public void onItemClick( AdapterView<?> parent, View viev, int position, long id )
    {
        try
        {
            if ( type == PackageResources.ACTIVITY )
            {
                Intent intent = new Intent();
                intent.setComponent( new ComponentName( packageName, list.get( position ) ) );
                this.startActivity( intent );
            }
        }
        catch( ActivityNotFoundException ex )
        {
            Toast.makeText( this, getString( R.string.error_activity_not_found ), Toast.LENGTH_LONG ).show();
        }
        catch( Exception ex )
        {
            Toast.makeText( this, getString( R.string.error ), Toast.LENGTH_LONG ).show();
        }
    }
}
