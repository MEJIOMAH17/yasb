package com.github.mejiomah17.yasb.dsl.join

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.ddl.Table
import com.github.mejiomah17.yasb.core.dsl.alias.`as`
import com.github.mejiomah17.yasb.core.dsl.eq
import com.github.mejiomah17.yasb.core.dsl.from
import com.github.mejiomah17.yasb.core.dsl.join.fullJoin
import com.github.mejiomah17.yasb.core.dsl.join.innerJoin
import com.github.mejiomah17.yasb.core.dsl.join.leftJoin
import com.github.mejiomah17.yasb.core.dsl.join.rightJoin
import com.github.mejiomah17.yasb.core.dsl.select
import com.github.mejiomah17.yasb.core.transaction.Transaction
import com.github.mejiomah17.yasb.core.transaction.TransactionFactory
import io.kotest.matchers.shouldBe
import org.junit.Test

interface TableJoinTest<
    TABLE : Table<TABLE, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    DRIVER_DATA_SOURCE,
    DRIVER_STATEMENT,
    DIALECT : DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    TRANSACTION : Transaction<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    > {
    // <editor-fold desc="Inner">
    @Test
    fun `builds_correct_query_for_inner_join`() {
        dialect().run {
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable())
                .from(firstTable())
                .innerJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, SECOND.A FROM FIRST INNER JOIN SECOND ON FIRST.A = SECOND.A"
        }
    }

    @Test
    fun `select_values_from_inner_join`() {
        transactionFactory().readCommitted {
            val row = select(dataColumnFromFirstTable(), dataColumnFromSecondTable())
                .from(firstTable())
                .innerJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.execute()
                .single()
            row[dataColumnFromFirstTable()] shouldBe "B1"
            row[dataColumnFromSecondTable()] shouldBe "B2"
        }
    }

    @Test
    fun `builds_correct_query_for_inner_join_with_aliased_table`() {
        dialect().run {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable)
                .from(firstTable())
                .innerJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST INNER JOIN SECOND AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select_values_from_inner_join_with_aliased_table`() {
        transactionFactory().readCommitted {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = secondTable[dataColumnFromSecondTable()]
            val row = select(dataColumnFromFirstTable(), dataColumnFromSecondTable)
                .from(firstTable())
                .innerJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.execute()
                .single()
            row[dataColumnFromFirstTable()] shouldBe "B1"
            row[dataColumnFromSecondTable] shouldBe "B2"
        }
    }

    @Test
    fun `builds_correct_query_for_inner_join_with_nested_query`() {
        dialect().run {
            val nestedQuery = select(joinColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable)
                .from(firstTable())
                .innerJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST INNER JOIN (SELECT SECOND.A FROM SECOND) AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select_values_from_inner_join_with_nested_query`() {
        transactionFactory().readCommitted {
            val nestedQuery = select(joinColumnFromSecondTable(), dataColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = nestedQuery[dataColumnFromSecondTable()]
            val row = select(dataColumnFromFirstTable(), dataColumnFromSecondTable)
                .from(firstTable())
                .innerJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.execute()
                .single()
            row[dataColumnFromFirstTable()] shouldBe "B1"
            row[dataColumnFromSecondTable] shouldBe "B2"
        }
    }
    // </editor-fold>

    // <editor-fold desc="LEFT">
    @Test
    fun `builds_correct_query_for_left_join`() {
        dialect().run {
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable())
                .from(firstTable())
                .leftJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, SECOND.A FROM FIRST LEFT JOIN SECOND ON FIRST.A = SECOND.A"
        }
    }

    @Test
    fun `select_values_from_left_join`() {
        transactionFactory().readCommitted {
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable(),
                dataColumnFromSecondTable()
            )
                .from(firstTable())
                .leftJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable()] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable()] shouldBe null
        }
    }

    @Test
    fun `builds_correct_query_for_left_join_with_aliased_table`() {
        val secondTable = secondTable().`as`("xxx")
        val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
        dialect().run {
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable)
                .from(firstTable())
                .leftJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST LEFT JOIN SECOND AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select_values_from_left_join_with_aliased_table`() {
        transactionFactory().readCommitted {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = secondTable[dataColumnFromSecondTable()]
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable,
                dataColumnFromSecondTable
            )
                .from(firstTable())
                .leftJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable] shouldBe null
        }
    }

    @Test
    fun `builds_correct_query_for_left_join_with_nested_query`() {
        dialect().run {
            val nestedQuery = select(joinColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable)
                .from(firstTable())
                .leftJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST LEFT JOIN (SELECT SECOND.A FROM SECOND) AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select_values_from_left_join_with_nested_query`() {
        transactionFactory().readCommitted {
            val nestedQuery = select(joinColumnFromSecondTable(), dataColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = nestedQuery[dataColumnFromSecondTable()]
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable,
                dataColumnFromSecondTable
            )
                .from(firstTable())
                .leftJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable] shouldBe null
        }
    }

    // </editor-fold>

    // <editor-fold desc="RIGHT">
    @Test
    fun `builds_correct_query_for_right_join`() {
        dialect().run {
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable())
                .from(firstTable())
                .rightJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, SECOND.A FROM FIRST RIGHT JOIN SECOND ON FIRST.A = SECOND.A"
        }
    }

    @Test
    fun `select_values_from_right_join`() {
        transactionFactory().readCommitted {
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable(),
                dataColumnFromSecondTable()
            )
                .from(firstTable())
                .rightJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable()] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe null
            rows[1][dataColumnFromSecondTable()] shouldBe "D1"
        }
    }

    @Test
    fun `builds_correct_query_for_right_join_with_aliased_table`() {
        dialect().run {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable)
                .from(firstTable())
                .rightJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST RIGHT JOIN SECOND AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select_values_from_right_join_with_aliased_table`() {
        transactionFactory().readCommitted {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = secondTable[dataColumnFromSecondTable()]
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable,
                dataColumnFromSecondTable
            )
                .from(firstTable())
                .rightJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe null
            rows[1][dataColumnFromSecondTable] shouldBe "D1"
        }
    }

    @Test
    fun `builds_correct_query_for_right_join_with_nested_query`() {
        dialect().run {
            val nestedQuery = select(joinColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable)
                .from(firstTable())
                .rightJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST RIGHT JOIN (SELECT SECOND.A FROM SECOND) AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select_values_from_right_join_with_nested_query`() {
        transactionFactory().readCommitted {
            val nestedQuery = select(joinColumnFromSecondTable(), dataColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = nestedQuery[dataColumnFromSecondTable()]
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable,
                dataColumnFromSecondTable
            )
                .from(firstTable())
                .rightJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe null
            rows[1][dataColumnFromSecondTable] shouldBe "D1"
        }
    }
    // </editor-fold>

    // <editor-fold desc="FULL">
    @Test
    fun `builds_correct_query_for_full_join`() {
        dialect().run {
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable())
                .from(firstTable())
                .fullJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, SECOND.A FROM FIRST FULL JOIN SECOND ON FIRST.A = SECOND.A"
        }
    }

    @Test
    fun `select_values_from_full_join`() {
        transactionFactory().readCommitted {
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable(),
                dataColumnFromSecondTable()
            )
                .from(firstTable())
                .fullJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.execute()
            rows.size shouldBe 3
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable()] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable()] shouldBe null
            rows[2][dataColumnFromFirstTable()] shouldBe null
            rows[2][dataColumnFromSecondTable()] shouldBe "D1"
        }
    }

    @Test
    fun `builds_correct_query_for_full_join_with_aliased_table`() {
        dialect().run {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable)
                .from(firstTable())
                .fullJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST FULL JOIN SECOND AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select_values_from_full_join_with_aliased_table`() {
        transactionFactory().readCommitted {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = secondTable[dataColumnFromSecondTable()]
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable,
                dataColumnFromSecondTable
            )
                .from(firstTable())
                .fullJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.execute()
            rows.size shouldBe 3
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable] shouldBe null
            rows[2][dataColumnFromFirstTable()] shouldBe null
            rows[2][dataColumnFromSecondTable] shouldBe "D1"
        }
    }

    @Test
    fun `builds_correct_query_for_full_join_with_nested_query`() {
        dialect().run {
            val nestedQuery = select(joinColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable)
                .from(firstTable())
                .fullJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A FROM FIRST FULL JOIN (SELECT SECOND.A FROM SECOND) AS xxx ON FIRST.A = xxx.A"
        }
    }

    @Test
    fun `select_values_from_full_join_with_nested_query`() {
        transactionFactory().readCommitted {
            val nestedQuery = select(joinColumnFromSecondTable(), dataColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = nestedQuery[dataColumnFromSecondTable()]
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable,
                dataColumnFromSecondTable
            )
                .from(firstTable())
                .fullJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.execute()
            rows.size shouldBe 3
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable] shouldBe null
            rows[2][dataColumnFromFirstTable()] shouldBe null
            rows[2][dataColumnFromSecondTable] shouldBe "D1"
        }
    }
    // </editor-fold>

    // <editor-fold desc="Multiple joins">
    @Test
    fun `builds_correct_query_for_multiple_join`() {
        dialect().run {
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable(), joinColumnFromThirdTable())
                .from(firstTable())
                .leftJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.leftJoin(thirdTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromThirdTable())
                }
                .buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, SECOND.A, THIRD.A FROM FIRST LEFT JOIN SECOND ON FIRST.A = SECOND.A LEFT JOIN THIRD ON FIRST.A = THIRD.A"
        }
    }

    @Test
    fun `select_values_from_for_multiple_join`() {
        transactionFactory().readCommitted {
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable(),
                dataColumnFromSecondTable(),
                dataColumnFromThirdTable()
            )
                .from(firstTable())
                .leftJoin(secondTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable())
                }.leftJoin(thirdTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromThirdTable())
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable()] shouldBe "B2"
            rows[0][dataColumnFromThirdTable()] shouldBe "B3"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable()] shouldBe null
            rows[1][dataColumnFromThirdTable()] shouldBe null
        }
    }

    @Test
    fun `builds_correct_query_for_multiple_join_with_aliased_table`() {
        dialect().run {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable, joinColumnFromThirdTable())
                .from(firstTable())
                .leftJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.leftJoin(thirdTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromThirdTable())
                }
                .buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A, THIRD.A FROM FIRST LEFT JOIN SECOND AS xxx ON FIRST.A = xxx.A LEFT JOIN THIRD ON FIRST.A = THIRD.A"
        }
    }

    @Test
    fun `select_values_from_for_multiple_join_with_aliased_table`() {
        transactionFactory().readCommitted {
            val secondTable = secondTable().`as`("xxx")
            val joinColumnFromSecondTable = secondTable[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = secondTable[dataColumnFromSecondTable()]
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable,
                dataColumnFromSecondTable,
                dataColumnFromThirdTable()
            )
                .from(firstTable())
                .leftJoin(secondTable) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.leftJoin(thirdTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromThirdTable())
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[0][dataColumnFromThirdTable()] shouldBe "B3"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable] shouldBe null
            rows[1][dataColumnFromThirdTable()] shouldBe null
        }
    }

    @Test
    fun `builds_correct_query_for_multiple_join_with_nested_query`() {
        dialect().run {
            val nestedQuery = select(joinColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val query = select(joinColumnFromFirstTable(), joinColumnFromSecondTable, joinColumnFromThirdTable())
                .from(firstTable())
                .leftJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.leftJoin(thirdTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromThirdTable())
                }.buildSelectQuery().sqlDefinition
            query shouldBe "SELECT FIRST.A, xxx.A, THIRD.A FROM FIRST LEFT JOIN (SELECT SECOND.A FROM SECOND) AS xxx ON FIRST.A = xxx.A LEFT JOIN THIRD ON FIRST.A = THIRD.A"
        }
    }

    @Test
    fun `select_values_from_multiple_join_with_nested_query`() {
        transactionFactory().readCommitted {
            val nestedQuery = select(joinColumnFromSecondTable(), dataColumnFromSecondTable())
                .from(secondTable())
                .`as`("xxx")
            val joinColumnFromSecondTable = nestedQuery[joinColumnFromSecondTable()]
            val dataColumnFromSecondTable = nestedQuery[dataColumnFromSecondTable()]
            val rows = select(
                joinColumnFromFirstTable(),
                dataColumnFromFirstTable(),
                joinColumnFromSecondTable,
                dataColumnFromSecondTable,
                dataColumnFromThirdTable()
            )
                .from(firstTable())
                .leftJoin(nestedQuery) {
                    joinColumnFromFirstTable().eq(joinColumnFromSecondTable)
                }.leftJoin(thirdTable()) {
                    joinColumnFromFirstTable().eq(joinColumnFromThirdTable())
                }.execute()
            rows.size shouldBe 2
            rows[0][dataColumnFromFirstTable()] shouldBe "B1"
            rows[0][dataColumnFromSecondTable] shouldBe "B2"
            rows[0][dataColumnFromThirdTable()] shouldBe "B3"
            rows[1][dataColumnFromFirstTable()] shouldBe "C1"
            rows[1][dataColumnFromSecondTable] shouldBe null
            rows[1][dataColumnFromThirdTable()] shouldBe null
        }
    }
    // </editor-fold>

    fun firstTable(): Table<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun joinColumnFromFirstTable(): Column<*, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun dataColumnFromFirstTable(): Column<*, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun secondTable(): TABLE
    fun joinColumnFromSecondTable(): Column<TABLE, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun dataColumnFromSecondTable(): Column<TABLE, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun thirdTable(): Table<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun joinColumnFromThirdTable(): Column<*, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun dataColumnFromThirdTable(): Column<*, String, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
    fun transactionFactory(): TransactionFactory<DRIVER_DATA_SOURCE, DRIVER_STATEMENT, DIALECT, TRANSACTION>
    fun dialect(): DIALECT
}
