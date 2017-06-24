package com.marlonjmoorer.odkast.Helpers

import android.net.Uri
import com.marlonjmoorer.odkast.Models.PodcastFeed
import com.marlonjmoorer.odkast.Models.SearchResults
import com.marlonjmoorer.odkast.Models.TopPodcasts
import java.net.URL



/*
 * Created by marlonmoorer on 6/12/17.
 */
class PodcastSearch {

    val baseUrl:Uri?
    val rssUrl:Uri?
    init {
        val country_code="us"
        val limit="10"
        rssUrl=Uri.Builder()
                .scheme("https")
                .authority("rss.itunes.apple.com")
                .appendPath("api")
                .appendPath("v1")
                .appendPath(country_code)
                .appendPath("podcasts")
                .appendPath("top-podcasts")
                .appendPath(limit)
                .appendPath("explicit")
                .appendPath("json")
                .build()


        baseUrl= Uri.Builder()
                .scheme("https")
                .authority("itunes.apple.com")
                .build()
    }
    inline fun baseApiUrl(body: (u:Uri.Builder)->Uri.Builder):String{
        var uri=baseUrl?.buildUpon()!!
        return body(uri).build().toString()
    }


    fun GetTopPodcast():TopPodcasts{
        var json= URL(rssUrl.toString()).readText()
        return  json.parseTo<TopPodcasts>()
    }
    fun GetShowById(id:String): SearchResults.ResultItem? {
        var url= baseApiUrl { it.appendEncodedPath("lookup").appendQueryParameter("id",id) }
        var json =URL(url).readText()
        return json.parseTo<SearchResults>().results.first()
    }

    fun GetShowByManyIds(idList:String): SearchResults {
        var url = baseApiUrl { it.appendEncodedPath("lookup").appendQueryParameter("id", idList) }
        var json = URL(url).readText()


        return   json.parseTo<SearchResults>()
    }
    fun getPodcastFeed(url:String):PodcastFeed{
        var url= Uri.Builder()
                .scheme("https")
                .authority("api.rss2json.com")
                .appendPath("v1")
                .appendPath("api.json")
                .appendQueryParameter("rss_url",url)
                .build()
                .toString()
        var json= URL(url).readText()
        return json.parseTo<PodcastFeed>()
    }

    fun SearchShows(query:String): SearchResults {
        //https://itunes.apple.com/search?term
        var url= baseApiUrl {
            it.appendEncodedPath("search")
                    .appendQueryParameter("term",query)
                    .appendQueryParameter("media","podcast")
        }
        var json =URL(url).readText()

        return  json.parseTo<SearchResults>()
    }
    fun SearchShowsByGenre(genre: Genre): SearchResults {
        //https://itunes.apple.com/search?term
        var url= baseApiUrl {
            it.appendEncodedPath("search")
                    .appendQueryParameter("genreId",genre.id)
                    .appendQueryParameter("media","podcast")
                    .appendQueryParameter("term","podcast")
                    .appendQueryParameter("limit","15")
        }
        var json =URL(url).readText()

        return  json.parseTo<SearchResults>()
    }

}