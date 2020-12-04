package util

import java.io.File

fun getInputFileForDay(day: Int): File {
    val dayStr = if (day < 10) "day0$day" else "day$day"
    return getInputFileForDay(dayStr)
}

fun getInputFileForDay(day: String): File {
    return File("src/main/resources/solutions/$day/input.txt")
}

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
