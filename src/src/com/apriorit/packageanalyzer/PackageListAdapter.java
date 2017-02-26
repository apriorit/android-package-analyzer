
package com.apriorit.packageanalyzer;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PackageListAdapter extends BaseAdapter
{
    private List<String>   packageList    = null;

    private LayoutInflater layoutInflater = null;

    private PackageScanner  packageScaner  = null;

    public PackageListAdapter( Context context, List<String> packageList )
    {
        layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        this.packageList = packageList;

        packageScaner = new PackageScanner( context );       
    }

    @Override
    public int getCount()
    {
        return packageList.size();
    }

    @Override
    public Object getItem( int position )
    {

        return packageList.get( position );
    }

    @Override
    public long getItemId( int position )
    {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        View view = convertView;
        String packageName = packageList.get( position );
        
        if ( view == null )
        {
            view = layoutInflater.inflate( R.layout.package_item_line, parent, false );
        }

        ImageView appImage = (ImageView) view.findViewById( R.id.ivAppImage );
        appImage.setImageDrawable( packageScaner.getAppIcon( packageName ) );

        ImageView statusImage = (ImageView) view.findViewById( R.id.ivStatusImage );
        statusImage.setImageResource( packageScaner.getStatusIcon( 
                packageScaner.getPermissions( packageName ) ) );        
        
        TextView tvAppName = (TextView) view.findViewById( R.id.tvName );
        tvAppName.setText( packageScaner.getApplicationName( packageName) );

        TextView tvPackageName = (TextView) view.findViewById( R.id.tvPackage );
        tvPackageName.setText( packageName );

        return view;
    }

}
