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

    private fun playTurn(spokenNumbers: MutableList<Long>) {
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

    private fun play(turns: Long, numbers: MutableList<Long>): Long {
        for (turn in numbers.size until turns) {
            playTurn(numbers)
            if (turn % 1000 == 0L) {
                println(turn)
            }
        }

        return numbers.last()
    }

    override fun runPart1(inputFile: File): String {
        val numbers = parseNumber(inputFile)
        val result = play(2020, numbers)
        return result.toString()
    }

    override fun runPart2(inputFile: File): String {
        TODO()
    }
}
