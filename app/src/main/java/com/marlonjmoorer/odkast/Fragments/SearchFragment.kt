package com.marlonjmoorer.odkast.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.ListView
import android.widget.SearchView
import com.marlonjmoorer.odkast.Adapters.PodcastListAdapter
import com.marlonjmoorer.odkast.Helpers.PodcastSearch
import com.marlonjmoorer.odkast.R
import com.marlonjmoorer.odkast.ShowDetailActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onItemClick
import org.jetbrains.anko.uiThread
import android.widget.Toast
import com.marlonjmoorer.odkast.R.id.toolbar
import org.jetbrains.anko.support.v4.longToast
import android.support.v4.view.MenuItemCompat.expandActionView
import android.support.v4.view.MenuItemCompat.getActionView
import org.jetbrains.anko.sdk25.coroutines.onQueryTextListener
import kotlin.coroutines.experimental.CoroutineContext

import android.support.v7.app.AppCompatActivity
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.marlonjmoorer.odkast.Helpers.OnPodcastSelectedLister
import org.jetbrains.anko.support.v4.act


/**
 * Created by marlonmoorer on 6/17/17.
 */


class SearchFragment : Fragment() {
    private var resultList: ListView? = null
    private var searchView: FloatingSearchView? = null



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater?.inflate(R.layout.activity_search, container, false)
        resultList = view?.find<ListView>(R.id.results)
        searchView= view?.find<FloatingSearchView>(R.id.search_view)
        searchView?.setOnSearchListener(object:FloatingSearchView.OnSearchListener {
            override fun onSearchAction(currentQuery: String?) {
              currentQuery?.isNotEmpty()?.let { search(currentQuery) }
            }

            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
               // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
        searchView?.setOnHomeActionClickListener(object: FloatingSearchView.OnHomeActionClickListener {
            override fun onHomeClicked() {
               fragmentManager.popBackStack()
            }
        })


        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        //setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    private fun search(query: String) {
        doAsync {

            var results = PodcastSearch().SearchShows(query)
            var adapter = PodcastListAdapter(results)
            uiThread {
                resultList?.adapter = adapter
                resultList?.onItemClick { parent, view, position, id ->
                   var listener=activity as OnPodcastSelectedLister
                   var show= adapter.getItem(position)
                   listener.onShowSelected(show.collectionId.toString())
                }
            }
        }
    }




    override fun onResume() {
        super.onResume()
        activity.supportInvalidateOptionsMenu();
    }
}