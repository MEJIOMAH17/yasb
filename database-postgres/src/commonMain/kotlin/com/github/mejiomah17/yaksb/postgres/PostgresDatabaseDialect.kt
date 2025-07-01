package com.github.mejiomah17.yaksb.sqlite

import com.github.mejiomah17.yaksb.core.DatabaseDialect
import com.github.mejiomah17.yaksb.core.SupportsFullJoin
import com.github.mejiomah17.yaksb.core.SupportsInsertReturning
import com.github.mejiomah17.yaksb.core.SupportsInsertWithDefaultValue
import com.github.mejiomah17.yaksb.core.SupportsLimit
import com.github.mejiomah17.yaksb.core.SupportsRightJoin

interface PostgresDatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT> :
    DatabaseDialect<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>,
    SupportsInsertWithDefaultValue,
    SupportsInsertReturning,
    SupportsLimit,
    SupportsRightJoin,
    SupportsFullJoin
