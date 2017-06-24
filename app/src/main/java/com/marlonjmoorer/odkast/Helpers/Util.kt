package com.marlonjmoorer.odkast.Helpers

import android.util.Log
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.GsonBuilder

/**
 * Created by marlonmoorer on 5/31/17.
 */



fun asycHandler()={ throwable : Throwable ->
    Log.e("ASYNC",throwable.message)
    Log.e("ASYNC",throwable.stackTrace.contentDeepToString())
    throw  throwable
}

