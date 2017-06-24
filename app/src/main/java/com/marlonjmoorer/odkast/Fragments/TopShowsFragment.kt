package com.marlonjmoorer.odkast.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marlonjmoorer.odkast.Adapters.PopularShowListAdapter
import com.marlonjmoorer.odkast.Helpers.PodcastSearch
import com.marlonjmoorer.odkast.Helpers.asycHandler
import com.marlonjmoorer.odkast.Helpers.database
import com.marlonjmoorer.odkast.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import java.util.*


/**
 * Created by marlonmoorer on 5/29/17.
 */
class TopShowsFragment : Fragment(), Observer {

    override fun update(o: Observable?, arg: Any?) {
        loadItems()
    }

    var  _view: View?=null
    var showListView:RecyclerView?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       _view= getUI(container!!)
       // _view = inflater?.inflate(R.layout.fragment_top,container,false)
        return _view
    }

    private fun getUI(container: ViewGroup): View? =with(container){
        linearLayout {
            setBackgroundColor(resources.getColor(R.color.colorPrimaryLight))
            lparams(width = matchParent,height = matchParent)
            //padding=dip(8)
            showListView= recyclerView{


            }.lparams{
                width = matchParent
                height = matchParent

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadItems()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.database.onUpdate(this)
    }

    private fun loadItems(){
        doAsync(asycHandler()){


            var searchResult=  PodcastSearch().GetTopPodcast()
            uiThread {

                showListView?.adapter = PopularShowListAdapter(searchResult)
                showListView?.setLayoutManager(LinearLayoutManager(activity!!));
                showListView?.setHasFixedSize(true);
            }
        }
    }
}