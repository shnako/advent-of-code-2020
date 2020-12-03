package util

import java.io.File

fun readLines(file : File) : List<String> {
    return file.readLines()
}

fun readIntegers(file : File) : List<Int> {
    return readLines(file).map { it.toInt() }
}
