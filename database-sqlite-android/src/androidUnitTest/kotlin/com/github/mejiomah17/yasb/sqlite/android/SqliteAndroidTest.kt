package com.github.mejiomah17.yasb.sqlite.android

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.test.platform.app.InstrumentationRegistry
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.dsl.TestTable
import com.github.mejiomah17.yasb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yasb.sqlite.android.parameter.TextParameter
import com.github.mejiomah17.yasb.sqlite.android.transaction.AndroidTransactionFactory
import org.junit.Before
import org.sqlite.database.sqlite.SQLiteDatabase.CREATE_IF_NECESSARY
import org.sqlite.database.sqlite.SQLiteDatabase.OPEN_READWRITE

abstract class SqliteAndroidTest {
    lateinit var context: Context
    val newDb: org.sqlite.database.sqlite.SQLiteDatabase by lazy {
        System.loadLibrary("sqliteX")
        val db = org.sqlite.database.sqlite.SQLiteDatabase.openDatabase(
            context.getDatabasePath("Test.db").path,
            null,
            SQLiteDatabase.OpenParams.Builder()
                .addOpenFlags(OPEN_READWRITE)
                .addOpenFlags(CREATE_IF_NECESSARY)
                .build().openFlags
        )
        """
                    DROP TABLE IF EXISTS test;
                    DROP TABLE IF EXISTS FIRST;
                    DROP TABLE IF EXISTS SECOND;
                    DROP TABLE IF EXISTS THIRD;
        """.trimIndent().split("\n").forEach {
            db.execSQL(it)
        }
        listOf(
            """
                            CREATE TABLE test(
                               a string DEFAULT NULL,
                               b string DEFAULT NULL,
                               c bigint DEFAULT NULL,
                               d boolean DEFAULT NULL,
                               e blob DEFAULT NULL
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
        ).forEach {
            db.execSQL(it)
        }
        db
    }

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().context
    }

    fun transactionFactory(): AndroidTransactionFactory {
        return AndroidTransactionFactory(newDb)
    }

    fun parameter(): Parameter<String, Cursor, AndroidSqliteDriverStatement> {
        return TextParameter("param")
    }

    fun tableTest(): SqliteAndroidTestTable {
        return SqliteAndroidTestTable
    }

    fun executeSql(sql: String) {
        newDb.execSQL(sql)
    }

    class DbHelper(
        context: Context,
        private val initSql: List<String>,
        private val cleanSql: List<String>
    ) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            initSql.forEach {
                db.execSQL(it)
            }
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            cleanSql.forEach {
                db.execSQL(it)
            }
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }

        companion object {
            // If you change the database schema, you must increment the database version.
            const val DATABASE_VERSION = 4
            const val DATABASE_NAME = "Test.db"
        }
    }

    fun firstTable() = FirstTable

    fun secondTable() = SecondTable

    fun thirdTable() = ThirdTable

    fun dialect(): SqliteAndroidDatabaseDialect = SqliteAndroidDatabaseDialect

    object FirstTable : SqliteAndroidTable<FirstTable>, TestTable<FirstTable, Cursor, AndroidSqliteDriverStatement> {
        override val tableName: String = "FIRST"
        override val a = text("A")
        override val b = text("B")
    }

    object SecondTable : SqliteAndroidTable<SecondTable>, TestTable<SecondTable, Cursor, AndroidSqliteDriverStatement> {
        override val tableName: String = "SECOND"
        override val a = text("A")
        override val b = text("B")
    }

    object ThirdTable : SqliteAndroidTable<ThirdTable>, TestTable<ThirdTable, Cursor, AndroidSqliteDriverStatement> {
        override val tableName: String = "THIRD"
        override val a = text("A")
        override val b = text("B")
    }
}
