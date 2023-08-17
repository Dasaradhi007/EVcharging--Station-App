package com.example.myapplication.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class FeaturedNearMe extends RecyclerView.Adapter<FeaturedNearMe.FeaturedViewHolder>{

    ArrayList<nearHelperClass> featuredLocations;

    public FeaturedNearMe(ArrayList<nearHelperClass> featuredLocations) {
        this.featuredLocations = featuredLocations;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.near_by_me_card_view,parent,false);
        FeaturedViewHolder featuredViewHolder=new FeaturedViewHolder(view);
        return featuredViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {
        nearHelperClass nearHelper=featuredLocations.get(position);
        holder.image.setImageResource(nearHelper.getImage());
        holder.title.setText(nearHelper.getTile());
        holder.description.setText(nearHelper.getDescription());
        holder.connector.setText(nearHelper.getConnector());

    }

    @Override
    public int getItemCount() {
        return featuredLocations.size();
    }


    public static class FeaturedViewHolder extends  RecyclerView.ViewHolder{
        ImageView image;
        TextView title,description,connector;

        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);
            //Hooks
            image=itemView.findViewById(R.id.mv_image);
            title=itemView.findViewById(R.id.mv_title);
            description=itemView.findViewById(R.id.mv_description);
            connector=itemView.findViewById(R.id.mv_watts2);

        }
    }
}
