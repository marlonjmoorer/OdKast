package com.marlonjmoorer.odkast.Helpers

import android.content.Context
import android.net.Uri
import com.google.api.client.util.Base64
import com.google.gson.GsonBuilder
import com.marlonjmoorer.odkast.Models.*
import com.marlonjmoorer.odkast.R
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL



/**
 * Created by marlonmoorer on 5/31/17.
 */
public class  AudioSearch private constructor(context: Context)  {
    val token:String?
    val baseUrl:Uri?
    val context:Context
    init {
            this.context=context
            token=getAuthToken()
            baseUrl= Uri.Builder()
                    .scheme("https")
                    .authority("www.audiosear.ch")
                    .build()
    }

    inline fun baseApiUrl(body: (u:Uri.Builder)->Uri.Builder):String{
        var uri=Uri.Builder()
                .scheme("https")
                .authority("www.audiosear.ch")
                .appendPath("api")
                .appendQueryParameter("access_token",token)
       return body(uri).build().toString()
    }





    companion object {

        lateinit var INSTANCE: AudioSearch

        private var initialized:Boolean=false

        fun getInstance(context: Context) : AudioSearch{

            if(!initialized) {

                INSTANCE = AudioSearch(context)
                initialized=true
            }
            return INSTANCE
        }

        private val SHARED_PREF_NAME = "SHARED_PREF_NAME"
        private val TOKEN_KEY = "TOKEN_KEY"

    }

   private  fun generateToken():String{
        var client_id= context.resources.getString(R.string.api_key)
        var client_secret= context.resources.getString(R.string.api_secret)
        var uri= Uri.Builder()
                .scheme("https")
                .authority("www.audiosear.ch")
                .appendPath("oauth")
                .appendPath("token")
                .appendQueryParameter("grant_type","client_credentials")
                .build()

        var url= URL(uri.toString());
        val encodedBytes = Base64.encodeBase64("$client_id:$client_secret".toByteArray())
        var conn= url.openConnection() as HttpURLConnection
        conn.setRequestProperty("Authorization","Basic ${String(encodedBytes)}")
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        conn.requestMethod="POST"
        conn.connect()
        var result=conn.inputStream.bufferedReader().use { JSONObject(it.readText()) }

        return result.getString("access_token")
    }

    fun GetTrendingPodcast(): List<TrendingPodcast> {
         /* var url= baseUrl?.buildUpon()
                  ?.appendPath("api")
                  ?.appendPath("trending")
                  ?.appendQueryParameter("access_token",token)
                  ?.build()
                  ?.toString()*/

           var url= baseApiUrl { it.appendEncodedPath("trending") }

            var json =URL(url).readText()

        val gson = GsonBuilder().create()
        var output:ArrayList<TrendingPodcast> = ArrayList<TrendingPodcast>()
        var jsonArray=JSONArray(json)
        for (i in 0..jsonArray.length()-1){

            output.add(Util.parseJson<TrendingPodcast>(jsonArray.getString(i).toString()))
        }
        return  output
    }

    fun GetTopShows2():List<Show>{

        var url= baseApiUrl { it.appendEncodedPath("chart_daily") }
        var json =URL(url).readText()
        var ids=Util.parseJson<PodcastChart>(json).shows.values.map{it.id}

        var shows=Util.parseJson<PodcastChart>(json).shows.values.map {this.GetShowById(it.id)}
        return shows//gson.fromJson(json,PodcastChart::class.java)
    }
    fun GetTopShows():ShowSearchResult{
        var url= baseApiUrl { it.appendEncodedPath("chart_daily") }
        var json =URL(url).readText()
        var query= Util.parseJson<PodcastChart>(json).shows.values.map{"id:${it.id}"}.joinToString()
        url= baseApiUrl { it.appendPath("search").appendPath("shows").appendPath("$query") }
        json =URL(url).readText()

        return Util.parseJson<ShowSearchResult>(json)
    }
    fun GetShowById(id:Int):Show{
        var url= baseApiUrl { it.appendEncodedPath("shows").appendPath("$id") }
        var json =URL(url).readText()


        return   Util.parseJson<Show>(json)
    }
    fun GetEpisodesByShowId(id: Int):EpisodeSearchResult{
        var url= baseApiUrl {
            it.appendPath("search")
            .appendPath("episodes")
            .appendPath("*")
            .appendQueryParameter("filters[show_id]","$id")
        }
        var json =URL(url).readText()

        //search/episodes
        return   Util.parseJson<EpisodeSearchResult>(json)
    }
    fun GetEpisodeById(id:Int):Episode{
        var url= baseApiUrl {
            it.appendPath("episodes")
                    .appendPath("$id")
        }
        var json =URL(url).readText()

        //search/episodes
        return   Util.parseJson<Episode>(json)
    }
    fun getAuthToken(): String? {
        val prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        var token =prefs.getString(TOKEN_KEY,"")
        return  when{
            token.isNullOrEmpty()-> {
                token=generateToken()
                setAuthToken(token)
                return token
            }
            else ->token
        }
    }

    fun setAuthToken(token: String) {
        val prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }


}