package com.github.mejiomah17.yasb.dsl.generator

import java.io.Serializable

interface ColumnMetadataFactory : Serializable {
    fun create(name: String, type: String, nullable: Boolean): ColumnMetadata
}
