package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import java.sql.PreparedStatement
import java.sql.ResultSet
import javax.sql.DataSource

class SqliteJdbcTransactionFactory(dataSource: DataSource) :
    TransactionFactory<SqliteJdbcDatabaseDialect, ResultSet, PreparedStatement>(dataSource) {
    override fun dialect(): SqliteJdbcDatabaseDialect {
        return SqliteJdbcDatabaseDialect
    }
}
