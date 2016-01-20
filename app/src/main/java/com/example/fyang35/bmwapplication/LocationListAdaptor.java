package com.example.fyang35.bmwapplication;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by fyang35 on 1/14/16.
 */
public class LocationListAdaptor extends RecyclerView.Adapter<LocationListAdaptor.ViewHolder> {

    private Activity mActivity;
    private JSONArray mItemArray;

    public LocationListAdaptor(Activity activity,JSONArray dataInput) {
        mActivity = activity;
        mItemArray = dataInput;
    }

    @Override
    public int getItemCount() {
        return mItemArray.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mItemId;
        public TextView mItemName;
        public TextView mItemAddress;
        public ViewHolder(View itemView) {
            super(itemView);
            mItemId = (TextView) itemView.findViewById(R.id.item_ID);
            mItemName = (TextView) itemView.findViewById(R.id.item_NAME);
            mItemAddress = (TextView) itemView.findViewById(R.id.item_Address);
        }
    }

    @Override
    public LocationListAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location_item, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        try{

             /*
    * "ID": 2745,
    "Name": "Doughnut Vault Canal",
    "Latitude": 41.883976,
    "Longitude": -87.639346,
    "Address": "11 N Canal St L30 Chicago, IL 60606",
    "ArrivalTime": "2016-01-14T04:15:28.723"*/

            JSONObject jsonObject = mItemArray.getJSONObject(position);

            final double id = jsonObject.getDouble("ID");
            final String name = jsonObject.getString("Name");
            final double latitude = jsonObject.getDouble("Latitude");
            final double longitude = jsonObject.getDouble("Longitude");
            final String address = jsonObject.getString("Address");
            final String arriveTime = jsonObject.getString("ArrivalTime");

            holder.mItemId.setText(String.valueOf(id));
            holder.mItemName.setText(name);
            holder.mItemAddress.setText(address);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("JimmyTest", "clicked on " + holder.mItemName.getText());
                    android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity)mActivity).getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    LocationMapFragment locationMap = LocationMapFragment.newInstance(id,name,latitude,longitude,address,arriveTime);
                    fragmentTransaction.replace(R.id.maincontainer, locationMap).addToBackStack(null).commit();
                }
            });
        }
        catch( Exception e) {
            e.printStackTrace();
        }

    }

}
