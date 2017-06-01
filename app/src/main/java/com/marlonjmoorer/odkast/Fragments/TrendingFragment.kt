package com.marlonjmoorer.odkast.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import com.marlonjmoorer.odkast.Adapters.TrendingListAdapter
import com.marlonjmoorer.odkast.Helpers.AudioSearch
import com.marlonjmoorer.odkast.Helpers.asycHandler
import com.marlonjmoorer.odkast.R
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onGroupCollapse
import org.jetbrains.anko.sdk25.coroutines.onGroupExpand
import org.jetbrains.anko.sdk25.coroutines.onItemClick
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.uiThread


/**
 * Created by marlonmoorer on 5/29/17.
 */
class TrendingFragment: Fragment() {


    var _view:View?=null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _view= inflater?.inflate(R.layout.fragment_trending,container,false)
        return _view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        doAsync(asycHandler()){
            var ai= AudioSearch.getInstance(activity)
            var podcast=ai.GetTrendingPodcast()
            uiThread {
                //_view?.find<ListView>(R.id.listview)?.adapter= PodcastListAdapter(podcast)
                var eview=  _view?.find<ExpandableListView>(R.id.listview)
                eview?.setAdapter(TrendingListAdapter(podcast))
                eview?.onItemClick { p0, p1, p2, p3 ->{
                    longToast("Clicked")
                }}
                eview?.onGroupExpand {

                }
                eview?.onGroupCollapse {

                }

            }
        }

    }
}