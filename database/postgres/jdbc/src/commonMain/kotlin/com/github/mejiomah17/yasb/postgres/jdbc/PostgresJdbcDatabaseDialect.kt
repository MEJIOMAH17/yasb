package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yasb.postgres.jdbc.type.BooleanDatabaseType
import com.github.mejiomah17.yasb.postgres.jdbc.type.LongDatabaseType
import com.github.mejiomah17.yasb.sqlite.PostgresDatabaseDialect
import java.sql.PreparedStatement
import java.sql.ResultSet

object PostgresJdbcDatabaseDialect : PostgresDatabaseDialect<ResultSet, PreparedStatement> {
    override fun booleanType(): JDBCDatabaseType<Boolean> {
        return BooleanDatabaseType
    }

    override fun longType(): JDBCDatabaseType<Long> {
        return LongDatabaseType
    }
}
