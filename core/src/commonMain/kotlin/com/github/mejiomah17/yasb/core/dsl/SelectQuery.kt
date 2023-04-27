package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.query.QueryForExecute

interface SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    fun buildSelectQuery(): QueryForExecute<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
}
