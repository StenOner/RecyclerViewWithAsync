package com.example.lenovo.recyclerviewwithasync;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MemeViewHolder>{

    List<Memes> memes;
    Context context;

    RVAdapter(List<Memes> memes, Context context){
        this.memes = memes;
        this.context = context;
    }

    public static class MemeViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView memeName;
        TextView memeDesc;
        ImageView memePhoto;

        MemeViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            memeName = (TextView)itemView.findViewById(R.id.meme_name);
            memeDesc = (TextView)itemView.findViewById(R.id.meme_desc);
            memePhoto = (ImageView)itemView.findViewById(R.id.meme_photo);
        }
    }
    @Override
    public int getItemCount() {
        return memes.size();
    }
    @Override
    public MemeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        MemeViewHolder mvh = new MemeViewHolder(v);
        return mvh;
    }
    @Override
    public void onBindViewHolder(@NonNull MemeViewHolder memeViewHolder, int i) {
        memeViewHolder.memeName.setText(memes.get(i).name);
        memeViewHolder.memeDesc.setText(memes.get(i).desc);

        Bitmap originalBitmap = memes.get(i).photoId;
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(context.getResources(),
                        originalBitmap);
        memeViewHolder.memePhoto.setImageDrawable(roundedDrawable);
    }
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
