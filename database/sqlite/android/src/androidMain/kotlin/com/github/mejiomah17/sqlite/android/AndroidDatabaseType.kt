package com.github.mejiomah17.sqlite.android

import android.database.Cursor
import com.github.mejiomah17.yasb.core.DatabaseType

interface AndroidDatabaseType<T> : DatabaseType<T, Cursor>
