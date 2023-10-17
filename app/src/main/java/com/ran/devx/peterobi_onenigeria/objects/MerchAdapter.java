package com.ran.devx.peterobi_onenigeria.objects;

import static com.ran.devx.peterobi_onenigeria.UpdateService.ctx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ran.devx.peterobi_onenigeria.R;
import com.ran.devx.peterobi_onenigeria.UpdatesActivity;

import java.util.ArrayList;

public class MerchAdapter extends BaseAdapter {

    public ArrayList<Merch> merchandises;
    public LayoutInflater layoutInflater;

    /**constructor**/
    public MerchAdapter(Context merchCtx, ArrayList<Merch> merches){
        merchandises = merches;
        layoutInflater = LayoutInflater.from(merchCtx);
    }
    @Override
    public int getCount() {
        return merchandises.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FrameLayout merchLayout = (FrameLayout) layoutInflater.inflate(R.layout.merch_layout, viewGroup, false);
        TextView merchTitle = (TextView) merchLayout.findViewById(R.id.merch_title);
        TextView merchInfo = (TextView) merchLayout.findViewById(R.id.merch_info);
        TextView merchPrice = (TextView) merchLayout.findViewById(R.id.merch_price);
        ImageView merchArt = (ImageView) merchLayout.findViewById(R.id.merch_image);

        final Merch merch = merchandises.get(i);
        merchTitle.setText(merch.getxTitle());
        merchInfo.setText(merch.getxInfo());
        merchPrice.setText(merch.getxPrice());

        Glide.with(ctx).load(merch.getxArt()).into(merchArt);

        merchLayout.setTag(i);
        return merchLayout;
    }
}
