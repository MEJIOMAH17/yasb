package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.query.QueryForExecute

interface SelectQuery {
    fun buildSelectQuery(): QueryForExecute
}

interface InsertQuery {
    fun buildInsertQuery(): QueryForExecute
}