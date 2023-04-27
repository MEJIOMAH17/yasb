package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.query.QueryForExecute

interface InsertQuery<S> {
    fun buildInsertQuery(): QueryForExecute<S>
}
