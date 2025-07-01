package com.github.mejiomah17.yaksb.sqlite.android.transaction

import android.database.Cursor
import com.github.mejiomah17.yaksb.core.parameter.Parameter
import com.github.mejiomah17.yaksb.sqlite.android.parameter.AndroidSqliteDriverStatement
import org.sqlite.database.sqlite.SQLiteDatabase

internal class SqliteDatabaseAdapter(
    private val db: SQLiteDatabase,
) {
    fun executeQuery(
        sql: String,
        params: List<Parameter<*, Cursor, AndroidSqliteDriverStatement>>,
    ): Cursor {
        // <editor-fold desc="SQLiteDatabase.rawQueryWithFactory">
        db.acquireReference()
        return try {
            val driver =
                YasbSQLiteDirectCursorDriver(
                    db,
                    sql,
                )
            driver.query(null, params)
        } finally {
            db.releaseReference()
        }
        // </editor-fold>
    }
}
