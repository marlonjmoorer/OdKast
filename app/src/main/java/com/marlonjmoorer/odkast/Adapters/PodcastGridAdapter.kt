package com.marlonjmoorer.odkast.Adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.marlonjmoorer.odkast.Helpers.loadUrl
import com.marlonjmoorer.odkast.Models.ShowSearchResult
import com.marlonjmoorer.odkast.R
import org.jetbrains.anko.*


/**
 * Created by marlonmoorer on 6/1/17.
 */
class PodcastGridAdapter(result: ShowSearchResult) :BaseAdapter() {

    private var  result: ShowSearchResult

    init {
        this.result=result
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var show=result.results[position]
        return with(parent!!.context){
           verticalLayout {
                lparams{
                    height= dip(250)
                    width= matchParent
                }
                frameLayout {
                   imageView {
                       loadUrl(show.image_files[0].file.url)
                       adjustViewBounds=true
                       scaleType= ImageView.ScaleType.FIT_XY
                   }.lparams{
                       height= matchParent
                       width= matchParent
                   }

                }.lparams{
                    height= 0
                    width= matchParent
                    weight=5f
                }
                linearLayout {
                    setBackgroundColor(resources.getColor(R.color.colorAccent))
                    textView{
                        text=show.title
                        textColor= resources.getColor(R.color.white)
                        maxLines=3
                    }.lparams{
                        margin=dip(16)
                        width=0
                        weight=4F
                    }
                    /*imageView {
                        imageResource= android.R.drawable.btn_plus
                        adjustViewBounds=true
                        scaleType= ImageView.ScaleType.CENTER_INSIDE
                    }.lparams{
                        margin=dip(16)
                        width=0
                        weight=1F
                    }*/

                }.lparams{
                    height= dip(48)
                    width= matchParent
                    //weight=1f
                }



               /*Picasso.Builder(context)
                       .downloader(OkHttpDownloader(context))
                       .build()
                       .load(show.image_files[0].file.url)
                       .into(object:com.squareup.picasso.Target{
                           override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                              // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                           }

                           override fun onBitmapFailed(errorDrawable: Drawable?) {
                               this@linearLayout.setBackgroundResource(R.drawable.abc_ic_star_half_black_16dp)
                               //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                           }

                           override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                               this@linearLayout.background = BitmapDrawable(bitmap)
                           }
                       })
              */

           }
       }
    }

    override fun getItem(position: Int): Any {
        return  result.results[position]
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getCount(): Int {
      return result.results.size
    }
}


