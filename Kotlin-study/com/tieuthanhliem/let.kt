package com.tieuthanhliem

fun sendEmailTo(email: String) {
    println("Sending email to $email")
}

fun testLet() {
    var email: String? = "tieuthanhliem@gmail.com"
    email?.let {
        sendEmailTo(it)
    }

    email = null
    email?.let {
        sendEmailTo(it)
    }
}