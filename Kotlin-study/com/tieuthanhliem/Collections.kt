package com.tieuthanhliem

import java.io.BufferedReader
import java.io.StringReader

fun readNumbers(reader: BufferedReader): List<Int?> {
    val result = ArrayList<Int?>()
    for (line in reader.lineSequence()) {
        val number = line.toIntOrNull()
        result.add(number)
    }
    return result
}

fun testReadNumbers() {
    val reader = BufferedReader(StringReader("1\nabc\n42"))
    val numbers = readNumbers(reader)
    println("numbers after read: $numbers")
    println("numbers after remove null: ${numbers.filterNotNull()}")
}