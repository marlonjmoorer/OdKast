package com.marlonjmoorer.odkast.Helpers

import android.widget.ImageView
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso

/**
 * Created by marlonmoorer on 5/31/17.
 */
fun ImageView.loadUrl(url: String) {


    var builder =  Picasso.Builder(context);

    builder.downloader(OkHttpDownloader(context));
    builder.build().load(url).into(this);
  //  var ur=url.replace("https","http")
    //Picasso.with(this.context)


}

fun asycHandler()={ throwable : Throwable ->
    throwable.printStackTrace()
}