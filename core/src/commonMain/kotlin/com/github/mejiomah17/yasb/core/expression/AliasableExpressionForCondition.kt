package com.github.mejiomah17.yasb.core.expression

interface AliasableExpressionForCondition<T, DRIVER_DATA_SOURCE> :
    AliasableExpression<T, DRIVER_DATA_SOURCE>,
    ExpressionForCondition<T, DRIVER_DATA_SOURCE>
