package com.github.mejiomah17.yasb.dsl.generator

import com.github.mejiomah17.yasb.core.ddl.Table
import kotlin.reflect.KClass

class TableMetadata(
    val tableName: String,
    val tableClass: KClass<out Table<*>>,
    val columns: List<ColumnMetadata>
)
