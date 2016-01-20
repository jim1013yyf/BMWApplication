package com.example.fyang35.bmwapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.Handler;

/**
 * Created by fyang35 on 1/13/16.
 */
public class LocationListFragment extends Fragment {

    private JSONArray jsar= new JSONArray();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public LocationListFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread thread1 =  new Thread(new Runnable() {
            @Override
            public void run() {

                StringBuilder result = new StringBuilder();
                HttpURLConnection urlConnection;

                try {
                    URL url = new URL("http://localsearch.azurewebsites.net/api/Locations/");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                        Log.v("JimmyTest", line);
                    }

                    urlConnection.disconnect();


                    jsar = new JSONArray(result.toString());

                    //Log.v("JimmyTest", "size = " + jsar.length());
                    for(int i = 0; i<jsar.length(); i++){
                        JSONObject curOBJ = jsar.getJSONObject(i);

                    }

                }catch( Exception e) {
                    e.printStackTrace();
                }


            }
        });

        try{
            thread1.start();
            thread1.join();
        }catch (Exception e){

        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.item_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getContext(),1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new LocationListAdaptor(getActivity(),jsar);
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }
}
