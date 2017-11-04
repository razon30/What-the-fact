package com.razon.whatthefact.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razon.whatthefact.R;
import com.razon.whatthefact.rest.ParseData;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserNumberFragment extends Fragment implements View.OnClickListener {


    private EditText number;
    private Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_number, container, false);
        initView(view);
        return view;
    }

    public UserNumberFragment() {
        // Required empty public constructor
    }

    private void initView(View view) {
        number = (EditText) view.findViewById(R.id.number);
        submit = (Button) view.findViewById(R.id.submit);

        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String numberString = number.getText().toString().trim();
        if (TextUtils.isEmpty(numberString)) {
            Toast.makeText(getContext(), "Enter Number..", Toast.LENGTH_SHORT).show();
            return;
        }else {
            submit.setEnabled(false);
            new ParseData("1",getActivity(), R.id.fragment_container, numberString, numberString);
        }

        // TODO validate success, do something


    }
}
