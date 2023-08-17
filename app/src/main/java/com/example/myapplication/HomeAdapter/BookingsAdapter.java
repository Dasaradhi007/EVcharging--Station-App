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

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.FeaturedViewHolder> {
    ArrayList<BookingsHelper> featuredLocations;

    public BookingsAdapter(ArrayList<BookingsHelper> featuredLocations) {
        this.featuredLocations = featuredLocations;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_bookings,parent,false);
       FeaturedViewHolder featuredViewHolder=new FeaturedViewHolder(view);
        return featuredViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {
        BookingsHelper featuredHelperClass=featuredLocations.get(position);
        holder.image.setImageResource(featuredHelperClass.getImage());
        holder.title.setText(featuredHelperClass.getTile());
        holder.status.setText(featuredHelperClass.getStatus());
        holder.connector.setText(featuredHelperClass.getConnector());
        holder.description.setText(featuredHelperClass.getDescription());


    }

    @Override
    public int getItemCount() {
        return featuredLocations.size();
    }


    public static class FeaturedViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title,status,connector,description;

        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);
            //Hooks
            image = itemView.findViewById(R.id.bk_image);
            title = itemView.findViewById(R.id.bk_title);
            status = itemView.findViewById(R.id.bk_status);
            connector = itemView.findViewById(R.id.bk_watts2);
            description=itemView.findViewById(R.id.bk_description);

        }
    }
}
