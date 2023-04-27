package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.query.QueryForExecute

interface InsertQuery<DRIVER_DATA_SOURCE> {
    fun buildInsertQuery(): QueryForExecute<DRIVER_DATA_SOURCE>
}
