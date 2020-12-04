package util

import java.io.File

fun readLines(file: File): List<String> {
    return file.readLines()
}

fun readIntegers(file: File): List<Int> {
    return readLines(file).map { it.toInt() }
}

fun read2dCharArray(file: File): Array<CharArray> {
    return readLines(file).map { it.toCharArray() }.toTypedArray()
}

fun readFileAsString(file: File): String {
    return file.readText()
}
