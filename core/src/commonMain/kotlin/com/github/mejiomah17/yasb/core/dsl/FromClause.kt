package com.github.mejiomah17.yasb.core.dsl

interface FromClause

interface FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    FromClause,
    SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
