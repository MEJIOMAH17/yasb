package com.github.mejiomah17.yasb.core.dsl

interface GroupByClause

interface GroupByClauseAndSelectQuery<DRIVER_DATA_SOURCE> : GroupByClause, SelectQuery<DRIVER_DATA_SOURCE>
