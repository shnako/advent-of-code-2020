package solutions.day15

import solutions.GenericSolution
import util.readLines
import java.io.File

class Solution : GenericSolution {

    private fun parseNumber(inputFile: File): MutableList<Long> {
        return readLines(inputFile)[0]
            .split(",")
            .map { it.toLong() }
            .toMutableList()
    }

    @Suppress("unused", "kotlin:S1144") // Keeping as alternate solution.
    private fun playTurnNaive(spokenNumbers: MutableList<Long>) {
        var lastNumberSpoken = -1L
        var previousOccurrence = -1L

        for (i in spokenNumbers.indices.reversed()) {
            if (lastNumberSpoken == -1L) {
                lastNumberSpoken = spokenNumbers[i]
                continue
            }

            if (spokenNumbers[i] == lastNumberSpoken) {
                previousOccurrence = i.toLong() + 1
                break
            }
        }

        if (previousOccurrence == -1L) {
            spokenNumbers.add(0)
        } else {
            spokenNumbers.add(spokenNumbers.size - previousOccurrence)
        }
    }

    private fun playTurnOptimal(spokenNumbers: MutableList<Long>, lastIndexMap: HashMap<Long, Long>): Long {
        val lastSpokenNumber = spokenNumbers.last()

        if (lastIndexMap.containsKey(lastSpokenNumber)) {
            spokenNumbers.add(spokenNumbers.size - lastIndexMap[lastSpokenNumber]!!)
        } else {
            spokenNumbers.add(0)
        }

        lastIndexMap[lastSpokenNumber] = (spokenNumbers.size - 1).toLong()

        return lastSpokenNumber
    }

    private fun play(turns: Long, numbers: MutableList<Long>): Long {
        val lastIndexMap = HashMap<Long, Long>()
        for (i in 0..numbers.size - 2) {
            lastIndexMap[numbers[i]] = (i + 1).toLong()
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
