package com.github.mejiomah17.yasb.sqlite.jdbc.join

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.dsl.join.TableJoinTest
import com.github.mejiomah17.yasb.sqlite.jdbc.SqliteJdbcDatabaseDialect
import com.github.mejiomah17.yasb.sqlite.jdbc.SqliteJdbcTable
import com.github.mejiomah17.yasb.sqlite.jdbc.SqliteJdbcTransactionFactory
import com.github.mejiomah17.yasb.sqlite.jdbc.SqliteTest
import org.junit.Before
import java.sql.PreparedStatement
import java.sql.ResultSet

class SqliteTableJoinTest :
    TableJoinTest<SqliteTableJoinTest.SecondTable, ResultSet, PreparedStatement, SqliteJdbcDatabaseDialect, JdbcTransaction>,
    SqliteTest() {
    @Before
    fun setup() {
        dataSource.connection.use {
            it.createStatement().use {
                it.execute("DELETE FROM FIRST")
                it.execute("DELETE FROM SECOND")
                it.execute("DELETE FROM THIRD")
                it.execute("INSERT INTO FIRST (A,B) values ('XXX','B1')")
                it.execute("INSERT INTO FIRST (A,B) values ('YYY','C1')")
                it.execute("INSERT INTO SECOND (A,B) values ('XXX','B2')")
                it.execute("INSERT INTO SECOND (A,B) values ('ZZZ','D1')")
                it.execute("INSERT INTO THIRD (A,B) values ('XXX','B3')")
                it.execute("INSERT INTO THIRD (A,B) values ('ZZZ','E1')")
            }
        }
    }

    override fun firstTable(): Table<*, ResultSet, PreparedStatement> = FirstTable

    override fun joinColumnFromFirstTable(): Column<*, String, ResultSet, PreparedStatement> = FirstTable.a
    override fun dataColumnFromFirstTable(): Column<*, String, ResultSet, PreparedStatement> = FirstTable.b

    override fun secondTable(): SecondTable = SecondTable

    override fun joinColumnFromSecondTable(): Column<SecondTable, String, ResultSet, PreparedStatement> = SecondTable.a
    override fun dataColumnFromSecondTable(): Column<SecondTable, String, ResultSet, PreparedStatement> = SecondTable.b
    override fun thirdTable(): Table<*, ResultSet, PreparedStatement> = ThirdTable

    override fun joinColumnFromThirdTable(): Column<*, String, ResultSet, PreparedStatement> = ThirdTable.a

    override fun dataColumnFromThirdTable(): Column<*, String, ResultSet, PreparedStatement> = ThirdTable.b

    override fun transactionFactory(): SqliteJdbcTransactionFactory = SqliteJdbcTransactionFactory(dataSource)

    override fun dialect(): SqliteJdbcDatabaseDialect = SqliteJdbcDatabaseDialect

    object FirstTable : SqliteJdbcTable<FirstTable> {
        override val tableName: String = "FIRST"
        val a = text("A")
        val b = text("B")
    }

    object SecondTable : SqliteJdbcTable<SecondTable> {
        override val tableName: String = "SECOND"
        val a = text("A")
        val b = text("B")
    }

    object ThirdTable : SqliteJdbcTable<ThirdTable> {
        override val tableName: String = "THIRD"
        val a = text("A")
        val b = text("B")
    }
}
