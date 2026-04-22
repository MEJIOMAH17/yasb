package com.github.mejiomah17.yaksb.core.dsl

import com.github.mejiomah17.yaksb.core.DatabaseDialect
import com.github.mejiomah17.yaksb.core.DatabaseType
import com.github.mejiomah17.yaksb.core.expression.AliasableExpressionForCondition
import com.github.mejiomah17.yaksb.core.expression.ExpressionForCondition
import com.github.mejiomah17.yaksb.core.parameter.Parameter

object ConditionContext

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.eq(
    other: ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = condition(other, "=")

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.eq(
    other: Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = condition(other, "=")

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.eq(
    other: T,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = eq(databaseType().parameterFactory().invoke(other))

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.greater(
    other: ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = condition(other, ">")

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.greater(
    other: Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = condition(other, ">")

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.greater(
    other: T,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = greater(databaseType().parameterFactory().invoke(other))

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.greaterEq(
    other: ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = condition(other, ">=")

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.greaterEq(
    other: Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = condition(other, ">=")

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.greaterEq(
    other: T,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> =
    greaterEq(databaseType().parameterFactory().invoke(other))

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.less(
    other: ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = condition(other, "<")

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.less(
    other: Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = condition(other, "<")

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.less(
    other: T,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = less(databaseType().parameterFactory().invoke(other))

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.lessEq(
    other: ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = condition(other, "<=")

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.lessEq(
    other: Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = condition(other, "<=")

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.lessEq(
    other: T,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = lessEq(databaseType().parameterFactory().invoke(other))

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<String?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.like(
    other: Parameter<String?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = condition(other, "like")

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<String?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.like(
    other: String,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    val parameter: Parameter<String?, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> =
        databaseType().parameterFactory().invoke(other)
    return like(parameter)
}

context (context: ConditionContext, dialect: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.inListParameters(
    list: Iterable<Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = condition(list, "in")

context (_: ConditionContext, _: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.inList(
    list: Iterable<T>,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> =
    inListParameters(
        list.map {
            databaseType().parameterFactory().invoke(it)
        },
    )

context (_: ConditionContext, dialect: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.and(
    other: ExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> =
    object : AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        override fun databaseType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = dialect.booleanType()

        override fun sql(): String = "(${this@and.sql()}) AND (${other.sql()})"

        override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = this@and.parameters() + other.parameters()
    }

context (_: ConditionContext, dialect: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
fun <DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.or(
    other: ExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> =
    object : AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        override fun databaseType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = dialect.booleanType()

        override fun sql(): String = "(${this@or.sql()}) OR (${other.sql()})"

        override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = this@or.parameters() + other.parameters()
    }

context (_: ConditionContext, dialect: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
private fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.condition(
    other: ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    operator: String,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> =
    object : AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        override fun databaseType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = dialect.booleanType()

        override fun sql(): String = "${this@condition.sql()} $operator ${other.sql()}"

        override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> =
            this@condition.parameters() + other.parameters()
    }

context (_: ConditionContext, dialect: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
private fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.condition(
    other: Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    operator: String,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> =
    object : AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        override fun databaseType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = dialect.booleanType()

        override fun sql(): String = "${this@condition.sql()} $operator ${other.parameterInSql}"

        override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = this@condition.parameters() + other
    }

context (_: ConditionContext, dialect: DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>)
private fun <T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>.condition(
    others: Iterable<Parameter<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>>,
    operator: String,
): AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    return object : AliasableExpressionForCondition<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
        override fun databaseType(): DatabaseType<Boolean, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> = dialect.booleanType()

        override fun sql(): String {
            val parameters =
                others.joinToString(",") {
                    it.parameterInSql
                }
            return "${this@condition.sql()} $operator ($parameters)"
        }

        override fun parameters(): List<Parameter<*, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>> = this@condition.parameters() + others
    }
}
