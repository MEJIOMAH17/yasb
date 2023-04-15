package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import java.sql.ResultSet
import javax.sql.DataSource

class SqliteJdbcTransactionFactory(dataSource: DataSource) :
    TransactionFactory<SqliteJdbcDatabaseDialect, ResultSet>(dataSource) {
    override fun dialect(): SqliteJdbcDatabaseDialect {
        return SqliteJdbcDatabaseDialect
    }
}
