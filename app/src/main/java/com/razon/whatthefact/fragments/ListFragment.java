package com.razon.whatthefact.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.razon.whatthefact.AdapterFact;
import com.razon.whatthefact.MainActivity;
import com.razon.whatthefact.Response;
import com.razon.whatthefact.Utils.MyRecyclerView;
import com.razon.whatthefact.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    MyRecyclerView recyclerView;
    AdapterFact adapter;
    LinearLayoutManager layoutManager;
 //   ArrayList<Response> responseList;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.fact_recycler);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new AdapterFact(getActivity(), MainActivity.responseList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
