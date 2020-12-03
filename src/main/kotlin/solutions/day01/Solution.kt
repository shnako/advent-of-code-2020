package solutions.day01

import solutions.SolutionInterface
import util.readIntegers
import java.io.File

/**
 * Simple backtrack solution which can calculate the product for any number of numbers.
 */
class Solution : SolutionInterface {
    private val targetSum = 2020

    private fun calculate(components: Int, inputSet: HashSet<Int>, selectedValues: HashSet<Int>): Int {
        if (components == 1) {
            if (inputSet.contains(targetSum - selectedValues.sum())) {
                selectedValues.add(targetSum - selectedValues.sum())
                return selectedValues.reduce { acc, i -> acc * i }
            }
        } else {
            for (value in inputSet) {
                if (selectedValues.contains(value)) {
                    continue
                }

                selectedValues.add(value)
                val result = calculate(components - 1, inputSet, selectedValues)
                if (result != -1) {
                    return result
                }
                selectedValues.remove(value)
            }
        }

        return -1
    }

    override fun runPart1(inputFile: File): String {
        val input = readIntegers(inputFile)
        val inputSet = HashSet(input)

        return calculate(2, inputSet, HashSet()).toString()
    }

    override fun runPart2(inputFile: File): String {
        val input = readIntegers(inputFile)
        val inputSet = HashSet(input)

        return calculate(3, inputSet, HashSet()).toString()
    }
}
