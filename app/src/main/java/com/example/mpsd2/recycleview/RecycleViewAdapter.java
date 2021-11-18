package com.example.mpsd2.recycleview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mpsd2.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mName = new ArrayList<>();
    private ArrayList<String> mImage = new ArrayList<>();
    private ArrayList<String> mDetails = new ArrayList<>();
    private Context mContext;

    public RecycleViewAdapter(Context context, ArrayList<String> name, ArrayList<String> image, ArrayList<String> details){
        mName = name;
        mImage = image;
        mContext = context;
        mDetails = details;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycle_view_horizontal, parent, false);

        return new ViewHolder(view);
    }

    @Override//bind the widgetes tgt
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onCreateViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImage.get(position))
                .into(holder.vaccineImage);

        holder.vaccineDetails.setText(mDetails.get(position));

        holder.vaccineName.setText(mName.get(position));
        holder.vaccineImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on an image" + mName.get(position));
                Toast.makeText(mContext, mName.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView vaccineImage;
        TextView vaccineName;
        TextView vaccineDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            vaccineImage = itemView.findViewById(R.id.horizontalImage);
            vaccineName = itemView.findViewById(R.id.horizontalName);
            vaccineDetails = itemView.findViewById(R.id.horizontalDetails);


        }
    }
}
