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
