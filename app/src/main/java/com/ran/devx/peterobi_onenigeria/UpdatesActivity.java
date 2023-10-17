package com.ran.devx.peterobi_onenigeria;

import static com.ran.devx.peterobi_onenigeria.UpdateService.acx;
import static com.ran.devx.peterobi_onenigeria.UpdateService.ctx;
import static com.ran.devx.peterobi_onenigeria.UpdateService.db;
import static com.ran.devx.peterobi_onenigeria.UpdateService.merchAdapterUU;
import static com.ran.devx.peterobi_onenigeria.UpdateService.srx;
import static com.ran.devx.peterobi_onenigeria.UpdateService.updateAdapterUU;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;


public class UpdatesActivity extends AppCompatActivity {

    StorageReference storageRef;
    RecyclerView merchList, updateList;
    LinearLayout baseLayout;
//    MerchAdapter merchAdapter;

    Boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);


        merchList = findViewById(R.id.merch_row);
        updateList = findViewById(R.id.news_update);
        baseLayout = findViewById(R.id.update_layout);

        acx = this;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false);
        merchList.setLayoutManager(layoutManager);
        merchList.setItemAnimator(new DefaultItemAnimator());
        merchList.setAdapter(merchAdapterUU);
        merchAdapterUU.notifyDataSetChanged();


        RecyclerView.LayoutManager layoutManagerX = new LinearLayoutManager(ctx);
        updateList.setLayoutManager(layoutManagerX);
        updateList.setItemAnimator(new DefaultItemAnimator());
        updateList.setAdapter(updateAdapterUU);
        updateAdapterUU.notifyDataSetChanged();


//        merchAdapter = new MerchAdapter(ctx,
//        getMerchandises(merchList);
//        );

//        getUpdates(updateList);



    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce){

            db.terminate().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    srx.stopSelf();
                    System.exit(0);
                }
            });
            return;
        }

//        ViewGroup viewGroup = new ViewGroup() {
//            @Override
//            protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
//
//            }
//        };

        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "One Nigeria!♥", Toast.LENGTH_SHORT).setView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.one_nigeria_exit, viewGroup, false)).show();
        Toast exitToast = new Toast(getApplicationContext());
        exitToast.setView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.one_nigeria_exit, baseLayout, false));
        exitToast.setDuration(Toast.LENGTH_LONG);
        exitToast.show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}