package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.SupportsInsertReturning
import com.github.mejiomah17.yasb.core.SupportsLimit
import com.github.mejiomah17.yasb.sqlite.SqliteDatabaseDialect
import com.github.mejiomah17.yasb.sqlite.jdbc.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.sqlite.jdbc.type.LongDatabaseType
import java.sql.PreparedStatement
import java.sql.ResultSet

object SqliteJdbcDatabaseDialect : SqliteDatabaseDialect<ResultSet, PreparedStatement>, SupportsLimit,
    SupportsInsertReturning {
    override fun booleanType(): DatabaseType<Boolean, ResultSet, PreparedStatement> {
        return BooleanDatabaseType
    }

    override fun longType(): DatabaseType<Long, ResultSet, PreparedStatement> {
        return LongDatabaseType
    }
}
