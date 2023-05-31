package com.github.mejiomah17.yasb.core.dsl

import com.github.mejiomah17.yasb.core.query.Query

interface FromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> : Query<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
interface DeleteFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> : FromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
interface SelectFromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    FromQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    SelectQuery<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
