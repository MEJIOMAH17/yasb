package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.query.QueryForExecute

class GroupBy<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> internal constructor(
    private val selectQuery: SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    private val groupingElementList: GroupingElementList
) : GroupByClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {

    override fun buildSelectQuery(): QueryForExecute<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        val query = selectQuery.buildSelectQuery()
        val groupDefinition = groupingElementList.joinToString(",") {
            it.sqlDefinition
        }
        return QueryForExecute(
            sqlDefinition = "${query.sqlDefinition} GROUP BY $groupDefinition",
            parameters = query.parameters,
            returnExpressions = query.returnExpressions
        )
    }
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.groupBy(
    columns: List<Column<*, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
): GroupByClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return GroupBy(
        selectQuery = this,
        groupingElementList = columns.map { ColumnReference(it) }
    )
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.groupBy(
    vararg columns: Column<*, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): GroupByClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return groupBy(columns.toList())
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> WhereClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.groupBy(
    columns: List<Column<*, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>
): GroupByClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return GroupBy(
        selectQuery = this,
        groupingElementList = columns.map { ColumnReference(it) }
    )
}

fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> WhereClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.groupBy(
    vararg columns: Column<*, *, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
): GroupByClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return groupBy(columns.toList())
}

internal typealias GroupingElementList = List<GroupingElement>

internal interface GroupingElement {
    val sqlDefinition: String
}

private class ColumnReference(column: Column<*, *, *, *>) : GroupingElement {
    override val sqlDefinition: String = column.build().sqlDefinition
}
