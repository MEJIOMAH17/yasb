package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.query.OldReturningQuery

interface SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    fun buildSelectQuery(): OldReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
}
