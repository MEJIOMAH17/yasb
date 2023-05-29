package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.query.OldReturningQuery

interface InsertQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> {
    fun buildInsertQuery(): OldReturningQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
}
