package com.razon.whatthefact.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.razon.whatthefact.Utils.NetworkAvailable;
import com.razon.whatthefact.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoadingFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView msg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        initView(view);

        if (! new NetworkAvailable().isNetworkAvailable(getActivity())){
            progressBar.setVisibility(View.GONE);
            msg.setText("No Connection");
        }else {
            progressBar.setVisibility(View.VISIBLE);
            msg.setText("Please wait..");
        }

        return view;
    }

    public LoadingFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        msg = (TextView) view.findViewById(R.id.msg);
    }
}
