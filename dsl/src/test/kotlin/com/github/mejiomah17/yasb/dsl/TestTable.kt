package com.github.mejiomah17.yasb.dsl

import com.github.mejiomah17.yasb.core.ddl.Table

object TestTable : Table<TestTable> {
    override val tableName: String = "test"
}
