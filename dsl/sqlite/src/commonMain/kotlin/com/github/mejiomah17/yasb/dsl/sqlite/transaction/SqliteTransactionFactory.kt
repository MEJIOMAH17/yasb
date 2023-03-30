package com.github.mejiomah17.yasb.dsl.sqlite.transaction

import com.github.mejiomah17.yasb.core.sqlite.SqliteDatabaseDialect
import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import javax.sql.DataSource

class SqliteTransactionFactory(dataSource: DataSource) : TransactionFactory<SqliteDatabaseDialect>(dataSource) {
    override fun dialect(): SqliteDatabaseDialect {
        return SqliteDatabaseDialect
    }
}
