package com.github.mejiomah17.yaksb.core

import java.io.Closeable

interface Rows : Sequence<Row>, Closeable {
    override fun iterator(): Iterator<Row>
    override fun close()
}
