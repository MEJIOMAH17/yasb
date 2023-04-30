package com.github.mejiomah17.yasb.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.core.DatabaseType
// TODO is String realy a type of PreparedStatement?
interface AndroidDatabaseType<T> : DatabaseType<T, Cursor, String>
