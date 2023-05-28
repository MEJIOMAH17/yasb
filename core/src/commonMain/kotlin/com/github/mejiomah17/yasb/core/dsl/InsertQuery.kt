package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.query.ReturningQuery

interface InsertQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    fun buildInsertQuery(): ReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
}
