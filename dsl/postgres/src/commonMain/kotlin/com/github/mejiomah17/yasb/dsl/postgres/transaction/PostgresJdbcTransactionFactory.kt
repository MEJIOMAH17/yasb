package com.github.mejiomah17.yasb.dsl.postgres.transaction

import com.github.mejiomah17.yasb.core.postgres.PostgresJdbcDatabaseDialect
import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import javax.sql.DataSource

class PostgresJdbcTransactionFactory(dataSource: DataSource) : TransactionFactory<PostgresJdbcDatabaseDialect>(dataSource) {
    override fun dialect(): PostgresJdbcDatabaseDialect {
        return PostgresJdbcDatabaseDialect
    }
}
