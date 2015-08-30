package com.example.android.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pankajanand on 22/8/15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<TheMovie> mThumbIdi;
    String LOG_TAG = ImageAdapter.class.getSimpleName();
    public ImageAdapter(Context c,List<TheMovie> mThumbId) {
        mContext = c;
        mThumbIdi = mThumbId;
        Log.d("ImageAdapter",((String)("Enter the Adapter" + mThumbIdi.size())));
    }

    public int getCount() {
        return mThumbIdi.size();
    }

    public Object getItem(int position) {

        return mThumbIdi.toArray()[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        TextView txtView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_movies, parent, false);


            txtView=(TextView) convertView.findViewById(R.id.list_item_text);
            imageView=(ImageView) convertView.findViewById(R.id.list_item_image);
           // imageView.setLayoutParams(new GridView.LayoutParams(50, 50));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);

        } else {
            convertView.findViewById(R.id.list_item_image);
            imageView = (ImageView) convertView.findViewById(R.id.list_item_image);
            txtView=(TextView) convertView.findViewById(R.id.list_item_text);
        }

        Log.d(LOG_TAG, mThumbIdi.get(position).getPosterPath("w185"));
        Picasso.with(mContext).load(mThumbIdi.get(position).getPosterPath("w185")).into(imageView);
        //imageView.setImageResource(mThumbIdi.get(position).getPosterPath());
        txtView.setText(mThumbIdi.get(position).getMovieTitle());

        return convertView;
    }

}