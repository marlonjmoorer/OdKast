package com.marlonjmoorer.odkast

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.SearchView
import com.marlonjmoorer.odkast.Adapters.PodcastListAdapter
import com.marlonjmoorer.odkast.Helpers.PodcastSearch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onItemClick
import org.jetbrains.anko.uiThread


class SearchActivity : AppCompatActivity() {

    private var  list: ListView?=null
    private var  searchView: SearchView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        list=find<ListView>(R.id.results)

        handleIntent(intent)
        /*  var search= find<SearchView>(R.id.search)
        var list=find<ListView>(R.id.results)

        search.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                doAsync {

                    var results= PodcastSearch().SearchShows(query!!)
                    var adapter=PodcastListAdapter(results)
                    uiThread {
                        list.adapter=adapter
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               return true
            }

        })
        */
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.menu_search).getActionView() as SearchView
       // searchView.setSearchableInfo(
               // searchManager.getSearchableInfo(componentName))

        searchView?.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        var query=intent.getStringExtra(SearchManager.QUERY)
        searchView?.setQuery(query,false)

        return true


    }



    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            search(query)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
        // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                finish()

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun search(query:String){
        doAsync {

            var results= PodcastSearch().SearchShows(query)
            var adapter= PodcastListAdapter(results)
            uiThread {
                list?.adapter=adapter
                list?.onItemClick { parent, view, position, id ->
                    var show= adapter.getItem(position)
                    var intent = Intent(this@SearchActivity, ShowDetailActivity::class.java)
                    intent.putExtra(ShowDetailActivity.id_key,show.collectionId)
                    startActivityForResult(intent, 0)

                }
            }
        }
    }
}
