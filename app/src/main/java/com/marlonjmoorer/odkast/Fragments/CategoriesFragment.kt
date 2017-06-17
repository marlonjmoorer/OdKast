package com.marlonjmoorer.odkast.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import com.marlonjmoorer.odkast.Adapters.CategoriesListAdapter
import com.marlonjmoorer.odkast.Adapters.TrendingListAdapter
import com.marlonjmoorer.odkast.Helpers.AudioSearch
import com.marlonjmoorer.odkast.Helpers.Genre
import com.marlonjmoorer.odkast.Helpers.PodcastSearch
import com.marlonjmoorer.odkast.Helpers.asycHandler
import com.marlonjmoorer.odkast.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onGroupCollapse
import org.jetbrains.anko.sdk25.coroutines.onGroupExpand
import org.jetbrains.anko.sdk25.coroutines.onItemClick
import org.jetbrains.anko.support.v4.longToast


/**
 * Created by marlonmoorer on 5/29/17.
 */
class CategoriesFragment: Fragment() {


    var _view:View?=null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _view= getUI(container!!)
        return _view
    }

    fun getUI(viewGroup: ViewGroup):View= with(viewGroup){
        linearLayout {
           lparams(width = matchParent,height = matchParent)
            expandableListView{
               id=R.id.listview
               setGroupIndicator(null)
            }.lparams{
               width = matchParent
               height = matchParent
            }
        }
    }

    private var  adapter: CategoriesListAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        doAsync(asycHandler()){
            adapter=CategoriesListAdapter()
            uiThread {
                //_view?.find<ListView>(R.id.listview)?.adapter= PodcastListAdapter(podcast)
                var eview=  _view?.find<ExpandableListView>(R.id.listview)

                eview?.setAdapter(adapter)
                eview?.onItemClick { p0, p1, p2, p3 ->{
                    longToast("Clicked")
                }}
                eview?.onGroupExpand{groupPosition ->
                    adapter?.getGroup(groupPosition)?.let { if (it.searchResults==null)  populateDropdowns(it)}
                }
                eview?.onGroupCollapse {

                }

            }
        }

    }

    fun populateDropdowns(group: CategoriesListAdapter.GenreGroup){
        doAsync {
            group.searchResults=PodcastSearch().SearchShowsByGenre(group.genre).results
            uiThread { adapter?.notifyDataSetChanged() }
        }
    }
}