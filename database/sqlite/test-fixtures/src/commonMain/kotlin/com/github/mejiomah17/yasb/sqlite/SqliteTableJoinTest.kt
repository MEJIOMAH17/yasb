package com.github.mejiomah17.yasb.sqlite

import com.github.mejiomah17.yasb.core.transaction.Transaction
import com.github.mejiomah17.yasb.dsl.TestTable
import com.github.mejiomah17.yasb.dsl.join.TableJoinTest

interface SqliteTableJoinTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : SqliteDatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    > :
    TableJoinTest<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION> {

    override fun initSqlScripts(): List<String> {
        return listOf(
            "DELETE FROM FIRST",
            "DELETE FROM SECOND",
            "DELETE FROM THIRD",
            "INSERT INTO FIRST (A,B) values ('XXX','B1')",
            "INSERT INTO FIRST (A,B) values ('YYY','C1')",
            "INSERT INTO SECOND (A,B) values ('XXX','B2')",
            "INSERT INTO SECOND (A,B) values ('ZZZ','D1')",
            "INSERT INTO THIRD (A,B) values ('XXX','B3')",
            "INSERT INTO THIRD (A,B) values ('ZZZ','E1')"
        )
    }
}
