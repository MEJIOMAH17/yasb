@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.sqlite.jdbc

import com.github.mejiomah17.yasb.dsl.transaction.JdbcTransactionFactory
import javax.sql.DataSource

class SqliteJdbcTransactionFactory(dataSource: DataSource) :
    JdbcTransactionFactory<SqliteJdbcDatabaseDialect>(dataSource) {
    override fun dialect(): SqliteJdbcDatabaseDialect {
        return SqliteJdbcDatabaseDialect
    }
}
