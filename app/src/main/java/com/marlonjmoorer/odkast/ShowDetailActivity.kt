package com.marlonjmoorer.odkast

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import com.marlonjmoorer.odkast.Adapters.EpisodeListAdapter
import com.marlonjmoorer.odkast.Helpers.PodcastSearch
import com.marlonjmoorer.odkast.Helpers.asycHandler
import com.marlonjmoorer.odkast.Helpers.loadUrl
import com.marlonjmoorer.odkast.Helpers.toJsonString
import com.marlonjmoorer.odkast.Models.EpisodeSearchResult
import com.marlonjmoorer.odkast.Models.PodcastFeed
import com.marlonjmoorer.odkast.Models.SearchResults
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onItemClick

class ShowDetailActivity : AppCompatActivity() {

    var show:SearchResults.ResultItem? = null

    companion object {
        val id_key = "id_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_detail)
        val image = find<ImageView>(R.id.thumb_nail)
        val episodeListView = find<ListView>(R.id.episode_listview)
        val loadingView = find<RelativeLayout>(R.id.loading_view)
        val title = find<TextView>(R.id.title)
        val description = find<TextView>(R.id.description)
        loadingView.visibility = View.VISIBLE
        if (intent != null) {

            var id = intent.getIntExtra(id_key, -1)
            doAsync(asycHandler()) {
                //var ai = AudioSearch.getInstance(this@ShowDetailActivity)
                show = PodcastSearch().GetShowById(id) //ai.GetShowById(id)
                var feed=   PodcastSearch().getPodcastFeed(show?.feedUrl!!)


                uiThread {

                    image.loadUrl(show?.artworkUrl600!!)

                    episodeListView.adapter = EpisodeListAdapter(feed)

                    episodeListView.onItemClick { parent, view, position, id ->

                        var episode = parent?.getItemAtPosition(position) as PodcastFeed.EpisodeItem

                        //showDetails(episode)
                        buidDialog(episode).show()
                    }
                    title.text = show?.collectionName
                    description.text = Html.fromHtml(feed.feed.description)
                    loadingView.visibility = View.GONE
                }


            }

        }
    }


    fun showDetails(episode: EpisodeSearchResult.ResultsBean) {
        alert {

            customView {

                verticalLayout {
                    lparams {
                        width = matchParent
                        height = matchParent
                    }

                    linearLayout {
                        setBackgroundResource(R.color.colorPrimaryDark)
                        frameLayout {
                            imageView {
                                loadUrl(episode.image_urls.thumb)
                                adjustViewBounds = true
                                scaleType = ImageView.ScaleType.FIT_XY

                            }.lparams {
                                height = matchParent
                                width = matchParent
                            }
                        }.lparams {
                            padding = dip(10)
                            width = 0
                            height = matchParent
                            weight = 1F

                        }

                        textView {
                            textSize = 24f
                            text = episode.title
                            textColor = resources.getColor(R.color.white)
                        }.lparams {
                            padding = dip(16)
                            width = 0
                            height = matchParent
                            weight = 3F
                            marginStart = dip(8)
                        }


                    }.lparams {
                        setPadding(0, 0, 0, dip(20))
                        height = 0
                        width = matchParent
                        weight = 1f
                    }
                    verticalLayout {
                        floatingActionButton {
                            imageResource = R.drawable.icons8_play_filled

                        }.lparams {
                            gravity = Gravity.TOP or Gravity.RIGHT
                            topMargin = dip(-20)


                        }

                        textView {
                            text = Html.fromHtml(episode.description)
                        }.lparams {
                            margin = dip(16)
                        }

                    }.lparams {
                        //padding=dip(16)
                        height = 0
                        width = matchParent
                        weight = 1f

                    }


                    textView {
                        text = Html.fromHtml(episode.description)
                    }.lparams {
                        margin = dip(16)
                    }

                }
            }
        }.show()
    }

    fun buidDialog(episode: PodcastFeed.EpisodeItem): Dialog {
        var dialog = Dialog(this@ShowDetailActivity)

        with(dialog) {
            setContentView(R.layout.episode_detail_old)
            find<TextView>(R.id.detail_title).text = episode.title
            find<TextView>(R.id.detail_description).text = Html.fromHtml(episode.description)
            find<ImageView>(R.id.detail_image).loadUrl(episode.thumbnail)
            find<FloatingActionButton>(R.id.detail_play).onClick {
                dismiss()
                var intent = Intent(this@ShowDetailActivity,MainActivity::class.java)
                intent.putExtra("ep",episode.toJsonString())
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                //setResult(Activity.RESULT_OK, intent)
                //finish()
                startActivity(intent)

            }
        }
        return dialog
    }


}
