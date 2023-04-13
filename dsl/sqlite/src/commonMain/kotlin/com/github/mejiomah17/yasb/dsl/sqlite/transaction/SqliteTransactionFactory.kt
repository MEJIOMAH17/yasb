package com.github.mejiomah17.yasb.dsl.sqlite.transaction

import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import com.github.mejiomah17.yasb.sqlite.jdbc.SqliteJdbcDatabaseDialect
import javax.sql.DataSource

class SqliteTransactionFactory(dataSource: DataSource) : TransactionFactory<SqliteJdbcDatabaseDialect>(dataSource) {
    override fun dialect(): SqliteJdbcDatabaseDialect {
        return SqliteJdbcDatabaseDialect
    }
}
