package com.ran.devx.peterobi_onenigeria.objects;

import static com.ran.devx.peterobi_onenigeria.UpdateService.acx;
import static com.ran.devx.peterobi_onenigeria.UpdateService.adsCountdown;
import static com.ran.devx.peterobi_onenigeria.UpdateService.ctx;
import static com.ran.devx.peterobi_onenigeria.UpdateService.showAds;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.ran.devx.peterobi_onenigeria.BigNewsActivity;
import com.ran.devx.peterobi_onenigeria.R;
import com.ran.devx.peterobi_onenigeria.UpdatesActivity;

import java.util.ArrayList;

public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.UpdateViewHolder> {
    private ArrayList<Update> updates;
    private OnUpdateItemClickListener updateItemClickListener;

    public UpdateAdapter(ArrayList<Update> updateList, OnUpdateItemClickListener listener){
        this.updates = updateList;
        this.updateItemClickListener = listener;
    }

    public interface OnUpdateItemClickListener {
        void onItemClick(Update updateItem);
    }

    public class UpdateViewHolder extends RecyclerView.ViewHolder{
        private ShapeableImageView updateImage;
        private TextView updateTitle, updateBody, updateTime;
//        AdView


        public UpdateViewHolder(@NonNull View itemView) {
            super(itemView);

            updateImage = itemView.findViewById(R.id.update_image);
            updateTitle = itemView.findViewById(R.id.update_title);
            updateBody = itemView.findViewById(R.id.update_body);
            updateTime = itemView.findViewById(R.id.update_time);

            updateTitle.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "fonts/palanquin_regular.ttf"));
            updateBody.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "fonts/palanquin_light.ttf"));
        }

        public void bind(Update update, OnUpdateItemClickListener updateItemClickListener, int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateItemClickListener.onItemClick(update);
//                    Toast.makeText(ctx, updates.get(position).getxTitle(), Toast.LENGTH_LONG).show();



                    int[] objectData = new int[2];

                    ctx.startActivity(new Intent(ctx, BigNewsActivity.class)
                            .putExtra("viewLeft",objectData[0])
                            .putExtra("viewTop",objectData[1])
                            .putExtra("viewHeight",view.getHeight())
                            .putExtra("pivotX",view.getPivotX())
                            .putExtra("pivotY",view.getPivotY())
                            .putExtra("viewWidth",view.getWidth())
                            //pull artwork from service instead
                            .putExtra("bigImage",updates.get(position).getxArt())
                            .putExtra("bigTitle",updates.get(position).getxTitle())
                            .putExtra("bigDate",updates.get(position).getxDate())
                            .putExtra("bigInfo",updates.get(position).getxInfo())
                            .putExtra("bigUrl",updates.get(position).getxUrl())
                            .putExtra("viewOrientation",acx.getResources().getConfiguration().orientation)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                    acx.overridePendingTransition(0,0);

                    adsCountdown = adsCountdown + 1;
                    if (adsCountdown>=10){
                        showAds();
                        adsCountdown = 0;
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public UpdateAdapter.UpdateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View updateItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.updates_layout, parent, false);

        return new UpdateViewHolder(updateItem);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateAdapter.UpdateViewHolder holder, int position) {
        holder.bind(updates.get(position), updateItemClickListener, position);

        String upTitle = updates.get(position).getxTitle();
        String upInfo = updates.get(position).getxInfo();
        String upDate = updates.get(position).getxDate();
//        String upUrl = updates.get(position).getxUrl();
        byte[] upArt = updates.get(position).getxArt();

        holder.updateTitle.setText(upTitle);
        holder.updateBody.setText(upInfo);
        holder.updateTime.setText(upDate);
        Glide.with(ctx).load(upArt).into(holder.updateImage);
    }

    @Override
    public int getItemCount() {
        return updates.size();
    }
}
