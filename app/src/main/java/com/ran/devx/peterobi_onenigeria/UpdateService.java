package com.ran.devx.peterobi_onenigeria;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ran.devx.peterobi_onenigeria.objects.Merch;
import com.ran.devx.peterobi_onenigeria.objects.MerchXAdapter;
import com.ran.devx.peterobi_onenigeria.objects.Update;
import com.ran.devx.peterobi_onenigeria.objects.UpdateAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpdateService extends Service {
    public static Context ctx;
    public static Activity acx;
    public static Service srx;
    public static FirebaseFirestore db;
    public static String TAG = "DEBUGGING PETER OBI";
    public static ArrayList<Merch> merchesFromServer;
    public static ArrayList<Update> serverUpdates;
    public static MerchXAdapter merchAdapterUU;

    public static int adsCountdown = 0;

    public static UpdateAdapter updateAdapterUU;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final long ONE_DAY = 24 * 60 * 60 * 1000;
    private static final long ONE_HOUR = 60 * 60 * 1000;
    private static final long ONE_MINUTE = 60 * 1000;

    private static InterstitialAd mInterstitialAd;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ctx = getApplicationContext();
        db = FirebaseFirestore.getInstance();
        srx = this;


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        processAds();

        merchesFromServer = new ArrayList();
        serverUpdates = new ArrayList<>();
        merchAdapterUU = new MerchXAdapter(merchesFromServer, new MerchXAdapter.OnMerchXItemClickListener() {
            @Override
            public void onItemClick(Merch merchItem) {

            }
        });
        updateAdapterUU = new UpdateAdapter(serverUpdates, new UpdateAdapter.OnUpdateItemClickListener() {
            @Override
            public void onItemClick(Update updateItem) {

            }
        });


        getMerchandises();
        getUpdates();


        return super.onStartCommand(intent, flags, startId);
    }

    //    private void killService (){
//    }
    private void processAds() {

//        String testID = "ca-app-pub-3940256099942544/1033173712";
        String liveObiID = "ca-app-pub-4236283999178355/6958219383";

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,liveObiID, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Log.d(TAG, "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad dismissed fullscreen content.");
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.e(TAG, "Ad failed to show fullscreen content.");
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d(TAG, "Ad recorded an impression.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad showed fullscreen content.");
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });

    }

    public static void showAds(){
        if (mInterstitialAd != null) {
            mInterstitialAd.show(acx);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }

    public static void placeOrder(String item, String customer, int quantity, String phone, String city, MaterialButton btn, Activity orderReceipt) {
        btn.setText("Sending...");
        AlphaAnimation alphaTest = new AlphaAnimation(0.5f, 1.0f);
        alphaTest.setDuration(5000);
        alphaTest.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                orderReceipt.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Map<String, Object> order = new HashMap<>();
        order.put("item", item);
        order.put("customer_name", customer);
        order.put("quantity", quantity);
        order.put("phone", phone);
        order.put("city", city);


        db.collection("orders")
                .add(order)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        btn.setText("Sent!");
                        btn.startAnimation(alphaTest);
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public static void getMerchandises() {

        final String[] convertedUrltoByte = {""};

        db.collection("merchandise")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                convertedUrltoByte[0] = document.getData().get("item_art").toString();

                                StorageReference pathReference = FirebaseStorage.getInstance().getReference().child(convertedUrltoByte[0]);
                                pathReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        Merch merch = new Merch(
                                                document.getData().get("item_name").toString(),
                                                document.getData().get("item_info").toString(),
                                                document.getData().get("item_price").toString(),
                                                document.getData().get("item_link").toString(),
                                                document.getData().get("item_type").toString(),
                                                bytes);

                                        merchesFromServer.add(merch);
//                                        merchAdapterUU.notifyDataSetChanged();
                                        try {
                                            merchAdapterUU.notifyItemInserted(merchesFromServer.size());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Toast.makeText(ctx, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


//                                Log.d(TAG, document.getId() + "PETER => " + document.getData().get("item_name"));
                            }
                        } else {
                            Log.w(UpdateService.TAG, "Error getting documents.", task.getException());
                        }
                        if (task.isComplete()) {
                        }
                    }
                });

    }

    private static void getUpdates() {

        final String[] convertedUrltoByte = {""};

        db.collection("updates")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                convertedUrltoByte[0] = document.getData().get("update_art").toString();

                                StorageReference pathReference = FirebaseStorage.getInstance().getReference().child(convertedUrltoByte[0]);
                                pathReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {

                                        Update upate = new Update(
                                                document.getData().get("update_title").toString(),
                                                document.getData().get("update_info").toString(),
                                                getTimeString(document.getData().get("update_date").toString()),
                                                document.getData().get("update_url").toString(),
                                                bytes);

                                        serverUpdates.add(upate);
//                                        updateAdapterUU.notifyDataSetChanged();
                                        try {
                                            updateAdapterUU.notifyItemInserted(serverUpdates.size());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Toast.makeText(ctx, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


//                                Log.d(TAG, document.getId() + "PETER => " + document.getData().get("item_name"));
                            }
                        } else {
                            Log.w(UpdateService.TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    private static String getTimeString(String update_date) {

        String simplePeriod = update_date;

        Date moment;
        Date now = new Date();
        try {
            moment = (Date)formatter.parse(update_date);
            long diff = now.getTime() - moment.getTime();
            long days = diff / ONE_DAY;
            long hours = diff/ ONE_HOUR;
            long minutes = diff/ ONE_MINUTE;

            if (hours<1) {
                simplePeriod = minutes+" ago";
            }else if (days<1 && hours>1) {
                simplePeriod = String.valueOf(hours)+" hours ago";
            }else if (days<32 && days>1) {
                simplePeriod = String.valueOf(days);
            }else if (days>=32 && days<62) {
                simplePeriod = "Last month on "+update_date;
            }else if (days>62) {
                simplePeriod = update_date;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }



        return simplePeriod;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
