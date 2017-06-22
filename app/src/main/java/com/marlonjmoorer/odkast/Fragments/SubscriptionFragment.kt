package com.marlonjmoorer.odkast.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.marlonjmoorer.odkast.Adapters.PodcastListAdapter
import com.marlonjmoorer.odkast.Adapters.PopularShowListAdapter
import com.marlonjmoorer.odkast.Adapters.SubscriptionAdapter
import com.marlonjmoorer.odkast.Helpers.*
import com.marlonjmoorer.odkast.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import java.util.*

/**
 * Created by marlonmoorer on 6/12/17.
 */
class SubscriptionFragment : Fragment(),Observer {

    var subListView: RecyclerView? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getUI(container!!)

    }

    private fun getUI(container: ViewGroup): View? = with(container) {

        verticalLayout {
            setBackgroundColor(resources.getColor(R.color.colorPrimary))
            subListView = recyclerView {


            }.lparams {
                width = matchParent
                height = matchParent
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadSubscriptions()
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
                if (subs.size != subListView?.childCount) {
                    var ids = subs.map {
                        (it as Subscription).show_id
                    }

                    var shows = PodcastSearch().GetShowByManyIds(ids.joinToString(","))
                    uiThread {
                        shows?.let {

                            var adapter=SubscriptionAdapter(shows)
                            subListView?.adapter=adapter
                            subListView?.layoutManager=GridLayoutManager(activity,2)

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

