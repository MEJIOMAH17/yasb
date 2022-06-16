package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.core.expression.AliasableExpression
import com.github.mejiomah17.yasb.core.expression.ExpressionForCondition
import com.github.mejiomah17.yasb.core.parameter.Parameter
import com.github.mejiomah17.yasb.core.query.QueryPart
import com.github.mejiomah17.yasb.core.query.QueryPartImpl

object ConditionContext

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.eq(other: ExpressionForCondition<T>): AliasableExpression<Boolean> {

    return object : AliasableExpression<Boolean> {
        override fun databaseType(): DatabaseType<Boolean> {
            return booleanType()
        }

        override fun build(): QueryPart {
            val leftExpression = this@eq.build()
            val rightExpression = other.build()
            return QueryPartImpl(
                sqlDefinition = "${leftExpression.sqlDefinition} = ${rightExpression.sqlDefinition}",
                parameters = leftExpression.parameters + rightExpression.parameters
            )
        }
    }
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.eq(other: Parameter<T>): AliasableExpression<Boolean> {
    return object : AliasableExpression<Boolean> {
        override fun databaseType(): DatabaseType<Boolean> {
            return booleanType()
        }

        override fun build(): QueryPart {
            val leftExpression = this@eq.build()
            return QueryPartImpl(
                sqlDefinition = "${leftExpression.sqlDefinition} = ${other.parameterInJdbcQuery}",
                parameters = leftExpression.parameters + other
            )
        }
    }
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.eq(other: T): AliasableExpression<Boolean> {
    return eq(databaseType().parameterFactory().invoke(other))
}