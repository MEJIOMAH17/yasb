package com.github.mejiomah17.yaksb.dsl.generator

import java.sql.ResultSet

class ResultSetIterator(
    private val resultSet: ResultSet,
) : Iterator<ResultSet> {
    // sqlite driver do not support isLast and previous method.
    private var nextResult: Boolean? = null

    override fun hasNext(): Boolean =
        (nextResult ?: resultSet.next()).also {
            nextResult = it
        }

    override fun next(): ResultSet =
        if (nextResult == true) {
            nextResult = null
            resultSet
        } else {
            nextResult = null
            resultSet.next()
            resultSet
        }
}
