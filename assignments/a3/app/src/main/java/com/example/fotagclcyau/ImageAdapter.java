package com.example.fotagclcyau;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder>
        implements Filterable {

    private Context mContext;
    private ArrayList<ImageObject> mImageList, fImageList;

    public ImageAdapter(Context context, ArrayList<ImageObject> imageList){
        mContext = context;
        mImageList = imageList;
        fImageList = imageList;
    }

    public void setImageList(ArrayList<ImageObject> imageList){
        mImageList = imageList;
        fImageList = imageList;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_image,
                viewGroup, false);
        return new ImageHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder imageHolder, int i) {
        ImageObject imageObject = fImageList.get(i);

//        Rating
        imageHolder.mRateBar.setRating(imageObject.getRating());
//        Image
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(mContext)
                .load(imageObject.getFileLoc())
                .apply(options)
                .into(imageHolder.mImageView);

    }

    @Override
    public int getItemCount() {
        return fImageList.size();
    }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String str_rate = charSequence.toString();
                    if (str_rate.isEmpty()){
                        fImageList = mImageList;
                    } else {
                        float rating = Float.valueOf(str_rate);
                        if (rating == 0){
                            fImageList = mImageList;
                        } else {
                            ArrayList<ImageObject> fList = new ArrayList<>();
                            for (ImageObject img : mImageList) {
                                if (img.getRating() >= rating) {
                                    fList.add(img);
                                }
                            }
                            fImageList = fList;
                        }
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = fImageList;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    fImageList = (ArrayList<ImageObject>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public class ImageHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        private RatingBar mRateBar;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_pic);
            mRateBar = itemView.findViewById(R.id.image_rate);

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(mContext, ImageActivity.class);

                        String url = fImageList.get(position).getFileLoc();
                        intent.putExtra(MainActivity.IMAGE_MESSAGE, url);
                        mContext.startActivity(intent);
                    }
                }
            });

            mRateBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        System.out.println("Change rating on: "+position);
                        ImageObject clickedOn = fImageList.get(position);
                        clickedOn.setRating(v);
                    }
                }
            });
        }
    }
}
