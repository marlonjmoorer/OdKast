package com.marlonjmoorer.odkast.Helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by marlonmoorer on 6/12/17.
 */
class DbHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 1) {
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

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db?.createTable("User", true,
                "id" to INTEGER + PRIMARY_KEY + UNIQUE+ AUTOINCREMENT ,
                "last_viewed_feed_url" to TEXT,
                "last_viewed_guid" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db?.dropTable("User", true)
    }

}

val Context.database: DbHelper
    get() = DbHelper.getInstance(getApplicationContext())