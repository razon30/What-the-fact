package com.razon.whatthefact.rest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.razon.whatthefact.MainActivity;
import com.razon.whatthefact.R;
import com.razon.whatthefact.Response;
import com.razon.whatthefact.Utils.NetworkAvailable;
import com.razon.whatthefact.Utils.ShowNetworkError;
import com.razon.whatthefact.Utils.URLEndPoints;
import com.razon.whatthefact.fragments.ListFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by HP on 04-Nov-17.
 */

public class ParseData {

    //public static ArrayList<Response> responseList;

    public ParseData(String type, Activity activity, int containerLayoutID, String number, String year){

        Observable<List<Response>> responseObservable;
      //  MainActivity.responseList = new ArrayList<Response>();

        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please Wait...");
        if (type.equals("1")){
            progressDialog.show();
        }

        final int[] reTry = {0};

        //  ProgressDialog progressDialog = new ProgressDialog(this, )

        Observable<Response> observable1 = new Parser(number, URLEndPoints.TRIVIA).getResponse();
        Observable<Response> observable2 = new Parser(year, URLEndPoints.DATE).getResponse();
        Observable<Response> observable3 = new Parser(number, URLEndPoints.MATH).getResponse();
        Observable<Response> observable4 = new Parser(number, URLEndPoints.YEAR).getResponse();

        List<Observable<Response>> observableList1 = new ArrayList<Observable<Response>>();
        observableList1.add(observable1);
        observableList1.add(observable3);
        observableList1.add(observable2);
        observableList1.add(observable4);

        MainActivity.responseList.clear();

        responseObservable = Observable.zip(observableList1, (restaurant) -> {

            for (int i = 0; i < restaurant.length; i++) {
                MainActivity.responseList.add((Response) restaurant[i]);
            }

            return MainActivity.responseList;
        });


        responseObservable.subscribe(new Observer<List<Response>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Response> orderValues) {

//                for (int i = 0; i < orderValues.size(); i++) {
//                    Toast.makeText(MainActivity.this, orderValues.get(i).getText(), Toast.LENGTH_LONG).show();
//                }

            }

            @Override
            public void onError(Throwable e) {

                if (reTry[0] <3 && new NetworkAvailable().isNetworkAvailable(activity)){
                    Toast.makeText(activity, "Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    responseObservable.retry();
                    reTry[0]++;
                }else {
                    new ShowNetworkError(activity);
                }

            }

            @Override
            public void onComplete() {
                if (type.equals("1")){
                    progressDialog.dismiss();
                }
                ListFragment fragment = new ListFragment();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.exit_to_left);
                transaction.replace(containerLayoutID, fragment);
                transaction.commit();

            }
        });



    }

}
