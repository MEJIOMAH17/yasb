package com.github.mejiomah17.yasb.core.dsl

interface FromClause

interface FromClauseAndSelectQuery<S> : FromClause, SelectQuery<S>
