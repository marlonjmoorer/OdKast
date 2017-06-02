package com.marlonjmoorer.odkast

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.marlonjmoorer.odkast.Adapters.HomeAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
























class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var toolbar= find<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var viewPager= find<ViewPager>(R.id.viewpager)
        var tabLayout= find<TabLayout>(R.id.tabs)

        viewPager.adapter= HomeAdapter(supportFragmentManager)


        tabLayout.setupWithViewPager(viewPager)

        doAsync {

          //  var test=AudioSearch.getInstance(this@MainActivity).GetTopShows()
          //  var i=test.hashCode()

        }


    }

    override fun onResume() {
        super.onResume()

    }


}


