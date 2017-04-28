package com.goomix.leninationapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.goomix.leninationapp.R;
import com.goomix.leninationapp.model.Statue;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FavoriteAdapter extends BaseAdapter {

    private List<Statue> favorite;
    private LayoutInflater inflater;
    private StatueAdapter statueAdapter;


    private static class ViewHolder {
        TextView description;
        TextView title;
        TextView coordinates;
        ImageView image;
    }

    public FavoriteAdapter(Context context, List<Statue> favorite) {
        this.favorite = favorite;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Statue statue = favorite.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_favorite, parent, false);
            viewHolder.title = (TextView)convertView.findViewById(R.id.value_statue_title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.value_statue_discription);
            viewHolder.coordinates = (TextView) convertView.findViewById(R.id.value_statue_coordinates);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.value_statue_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(statue.getTitle());
        viewHolder.description.setText(statue.getDescription());
        viewHolder.coordinates.setText("GPS: " + statue.getCoordinates());
        Picasso.with(inflater.getContext()).load(statue.getImage()).resize(50,50).into(viewHolder.image);

        if (TextUtils.isEmpty(statue.getTitle())) {
            viewHolder.title.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(statue.getDescription())) {
            viewHolder.description.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(statue.getCoordinates())) {
            viewHolder.coordinates.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(statue.getImage())) {
            viewHolder.image.setVisibility(View.GONE);
        }

        final ImageButton removeButton = (ImageButton)convertView.findViewById(R.id.removeButton);
        removeButton.setTag(statue);
        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                favorite.remove(removeButton.getTag());
                notifyDataSetChanged();
                statueAdapter.notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return favorite.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setStatueAdapter(StatueAdapter statueAdapter) {
        this.statueAdapter = statueAdapter;
    }
}
