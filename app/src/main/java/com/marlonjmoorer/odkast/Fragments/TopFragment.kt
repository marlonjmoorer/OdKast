package com.marlonjmoorer.odkast.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marlonjmoorer.odkast.R


/**
 * Created by marlonmoorer on 5/29/17.
 */
class TopFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      return inflater?.inflate(R.layout.fragment_top,container,false)
    }
}