package com.tieuthanhliem

fun testArray() {
    val letters = Array<String>(26){i->
        ('a' + i).toString()
    }

    println(letters.joinToString(""))
}

fun testIntArray() {
    // Primitive type array
    val squares = IntArray(5) {i->
        i * i
    }
    println(squares.joinToString())
}