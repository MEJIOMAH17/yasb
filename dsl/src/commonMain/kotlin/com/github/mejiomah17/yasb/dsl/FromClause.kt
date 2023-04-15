package com.github.mejiomah17.yasb.dsl

interface FromClause

interface FromClauseAndSelectQuery<S> : FromClause, SelectQuery<S>
