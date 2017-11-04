package com.razon.whatthefact.rest;

import com.razon.whatthefact.Response;
import com.razon.whatthefact.Utils.URLEndPoints;
import com.razon.whatthefact.rest.FactClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HP on 27-Oct-17.
 */

public class Parser {


   FactClient factClient;
    Observable<Response> observable;

    public Parser(String number, String type) {

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(URLEndPoints.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        Retrofit retrofit = builder.client(okBuilder.build()).build();

        observable = retrofit
                .create(FactClient.class)
                .getResponse(number,type)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

       // factClient = retrofit.create(FactClient.class);
       // responseCall = factClient.getResponse(number, type);
    }


    public Observable<Response> getResponse(){
        return observable;
    }


}
