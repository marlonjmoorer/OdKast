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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.GsonBuilder
import com.marlonjmoorer.odkast.R
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import org.jetbrains.anko.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by marlonmoorer on 6/3/17.
 */
fun ImageView.loadUrl(url: String) {

    try {

        Glide.with(context)
                .load(url)
                .into(this);
    }
    catch (ex:Exception){
        print(ex.stackTrace.contentDeepToString())
    }

}


fun View.loadUrl(url: String) {




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
           findViewById(R.id.loading_overlay)?.let {  this.removeView(it)}
       }
    }
}


private fun loadingUI(context: Context)= with(context){
    frameLayout{
        id= R.id.loading_overlay
        backgroundColor= resources.getColor(R.color.black)


        //gravity=Gravity.CENTER
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

fun Any.toJsonString():String{
    var gson = GsonBuilder().create()
    return gson.toJson(this)
}
inline fun <reified T:Any>String.parseTo():T{
    var gson = GsonBuilder().create()
    return gson.fromJson<T>(this)
}

