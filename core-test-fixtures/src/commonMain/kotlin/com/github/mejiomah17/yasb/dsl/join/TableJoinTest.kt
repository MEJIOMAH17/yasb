@file:Suppress("UNSUPPORTED_FEATURE", "UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL")

package com.github.mejiomah17.yasb.dsl.join

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.SupportsFullJoin
import com.github.mejiomah17.yasb.core.SupportsRightJoin
import com.github.mejiomah17.yasb.core.dsl.alias.`as`
import com.github.mejiomah17.yasb.core.dsl.eq
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.join.fullJoin
import com.github.mejiomah17.yasb.core.dsl.join.innerJoin
import com.github.mejiomah17.yasb.core.dsl.join.leftJoin
import com.github.mejiomah17.yasb.core.dsl.join.rightJoin
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.transaction.TransactionAtLeastRepeatableRead
import com.github.mejiomah17.yasb.core.transaction.TransactionFactory
import com.github.mejiomah17.yasb.dsl.SqlTest
import com.github.mejiomah17.yasb.dsl.TestTable
import io.kotest.matchers.shouldBe
import org.junit.Test

interface TableJoinTest<
    TABLE : TestTable<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : TransactionAtLeastRepeatableRead<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    > : SqlTest {

    // <editor-fold desc="Inner">
    @Test
    fun `builds_correct_query_for_inner_join`() {
        dialect().run {
            val query = select(firstTable().a, secondTable().a)
                .from(firstTable())
                .innerJoin(secondTable()) {
                    firstTable().a.eq(secondTable().a)
                }.buildSelectQuery().sql
            query shouldBe "SELECT FIRST.A, SECOND.A FROM FIRST INNER JOIN SECOND ON FIRST.A = SECOND.A"
        }
    }

    @Test
    fun `select_values_from_inner_join`() {
        transactionFactory().repeatableRead {
            val row = select(firstTable().b, secondTable().b)
                .from(firstTable())
                .innerJoin(secondTable()) {
                    firstTable().a.eq(secondTable().a)
                }.execute()
                .single()
            row[firstTable().b] shouldBe "B1"
            row[secondTable().b] shouldBe "B2"
        }
    }

    @Test
    fun `builds_correct_query_for_inner_join_with_aliased_table`() {
        dialect().run {
            val secondTable = secondTable().`as`("xxx")
            // TODO I should able invoke columns on table alias
            val joinColumnFromSecondTable = secondTable[secondTable().a]
            val query = select(firstTable().a, joinColumnFromSecondTable)
                .from(firstTable())
                .innerJoin(secondTable) {
                    firstTable().a.eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sql
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST INNER JOIN SECOND AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select_values_from_inner_join_with_aliased_table`() {
        transactionFactory().repeatableRead {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[secondTable().a]
            val dataColumnFromSecondTable = secondTable[secondTable().b]
            val row = select(firstTable().b, dataColumnFromSecondTable)
                .from(firstTable())
                .innerJoin(secondTable) {
                    firstTable().a.eq(joinColumnFromSecondTable)
                }.execute()
                .single()
            row[firstTable().b] shouldBe "B1"
            row[dataColumnFromSecondTable] shouldBe "B2"
        }
    }

    @Test
    fun `builds_correct_query_for_inner_join_with_nested_query`() {
        dialect().run {
            val nestedQuery = select(secondTable().a)
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[secondTable().a]
            val query = select(firstTable().a, joinColumnFromSecondTable)
                .from(firstTable())
                .innerJoin(nestedQuery) {
                    firstTable().a.eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sql
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST INNER JOIN (SELECT SECOND.A FROM SECOND) AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select_values_from_inner_join_with_nested_query`() {
        transactionFactory().repeatableRead {
            val nestedQuery = select(secondTable().a, secondTable().b)
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[secondTable().a]
            val dataColumnFromSecondTable = nestedQuery[secondTable().b]
            val row = select(firstTable().b, dataColumnFromSecondTable)
                .from(firstTable())
                .innerJoin(nestedQuery) {
                    firstTable().a.eq(joinColumnFromSecondTable)
                }.execute()
                .single()
            row[firstTable().b] shouldBe "B1"
            row[dataColumnFromSecondTable] shouldBe "B2"
        }
    }
    // </editor-fold>

    // <editor-fold desc="LEFT">
    @Test
    fun `builds_correct_query_for_left_join`() {
        dialect().run {
            val query = select(firstTable().a, secondTable().a)
                .from(firstTable())
                .leftJoin(secondTable()) {
                    firstTable().a.eq(secondTable().a)
                }.buildSelectQuery().sql
            query shouldBe "SELECT FIRST.A, SECOND.A FROM FIRST LEFT JOIN SECOND ON FIRST.A = SECOND.A"
        }
    }

    @Test
    fun `select_values_from_left_join`() {
        transactionFactory().repeatableRead {
            val rows = select(
                firstTable().a,
                firstTable().b,
                secondTable().a,
                secondTable().b
            )
                .from(firstTable())
                .leftJoin(secondTable()) {
                    firstTable().a.eq(secondTable().a)
                }.execute()
            rows.size shouldBe 2
            rows[0][firstTable().b] shouldBe "B1"
            rows[0][secondTable().b] shouldBe "B2"
            rows[1][firstTable().b] shouldBe "C1"
            rows[1][secondTable().b] shouldBe null
        }
    }

    @Test
    fun `builds_correct_query_for_left_join_with_aliased_table`() {
        val secondTable = secondTable().`as`("xxx")
        val joinColumnFromSecondTable = secondTable[secondTable().a]
        dialect().run {
            val query = select(firstTable().a, joinColumnFromSecondTable)
                .from(firstTable())
                .leftJoin(secondTable) {
                    firstTable().a.eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sql
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST LEFT JOIN SECOND AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select_values_from_left_join_with_aliased_table`() {
        transactionFactory().repeatableRead {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[secondTable().a]
            val dataColumnFromSecondTable = secondTable[secondTable().b]
            val rows = select(
                firstTable().a,
                firstTable().b,
                joinColumnFromSecondTable,
                dataColumnFromSecondTable
            )
                .from(firstTable())
                .leftJoin(secondTable) {
                    firstTable().a.eq(joinColumnFromSecondTable)
                }.execute()
            rows.size shouldBe 2
            rows[0][firstTable().b] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[1][firstTable().b] shouldBe "C1"
            rows[1][dataColumnFromSecondTable] shouldBe null
        }
    }

    @Test
    fun `builds_correct_query_for_left_join_with_nested_query`() {
        dialect().run {
            val nestedQuery = select(secondTable().a)
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[secondTable().a]
            val query = select(firstTable().a, joinColumnFromSecondTable)
                .from(firstTable())
                .leftJoin(nestedQuery) {
                    firstTable().a.eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sql
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST LEFT JOIN (SELECT SECOND.A FROM SECOND) AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select_values_from_left_join_with_nested_query`() {
        transactionFactory().repeatableRead {
            val nestedQuery = select(secondTable().a, secondTable().b)
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[secondTable().a]
            val dataColumnFromSecondTable = nestedQuery[secondTable().b]
            val rows = select(
                firstTable().a,
                firstTable().b,
                joinColumnFromSecondTable,
                dataColumnFromSecondTable
            )
                .from(firstTable())
                .leftJoin(nestedQuery) {
                    firstTable().a.eq(joinColumnFromSecondTable)
                }.execute()
            rows.size shouldBe 2
            rows[0][firstTable().b] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[1][firstTable().b] shouldBe "C1"
            rows[1][dataColumnFromSecondTable] shouldBe null
        }
    }

    // </editor-fold>

    // <editor-fold desc="RIGHT">
    @Test
    fun `builds_correct_query_for_right_join`() {
        dialect().run {
            if (this is SupportsRightJoin) {
                val query = select(firstTable().a, secondTable().a)
                    .from(firstTable())
                    .rightJoin(secondTable()) {
                        firstTable().a.eq(secondTable().a)
                    }.buildSelectQuery().sql
                query shouldBe "SELECT FIRST.A, SECOND.A FROM FIRST RIGHT JOIN SECOND ON FIRST.A = SECOND.A"
            }
        }
    }

    @Test
    fun `select_values_from_right_join`() {
        transactionFactory().repeatableRead {
            if (this is SupportsRightJoin) {
                val rows = select(
                    firstTable().a,
                    firstTable().b,
                    secondTable().a,
                    secondTable().b
                )
                    .from(firstTable())
                    .rightJoin(secondTable()) {
                        firstTable().a.eq(secondTable().a)
                    }.execute()
                rows.size shouldBe 2
                rows[0][firstTable().b] shouldBe "B1"
                rows[0][secondTable().b] shouldBe "B2"
                rows[1][firstTable().b] shouldBe null
                rows[1][secondTable().b] shouldBe "D1"
            }
        }
    }

    @Test
    fun `builds_correct_query_for_right_join_with_aliased_table`() {
        dialect().run {
            if (this is SupportsRightJoin) {
                val secondTable = secondTable().`as`("xxx")
                val joinColumnFromSecondTable = secondTable[secondTable().a]
                val query = select(firstTable().a, joinColumnFromSecondTable)
                    .from(firstTable())
                    .rightJoin(secondTable) {
                        firstTable().a.eq(joinColumnFromSecondTable)
                    }.buildSelectQuery().sql
                query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST RIGHT JOIN SECOND AS xxx ON FIRST.A = xxx.A"
            }
        }
    }

    @Test
    fun `select_values_from_right_join_with_aliased_table`() {
        transactionFactory().repeatableRead {
            if (this is SupportsRightJoin) {
                val secondTable = secondTable().`as`("xxx")
                val joinColumnFromSecondTable = secondTable[secondTable().a]
                val dataColumnFromSecondTable = secondTable[secondTable().b]
                val rows = select(
                    firstTable().a,
                    firstTable().b,
                    joinColumnFromSecondTable,
                    dataColumnFromSecondTable
                )
                    .from(firstTable())
                    .rightJoin(secondTable) {
                        firstTable().a.eq(joinColumnFromSecondTable)
                    }.execute()
                rows.size shouldBe 2
                rows[0][firstTable().b] shouldBe "B1"
                rows[0][dataColumnFromSecondTable] shouldBe "B2"
                rows[1][firstTable().b] shouldBe null
                rows[1][dataColumnFromSecondTable] shouldBe "D1"
            }
        }
    }

    @Test
    fun `builds_correct_query_for_right_join_with_nested_query`() {
        dialect().run {
            if (this is SupportsRightJoin) {
                val nestedQuery = select(secondTable().a)
                    .from(secondTable())
                    .`as`("xxx")
                val joinColumnFromSecondTable = nestedQuery[secondTable().a]
                val query = select(firstTable().a, joinColumnFromSecondTable)
                    .from(firstTable())
                    .rightJoin(nestedQuery) {
                        firstTable().a.eq(joinColumnFromSecondTable)
                    }.buildSelectQuery().sql
                query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST RIGHT JOIN (SELECT SECOND.A FROM SECOND) AS xxx ON FIRST.A = xxx.A"
            }
        }
    }

    @Test
    fun `select_values_from_right_join_with_nested_query`() {
        transactionFactory().repeatableRead {
            if (this is SupportsRightJoin) {
                val nestedQuery = select(secondTable().a, secondTable().b)
                    .from(secondTable())
                    .`as`("xxx")
                val joinColumnFromSecondTable = nestedQuery[secondTable().a]
                val dataColumnFromSecondTable = nestedQuery[secondTable().b]
                val rows = select(
                    firstTable().a,
                    firstTable().b,
                    joinColumnFromSecondTable,
                    dataColumnFromSecondTable
                )
                    .from(firstTable())
                    .rightJoin(nestedQuery) {
                        firstTable().a.eq(joinColumnFromSecondTable)
                    }.execute()
                rows.size shouldBe 2
                rows[0][firstTable().b] shouldBe "B1"
                rows[0][dataColumnFromSecondTable] shouldBe "B2"
                rows[1][firstTable().b] shouldBe null
                rows[1][dataColumnFromSecondTable] shouldBe "D1"
            }
        }
    }
    // </editor-fold>

    // <editor-fold desc="FULL">
    @Test
    fun `builds_correct_query_for_full_join`() {
        dialect().run {
            if (this is SupportsFullJoin) {
                val query = select(firstTable().a, secondTable().a)
                    .from(firstTable())
                    .fullJoin(secondTable()) {
                        firstTable().a.eq(secondTable().a)
                    }.buildSelectQuery().sql
                query shouldBe "SELECT FIRST.A, SECOND.A FROM FIRST FULL JOIN SECOND ON FIRST.A = SECOND.A"
            }
        }
    }

    @Test
    fun `select_values_from_full_join`() {
        transactionFactory().repeatableRead {
            if (this is SupportsFullJoin) {
                val rows = select(
                    firstTable().a,
                    firstTable().b,
                    secondTable().a,
                    secondTable().b
                )
                    .from(firstTable())
                    .fullJoin(secondTable()) {
                        firstTable().a.eq(secondTable().a)
                    }.execute()
                rows.size shouldBe 3
                rows[0][firstTable().b] shouldBe "B1"
                rows[0][secondTable().b] shouldBe "B2"
                rows[1][firstTable().b] shouldBe "C1"
                rows[1][secondTable().b] shouldBe null
                rows[2][firstTable().b] shouldBe null
                rows[2][secondTable().b] shouldBe "D1"
            }
        }
    }

    @Test
    fun `builds_correct_query_for_full_join_with_aliased_table`() {
        dialect().run {
            if (this is SupportsFullJoin) {
                val secondTable = secondTable().`as`("xxx")
                val joinColumnFromSecondTable = secondTable[secondTable().a]
                val query = select(firstTable().a, joinColumnFromSecondTable)
                    .from(firstTable())
                    .fullJoin(secondTable) {
                        firstTable().a.eq(joinColumnFromSecondTable)
                    }.buildSelectQuery().sql
                query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST FULL JOIN SECOND AS xxx ON FIRST.A = xxx.A"
            }
        }
    }

    @Test
    fun `select_values_from_full_join_with_aliased_table`() {
        transactionFactory().repeatableRead {
            if (this is SupportsFullJoin) {
                val secondTable = secondTable().`as`("xxx")
                val joinColumnFromSecondTable = secondTable[secondTable().a]
                val dataColumnFromSecondTable = secondTable[secondTable().b]
                val rows = select(
                    firstTable().a,
                    firstTable().b,
                    joinColumnFromSecondTable,
                    dataColumnFromSecondTable
                )
                    .from(firstTable())
                    .fullJoin(secondTable) {
                        firstTable().a.eq(joinColumnFromSecondTable)
                    }.execute()
                rows.size shouldBe 3
                rows[0][firstTable().b] shouldBe "B1"
                rows[0][dataColumnFromSecondTable] shouldBe "B2"
                rows[1][firstTable().b] shouldBe "C1"
                rows[1][dataColumnFromSecondTable] shouldBe null
                rows[2][firstTable().b] shouldBe null
                rows[2][dataColumnFromSecondTable] shouldBe "D1"
            }
        }
    }

    @Test
    fun `builds_correct_query_for_full_join_with_nested_query`() {
        dialect().run {
            if (this is SupportsFullJoin) {
                val nestedQuery = select(secondTable().a)
                    .from(secondTable())
                    .`as`("xxx")
                val joinColumnFromSecondTable = nestedQuery[secondTable().a]
                val query = select(firstTable().a, joinColumnFromSecondTable)
                    .from(firstTable())
                    .fullJoin(nestedQuery) {
                        firstTable().a.eq(joinColumnFromSecondTable)
                    }.buildSelectQuery().sql
                query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST FULL JOIN (SELECT SECOND.A FROM SECOND) AS xxx ON FIRST.A = xxx.A"
            }
        }
    }

    @Test
    fun `select_values_from_full_join_with_nested_query`() {
        transactionFactory().repeatableRead {
            if (this is SupportsFullJoin) {
                val nestedQuery = select(secondTable().a, secondTable().b)
                    .from(secondTable())
                    .`as`("xxx")
                val joinColumnFromSecondTable = nestedQuery[secondTable().a]
                val dataColumnFromSecondTable = nestedQuery[secondTable().b]
                val rows = select(
                    firstTable().a,
                    firstTable().b,
                    joinColumnFromSecondTable,
                    dataColumnFromSecondTable
                )
                    .from(firstTable())
                    .fullJoin(nestedQuery) {
                        firstTable().a.eq(joinColumnFromSecondTable)
                    }.execute()
                rows.size shouldBe 3
                rows[0][firstTable().b] shouldBe "B1"
                rows[0][dataColumnFromSecondTable] shouldBe "B2"
                rows[1][firstTable().b] shouldBe "C1"
                rows[1][dataColumnFromSecondTable] shouldBe null
                rows[2][firstTable().b] shouldBe null
                rows[2][dataColumnFromSecondTable] shouldBe "D1"
            }
        }
    }
    // </editor-fold>

    // <editor-fold desc="Multiple joins">
    @Test
    fun `builds_correct_query_for_multiple_join`() {
        dialect().run {
            val query = select(firstTable().a, secondTable().a, thirdTable().a)
                .from(firstTable())
                .leftJoin(secondTable()) {
                    firstTable().a.eq(secondTable().a)
                }.leftJoin(thirdTable()) {
                    firstTable().a.eq(thirdTable().a)
                }
                .buildSelectQuery().sql
            query shouldBe "SELECT FIRST.A, SECOND.A, THIRD.A FROM FIRST LEFT JOIN SECOND ON FIRST.A = SECOND.A LEFT JOIN THIRD ON FIRST.A = THIRD.A"
        }
    }

    @Test
    fun `select_values_from_for_multiple_join`() {
        transactionFactory().repeatableRead {
            val rows = select(
                firstTable().a,
                firstTable().b,
                secondTable().a,
                secondTable().b,
                thirdTable().b
            )
                .from(firstTable())
                .leftJoin(secondTable()) {
                    firstTable().a.eq(secondTable().a)
                }.leftJoin(thirdTable()) {
                    firstTable().a.eq(thirdTable().a)
                }.execute()
            rows.size shouldBe 2
            rows[0][firstTable().b] shouldBe "B1"
            rows[0][secondTable().b] shouldBe "B2"
            rows[0][thirdTable().b] shouldBe "B3"
            rows[1][firstTable().b] shouldBe "C1"
            rows[1][secondTable().b] shouldBe null
            rows[1][thirdTable().b] shouldBe null
        }
    }

    @Test
    fun `builds_correct_query_for_multiple_join_with_aliased_table`() {
        dialect().run {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[secondTable().a]
            val query = select(firstTable().a, joinColumnFromSecondTable, thirdTable().a)
                .from(firstTable())
                .leftJoin(secondTable) {
                    firstTable().a.eq(joinColumnFromSecondTable)
                }.leftJoin(thirdTable()) {
                    firstTable().a.eq(thirdTable().a)
                }
                .buildSelectQuery().sql
            query shouldBe "SELECT FIRST.A, xxx.A, THIRD.A FROM FIRST LEFT JOIN SECOND AS xxx ON FIRST.A = xxx.A LEFT JOIN THIRD ON FIRST.A = THIRD.A"
        }
    }

    @Test
    fun `select_values_from_for_multiple_join_with_aliased_table`() {
        transactionFactory().repeatableRead {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[secondTable().a]
            val dataColumnFromSecondTable = secondTable[secondTable().b]
            val rows = select(
                firstTable().a,
                firstTable().b,
                joinColumnFromSecondTable,
                dataColumnFromSecondTable,
                thirdTable().b
            )
                .from(firstTable())
                .leftJoin(secondTable) {
                    firstTable().a.eq(joinColumnFromSecondTable)
                }.leftJoin(thirdTable()) {
                    firstTable().a.eq(thirdTable().a)
                }.execute()
            rows.size shouldBe 2
            rows[0][firstTable().b] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[0][thirdTable().b] shouldBe "B3"
            rows[1][firstTable().b] shouldBe "C1"
            rows[1][dataColumnFromSecondTable] shouldBe null
            rows[1][thirdTable().b] shouldBe null
        }
    }

    @Test
    fun `builds_correct_query_for_multiple_join_with_nested_query`() {
        dialect().run {
            val nestedQuery = select(secondTable().a)
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[secondTable().a]
            val query = select(firstTable().a, joinColumnFromSecondTable, thirdTable().a)
                .from(firstTable())
                .leftJoin(nestedQuery) {
                    firstTable().a.eq(joinColumnFromSecondTable)
                }.leftJoin(thirdTable()) {
                    firstTable().a.eq(thirdTable().a)
                }.buildSelectQuery().sql
            query shouldBe "SELECT FIRST.A, xxx.A, THIRD.A FROM FIRST LEFT JOIN (SELECT SECOND.A FROM SECOND) AS xxx ON FIRST.A = xxx.A LEFT JOIN THIRD ON FIRST.A = THIRD.A"
        }
    }

    @Test
    fun `select_values_from_multiple_join_with_nested_query`() {
        transactionFactory().repeatableRead {
            val nestedQuery = select(secondTable().a, secondTable().b)
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[secondTable().a]
            val dataColumnFromSecondTable = nestedQuery[secondTable().b]
            val rows = select(
                firstTable().a,
                firstTable().b,
                joinColumnFromSecondTable,
                dataColumnFromSecondTable,
                thirdTable().b
            )
                .from(firstTable())
                .leftJoin(nestedQuery) {
                    firstTable().a.eq(joinColumnFromSecondTable)
                }.leftJoin(thirdTable()) {
                    firstTable().a.eq(thirdTable().a)
                }.execute()
            rows.size shouldBe 2
            rows[0][firstTable().b] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[0][thirdTable().b] shouldBe "B3"
            rows[1][firstTable().b] shouldBe "C1"
            rows[1][dataColumnFromSecondTable] shouldBe null
            rows[1][thirdTable().b] shouldBe null
        }
    }
    // </editor-fold>

    fun firstTable(): TestTable<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun secondTable(): TABLE
    fun thirdTable(): TestTable<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun transactionFactory(): TransactionFactory<DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, *, *, TRANSACTION, *>
    fun dialect(): DIALECT
}
