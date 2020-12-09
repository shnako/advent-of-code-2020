package solutions.day09

import solutions.GenericSolution
import util.readLines
import java.io.File


/**
 * The first part simply does what the problem asks so nothing fancy.
 *
 * The second part would normally be a Knapsack problem.
 * However, because we know:
 * - the items in the sum are contiguous
 * - the input is relatively small
 * - I've only slept for less than 1 hour in an airport last night
 * I will simply bruteforce it :)
 */
class Solution : GenericSolution {
    private val preambleSize = 25

    private fun isValid(number: Long, preamble: List<Long>): Boolean {
        for (preambleNumber in preamble) {
            if (preamble.contains(number - preambleNumber)) {
                return true
            }
        }

        return false
    }

    private fun findFirstInvalid(numbers: List<Long>): Long {
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
        return findFirstInvalid(input).toString()
    }

    private fun findEncryptionWeakness(invalidNumber: Long, numbers: List<Long>, sumSize: Int): Long {
        var bottomIndex = 0
        var topIndex = sumSize - 1
        var sum = numbers.slice(bottomIndex..topIndex).sum()

        while (topIndex < numbers.size - 1) {
            if (sum == invalidNumber) {
                val sumNumbers = numbers.slice(bottomIndex..topIndex)
                return sumNumbers.minOrNull()!! + sumNumbers.maxOrNull()!!
            }

            topIndex++

            sum -= numbers[bottomIndex]
            sum += numbers[topIndex]

            bottomIndex++
        }


        return -1
    }

    override fun runPart2(inputFile: File): String {
        val input = readLines(inputFile)
            .map { it.toLong() }
        val invalidNumber = findFirstInvalid(input)
        for (size in 2..input.size) {
            val encryptionWeakness = findEncryptionWeakness(invalidNumber, input, size)
            if (encryptionWeakness != -1L) {
                return encryptionWeakness.toString()
            }
        }

        return "not found"
    }
}
