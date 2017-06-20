package com.marlonjmoorer.odkast.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.marlonjmoorer.odkast.Adapters.PodcastListAdapter
import com.marlonjmoorer.odkast.Adapters.ShowListAdapter
import com.marlonjmoorer.odkast.Helpers.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.IntParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.toast
import java.util.*

/**
 * Created by marlonmoorer on 6/12/17.
 */
class SubscriptionFragment : Fragment(),Observer {

    var subListView: ListView? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getUI(container!!)

    }

    private fun getUI(container: ViewGroup): View? = with(container) {

        verticalLayout {
            subListView = listView {}
                    .lparams {
                        width = matchParent
                        height = matchParent
                    }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.database.onUpdate(this)
    }
    override fun onResume() {
        super.onResume()
        loadSubscriptions()
    }
    fun loadSubscriptions() {

        doAsync {
            activity.database.pultus?.let {
                var subs = it.find(Subscription())
                if (subs.size != subListView?.count) {
                    var ids = subs.map {
                        (it as Subscription).show_id
                    }

                    var shows = PodcastSearch().GetShowByManyIds(ids.joinToString(","))
                    uiThread {
                        shows?.let {
                            var adapter=PodcastListAdapter(shows)
                            subListView?.adapter=adapter

                        }
                    }

                }
            }
        }
    }

    override fun update(o: Observable?, arg: Any?) {
       loadSubscriptions()
    }
}

