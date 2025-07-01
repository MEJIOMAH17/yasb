package com.github.mejiomah17.yaksb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yaksb.core.DatabaseType
import com.github.mejiomah17.yaksb.core.SupportsLimit
import com.github.mejiomah17.yaksb.sqlite.SqliteDatabaseDialect
import com.github.mejiomah17.yaksb.sqlite.android.parameter.AndroidSqliteDriverStatement
import com.github.mejiomah17.yaksb.sqlite.android.type.BooleanDatabaseType
import com.github.mejiomah17.yaksb.sqlite.android.type.LongDatabaseType

object SqliteAndroidDatabaseDialect : SqliteDatabaseDialect<Cursor, AndroidSqliteDriverStatement>, SupportsLimit {
    override fun booleanType(): DatabaseType<Boolean, Cursor, AndroidSqliteDriverStatement> {
        return BooleanDatabaseType
    }

    override fun longType(): DatabaseType<Long, Cursor, AndroidSqliteDriverStatement> {
        return LongDatabaseType
    }
}
