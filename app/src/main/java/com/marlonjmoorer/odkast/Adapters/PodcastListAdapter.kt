package com.marlonjmoorer.odkast.Adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.marlonjmoorer.odkast.Helpers.loadUrl
import com.marlonjmoorer.odkast.Models.TrendingPodcast
import com.marlonjmoorer.odkast.R
import org.jetbrains.anko.*

/**
 * Created by marlonmoorer on 5/30/17.
 */
class PodcastListAdapter(podcast: TrendingPodcast?) : BaseAdapter() {


    private val podcast:TrendingPodcast

    init {
        this.podcast=podcast!!
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var episode= this.getItem(position) as TrendingPodcast.RelatedEpisodesBean
        episode.show_id
        return with(parent!!.context){

            linearLayout {
                lparams(height= dip(60), width = matchParent)


                frameLayout {
                    setBackgroundColor(R.color.colorAccent)
                    imageView{
                        loadUrl(episode.image_urls.thumb)
                        adjustViewBounds=true
                        scaleType= ImageView.ScaleType.FIT_XY

                    }.lparams{
                        width= wrapContent
                        height= wrapContent

                    }
                }.lparams{
                    weight = 1F
                    height= wrapContent
                    width=dip(0)
                }
                verticalLayout {
                    textView {
                        text=episode.title
                    }.lparams{
                        width= wrapContent
                        height= wrapContent
                    }
                    textView {
                        text=episode.show_title
                    }.lparams{
                        width= wrapContent
                        height= wrapContent
                    }
                }.lparams{
                   height= wrapContent
                    weight=4F
                    width=dip(0)
                }
            }

        }
    }

    override fun getItem(position: Int): Any {
       return this.podcast.related_episodes[position]
    }

    override fun getItemId(position: Int): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
      return this.podcast.related_episodes.size
    }


}