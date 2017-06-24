package com.marlonjmoorer.odkast.Adapters

import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
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
import com.marlonjmoorer.odkast.Models.TopPodcasts
import com.marlonjmoorer.odkast.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by marlonmoorer on 6/10/17.
 */
class PopularShowListAdapter(result: TopPodcasts) : RecyclerView.Adapter<PopularShowListAdapter.ShowViewHolder>(){

    private var result: TopPodcasts


    init {
        this.result = result
    }


    override fun onBindViewHolder(holder: ShowViewHolder?, position: Int) {
        var show = result.feed.results[position]

        with(holder!!) {
            var isSubbed= itemView.context.database.subsciptions?.contains(show.id)!!
            thumbnail!!.loadUrl(show.artworkUrl100)
            title!!.text = show.name

            var ctx = itemView.context as OnPodcastSelectedLister
            itemView.onClick {
                ctx.onShowSelected(show.id)
            }
            menuBtn?.imageResource= if(isSubbed) R.drawable.icons8_checked_checkbox_filled else R.drawable.icons8_add_filled
            menuBtn?.onClick { v ->
                if (isSubbed){
                    ctx.onUnSubscribe(show.id)
                }else{
                    ctx.onSubscribe(show.id)
                }
                notifyItemChanged(position)
                /*with(PopupMenu(itemView.context, v)) {
                    menuInflater.inflate(R.menu.show_menu, menu)

                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.subscribe -> {
                                ctx.onSubscribe(show.id)
                                notifyItemChanged(position)
                            }
                            R.id.unsubscribe->{
                                ctx.onUnSubscribe(show.id)
                                notifyItemChanged(position)
                            }
                            else -> {
                            }
                        }
                        false
                    }
                    if(!isSubbed){
                        menu.removeItem(R.id.subscribe)
                    }else{
                        menu.removeItem(R.id.unsubscribe)
                    }
                    show()
                }*/
            }
        }
    }

    override fun getItemCount(): Int {
        return result.feed.results.size
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ShowViewHolder {

        //var  _view = LayoutInflater.from(parent?.getContext()).inflate(R.layout.show_item, parent, false);
        var _view = with(parent!!.context) {
            relativeLayout {
                backgroundResource = R.drawable.under_line
                lparams {
                    height = dip(72)
                    width = matchParent


                }
                cardView {
                    setBackgroundColor(resources.getColor(R.color.black))
                    elevation = dip(10).toFloat()
                    //backgroundResource=R.drawable.card_outline
                    linearLayout {
                        padding = dip(8)

                        frameLayout {

                            imageView {
                                id = R.id.show_image
                                // loadUrl(show.image_files[0].file.url)
                                //setImageBitmap(holder.bitMap)
                                adjustViewBounds = true
                                scaleType = ImageView.ScaleType.FIT_XY
                            }.lparams {
                                height = matchParent
                                width = matchParent
                            }
                        }.lparams {
                            height = matchParent
                            width = 0
                            weight = 1f
                        }


                        //gravity = Gravity.CENTER
                        textView {
                            id = R.id.show_title
                            textColor = resources.getColor(R.color.white)
                            textSize = 19f
                            maxLines = 3
                            padding = dip(8)
                            gravity = Gravity.CENTER_VERTICAL

                        }.lparams {

                            width = 0
                            height = matchParent
                            weight = 5f
                        }


                        frameLayout {

                            imageButton {
                                id = R.id.overflow_menu
                                imageResource = R.drawable.icons8_menu_2
                               //backgroundColor=resources.getColor(R.color.white)
                                background=null
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
                            padding=dip(8)
                        }



                    }
                }.lparams {
                    height = matchParent
                    width = matchParent
                }
            }
        }

        return ShowViewHolder(_view)

    }




    inner class ShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        var title: TextView? = null
        var thumbnail: ImageView? = null
        var menuBtn: ImageButton? = null
        var description: TextView? = null

        init {
            title = view.find<TextView>(R.id.show_title)
            thumbnail = view.find<ImageView>(R.id.show_image)
            //  description = view.find<TextView>(R.id.show_discription)
            menuBtn = view.find<ImageButton>(R.id.overflow_menu)
        }
    }
}