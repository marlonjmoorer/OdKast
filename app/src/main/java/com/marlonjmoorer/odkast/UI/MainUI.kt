package com.marlonjmoorer.odkast.UI

import android.app.Activity
import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.support.v4.viewPager
import org.jetbrains.anko.toolbar

/**
 * Created by marlonmoorer on 5/29/17.
 */
class MainUI:AnkoComponent<Activity> {
    override fun createView(ui: AnkoContext<Activity>): View =with(ui){
        relativeLayout {

            toolbar {

            }

            viewPager {  }





        }

    }


}