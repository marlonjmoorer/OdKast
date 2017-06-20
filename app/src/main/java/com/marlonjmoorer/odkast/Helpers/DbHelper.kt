package com.marlonjmoorer.odkast.Helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import com.marlonjmoorer.odkast.Models.PodcastFeed
import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.PrimaryKey
import ninja.sakib.pultusorm.core.PultusORM
import org.jetbrains.anko.db.*
import java.util.*

/**
 * Created by marlonmoorer on 6/12/17.
 */
class DbHelper(ctx: Context) {

    var pultus: PultusORM? = null
    private val dbOberserver: DbObservable?
    val subsciptions: List<String>?
        get() = instance?.pultus?.find(Subscription())?.map { (it as Subscription).show_id }

    init {
        val appPath = ctx.getFilesDir().getAbsolutePath()
        pultus = PultusORM("kast.db", appPath)
        dbOberserver = DbObservable()

    }

    companion object {
        private var instance: DbHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DbHelper {
            if (instance == null) {
                instance = DbHelper(ctx.getApplicationContext())
            }
            return instance!!
        }


    }

    fun subscribe(id: String) {
        var sub = Subscription().apply {
            show_id = id
        }
        pultus?.save(sub)
        dbOberserver?.emitChange()
    }

    fun unSubscribe(id: String){

        pultus?.delete(Subscription().apply {
            show_id = id
        })
        dbOberserver?.emitChange()
    }

    fun onUpdate(observer: Observer){

        dbOberserver?.addObserver(observer)
    }

}

val Context.database: DbHelper
    get() = DbHelper.getInstance(getApplicationContext())

class DbObservable : Observable() {


    fun emitChange() {
        setChanged()
        notifyObservers()
    }

}

class Subscription() {
    @PrimaryKey
    @AutoIncrement
    var id: Int = 0
    var show_id: String = ""

}
