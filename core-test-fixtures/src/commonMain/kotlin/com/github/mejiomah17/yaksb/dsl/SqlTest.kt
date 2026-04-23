package com.github.mejiomah17.yaksb.dsl

import kotlin.test.BeforeTest

interface SqlTest {
    @BeforeTest
    fun init() {
        initSqlScripts().forEach {
            executeSql(it)
        }
    }

    fun initSqlScripts(): List<String>

    fun executeSql(sql: String)
}
