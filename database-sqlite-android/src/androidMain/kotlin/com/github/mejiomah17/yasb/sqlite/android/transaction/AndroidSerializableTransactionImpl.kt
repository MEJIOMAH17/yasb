package com.github.mejiomah17.yasb.sqlite.android.transaction

import android.database.Cursor
import com.github.mejiomah17.yasb.core.transaction.TransactionSerializable
import com.github.mejiomah17.yasb.sqlite.android.parameter.AndroidSqliteDriverStatement
import org.sqlite.database.sqlite.SQLiteDatabase

class AndroidSerializableTransactionImpl(override val database: SQLiteDatabase) :
    TransactionSerializable<Cursor, AndroidSqliteDriverStatement>, AndroidTransaction
