package com.razon.whatthefact;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.balysv.materialripple.MaterialRippleLayout;
import com.codemybrainsout.ratingdialog.RatingDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razon.whatthefact.Utils.SharePreferenceSingleton;
import com.razon.whatthefact.fragments.LoadingFragment;
import com.razon.whatthefact.fragments.UserNumberFragment;
import com.razon.whatthefact.rest.ParseData;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;

    SlidingRootNavBuilder slidingRootNavBuilder;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    public static InterstitialAd mInterstitialAd;
    private AdView mAdView;

    //    Observable<List<Response>> responseObservable;
    public static ArrayList<Response> responseList;
    private MaterialRippleLayout yourFact;
    private MaterialRippleLayout factOfTheDay;
    private MaterialRippleLayout dev;
    private MaterialRippleLayout rateTheApp;
    int i = 0;
//
//    public static String number, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        populateData();
        worksOnFAB();
        worksOnNav();
        worksOnAds();
        showCustomRateMeDialogClick1();
        //  parseData();

    }

    private void worksOnAds() {

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                mAdView.setVisibility(View.GONE);
            }
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

    }

    public void showInterstitialAd() {
        if (mInterstitialAd.isLoaded()) {
            int count = SharePreferenceSingleton.getInstance(MainActivity.this).getInt("count");
            if (count%2==0){
                mInterstitialAd.show();
            }else {

                count++;
                SharePreferenceSingleton.getInstance(MainActivity.this).saveInt("count",count);

            }


        } else {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }
    }

    private void populateData() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("d MMM, EEE, ''yy");
        SimpleDateFormat mdformat1 = new SimpleDateFormat("d");
        SimpleDateFormat mdformat2 = new SimpleDateFormat("d/m");
        String strDate = mdformat.format(calendar.getTime());
        String number = mdformat1.format(calendar.getTime());
        String year = mdformat2.format(calendar.getTime());
        collapsingToolbarLayout.setTitle(strDate);

        new ParseData("0", this, R.id.fragment_container, number, year);

    }

    private void worksOnNav() {

        slidingRootNavBuilder = new SlidingRootNavBuilder(this);
        slidingRootNavBuilder.withToolbarMenuToggle(toolbar)
                .withMenuLayout(R.layout.darwer_layout)
                .withDragDistance(140) //Horizontal translation of a view. Default == 180dp
                .withRootViewScale(0.7f) //Content view's scale will be interpolated between 1f and 0.7f. Default == 0.65f;
                .withRootViewElevation(10) //Content view's elevation will be interpolated between 0 and 10dp. Default == 8.
                .withRootViewYTranslation(4) //Cont
                .withGravity(SlideGravity.LEFT)
                .inject();

        yourFact = findViewById(R.id.your_fact);
        factOfTheDay = findViewById(R.id.fact_of_the_day);
        dev = findViewById(R.id.dev);
        rateTheApp = findViewById(R.id.rateTheApp);

        yourFact.setOnClickListener(v->{
            gotoUserInput();
        });

        factOfTheDay.setOnClickListener(v->{
            populateData();
        });

        rateTheApp.setOnClickListener(v->{
            showCustomRateMeDialogClick();
        });

        dev.setOnClickListener(v->
        {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/developer?id=Md.+Razon+Hossain")));

        });

    }

    private void showCustomRateMeDialogClick() {

        final RatingDialog ratingDialog = new RatingDialog.Builder(this)
                .icon(getResources().getDrawable(R.drawable.logo))
                //   .session(7)
                .threshold(3)
                .ratingBarBackgroundColor(R.color.backgroundColor)
                .title("How was your experience with us?")
                .titleTextColor(R.color.backgroundColor)
                .positiveButtonText("Not Now")
                .negativeButtonText("Never")
                .positiveButtonTextColor(R.color.colorPrimary)
                .negativeButtonTextColor(R.color.grey_500)
                .ratingBarColor(R.color.colorPrimary)
                .playstoreUrl("market://details?id=com.razon.whatthefact")
                .formTitle("Submit Feedback")
                .formHint("Tell us where we can improve")
                .formSubmitText("Submit")
                .formCancelText("Cancel")
                .ratingBarColor(R.color.accent)
                .playstoreUrl("market://details?id=razon.nctballbooksfree")
                .onRatingChanged((rating, thresholdCleared) -> {

                })
                .onRatingBarFormSumbit(feedback -> {

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("feedback");
                    databaseReference.push().setValue(feedback);

                }).build();
        ratingDialog.show();

    }

    private void showCustomRateMeDialogClick1() {

        final RatingDialog ratingDialog = new RatingDialog.Builder(this)
                .icon(getResources().getDrawable(R.drawable.logo))
                .session(7)
                .threshold(3)
                .ratingBarBackgroundColor(R.color.backgroundColor)
                .title("How was your experience with us?")
                .titleTextColor(R.color.backgroundColor)
                .positiveButtonText("Not Now")
                .negativeButtonText("Never")
                .positiveButtonTextColor(R.color.colorPrimary)
                .negativeButtonTextColor(R.color.grey_500)
                .ratingBarColor(R.color.colorPrimary)
                .playstoreUrl("market://details?id=com.razon.whatthefact")
                .formTitle("Submit Feedback")
                .formHint("Tell us where we can improve")
                .formSubmitText("Submit")
                .formCancelText("Cancel")
                .ratingBarColor(R.color.accent)
                .playstoreUrl("market://details?id=razon.nctballbooksfree")
                .onRatingChanged((rating, thresholdCleared) -> {

                })
                .onRatingBarFormSumbit(feedback -> {

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("feedback");
                    databaseReference.push().setValue(feedback);

                }).build();

        ratingDialog.show();

    }




//    private void parseData() {
//
//
//        final int[] reTry = {0};
//
//      //  ProgressDialog progressDialog = new ProgressDialog(this, )
//
//        Observable<Response> observable1 = new Parser(number, URLEndPoints.TRIVIA).getResponse();
//        Observable<Response> observable2 = new Parser(year, URLEndPoints.DATE).getResponse();
//        Observable<Response> observable3 = new Parser(number, URLEndPoints.MATH).getResponse();
//        Observable<Response> observable4 = new Parser(number, URLEndPoints.YEAR).getResponse();
//
//        List<Observable<Response>> observableList1 = new ArrayList<Observable<Response>>();
//        observableList1.add(observable1);
//        observableList1.add(observable3);
//        observableList1.add(observable2);
//        observableList1.add(observable4);
//
//        responseObservable = Observable.zip(observableList1, (restaurant) -> {
//
//            for (int i = 0; i < restaurant.length; i++) {
//                responseList.add((Response) restaurant[i]);
//            }
//
//            return responseList;
//        });
//
//
//        responseObservable.subscribe(new Observer<List<Response>>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(List<Response> orderValues) {
//
////                for (int i = 0; i < orderValues.size(); i++) {
////                    Toast.makeText(MainActivity.this, orderValues.get(i).getText(), Toast.LENGTH_LONG).show();
////                }
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//
//
//                if (reTry[0] <3 && new NetworkAvailable().isNetworkAvailable(MainActivity.this)){
//                    Toast.makeText(MainActivity.this, "Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                    responseObservable.retry();
//                    reTry[0]++;
//                }else {
//                    new ShowNetworkError(MainActivity.this);
//                }
//
//            }
//
//            @Override
//            public void onComplete() {
//                ListFragment fragment = new ListFragment();
//                fragmentTransaction(fragment);
//               // getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new ListFragment()).commit();
//              //  Toast.makeText(MainActivity.this, "Completed", Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//
//    }
//
//    public void fragmentTransaction(Fragment fragment){
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.exit_to_left);
//        transaction.replace(R.id.fragment_container, fragment);
//        transaction.commit();
//
//    }

    private void init() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        responseList = new ArrayList<Response>();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new LoadingFragment()).commit();

        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        Typeface font = Typeface.createFromAsset(getAssets(), "font/StalinistOne-Regular.ttf");
        collapsingToolbarLayout.setExpandedTitleTypeface(font);
        collapsingToolbarLayout.setCollapsedTitleTypeface(font);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void worksOnFAB() {


        fab.setOnClickListener(view -> {

            gotoUserInput();

        });

    }

    private void gotoUserInput() {

        UserNumberFragment fragment = new UserNumberFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        if (i==0){
            showInterstitialAd();
            i=1;
            super.onBackPressed();
        }else super.onBackPressed();


    }
}
