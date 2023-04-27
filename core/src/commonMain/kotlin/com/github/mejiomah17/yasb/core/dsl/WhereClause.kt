package com.github.mejiomah17.yasb.core.dsl

interface WhereClause
interface WhereClauseAndSelectQuery<S> : WhereClause, SelectQuery<S>
