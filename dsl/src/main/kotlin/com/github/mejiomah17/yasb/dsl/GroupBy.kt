package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Column
import com.github.mejiomah17.yasb.core.query.QueryForExecute

class GroupBy internal constructor(
    private val selectQuery: SelectQuery,
    private val groupingElementList: GroupingElementList,
) : GroupByClauseAndSelectQuery {

    override fun buildSelectQuery(): QueryForExecute {
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

fun FromClauseAndSelectQuery.groupBy(
    columns: List<Column<*, *>>
): GroupByClauseAndSelectQuery {
    return GroupBy(
        selectQuery = this,
        groupingElementList = columns.map { ColumnReference(it) }
    )
}

fun FromClauseAndSelectQuery.groupBy(
    vararg columns: Column<*, *>
): GroupByClauseAndSelectQuery {
    return groupBy(columns.toList())
}

fun WhereClauseAndSelectQuery.groupBy(
    columns: List<Column<*, *>>
): GroupByClauseAndSelectQuery {
    return GroupBy(
        selectQuery = this,
        groupingElementList = columns.map { ColumnReference(it) }
    )
}

fun WhereClauseAndSelectQuery.groupBy(
    vararg columns: Column<*, *>
): GroupByClauseAndSelectQuery {
    return groupBy(columns.toList())
}

internal typealias GroupingElementList = List<GroupingElement>

internal interface GroupingElement {
    val sqlDefinition: String
}

private class ColumnReference(column: Column<*, *>) : GroupingElement {
    override val sqlDefinition: String = column.build().sqlDefinition
}