package com.ran.devx.peterobi_onenigeria;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
public class FullGalleryActivity extends AppCompatActivity {
    ImageView po_splash, po_one, map_dot;
    TextView twentythree, r1, r2, r3;
    FrameLayout baseBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_gallery);

        startService(new Intent(getApplicationContext(), UpdateService.class));

        baseBlack = findViewById(R.id.base_black);
        po_splash = findViewById(R.id.peter_obi_splash);
        po_one = findViewById(R.id.peter_obi_one);
        map_dot = findViewById(R.id.map_dot);
        twentythree = findViewById(R.id.twentythree);

        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        r3 = findViewById(R.id.r3);

        r1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/palanquindark_semibold.ttf"));
        r2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/palanquindark_semibold.ttf"));
        r3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/palanquindark_semibold.ttf"));


        TranslateAnimation moveRight = new TranslateAnimation(0f, 200.0f, 0f, 0f);
        moveRight.setDuration(10000);
        moveRight.setInterpolator(new AccelerateDecelerateInterpolator());



        TranslateAnimation moveLeft = new TranslateAnimation(0f, -200.0f, 0f, 0f);
        moveLeft.setDuration(10000);
        moveLeft.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimationSet liftExpand = new AnimationSet(true);
        ScaleAnimation expand = new ScaleAnimation(0.9f,1.0f,0.9f,1.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        TranslateAnimation moveUp = new TranslateAnimation(0f, 0f, 250.0f, 0.f);
        liftExpand.addAnimation(expand);
        liftExpand.addAnimation(moveUp);
        liftExpand.setDuration(8000);
        liftExpand.setInterpolator(new AccelerateDecelerateInterpolator());


        ScaleAnimation bigMap = new ScaleAnimation(0.7f,1.0f,0.7f,1.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        bigMap.setDuration(3000);
        bigMap.setInterpolator(new AccelerateDecelerateInterpolator());


        AnimationSet fadeScreen = new AnimationSet(true);
        ScaleAnimation bigScreen = new ScaleAnimation(1.0f,1.3f,1.0f,1.3f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        AlphaAnimation fadeOff = new AlphaAnimation(1.0f, 0.0f);
        fadeScreen.addAnimation(bigScreen);
        fadeScreen.addAnimation(fadeOff);
        fadeScreen.setInterpolator(new DecelerateInterpolator());
        fadeScreen.setDuration(1000);

        fadeScreen.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                baseBlack.setAlpha(0f);
                startActivity(new Intent(getApplicationContext(), UpdatesActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        moveRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                twentythree.setTranslationX(200.0f);
                baseBlack.startAnimation(fadeScreen);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        twentythree.startAnimation(moveRight);
        po_one.startAnimation(liftExpand);
        po_splash.startAnimation(moveLeft);
        map_dot.startAnimation(bigMap);



        Glide.with(getApplicationContext()).load(R.drawable.ic_launcher_background_raw).into(po_splash);
        Glide.with(getApplicationContext()).load(R.drawable.obi_one).into(po_one);
        Glide.with(getApplicationContext()).load(R.drawable.nigeria_map_dots).into(map_dot);


    }
}