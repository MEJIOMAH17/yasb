package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.query.Query

interface FromClause<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> : Query<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

interface FromClauseAndSelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    FromClause<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
