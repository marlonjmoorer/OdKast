package com.marlonjmoorer.odkast.Helpers

/**
 * Created by marlonmoorer on 6/12/17.
 */
enum class Genre{
    Arts{
         override fun id()="1301"
         override fun displayname()="Art"
    },
    Comedy{
        override fun id()="1303"
        override fun displayname()="Comedy"
    },
    Family{
        override fun id()="1305"
        override fun displayname()="Family"
    },
    Health{
        override fun id()="1307"
        override fun displayname()="Art"
    },
    TV{
        override fun id()="1309"
        override fun displayname()="Art"
    },
    Music{
        override fun id()="1310"
        override fun displayname()="Art"
    };

    abstract fun displayname(): String
    abstract fun id():String

}


