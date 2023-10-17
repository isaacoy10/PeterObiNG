package com.ran.devx.peterobi_onenigeria;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.CATEGORY_BROWSABLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

public class BigNewsActivity extends AppCompatActivity {

    int leftDelta, topDelta, widthScale, heightScale;
    LinearLayout bigLayout;
    ImageView bigImage;
    TextView bigTitle, bigDate, bigInfo;
    ScaleAnimation slowContract, slowExpandUp;
    int pivotX = 0, pivotY = 0;
    AppCompatButton bigOpenBtn;
    MaterialCardView homeCard;
    TranslateAnimation moveUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_news);

        final Bundle bundle = getIntent().getExtras();


        pivotX = bundle.getInt("pivotX");
        pivotY = bundle.getInt("pivotY");
        final int top = bundle.getInt("viewTop");
        final int left = bundle.getInt("viewLeft");
        final int width = bundle.getInt("viewWidth");
        final int height = bundle.getInt("viewHeight");
        final byte[] imageByte = bundle.getByteArray("bigImage");
        final String title = bundle.getString("bigTitle");
        final String date = bundle.getString("bigDate");
        final String info = bundle.getString("bigInfo");
        final String url = bundle.getString("bigUrl");
        final int orientation = bundle.getInt("viewOrientation");

        bigLayout = findViewById(R.id.big_layout);
        bigImage = findViewById(R.id.big_image);
        bigTitle = findViewById(R.id.big_title);
        bigDate = findViewById(R.id.big_date);
        bigInfo = findViewById(R.id.big_info);
        bigOpenBtn = findViewById(R.id.open_url);
        homeCard = findViewById(R.id.home_card);

        moveUp = new TranslateAnimation(0.0f, 0.0f, 100f, 0.0f);
        moveUp.setDuration(500);
        moveUp.setInterpolator(new DecelerateInterpolator());
        moveUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                homeCard.setTranslationY(0.0f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        homeCard.setAnimation(moveUp);
        homeCard.startAnimation(moveUp);
//        moveUp.start();

        Glide.with(getApplicationContext()).load(imageByte).into(bigImage);
        bigTitle.setText(title);
        bigDate.setText(date);
        bigInfo.setText(info);

        bigTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/palanquin_bold.ttf"));
        bigDate.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/palanquin_medium.ttf"));
        bigInfo.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/palanquin_regular.ttf"));

        slowContract = new ScaleAnimation(1.15f,1.0f,1.15f,1.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        slowExpandUp = new ScaleAnimation(1.0f,1.15f,1.0f,1.15f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        slowContract.setDuration(10500);
        slowExpandUp.setDuration(10500);
//        slowExpand.setRepeatCount(Animation.INFINITE);
//        slowExpandUp.setRepeatCount(Animation.INFINITE);
//        slowContract.setRepeatCount(Animation.INFINITE);
//        slowExpand.setRepeatCount(200);
        slowContract.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                bigImage.setScaleX(1.0f);
//                bigImage.setScaleY(1.0f);
                bigImage.startAnimation(slowExpandUp);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slowExpandUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                bigImage.setScaleX(1.15f);
//                bigImage.setScaleY(1.15f);
                bigImage.startAnimation(slowContract);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        bigImage.startAnimation(slowContract);

        bigOpenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String query = Uri.encode(url, "UTF-8");
                Intent browserIntent = new Intent(CATEGORY_BROWSABLE, Uri.parse(Uri.decode(query)));
                browserIntent.setAction(ACTION_VIEW);
                startActivity(browserIntent);
            }
        });

//        bigImage
        if(savedInstanceState==null){
            ViewTreeObserver observer = bigLayout.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    //LinearLayout is poppedAlbum
                    bigLayout.getViewTreeObserver().removeOnPreDrawListener(this);

                    int[] screenLocation = new int[2];
                    bigLayout.getLocationOnScreen(screenLocation);

                    leftDelta = left - screenLocation[0];
                    topDelta = top - screenLocation[1];

                    widthScale = width/bigLayout.getWidth();
                    heightScale = height/bigLayout.getHeight();

//                    runEnterAnimation();
                    return true;
                }
            });
        }
    }

    private void runEnterAnimation(){
        bigLayout.setTranslationY(-0.25f);
        bigLayout.setPivotX(pivotX);
        bigLayout.setPivotY(pivotY);
        bigLayout.setScaleX(widthScale);
        bigLayout.setScaleY(heightScale);
        bigLayout.setTranslationX(leftDelta);
        bigLayout.setTranslationY(topDelta);

        bigLayout.setAlpha(0.0f);
        bigLayout.animate()
                .setDuration(300)
//                .scaleX(1)
//                .scaleY(1)
                .alpha(1.0f)
                .translationX(0)
                .translationY(0)
                .setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        bigLayout.setTranslationY(0);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }
}