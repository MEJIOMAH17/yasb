package com.github.mejiomah17.yasb.core.dsl

interface WhereClause
interface WhereClauseAndSelectQuery<DRIVER_DATA_SOURCE> : WhereClause, SelectQuery<DRIVER_DATA_SOURCE>
