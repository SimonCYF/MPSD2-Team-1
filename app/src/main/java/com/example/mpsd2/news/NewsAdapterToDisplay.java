package com.example.mpsd2.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mpsd2.R;

import java.util.ArrayList;

public class NewsAdapterToDisplay extends RecyclerView.Adapter<NewsAdapterToDisplay.ViewHolder> {

    Context context;
    ArrayList<ModelClass> modelClassArrayList;

    public NewsAdapterToDisplay(Context context, ArrayList<ModelClass> modelClassArrayList) {
        this.context = context;
        this.modelClassArrayList = modelClassArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_layout_news,null,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, WebView.class);
                intent.putExtra("url",modelClassArrayList.get(position).getUrl());
                context.startActivity(intent);
            }
        });

        holder.mTime.setText("Published At:"+modelClassArrayList.get(position).getPublishedAt());
        holder.mAuthor.setText(modelClassArrayList.get(position).getAuthor());
        holder.mHeading.setText(modelClassArrayList.get(position).getTitle());
        holder.mContent.setText(modelClassArrayList.get(position).getDescription());
        Glide.with(context).load(modelClassArrayList.get(position).getUrlToImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return modelClassArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mHeading, mContent, mAuthor, mCategory, mTime;
        CardView cardView;
        ImageView imageView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mHeading=itemView.findViewById(R.id.newsMainHeading);
            mContent=itemView.findViewById(R.id.newsContent);
            mAuthor=itemView.findViewById(R.id.newsAuthor);
            mTime=itemView.findViewById(R.id.newsTime);
            imageView=itemView.findViewById(R.id.imageViewForNews);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}