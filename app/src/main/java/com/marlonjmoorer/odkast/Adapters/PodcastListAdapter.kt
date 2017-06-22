package com.marlonjmoorer.odkast.Adapters

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.marlonjmoorer.odkast.Helpers.loadUrl
import com.marlonjmoorer.odkast.Models.SearchResults
import com.marlonjmoorer.odkast.R
import org.jetbrains.anko.*

/**
 * Created by marlonmoorer on 5/30/17.
 */
class PodcastListAdapter(results: SearchResults) : BaseAdapter() {


    private val results: SearchResults

    init {
        this.results = results
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var show = this.getItem(position)

        return with(parent!!.context) {

            linearLayout {
                setBackgroundColor(resources.getColor(R.color.colorPrimary))
                lparams {
                    height = dip(80)
                    width = matchParent
                    padding = dip(8)
                    //bottomMargin=dip(4)
                }

                frameLayout {
                    setBackgroundColor(R.color.colorAccent)
                    imageView {
                        loadUrl(show.artworkUrl100)
                        adjustViewBounds = true
                        scaleType = ImageView.ScaleType.FIT_XY

                    }.lparams {
                        width = matchParent
                        height = matchParent


                    }
                }.lparams {
                    weight = 1F
                    height = matchParent
                    width = dip(0)
                }
                verticalLayout {

                    textView {
                        text = show.collectionName
                        textColor=resources.getColor(R.color.white)
                    }.lparams {
                        width = wrapContent
                        height = wrapContent

                    }
                    /*textView {
                        text = show.artistName
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                    }*/
                }.lparams {
                    height = wrapContent
                    weight = 4F
                    width = dip(0)
                    marginStart=dip(8)
                    gravity= Gravity.CENTER_VERTICAL

                }

            }

        }
    }

    override fun getItem(position: Int): SearchResults.ResultItem {
        return this.results.results[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {

        return this.results.results.size
    }

    fun update() {
        notifyDataSetChanged()
    }


}