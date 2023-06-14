/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.mejiomah17.yasb.sqlite.android.transaction

import android.database.Cursor
import android.os.CancellationSignal
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yasb.sqlite.android.parameter.AndroidSqliteDriverStatementImpl
import com.github.mejiomah17.yasb.sqlite.android.parameter.TextParameter
import org.sqlite.database.sqlite.SQLiteCursor
import org.sqlite.database.sqlite.SQLiteCursorDriver
import org.sqlite.database.sqlite.SQLiteDatabase
import org.sqlite.database.sqlite.SQLiteQuery

/**
 * A cursor driver that uses the given query directly.
 * @see org.sqlite.database.sqlite.SQLiteDirectCursorDriver
 * @hide
 */
internal class YasbSQLiteDirectCursorDriver(
    private val mDatabase: SQLiteDatabase,
    private val mSql: String
) : SQLiteCursorDriver {
    private val mCancellationSignal: CancellationSignal? = null
    private val mEditTable = null
    private var mQuery: SQLiteQuery? = null

    override fun query(factory: SQLiteDatabase.CursorFactory, selectionArgs: Array<String>): Cursor {
        return this.query(factory, selectionArgs.map { TextParameter(it) })
    }

    fun query(
        factory: SQLiteDatabase.CursorFactory?,
        params: List<Parameter<*, Cursor, AndroidSqliteDriverStatement>>
    ): Cursor {
        val clazz = SQLiteQuery::class.java
        val query = clazz.declaredConstructors.first {
            it.parameterTypes.size == 3 &&
                    it.parameterTypes.first().isAssignableFrom(SQLiteDatabase::class.java) &&
                    it.parameterTypes[1].isAssignableFrom(String::class.java) &&
                    it.parameterTypes[2].isAssignableFrom(CancellationSignal::class.java)
        }.newInstance(mDatabase, mSql, mCancellationSignal) as SQLiteQuery
        val cursor: Cursor
        cursor = try {
            //<editor-fold desc="original SQLiteDirectCursorDriver invokes query.bindAllArgsAsStrings(selectionArgs);">
            val statement = AndroidSqliteDriverStatementImpl(query)
            params.forEachIndexed { i, it ->
                it.applyToStatement(statement, i + 1)
            }
            //</editor-fold>

            if (factory == null) {
                SQLiteCursor(this, mEditTable, query)
            } else {
                factory.newCursor(mDatabase, this, mEditTable, query)
            }
        } catch (ex: RuntimeException) {
            query.close()
            throw ex
        }
        mQuery = query
        return cursor
    }

    override fun cursorClosed() {
        // Do nothing
    }

    override fun setBindArguments(bindArgs: Array<String>) {
        mQuery!!.bindAllArgsAsStrings(bindArgs)
    }

    override fun cursorDeactivated() {
        // Do nothing
    }

    override fun cursorRequeried(cursor: Cursor) {
        // Do nothing
    }

    override fun toString(): String {
        return "SQLiteDirectCursorDriver: $mSql"
    }
}
