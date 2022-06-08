package com.github.mejiomah17.yasb.dsl.join

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.postgres.PostgresDatabaseDialect
import com.github.mejiomah17.yasb.core.postgres.ddl.PostgresTable
import com.github.mejiomah17.yasb.dsl.PostgresTest
import com.github.mejiomah17.yasb.dsl.transaction.PostgresTransactionFactory
import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import org.junit.jupiter.api.BeforeEach

class PostgresTableJoinTest : TableJoinTest, PostgresTest() {
    @BeforeEach
    fun setup() {
        dataSource.connection.use {
            it.createStatement().use {
                it.execute("TRUNCATE TABLE FIRST")
                it.execute("TRUNCATE TABLE SECOND")
                it.execute("TRUNCATE TABLE THIRD")
                it.execute("INSERT INTO FIRST (A,B) values ('XXX','B1')")
                it.execute("INSERT INTO FIRST (A,B) values ('YYY','C1')")
                it.execute("INSERT INTO SECOND (A,B) values ('XXX','B2')")
                it.execute("INSERT INTO SECOND (A,B) values ('ZZZ','D1')")
                it.execute("INSERT INTO THIRD (A,B) values ('XXX','B3')")
                it.execute("INSERT INTO THIRD (A,B) values ('ZZZ','E1')")
            }
        }
    }

    override fun firstTable(): Table<*> = FirstTable

    override fun joinColumnFromFirstTable(): Column<*, String> = FirstTable.a
    override fun dataColumnFromFirstTable(): Column<*, String> = FirstTable.b

    override fun secondTable(): Table<*> = SecondTable

    override fun joinColumnFromSecondTable(): Column<*, String> = SecondTable.a
    override fun dataColumnFromSecondTable(): Column<*, String> = SecondTable.b
    override fun thirdTable(): Table<*> = ThirdTable

    override fun joinColumnFromThirdTable(): Column<*, String> = ThirdTable.a

    override fun dataColumnFromThirdTable(): Column<*, String> = ThirdTable.b

    override fun transactionFactory(): TransactionFactory = PostgresTransactionFactory(dataSource)

    override fun dialect(): DatabaseDialect = PostgresDatabaseDialect

    object FirstTable : PostgresTable<FirstTable> {
        override val tableName: String = "FIRST"
        val a = text("A")
        val b = text("B")
    }

    object SecondTable : PostgresTable<FirstTable> {
        override val tableName: String = "SECOND"
        val a = text("A")
        val b = text("B")
    }

    object ThirdTable : PostgresTable<FirstTable> {
        override val tableName: String = "THIRD"
        val a = text("A")
        val b = text("B")
    }
}