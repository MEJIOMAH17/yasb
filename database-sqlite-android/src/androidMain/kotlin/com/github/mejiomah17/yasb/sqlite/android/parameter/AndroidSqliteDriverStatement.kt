package com.github.mejiomah17.yasb.sqlite.android.parameter

import org.sqlite.database.sqlite.SQLiteProgram

interface AndroidSqliteDriverStatement {

    fun bindLong(index: Int, value: Long)

    fun bindDouble(index: Int, value: Double)

    fun bindString(index: Int, value: String?)

    fun bindBlob(index: Int, value: ByteArray?)
    fun bindNull(index: Int)
}

internal class AndroidSqliteDriverStatementImpl(private val sqliteProgram: SQLiteProgram) :
    AndroidSqliteDriverStatement {
    override fun bindLong(index: Int, value: Long) {
        sqliteProgram.bindLong(index, value)
    }

    override fun bindDouble(index: Int, value: Double) {
        sqliteProgram.bindDouble(index, value)
    }

    override fun bindString(index: Int, value: String?) {
        sqliteProgram.bindString(index, value)
    }

    override fun bindBlob(index: Int, value: ByteArray?) {
        sqliteProgram.bindBlob(index, value)
    }

    override fun bindNull(index: Int) {
        sqliteProgram.bindNull(index)
    }
}
