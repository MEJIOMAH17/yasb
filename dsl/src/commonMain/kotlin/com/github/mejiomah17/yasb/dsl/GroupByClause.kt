package com.github.mejiomah17.yasb.dsl

interface GroupByClause

interface GroupByClauseAndSelectQuery<S> : GroupByClause, SelectQuery<S>
