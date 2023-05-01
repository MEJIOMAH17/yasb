package com.github.mejiomah17.yasb.sqlite.android

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.test.platform.app.InstrumentationRegistry
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.dsl.TestTable
import com.github.mejiomah17.yasb.sqlite.android.parameter.TextParameter
import com.github.mejiomah17.yasb.sqlite.android.transaction.AndroidTransactionFactory
import org.junit.Before

abstract class SqliteAndroidTest {
    lateinit var context: Context
    val dbHelper: SQLiteOpenHelper by lazy {
        DbHelper(
            context,
            initSql = listOf(
                """
                            CREATE TABLE test(
                               a string DEFAULT NULL,
                               b string DEFAULT NULL
                            )
                        """.trimIndent(),
                """
                            CREATE TABLE FIRST(
                               A string,
                               B string
                            );
                        """.trimIndent(),
                """
                            CREATE TABLE SECOND(
                               A string,
                               B string
                            );
                        """.trimIndent(),
                """
                            CREATE TABLE THIRD(
                               A string,
                               B string
                            );
                        """.trimIndent()
            ),
            cleanSql = """
                    DROP TABLE IF EXISTS test
            """.trimIndent()
        )
    }

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().context
    }

    fun transactionFactory(): AndroidTransactionFactory {
        return AndroidTransactionFactory(dbHelper.writableDatabase)
    }

    fun parameter(): Parameter<String, Cursor, (String) -> Unit> {
        return TextParameter("param")
    }

    fun tableTest(): SqliteAndroidTestTable {
        return SqliteAndroidTestTable
    }

    fun executeSql(sql: String) {
        dbHelper.writableDatabase.execSQL(sql)
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
            const val DATABASE_NAME = "Test.db"
        }
    }

    fun firstTable() = FirstTable

    fun secondTable() = SecondTable

    fun thirdTable() = ThirdTable

    fun dialect(): SqliteAndroidDatabaseDialect = SqliteAndroidDatabaseDialect

    object FirstTable : SqliteAndroidTable<FirstTable>, TestTable<FirstTable, Cursor, (String) -> Unit> {
        override val tableName: String = "FIRST"
        override val a = text("A")
        override val b = text("B")
    }

    object SecondTable : SqliteAndroidTable<SecondTable>, TestTable<SecondTable, Cursor, (String) -> Unit> {
        override val tableName: String = "SECOND"
        override val a = text("A")
        override val b = text("B")
    }

    object ThirdTable : SqliteAndroidTable<ThirdTable>, TestTable<ThirdTable, Cursor, (String) -> Unit> {
        override val tableName: String = "THIRD"
        override val a = text("A")
        override val b = text("B")
    }
}
