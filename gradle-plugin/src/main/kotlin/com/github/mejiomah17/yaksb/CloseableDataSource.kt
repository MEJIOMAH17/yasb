package com.github.mejiomah17.yaksb

import java.io.Closeable
import javax.sql.DataSource

internal interface CloseableDataSource : DataSource, Closeable
