package com.github.mejiomah17.yasb.postgres.jdbc.join

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.dsl.join.TableJoinTest
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcDatabaseDialect
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresJdbcTable
import com.github.mejiomah17.yasb.postgres.jdbc.PostgresTest
import java.sql.PreparedStatement
import java.sql.ResultSet

class PostgresTableJoinTest :
    TableJoinTest<PostgresTableJoinTest.SecondTable, ResultSet, PreparedStatement, PostgresJdbcDatabaseDialect, JdbcTransaction>,
    PostgresTest() {
    override fun initSqlScripts(): List<String> {
        return listOf(
            "TRUNCATE TABLE FIRST",
            "TRUNCATE TABLE SECOND",
            "TRUNCATE TABLE THIRD",
            "INSERT INTO FIRST (A,B) values ('XXX','B1')",
            "INSERT INTO FIRST (A,B) values ('YYY','C1')",
            "INSERT INTO SECOND (A,B) values ('XXX','B2')",
            "INSERT INTO SECOND (A,B) values ('ZZZ','D1')",
            "INSERT INTO THIRD (A,B) values ('XXX','B3')",
            "INSERT INTO THIRD (A,B) values ('ZZZ','E1')"
        )
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

    override fun dialect(): PostgresJdbcDatabaseDialect = PostgresJdbcDatabaseDialect

    object FirstTable : PostgresJdbcTable<FirstTable> {
        override val tableName: String = "FIRST"
        val a = text("A")
        val b = text("B")
    }

    object SecondTable : PostgresJdbcTable<SecondTable> {
        override val tableName: String = "SECOND"
        val a = text("A")
        val b = text("B")
    }

    object ThirdTable : PostgresJdbcTable<ThirdTable> {
        override val tableName: String = "THIRD"
        val a = text("A")
        val b = text("B")
    }
}
