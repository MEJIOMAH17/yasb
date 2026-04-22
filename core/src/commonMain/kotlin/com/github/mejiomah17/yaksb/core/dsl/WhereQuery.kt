package com.github.mejiomah17.yaksb.core.dsl

import com.github.mejiomah17.yaksb.core.query.Query

interface WhereQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> : Query<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

interface DeleteWhereQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> : WhereQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>

interface SelectWhereQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    WhereQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
