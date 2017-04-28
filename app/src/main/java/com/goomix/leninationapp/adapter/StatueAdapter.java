package com.goomix.leninationapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.goomix.leninationapp.R;
import com.goomix.leninationapp.model.Statue;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StatueAdapter extends BaseAdapter implements Filterable {

    private List<Statue> originStatues;
    private List<Statue> statues;
    private List<Statue> favorite;
    LayoutInflater inflater;
    FavoriteAdapter favoriteAdapter;


    private static class ViewHolder {
        TextView description;
        TextView title;
        TextView coordinates;
        ImageView image;
    }

    public StatueAdapter(Context context, List<Statue> statues, List<Statue> favorite, FavoriteAdapter favoriteAdapter) {
        this.statues = statues;
        this.favorite = favorite;
        this.favoriteAdapter = favoriteAdapter;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Statue statue = statues.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_statues, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.value_statue_title);
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

        CheckBox statueCheck = (CheckBox)convertView.findViewById(R.id.statueCheck);
        statueCheck.setChecked(isInFavorite(statue));
        statueCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Statue st = (Statue)cb.getTag();
                if (cb.isChecked()) {
                    favorite.add(st);
                } else {
                    favorite.remove(st);
                }
                favoriteAdapter.notifyDataSetChanged();
            }
        });
        statueCheck.setTag(statue);

        return convertView;
    }

    @Override
    public Filter getFilter() {
            Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {
                statues = (List<Statue>)results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Statue> filteredStatues = new ArrayList<>();

                if (originStatues == null) {
                    originStatues = new ArrayList<>(statues);
                }

                if (constraint == null || constraint.length() == 0) {
                    results.count = originStatues.size();
                    results.values = originStatues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < originStatues.size(); i++) {
                        Statue data = originStatues.get(i);
                        if (data.getTitle().toLowerCase().contains(constraint.toString()) ||
                            data.getDescription().toLowerCase().contains(constraint.toString())) {
                            filteredStatues.add(data);
                        }
                    }
                    results.count = filteredStatues.size();
                    results.values = filteredStatues;
                }
                return results;
            }
        };
        return filter;
    }

    private boolean isInFavorite(Statue statue) {
        if (favorite.contains(statue)) {
            return true;
        }
        return false;
    }


    @Override
    public int getCount() {
        return statues.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setFavorite(List<Statue> favorite) {
        this.favorite = favorite;
    }

    public void setFavoriteAdapter(FavoriteAdapter favoriteAdapter) {
        this.favoriteAdapter = favoriteAdapter;
    }


}