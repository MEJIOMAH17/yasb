package com.github.mejiomah17.yasb.dsl.transaction

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.postgres.PostgresDatabaseDialect
import javax.sql.DataSource

class PostgresTransactionFactory(dataSource: DataSource) : TransactionFactory(dataSource) {
    override fun dialect(): DatabaseDialect {
        return PostgresDatabaseDialect
    }
}