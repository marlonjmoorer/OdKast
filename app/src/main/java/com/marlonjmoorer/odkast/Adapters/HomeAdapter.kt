package com.marlonjmoorer.odkast.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.marlonjmoorer.odkast.Fragments.CategoriesFragment
import com.marlonjmoorer.odkast.Fragments.SubscriptionFragment
import com.marlonjmoorer.odkast.Fragments.TopShowsFragment

/**
 * Created by marlonmoorer on 5/29/17.
 */
class HomeAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var manager:FragmentManager;
    var fragments= mapOf(
            "Subscription" to SubscriptionFragment(),
            "Top" to TopShowsFragment(),
            "Categories" to CategoriesFragment())

    init {
        this.manager=fm
    }

    override fun getItem(position: Int): Fragment {
      return this.fragments.values.elementAt(position)
    }

    override fun getCount(): Int {
        return this.fragments.values.count()
    }

    override fun getPageTitle(position: Int): CharSequence {
        return this.fragments.keys.elementAt(position)
    }
}