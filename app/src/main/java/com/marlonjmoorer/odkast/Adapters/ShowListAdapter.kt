package com.marlonjmoorer.odkast.Adapters

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.marlonjmoorer.odkast.Helpers.loadUrl
import com.marlonjmoorer.odkast.Models.ShowSearchResult
import com.marlonjmoorer.odkast.R
import com.marlonjmoorer.odkast.ShowDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by marlonmoorer on 6/10/17.
 */
class ShowListAdapter(result: ShowSearchResult) : RecyclerView.Adapter<ShowListAdapter.ShowViewHolder>() {

    private var result: ShowSearchResult

    init {
        this.result = result
    }


    override fun onBindViewHolder(holder: ShowViewHolder?, position: Int) {
        var show = result.results[position]

        with(holder!!){
            thumbnail!!.loadUrl(show.image_files[0].file.url)
            title!!.text =show.title
            description!!.text= Html.fromHtml(show.description)
            itemView.onClick {
                var ctx=holder.itemView.context as Activity
                var intent= Intent(ctx,ShowDetailActivity::class.java)
                intent.putExtra(ShowDetailActivity.id_key ,show.id)
                ctx.startActivityForResult(intent,0)
            }
        }

    }

    override fun getItemCount(): Int {
        return result.results.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ShowViewHolder {

        //var  _view = LayoutInflater.from(parent?.getContext()).inflate(R.layout.show_item, parent, false);
        var _view = with(parent!!.context) {
            relativeLayout {

                lparams {
                    height = dip(180)
                    width = matchParent
                    //bottomMargin=dip(4)
                }
                cardView {
                   // setBackgroundColor(resources.getColor(R.color.black))
                   // elevation=dip(10).toFloat()
                    //backgroundResource=R.drawable.card_outline
                    linearLayout {
                        padding=dip(8)
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
                        verticalLayout {

                            gravity = Gravity.CENTER
                            textView {
                                id = R.id.show_title
                                textColor = resources.getColor(R.color.black)
                                textSize=16f
                                maxLines = 3
                                gravity =Gravity.CENTER
                              //  setBackgroundColor(resources.getColor(R.color.green_400))
                            }.lparams {

                                width = matchParent
                                height = 0
                                weight = 1f
                            }
                            textView {
                                id = R.id.show_discription
                              //  setBackgroundColor(resources.getColor(R.color.blue_500))
                                //textColor = resources.getColor(R.color.white)
                                padding=dip(8f)
                                maxLines = 4
                                gravity =Gravity.LEFT
                            }.lparams {

                                width = matchParent
                                height = 0
                                weight = 3f
                            }
                        }.lparams {
                            height = matchParent
                            width = 0
                            weight = 1f
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
        var bitMap: Bitmap? = null
        var description:  TextView? = null

        init {
            title = view.find<TextView>(R.id.show_title)
            thumbnail = view.find<ImageView>(R.id.show_image)
            description = view.find<TextView>(R.id.show_discription)
        }
    }
}