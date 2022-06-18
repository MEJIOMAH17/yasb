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
    return condition(other, "=")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.eq(other: Parameter<T>): AliasableExpression<Boolean> {
    return condition(other, "=")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.eq(other: T): AliasableExpression<Boolean> {
    return eq(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.greater(other: ExpressionForCondition<T>): AliasableExpression<Boolean> {
    return condition(other, ">")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.greater(other: Parameter<T>): AliasableExpression<Boolean> {
    return condition(other, ">")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.greater(other: T): AliasableExpression<Boolean> {
    return greater(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.greaterEq(other: ExpressionForCondition<T>): AliasableExpression<Boolean> {
    return condition(other, ">=")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.greaterEq(other: Parameter<T>): AliasableExpression<Boolean> {
    return condition(other, ">=")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.greaterEq(other: T): AliasableExpression<Boolean> {
    return greaterEq(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.less(other: ExpressionForCondition<T>): AliasableExpression<Boolean> {
    return condition(other, "<")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.less(other: Parameter<T>): AliasableExpression<Boolean> {
    return condition(other, "<")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.less(other: T): AliasableExpression<Boolean> {
    return less(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.lessEq(other: ExpressionForCondition<T>): AliasableExpression<Boolean> {
    return condition(other, "<=")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.lessEq(other: Parameter<T>): AliasableExpression<Boolean> {
    return condition(other, "<=")
}

context (ConditionContext, DatabaseDialect)
fun <T> ExpressionForCondition<T>.lessEq(other: T): AliasableExpression<Boolean> {
    return lessEq(databaseType().parameterFactory().invoke(other))
}

context (ConditionContext, DatabaseDialect) private fun <T> ExpressionForCondition<T>.condition(
    other: ExpressionForCondition<T>,
    operator: String
): AliasableExpression<Boolean> {
    return object : AliasableExpression<Boolean> {
        override fun databaseType(): DatabaseType<Boolean> {
            return booleanType()
        }

        override fun build(): QueryPart {
            val leftExpression = this@condition.build()
            val rightExpression = other.build()
            return QueryPartImpl(
                sqlDefinition = "${leftExpression.sqlDefinition} $operator ${rightExpression.sqlDefinition}",
                parameters = leftExpression.parameters + rightExpression.parameters
            )
        }
    }
}

context (ConditionContext, DatabaseDialect) private fun <T> ExpressionForCondition<T>.condition(
    other: Parameter<T>,
    operator: String
): AliasableExpression<Boolean> {
    return object : AliasableExpression<Boolean> {
        override fun databaseType(): DatabaseType<Boolean> {
            return booleanType()
        }

        override fun build(): QueryPart {
            val leftExpression = this@condition.build()
            return QueryPartImpl(
                sqlDefinition = "${leftExpression.sqlDefinition} $operator ${other.parameterInJdbcQuery}",
                parameters = leftExpression.parameters + other
            )
        }
    }
}