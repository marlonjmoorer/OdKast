package com.marlonjmoorer.odkast.Adapters

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import com.marlonjmoorer.odkast.Helpers.Genre
import com.marlonjmoorer.odkast.Helpers.PodcastSearch
import com.marlonjmoorer.odkast.Helpers.loadUrl
import com.marlonjmoorer.odkast.Models.SearchResults
import com.marlonjmoorer.odkast.Models.TrendingPodcast
import com.marlonjmoorer.odkast.R
import org.jetbrains.anko.*

/**
 * Created by marlonmoorer on 5/31/17.
 */
class CategoriesListAdapter():BaseExpandableListAdapter(){

    private var  categoriesGroups: List<GenreGroup> ? = null


    init {
        categoriesGroups=Genre.values().map {
          //  var results=PodcastSearch().SearchShowsByGenre(it).results
            GenreGroup(it,null)
        }

           /* doAsync {
               categoriesGroups=Genre.values().map {
                   var results=PodcastSearch().SearchShowsByGenre(it).results
                  GenreGroup(it,results)
                }
                notifyDataSetChanged()
            }*/



    }
    override fun getGroup(groupPosition: Int): GenreGroup? {
       return categoriesGroups?.get(groupPosition)//To change body of created functions use File | Settings | File Templates.
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true;
    }

    override fun hasStableIds(): Boolean {
        return false;
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var group=getGroup(groupPosition)

        return  with(parent!!.context){
          linearLayout {

              lparams{
                  width= matchParent
                  height= matchParent
              }
              textView {
                  gravity= Gravity.CENTER_VERTICAL
                  text=group?.genre?.name

              }.lparams{
                  height= matchParent
                  width=0
                  weight=5F
              }
              imageView {
                  imageResource= if(isExpanded) android.R.drawable.arrow_up_float else android.R.drawable.arrow_down_float
                  adjustViewBounds=true
                  scaleType= ImageView.ScaleType.CENTER_INSIDE
              }.lparams{
                  width=0
                  height= matchParent
                  weight=1F
              }

              lparams {
                  height = dip(48)
                  width = matchParent
                  //paddingLeft = dip(24)
                  //paddingRight = dip(24)
              }
              setPadding(dip(24),0,dip(24),0)
          }
      }
    }

    override fun getChildrenCount(groupPosition: Int): Int {
       return categoriesGroups?.get(groupPosition)?.searchResults?.size?:0 //podcast[groupPosition]?.related_episodes?.size?:0
    }

    override fun getChild(groupPosition: Int, childPosition: Int):SearchResults.ResultItem? {
        return categoriesGroups?.get(groupPosition)?.searchResults?.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
      return  groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {

        var show= this.getChild(groupPosition,childPosition)
        return with(parent!!.context){

            linearLayout {
                lparams(height= matchParent, width = matchParent)
                setPadding(dip(8),dip(8),dip(8),dip(8))


                frameLayout {
                    setBackgroundColor(R.color.colorAccent)
                    imageView{
                        show?.artworkUrl60?.let{loadUrl(it)}
                        adjustViewBounds=true
                        scaleType= ImageView.ScaleType.FIT_XY

                    }.lparams{
                        width= matchParent
                        height= matchParent

                    }
                }.lparams{
                    weight = 1F
                    height= wrapContent
                    width=0
                    marginEnd=dip(8)
                }
                verticalLayout {
                    textView {
                        text=show?.collectionName
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
        return  categoriesGroups?.size?:0
    }

     data class GenreGroup(var genre: Genre,var searchResults: List<SearchResults.ResultItem>?)


}