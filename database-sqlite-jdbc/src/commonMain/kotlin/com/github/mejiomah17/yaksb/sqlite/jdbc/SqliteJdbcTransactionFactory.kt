package com.github.mejiomah17.yaksb.sqlite.jdbc

import com.github.mejiomah17.yaksb.core.jdbc.transaction.JdbcTransactionFactory
import javax.sql.DataSource

class SqliteJdbcTransactionFactory(
    dataSource: DataSource,
) : JdbcTransactionFactory<SqliteJdbcDatabaseDialect>(dataSource) {
    override fun dialect(): SqliteJdbcDatabaseDialect = SqliteJdbcDatabaseDialect
}
