package com.github.mejiomah17.yaksb.postgres.jdbc

import com.github.mejiomah17.yaksb.core.jdbc.JDBCDatabaseType
import com.github.mejiomah17.yaksb.postgres.jdbc.type.BooleanDatabaseType
import com.github.mejiomah17.yaksb.postgres.jdbc.type.LongDatabaseType
import com.github.mejiomah17.yaksb.sqlite.PostgresDatabaseDialect
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
