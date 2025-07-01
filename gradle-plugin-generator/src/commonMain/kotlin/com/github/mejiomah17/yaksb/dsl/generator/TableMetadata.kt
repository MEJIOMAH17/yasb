package com.github.mejiomah17.yaksb.dsl.generator

import com.github.mejiomah17.yaksb.core.ddl.Table
import kotlin.reflect.KClass

class TableMetadata(
    val tableName: String,
    val tableClassQualifiedName: String,
    val columns: List<ColumnMetadata>
) {
    constructor(
        tableName: String,
        tableClass: KClass<out Table<*, *, *>>,
        columns: List<ColumnMetadata>
    ) : this(tableName, tableClass.qualifiedName!!, columns)
}
