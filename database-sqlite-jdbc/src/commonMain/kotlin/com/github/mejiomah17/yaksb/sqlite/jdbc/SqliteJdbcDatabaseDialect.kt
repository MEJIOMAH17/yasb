package com.github.mejiomah17.yaksb.sqlite.jdbc

import com.github.mejiomah17.yaksb.core.DatabaseType
import com.github.mejiomah17.yaksb.core.SupportsFullJoin
import com.github.mejiomah17.yaksb.core.SupportsInsertReturning
import com.github.mejiomah17.yaksb.core.SupportsLimit
import com.github.mejiomah17.yaksb.core.SupportsRightJoin
import com.github.mejiomah17.yaksb.sqlite.SqliteDatabaseDialect
import com.github.mejiomah17.yaksb.sqlite.jdbc.type.BooleanDatabaseType
import com.github.mejiomah17.yaksb.sqlite.jdbc.type.LongDatabaseType
import java.sql.PreparedStatement
import java.sql.ResultSet

object SqliteJdbcDatabaseDialect :
    SqliteDatabaseDialect<ResultSet, PreparedStatement>,
    SupportsLimit,
    SupportsRightJoin,
    SupportsFullJoin,
    SupportsInsertReturning {
    override fun booleanType(): DatabaseType<Boolean, ResultSet, PreparedStatement> {
        return BooleanDatabaseType
    }

    override fun longType(): DatabaseType<Long, ResultSet, PreparedStatement> {
        return LongDatabaseType
    }
}
