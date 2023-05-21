package com.github.mejiomah17.yasb.sqlite.android.transaction

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.github.mejiomah17.yasb.core.transaction.TransactionSerializable

class AndroidSerializableTransactionImpl(override val database: SQLiteDatabase) :
    TransactionSerializable<Cursor, (String) -> Unit>, AndroidTransaction
