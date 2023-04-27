package com.github.mejiomah17.yasb.core.dsl

interface GroupByClause

interface GroupByClauseAndSelectQuery<S> : GroupByClause, SelectQuery<S>
