package com.marlonjmoorer.odkast.Adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.marlonjmoorer.odkast.Helpers.loadUrl
import com.marlonjmoorer.odkast.Models.ShowSearchResult
import com.marlonjmoorer.odkast.R
import org.jetbrains.anko.*


/**
 * Created by marlonmoorer on 6/1/17.
 */
class PodcastGridAdapter(result: ShowSearchResult) : BaseAdapter() {

    private var result: ShowSearchResult

    init {
        this.result = result
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var show = result.results[position]

        var holder: ViewHolderItem
        if (convertView == null) {

            holder = ViewHolderItem()

            var _view = with(parent!!.context) {
                verticalLayout {
                    lparams {
                        height = dip(250)
                        width = matchParent
                    }
                    frameLayout {
                        holder.thumbnail = imageView {
                            loadUrl(show.image_files[0].file.url)
                            adjustViewBounds = true
                            scaleType = ImageView.ScaleType.FIT_XY
                        }.lparams {
                            height = matchParent
                            width = matchParent
                        }

                    }.lparams {
                        height = 0
                        width = matchParent
                        weight = 5f
                    }
                    linearLayout {
                        setBackgroundColor(resources.getColor(R.color.colorAccent))
                        holder.title = textView {
                            text = show.title
                            textColor = resources.getColor(R.color.white)
                            maxLines = 3
                        }.lparams {
                            margin = dip(16)
                            width = 0
                            weight = 4F
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

                    }.lparams {
                        height = dip(48)
                        width = matchParent
                        //weight=1f
                    }


                }


            }
            _view.tag=holder;
            return _view
        } else {
            holder=convertView.tag as ViewHolderItem
            holder.thumbnail?.loadUrl(show.image_files[0].file.url)
            holder.title?.text= show.title
            return convertView!!
        }

    }


    override fun getItem(position: Int): Any {
        return result.results[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return result.results.size
    }
}

internal class ViewHolderItem {
    var title: TextView? = null
    var thumbnail: ImageView? = null
}


