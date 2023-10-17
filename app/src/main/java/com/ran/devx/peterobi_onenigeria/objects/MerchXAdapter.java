package com.ran.devx.peterobi_onenigeria.objects;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.CATEGORY_BROWSABLE;
import static com.ran.devx.peterobi_onenigeria.UpdateService.acx;
import static com.ran.devx.peterobi_onenigeria.UpdateService.adsCountdown;
import static com.ran.devx.peterobi_onenigeria.UpdateService.ctx;
import static com.ran.devx.peterobi_onenigeria.UpdateService.showAds;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ran.devx.peterobi_onenigeria.BigNewsActivity;
import com.ran.devx.peterobi_onenigeria.MerchShopActivity;
import com.ran.devx.peterobi_onenigeria.R;
import com.ran.devx.peterobi_onenigeria.UpdatesActivity;

import java.util.ArrayList;

public class MerchXAdapter extends RecyclerView.Adapter<MerchXAdapter.MerchXViewHolder> {
    private ArrayList<Merch> merches;
    private OnMerchXItemClickListener merchItemClickListener;

    public MerchXAdapter(ArrayList<Merch> merches, OnMerchXItemClickListener listener) {
        this.merches = merches;
        this.merchItemClickListener = listener;
    }

    public interface OnMerchXItemClickListener {
        void onItemClick(Merch merchItem);
    }

    public class MerchXViewHolder extends RecyclerView.ViewHolder {
        ImageView merchImage;
        TextView merchTitle, merchInfo, merchPrice;

        public MerchXViewHolder(@NonNull View itemView) {
            super(itemView);

            merchImage = itemView.findViewById(R.id.merch_image);
            merchTitle = itemView.findViewById(R.id.merch_title);
            merchInfo = itemView.findViewById(R.id.merch_info);
            merchPrice = itemView.findViewById(R.id.merch_price);

//            merchPrice.setAlpha(0);

            merchTitle.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "fonts/palanquin_regular.ttf"));
            merchInfo.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "fonts/palanquin_light.ttf"));
        }

        public void bind(Merch merch, OnMerchXItemClickListener merchXItemClickListener, int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    merchXItemClickListener.onItemClick(merch);
//                    Toast.makeText(ctx, updates.get(position).getxTitle(), Toast.LENGTH_LONG).show();

                    if (merches.get(position).getxType().equals("MERCH")){

                        int[] objectData = new int[2];

                        ctx.startActivity(new Intent(ctx, MerchShopActivity.class)
                                //pull artwork from service instead
                                .putExtra("shopImage",merches.get(position).getxArt())
                                .putExtra("shopTitle",merches.get(position).getxTitle())
                                .putExtra("shopInfo",merches.get(position).getxInfo())
                                .putExtra("shopPrice",merches.get(position).getxPrice())
                                .putExtra("shopType",merches.get(position).getxType())
                                .putExtra("viewOrientation",acx.getResources().getConfiguration().orientation)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                        acx.overridePendingTransition(0,0);

                        adsCountdown = adsCountdown + 1;
                        if (adsCountdown>=5){
                            showAds();
                            adsCountdown = 0;
                        }
                    }else if(merches.get(position).getxType().equals("REDIR")){

                        String query = Uri.encode(merches.get(position).getxLink(), "UTF-8");
                        Intent browserIntent = new Intent(CATEGORY_BROWSABLE, Uri.parse(Uri.decode(query)));
                        browserIntent.setAction(ACTION_VIEW);
                        ctx.startActivity(browserIntent);
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public MerchXAdapter.MerchXViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View merchItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.merch_layout, parent, false);

        return new MerchXViewHolder(merchItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MerchXAdapter.MerchXViewHolder holder, int position) {
        holder.bind(merches.get(position), merchItemClickListener, position);

        String vTitle = merches.get(position).getxTitle();
        String vInfo = merches.get(position).getxInfo();
        String vPrice = merches.get(position).getxPrice();
        byte[] vArt = merches.get(position).getxArt();

        holder.merchTitle.setText(vTitle);
        holder.merchInfo.setText(vInfo);
        holder.merchPrice.setText(vPrice);
        Glide.with(ctx).load(vArt).into(holder.merchImage);
    }

    @Override
    public int getItemCount() {
        return merches.size();
    }
}
