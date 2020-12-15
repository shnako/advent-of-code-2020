package solutions.day15

import solutions.GenericSolution
import util.readLines
import java.io.File

class Solution : GenericSolution {

    private fun parseNumber(inputFile: File): MutableList<Int> {
        return readLines(inputFile)[0]
            .split(",")
            .map { it.toInt() }
            .toMutableList()
    }

    @Suppress("unused", "kotlin:S1144") // Keeping as alternate solution.
    private fun playTurnNaive(spokenNumbers: MutableList<Int>) {
        var lastNumberSpoken = -1
        var previousOccurrence = -1

        for (i in spokenNumbers.indices.reversed()) {
            if (lastNumberSpoken == -1) {
                lastNumberSpoken = spokenNumbers[i]
                continue
            }

            if (spokenNumbers[i] == lastNumberSpoken) {
                previousOccurrence = i + 1
                break
            }
        }

        if (previousOccurrence == -1) {
            spokenNumbers.add(0)
        } else {
            spokenNumbers.add(spokenNumbers.size - previousOccurrence)
        }
    }

    private fun playTurnOptimal(spokenNumbers: MutableList<Int>, lastIndexMap: HashMap<Int, Int>): Int {
        val lastSpokenNumber = spokenNumbers.last()

        if (lastIndexMap.containsKey(lastSpokenNumber)) {
            spokenNumbers.add(spokenNumbers.size - lastIndexMap[lastSpokenNumber]!!)
        } else {
            spokenNumbers.add(0)
        }

        lastIndexMap[lastSpokenNumber] = (spokenNumbers.size - 1)

        return lastSpokenNumber
    }

    private fun play(turns: Int, numbers: MutableList<Int>): Int {
        val lastIndexMap = HashMap<Int, Int>()
        for (i in 0..numbers.size - 2) {
            lastIndexMap[numbers[i]] = (i + 1)
        }

        for (turn in numbers.size until turns) {
            playTurnOptimal(numbers, lastIndexMap)
        }

        return numbers.last()
    }

    override fun runPart1(inputFile: File): String {
        val numbers = parseNumber(inputFile)
        val result = play(2020, numbers)
        return result.toString()
    }

    override fun runPart2(inputFile: File): String {
        val numbers = parseNumber(inputFile)
        val result = play(30000000, numbers)
        return result.toString()
    }
}
