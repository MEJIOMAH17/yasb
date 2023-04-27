package com.github.mejiomah17.yasb.core.dsl

interface GroupByClause

interface GroupByClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> : GroupByClause,
    SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
