package com.github.mejiomah17.yasb.postgres.jdbc

import java.sql.ResultSet
import javax.sql.DataSource

class PostgresJdbcTransactionFactory(dataSource: DataSource) :
    com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory<PostgresJdbcDatabaseDialect, ResultSet>(dataSource) {
    override fun dialect(): PostgresJdbcDatabaseDialect {
        return PostgresJdbcDatabaseDialect
    }
}
