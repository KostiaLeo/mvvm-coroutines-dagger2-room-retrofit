package com.example.restkotlinized.view;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class ImgSetter {
    @BindingAdapter("android:src_image")
    public static void setImageUrl(ImageView imageView, String url) {
        String basePath = "http://image.tmdb.org/t/p/w500";
        String fullURL = basePath + url;

        Glide.with(imageView.getContext()).load(fullURL).into(imageView);
    }
}