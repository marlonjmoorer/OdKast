package com.marlonjmoorer.odkast.Adapters

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.marlonjmoorer.odkast.Helpers.loadUrl
import com.marlonjmoorer.odkast.Models.ShowSearchResult
import com.marlonjmoorer.odkast.Models.TopPodcasts
import com.marlonjmoorer.odkast.R
import com.marlonjmoorer.odkast.ShowDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by marlonmoorer on 6/10/17.
 */
class ShowListAdapter(result: TopPodcasts) : RecyclerView.Adapter<ShowListAdapter.ShowViewHolder>() {

    private var result: TopPodcasts

    init {
        this.result = result
    }


    override fun onBindViewHolder(holder: ShowViewHolder?, position: Int) {
        var show = result.feed.results[position]

        with(holder!!) {
            thumbnail!!.loadUrl(show.artworkUrl100)
            title!!.text = show.name
            // description!!.text = Html.fromHtml(show.description)
            itemView.onClick { v ->
                var ctx = holder.itemView.context as Activity
                var intent = Intent(ctx, ShowDetailActivity::class.java)
                intent.putExtra(ShowDetailActivity.id_key, show.id.toInt())
                ctx.startActivityForResult(intent, 0)
            }
            menu?.onClick { v ->
                val popup = PopupMenu(itemView.context, v)
                popup.getMenuInflater().inflate(R.menu.show_menu, popup.getMenu())
                popup.setOnMenuItemClickListener { item ->
                    when (item) {


                    }
                }
                popup.show()
            }
        }

    }

    override fun getItemCount(): Int {
        return result.feed.results.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ShowViewHolder {

        //var  _view = LayoutInflater.from(parent?.getContext()).inflate(R.layout.show_item, parent, false);
        var _view = with(parent!!.context) {
            relativeLayout {

                lparams {
                    height = dip(80)
                    width = matchParent
                    //bottomMargin=dip(4)
                }
                cardView {
                    // setBackgroundColor(resources.getColor(R.color.black))
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
                            textColor = resources.getColor(R.color.black)
                            textSize = 16f
                            maxLines = 3
                            padding=dip(8)
                            //gravity = Gravity.CENTER
                            //  setBackgroundColor(resources.getColor(R.color.green_400))
                        }.lparams {

                            width = 0
                            height = matchParent
                            weight = 5f
                        }


                        imageButton {
                            id = R.id.overflow_menu
                            imageResource = R.drawable.icons8_menu_2
                            background = null

                        }.lparams {
                            height = dip(20)
                            width = dip(30)
                            gravity = Gravity.BOTTOM or Gravity.END
                            bottomMargin = dip(8)
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
        var menu: ImageButton? = null
        var description: TextView? = null

        init {
            title = view.find<TextView>(R.id.show_title)
            thumbnail = view.find<ImageView>(R.id.show_image)
          //  description = view.find<TextView>(R.id.show_discription)
            menu = view.find<ImageButton>(R.id.overflow_menu)
        }
    }
}