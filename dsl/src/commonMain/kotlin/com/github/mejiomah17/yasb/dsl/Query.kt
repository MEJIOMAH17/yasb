package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.query.QueryForExecute

interface SelectQuery<S> {
    fun buildSelectQuery(): QueryForExecute<S>
}

interface InsertQuery<S> {
    fun buildInsertQuery(): QueryForExecute<S>
}
