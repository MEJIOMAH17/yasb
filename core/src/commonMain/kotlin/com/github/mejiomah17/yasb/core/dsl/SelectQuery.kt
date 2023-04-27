package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.query.QueryForExecute

interface SelectQuery<S> {
    fun buildSelectQuery(): QueryForExecute<S>
}
