
package com.apriorit.packageanalyzer;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ComponentListAdapter extends BaseAdapter
{
    private List<String>   list           = null;

    private LayoutInflater layoutInflater = null;

    public ComponentListAdapter( Context context, List<String> list )
    {
        layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        this.list = list;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem( int position )
    {

        return list.get( position );
    }

    @Override
    public long getItemId( int position )
    {
        return position;
    }

    public View getView( int position, View convertView, ViewGroup parent )
    {
        View view = convertView;
        String componentName = list.get( position );

        if ( view == null )
        {
            view = layoutInflater.inflate( R.layout.package_item_line, parent, false );
        }

        TextView tvAppName = (TextView) view.findViewById( R.id.tvName );
        tvAppName.setText( parseName( componentName ) );
        tvAppName.setTextColor( Color.BLACK );

        TextView tvPackageName = (TextView) view.findViewById( R.id.tvPackage );
        tvPackageName.setText( parsePackage( componentName ) );
        tvPackageName.setTextColor( Color.BLACK );

        ImageView imageLeft = (ImageView) view.findViewById( R.id.ivAppImage );
        imageLeft.setVisibility( View.GONE );

        ImageView imageRight = (ImageView) view.findViewById( R.id.ivStatusImage );
        imageRight.setVisibility( View.GONE );

        return view;
    }

    private String parsePackage( String componentName )
    {
        return componentName.substring( 0, componentName.lastIndexOf( "." ) );
    }

    private String parseName( String componentName )
    {
        return componentName.substring( componentName.lastIndexOf( "." ) + 1, componentName.length() );
    }
}
