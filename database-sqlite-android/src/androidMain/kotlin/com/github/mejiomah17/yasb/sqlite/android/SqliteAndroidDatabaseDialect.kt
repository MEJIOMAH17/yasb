package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.SupportsLimit
import com.github.mejiomah17.yasb.sqlite.SqliteDatabaseDialect
import com.github.mejiomah17.yasb.sqlite.android.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.type.LongDatabaseType

object SqliteAndroidDatabaseDialect : SqliteDatabaseDialect<Cursor, (String) -> Unit>, SupportsLimit {
    override fun booleanType(): DatabaseType<Boolean, Cursor, (String) -> Unit> {
        return BooleanDatabaseType
    }

    override fun longType(): DatabaseType<Long, Cursor, (String) -> Unit> {
        return LongDatabaseType
    }
}
