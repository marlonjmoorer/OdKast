package com.marlonjmoorer.odkast.Adapters

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.marlonjmoorer.odkast.Helpers.OnPodcastSelectedLister
import com.marlonjmoorer.odkast.Helpers.database
import com.marlonjmoorer.odkast.Helpers.loadUrl
import com.marlonjmoorer.odkast.Models.SearchResults
import com.marlonjmoorer.odkast.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by marlonmoorer on 5/30/17.
 */
class PodcastListAdapter(results: SearchResults) : RecyclerView.Adapter<PodcastListAdapter.ViewHolder>() {


    private val results: SearchResults

    init {
        this.results = results
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        var v = with(parent!!.context) {

            linearLayout {
                setBackgroundColor(resources.getColor(R.color.black))
                lparams {
                    height = dip(80)
                    width = matchParent
                    padding = dip(8)
                    margin = dip(1)
                    //bottomMargin=dip(4)
                }

                frameLayout {
                    setBackgroundColor(R.color.colorAccent)
                    imageView {
                        id = R.id.item_image
                        //loadUrl(show.artworkUrl100)
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
                        id = R.id.item_title
                        //text = show.collectionName
                        textColor = resources.getColor(R.color.white)
                    }.lparams {
                        width = wrapContent
                        height = wrapContent

                    }

                }.lparams {
                    height = wrapContent
                    weight = 4F
                    width = dip(0)
                    marginStart = dip(8)
                    gravity = Gravity.CENTER_VERTICAL

                }
                frameLayout {

                    imageButton {
                        id = R.id.item_action
                        imageResource = R.drawable.icons8_menu_2
                        //backgroundColor=resources.getColor(R.color.white)
                        background = null
                        adjustViewBounds = true
                        scaleType = ImageView.ScaleType.FIT_XY


                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                    }


                }.lparams {
                    width = 0
                    height = matchParent
                    weight = .9f
                    padding = dip(8)
                }

            }

        }

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var show = results.results[position]
        with(holder!!) {
            var isSubbed:Boolean = itemView.context.database.subsciptions!!.contains("${show.collectionId}")


            title.text = show.collectionName
            image.loadUrl(show.artworkUrl60)

            var listener = itemView.context
            itemView.onClick {

                if (listener is OnPodcastSelectedLister) {
                    listener.onShowSelected(show.collectionId.toString())
                }
            }
            action.imageResource = if (isSubbed) R.drawable.icons8_checked_checkbox_filled else R.drawable.icons8_add_filled
            action.onClick {
                if (listener is OnPodcastSelectedLister) {
                    if (isSubbed) {
                        listener.onUnSubscribe("${show.collectionId}")
                    } else {
                        listener.onSubscribe("${show.collectionId}")
                    }
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return results.results.size
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    fun update() {
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView
        val image: ImageView
        val action: ImageButton

        init {
            title = view.find<TextView>(R.id.item_title)
            image = view.find<ImageView>(R.id.item_image)
            action = view.find<ImageButton>(R.id.item_action)
        }
    }


}