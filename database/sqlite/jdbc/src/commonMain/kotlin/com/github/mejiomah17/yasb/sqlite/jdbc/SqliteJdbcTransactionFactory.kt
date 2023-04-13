package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import javax.sql.DataSource

class SqliteJdbcTransactionFactory(dataSource: DataSource) : TransactionFactory<SqliteJdbcDatabaseDialect>(dataSource) {
    override fun dialect(): SqliteJdbcDatabaseDialect {
        return SqliteJdbcDatabaseDialect
    }
}
