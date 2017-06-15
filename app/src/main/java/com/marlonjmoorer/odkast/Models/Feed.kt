package com.marlonjmoorer.odkast.Models

import java.util.*

/**
 * Created by marlonmoorer on 6/12/17.
 */

class Feed {

    var title: String? = null
    var description: String? = null
    var image:String?=null
    var episodes:ArrayList<EpisodeItemX>?=null





}

class EpisodeItemX{
    var title: String? = null
    var description: String? = null
    var image:String?=null
    var uploadDate: Date?=null
    var enclosureUrl:String?=null
    var duration:String?=null

}