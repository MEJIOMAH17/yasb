package com.github.mejiomah17.yaksb.dsl.generator

import java.io.Serializable

interface ColumnMetadataFactory : Serializable {
    fun create(
        name: String,
        type: String,
        nullable: Boolean,
    ): ColumnMetadata
}
