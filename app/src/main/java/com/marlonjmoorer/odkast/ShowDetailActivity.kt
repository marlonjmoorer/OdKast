package com.marlonjmoorer.odkast

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.RelativeLayout
import com.marlonjmoorer.odkast.Adapters.EpisodeListAdapter
import com.marlonjmoorer.odkast.Helpers.AudioSearch
import com.marlonjmoorer.odkast.Helpers.asycHandler
import com.marlonjmoorer.odkast.Helpers.loadUrl
import com.marlonjmoorer.odkast.Models.EpisodeSearchResult
import com.marlonjmoorer.odkast.Models.Show
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onItemClick
import org.jetbrains.anko.uiThread

class ShowDetailActivity : AppCompatActivity() {

    var show: Show? =null
    companion object{
        val id_key="id_key"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_detail)

        if(intent!=null){

            var id= intent.getIntExtra(id_key,-1)
            doAsync(asycHandler()) {
                var ai= AudioSearch.getInstance(this@ShowDetailActivity)
                show =ai.GetShowById(id)
                var episodes=ai.GetEpisodesByShowId(id)

                uiThread {
                    var image=this@ShowDetailActivity.find<ImageView>(R.id.thumb_nail)
                    image.loadUrl(show?.image_files?.first()?.url?.thumb!!)
                    var episodeListView= this@ShowDetailActivity.find<ListView>(R.id.episode_listview)
                    episodeListView.adapter= EpisodeListAdapter(episodes)
                    episodeListView.onItemClick { parent, view, position, id  ->

                        var item=parent?.getItemAtPosition(position) as EpisodeSearchResult.ResultsBean
                        var intent=Intent()
                        intent.putExtra("ep_id",item.id)
                        setResult(Activity.RESULT_OK,intent)
                        finish()
                    }
                    this@ShowDetailActivity.find<RelativeLayout>(R.id.loading_view).visibility= View.GONE


                }


            }

        }
    }
}
