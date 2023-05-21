package com.github.mejiomah17.yasb.core.jdbc

import com.github.mejiomah17.yasb.core.DatabaseType
import java.sql.PreparedStatement
import java.sql.ResultSet

interface JDBCDatabaseType<T> : DatabaseType<T, ResultSet, PreparedStatement>
