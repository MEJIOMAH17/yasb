package com.github.mejiomah17.yasb.dsl.postgres.transaction

import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcDatabaseDialect
import javax.sql.DataSource

class PostgresJdbcTransactionFactory(dataSource: DataSource) : TransactionFactory<PostgresJdbcDatabaseDialect>(dataSource) {
    override fun dialect(): PostgresJdbcDatabaseDialect {
        return PostgresJdbcDatabaseDialect
    }
}
