package com.github.mejiomah17.yasb.dsl.transaction

import com.github.mejiomah17.yasb.core.postgres.PostgresDatabaseDialect
import javax.sql.DataSource

class PostgresTransactionFactory(dataSource: DataSource) : TransactionFactory<PostgresDatabaseDialect>(dataSource) {
    override fun dialect(): PostgresDatabaseDialect {
        return PostgresDatabaseDialect
    }
}