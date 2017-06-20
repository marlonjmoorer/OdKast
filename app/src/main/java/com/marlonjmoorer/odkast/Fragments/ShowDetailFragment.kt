package com.marlonjmoorer.odkast

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.*
import android.widget.*
import com.marlonjmoorer.odkast.Adapters.EpisodeListAdapter
import com.marlonjmoorer.odkast.Helpers.*
import com.marlonjmoorer.odkast.Models.PodcastFeed
import com.marlonjmoorer.odkast.Models.SearchResults
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onItemClick

class ShowDetailFragment : DialogFragment() {

    var show: SearchResults.ResultItem? = null


    companion object {
        val id_key = "id_key"

        fun newInstace(id: String): ShowDetailFragment {
            var fragment = ShowDetailFragment()
            val args = Bundle()
            args.putString(id_key, id)
            fragment.setArguments(args)
            return fragment
        }
    }

    private var collapsingToolbar: CollapsingToolbarLayout? = null
    private var contentView: View? = null
    private var toolbar: Toolbar? = null
    private var image: ImageView? = null
    private var episodeListView: ListView? = null
    // private  var title:TextView?=null
    private var description: TextView? = null
    private var loadingView: View? = null
    private var listener: OnPodcastSelectedLister? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentView = inflater?.inflate(R.layout.activity_show_detail, container, false)
        toolbar = contentView?.find<Toolbar>(R.id.toolbar)

        collapsingToolbar = contentView?.find<CollapsingToolbarLayout>(R.id.collapsingToolbar)
        image = contentView?.find<ImageView>(R.id.thumb_nail)
        episodeListView = contentView?.find<ListView>(R.id.episode_listview)
        loadingView = contentView?.find<View>(R.id.loading_view)
        description = contentView?.find<TextView>(R.id.description)

        // loadingView.visibility = View.VISIBLE


        return contentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        //activity.supportInvalidateOptionsMenu()
    }

    override fun onResume() {
        super.onResume()
        // activity.supportInvalidateOptionsMenu();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if (arguments.containsKey(id_key)) {
            // view?.loadingScreen(true)
            var id = arguments.getString(id_key)
            doAsync(asycHandler()) {
                show = PodcastSearch().GetShowById(id) //ai.GetShowById(id)
                var feed = PodcastSearch().getPodcastFeed(show?.feedUrl!!)


                uiThread {


                    collapsingToolbar?.title = show?.collectionName

                    collapsingToolbar?.setStatusBarScrimColor(resources.getColor(R.color.red_500))
                    image?.loadUrl(show?.artworkUrl600!!)

                    episodeListView?.adapter = EpisodeListAdapter(feed)
                    ViewCompat.setNestedScrollingEnabled(episodeListView, true)

                    episodeListView?.onItemClick { parent, view, position, id ->

                        var episode = parent?.getItemAtPosition(position) as PodcastFeed.EpisodeItem


                        buidDialog(episode).show()
                    }
                    description?.text = Html.fromHtml(feed.feed.description)
                    // loadingView.visibility = View.GONE
                    //  view?.loadingScreen(false)
                }


            }

        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as OnPodcastSelectedLister
    }


    fun buidDialog(episode: PodcastFeed.EpisodeItem): Dialog {
        var dialog = Dialog(this.context)

        with(dialog) {
            setContentView(R.layout.episode_detail)
            find<TextView>(R.id.detail_title).text = episode.title
            find<TextView>(R.id.detail_description).text = Html.fromHtml(episode.description)
            find<ImageView>(R.id.detail_image).loadUrl(episode.thumbnail)
            find<FloatingActionButton>(R.id.detail_play).onClick {
                dismiss()
                listener?.onEpisodeSelected(episode)
            }
        }
        return dialog
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        inflater?.inflate(R.menu.menu_blank, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}
