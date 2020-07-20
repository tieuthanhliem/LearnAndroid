package com.tieuthanhliem

import java.io.File

fun File.isInsideHiddenDirectory() =
        generateSequence(this) { it.parentFile }.any { it.isHidden }

fun checkFileInHiddenDirectory() {
    val file = File("/Users/liem/GitHub/Kotlin-study/Sequence.kt")
    println(file.isInsideHiddenDirectory())
}

fun printNaturalNumbersTo100() {
    val naturalNumbers = generateSequence(0) { it + 1 }
    val numbersTo100 = naturalNumbers.takeWhile {
        it <= 100
    }

    println(numbersTo100.toList())
}