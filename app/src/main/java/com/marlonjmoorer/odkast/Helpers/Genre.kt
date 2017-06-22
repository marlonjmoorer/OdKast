package com.marlonjmoorer.odkast.Helpers

/**
 * Created by marlonmoorer on 6/12/17.
 */
enum class Genre(private val value:String){
    Arts("1301"){
         override fun displayname()="Art"
    },
    Comedy("1303"){

        override fun displayname()="Comedy"
    },
    Education("1304"){
        override fun displayname()="Education"

    },
    Family("1305"){

        override fun displayname()="Kids & Family"
    },
    Health("1307"){
        override fun displayname()="Health"
    },
    TV("1309"){
        override fun displayname()="TV & Film"
    },
    Music("1310"){
        override fun displayname()="Music"
    },
    News("1311"){
        override fun displayname()="News & Politics"
    },
    Religion("1314"){
        override fun displayname()="Religion & Spirituality"
    },
    Science("1315"){
        override fun displayname()="Science & Medicine"
    },
    Sports("1316"){
        override fun displayname()="Sports & Recreation"
    },
    Technology("1318"){
        override fun displayname()="Technology"
    },
    Business("1321"){
        override fun displayname()="Business"
    },
    Games("1323"){
        override fun displayname()="Games & Hobbies"
    },
    Culture("1324"){
        override fun displayname()="Society & Culture"
    },
    Government("1325"){
        override fun displayname()="Government & Organizations"
    };

    abstract fun displayname(): String

    val id :String
      get() = this.value

}


