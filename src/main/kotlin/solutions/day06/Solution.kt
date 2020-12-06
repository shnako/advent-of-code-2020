package solutions.day06

import solutions.GenericSolution
import util.readFileAsString
import java.io.File

/**
 * A simple solution using set operations.
 */
class Solution : GenericSolution {
    private fun parseInputToGroups(inputFile: File): List<String> {
        val inputText = readFileAsString(inputFile)
        return inputText.split("\n\n")
    }

    private fun calculateGroupSumPart1(group: String): Int {
        return group
            .split("\n")
            .map { person -> person.toCharArray() }
            .flatMap { it.asIterable() }
            .toSet()
            .count()
    }

    private fun calculateGroupSumPart2(group: String): Int {
        val persons = group
            .split("\n")
            .map { person -> person.toCharArray().toSet() }
            .filter { it.isNotEmpty() }
        var intersection: Set<Char> = persons[0]
        for (person in persons) {
            intersection = intersection.intersect(person)
        }
        return intersection.count()
    }

    override fun runPart1(inputFile: File): String {
        return parseInputToGroups(inputFile)
            .map { calculateGroupSumPart1(it) }
            .sum()
            .toString()
    }

    override fun runPart2(inputFile: File): String {
        return parseInputToGroups(inputFile)
            .map { calculateGroupSumPart2(it) }
            .sum()
            .toString()
    }
}
