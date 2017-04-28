package com.goomix.leninationapp;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.goomix.leninationapp.adapter.FavoriteAdapter;
import com.goomix.leninationapp.adapter.StatueAdapter;
import com.goomix.leninationapp.model.Statue;

import java.util.List;
import java.util.Locale;


public class StatueList extends Fragment {

    private EditText editsearch;
    private StatueAdapter statueAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.statue_list, container, false);
        ListView statueListView = (ListView)rootView.findViewById(R.id.statueListView);
        MainActivity mainActivity = (MainActivity)getActivity();
        statueAdapter = mainActivity.getStatueAdapter();
        statueListView.setAdapter(statueAdapter);
        editsearch = (EditText) rootView.findViewById(R.id.inputSearch);
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                statueAdapter.getFilter().filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment fragment = getFragmentManager()
                .findFragmentById(R.id.statueList);
        if (fragment != null)
            getFragmentManager().beginTransaction().remove(fragment).commit();
    }

}
