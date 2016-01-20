package com.example.fyang35.bmwapplication;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;


/**
 * Created by fyang35 on 1/18/16.
 */
public class LocationMapFragment extends Fragment implements OnMapReadyCallback{

    private SupportMapFragment mMap;
    private double mId;
    private String mName;
    private LatLng mLocation;
    private String mAddress;
    private String mArriveTime;

    private TextView mTextId,mTextName,mTextLocation,mTextAddress,mTextArriveTime;


    /* "ID": 2745,
            "Name": "Doughnut Vault Canal",
            "Latitude": 41.883976,
            "Longitude": -87.639346,
            "Address": "11 N Canal St L30 Chicago, IL 60606",
            "ArrivalTime": "2016-01-14T04:15:28.723"*/

    public static LocationMapFragment newInstance(double id, String name,
                                                  double latitude, double longitude,
                                                  String address, String arriveTime){
        LocationMapFragment fragment = new LocationMapFragment();
        Bundle args = new Bundle();
        args.putDouble("id", id);
        args.putString("name", name);
        args.putDouble("latitude", latitude);
        args.putDouble("longitude", longitude);
        args.putString("address", address);
        args.putString("arriveTime",arriveTime);
        fragment.setArguments(args);
        return fragment;
    }


    public LocationMapFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mId = getArguments().getDouble("id");
        mName = getArguments().getString("name");
        mLocation = new LatLng(getArguments().getDouble("latitude"),getArguments().getDouble("longitude"));
        mAddress = getArguments().getString("address");
        mArriveTime = getArguments().getString("arriveTime");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mTextId = (TextView) view.findViewById(R.id.txt_id);
        mTextId.setText("ID: " + mId);

        mTextName = (TextView) view.findViewById(R.id.txt_name);
        mTextName.setText("Name: "+mName);

        mTextLocation = (TextView) view.findViewById(R.id.txt_coordinate);
        mTextLocation.setText("Coordinates: "+mLocation.latitude+ "," +mLocation.longitude);

        mTextAddress = (TextView) view.findViewById(R.id.txt_address);
        mTextAddress.setText("Address: "+mAddress);

        mTextArriveTime = (TextView) view.findViewById(R.id.txt_arriveTime);
        mTextArriveTime.setText("Arrive Time: "+mArriveTime);

        mMap = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mMap.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(mLocation).title(mName));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(mLocation));
    }
}
