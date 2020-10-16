package com.example.android_skills.view;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class ImgSetter {
    @BindingAdapter("android:src_image_hiding")
    public static void setImageUrlHiding(ImageView imageView, String url) {
        if (url != null && !url.equals("null") && url.length() != 0) {
            Glide.with(imageView.getContext()).load(url).into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("android:src_image")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}