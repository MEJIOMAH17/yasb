package com.github.mejiomah17.yasb.postgres.jdbc

import javax.sql.DataSource

class PostgresJdbcTransactionFactory(dataSource: DataSource) :
    com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory<PostgresJdbcDatabaseDialect>(dataSource) {
    override fun dialect(): PostgresJdbcDatabaseDialect {
        return PostgresJdbcDatabaseDialect
    }
}
