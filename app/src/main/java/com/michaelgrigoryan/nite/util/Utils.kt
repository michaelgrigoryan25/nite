package com.michaelgrigoryan.nite.util

fun truncateString(string: String, maxChar: Int): String {
    return if (string.length < maxChar) {
        string
    } else {
        (string.trim().subSequence(0, maxChar)).toString() + "..."
    }
}