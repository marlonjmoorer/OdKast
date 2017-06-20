package com.marlonjmoorer.odkast.Helpers

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Notification
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.RemoteViews
import com.marlonjmoorer.odkast.R
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import org.jetbrains.anko.*
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
    try {
        builder.build().load(url).fit().into(this)
    }
    catch (ex:Exception){
        print(ex.stackTrace.contentDeepToString())
    }

    //  var ur=url.replace("https","http")
    //Picasso.with(this.contex)

}


fun View.loadUrl2(url: String) {


    var builder =  Picasso.Builder(context)
    var target= object:Target{
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
          Log.e("","")
        }

        override fun onBitmapFailed(errorDrawable: Drawable?) {
            this@loadUrl2.backgroundResource=R.drawable.icons8_add_filled
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
           this@loadUrl2.background= BitmapDrawable(bitmap)
        }
    }

    this.setTag(this.id,target)
    builder.downloader(OkHttpDownloader(context));
    try {
        var localTarget=getTag(this.id) as Target
        builder.build().load(url).into(localTarget)

    }
    catch (ex:Exception){
        print(ex.stackTrace.contentDeepToString())
    }

    //  var ur=url.replace("https","http")
    //Picasso.with(this.context)


}
fun String.toDate():Date?{
    val df = SimpleDateFormat("yyyyy-mm-dd hh:mm:ss", Locale.US)
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
fun View.loadingScreen(boolean: Boolean){
    var loading= loadingUI(context)
    if(this is ViewGroup){

       if (boolean)
           this.addView(loading)
        else{
           find<RelativeLayout>(R.id.loading_overlay)?.let {  this.removeView(it)}
       }
    }
}


private fun loadingUI(context: Context)= with(context){
    relativeLayout {
        id= R.id.loading_overlay
        backgroundColor= resources.getColor(R.color.black)


        gravity=Gravity.CENTER
        progressBar{
            isIndeterminate=true
        }.lparams{
            width=dip(75)
            height= dip(75)

        }
        lparams {
            height= matchParent
            width= matchParent
        }
    }
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

fun Notification.loadImage(url:String,resource:Int){


    //var builder =  Picasso.Builder();

   // builder.downloader(OkHttpDownloader(context));
    try {
      //  builder.build().load(url).fit().into(this,)
    }
    catch (ex:Exception){
        print(ex.stackTrace.contentDeepToString())
    }

}
