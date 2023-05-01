package com.github.mejiomah17.yasb.core

/**
 * Marker interface is database supports statements like
 * INSERT INTO USERS(NAME,CREATE_DATE) VALUES ('MARK', DEFAULT) RETURNING ID, NAME, CREATE_DATE
 */
interface SupportsInsertReturning
