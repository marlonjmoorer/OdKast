package com.marlonjmoorer.odkast.Adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import com.marlonjmoorer.odkast.Helpers.loadUrl
import com.marlonjmoorer.odkast.Models.TrendingPodcast
import com.marlonjmoorer.odkast.R
import org.jetbrains.anko.*

/**
 * Created by marlonmoorer on 5/31/17.
 */
class TrendingListAdapter(podcast:List<TrendingPodcast?>):BaseExpandableListAdapter(){

    private var  podcast: List<TrendingPodcast?>

    init {
        this.podcast=podcast
    }
    override fun getGroup(groupPosition: Int): TrendingPodcast? {
       return podcast[groupPosition]//To change body of created functions use File | Settings | File Templates.
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true;
    }

    override fun hasStableIds(): Boolean {
        return false;
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
      return  with(parent!!.context){
          verticalLayout {
              textView {
                  text= podcast[groupPosition]?.trend
              }
              textView {
                  text= podcast[groupPosition]?.twitter_url
              }
          }
      }
    }

    override fun getChildrenCount(groupPosition: Int): Int {
       return podcast[groupPosition]?.related_episodes?.size?:0
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return podcast[groupPosition]?.related_episodes!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
      return  groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {

        var episode= this.getChild(groupPosition,childPosition) as TrendingPodcast.RelatedEpisodesBean
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
                    weight=7F
                    width=dip(0)
                }
            }

        }
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
      return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return  podcast.size?:0
    }


}