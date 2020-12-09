package solutions.day09

import solutions.GenericSolution
import util.readLines
import java.io.File

class Solution : GenericSolution {
    private fun isValid(number: Long, preamble: List<Long>): Boolean {
        for (preambleNumber in preamble) {
            if (preamble.contains(number - preambleNumber)) {
                return true
            }
        }

        return false
    }

    private fun findFirstInvalid(numbers: List<Long>, preambleSize: Int): Long {
        var index = preambleSize
        while (index < numbers.size) {
            if (!isValid(numbers[index], numbers.slice(index - preambleSize until index))) {
                return numbers[index]
            }
            index++
        }
        return -1
    }

    override fun runPart1(inputFile: File): String {
        val input = readLines(inputFile)
            .map { it.toLong() }
        return findFirstInvalid(input, 25).toString()
    }

    override fun runPart2(inputFile: File): String {
        TODO("Not yet implemented")
    }
}
