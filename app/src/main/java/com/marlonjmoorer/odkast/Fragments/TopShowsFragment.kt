package com.marlonjmoorer.odkast.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marlonjmoorer.odkast.Adapters.ShowListAdapter
import com.marlonjmoorer.odkast.Helpers.AudioSearch
import com.marlonjmoorer.odkast.Helpers.PodcastSearch
import com.marlonjmoorer.odkast.Helpers.asycHandler
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView


/**
 * Created by marlonmoorer on 5/29/17.
 */
class TopShowsFragment : Fragment() {
    var  _view: View?=null
    var showListView:RecyclerView?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       _view= getUI(container!!)
       // _view = inflater?.inflate(R.layout.fragment_top,container,false)
        return _view
    }

    private fun getUI(container: ViewGroup): View? =with(container){
        linearLayout {
            lparams(width = matchParent,height = matchParent)
            padding=dip(8)
            showListView= recyclerView{

            }.lparams{
                width = matchParent
                height = matchParent

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(activity!!);


        doAsync(asycHandler()){

            //var ai=AudioSearch.getInstance(context)
            var searchResult=  PodcastSearch().GetTopPodcast() //ai.GetTopShows()
            uiThread {

                showListView?.adapter = ShowListAdapter(searchResult)//PodcastGridAdapter(searchResult)
                showListView?.setLayoutManager(linearLayoutManager);
                showListView?.setHasFixedSize(true);

               /* grid.onItemClick { parent, view, position, id ->
                   var show= grid.adapter.getItem(position) as ShowSearchResult.ResultsBean
                    this@TopShowsFragment.startActivityForResult<ShowDetailActivity>(0,ShowDetailActivity.id_key to show.id)
                }*/
            }
        }


    }
}