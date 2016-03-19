package com.videtechs.mobile.utick;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class EventsAdapter extends ArrayAdapter<Events> {

    Context context;
    int layoutResId;
    Events data[] = null;

    public EventsAdapter(Context context, int layoutResId, Events[] data) {
        super(context, layoutResId, data);
        this.layoutResId = layoutResId;
        this.context = context;
        this.data = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventsHolder holder = null;

        if(convertView == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResId, parent, false);

            holder = new EventsHolder();
            holder.txtId = (TextView)convertView.findViewById(R.id.event_id);
            holder.txtTitle = (TextView)convertView.findViewById(R.id.event_title);
            holder.txtPrice = (TextView)convertView.findViewById(R.id.event_price);
            holder.txtDate = (TextView)convertView.findViewById(R.id.event_date);
            holder.txtVenue = (TextView)convertView.findViewById(R.id.event_venue);
            holder.imgImage = (ImageView)convertView.findViewById(R.id.event_image);

            convertView.setTag(holder);
        }
        else
        {
            holder = (EventsHolder)convertView.getTag();
        }

        Events event = data[position];
        holder.txtId.setText(event.voId);
        holder.txtTitle.setText(event.voTitle);
        holder.txtPrice.setText(event.voPrice);
        holder.txtDate.setText(event.voDate);
        holder.txtVenue.setText(event.voVenue);

        Picasso.with(this.context)
                .load(event.voImage)
                .placeholder(R.drawable.event)
                .error(R.drawable.event)
                .into(holder.imgImage);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.event_image);
        imageView.setTag(event.voId);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent canDet = new Intent(context, DetailsActivity.class);
                final String eventid = view.getTag().toString();
                DBAdapter db = new DBAdapter(context);
                db.open();
                db.editNaviEvent(1, eventid);
                db.close();
                context.startActivity(canDet);
            }
        });

        return convertView;
    }

    static class EventsHolder
    {
        TextView txtId;
        TextView txtTitle;
        TextView txtPrice;
        TextView txtDate;
        TextView txtVenue;
        ImageView imgImage;
    }
}
