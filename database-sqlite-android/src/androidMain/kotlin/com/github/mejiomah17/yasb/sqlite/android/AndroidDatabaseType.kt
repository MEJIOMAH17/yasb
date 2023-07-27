package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.core.DatabaseType
import com.github.mejiomah17.yasb.sqlite.android.parameter.AndroidSqliteDriverStatement

interface AndroidDatabaseType<T> : DatabaseType<T, Cursor, AndroidSqliteDriverStatement>
