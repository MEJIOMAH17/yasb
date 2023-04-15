package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.SupportsLimit
import com.github.mejiomah17.yasb.sqlite.jdbc.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.sqlite.jdbc.type.LongDatabaseType
import java.sql.ResultSet

object SqliteJdbcDatabaseDialect : DatabaseDialect<ResultSet>, SupportsLimit {
    override fun booleanType(): DatabaseType<Boolean, ResultSet> {
        return BooleanDatabaseType
    }

    override fun longType(): DatabaseType<Long, ResultSet> {
        return LongDatabaseType
    }
}
