
package com.apriorit.packageanalyzer.activities;

import java.util.List;

import com.apriorit.packageanalyzer.PackageListAdapter;
import com.apriorit.packageanalyzer.PackageScanner;
import com.apriorit.packageanalyzer.R;
import com.apriorit.packageanalyzer.ServiceTools;
import com.apriorit.packageanalyzer.services.ScannerService;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ToggleButton;

public class MainActivity extends Activity
{ 
    private ToggleButton  serviceButton = null;

    private ListView      lvPackageList = null;

    private PackageScanner packageScaner = null;

    private List<String>  packageList   = null;

    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        serviceButton = (ToggleButton) findViewById( R.id.tbServiceButton );
        lvPackageList = (ListView) findViewById( R.id.lvPackageList );

        packageScaner = new PackageScanner( this );

        new AsyncTask<Void, Void, List<String>>()
        {
            ProgressDialog progress = null;

            protected void onPreExecute()
            {
                progress = ProgressDialog.show( MainActivity.this, null, getString( R.string.list_loads ), true );
            }

            protected List<String> doInBackground( Void... params )
            {
                packageList = packageScaner.getInstalledPackagesList();
                return packageList;
            }

            protected void onPostExecute( List<String> result )
            {
                lvPackageList.setAdapter( new PackageListAdapter( MainActivity.this, packageList ) );

                progress.dismiss();
            }
        }.execute();

        lvPackageList.setOnItemClickListener( new OnItemClickListener()
        {
            public void onItemClick( AdapterView<?> parent, View view, int position, long id )
            {
                Intent intent = new Intent( MainActivity.this, PackageInfoActivity.class );
                intent.putExtra( PackageInfoActivity.PACKAGE_NAME_EXTRAS, packageList.get( position ) );

                MainActivity.this.startActivity( intent );
            }
        } );
        
        serviceButton.setOnClickListener( new OnClickListener()
        {
            public void onClick( View v )
            {
                ToggleButton button = (ToggleButton) v;
                
                Intent service = new Intent(MainActivity.this, ScannerService.class);
                if (button.isChecked())
                {
                    MainActivity.this.startService( service );
                }
                else
                {
                    MainActivity.this.stopService( service );
                }
            }
        } );
    }

    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate( R.menu.activity_main, menu );
        return true;
    }
        
    protected void onResume()
    {
        super.onResume();
        serviceButton.setChecked( ServiceTools.isServiceRunning( getApplicationContext(), ScannerService.class.getName() ) );        
    }
}
