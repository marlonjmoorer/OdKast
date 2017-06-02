package com.marlonjmoorer.odkast.Helpers

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.GsonBuilder
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

/**
 * Created by marlonmoorer on 5/31/17.
 */

class Util{


   companion object{

       inline fun <reified T:Any> parseJson(json:String):T{
           var gson = GsonBuilder().create()
           return gson.fromJson<T>(json)
       }
   }
}

fun ImageView.loadUrl(url: String) {


    var builder =  Picasso.Builder(context);

    builder.downloader(OkHttpDownloader(context));
    builder.build().load(url).into(this);
  //  var ur=url.replace("https","http")
    //Picasso.with(this.context)


}

fun View.loadUrl(url:String){
    var builder =  Picasso.Builder(context);

    builder.downloader(OkHttpDownloader(context));
    builder.build().load(url).into(object:Target{
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onBitmapFailed(errorDrawable: Drawable?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            this@loadUrl.background = BitmapDrawable(bitmap)
          //  this@loadUrl.
        }

    });

}

fun asycHandler()={ throwable : Throwable ->
    throwable.printStackTrace()
}