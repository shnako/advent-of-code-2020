package solutions.day05

import solutions.GenericSolution
import util.readLines
import java.io.File
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Using a basic binary search algorithm to find the seat number.
 */
class Solution : GenericSolution {
    private val upperBoundRow = 127
    private val upperBoundCol = 7

    private fun mapCharToBinary(ch: Char): Int {
        when (ch) {
            'F' -> return 0
            'B' -> return 1
            'L' -> return 0
            'R' -> return 1
        }

        throw InputMismatchException()
    }

    private fun binarySearch(code: List<Int>, upperBoundMax: Int): Int {
        var lowerBound = 0
        var upperBound = upperBoundMax
        for (direction in code) {
            if (direction == 0) {
                upperBound = floor((lowerBound + upperBound).toDouble() / 2).toInt()
            } else {
                lowerBound = ceil((lowerBound + upperBound).toDouble() / 2).toInt()
            }
        }
        return lowerBound
    }

    private fun calculateSeatNumber(code: String): Int {
        val binaryCode = code.map { mapCharToBinary(it) }
        val row = binarySearch(binaryCode.slice(0..6), upperBoundRow)
        val col = binarySearch(binaryCode.slice(7..9), upperBoundCol)
        return row * 8 + col
    }

    override fun runPart1(inputFile: File): String {
        return readLines(inputFile)
            .map { calculateSeatNumber(it) }
            .maxOrNull()
            .toString()
    }

    override fun runPart2(inputFile: File): String {
        val seats = readLines(inputFile)
            .map { calculateSeatNumber(it) }
            .sorted()
        for (seat in seats) {
            if (!seats.contains(seat + 1)) {
                return (seat + 1).toString()
            }
        }
        return "failed"
    }
}
