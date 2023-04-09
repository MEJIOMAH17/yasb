package com.github.mejiomah17.yasb

import java.io.Closeable
import javax.sql.DataSource

internal interface CloseableDataSource : DataSource, Closeable
