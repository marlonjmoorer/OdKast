package com.marlonjmoorer.odkast.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.marlonjmoorer.odkast.Fragments.TopFragment
import com.marlonjmoorer.odkast.Fragments.TrendingFragment

/**
 * Created by marlonmoorer on 5/29/17.
 */
class HomeAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var manager:FragmentManager;
    init {
        this.manager=fm;

    }

    override fun getItem(position: Int): Fragment = when(position){

        0-> TrendingFragment()
        1-> TopFragment()
        else -> TrendingFragment()

    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "DOPE"
    }
}