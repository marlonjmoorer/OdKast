package com.marlonjmoorer.odkast.Adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.marlonjmoorer.odkast.Helpers.loadUrl
import com.marlonjmoorer.odkast.Models.EpisodeSearchResult
import com.marlonjmoorer.odkast.R
import org.jetbrains.anko.*

/**
 * Created by marlonmoorer on 6/2/17.
 */
class EpisodeListAdapter(result:EpisodeSearchResult):BaseAdapter() {

    var result:EpisodeSearchResult?=null
    init {
        this.result=result
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var episode= result?.results!![position]
       return  with(parent!!.context){
           linearLayout {
               setBackgroundColor(resources.getColor(R.color.colorAccent))
                frameLayout {
                    setBackgroundColor(resources.getColor(R.color.red_100))
                    imageView {
                        loadUrl(episode.image_urls.thumb)
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
                    setBackgroundColor(resources.getColor(R.color.blue_50))
                   textView {
                       text=episode.title
                   }.lparams {
                       width= matchParent
                       height=0
                       weight=1f
                   }
                   textView {
                       text=episode.updated_at
                   }.lparams {
                       width= matchParent
                       height=0
                       weight=1f
                   }

                }.lparams{
                   width=0
                   height= matchParent
                   weight=5F
                }

                lparams {
                    width= matchParent
                    height= matchParent
                }
                setPadding(dip(16),0,dip(16),0)
           }
       }
    }

    override fun getItem(position: Int): Any {
      return result?.results?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
      return position.toLong()
    }

    override fun getCount(): Int {
       return  result?.results?.size!!
    }
}