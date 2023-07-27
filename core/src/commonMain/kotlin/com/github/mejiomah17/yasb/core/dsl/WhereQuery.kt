package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.query.Query

interface WhereQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> : Query<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

interface DeleteWhereQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    WhereQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

interface SelectWhereQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    WhereQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
