package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Table

data class TestTable<S>(override val tableName: String = "test") : Table<TestTable<S>, S>
