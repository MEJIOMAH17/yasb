package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Table

data class TestTable<DRIVER_DATA_SOURCE>(override val tableName: String = "test") :
    Table<TestTable<DRIVER_DATA_SOURCE>, DRIVER_DATA_SOURCE>
