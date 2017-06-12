package com.marlonjmoorer.odkast.Adapters


import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.marlonjmoorer.odkast.Models.ShowSearchResult
import com.marlonjmoorer.odkast.R
import com.squareup.picasso.Picasso
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
            doAsync {
                holder.bitMap= Picasso.with(parent?.context).load(show.image_files[0].file.url).get();
            }

            var _view = with(parent!!.context) {

                verticalLayout {
                    lparams {
                        height = dip(250)
                        width = matchParent
                    }
                    frameLayout {
                        holder.thumbnail = imageView {
                            //loadUrl(show.image_files[0].file.url)
                            setImageBitmap(holder.bitMap)
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
                        setBackgroundColor(resources.getColor(R.color.black))
                        holder.title = textView {
                            text = show.title
                            textColor = resources.getColor(R.color.white)
                            //textSize=dip(16).toFloat()
                            maxLines = 3
                        }.lparams {
                            margin = dip(16)
                            width = 0
                            weight = 4F

                        }

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
           // holder.thumbnail?.loadUrl(show.image_files[0].file.url)
           holder.thumbnail?.setImageBitmap(holder.bitMap)
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
    var bitMap:Bitmap?=null
}


