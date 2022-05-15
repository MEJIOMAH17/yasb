package com.github.mejiomah17.yasb.core

import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryForExecute
import com.github.mejiomah17.yasb.core.query.QueryWithParameters
import com.github.mejiomah17.yasb.dsl.From
import com.github.mejiomah17.yasb.dsl.SelectQuery

class Where(
    private val from: From,
    private val where: Expression<Boolean>
) : SelectQuery {
    override fun buildSelectQuery(): QueryForExecute {
        val fromExpression = from.buildSelectQuery()
        val whereExpression = where.build()
        return QueryForExecute(
            value = "${fromExpression.value} WHERE ${whereExpression.value}",
            returnExpressions = fromExpression.returnExpressions,
            parameters = fromExpression.parameters + whereExpression.parameters
        )
    }
}

fun From.where(expression: WhereContext.() -> Expression<Boolean>): Where {
    return Where(this, expression(WhereContext))
}

object WhereContext

context (WhereContext, DatabaseDialect)
fun <T> Expression<T>.eq(other: Expression<T>): Expression<Boolean> {

    return object : Expression<Boolean> {
        override fun databaseType(): DatabaseType<Boolean> {
            return booleanType()
        }

        override fun build(): QueryWithParameters {
            val leftExpression = this@eq.build()
            val rightExpression = other.build()
            return QueryWithParameters(
                value = "${leftExpression.value} = ${rightExpression.value}",
                parameters = leftExpression.parameters + rightExpression.parameters
            )
        }
    }
}

context (WhereContext, DatabaseDialect)
fun <T> Expression<T>.eq(other: Parameter<T>): Expression<Boolean> {
    return object : Expression<Boolean> {
        override fun databaseType(): DatabaseType<Boolean> {
            return booleanType()
        }

        override fun build(): QueryWithParameters {
            val leftExpression = this@eq.build()
            return QueryWithParameters(
                value = "${leftExpression.value} = ${other.parameterInJdbcQuery}",
                parameters = leftExpression.parameters + other
            )
        }
    }
}

context (WhereContext, DatabaseDialect)
fun <T> Expression<T>.eq(other: T): Expression<Boolean> {
    return eq(databaseType().parameterFactory().invoke(other))
}