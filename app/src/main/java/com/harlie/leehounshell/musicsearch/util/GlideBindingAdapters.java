package com.harlie.leehounshell.musicsearch.util;

import android.content.Context;
import android.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.harlie.leehounshell.musicsearch.R;
import com.makeramen.roundedimageview.RoundedImageView;

public class GlideBindingAdapters {
    private final static String TAG = "LEE: <" + GlideBindingAdapters.class.getSimpleName() + ">";

    @BindingAdapter("imageUrl")
    public static void setImage(RoundedImageView view, String imageUrl) {
        LogHelper.v(TAG, "setImage: imageUrl=" + imageUrl);
        Context context = view.getContext();

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.album_image)
                .error(R.drawable.album_image)
                .fitCenter();

        Glide.with(context)
                .load(imageUrl)
                .apply(options)
                .into(view);
    }
}
