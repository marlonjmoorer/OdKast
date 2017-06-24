package com.marlonjmoorer.odkast.Fragments

import android.app.Dialog
import android.content.Context
import com.marlonjmoorer.odkast.Models.PodcastFeed
import android.view.View

import android.os.Bundle
import android.support.annotation.NonNull
import android.support.design.widget.*
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import com.marlonjmoorer.odkast.Helpers.OnPodcastSelectedLister
import com.marlonjmoorer.odkast.Helpers.loadUrl
import com.marlonjmoorer.odkast.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by marlonmoorer on 6/23/17.
 */
class EpisodeDetailFragment(episode: PodcastFeed.EpisodeItem):BottomSheetDialogFragment() {

    private var  episode: PodcastFeed.EpisodeItem
    private  var listener:OnPodcastSelectedLister?=null

    init {
        this.episode=episode
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog


        with(dialog) {
            setContentView(R.layout.episode_detail)
            find<TextView>(R.id.detail_title).text = episode.title
            find<TextView>(R.id.detail_description).text = Html.fromHtml(episode.description)
            find<ImageView>(R.id.detail_image).loadUrl(episode.thumbnail)
            find<FloatingActionButton>(R.id.detail_play).onClick {
                listener?.onEpisodeSelected(episode)
                dismiss()
            }
        }


        return dialog
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is OnPodcastSelectedLister){
            listener=context
        }
    }

}