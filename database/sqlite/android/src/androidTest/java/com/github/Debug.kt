package com.github

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Test

class Debug {

    lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun x() {
        val x = FeedReaderDbHelper(context)
        val db = x.writableDatabase
        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, "A")
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, "B")
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
        val z = db.rawQuery("SELECT * FROM entry WHERE title = ?", arrayOf("B"))
        db.beginTransaction()
        println(newRowId)
    }

    class FeedReaderDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES)
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }

        companion object {
            // If you change the database schema, you must increment the database version.
            const val DATABASE_VERSION = 1
            const val DATABASE_NAME = "FeedReader.db"
        }
    }
}

object FeedReaderContract {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "entry"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_SUBTITLE = "subtitle"
    }
}

private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${FeedReaderContract.FeedEntry.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
        "${FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE} TEXT," +
        "${FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE} TEXT)"

private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.TABLE_NAME}"