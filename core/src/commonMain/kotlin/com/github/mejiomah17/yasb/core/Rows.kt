package com.github.mejiomah17.yasb.core

import java.io.Closeable

interface Rows : Sequence<Row>, Closeable {
    override fun iterator(): Iterator<Row>
    override fun close()
}
