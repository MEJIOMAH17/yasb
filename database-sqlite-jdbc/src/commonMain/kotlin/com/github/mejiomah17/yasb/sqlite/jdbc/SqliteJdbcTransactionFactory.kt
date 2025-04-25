package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransactionFactory
import javax.sql.DataSource

class SqliteJdbcTransactionFactory(dataSource: DataSource) :
    JdbcTransactionFactory<SqliteJdbcDatabaseDialect>(dataSource) {
    override fun dialect(): SqliteJdbcDatabaseDialect {
        return SqliteJdbcDatabaseDialect
    }
}
