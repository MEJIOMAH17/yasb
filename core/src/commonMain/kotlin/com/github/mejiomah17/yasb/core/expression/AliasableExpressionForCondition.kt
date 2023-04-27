package com.github.mejiomah17.yasb.core.expression

interface AliasableExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    AliasableExpression<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    ExpressionForCondition<T, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
