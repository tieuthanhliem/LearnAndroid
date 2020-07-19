package com.tieuthanhliem

import java.util.*

fun map(){
    val binaryReps = TreeMap<Char, String>()

    for (c in 'A'..'F') {
        val binary = Integer.toBinaryString(c.toInt())
        binaryReps[c] = binary
    }

    for ((k,v) in binaryReps) {
        println("key = $k, value = $v")
    }
}