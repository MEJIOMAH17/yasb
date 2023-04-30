package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Table

data class TestTable<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>(override val tableName: String = "test") :
    Table<TestTable<DRIVER_DATA_SOURCE, DRIVER_STATEMENT>, DRIVER_DATA_SOURCE, DRIVER_STATEMENT>
