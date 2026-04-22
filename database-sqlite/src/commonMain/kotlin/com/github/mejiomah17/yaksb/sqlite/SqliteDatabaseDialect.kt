package com.github.mejiomah17.yaksb.sqlite

import com.github.mejiomah17.yaksb.core.DatabaseDialect
import com.github.mejiomah17.yaksb.core.SupportsLimit

interface SqliteDatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    SupportsLimit
