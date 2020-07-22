package com.tieuthanhliem

data class Point(val x:Int, val y: Int) {
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }
}

operator fun Point.minus(other: Point): Point {
    return Point(x - other.x, y - other.y)
}

fun testPlus() {
    val p1 = Point(1, 1)
    val p2 = Point(2, 2)
    println(p1 + p2)
    println(p1 - p2)
}