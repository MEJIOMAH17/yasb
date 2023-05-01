package com.github.mejiomah17.yasb.core

/**
 * Marker interface is database supports statements like
 * SELECT column_name(s)
 * FROM table1
 * FULL OUTER JOIN table2
 * ON table1.column_name = table2.column_name
 * WHERE condition;
 */
interface SupportsFullJoin
