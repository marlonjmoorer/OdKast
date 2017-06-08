package com.marlonjmoorer.odkast.Helpers

import android.util.Log
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.GsonBuilder

/**
 * Created by marlonmoorer on 5/31/17.
 */

class Util{


   companion object{

       inline fun <reified T:Any> parseJson(json:String):T{
           var gson = GsonBuilder().create()
           return gson.fromJson<T>(json)
       }
   }
}


fun asycHandler()={ throwable : Throwable ->
    Log.e("ASYNC",throwable.message)
    Log.e("ASYNC",throwable.stackTrace.contentDeepToString())
    throw  throwable
}