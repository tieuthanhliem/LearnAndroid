package com.tieuthanhliem

fun fail(message: String): Nothing {
    throw IllegalStateException(message)
}

fun testNothing() {
    fail("Error occurred")
}