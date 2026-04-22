package com.github.mejiomah17.yaksb.postgres.jdbc

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionFactory
import javax.sql.DataSource

class PostgresJdbcTransactionFactory(
    dataSource: DataSource,
) : JdbcTransactionFactory<PostgresJdbcDatabaseDialect>(
        dataSource,
    ) {
    override fun dialect(): PostgresJdbcDatabaseDialect = PostgresJdbcDatabaseDialect
}
