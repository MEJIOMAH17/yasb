package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.postgres.parameter.TextParameter
import com.github.mejiomah17.yasb.core.where
import com.github.mejiomah17.yasb.dsl.transaction.PostgresTransactionFactory
import com.github.mejiomah17.yasb.dsl.transaction.TransactionFactory
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import java.sql.Timestamp
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PostgresWhereTest : WhereTest<TestTable>, PostgresTest() {
    @BeforeEach
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
    fun `where filters query by uuid`() {
        transactionFactory().readUncommitted {
            val queryWithoutWhere = select(columnA(), columnB(), TestTable.c).from(tableTest())
            val given = queryWithoutWhere.execute()
            given.shouldHaveSize(2)
            given.get(1)[TestTable.c].toString().shouldBe("3e2220cd-e6a5-4eae-a258-6ed41e91c222")

            val query = queryWithoutWhere.where {
                TestTable.c.eq(UUID.fromString("3e2220cd-e6a5-4eae-a258-6ed41e91c222"))
            }
            val result = query.execute()
            result.shouldHaveSize(1)
            val row = result.single()
            row[TestTable.c].toString() shouldBe "3e2220cd-e6a5-4eae-a258-6ed41e91c222"
        }
    }

    @Test
    fun `where filters query by timestamp`() {
        transactionFactory().readUncommitted {
            val queryWithoutWhere = select(columnA(), columnB(), TestTable.c, TestTable.d).from(tableTest())
            val given = queryWithoutWhere.execute()
            given.shouldHaveSize(2)
            given.get(1)[TestTable.d].shouldBe(Timestamp.valueOf("2022-05-13 02:09:09.683195"))

            val query = queryWithoutWhere.where {
                TestTable.d.eq(Timestamp.valueOf("2022-05-13 02:09:09.683195"))
            }
            val result = query.execute()
            result.shouldHaveSize(1)
            val row = result.single()
            row[TestTable.d] shouldBe Timestamp.valueOf("2022-05-13 02:09:09.683195")
        }
    }

    override fun columnA(): Column<TestTable, String> {
        return TestTable.a
    }

    override fun columnB(): Column<TestTable, String> {
        return TestTable.b
    }

    override fun parameter(): Parameter<String> {
        return TextParameter("param")
    }

    override fun tableTest(): TestTable {
        return TestTable
    }

    override fun transactionFactory(): TransactionFactory {
        return PostgresTransactionFactory(dataSource)
    }
}