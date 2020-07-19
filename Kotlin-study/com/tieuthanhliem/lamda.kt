package com.tieuthanhliem

import java.util.*

fun test2() = println("Liem is tesing lamda2")

fun lamda(){
    val test = { println("Liem is tesing lamda") }
    run(test)
    run(::test2)
}