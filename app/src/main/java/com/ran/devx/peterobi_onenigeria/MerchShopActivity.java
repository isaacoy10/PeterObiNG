package com.ran.devx.peterobi_onenigeria;

import static com.ran.devx.peterobi_onenigeria.UpdateService.placeOrder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import jp.wasabeef.blurry.Blurry;

public class MerchShopActivity extends AppCompatActivity {

    ImageView orderImage, smallNaijaBadge;
    TextView orderItem, orderPrice;
    TextView accountName, accountNumber, bankName;
    EditText orderCustomerName, orderQuantity, orderPhone, orderCity;
    AppCompatCheckBox confirmDetails, confirmPayment;
    MaterialButton placeOrderButton;
    Activity activity;

    String firstName = "", phone = "", city = "";
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merch_shop);

        activity = this;


        orderPrice = findViewById(R.id.order_price);
        orderImage = findViewById(R.id.order_image);
        smallNaijaBadge = findViewById(R.id.small_naija_badge);
        orderItem = findViewById(R.id.order_item_name);
        accountName = findViewById(R.id.account_owner);
        accountNumber = findViewById(R.id.account_number);
        bankName = findViewById(R.id.bank_name);
        orderCustomerName = findViewById(R.id.order_customer_name);
        orderQuantity = findViewById(R.id.order_quantity);
        orderPhone = findViewById(R.id.order_customers_phone);
        orderCity = findViewById(R.id.order_city);
        confirmDetails = findViewById(R.id.confirm_details);
        confirmPayment = findViewById(R.id.confirm_payment);
        placeOrderButton = findViewById(R.id.confirm_order);


        final Bundle bundle = getIntent().getExtras();

        final byte[] shopImage = bundle.getByteArray("shopImage");
        final String shopTitle = bundle.getString("shopTitle");
//        final String shopInfo = bundle.getString("shopInfo");
        final String[] shopPrice = {bundle.getString("shopPrice")};
        final String shopType = bundle.getString("shopType");

        Glide.with(getApplicationContext()).load(shopImage).into(new Target<Drawable>() {
            @Override
            public void onLoadStarted(@Nullable Drawable placeholder) {

            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {

            }

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                Blurry.with(getApplicationContext()).from(drawableToBitmap(resource)).into(orderImage);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }

            @Override
            public void getSize(@NonNull SizeReadyCallback cb) {

            }

            @Override
            public void removeCallback(@NonNull SizeReadyCallback cb) {

            }

            @Override
            public void setRequest(@Nullable Request request) {

            }

            @Nullable
            @Override
            public Request getRequest() {
                return null;
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onStop() {

            }

            @Override
            public void onDestroy() {

            }
        });
        Glide.with(getApplicationContext()).load(shopImage).into(smallNaijaBadge);

        if (quantity>1){
            shopPrice[0] = shopPrice[0] +"x"+quantity;
            orderPrice.setText(shopPrice[0]);
        }else {
            shopPrice[0] = bundle.getString("shopPrice");
            orderPrice.setText(shopPrice[0]);
        }
        orderItem.setText(shopTitle);
        orderPrice.setText(shopPrice[0]);

//        accountNumber.setText();
//        accountName.setText();
//        bankName.setText();

        orderCustomerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                firstName = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        orderQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try {
                    quantity = Integer.parseInt(charSequence.toString());
                    if (quantity>1){
                        shopPrice[0] = shopPrice[0] +" (x"+quantity+")";
                        orderPrice.setText(shopPrice[0]);
                        shopPrice[0] = "";
                    }else {
                        shopPrice[0] = bundle.getString("shopPrice");
                        orderPrice.setText(shopPrice[0]);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        orderPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                phone = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        orderCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                city = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (confirmDetails.isChecked() && confirmPayment.isChecked()){
                    if (!firstName.isEmpty() && quantity!=0 && !phone.isEmpty() && !city.isEmpty()){

                        placeOrder(shopTitle, firstName,
                                quantity,
                                phone, city, placeOrderButton, activity);
                    }else {
                        Snackbar.make(placeOrderButton, "Please fill out all entries correctly.", Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar.make(placeOrderButton, "Confirm both boxes and try again.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}