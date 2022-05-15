package com.github.mejiomah17.yasb.dsl.generator

interface ColumnMetadataFactory {
    fun create(name: String, type: String, nullable: Boolean): ColumnMetadata
}