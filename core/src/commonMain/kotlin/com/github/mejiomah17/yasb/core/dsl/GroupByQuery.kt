package com.github.mejiomah17.yasb.core.dsl

interface GroupByQuery

interface GroupByQueryAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    GroupByQuery,
    SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
