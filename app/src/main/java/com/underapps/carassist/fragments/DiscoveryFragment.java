package com.underapps.carassist.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.underapps.carassist.R;

public class DiscoveryFragment extends Fragment {

    private static final String ARG_LAT = "lat";
    private static final String ARG_LNG = "lng";

    private String mLat;
    private String mLng;


    public static DiscoveryFragment newInstance(String lat, String lng) {
        DiscoveryFragment fragment = new DiscoveryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LAT, lat);
        args.putString(ARG_LNG, lng);
        fragment.setArguments(args);
        return fragment;
    }

    public static DiscoveryFragment newInstance() {
        DiscoveryFragment fragment = new DiscoveryFragment();
        return fragment;
    }

    public DiscoveryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLat = getArguments().getString(ARG_LAT);
            mLng = getArguments().getString(ARG_LNG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discovery, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
