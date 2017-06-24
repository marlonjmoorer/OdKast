package com.marlonjmoorer.odkast.Adapters

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.marlonjmoorer.odkast.Helpers.OnPodcastSelectedLister
import com.marlonjmoorer.odkast.Helpers.database
import com.marlonjmoorer.odkast.Helpers.loadUrl
import com.marlonjmoorer.odkast.Models.SearchResults
import com.marlonjmoorer.odkast.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by marlonmoorer on 6/21/17.
 */
class SubscriptionAdapter(results: SearchResults) : RecyclerView.Adapter<SubscriptionAdapter.ViewHolder>() {

    private val results: SearchResults

    init {
        this.results = results

    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var show = results.results[position]

        with(holder!!) {
            var listener= itemView.context as OnPodcastSelectedLister?
            title?.text = show.collectionName
            thumbnail?.loadUrl(show.artworkUrl100)
            itemView.onClick {
                listener?.onShowSelected("${show.collectionId}")
            }
            menu_button?.onClick { v->
                with(PopupMenu(itemView.context, v)) {
                    menuInflater.inflate(R.menu.show_menu, menu)

                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.unsubscribe->{
                                listener?.onUnSubscribe(show.collectionId.toString())
                                notifyItemChanged(position)
                            }
                            else -> {
                            }
                        }
                        false
                    }

                    show()
                }

            }
           /* subscribed?.imageResource = when (show.collectionId) {

                else -> R.drawable.icons8_circled_play_filled
            }*/
        }


    }

    override fun getItemCount(): Int {
        return results.results.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        var v = with(parent?.context!!) {
            cardView {
                useCompatPadding = true
                verticalLayout {
                    frameLayout {
                        imageView {
                            id = R.id.show_image
                            imageResource = R.drawable.icons8_play_2

                        }.lparams {
                            width = matchParent
                            height = matchParent
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(120)
                    }
                    linearLayout {
                        // padding = dip(16)
                        setBackgroundColor(resources.getColor(R.color.grey_900))
                        verticalLayout {
                            padding = dip(8)
                            gravity = Gravity.CENTER
                            textView {
                                id = R.id.show_title
                                text = "test"
                                textColor=resources.getColor(R.color.white)
                                textSize=sp(4).toFloat()
                            }.lparams {
                                width = matchParent
                                // height= matchParent
                            }

                        }.lparams {
                            width = 0
                            weight = 3f
                            height = matchParent
                        }
                        imageButton {
                            id = R.id.menu_view
                            imageResource = R.drawable.icons8_menu_2_filled
                           backgroundDrawable=null
                        }.lparams {
                            width = 0
                            weight = 1f
                            height = matchParent
                        }
                    }.lparams {
                        height = dip(48)
                        width = matchParent
                    }
                }
            }
        }




        return ViewHolder(v)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        var title: TextView? = null
        var thumbnail: ImageView? = null
        var menu_button: ImageButton? = null
        // var description: TextView? = null

        init {
            title = view.find<TextView>(R.id.show_title)
            thumbnail = view.find<ImageView>(R.id.show_image)
            menu_button = view.find<ImageButton>(R.id.menu_view)
        }
    }
}