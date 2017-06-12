package com.marlonjmoorer.odkast.Helpers

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by marlonmoorer on 6/3/17.
 */
fun ImageView.loadUrl(url: String) {


    var builder =  Picasso.Builder(context);

    builder.downloader(OkHttpDownloader(context));
    builder.build().load(url).into(this);
    //  var ur=url.replace("https","http")
    //Picasso.with(this.context)


}
fun String.toDate():Date?{
    val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    val date: Date
    try {
        date = df.parse(this)
        return date
    }catch(ex:ParseException){
        return null
    }

}
fun Date.toDateString():String{
    val df = SimpleDateFormat("EEE, MMM dd, yyyy")
    return df.format(this)
}

fun SlidingUpPanelLayout.expand(){
    this.panelState=PanelState.EXPANDED
}
fun SlidingUpPanelLayout.collapse(){
    this.panelState=PanelState.COLLAPSED
}
fun View.loadUrl(url:String){
    var builder =  Picasso.Builder(context);

    builder.downloader(OkHttpDownloader(context));
    builder.build().load(url).into(object: Target {
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



fun View.fade(alpha:Float){
    this.animate()
            .alpha(alpha)
            .setDuration(0)
            .setListener(object :AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    when(this@fade.alpha){
                        in 0f..(.50f) ->this@fade.visibility=View.GONE
                        in (.50f)..1f->this@fade.visibility=View.VISIBLE
                    }
                }
            });

}

fun View.fadeOut(){
    this.animate()
            .alpha(0.0f)
            .setDuration(500)
            .setListener(object :AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    this@fadeOut.visibility=View.GONE
                }
            });

}
fun View.fadeIn(){
    this.animate()
            .alpha(1.0f)
            .setDuration(500)
            .setListener(object :AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    this@fadeIn.visibility=View.VISIBLE
                }
            });

}

fun Int.toShortTime():String{
    val millis=this.toLong()
   return  String.format("%01d:%02d:%02d",
            TimeUnit.SECONDS.toHours(millis),
            TimeUnit.SECONDS.toMinutes(millis) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(millis)), // The change is in this line
            TimeUnit.SECONDS.toSeconds(millis) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(millis)));
}
fun Int.toTime():String{

    val millis=this.toLong()
    val buf = StringBuffer()

    buf
            .append(String.format("%02d", millis / (1000 * 60 * 60)))
            .append(":")
            .append(String.format("%02d", millis % (1000 * 60 * 60) / (1000 * 60)))
            .append(":")
            .append(String.format("%02d", millis % (1000 * 60 * 60) % (1000 * 60) / 1000))

    return buf.toString()
}
