package com.github.mejiomah17.yasb.dsl

import org.junit.Before

interface SqlTest {
    @Before
    fun init() {
        initSqlScripts().forEach {
            executeSql(it)
        }
    }

    fun initSqlScripts(): List<String>
    fun executeSql(sql: String)
}
