package com.github.mejiomah17.yasb.sqlite.android

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.test.platform.app.InstrumentationRegistry
import com.github.mejiomah17.yasb.sqlite.android.transaction.AndroidTransactionFactory
import org.junit.Before

abstract class SqliteAndroidTest {
    lateinit var context: Context
    val dbHelper: SQLiteOpenHelper by lazy {
        DbHelper(
            context,
            initSql = arrayListOf(*initSqlScripts().toTypedArray()).also {
                it.add(
                    0,
                    """
                                CREATE TABLE test(
                                   a string DEFAULT NULL,
                                   b string DEFAULT NULL
                                )
                    """.trimIndent()
                )
            },
            cleanSql = """
                    DROP TABLE IF EXISTS test
            """.trimIndent()
        )
    }

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().context
    }

    protected abstract fun initSqlScripts(): List<String>

    fun transactionFactory(): AndroidTransactionFactory {
        return AndroidTransactionFactory(dbHelper.writableDatabase)
    }

    class DbHelper(
        context: Context,
        private val initSql: List<String>,
        private val cleanSql: String
    ) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            initSql.forEach {
                db.execSQL(it)
            }
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(cleanSql)
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }

        companion object {
            // If you change the database schema, you must increment the database version.
            const val DATABASE_VERSION = 1
            const val DATABASE_NAME = "FeedReadedr.db"
        }
    }
}
