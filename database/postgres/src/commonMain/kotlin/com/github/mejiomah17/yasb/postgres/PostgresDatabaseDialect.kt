package com.github.mejiomah17.yasb.sqlite

import com.github.mejiomah17.yasb.core.DatabaseDialect
import com.github.mejiomah17.yasb.core.SupportsInsertWithDefaultValue
import com.github.mejiomah17.yasb.core.SupportsLimit
interface PostgresDatabaseDialect<DRIVER_DATA_SOURCE> : DatabaseDialect<DRIVER_DATA_SOURCE>,
    SupportsInsertWithDefaultValue, SupportsLimit
