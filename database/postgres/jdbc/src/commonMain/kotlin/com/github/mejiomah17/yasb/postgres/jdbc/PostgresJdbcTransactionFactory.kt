package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionFactory
import javax.sql.DataSource

class PostgresJdbcTransactionFactory(dataSource: DataSource) :
    JdbcTransactionFactory<PostgresJdbcDatabaseDialect>(
        dataSource
    ) {
    override fun dialect(): PostgresJdbcDatabaseDialect {
        return PostgresJdbcDatabaseDialect
    }
}
