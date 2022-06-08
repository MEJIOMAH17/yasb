package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.Expression
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryWithParameters

object ConditionContext

context (ConditionContext, DatabaseDialect)
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

context (ConditionContext, DatabaseDialect)
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

context (ConditionContext, DatabaseDialect)
fun <T> Expression<T>.eq(other: T): Expression<Boolean> {
    return eq(databaseType().parameterFactory().invoke(other))
}