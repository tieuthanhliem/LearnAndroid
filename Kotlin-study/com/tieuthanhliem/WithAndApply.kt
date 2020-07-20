package com.tieuthanhliem

fun printAlphabetSongWith() {
    val alphabet = with(StringBuilder()) {
        for (letter in 'A'..'Z') {
            append(letter)
        }
        append("\nNow I know the alphabet!")
        toString()
    }

    println(alphabet)
}

fun printAlphabetSongApply() {
    val alphabetApply = StringBuilder().apply {
        for (letter in 'A'..'Z') {
            append(letter)
        }
        append("\nNow I know the alphabet!")
    }.toString()
    println(alphabetApply)
}