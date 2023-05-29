package com.example.pinkproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pinkproject.appmodel.ListCategoryModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.util.ArrayList;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.MyViewHolder> {
    Context context;
    ArrayList<ListCategoryModel> list;


    public RecyclerAdapter2(Context context, ArrayList<ListCategoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_data_cont,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ListCategoryModel model = list.get(position);
        holder.teamname.setText(model.getName());
        String imgUrl =model.getImageUrl();

        float scale = context.getResources().getDisplayMetrics().density;
        int desiredWidth = (int) (300 * scale + 0.5f);
        int desiredHeight = (int) (100 * scale + 0.5f);

        Picasso.get()
                .load(imgUrl)
                .resize(desiredWidth, desiredHeight)
                .centerCrop()
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        // Create a BitmapDrawable with the loaded bitmap
                        BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);

                        // Set the background of the LinearLayout with the bitmap drawable
                        holder.cardView.setBackground(drawable);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        // Handle any errors that occur during image loading
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        // This method is optional and can be used to handle placeholder drawable
                    }
                });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(context,ListCategory.class);
//                intent2.putExtra("userid",model.ge());
//                intent2.putExtra("userdate",model.getDate());
//                intent2.putExtra("username",model.getName());

                context.startActivity(intent2);
            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView teamname;
        LinearLayout backgroundLayout;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            teamname = itemView.findViewById(R.id.teamname);
            backgroundLayout = itemView.findViewById(R.id.backgroundLayout);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
