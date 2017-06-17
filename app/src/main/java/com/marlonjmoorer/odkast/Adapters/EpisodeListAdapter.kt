package com.marlonjmoorer.odkast.Adapters

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.marlonjmoorer.odkast.Helpers.loadUrl
import com.marlonjmoorer.odkast.Helpers.toDate
import com.marlonjmoorer.odkast.Helpers.toDateString
import com.marlonjmoorer.odkast.Helpers.toShortTime
import com.marlonjmoorer.odkast.Models.PodcastFeed
import com.marlonjmoorer.odkast.R
import org.jetbrains.anko.*

/**
 * Created by marlonmoorer on 6/2/17.
 */
class EpisodeListAdapter(feed:PodcastFeed):BaseAdapter() {

    var feed:PodcastFeed?=null

    init {
        this.feed=feed
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var episode= feed?.items!![position]
       if(episode.thumbnail.isNullOrEmpty()){
           episode.thumbnail=feed?.feed?.image!!
       }
       return  with(parent!!.context){
           linearLayout {
               //setBackgroundColor(resources.getColor(R.color.colorAccent))

                frameLayout {

                    imageView {
                        loadUrl(episode.thumbnail)
                        adjustViewBounds=true
                        scaleType= ImageView.ScaleType.FIT_XY

                    }.lparams{
                        height= matchParent
                        width= matchParent
                    }
                }.lparams{
                    padding=dip(10)
                    width=0
                    height= matchParent
                    weight=1F
                }
                verticalLayout {

                   textView {
                       text=episode.title
                       maxLines=1
                   }.lparams {
                       width= matchParent
                       height=0
                       weight=1f
                   }
                   textView {
                       text=episode.pubDate.toDate()?.toDateString()
                   }.lparams {
                       width= matchParent
                       height=0
                       weight=1f
                   }

                }.lparams{
                   width=0
                   height= matchParent
                   weight=5F
                   marginStart=dip(8)
                }
               linearLayout {
                   gravity=Gravity.BOTTOM
                   textView {

                       textColor=resources.getColor(R.color.colorAccent)
                       text=episode.enclosure.duration.toShortTime()
                   }
               }.lparams{
                   width=0
                   height= matchParent
                   weight=1F
               }


                lparams {
                    width= matchParent
                    height= matchParent
                    padding=dip(10)
                }
                setPadding(dip(16),dip(8),dip(16),dip(8))
           }
       }
    }

    override fun getItem(position: Int): Any {
      return feed?.items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
      return position.toLong()
    }

    override fun getCount(): Int {
       return  feed?.items?.size!!
    }

    class ViewHolder(){

    }
}