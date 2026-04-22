package com.github.mejiomah17.yaksb.core

interface Rows :
    Sequence<Row>,
    AutoCloseable {
    override fun iterator(): Iterator<Row>

    override fun close()
}
