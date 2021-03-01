package com.printful.foodie.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.printful.foodie.R

//using glide for downloading image from the internet
fun ImageView.downloadImageFromInternet(url: String?, placeholder: CircularProgressDrawable){
    val options = RequestOptions().placeholder(placeholder).error(R.drawable.ic_launcher_foreground)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

//for circles to appear instead of images while waiting for the data to download
fun makePlaceHolder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

//databinding for image
@BindingAdapter("android:downloadImage")
fun downloadImage(view: ImageView, url: String?){
    view.downloadImageFromInternet(url, makePlaceHolder(view.context))
}