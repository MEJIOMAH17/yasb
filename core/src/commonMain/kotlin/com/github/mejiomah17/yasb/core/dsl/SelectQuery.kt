package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.query.ReturningQuery

interface SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    fun buildSelectQuery(): ReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
}
