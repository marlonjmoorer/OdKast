package com.marlonjmoorer.odkast

import android.app.Dialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.*
import com.marlonjmoorer.odkast.Adapters.EpisodeListAdapter
import com.marlonjmoorer.odkast.Helpers.*
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

    private var  collapsingToolbar: CollapsingToolbarLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_detail)
        var toolbar = find<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.inflateMenu(R.menu.menu_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        collapsingToolbar=find<CollapsingToolbarLayout>(R.id.collapsingToolbar)
        val image = find<ImageView>(R.id.thumb_nail)
        val episodeListView = find<ListView>(R.id.episode_listview)
        val loadingView = find<RelativeLayout>(R.id.loading_view)
        val title = find<TextView>(R.id.title)
        val description = find<TextView>(R.id.description)

       // loadingView.visibility = View.VISIBLE
        contentView?.loadingScreen(true)
        if (intent != null) {

            var id = intent.getIntExtra(id_key, -1)
            doAsync(asycHandler()) {
                //var ai = AudioSearch.getInstance(this@ShowDetailActivity)
                show = PodcastSearch().GetShowById(id) //ai.GetShowById(id)
                var feed=   PodcastSearch().getPodcastFeed(show?.feedUrl!!)


                uiThread {

                    title.visibility= View.GONE
                    collapsingToolbar?.title=show?.collectionName
                    //collapsingToolbar?.setExpandedTitleColor(resources.getColor(android.R.color.transparent));
                    collapsingToolbar?.setStatusBarScrimColor(resources.getColor(R.color.red_500))
                    image.loadUrl(show?.artworkUrl600!!)

                    episodeListView.adapter = EpisodeListAdapter(feed)
                    ViewCompat.setNestedScrollingEnabled(episodeListView, true)

                    episodeListView.onItemClick { parent, view, position, id ->

                        var episode = parent?.getItemAtPosition(position) as PodcastFeed.EpisodeItem


                       buidDialog(episode).show()
                    }
                    title.text = show?.collectionName
                    description.text = Html.fromHtml(feed.feed.description)
                   // loadingView.visibility = View.GONE
                    contentView?.loadingScreen(false)
                }


            }

        }
    }




    fun buidDialog(episode: PodcastFeed.EpisodeItem): Dialog {
        var dialog = Dialog(this@ShowDetailActivity)

        with(dialog) {
            setContentView(R.layout.episode_detail)
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

   /* override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater

        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }*/






}
