package com.marlonjmoorer.odkast.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.verticalLayout

/**
 * Created by marlonmoorer on 6/12/17.
 */
class SubscriptionFragment : Fragment() {
    var _view:View?=null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _view= getUI(container!!)
        return _view
    }

    private fun getUI(container: ViewGroup): View? =with(container){

        verticalLayout {


        }
    }
}