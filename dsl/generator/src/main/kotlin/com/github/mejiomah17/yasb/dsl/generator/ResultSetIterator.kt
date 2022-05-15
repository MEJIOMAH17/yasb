package com.github.mejiomah17.yasb.dsl.generator

import java.sql.ResultSet

class ResultSetIterator(private val resultSet: ResultSet) : Iterator<ResultSet> {

    override fun hasNext(): Boolean {
        return !(resultSet.isLast || resultSet.isAfterLast)
    }

    override fun next(): ResultSet {
        resultSet.next()
        return resultSet
    }
}