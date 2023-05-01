package com.github.mejiomah17.yasb.postgres.jdbc

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.dsl.eq
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.jdbc.transaction.JdbcTransaction
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.where
import com.github.mejiomah17.yasb.dsl.WhereTest
import com.github.mejiomah17.yasb.postgres.jdbc.parameter.TextParameter
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp
import java.util.UUID

class PostgresWhereTest :
    WhereTest<PostgresJdbcTestTable, ResultSet, PreparedStatement, PostgresJdbcDatabaseDialect, JdbcTransaction>,
    PostgresTest() {
    @Before
    fun setup() {
        dataSource.connection.use {
            it.createStatement().use {
                it.execute("TRUNCATE TABLE test")
                it.execute(
                    """INSERT INTO test (a,b,c,d) values (
                    'the a',
                    'the b',
                    '3e2220cd-e6a5-4eae-a258-6ed41e91c221',
                    '2022-05-13 02:09:09.683194'::timestamp
                     );
                    INSERT INTO test (a,b,c,d) values (
                    '42',
                    '42',
                    '3e2220cd-e6a5-4eae-a258-6ed41e91c222',
                    '2022-05-13 02:09:09.683195'::timestamp
                    )
                    """.trimIndent()
                )
            }
        }
    }

    @Test
    fun `where_filters_query_by_uuid`() {
        transactionFactory().readUncommitted {
            val queryWithoutWhere = select(columnA(), columnB(), PostgresJdbcTestTable.c).from(tableTest())
            val given = queryWithoutWhere.execute()
            given.shouldHaveSize(2)
            given.get(1)[PostgresJdbcTestTable.c].toString().shouldBe("3e2220cd-e6a5-4eae-a258-6ed41e91c222")

            val query = queryWithoutWhere.where {
                PostgresJdbcTestTable.c.eq(UUID.fromString("3e2220cd-e6a5-4eae-a258-6ed41e91c222"))
            }
            val result = query.execute()
            result.shouldHaveSize(1)
            val row = result.single()
            row[PostgresJdbcTestTable.c].toString() shouldBe "3e2220cd-e6a5-4eae-a258-6ed41e91c222"
        }
    }

    @Test
    fun `where_filters_query_by_timestamp`() {
        transactionFactory().readUncommitted {
            val queryWithoutWhere =
                select(columnA(), columnB(), PostgresJdbcTestTable.c, PostgresJdbcTestTable.d).from(tableTest())
            val given = queryWithoutWhere.execute()
            given.shouldHaveSize(2)
            given.get(1)[PostgresJdbcTestTable.d].shouldBe(Timestamp.valueOf("2022-05-13 02:09:09.683195"))

            val query = queryWithoutWhere.where {
                PostgresJdbcTestTable.d.eq(Timestamp.valueOf("2022-05-13 02:09:09.683195"))
            }
            val result = query.execute()
            result.shouldHaveSize(1)
            val row = result.single()
            row[PostgresJdbcTestTable.d] shouldBe Timestamp.valueOf("2022-05-13 02:09:09.683195")
        }
    }

    fun columnA(): Column<PostgresJdbcTestTable, String, ResultSet, PreparedStatement> {
        return PostgresJdbcTestTable.a
    }

    fun columnB(): Column<PostgresJdbcTestTable, String, ResultSet, PreparedStatement> {
        return PostgresJdbcTestTable.b
    }

    override fun parameter(): Parameter<String, ResultSet, PreparedStatement> {
        return TextParameter("param")
    }

    override fun tableTest(): PostgresJdbcTestTable {
        return PostgresJdbcTestTable
    }

    override fun transactionFactory(): PostgresJdbcTransactionFactory {
        return PostgresJdbcTransactionFactory(dataSource)
    }
}
