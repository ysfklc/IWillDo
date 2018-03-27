package com.ysfaklc.iwilldo;

import android.app.Activity;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yusuf on 24.03.2018.
 */

public class CustomAdapter extends ArrayAdapter<Item>  {
    public CustomAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textvievem = (TextView) convertView.findViewById(R.id.info);
            viewHolder.imageitem = (ImageButton) convertView.findViewById(R.id.edit);
            viewHolder.imageitem2 = (ImageButton) convertView.findViewById(R.id.del);
            viewHolder.imageitem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ((ListView) parent).performItemClick(v, position, 0);
                }
            });
            viewHolder.imageitem2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ((ListView) parent).performItemClick(v, position, 0);
                }
            });

            convertView.setTag(viewHolder);
        }

        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Item i1 = getItem(position);
        if(i1!=null) {
            viewHolder.textvievem.setText(i1.getVeri());
        }
        return convertView;
    }

}
