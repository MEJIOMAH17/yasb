package com.github.mejiomah17.yaksb.core.jdbc

import com.github.mejiomah17.yaksb.core.DatabaseType
import java.sql.PreparedStatement
import java.sql.ResultSet

interface JDBCDatabaseType<T> : DatabaseType<T, ResultSet, PreparedStatement>
