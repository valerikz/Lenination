package com.goomix.leninationapp;
import android.os.Bundle;

import android.widget.ListView;
import android.support.v7.app.AppCompatActivity;

import com.goomix.leninationapp.adapter.FavoriteAdapter;
import com.goomix.leninationapp.adapter.StatueAdapter;
import com.goomix.leninationapp.client.StatueRestClient;
import com.goomix.leninationapp.model.Statue;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import android.support.v7.widget.Toolbar;
import com.goomix.leninationapp.TabLayout.Pager;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private List<Statue> statues = new ArrayList<>();
    private List<Statue> favorite = new ArrayList<>();
    private FavoriteAdapter favoriteAdapter;
    private StatueAdapter statueAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiveStatues();
    }

    private void initTabs() {
        //Adding toolbar to the activity
        MainActivity mainActivity = MainActivity.this;
        mainActivity.setContentView(R.layout.activity_main);

        favoriteAdapter = new FavoriteAdapter(this, favorite);
        statueAdapter = new StatueAdapter(this, statues, favorite, favoriteAdapter);
        favoriteAdapter.setStatueAdapter(statueAdapter);

        Toolbar toolbar = (Toolbar) mainActivity.findViewById(R.id.toolbar);
        mainActivity.setSupportActionBar(toolbar);

        //Initializing the tablayout
        tabLayout = (TabLayout) mainActivity.findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Карта"));
        tabLayout.addTab(tabLayout.newTab().setText("Список"));
        tabLayout.addTab(tabLayout.newTab().setText("Избранные"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) mainActivity.findViewById(R.id.pager);

        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        tabLayout.setOnTabSelectedListener(this);

    }

    private void receiveStatues() {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Accept", "application/json"));

        StatueRestClient.get(MainActivity.this, "", headers.toArray(new Header[headers.size()]),
            null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                    try {
                        JSONArray jsonStatues = jsonObject.getJSONArray("statues");
                        for (int i = 0; i < jsonStatues.length(); i++) {
                            AsyncHttpClient.log.w("JsonHttpRH", jsonStatues.getJSONObject(i).toString());
                            Statue statue = new Statue(jsonStatues.getJSONObject(i));
                            statues.add(statue);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    initTabs();
                }
            });
    }

    public List<Statue> getStatues() {
        return statues;
    }

    public List<Statue> getFavorite() {
        return favorite;
    }

    public FavoriteAdapter getFavoriteAdapter() {
        return favoriteAdapter;
    }

    public void setFavoriteAdapter(FavoriteAdapter favoriteAdapter) {
        this.favoriteAdapter = favoriteAdapter;
    }

    public StatueAdapter getStatueAdapter() {
        return statueAdapter;
    }

    public void setStatueAdapter(StatueAdapter statueAdapter) {
        this.statueAdapter = statueAdapter;
    }
}
