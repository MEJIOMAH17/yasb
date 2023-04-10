package com.github.mejiomah17.yasb.dsl.generator

import java.sql.ResultSet

class ResultSetIterator(private val resultSet: ResultSet) : Iterator<ResultSet> {
    // sqlite driver do not support isLast and previous method.
    private var nextResult: Boolean? = null

    override fun hasNext(): Boolean {
        return (nextResult ?: resultSet.next()).also {
            nextResult = it
        }
    }

    override fun next(): ResultSet {
        return if (nextResult == true) {
            nextResult = null
            resultSet
        } else {
            nextResult = null
            resultSet.next()
            resultSet
        }
    }
}
