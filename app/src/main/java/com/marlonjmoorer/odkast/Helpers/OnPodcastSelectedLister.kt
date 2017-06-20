package com.marlonjmoorer.odkast.Helpers

import com.marlonjmoorer.odkast.Models.PodcastFeed

/**
 * Created by marlonmoorer on 6/17/17.
 */
interface OnPodcastSelectedLister {
    fun onEpisodeSelected(episode: PodcastFeed.EpisodeItem)
    fun onShowSelected(id: String)
    fun onSubscribe(id:String)
    fun onUnSubscribe(id:String)
}