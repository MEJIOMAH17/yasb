package com.github.mejiomah17.yaksb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yaksb.core.DatabaseType
import com.github.mejiomah17.yaksb.sqlite.android.parameter.AndroidSqliteDriverStatement

interface AndroidDatabaseType<T> : DatabaseType<T, Cursor, AndroidSqliteDriverStatement>
