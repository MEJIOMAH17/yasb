package com.github.mejiomah17.yasb.dsl

interface WhereClause
interface WhereClauseAndSelectQuery<S> : WhereClause, SelectQuery<S>
