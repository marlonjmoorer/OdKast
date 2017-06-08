package com.marlonjmoorer.odkast.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.marlonjmoorer.odkast.Adapters.PodcastGridAdapter
import com.marlonjmoorer.odkast.Helpers.AudioSearch
import com.marlonjmoorer.odkast.Helpers.asycHandler
import com.marlonjmoorer.odkast.Models.ShowSearchResult
import com.marlonjmoorer.odkast.R
import com.marlonjmoorer.odkast.ShowDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onItemClick
import org.jetbrains.anko.support.v4.startActivityForResult


/**
 * Created by marlonmoorer on 5/29/17.
 */
class TopShowsFragment : Fragment() {
    var  _view: View?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _view= getUI(container!!)
        return _view
    }

    private fun getUI(container: ViewGroup): View? =with(container){
        linearLayout {
            lparams(width = matchParent,height = matchParent)

            gridView{
                id=  R.id.gridview
               numColumns=2


            }.lparams{
                width = matchParent
                height = matchParent
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doAsync(asycHandler()){

            var ai=AudioSearch.getInstance(context)
            var searchResult= ai.GetTopShows()
            uiThread {
               var grid= _view?.find<GridView>(R.id.gridview)!!
                grid.adapter = PodcastGridAdapter(searchResult)
                grid.onItemClick { parent, view, position, id ->
                   var show= grid.adapter.getItem(position) as ShowSearchResult.ResultsBean
                    this@TopShowsFragment.startActivityForResult<ShowDetailActivity>(0,ShowDetailActivity.id_key to show.id)
                }
            }
        }


    }
}