package com.tieuthanhliem

fun ignoreNulls(s: String?) {
    val sNotNull: String = s!!
    println(sNotNull.length)
}

fun makeNullException() {
    ignoreNulls(null)
}