package com.goomix.leninationapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.goomix.leninationapp.adapter.FavoriteAdapter;
import com.goomix.leninationapp.adapter.StatueAdapter;
import com.goomix.leninationapp.model.Statue;

import java.util.List;

public class Favorite extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favorite_list, container, false);
        ListView statueListView = (ListView)rootView.findViewById(R.id.favoriteListView);
        MainActivity mainActivity = (MainActivity)getActivity();
        FavoriteAdapter favoriteAdapter = mainActivity.getFavoriteAdapter();
        statueListView.setAdapter(favoriteAdapter);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment fragment = getFragmentManager()
                .findFragmentById(R.id.favorite);
        if (fragment != null)
            getFragmentManager().beginTransaction().remove(fragment).commit();
    }
}
