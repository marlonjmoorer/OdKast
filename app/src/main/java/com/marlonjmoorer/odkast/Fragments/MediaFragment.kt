package com.marlonjmoorer.odkast.Fragments

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marlonjmoorer.odkast.R

/**
 * Created by marlonmoorer on 6/18/17.
 */
class MediaFragment: BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

         return inflater?.inflate(R.layout.activity_show_detail, container);

    }
}