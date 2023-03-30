package com.github.mejiomah17.yasb.dsl.postgres.transaction

import com.github.mejiomah17.yasb.core.postgres.PostgresDatabaseDialect
import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import javax.sql.DataSource

class PostgresTransactionFactory(dataSource: DataSource) : TransactionFactory<PostgresDatabaseDialect>(dataSource) {
    override fun dialect(): PostgresDatabaseDialect {
        return PostgresDatabaseDialect
    }
}
