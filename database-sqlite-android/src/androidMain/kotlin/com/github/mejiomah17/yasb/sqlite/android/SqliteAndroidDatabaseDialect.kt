package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.SupportsLimit
import com.github.mejiomah17.yasb.sqlite.SqliteDatabaseDialect
import com.github.mejiomah17.yasb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yasb.sqlite.android.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.sqlite.android.type.LongDatabaseType

object SqliteAndroidDatabaseDialect : SqliteDatabaseDialect<Cursor, AndroidSqliteDriverStatement>, SupportsLimit {
    override fun booleanType(): DatabaseType<Boolean, Cursor, AndroidSqliteDriverStatement> {
        return BooleanDatabaseType
    }

    override fun longType(): DatabaseType<Long, Cursor, AndroidSqliteDriverStatement> {
        return LongDatabaseType
    }
}
